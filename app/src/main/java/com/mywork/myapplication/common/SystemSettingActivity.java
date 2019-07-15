package com.mywork.myapplication.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mywork.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemSettingActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemsetting);

        final ListView listView=(ListView)findViewById(R.id.listView1);//获取列表视图

        listView.addHeaderView(line());		//设置header view

        /****************创建用于为ListView指定列表项的适配器********************/
//		方法一
//		String[] ctype=new String[]{"情景模式","主题模式","手机","程序管理"};
//		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,ctype);
//		方法二
        int[] imageld = new int[]{R.mipmap.img01,R.mipmap.img01,R.mipmap.img01,R.mipmap.img01,R.mipmap.img01};
        String[] ctype = new String[]{"情景模式","主题模式","程序管理","关于应用","聊天设置"};
        List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
        for (int i = 0;i<imageld.length;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("image",imageld[i]);
            map.put("ctype",ctype[i]);
            listItems.add(map);
        }
        SimpleAdapter  adapter =new SimpleAdapter(this,listItems,R.layout.activity_systemsetting,new String[]{"ctype","image"},new int[]{
                R.id.title03,R.id.image
        });
        	//创建一个适配器

        /***************************************************************************/



        listView.setAdapter(adapter); // 将适配器与ListView关联
        listView.addFooterView(line());		//设置footer view
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                String result = parent.getItemAtPosition(pos).toString(); // 获取选择项的值
                Toast.makeText(SystemSettingActivity.this, result, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private View line() {
        ImageView image=new ImageView(this);	//创建一个图像视图
        image.setImageResource(R.mipmap.line1);	//设置要显示的图片
        return image;
    }
}