package com.example.admin.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by admin on 2019/1/21.
 */

public class Fragment_show extends Fragment {

    private View mView;
    private static final String URL = "jdbc:mysql://39.105.105.165:3306/Master_meter";
    private static final String USER="root";
    private static final String PWD="root";
    private static EditText Edt_Show_V,Edt_Show_A,Edt_Show_P;
    private static Button btn_Show_start,btn_Show_end;
    private Handler mHandler;
    public static float P,A1,V1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意View对象的重复使用，以便节省资源
        mView = inflater.inflate(R.layout.activity_show,container,false);
        //相应的控件
        initView(mView);
        return mView;
    }
    private void initView(View mView) {
        Edt_Show_V=(EditText) mView.findViewById(R.id.Edt_Show_V);
        Edt_Show_A=(EditText) mView.findViewById(R.id.Edt_Show_A);
        Edt_Show_P=(EditText) mView.findViewById(R.id.Edt_Show_P);
        btn_Show_start=(Button) mView.findViewById(R.id.btn_Show_start);
        btn_Show_end=(Button) mView.findViewById(R.id.btn_Show_end);
        //事件
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        String Data_V = (String) msg.obj;
                        Edt_Show_V.setText(Data_V);
                        break;
                    case 1:
                        String Data_A = (String) msg.obj;
                        Edt_Show_A.setText(Data_A);
                        break;
                    case 2:
                        String Data_P = (String) msg.obj;
                        Edt_Show_P.setText(Data_P);
                        break;
                    default:
                        break;
                }
            }
        };
        //开始
        btn_Show_start.setTag(0);
        btn_Show_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag=(Integer)view.getTag();
                switch(tag){
                    case 0:
                        new Thread(new Runnable() {
                            private volatile boolean exit1 = false;
                            @Override
                            public void run() {
                                while(!exit1){
                                    try {
                                        java.sql.Connection conn;
                                        Class.forName("com.mysql.jdbc.Driver");
                                        System.out.println("成功加载驱动！");
                                        System.out.println("子线程runRing");
                                        conn =  DriverManager.getConnection(URL, USER, PWD);
                                        Statement stmt = conn.createStatement(); //创建Statement对象
                                        System.out.println("成功连接到数据库-表-Data！");
//resultv ->  V  ->msgv.obj ->   msgv.what   ->  Data_V    ->Edt_Show_V
                                        ResultSet resultv ;
                                        ResultSet resulta ;
                                        Thread.sleep(1000);
                                        resultv = stmt.executeQuery("select v from data");
                                        while (resultv.next()) {
                                            if (resultv.isLast()) {
                                                String V = resultv.getString("v");
                                                //   Log.i("MainActivity",V);
                                                Message msgv = new Message();
                                                msgv.obj = V;
                                                msgv.what = 0;
                                                mHandler.sendMessage(msgv);
                                                V1 = Float.valueOf(V);
                                            }
                                        }
                                        resulta = stmt.executeQuery("select a from Data");
                                        while (resulta.next()) {
                                            if (resulta.isLast()) {
                                                String A = resulta.getString("a");
                                                //    Log.i("MainActivity",A);
                                                Message msga = new Message();
                                                msga.obj = A;
                                                msga.what = 1;
                                                mHandler.sendMessage(msga);
                                                A1 = Float.valueOf(A);
                                            }
                                        }
                                        P = V1 * A1;
                                        String P1 = Float.toString(P);
                                        //     Log.i("MainActivity",P1);
                                        Message msgp = new Message();
                                        msgp.obj = P1;
                                        msgp.what = 2;
                                        mHandler.sendMessage(msgp);
                                        conn.close();
                                        stmt.close();
                                        resultv.close();
                                        resulta.close();
                                        btn_Show_end.setOnClickListener(new View.OnClickListener(){
                                            public void onClick(View v){
                                                exit1 = true;
                                            }
                                        });
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }).start();
                        break;
                }
            }
        });
    }

}
