package com.mywork.myapplication.common;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mywork.myapplication.R;

import java.util.ArrayList;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class book_list extends AppCompatActivity {
    ArrayList<String> author=new ArrayList<String>();
    ArrayList<String> bookname=new ArrayList<String>();
    ArrayList<String> describtion=new ArrayList<String>();
    ArrayList<String> img=new ArrayList<String>();
    ArrayList<Integer> book_id=new ArrayList<Integer>();
    ArrayList<String> article_url=new ArrayList<String>();
    private Handler handler=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myset);
    }
}
