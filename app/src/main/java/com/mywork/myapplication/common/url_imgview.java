package com.mywork.myapplication.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes
 */
public class url_imgview extends android.support.v7.widget.AppCompatImageView {
    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap= (Bitmap) msg.obj;
            setImageBitmap(bitmap);
        }
    };
    public url_imgview(Context context) {
        super(context);
    }

    public url_imgview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public url_imgview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void geturlimg(final String urlpath)
    {
        new Thread(){
            @Override
            public void run() {
                try {
                    String urlpath = "http://awb.img.xmtbang.com/img/uploadnew/201510/17/68cba5956786473cae0ebff8f3256736.jpg?tdsourcetag=s_pcqq_aiomsg";
                    URL url=new URL(urlpath);
                    Log.e("s","hahahaha");
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    InputStream is=conn.getInputStream();
                    Bitmap bitmap=BitmapFactory.decodeStream(is);
                    Message mes=Message.obtain();
                    mes.obj=bitmap;
                    handler.sendMessage(mes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
