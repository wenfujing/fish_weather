package com.mywork.myapplication.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.mywork.myapplication.R;
import com.mywork.myapplication.weather_station.BaseActivity;

public class HeadActivity extends BaseActivity {
	
	public static int[] imageId=new int[] {R.mipmap.tou01,R.mipmap.tou2,
			R.mipmap.tou3,R.mipmap.tou4,R.mipmap.tou6,R.mipmap.tou7,
			R.mipmap.tou8,R.mipmap.tou9,R.mipmap.tou10,R.mipmap.tou11,
			R.mipmap.tou12};
	private int index=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.head);
	GridView gridview=(GridView)findViewById(R.id.gridview1);
	BaseAdapter adapter=new BaseAdapter() {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView imageview;
			if(convertView==null) {
				imageview=new ImageView(HeadActivity.this);
				/*****����ͼ��Ŀ�Ⱥ͸߶�*********/
				imageview.setAdjustViewBounds(true);
				imageview.setMaxWidth(158);
				imageview.setMaxHeight(150);
				//����ImageView���ڱ߾�
				imageview.setPadding(5, 5, 5, 5);
			}else {
				imageview=(ImageView) convertView;
			}
			imageview.setImageResource(imageId[position]);//ΪImageView����Ҫ��ʾ��ͼƬ

			return imageview;	//����ImageView
		}
		//��õ�ǰѡ���id
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		//��õ�ǰѡ��
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		//�������
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageId.length;
		}
	};
	gridview.setAdapter(adapter);
	gridview.setOnItemClickListener(new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Intent intent=getIntent();	//��ȡIntent����
			Bundle bundle=new Bundle();	//ʵ�������ݵ����ݰ�
			bundle.putInt("imageId",imageId[position]);
			intent.putExtras(bundle);   //�����ݰ����浽intent��
			setResult(0x11,intent);		//���÷��صĽ���룬�����ص��ø�Activity��Activity
			finish();		//�رյ�ǰ��Activity
		}
	});
}
	
}
