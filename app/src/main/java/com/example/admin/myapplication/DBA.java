package com.example.admin.myapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by admin on 2019/1/19.
 */

public class DBA {
    private static final String URL = "jdbc:mysql://39.105.105.165:3306/Master_meter";
    private static final String USER="root";
    private static final String PWD="root";
    public static Connection connection()
    {
        Connection conn=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载驱动！");
            System.out.println("子线程runRing");
            conn = (Connection) DriverManager.getConnection(URL, USER, PWD);
            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象
            System.out.println("成功连接到数据库-表-user！");
           /* String sql = "select * from User ";
            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
            System.out.println("user\tpasswd");
            while (rs.next()) {
                if (rs.isLast()){
                    System.out.println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
                    System.out.println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
                }
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static boolean select(Connection conn,String sql) {
        boolean res = false;
        try {
            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);
            if (rs!=null&&rs.first()) {
                res=true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static boolean excel(Connection conn,String sql) {
        boolean res;
        try {
            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象
            stmt.execute(sql);
            res=true;
        } catch (SQLException e) {
            res=false;
        }
        return res;
    }
}
