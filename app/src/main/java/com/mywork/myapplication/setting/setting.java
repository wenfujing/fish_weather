package com.mywork.myapplication.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mywork.myapplication.R;
import com.mywork.myapplication.weather_station.Weather_StationActivity;


public class setting extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button contact= (Button) findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, contact.class);
                startActivity(intent);
            }
        });

        Button cancel= (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences user_data=getSharedPreferences("user_data",MODE_PRIVATE);
                int user_id=user_data.getInt("user_id",0);
                if(user_id==0){
                    Toast.makeText(setting.this,"你本来就还没有登录........有啥好退的.....",Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor et=user_data.edit();
                    et.remove("user_id");
                    et.commit();
                    Intent intent = new Intent(setting.this, cancel.class);
                    startActivity(intent);
                    Toast.makeText(setting.this,"你已经成功离开了......再见了您勒！",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button settinghuitui= (Button) findViewById(R.id.settinghuitui);
        settinghuitui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, Weather_StationActivity.class);
                startActivity(intent);
            }
        });

    }
}
