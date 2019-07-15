package com.mywork.myapplication.weather_station;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mywork.myapplication.R;

public class ScanWeather_Station extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanweather_station);
        // 给返回按钮添加监听
        ImageView imgBack;
        imgBack = (ImageView) findViewById(R.id.imgLeft);
        imgBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                setResult(0x22, intent);
                finish();
            }
        });

    }

}
