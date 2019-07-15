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
/*控制气象站的数据post*/
public class MySetActivityService {

    //返回null表示登录异常
    public static String MyfishgetByClientPost(String id) {
        try {
            //打开一个浏览器
            HttpClient httpClient = new DefaultHttpClient();

            //输入地址
            String path = "http://10.168.14.3:8080/meteorological/sp.json";
            HttpPost httpPost = new HttpPost(path);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("id", id));

            httpPost.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));

            //敲回车
            HttpResponse response=httpClient.execute(httpPost);
            int  code= response.getStatusLine().getStatusCode();
            Log.i("code", String.valueOf(code));
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


    public static String setByClientPost(String WSid,String temperhigh,
                                               String temperlow,String O2high,String O2low,
                                               String lighthigh,String lightlow) {
        try {
            //打开一个浏览器
            HttpClient httpClient = new DefaultHttpClient();

            //输入地址
            String path = "http://10.168.14.55:8080/meteorological/sp";
            HttpPost httpPost = new HttpPost(path);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("id", WSid));
            parameters.add(new BasicNameValuePair("maxTemp", temperhigh));
            parameters.add(new BasicNameValuePair("minTemp", temperlow));
            parameters.add(new BasicNameValuePair("maxPh", O2high));
            parameters.add(new BasicNameValuePair("minPh", O2low));
            parameters.add(new BasicNameValuePair("maxLight", lighthigh));
            parameters.add(new BasicNameValuePair("minLight", lightlow));

            httpPost.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));

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
