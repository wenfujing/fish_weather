package com.mywork.myapplication.service;

import android.util.Log;


import com.mywork.myapplication.utils.StreamTools;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PersonalDateService {

	//返回null表示登录异常
	public static String btnsaveByClientPost(String id, String sex, String username,
                                             String name, String e_mail,String identity) {
		try {
			//打开一个浏览器
			HttpClient httpClient = new DefaultHttpClient();

			//输入地址
			String path = "http://47.111.134.50:8100/auth/applogin";
			HttpPost httpPost = new HttpPost(path);

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("id", id));
			parameters.add(new BasicNameValuePair("username", username));
			parameters.add(new BasicNameValuePair("name", name));
			parameters.add(new BasicNameValuePair("sex", sex));
			parameters.add(new BasicNameValuePair("email", e_mail));
			parameters.add(new BasicNameValuePair("identity", identity));

			httpPost.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
			Log.i("sss", "=======--++");
			//敲回车
			HttpResponse response=httpClient.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();

			//判断状态
			if(code== 200) {
				//success
				InputStream is =response.getEntity().getContent();
				String text = StreamTools.readInputStream(is);
				return text;
			}else {
				//error
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
}
