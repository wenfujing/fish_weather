package com.mywork.myapplication.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mywork.myapplication.R;
import com.mywork.myapplication.weather_station.BaseActivity;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/*找回密码*/
public class LostFindActivity extends BaseActivity {
    private EditText edit1; // 输入用户名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
//设置屏幕为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 初始化界面
        setupViews();
    }
    public void setupViews(){
        //lost_find.xml 页面中获取对应的UI控件
        final TextView email = (TextView)findViewById(R.id.email);
        TextView phone = (TextView)findViewById(R.id.phone);
        final TextView edit= (TextView)findViewById(R.id.edit1) ;
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String editing = edit.getText().toString().trim();
                //按钮
                if(TextUtils.isEmpty(editing)){
                    showToast(getApplicationContext(),"请输入用户名");
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            super.run();
                            Object result;
                            //调用编写的isEmail()函数
                            //获取到输入的内容并转换为字符串形式，这样才可以进行匹配，且邮箱格式不能太长，字符串长度31以内
                            //按照实际需要可以添加不同的事件
                            if (TextUtils.isEmpty((CharSequence) edit1)) {
                                showToast(getApplicationContext(),"请输入用户名");
                            }else {
                                try {
                                    HttpURLConnection connection = (HttpURLConnection) new URL("http://10.168.14.3:8080/auth/regist").openConnection();
                                    connection.setRequestMethod("POST");
                                    connection.setDoInput(true);
                                    connection.setDoOutput(true);
                                    connection.setUseCaches(false);
                                    connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
                                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

                                    Map params = new HashMap();
                                 /*   params.put("username", username);
                                    params.put("password", psw);
                                    params.put("sex", (sex == 0 ? "女" : "男"));
                                    params.put("email", Email);*/

                                    outputStream.write(new JSONObject(params).toString().getBytes()); // POST
                                    connection.connect(); // GET

                                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                        String inputLine = null;

                                        while ((inputLine = bufferedReader.readLine()) != null) {
                                            Log.i("result", inputLine);
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            showToast(getApplicationContext(),"找回成功");
                                            startActivity(intent);
                                            finish();
                                        }

                                        bufferedReader.close();
                                    }
                                    showToast(getApplicationContext(),"网络连接错误");

                                    connection.disconnect();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                showToast(getApplicationContext(),"服务器连接错误");
                            }
                        }
                    }.start();
                }

            }
        });
        phone.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String editing = edit.getText().toString().trim();
                //按钮
                if(TextUtils.isEmpty(editing)){
                    showToast(getApplicationContext(),"请输入用户名");
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            super.run();
                            Object result;
                            //调用编写的isEmail()函数
                            //获取到输入的内容并转换为字符串形式，这样才可以进行匹配，且邮箱格式不能太长，字符串长度31以内
                            //按照实际需要可以添加不同的事件
                            if (TextUtils.isEmpty((CharSequence) edit1)) {
                                showToast(getApplicationContext(),"请输入用户名");
                            }else {
                                try {
                                    HttpURLConnection connection = (HttpURLConnection) new URL("http://47.111.134.50:8100/auth/regist").openConnection();
                                    connection.setRequestMethod("POST");
                                    connection.setDoInput(true);
                                    connection.setDoOutput(true);
                                    connection.setUseCaches(false);
                                    connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
                                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

                                    Map params = new HashMap();
                                 /*   params.put("username", username);
                                    params.put("password", psw);
                                    params.put("sex", (sex == 0 ? "女" : "男"));
                                    params.put("email", Email);*/

                                    outputStream.write(new JSONObject(params).toString().getBytes()); // POST
                                    connection.connect(); // GET

                                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                        String inputLine = null;

                                        while ((inputLine = bufferedReader.readLine()) != null) {
                                            Log.i("result", inputLine);
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            showToast(getApplicationContext(),"找回成功");
                                            startActivity(intent);
                                            finish();
                                        }

                                        bufferedReader.close();
                                    }
                                    showToast(getApplicationContext(),"网络连接错误");

                                    connection.disconnect();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                showToast(getApplicationContext(),"服务器连接错误");
                            }
                        }
                    }.start();
                }


            }
        });
    }
    /**
     * 在线程中正常使用吐司
     **/
    private static Toast toast = null;
    public static void showToast(Context context, String text) {
        Looper myLooper = Looper.myLooper();
        if (myLooper == null) {
            Looper.prepare();
            myLooper = Looper.myLooper();
        }
        //设置toast的显示位置
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);

        toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, size.y / 9);


        toast.show();
        if ( myLooper != null) {
            Looper.loop();
            myLooper.quit();
        }

    }
}