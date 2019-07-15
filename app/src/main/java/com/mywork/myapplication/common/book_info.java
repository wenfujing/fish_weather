package com.mywork.myapplication.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mywork.myapplication.R;

import java.util.ArrayList;

public class book_info extends AppCompatActivity {
    int flag;
    Handler handler;
    ArrayList<Fragment> datas=new ArrayList<>();;
    ViewPager mViewPager;
    int book_id;
    String book_name;
    String book_author;
    String book_introduction;
    String book_pic;
    String article_url;
    ArrayList<String> user_name=new ArrayList<String>();
    ArrayList<String> post_context=new ArrayList<String>();
    ArrayList<Integer> chapter_id=new ArrayList<Integer>();
    ArrayList<String> chapter_name=new ArrayList<String>();
    ArrayList<String> chapter_context=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myset);
        Intent intent=getIntent();
        handler=new Handler();
        flag=intent.getIntExtra("flag",0);
        book_id=intent.getIntExtra("book_id",0);
        final String urlStr="http://122.114.237.201/read/bookinfo";
        final String urlStr_article="http://122.114.237.201/read/Articleinfo";
        final String urlStr2="http://122.114.237.201/read/chapters";


    }
}
