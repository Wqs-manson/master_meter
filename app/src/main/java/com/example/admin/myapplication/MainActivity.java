package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static EditText Edt_user,Edt_paswd;
    private static Button btn_login;
    private static CheckBox checkBox;
    private static TextView text_Mreg,text_quit;
    public static List<Activity> activityList = new LinkedList();
    public boolean State;
    public static SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstStart();
        setContentView(R.layout.activity_main);
        MainActivity.activityList.add(this);
        //相应的控件
        Edt_user=(EditText)findViewById(R.id.Edt_user);
        Edt_paswd=(EditText)findViewById(R.id.Edt_paswd);
        btn_login=(Button)findViewById(R.id.btn_login);
        text_Mreg=(TextView)findViewById(R.id.text_Mreg);
        text_quit=(TextView)findViewById(R.id.text_quit);
        checkBox=(CheckBox)findViewById(R.id.checkBox);



        //登陆
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user, pass;
                user=Edt_user.getText().toString().trim();
                pass=Edt_paswd.getText().toString().trim();
                final String sql="select * from user where user='"+user+"' and passwd ='"+pass+"'";//匹配
              //  System.out.println(sql);
               new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();//创建新的消息队列
                        Connection conn= DBA.connection();
                       if (DBA.select(conn,sql)) {
                           pref.edit().putBoolean("fir", false).commit();
                           Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(MainActivity.this, FragmentActivity.class);
                           startActivity(intent);
                        }else{
                           pref.edit().putBoolean("fir", true).commit();
                          Toast.makeText(MainActivity.this, "账户或密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                       }
                        Looper.loop();//开始
                    }
                }).start();
            }
        });
        //设置密码可不可见
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (checkBox.isChecked()){
                    Edt_paswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    Edt_paswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //注册
        text_Mreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });
        //退出
        text_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建构建器
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // 设置参数
                builder.setMessage("确定要退出程序吗？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        for(Activity act:activityList)
                        {
                            act.finish();
                        }
                        System.exit(0);
                    }
                });
                builder.create().show();
            }
        });
    }
    //设置是否第一次登陆
    private void isFirstStart(){
       pref=getSharedPreferences("first", Context.MODE_PRIVATE);
        State=pref.getBoolean("fir",true);
        if(!State){
            startActivity(new Intent(MainActivity.this,FragmentActivity.class));
            finish();
        }
        pref.edit().commit();

    }


}
