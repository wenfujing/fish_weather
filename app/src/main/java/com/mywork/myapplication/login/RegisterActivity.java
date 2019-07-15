package com.mywork.myapplication.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mywork.myapplication.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*注册*/
public class RegisterActivity extends Activity {

    //用户名，密码，再次输入的密码的控件的获取值
    private String userName,psw,pswAgain,Email;
    private RadioGroup Sex;

   /* private final String TAG = getClass().getSimpleName();
    private IntentFilter intentFilter1;
    private IntentFilter intentFilter2;
    private final static String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private final static String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";*/

    private Handler handler;

    //判断输入框内容
    public static Pattern p =
            Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private EditText et_user_name; // 输入用户名
    private EditText et_psw; // 输入密码
    private EditText et_psw_again; // 确认密码
    private EditText email; // 邮箱
    public Button btn_register; // 注册按钮
    private CheckBox checkbox; // 协议同意框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 初始化界面
        setupViews();

        handler = new Handler();

        final ImageView imageview = (ImageView) findViewById(R.id.imgIcon);
        checkbox = (CheckBox) findViewById(R.id.checkbox1);
        // 给协议按钮添加监听
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    btn_register.setVisibility(View.VISIBLE);
                    imageview.setVisibility(View.GONE);
                    Intent intent = new Intent(RegisterActivity.this, AgreementActivity.class);
                    startActivity(intent);
                } else {
                    btn_register.setVisibility(View.INVISIBLE);
                    imageview.setVisibility(View.VISIBLE);
                }
            }

        });
    }
    private void setupViews() {
        //从activity_register.xml 页面中获取对应的UI控件
        btn_register = (Button) findViewById(R.id.btn_register);
        et_user_name= (EditText) findViewById(R.id.et_user_name);
        et_psw= (EditText) findViewById(R.id.et_psw);
        et_psw_again= (EditText) findViewById(R.id.et_psw_again);
        Sex= (RadioGroup) findViewById(R.id.SexRadio);
        email=(EditText)findViewById(R.id.et_eamil);
        //点击事件
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //获取输入在相应控件中的字符串
                    // 获取用户名和用户密码
                    final String username = et_user_name.getText().toString().trim();
                    final String psw = et_psw.getText().toString().trim();
                    final String pswAgain = et_psw_again.getText().toString().trim();
                     /*final String sex = et_psw_again.getText().toString().trim();*/
                    final String Email = email.getText().toString().trim();
                        Sex= (RadioGroup) findViewById(R.id.SexRadio);

                final int sex;
                final int sexChoseId = Sex.getCheckedRadioButtonId();
                switch (sexChoseId) {
                    case R.id.mainRegisterRdBtnFemale:
                        sex = 0;
                        break;
                    case R.id.mainRegisterRdBtnMale:
                        sex = 1;
                        break;
                    default:
                        sex = -1;
                        break;
                }
                    //注册按钮
                switch (v.getId()) {
                    case R.id.btn_register:
                        if (pswAgain.equals(psw)) {
                            new Thread() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    super.run();
                                    Object result;
                                    //调用编写的isEmail()函数
                                    //获取到输入的内容并转换为字符串形式，这样才可以进行匹配，且邮箱格式不能太长，字符串长度31以内
                                    //按照实际需要可以添加不同的事件
                                    if (TextUtils.isEmpty(username)) {
                                        showToast(RegisterActivity.this,"请输入用户名");
                                    }  else if (TextUtils.isEmpty(psw)) {
                                        showToast(RegisterActivity.this,"请输入密码");
                                    }  else if (psw.length() > 16 || psw.length() <5) {
                                        showToast(RegisterActivity.this,"请输入6-15位密码");
                                    }else if (TextUtils.isEmpty(pswAgain)) {
                                        showToast(RegisterActivity.this,"请再次输入密码");
                                    }else if (TextUtils.isEmpty(Email)) {
                                        showToast(RegisterActivity.this,"请输入邮箱");
                                    } else  if (!(isEmail(email.getText().toString().trim()) && email.getText().toString().trim().length()<=31)){
                                        showToast(RegisterActivity.this,"邮箱格式错误");
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
                                            params.put("username", username);
                                            params.put("password", psw);
                                            params.put("sex", (sex == 0 ? "女" : "男"));
                                            params.put("email", Email);

                                            outputStream.write(new JSONObject(params).toString().getBytes()); // POST
                                            connection.connect(); // GET

                                            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                                String inputLine = null;
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                finish();
                                                startActivity(intent);
                                                while ((inputLine = bufferedReader.readLine()) != null) {
                                                    Log.i("result", inputLine);
//                                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                    showToast(RegisterActivity.this,"注册成功");
//                                                        finish();
//                                                        startActivity(intent);

                                                }
//                                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                System.out.println("测试");
//                                                    finish();
//                                                    startActivity(intent);
                                                bufferedReader.close();
                                            }
                                            showToast(RegisterActivity.this,"注册成功");

                                            connection.disconnect();

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        } else {
                            Toast.makeText(RegisterActivity.this, "密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                            et_psw_again.setText(null);
                        }

                }
                }
        });


        et_user_name.setText("");
        et_user_name.requestFocus();
        et_user_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                refreshViews(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }

        });
    }
    //验证函数优化版
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    // 根据edittext的内容来判断是否应当出现“清除所有”的按钮x
    private void refreshViews(CharSequence s) {
        if (s.length() > 0) {
            btn_register.setEnabled(true);
        } else {
            btn_register.setEnabled(false);
        }
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