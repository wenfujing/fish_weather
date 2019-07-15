package com.mywork.myapplication.setting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mywork.myapplication.R;



public class contact extends AppCompatActivity {

    private TextView tev;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        tev=(TextView)findViewById(R.id.textView7);
//        设置号码
        String number ="                    5201314";
//拨打电话
        callPhone(number);
        tev.setText(number);
    }
    public void callPhone(String number){
//        android6获取动态权限
        if(Build.VERSION.SDK_INT>=23){
            int REQUEST_CODE_CONTACT=101;
            String [] permissions = {Manifest.permission.CALL_PHONE};
//            验证是否有许可权限
            for(String str:permissions){
                if(this.checkSelfPermission(str)!= PackageManager.PERMISSION_GRANTED){
//                    申请权限
                    this.requestPermissions(permissions,REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data= Uri.parse("tel:"+number);
        intent.setData(data);
        startActivity(intent);
    }

}
