package com.mywork.myapplication.common;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
public class Uploadfile {
    String target ="http://10.168.14.55:8080/upload/image";
    String path = "/storage/emulated/0/DCIM/Camera/IMG_20190628_024148.jpg";
    private String newName ="image.jpg";
    /* 上传文件至Server的方法 */

    public boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    public String uploadFile() {

        String end = "\r\n";
        String twoHyphens = "--";

        String boundary = "*****";//边界标识
        StringBuffer b = null;



        try {



            System.out.println("wenfujinjin");
            URL url = new URL(target);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */

            con.setDoInput(true);//允许输入流
            con.setDoOutput(true);//允许输出流

            con.setUseCaches(false);//不允许使用缓存
            /* 设置传送的method=POST */

            con.setRequestMethod("POST");
            /* setRequestProperty 设置编码 */

            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");

            con.setRequestProperty("Content-Type",// "multipart/form-data"这个参数来说明我们这传的是文件不是字符串了
                    "multipart/form-data;boundary=" + boundary);

            /* 设置DataOutputStream */
            DataOutputStream ds =

                    new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            Log.i("ds", String.valueOf(ds));
            ds.writeBytes("Content-Disposition: form-data; " +
                    "name=\"img\"; filename=\"image.jpg" +

                    newName + "\"" + end);

            ds.writeBytes(end);

            /* 取得文件的FileInputStream */

            FileInputStream fStream = new FileInputStream(path);
            /* 设置每次写入1024bytes */

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int length = -1;
            /* 从文件读取数据至缓冲区 */

            while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */

                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);

            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */

            fStream.close();
            ds.flush();

            /* 取得Response内容 */
            InputStream is = con.getInputStream();

            int ch;
            b = new StringBuffer();

            while ((ch = is.read()) != -1) {
                b.append((char) ch);

            }
            /* 将Response显示于Dialog */

            // showDialog("上传成功"+b.toString().trim());

            /* 关闭DataOutputStream */
            ds.close();

          /*  //返回客户端返回的信息
            return b.toString().trim();*/

        } catch (Exception e) {
            //showDialog("上传失败"+e);
           e.printStackTrace();
        }
        return end;
    }


}
