package com.mywork.myapplication.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mywork.myapplication.R;
import com.mywork.myapplication.common.HeadActivity;
import com.mywork.myapplication.weather_station.Weather_StationActivity;

public class photo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touxiang);

        Button xiugaihuitui= (Button) findViewById(R.id.xiugaihuitui);
        xiugaihuitui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(photo.this, Weather_StationActivity.class);
                startActivity(intent);
            }
        });
        // 获取选择的图像
        ImageView img = (ImageView) findViewById(R.id.touxiang);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), HeadActivity.class);
                startActivityForResult(intent, 0x11);
            }
        });
    }
}
