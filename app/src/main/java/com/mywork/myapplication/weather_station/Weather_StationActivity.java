package com.mywork.myapplication.weather_station;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mywork.myapplication.Fishing_Forest.Fishing_ForestActivity;
import com.mywork.myapplication.R;
import com.mywork.myapplication.common.HeadActivity;
import com.mywork.myapplication.common.book_info;
import com.mywork.myapplication.common.book_list;
import com.mywork.myapplication.common.search;
import com.mywork.myapplication.common.url_imgview;
import com.mywork.myapplication.service.Analysis;
import com.mywork.myapplication.service.HttpRequest;
import com.mywork.myapplication.setting.contact;
import com.mywork.myapplication.setting.createbook;
import com.mywork.myapplication.setting.photo;
import com.mywork.myapplication.setting.setting;

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

/*添加好友*/
public class Weather_StationActivity extends BaseActivity {

    DrawerLayout drawerlayout;
    ImageView imghead1, imghead2, imgMenu;    //声明图片
    TextView personalData;                    //声明个人资料点击
    TextView changeuser;                    //声明改变用户按钮
    final int CODE = 0x22;                    //声明一个返回的结果码
    TextView tvWeather_station;                        //声明进入气象站按钮

    Button settingbutton;
    Button xiugai;
    Button create;
    Button contact;

    //社区界面定义控件按钮并获取
    private ImageView imggood;
    private ImageView imgExpert;
    private ImageView fish;
    String userid = null;

    //气象站信息界面声明
    private List<HashMap<String, Object>> result;                            //声明获取服务的结果值

    //传输的数据
    String WSid = null;
    String air_temper = null;
    String soil_temper = null;
    String air_templa = null;
    String soil_templa = null;
    String oxygen = null;
    String waterLevel = null;
    String light = null;
    String wind_speed = null;
    String wind_direction = null;
    String pressure = null;

    String wsid = null;
    String temp = null;
    //桌面切换
    ViewPager pager = null;
    View framgment1;    //首页
    View data_set_one;        //数据按钮控制页面
    View setup_set_two;        //设置按钮控制页面
    View community_set_three;    //社区按钮控制页面
    ArrayList<View> viewContainter = new ArrayList<View>();

    //定义ViewPager TextView的变量
    private ImageView text0;    //声明底部首页按钮
    private ImageView text1;    //声明底部菜单数据按钮
    private ImageView text2;    //声明底部菜单设置按钮
    private ImageView text3;    //声明底部菜单社区按钮
    private TextView tvtitle;   //声明界面标题按钮


    //	//数据界面按钮获取
    private ImageView img_new;
    //数据控件获取
    private TextView tvwsid;
    private TextView tvairtemper;
    private TextView tvsoiltemper;
    private TextView tvairtempla;
    private TextView tvsoiltempla;
    private TextView tvoxygen;
    private TextView tvwaterlevel;
    private TextView tvlight;
    private TextView tvwind_speed;
    private TextView tvwind_direction;
    private TextView tvpressure;
    private ImageView igTouxiang;
    private String person;
    private Handler handler;
    private static List<HashMap<String,Object>> list_data = new ArrayList<HashMap<String, Object>>();

    /*    public Weather_StationActivity() {*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_station);
        drawerlayout = (DrawerLayout) findViewById(R.id.draw);
        imghead1 = (ImageView) findViewById(R.id.imghead1);        //获取标题图片
//        imghead2 = (ImageView) findViewById(R.id.imgheadce);        //获取侧滑界面头像图标

        //头像统一处理
        List<HashMap<String,Object>> allData = new ArrayList<HashMap<String,Object>>();
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
//        int imghead=bundle.getInt("head");//获取主界面选择头像传过来的id并且将其拿出
        String person = bundle.getString("userkey");
        System.out.println(person+"sdsdfdsgdsgdf");
        try {
            JSONObject json = new JSONObject(person).getJSONObject("user");
            allData = Analysis.AnalysisUserInfo(person);
            userid = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


      /*  final List<HashMap<String, Object>> finalAllData = allData;
        new Thread(){
            public void run(){

                String url =finalAllData.get(0).get("face").toString();
                Log.i("we",url);
                HttpRequest.getImage(url,"GET");
                Message msg = Message.obtain();
                msg.what = 0;
                handler.sendMessage(msg);
            }

        }.start();*/

        ServiceThread serviceThread1;            //开启一个新的线程获取服务
        Thread thread1;                            //新建一个线程

