package com.mywork.myapplication.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mywork.myapplication.R;

public class AgreementActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		FrameLayout fl=new FrameLayout(this);
		fl.setPadding(20, 20, 20, 20);
		TextView Tv=new TextView(this);
		Tv.setTextSize(24);
		Tv.setText(R.string.agreements);
		fl.addView(Tv);
		setContentView(fl);
	}
	
}
