package com.mywork.myapplication.weather_station;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mywork.myapplication.R;
import com.mywork.myapplication.common.Uploadfile;
import com.mywork.myapplication.common.url_imgview;
import com.mywork.myapplication.login.LoginActivity;
import com.mywork.myapplication.login.RegisterActivity;
import com.mywork.myapplication.service.Analysis;
import com.mywork.myapplication.service.HttpRequest;
import com.mywork.myapplication.service.PersonalDateService;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalDate extends BaseActivity{
	private ImageView imageView;
	private ImageView imageView1;
	private ImageView imghead;
	private Button btnsave;
	private EditText Sex;				//声明性别显示框
	private EditText Username;			//声明用户名显示框
	private EditText Name;			//声明用户名显示框
	private EditText userpassword;		//声明用户密码显示框
	private EditText E_mail;			//声明E_mail显示框
	private TextView Identify;			//声明用户地址
	private TextView userid;			//获取用户id
	private ImageView Face;			//获取用户id

	ArrayList<String> image=new ArrayList<String>();
	private String[] sexArry= new String[] {"男","女"};		//性别选择
	private static List<HashMap<String,Object>> list_data = new ArrayList<HashMap<String, Object>>();
	String f_id ;
	String f_username ;
	String f_name ;
	String f_sex ;
	String f_email ;
	String f_identifys ;
	private Handler handler;
	private Weather_StationActivity.ServiceThread serviceThread3;            //开启一个新的线程获取服务
	private Thread thread3;                            //新建一个线程
	private String person;
	private AdapterView freetime;

	//传输的数据
	@SuppressLint("HandlerLeak")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personaldate);
		save();

		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		int imghead=bundle.getInt("head");//获取主界面选择头像传过来的id并且将其拿出
		person = bundle.getString("userkey");
		System.out.println(person+"sdsdfdsgdsgdf");
//		Toast.makeText(PersonalDate.this, id, Toast.LENGTH_LONG).show();
		init();
//		imgTitle.setImageResource(imghead);  			//显示用户资料中用户头像
		// 为返回按钮设置监听
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(0x22, intent);
				finish();
			}
		});


		final Handler myhandler = new Handler() {
			public void handleMessage(Message msg) {

				String text = (String) msg.obj;

				if (text!=null) {

					Log.i("click", "不为空了");
					Toast.makeText(getApplicationContext(), "发布成功！！！", Toast.LENGTH_SHORT).show();
				} else {

					Toast.makeText(getApplicationContext(), "发布失败！！！",
							Toast.LENGTH_LONG).show();

				}
			};

		};


