package com.mywork.myapplication.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mywork.myapplication.R;
import com.mywork.myapplication.login.LoginActivity;

public class cancel extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        Button cancelhuitui= (Button) findViewById(R.id.cancelhuitui);
        cancelhuitui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cancel.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
