package com.example.admin.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.myapplication.Head_layout.head_about;

import java.util.ArrayList;
import java.util.List;

import static com.example.admin.myapplication.MainActivity.activityList;
import static com.example.admin.myapplication.MainActivity.pref;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {
    private static RadioButton main_tab,wave_tab,history_tab;
    private RadioGroup radioGroup; private FrameLayout frameLayout;
    private Fragment_show fragment_1;    private Fragment_chart fragment_2;    private Fragment_history fragment_3;
    private List<Fragment> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        activityList.add(this);
        //显示图片本身的颜色
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        //引入header和menu
       // navigationView.inflateHeaderView(R.layout.nav_header);
     //   navigationView.inflateMenu(R.menu.main);
        View headerView = navigationView.getHeaderView(0);//获取头布局
        //跳转
        View head_user = headerView.findViewById(R.id.head_user);
        head_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.edit().putBoolean("fir", false).commit();
                Intent intent = new Intent(FragmentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //获取Item的事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //在这里处理item的点击事件
                switch (item.getItemId()) {
                    case R.id.about:

                        Intent intent = new Intent(FragmentActivity.this, head_about.class);
                        startActivity(intent);
                        break;
                    case R.id.quit:

                        AlertDialog.Builder builder = new AlertDialog.Builder(FragmentActivity.this);
                        // 设置参数
                        builder.setMessage("确定要注销此账号吗？")
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
                                    pref.edit().putBoolean("fir", true).commit();
                                    act.finish();
                                }
                                System.exit(0);
                            }
                        });
                        builder.create().show();
                        break;
                }
                return true;
            }
        });
        //使用管理器开启事务
        FragmentManager fragmentManager = getSupportFragmentManager();
        //不同按钮对应着不同的Fragment对象页面
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //找到按钮
        main_tab=(RadioButton)findViewById(R.id.main_tab);
        wave_tab=(RadioButton)findViewById(R.id.wave_tab);
        history_tab=(RadioButton)findViewById(R.id.history_tab);
        //创建Fragment对象及集合
        fragment_1 = new Fragment_show();
        fragment_2 = new Fragment_chart();
        fragment_3 = new Fragment_history();
        //将Fragment对象添加到list中
        list = new ArrayList<>();
        list.add(fragment_1);
        list.add(fragment_2);
        list.add(fragment_3);
        main_tab.setOnClickListener((View.OnClickListener) this);
        wave_tab.setOnClickListener((View.OnClickListener) this);
        history_tab.setOnClickListener((View.OnClickListener) this);
        //设置RadioGroup开始时设置的按钮，设置第一个按钮为默认值
        radioGroup.check(R.id.main_tab);
        //初始时向容器中添加第一个Fragment对象
        fragmentTransaction.replace(R.id.framelayout,fragment_1);
        fragmentTransaction.commit();


    }
  public void exit()
  {
      for(Activity act:activityList)
      {
          act.finish();
      }
      System.exit(0);
  }
  //底部按钮的事件
    public void onClick(View view) {
       FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.main_tab:
                    fragmentTransaction.replace(R.id.framelayout,fragment_1);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                break;
            case R.id.wave_tab:
                    fragmentTransaction.replace(R.id.framelayout,fragment_2);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction. commit();

                break;
            case R.id.history_tab:
                    fragmentTransaction.replace(R.id.framelayout,fragment_3);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                break;
            default:
                break;
        }
    }
    //返回
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
