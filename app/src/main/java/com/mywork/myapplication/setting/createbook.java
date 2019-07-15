package com.mywork.myapplication.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mywork.myapplication.R;
import com.mywork.myapplication.weather_station.Weather_StationActivity;


public class createbook extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button createhuitui= (Button) findViewById(R.id.createhuitui);
        createhuitui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(createbook.this, Weather_StationActivity.class);
                startActivity(intent);
            }
        });
    }



}