        //数据显示线程
        serviceThread1 = new ServiceThread();
        thread1 = new Thread(serviceThread1);
        thread1.start();
       /* new ServiceThread().start();*/
        //获取个人资料按钮
        personalData = (TextView) findViewById(R.id.personalData);
        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(), PersonalDate.class);
                intent2.putExtras(bundle);
//                intent2.putExtra("head", imageId);
                startActivityForResult(intent2, CODE);


            }
        });

        //获取修改用户按钮
        changeuser = (TextView) findViewById(R.id.changeUser);
        changeuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsernameChange.class);
                startActivityForResult(intent, CODE);

            }
        });

        //获取我的设备按钮

        tvWeather_station = (TextView) findViewById(R.id.tvWeather_station);
        tvWeather_station.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), ScanWeather_Station.class);
                startActivityForResult(intent, CODE);
            }
        });


        //初始化ViewPager的三部分
        InitViewPager();
        //ViewPager绑定页面滑动监听器
        pager.setOnPageChangeListener(new viewPagerOnPageChangeListener());
        //初始化ViewPager的页面切换按钮
        InitViewPagerSwitchTv();
        //定义data_one_page,并获取页面控件
        InitDatePage();
        //定义社区活动页面community_three_page的方法，获取页面控件
        setDataListenerProcess();
        InitSetCommunity();
        setListenerProcess();
        //定义设置页面方法，并给页面控件添加监听处理
        //第三个页面(设置界面）

        //首页（时间因素，静态数据）
        Initmainpage();
        setbook();

//        setListenerProcess();

        //给图片添加监听点击后划出界面
        imghead1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(Gravity.LEFT);
            }
        });

       /* //点击侧滑界面中的按钮缩回侧滑界面
        imghead2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.closeDrawer(Gravity.LEFT);
            }
        });
        //点击侧滑界面中的按钮缩回侧滑界面
        imghead2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.closeDrawer(Gravity.LEFT);
            }
        });*/

        //获取个人资料按钮
        personalData = (TextView) findViewById(R.id.personalData);
        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(), PersonalDate.class);
                intent2.putExtras(bundle);
