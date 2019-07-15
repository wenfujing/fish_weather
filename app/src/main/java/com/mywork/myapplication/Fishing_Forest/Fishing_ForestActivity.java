package com.mywork.myapplication.Fishing_Forest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mywork.myapplication.R;
import com.mywork.myapplication.service.HttpRequest;
import com.mywork.myapplication.weather_station.BaseActivity;
import com.mywork.myapplication.weather_station.ExpertSystem;
import com.mywork.myapplication.weather_station.Weather_StationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Fishing_ForestActivity extends BaseActivity {
    String userid = null;
    final int CODE = 0x23;                    //声明一个返回的结果码
    //气象站信息界面声明
    private List<HashMap<String, Object>> result;                            //声明获取服务的结果值
    private ServiceThread serviceThread1;            //开启一个新的线程获取服务
    private Thread thread;                            //新建一个线程
    //	//数据界面按钮获取
    private ImageView img_new;
    private ImageView imggood;
    private ImageView imgExpert;
    private ImageView weather;
    //数据控件获取
    private TextView tvwsid;
    private TextView tvwater_tem;
    private TextView tvmuddy;
    private TextView tvair_temper;
    private TextView tvair_templa;
    private TextView tvsoil_temper;
    private TextView tvsoil_templa;
    private TextView tvco2;
    private TextView tvo2;
    private TextView tvelectrical;
    private TextView tvsalinity;
    private TextView tvsmoke;

    private Handler handler;
    private static List<HashMap<String,Object>> list_data = new ArrayList<HashMap<String, Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishing_forest);


       /* Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        String person = bundle.getString("userkey");
        System.out.println(person+"sdsdfdsgdsgdf");
        try {
            JSONObject json = new JSONObject(person).getJSONObject("user");
            userid = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

        //数据显示线程
        serviceThread1 = new ServiceThread();
        thread = new Thread(serviceThread1);
        thread.start();
        //初始化界面
        InitDatePage();
        setDataListenerProcess();
    }

    //---------------页面数据显示-----------------------//

    //获取气象站数据服务
    class ServiceThread extends Thread {
        @Override
        public void run() {
            //
            // TODO Auto-generated method stub
            super.run();
            String request = "";
            list_data = HttpRequest.resultJson(request, "GET", "dateWS");
            /*Message msg = Message.obtain();
            msg.what = 0;
            handler.sendMessage(msg);*/

            if (result != null) {
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Fishing_ForestActivity.this, "请求成功", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }

    }

    public void InitDatePage() {
        img_new = (ImageView) findViewById(R.id.img_with_new);

        imggood = (ImageView) findViewById(R.id.imgGood);
        imgExpert = (ImageView) findViewById(R.id.imgExpert);
        weather = (ImageView) findViewById(R.id.weather);

       /* tvffid = (TextView) findViewById(R.id.tvId);*/
        //鱼塘
        tvwater_tem = (TextView) findViewById(R.id.tvwater_tem);//水温
        tvmuddy = (TextView) findViewById(R.id.tvmuddy);//浑浊度
        //园林
        tvair_temper = (TextView) findViewById(R.id.tvair_temper);//空气温度
        tvair_templa = (TextView) findViewById(R.id.tvair_templa);//空气湿度
        tvsoil_temper = (TextView) findViewById(R.id.tvsoil_temper);//土壤温度
        tvsoil_templa = (TextView) findViewById(R.id.tvsoil_templa);//土壤湿度
        tvco2 = (TextView) findViewById(R.id.tvco2);
        tvo2 = (TextView) findViewById(R.id.tvo2);
        tvelectrical = (TextView) findViewById(R.id.tvelectrical);//电导率;
        tvsalinity = (TextView) findViewById(R.id.tvsalinity);//盐度
        tvsmoke = (TextView) findViewById(R.id.smoke);//烟度等级

        /*final String tvairtempers = tvairtemper.getText().toString().trim();
        final String tvsoiltempers = tvsoiltemper.getText().toString().trim();
        final String tvairtemplas = tvairtempla.getText().toString().trim();
        final String tvsoiltemplas = tvsoiltempla.getText().toString().trim();
        final String tvoxygens = tvoxygen.getText().toString().trim();
        final String tvwaterlevels = tvwaterlevel.getText().toString().trim();
        final String tvlights = tvlight.getText().toString().trim();
        final String tvwind_speeds = tvwind_speed.getText().toString().trim();
        final String tvwind_directions = tvwind_direction.getText().toString().trim();
        final String tvpressures = tvpressure.getText().toString().trim();*/

        System.out.println("显示气象站数据中");
        // getinformation();
        if (list_data != null) {
            System.out.println("123456");
            if ("false".equals(list_data)) {
                Toast.makeText(Fishing_ForestActivity.this, "获取实时信息失败，请确定气象站设备已连接。。。", 0).show();
            } else {
                try {

                    tvwater_tem.setText(list_data.get(0).get("water_tem").toString());//空气温度
                    tvmuddy.setText(list_data.get(0).get("turbidity").toString());//浑浊
                    //园林
                    //  for_soil
                    tvsoil_temper.setText(list_data.get(0).get("soil_hum").toString());//土壤温度
                    tvsoil_templa.setText(list_data.get(0).get("soil_tem").toString());//土壤湿度
                    tvelectrical.setText(list_data.get(0).get("conductivity").toString());//电导率
                    tvsalinity.setText(list_data.get(0).get("soil_nity").toString());//盐度

                    tvair_temper.setText(list_data.get(0).get("atm_tem").toString());//空气温度
                    tvair_templa.setText(list_data.get(0).get("atm_humidity").toString());//空气湿度
                    tvo2.setText(list_data.get(0).get("oxygen").toString());//o2

                    tvco2.setText(list_data.get(0).get("co2").toString());//co2

                    tvsmoke.setText(list_data.get(0).get("smoke").toString());//盐度
                } catch (Exception e) {

                }

            }
        }
        ;
    }
    public void setDataListenerProcess() {
        imggood.setOnClickListener(clickListener1);
        imgExpert.setOnClickListener(clickListener1);
        weather.setOnClickListener(clickListener1);

        img_new.setOnClickListener(clickListener1);            //获取刷新数据按钮
        if (list_data == null) {
            System.out.println("111111111111111111111111");
        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //渔林
                tvwater_tem.setText(list_data.get(0).get("water_tem").toString());//空气温度
                tvmuddy.setText(list_data.get(0).get("turbidity").toString());//浑浊
                //园林
                //  for_soil
                tvsoil_temper.setText(list_data.get(0).get("soil_hum").toString());//土壤温度
                tvsoil_templa.setText(list_data.get(0).get("soil_tem").toString());//土壤湿度
                tvelectrical.setText(list_data.get(0).get("conductivity").toString());//电导率
                tvsalinity.setText(list_data.get(0).get("soil_nity").toString());//盐度

                tvair_temper.setText(list_data.get(0).get("atm_tem").toString());//空气温度
                tvair_templa.setText(list_data.get(0).get("atm_humidity").toString());//空气湿度
                tvo2.setText(list_data.get(0).get("oxygen").toString());//o2

                tvco2.setText(list_data.get(0).get("co2").toString());//co2

                tvsmoke.setText(list_data.get(0).get("smoke").toString());//盐度
                super.handleMessage(msg);
            }
        };
    }

    View.OnClickListener clickListener1 = new View.OnClickListener() {    //设置点击刷新按钮监听
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.img_with_new) {
                new Thread() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        super.run();

                        String request = "";

                        list_data = HttpRequest.resultJson(request, "GET", "dateWS");
//                        System.out.println(list_data.get(0).get("waterLevel").toString());
                        Message msg = Message.obtain();
                        msg.what = 0;
                        handler.sendMessage(msg);
                        Log.i("reg",msg.toString());
                        if (result != null) {
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Fishing_ForestActivity.this, "请求成功", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                }.start();
            }if (v.getId() == R.id.imgGood) {
                Intent intent = new Intent(getApplicationContext(), ExpertSystem.class);
                startActivityForResult(intent, CODE);
            } else if (v.getId() == R.id.imgExpert) {
                Intent intent = new Intent(getApplicationContext(), ExpertSystem.class);
                startActivityForResult(intent, CODE);
            } else if(v.getId() == R.id.weather){      //数据图
                Intent intent = new Intent(getApplicationContext(), Weather_StationActivity.class);
                startActivityForResult(intent, CODE);
            }
            else {
                Toast.makeText(Fishing_ForestActivity.this, "连接网络中", Toast.LENGTH_SHORT).show();
            }
        }
    };


}