//头像
		imageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {

// TODO Auto-generated method stub
						Uploadfile up=new Uploadfile();

						try {
							up.fileIsExists("/storage/emulated/0/DCIM/Camera/IMG_20190628_024148.jpg");
//							String path = getrubbishIfo();
							String restr=up.uploadFile();//HttpPath.FABU_PATH为路径，将封装好的数据path绑到路径后传递给服务器，picpath为图片路径
                            System.out.println(restr);
							Message message = myhandler.obtainMessage();
							message.obj = restr;

							myhandler.sendMessage(message);
						} catch (Exception e) {

// TODO Auto-generated catch block
							e.printStackTrace();

						}

					}
				}).start();
			}
		});

		//点击输入e_mail
		E_mail.setOnKeyListener(new EditText.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				//判断输入的类型是哪一种，并与系统连接
				Linkify.addLinks(E_mail, Linkify.EMAIL_ADDRESSES);
				return false;
			}

		});
	}

	private String getrubbishIfo() throws IOException{
		final List<NameValuePair> Parameters = new ArrayList<NameValuePair>();
		/*Parameters.add(new BasicNameValuePair("runame", thingname.getText().toString().trim()));
		Parameters.add(new BasicNameValuePair("ruleibie", fenlei.getSelectedItem().toString()));
		Parameters.add(new BasicNameValuePair("rubeizhu", beizhu.getText().toString().trim()));*/
//		Parameters.add(new BasicNameValuePair("rufreetime", freetime.getSelectedItem().toString()));
		String userphone="123";
//		Parameters.add(new BasicNameValuePair("userphone", userphone));
//		Parameters.add(new BasicNameValuePair("address", address.getText().toString().trim()));
		String username="wenfujin";
//		Parameters.add(new BasicNameValuePair("username", username));
//		Parameters.add(new BasicNameValuePair("rudate", ""));

		System.out.println("Parameters:" + Parameters);
		String path= URLEncodedUtils.format(Parameters,"UTF-8");

		System.out.println(path+"0000000000000000000");
		return path;


	}


	//个人信息界面初始化
		@SuppressLint("WrongViewCast")
		public void init() {
		imageView = (ImageView) findViewById(R.id.imgLeft);				//获取返回
		btnsave =(Button) findViewById(R.id.btnSave);					//获取保存用户信息
		Sex =(EditText) findViewById(R.id.etSex);						//获取用户性别
		Username =(EditText) findViewById(R.id.etUsername);				//获取用户名
		Name =(EditText) findViewById(R.id.name);				//获取姓名
		/*userpassword =(EditText) findViewById(R.id.etUserpass);			//获取密码*/
		E_mail=(EditText) findViewById(R.id.E_mail);					//获取e_mail
		Identify = (TextView) findViewById(R.id.identifys);				//获取地址*/
		 userid = (TextView) findViewById(R.id.Userid);					//获取用户id
//			Face = (ImageView) findViewById(R.id.imgTitle);					//获取用户id
		/*imgTitle=(ImageView) findViewById(R.id.imgTitle);*/


		List<HashMap<String,Object>> allData = new ArrayList<HashMap<String,Object>>();
		try {
			allData = Analysis.AnalysisUserInfo(person);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Username.setText(allData.get(0).get("username").toString());
		Sex.setText(allData.get(0).get("sex").toString());
		E_mail.setText(allData.get(0).get("email").toString());
		Identify.setText(allData.get(0).get("identity").toString());
		Name.setText(allData.get(0).get("name").toString());
		userid.setText(allData.get(0).get("id").toString());
//		/*imgTitle.setImageURI(Uri.parse(allData.get(0).get("face").toString()));*/

//		imgTitle.setText(allData.get(0).get("face").toString());//获取图片字符串
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if(HttpRequest.bitmap != null){
						Face = (ImageView) findViewById(R.id.imgTitle);
						Face.setImageBitmap(HttpRequest.bitmap);
					}
					super.handleMessage(msg);
				}

			};

			final List<HashMap<String, Object>> finalAllData = allData;
			new Thread(){
				public void run(){

					String url =finalAllData.get(0).get("face").toString();
					Log.i("we",url);
					HttpRequest.getImage(url,"GET");
					Message msg = Message.obtain();
					msg.what = 0;
					handler.sendMessage(msg);
				}

			}.start();
		}

	//点击性别选择
	public void etSex(View sex){
		AlertDialog.Builder builderSex= new AlertDialog.Builder(this);	//自定义对话框
		builderSex.setTitle("请选择性别");
		builderSex.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {	//whitch是被选中的性别位置
				Sex.setText(sexArry[which]);
				Sex.setSelection(Sex.getText().length());

				dialog.dismiss(); 		//对边点击一个item显示对话框，不用点击确认消息
			}
		});
		builderSex.show();	//显示性别选择弹框

	}

	//验证是否是电话号的
	public static boolean isMobile(String str) {
	    Pattern p = null;
	    Matcher m = null;
	    boolean b = false;
	    p = Pattern.compile("^[1][3,5,8][0-9]{9}$"); // 验证手机号
	    m = p.matcher(str);
	    b = m.matches();
	    return b;
	}

	//验证是否是邮箱验证
	public static boolean isEmail(String strEmail) {
		String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		if(TextUtils.isEmpty(strPattern)) {
			return false;
		}else {
			return strEmail.matches(strPattern);
		}

	}
	public void etUserpass(View v) {
		String password=userpassword.getText().toString().trim();
		if(TextUtils.isEmpty(password)) {
			Toast.makeText(this, "密码不能为空！！！", 0).show();
		}
	}

	//点击输入E_mail
	public void E_mail(View v) {
		String email =E_mail.getText().toString();
		boolean emailNB = isEmail(email);
		if(emailNB==true) {
			Toast.makeText(this, "输入正确", Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(this, "请重新输入", Toast.LENGTH_SHORT).show();
		}
	}

	//点击保存个人信息
	public void save() {
		imageView = (ImageView) findViewById(R.id.imgLeft);				//获取返回
		imageView1 = (ImageView) findViewById(R.id.imgTitle);				//获取返回
		btnsave =(Button) findViewById(R.id.btnSave);					//获取保存用户信息
		Sex =(EditText) findViewById(R.id.etSex);						//获取用户性别
		Username =(EditText) findViewById(R.id.etUsername);				//获取用户名
		Name =(EditText) findViewById(R.id.name);				//获取姓名
		/*userpassword =(EditText) findViewById(R.id.etUserpass);			//获取密码*/
		E_mail=(EditText) findViewById(R.id.E_mail);					//获取e_mail
		Identify = (TextView) findViewById(R.id.identifys);				//获取地址*/
		userid = (TextView) findViewById(R.id.Userid);					//获取用户id
		btnsave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//获取信息框内输入的内容
				final String id = userid.getText().toString().trim();
				final String sex = Sex.getText().toString().trim();
				final String username = Username.getText().toString().trim();
				final String name = Name.getText().toString().trim();
				/*final String password = userpassword.getText().toString().trim();*/
				final String e_mail = E_mail.getText().toString().trim();
				final String identity = Identify.getText().toString().trim();
				/*final String address =Address.getText().toString().trim();*/
				if("".equals(name)||"".equals(e_mail)){
					Toast.makeText(getApplicationContext(), "真实姓名、邮箱不能为空，请重新输入！", Toast.LENGTH_SHORT).show();
				}else{
					new Thread() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							super.run();
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
								params.put("name", name);
								params.put("identity", identity);
								params.put("id", id);
								params.put("sex", sex);
								params.put("email", e_mail);
								params.put("email", e_mail);

								outputStream.write(new JSONObject(params).toString().getBytes()); // POST
								connection.connect(); // GET

								if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
									BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
									String inputLine = null;
									while ((inputLine = bufferedReader.readLine()) != null) {
										Intent intent = new Intent(PersonalDate.this, LoginActivity.class);
										showToast(PersonalDate.this, "请求成功");
										startActivity(intent);
										finish();
									}

									bufferedReader.close();
								}else {
								}
								connection.disconnect();

							} catch (IOException e) {
								e.printStackTrace();
								showToast(PersonalDate.this, "网络连接错误");
							}

						}
					}.start();
				}
			}
		});

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
