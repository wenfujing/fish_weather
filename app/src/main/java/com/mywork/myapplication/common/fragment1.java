package com.mywork.myapplication.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mywork.myapplication.Fishing_Forest.Fishing_ForestActivity;
import com.mywork.myapplication.R;
import com.mywork.myapplication.service.WSInformationService;
import com.mywork.myapplication.weather_station.Weather_StationActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class fragment1 extends Fragment {
   /* LoopPager mLoopPager;*/
    List<String> picture_urls = new ArrayList<String>();
    ArrayList<String> book_name=new ArrayList<String>();
    ArrayList<String> book_img=new ArrayList<String>();
    ArrayList<Integer> book_id=new ArrayList<Integer>();
    ArrayList<String> article_author=new ArrayList<String>();
    ArrayList<String> article_name=new ArrayList<String>();
    ArrayList<Integer> article_hot=new ArrayList<Integer>();
    ArrayList<String> article_img=new ArrayList<String>();
    ArrayList<Integer> article_id=new ArrayList<Integer>();
    Handler handler=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_item_zero, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler=new Handler();
        final String url="http://10.168.14.55:8080/meteorological/sp";
        new Thread(){
            @Override
            public void run() {
                String result= WSInformationService.gethttpresult(url);
                System.out.println(result);
                if(result==null)
                {
                    Looper.prepare();
                    Toast.makeText(getContext(),"网络连接错误！",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                else
                {
                    try {
                        JSONObject result_json=new JSONObject(result);
                        JSONArray picture=result_json.getJSONArray("picture");
                        JSONArray booklist=result_json.getJSONArray( "Booklist");
                        JSONArray articlelist=result_json.getJSONArray("Articlelist");
                        for (int i = 0; i < picture.length(); i++) {
                            JSONObject object=picture.getJSONObject(i);
                            picture_urls.add(object.getString("url"));
                        }
                        for (int i = 0; i < 3; i++) {
                            JSONObject object=booklist.getJSONObject(i);
                            book_id.add(object.getInt("bookId"));
                            book_name.add(object.getString("bookName"));
                            book_img.add(object.getString("bookPicture"));
                        }
                        for (int i = 0; i < 2; i++) {
                            JSONObject object=articlelist.getJSONObject(i);
                            System.out.println(object.getString("articleContext"));
                            article_id.add(object.getInt("articleId"));
                            article_name.add(object.getString("articleName"));
                            article_img.add(object.getString("articlePicture"));
                            article_author.add(object.getString("articleAuthor"));
                            article_hot.add(object.getInt("articleHot"));
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                /*setimg();*/
                                setclick();
                                setbook();
                                setarticle();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                        Looper.prepare();
                        Toast.makeText(getContext(),"文件解析错误！",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }

            }
        }.start();

    }

    private void setarticle() {
        url_imgview article_img1=(url_imgview) getActivity().findViewById(R.id.article_img1);
        url_imgview article_img2=(url_imgview) getActivity().findViewById(R.id.article_img2);
        TextView article_name1=(TextView) getActivity().findViewById(R.id.article_name1);
        TextView article_name2=(TextView) getActivity().findViewById(R.id.article_name2);
        TextView article_author1=(TextView) getActivity().findViewById(R.id.article_author1);
        TextView article_author2=(TextView) getActivity().findViewById(R.id.article_author2);
        TextView article_hot1=(TextView) getActivity().findViewById(R.id.article_hot1);
        TextView article_hot2=(TextView) getActivity().findViewById(R.id.article_hot2);
        article_img1.geturlimg(article_img.get(0));
        article_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),book_info.class);
                intent.putExtra("book_id",article_id.get(0));
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });
        article_name1.setText(article_name.get(0));
        article_author1.setText(article_author.get(0));
        article_hot1.setText(String.valueOf(article_hot.get(0)));
        article_img2.geturlimg(article_img.get(1));
        article_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),book_info.class);
                intent.putExtra("book_id",article_id.get(1));
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });
        article_name2.setText(article_name.get(1));
        article_author2.setText(article_author.get(1));
        article_hot2.setText(String.valueOf(article_hot.get(1)));

    }

    private void setbook() {
        url_imgview book_img1=(url_imgview) getActivity().findViewById(R.id.book_img1);
        url_imgview book_img2=(url_imgview) getActivity().findViewById(R.id.book_img2);
        url_imgview book_img3=(url_imgview) getActivity().findViewById(R.id.book_img3);
        TextView book_name1=(TextView) getActivity().findViewById(R.id.book_name1);
        TextView book_name2=(TextView) getActivity().findViewById(R.id.book_name2);
        TextView book_name3=(TextView) getActivity().findViewById(R.id.book_name3);
        book_img1.geturlimg(book_img.get(0));
        book_img2.geturlimg(book_img.get(1));
        book_img3.geturlimg(book_img.get(2));
        book_name1.setText(book_name.get(0));
        book_name2.setText(book_name.get(1));
        book_name3.setText(book_name.get(2));
        book_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),book_info.class);
                intent.putExtra("book_id",book_id.get(0));
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });
        book_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),book_info.class);
                intent.putExtra("book_id",book_id.get(1));
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });
        book_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),book_info.class);
                intent.putExtra("book_id",book_id.get(2));
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });
    }

    private void setclick() {
        ImageView xinshuzuire=(ImageView) getActivity().findViewById(R.id.xinshuzuize);
        ImageView shujizaidu=(ImageView) getActivity().findViewById(R.id.shujizaidu);
        ImageView wenzhangredu=(ImageView) getActivity().findViewById(R.id.wenzhangrudu);
        EditText editText=(EditText) getActivity().findViewById(R.id.Search);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),search.class);
                startActivity(intent);
            }
        });
        xinshuzuire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://122.114.237.201/getbook/newbooks";
                Intent intent=new Intent(getContext(), Weather_StationActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("flag",1);
                intent.putExtra("type","新书最热");
                startActivity(intent);
            }
        });
        shujizaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://122.114.237.201/getbook/hotbooks";
                Intent intent=new Intent(getContext(), Fishing_ForestActivity.class);
                intent.putExtra("type","书籍在读榜");
                intent.putExtra("flag",1);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        wenzhangredu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://122.114.237.201/getbook/hotarticles";
                Intent intent=new Intent(getContext(),book_list.class);
                intent.putExtra("type","文章热度榜");
                intent.putExtra("flag",2);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
    }

    /*private void setimg() {
        try {
            mLoopPager=(LoopPager)getActivity().findViewById(R.id.loopPager);
            mLoopPager.setRemoteImageUrls(picture_urls);
            mLoopPager.setEnableNavi(true);
            mLoopPager.setLoop(true);
            mLoopPager.setLoopDuration(3000);
            mLoopPager.setNaviRadius(4);
            mLoopPager.setNaviShape(LoopPager.CIRCLE);
            mLoopPager.setNaviWidthAndHeight(8,4);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"图片解析错误！",Toast.LENGTH_SHORT).show();
        }

    }

    class myclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "numpty", Toast.LENGTH_SHORT).show();
        }
    }*/
}
