package com.mywork.myapplication.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mywork.myapplication.R;
import com.mywork.myapplication.weather_station.Weather_StationActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    private static boolean mBackKeyPressed=false;		//记录是否首次点击按键
    private String userName,psw,spPsw;//获取的用户名，密码，加密密码
    private EditText et_user_name,et_psw;//编辑框
    private CheckBox rem_pw;
    private CheckBox checkBox2;
    private TextView tv_lgi_hop;
    private SharedPreferences sp;
    public static String cookieVal="";
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    //获取界面控件
    private void init() {
        //从main_title_bar中获取的id
        //从activity_login.xml中获取的
        TextView tv_register = (TextView) findViewById(R.id.tv_register);
        TextView tv_find_psw = (TextView) findViewById(R.id.tv_find_psw);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        et_user_name= (EditText) findViewById(R.id.et_user_name);
        et_psw= (EditText) findViewById(R.id.et_psw);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        rem_pw = (CheckBox) findViewById(R.id.checkbox1);						//获取记住密码框
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);						//获取记住密码框
        tv_lgi_hop=(TextView) findViewById(R.id.lgi_tv_hop);

        //案键 跳过 点击事件
        tv_lgi_hop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent1=new Intent(LoginActivity.this, Weather_StationActivity.class);
                    intent1.putExtra("userkey","{\"user\":{\"face\":\"http://47.111.134.50:8100/headport/92eee6c96ffb4f0395b329f8faf017cf.jpeg\",\"identity\":\"试用身份\",\"sex\":\"男\",\"name\":\"李白\",\"id\":81,\"email\":\"123456@qq.com\",\"hobby\":null,\"username\":\"试用用户\"}}");
                    startActivity(intent1);
                    finish();
            }
        });
        //监听协议多选框按钮事件
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (checkBox2.isChecked()) {

                   /* System.out.println("协议已选中");
                    Intent intent=new Intent(LoginActivity.this, AgreementActivity.class);*/
                    /*Toast.makeText(LoginActivity.this,"记住密码已选中", Toast.LENGTH_SHORT).show();*/
                    sp.edit().putBoolean("ISCHECK1", true).commit();
                   /* startActivityForResult(intent, 1);*/
                }else {

                    System.out.println("记住密码没有选中");
                    /*Toast.makeText(LoginActivity.this,"记住密码没有选中", Toast.LENGTH_SHORT).show();*/
                    sp.edit().putBoolean("ISCHECK1", false).commit();

                }

            }
        });
        //判断记住协议多选框的状态
        if(sp.getBoolean("ISCHECK1", false)) {
            //设置默认是记录密码状态
            checkBox2.setChecked(true);

        }
        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (rem_pw.isChecked()) {

                    System.out.println("记住密码已选中");
                    /*Toast.makeText(LoginActivity.this,"记住密码已选中", Toast.LENGTH_SHORT).show();*/
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {

                    System.out.println("记住密码没有选中");
                    /*Toast.makeText(LoginActivity.this,"记住密码没有选中", Toast.LENGTH_SHORT).show();*/
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });
        //判断记住密码多选框的状态
        if(sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            et_user_name.setText(sp.getString("USER_NAME", ""));
            et_psw.setText(sp.getString("PASSWORD", ""));

        }

        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LostFindActivity.class));
            }
        });

        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始登录，获取用户名和密码 getText().toString().trim();
                LoginActivity.this.userName = et_user_name.getText().toString().trim();
                LoginActivity.this.psw = et_psw.getText().toString().trim();

                final ByteArrayOutputStream BufferedReader = new ByteArrayOutputStream();
                new Thread() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        super.run();
                        String inputLine = null;

                        if (TextUtils.isEmpty(userName)) {
                            showToast(LoginActivity.this, "请输入用户名");
                            /*Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();*/
                        } else if (TextUtils.isEmpty(psw)) {
                            showToast(LoginActivity.this, "请输入密码");
                            // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                        }else if (!checkBox2.isChecked()) {
                            showToast(LoginActivity.this, "首次见面，请确认协议");
                        }
                            else{
                                try {
                                    HttpURLConnection connection = (HttpURLConnection) new URL("http://47.111.134.50:8100/auth/applogin").openConnection();
                                    connection.setRequestMethod("POST");
                                    connection.setDoInput(true);
                                    connection.setDoOutput(true);
                                    connection.setUseCaches(false);
                                    connection.setConnectTimeout(1000);
                                    connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
                                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                                    Map params = new HashMap();
                                    params.put("username", userName);
                                    params.put("password", psw);
                                    outputStream.write(new JSONObject(params).toString().getBytes()); // POST
                                    connection.connect(); // GET
                                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                        /*Toast.makeText(LoginActivity.this,"登陆成功", Toast.LENGTH_SHORT).show();*/
                                        while ((inputLine = bufferedReader.readLine()) != null) {
                                            if (rem_pw.isChecked()) {
                                                // 记住用户名、密码
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.putString("USER_NAME", userName);
                                                editor.putString("PASSWORD", psw);
                                                editor.commit();
                                            }
                                            // 跳转界面
                                            Intent intent = new Intent(getApplicationContext(), Weather_StationActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("userkey", String.valueOf(inputLine));
                                            intent.putExtras(bundle);
                                            finish();
                                            startActivity(intent);
                                            showToast(LoginActivity.this, "登录成功");
                                        }
                                        bufferedReader.close();
                                    } else {
                                        Log.i("reault","run");
                                        showToast(LoginActivity.this, "输入的用户名和密码不一致");
                                    }
                                    connection.disconnect();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    showToast(getApplicationContext(), "连接网络失败");
                                }
                            }
                        }
                }.start();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("提示!").setMessage("确定退出登陆！！！").setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }


    }
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }/**
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