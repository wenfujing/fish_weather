package com.mywork.myapplication.weather_station;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

public class BaseActivity extends Activity {

	private myApplication application;
	private BaseActivity oContext;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(application == null) {
			//得到Application对象
			application=(myApplication) getApplication();

		}

		oContext =this;		//把当前的上下文对象复制给BaseActivity
		addActivity();		//调用添加Activity的方法
	}


	//添加Activity方法
	public void addActivity() {
		application.addActivity_(oContext);  //调用myApplication的添加Activity方法
	}

	 //销毁当个Activity的方法
	public void removeActivity() {
		application.removeActivity_(oContext);
	}

	//销毁所有的Activity
	public void removeALLActivity() {
		application.removeALLActivity_();
	}

	//定义Toast方法，提示所执行的内容，可以重复使用
	public void show_Toast(String text) {
		Toast.makeText(oContext, text, Toast.LENGTH_SHORT).show();
	}


	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}


    protected void onActivityCreated(Bundle savedInstanceState) {
    }
}
