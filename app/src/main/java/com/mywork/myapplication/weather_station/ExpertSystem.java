package com.mywork.myapplication.weather_station;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mywork.myapplication.R;

public class ExpertSystem extends BaseActivity implements Runnable {

    private ImageView imageView;
    private TextView showText;
    private boolean isVisible=true;
    private Spinner spinner;
    private Button btnExpert;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.expert_system);

        spinner=(android.widget.Spinner) findViewById(R.id.spinner1);		//获取下列列表控件
        btnExpert=(Button) findViewById(R.id.btnExpertAdvice);				//获取专家意见按钮控件
        showText=(TextView)findViewById(R.id.TextShow);						//获取显示列表选择的内容
        scrollView=(ScrollView) findViewById(R.id.scrollView1);				//添加滑动布局
        scrollView.setVisibility(View.GONE);								//设置滑动布局不可见

        spinner.getSelectedItem();											//点击下拉列表方法
        //给下拉列表添加监听，监听选择内容 ，并且将选择的内容显示到指定文本上
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?>parent,View arg1,int pos,long id) {
                String result=parent.getItemAtPosition(pos).toString();		//获取选择项的值
                showText.setText(result);

            }

            public void onNothingSelected(AdapterView<?>arg0) {

            }
        });



        btnExpert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExpertSystem.this);
                builder.setTitle("专家建议");
                //引入专家建议布局
                View view = LayoutInflater.from(ExpertSystem.this).inflate(R.layout.expert_advice, null);
                builder.setView(view);					//设置我自定义的布局文件为弹出窗口
                builder.show();							//显示弹窗
                builder.setCancelable(true);			//点击弹窗外，出发事件让弹框消失
                //点击我的专家意见按钮后判断滑动布局是否显示
                if(isVisible) {
                    isVisible=false;
                    scrollView.setVisibility(View.VISIBLE);			//显示滑动布局内容
                }else {
                    scrollView.setVisibility(View.GONE);			//隐藏滑动布局内容
                    isVisible=true;
                }
            }
        });

        //使用字符串数组创建适配器
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(
                this, R.array.ftype,android.R.layout.simple_dropdown_item_1line);
        //为适配器设置列表框下拉时的选项样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //给界面返回按钮添加监听
        imageView=(ImageView) findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                setResult(0x22,intent);
                finish();
            }
        });
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }
}
