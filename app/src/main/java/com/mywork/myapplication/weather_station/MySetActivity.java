package com.mywork.myapplication.weather_station;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mywork.myapplication.R;
import com.mywork.myapplication.service.MySetActivityService;

public class MySetActivity extends BaseActivity {

    private ImageView imgBack;
    private EditText temperhigh;
    private EditText temperlow;
    private EditText O2high;
    private EditText O2low;
    private EditText lighthigh;
    private EditText lightlow;
    private Button setWeather_StationConfig;

    String id;
    String	minTemper;
    String	maxTemper;
    String	minO2;
    String	maxO2;
    String	minLight;
    String	maxLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myset);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        /*String result = bundle.getString("fishConfig");*/
        /*Log.i("msg11", result);
        Log.i("fishconfig", result);
        try {

            JSONObject json = new JSONObject(result);			//解析从登陆界面获取的数据，并且解析
            Log.i("msg12", "+++++=====+++++");
            id = json.getString("ftId");
            minTemper =json.getString("minTemper");
            maxTemper = json.getString("maxTemper");
            minO2 = json.getString("minO2");
            maxO2 = json.getString("maxO2");
            minLight = json.getString("minLight");
            maxLight = json.getString("maxLight");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/



        //获取界面控件方法
        init();
        //给界面控件添加监听的方法
        textViewOnClickListener();
    }

    //获取界面的控件id
    public void init() {
        imgBack=(ImageView) findViewById(R.id.imgLeft);			//获取返回按钮图片控件
        temperhigh= (EditText) findViewById(R.id.ethigh1);
        temperlow = (EditText) findViewById(R.id.etlow1);
        O2high  = (EditText) findViewById(R.id.ethigh2);
        O2low   = (EditText) findViewById(R.id.etlow2);
        lighthigh = (EditText) findViewById(R.id.ethigh4);
        lightlow = (EditText) findViewById(R.id.etlow4);

        setWeather_StationConfig=(Button)findViewById(R.id.setFishConfig);

        temperlow.setText(minTemper);
        temperhigh.setText(maxTemper);
        O2low.setText(minO2);
        O2high.setText(maxO2);
        lightlow.setText(minLight);
        lighthigh.setText(maxLight);

    }

    //给界面控件添加监听
    public void textViewOnClickListener() {

        //给返回按钮添加监听
        imgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                setResult(0x22,intent);
                finish();
            }
        });
    }


    //用户配置设置保存
    public void setConfig(View v) {

        minTemper =temperlow.getText().toString().trim();
        maxTemper = temperhigh.getText().toString().trim();
        minO2 = O2low.getText().toString().trim();
        maxO2 = O2high.getText().toString().trim();
        minLight = lightlow.getText().toString().trim();
        maxLight = lighthigh.getText().toString().trim();

        new Thread() {

            @Override
            public void run() {
                super.run();
                final String fishConfigResult = MySetActivityService.setByClientPost(id, maxTemper, minTemper, maxO2, minO2, maxLight, minLight);
                if(fishConfigResult != null) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if(fishConfigResult.equals("false")) {
                                //跳转界面
                                Intent intent = new Intent(MySetActivity.this,MySetActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    //error
                    runOnUiThread(new Runnable() {

                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {
                            Toast.makeText(MySetActivity.this, "请求失败", 0).show();
                        }
                    });
                }
            }

        }.start();
    }

}
