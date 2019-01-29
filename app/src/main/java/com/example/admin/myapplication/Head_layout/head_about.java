package com.example.admin.myapplication.Head_layout;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.example.admin.myapplication.R;

public class head_about extends AppCompatActivity {
    private static TextView head_about_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_about);
        head_about_textview=(TextView)findViewById(R.id.head_about_textview);
        Spannable string = new SpannableString("\t\t\t\t\t\t智能电表\n" +
                "\t\t\t\t\t 本APP是对电压、电流的精确计算。");
        // 背景色
        //string.setSpan(new BackgroundColorSpan(Color.RED), 2, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 粗体
        string.setSpan(new StyleSpan(Typeface.BOLD), 4, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 字体大小
        string.setSpan(new AbsoluteSizeSpan(50), 11, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 显示
        head_about_textview.setText(string);
    }
}