//					intent2.putExtra("head", imageId);
                startActivityForResult(intent2, CODE);

            }
        });


        // 获取选择的图像
       /* ImageView img = (ImageView) findViewById(R.id.imgheadce);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), HeadActivity.class);
                startActivityForResult(intent, 0x11);
            }
        });
*/
        //获取退出程序退出App
        TextView exit = (TextView) findViewById(R.id.exitLogin);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("提示!").setMessage("确定退出程序?").setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exit();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            }

            //点击按钮执行退出方法
            private void exit() {
                removeALLActivity();        //执行移除所有Activity得方法
                Toast.makeText(getApplicationContext(), "退出程序", Toast.LENGTH_SHORT).show();
            }

        });

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //初始化设置setup_two_page
                InitSetPage();
                //set_two_page社区活动页面监听处理，并给界面获取控件添加监听
                setPageListenerProcess();
            }

        }, 5000);

    }

    //切换桌面的方法
    //初始化ViewPager的页面////////////==========数据、设置、社区页面的切换============/////////
    public void InitViewPager() {
        //实例化ViewPager
        //实例化ViewPager
        pager = (ViewPager) findViewById(R.id.vPager);

        //为viewPager设置内容
        //view是我们放进viewPager里面的东西，要为它设置好布局，再放进去
        framgment1 = LayoutInflater.from(this).inflate(R.layout.framgment1, null);
        data_set_one = LayoutInflater.from(this).inflate(R.layout.data_one, null);
        setup_set_two = LayoutInflater.from(this).inflate(R.layout.setup_two, null);
        community_set_three = LayoutInflater.from(this).inflate(R.layout.community_three, null);

        //这是个ArrayList,加进去了4个view
        viewContainter.add(framgment1);
        viewContainter.add(data_set_one);
        viewContainter.add(setup_set_two);
        viewContainter.add(community_set_three);


        //设置适配器
        pager.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                //return false;
                return arg0 == arg1;
            }

            //viewpager中的组件数量
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewContainter.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                // TODO Auto-generated method stub
                //super.destroyItem(container, position, object);
                ((ViewPager) container).removeView(viewContainter.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                // TODO Auto-generated method stub
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                //return super.instantiateItem(container, position);
                ((ViewPager) container).addView(viewContainter.get(position));
                return viewContainter.get(position);
            }


        });

    }
    //ViewPager页面滑动监听器

    class viewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int pItem) {
            // TODO Auto-generated method stub

            switch (pItem) {
                //如果是第一个页面
                case 0:
                    text0.setImageResource(R.mipmap.mainpage1);
                    text1.setImageResource(R.mipmap.data);
                    text2.setImageResource(R.mipmap.set);
                    text3.setImageResource(R.mipmap.personal01);
                    System.out.println("首页");
                    break;
                //如果是第一个页面
                case 1:
                    text0.setImageResource(R.mipmap.mainpage01);
                    text1.setImageResource(R.mipmap.data1);
                    text2.setImageResource(R.mipmap.set);
                    text3.setImageResource(R.mipmap.personal01);
                    System.out.println("数据");
                    break;
                //如果是第二个页面
                case 2:
                    text0.setImageResource(R.mipmap.mainpage01);
                    text1.setImageResource(R.mipmap.data);
                    text2.setImageResource(R.mipmap.set1);
                    text3.setImageResource(R.mipmap.personal01);
                    System.out.println("设置");
                    break;
                //如果是第三个页面
                case 3:
                    text0.setImageResource(R.mipmap.mainpage01);
                    text1.setImageResource(R.mipmap.data);
                    text2.setImageResource(R.mipmap.set);
                    text3.setImageResource(R.mipmap.personal2);
                    System.out.println("社区");
                    break;
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

    }
    //初始化ViewPager切换按钮

    public void InitViewPagerSwitchTv() {

        //绑定控件ID
        text0 = (ImageView) this.findViewById(R.id.mainpage);
        text1 = (ImageView) this.findViewById(R.id.data);
        text2 = (ImageView) this.findViewById(R.id.set);
        text3 = (ImageView) this.findViewById(R.id.community);
        //设置按钮跳转到对应的viewPager页面
        text0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pager.setCurrentItem(0);

            }
        });
        text1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pager.setCurrentItem(1);

            }
        });

        text2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pager.setCurrentItem(2);
            }
        });

        text3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pager.setCurrentItem(3);
            }
        });

    }

    //---------------页面数据显示-----------------------//

    //获取气象站数据服务
    public class ServiceThread extends Thread {
        @Override
        public void run() {
            //
            // TODO Auto-generated method stub
            super.run();
            String request = "";
            list_data = HttpRequest.resultJson(request, "GET", "dateWS");
            if (result != null) {
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    //首页获取控件的方法
    //data_one_page数据页面获取控件的方法
    public void InitDatePage(){
            img_new = (ImageView) data_set_one.findViewById(R.id.img_with_new);

            imggood = (ImageView) data_set_one.findViewById(R.id.imgGood);
            imgExpert = (ImageView) data_set_one.findViewById(R.id.imgExpert);
            fish = (ImageView) data_set_one.findViewById(R.id.fish);

            tvwsid = (TextView) data_set_one.findViewById(R.id.tvId);
            tvairtemper = (TextView) data_set_one.findViewById(R.id.textair_temper);
            tvsoiltemper = (TextView) data_set_one.findViewById(R.id.textsoil_temper);
            tvairtempla = (TextView) data_set_one.findViewById(R.id.textair_templa);
            tvsoiltempla = (TextView) data_set_one.findViewById(R.id.textsoil_templa);
            tvoxygen = (TextView) data_set_one.findViewById(R.id.textoxygen);
            tvwaterlevel = (TextView) data_set_one.findViewById(R.id.textwaterlevel);
            tvlight = (TextView) data_set_one.findViewById(R.id.textlight);
            tvwind_speed = (TextView) data_set_one.findViewById(R.id.textwind_speed);
            tvwind_direction = (TextView) data_set_one.findViewById(R.id.textwind_direction);
            tvpressure = (TextView) data_set_one.findViewById(R.id.textpressure);

            final String tvairtempers = tvairtemper.getText().toString().trim();
            final String tvsoiltempers = tvsoiltemper.getText().toString().trim();
            final String tvairtemplas = tvairtempla.getText().toString().trim();
            final String tvsoiltemplas = tvsoiltempla.getText().toString().trim();
            final String tvoxygens = tvoxygen.getText().toString().trim();
            final String tvwaterlevels = tvwaterlevel.getText().toString().trim();
            final String tvlights = tvlight.getText().toString().trim();
            final String tvwind_speeds = tvwind_speed.getText().toString().trim();
            final String tvwind_directions = tvwind_direction.getText().toString().trim();
            final String tvpressures = tvpressure.getText().toString().trim();

            System.out.println("显示气象站数据中");
           // getinformation();
            if(list_data!=null) {
                System.out.println("123456");
                if ("false".equals(list_data)) {
                    Toast.makeText(getApplicationContext(), "获取实时信息失败，请确定气象站设备已连接。。。", 0).show();
                } else {
                    try {

                        tvwaterlevel.setText(list_data.get(0).get("waterLevel").toString());
                        tvlight.setText(list_data.get(0).get("light").toString());
                        tvwind_speed.setText(list_data.get(0).get("wind_sp").toString());
                        tvwind_direction.setText(list_data.get(0).get("wind_dir").toString());

                        tvoxygen.setText(list_data.get(0).get("oxygen").toString());
                        tvairtemper.setText(list_data.get(0).get("air_temper").toString());
                        tvairtempla.setText(list_data.get(0).get("air_templa").toString());
                        tvsoiltemper.setText(list_data.get(0).get("soil_temper").toString());
                        tvsoiltempla.setText(list_data.get(0).get("soil_templa").toString());
                        tvpressure.setText(list_data.get(0).get("pressure").toString());
                    } catch (Exception e) {

                    }

                }
        };
    }
        //data_one_page数据页面控件的监听绑定
        public void setDataListenerProcess() {
            imggood.setOnClickListener(clickListener1);
            imgExpert.setOnClickListener(clickListener1);
            fish.setOnClickListener(clickListener1);

            img_new.setOnClickListener(clickListener1);            //获取刷新数据按钮
            if (list_data == null) {
                System.out.println("服务器数据为空");
            }
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    tvwaterlevel.setText(list_data.get(0).get("waterLevel").toString());
                    tvlight.setText(list_data.get(0).get("light").toString());
                    tvwind_speed.setText(list_data.get(0).get("wind_sp").toString());
                    tvwind_direction.setText(list_data.get(0).get("wind_dir").toString());

                    tvoxygen.setText(list_data.get(0).get("oxygen").toString());
                    tvairtemper.setText(list_data.get(0).get("air_temper").toString());
                    tvairtempla.setText(list_data.get(0).get("air_templa").toString());
                    tvsoiltemper.setText(list_data.get(0).get("soil_temper").toString());
                    tvsoiltempla.setText(list_data.get(0).get("soil_templa").toString());
                    tvpressure.setText(list_data.get(0).get("pressure").toString());
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
                            Log.i("test", "run");
                            String request = "";

                            list_data = HttpRequest.resultJson(request, "GET", "dateWS");
                             System.out.println(list_data.get(0).get("oxygen").toString());
                            Message msg = Message.obtain();
                            msg.what = 0;
                            handler.sendMessage(msg);

                            if (result != null) {
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();

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
                } else if(v.getId() == R.id.fish){      //进入渔林按钮
                    Intent intent = new Intent(getApplicationContext(), Fishing_ForestActivity.class);
                    startActivityForResult(intent, CODE);
                }
                else {
                }
            }
        };

        //set_two_page设置活动界面

        class SwitchServiceThread implements Runnable {
            @Override
            public void run() {
            /*resultSwitch = FishSwitchsetService.getFishSwitchSetByClientPost(WSid);
            Log.i("switch", fishid);
            Log.i("msg",resultSwitch );*/
            }
        }

        public void getFishSwitchSet() {
            Log.i("msg", "=========");

        }
        // 初始化set two page并且获取ViewPager中xml的子控件

        public void InitSetPage() {
       /* //获取setting view Switch
        changeWater = (Switch)setup_set_two.findViewById(R.id.switch1_changeWater);
        addWater = (Switch)setup_set_two.findViewById(R.id.switch2_addWater);
        outWater = (Switch)setup_set_two.findViewById(R.id.switch3_outWater);
        feeding = (Switch)setup_set_two.findViewById(R.id.switch4_feeding);
        Turnon_light = (Switch)setup_set_two.findViewById(R.id.switch5_Turnon_light);
        addWarming = (Switch)setup_set_two.findViewById(R.id.switch6_addWarming);
        Cool = (Switch)setup_set_two.findViewById(R.id.switch7_Cool);
        addOxygen = (Switch)setup_set_two.findViewById(R.id.switch8_addOxygen);*/

            getFishSwitchSet();
            System.out.println("开关设置");
        }

        /*
         * set two page监听器绑定 ////==============开关控制===============///
         */
        public void setPageListenerProcess() {

             /*//开关
        changeWater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

   //开关
        addWater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        });*/

        }
        //灯光开关

        //社区活动页面
        public void InitSetCommunity() {
            /* View view= inflater.inflate(R.layout.fragment4, container, false);*/
            settingbutton = (Button) community_set_three.findViewById(R.id.setting);
            xiugai = (Button) community_set_three.findViewById(R.id.xiugai);
            create = (Button) community_set_three.findViewById(R.id.create);
            contact = (Button) community_set_three.findViewById(R.id.button5);
//        setListenerProcess();
        }

        public void setListenerProcess() {
            settingbutton.setOnClickListener(clickListener3);
            xiugai.setOnClickListener(clickListener3);
            create.setOnClickListener(clickListener3);
            contact.setOnClickListener(clickListener3);
        }

        View.OnClickListener clickListener3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.setting) {
                    Intent intent = new Intent(getApplicationContext(), setting.class);
                    startActivity(intent);
                } else if (view.getId() == R.id.xiugai) {
                    Intent intent = new Intent(getApplicationContext(), photo.class);
                    startActivity(intent);
                } else if (view.getId() == R.id.create) {
                    Intent intent = new Intent(getApplicationContext(), createbook.class);
                    startActivity(intent);
                } else if(view.getId() == R.id.button5) {
                    Intent intent = new Intent(getApplicationContext(), com.mywork.myapplication.setting.contact.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
        //判断HeadActivity界面的头像是否被选中，如果选中了就返回
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);    //获取请求码
            if (requestCode == 0x11 && resultCode == 0x11) {
                Bundle bundle3 = data.getExtras();        //判断是否为待处理的结果
                int imageId = bundle3.getInt("imageId");    //获取选择的头像id
                //获取布局文件中添加的ImageView的组件
//                ImageView tv = (ImageView) findViewById(R.id.imgheadce);

//                tv.setImageResource(imageId);//显示选择的头像

            }
        }

        //首页事件监听
        public void Initmainpage(){
            url_imgview article_img1=(url_imgview)framgment1. findViewById(R.id.article_img1);
            url_imgview article_img2=(url_imgview) framgment1.findViewById(R.id.article_img2);
            TextView article_name1=(TextView)framgment1. findViewById(R.id.article_name1);
            TextView article_name2=(TextView) framgment1.findViewById(R.id.article_name2);
            TextView article_author1=(TextView)framgment1.findViewById(R.id.article_author1);
            TextView article_author2=(TextView) framgment1.findViewById(R.id.article_author2);
            TextView article_hot1=(TextView)framgment1. findViewById(R.id.article_hot1);
            TextView article_hot2=(TextView) framgment1.findViewById(R.id.article_hot2);

            article_img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),book_info.class);
                   /* intent.putExtra("book_id",article_id.get(0));
                    intent.putExtra("flag",2);*/
                    startActivity(intent);
                }
            });
            article_img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), book_info.class);
                    startActivity(intent);
                }
            });

        }
    private void setbook() {
        url_imgview book_img1 = (url_imgview) framgment1.findViewById(R.id.book_img1);
        url_imgview book_img2 = (url_imgview) framgment1.findViewById(R.id.book_img2);
        url_imgview book_img3 = (url_imgview) framgment1.findViewById(R.id.book_img3);
        TextView book_name1 = (TextView) framgment1.findViewById(R.id.book_name1);
        TextView book_name2 = (TextView) framgment1.findViewById(R.id.book_name2);
        TextView book_name3 = (TextView) framgment1.findViewById(R.id.book_name3);
        book_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),book_info.class);
                startActivity(intent);
            }
        });
        book_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),book_info.class);
                startActivity(intent);
            }
        });
        book_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),book_info.class);
                startActivity(intent);
            }
        });

    }
   /* private void setclick() {
       *//* ImageView fish=(ImageView) framgment1.findViewById(R.id.btnfish);
        ImageView ws=(ImageView) framgment1.findViewById(R.id.btnws);*//*

        EditText editText=(EditText) framgment1.findViewById(R.id.Search);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), search.class);
                startActivity(intent);
            }
        });
        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Fishing_ForestActivity.class);
                startActivity(intent);
            }
        });
        ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Weather_StationActivity.class);
                startActivity(intent);
            }
        });
    }*/

}
