package com.mywork.myapplication.weather_station;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mywork.myapplication.R;

public class UsernameChange extends BaseActivity {
	
	private ImageView imageView;
	private Button btnUsernameChange;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usernamechange);
		//获得button1并且为其添加监听
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

		btnUsernameChange=(Button) findViewById(R.id.btnUsernameChange);
		btnUsernameChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(UsernameChange.this);
                builder.setIcon(R.mipmap.test);
                builder.setTitle("请输入用户名和密码");
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View view = LayoutInflater.from(UsernameChange.this).inflate(R.layout.changeuser, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(view);

                final EditText username = (EditText)view.findViewById(R.id.username);
                final EditText password = (EditText)view.findViewById(R.id.password);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String a = username.getText().toString().trim();
                        String b = password.getText().toString().trim();
                        //    将输入的用户名和密码打印出来
                        Toast.makeText(UsernameChange.this, "用户名: " + a + ", 密码: " + b, Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        
                    }
                });
                builder.show();
            }
        });
		
		
	}
	
	
	

}
