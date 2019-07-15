package com.mywork.myapplication.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName HttpRequest
 * @Date 2019-06-14 10:36
 **/

public class HttpRequest {
    private String result = "";
    public static Bitmap bitmap = null;
    private static String session_id = null;
    static List<HashMap<String,Object>> allData = null;
    /**
     * @Description //TOOD http连接服务器，获取数据
     * @Param [URL地址, 发送请求的类型]
     * @return java.lang.String
     **/
    public static String send(String u, String request,String type){
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            URL url = new URL(u);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //请求类型
            conn.setRequestMethod(request);
//            conn.connect();
            /*if (session_id != null && ((type == "createOrder")||(type == "addCollection")||(type == "addCart")||(type == "showCart"))) {
                conn.setRequestProperty("Cookie", session_id);//设置sessionid
            }*/
            InputStream inStream = conn.getInputStream();
            String cookieval = conn.getHeaderField("Set-Cookie");
            if (cookieval != null && type == "login") {
                session_id = null;
                session_id = cookieval.substring(0, cookieval.indexOf(";"));//获取sessionid
                Log.i("SESSION", "session_id=" + session_id);
            }
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(new String(outStream.toByteArray()));
        //通过out.Stream.toByteArray获取到写的数据
        return new String(outStream.toByteArray());
    }
    /**
     * fileName 文件名(不带后缀)
     * filePath 文件的本地路径 (xxx / xx / test.jpg)
     */
    public static void uploadFile(String fileName, String filePath) {

        HttpURLConnection conn = null;

        /// boundary就是request头和上传文件内容的分隔符(可自定义任意一组字符串)
        String BOUNDARY = "******";
        // 用来标识payLoad+文件流的起始位置和终止位置(相当于一个协议,告诉你从哪开始,从哪结束)
        String  preFix = ("\r\n--" + BOUNDARY + "--\r\n");

        String target="http://10.168.14.55:8080/upload/image";
        URL url;
        try {
            // (HttpConst.uploadImage 上传到服务器的地址
            url = new URL(target);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方法
            conn.setRequestMethod("POST");
            // 设置header
            conn.setRequestProperty("Accept","*/*");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            // 获取写输入流
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 获取上传文件
            File file = new File(filePath);

            // 要上传的数据
            StringBuffer strBuf = new StringBuffer();

            // 标识payLoad + 文件流的起始位置
            strBuf.append(preFix);

            // 这里只要把.jpg改成.txt，把Content-Type改成上传文本的类型，就能上传txt文件了。
            strBuf.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName+".jpg" + "\"\r\n");
            strBuf.append("Content-Type: image/jpeg"  + "\r\n\r\n");
            out.write(strBuf.toString().getBytes());

            // 获取文件流
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream inputStream = new DataInputStream(fileInputStream);

            // 每次上传文件的大小(文件会被拆成几份上传)
            int bytes = 0;
            // 计算上传进度
            float count = 0;
            // 获取文件总大小
            int fileSize = fileInputStream.available();
            // 每次上传的大小
            byte[] bufferOut = new byte[1024];
            // 上传文件
            while ((bytes = inputStream.read(bufferOut)) != -1) {
                // 上传文件(一份)
                out.write(bufferOut, 0, bytes);
                // 计算当前已上传的大小
                count += bytes;
                // 打印上传文件进度(已上传除以总大*100就是进度)
//                utils.logD("progress:" +(count / fileSize * 100) +"%");
                Log.i("progress：",(count / fileSize * 100) +"%");
            }

            // 关闭文件流
            inputStream.close();

            // 标识payLoad + 文件流的结尾位置
            out.write(preFix.getBytes());

            // 至此上传代码完毕

            // 总结上传数据的流程：preFix + payLoad(标识服务器表单接收文件的格式) + 文件(以流的形式) + preFix
            // 文本与图片的不同,仅仅只在payLoad那一处的后缀的不同而已。

            // 输出所有数据到服务器
            out.flush();

            // 关闭网络输出流
            out.close();

            // 重新构造一个StringBuffer,用来存放从服务器获取到的数据
            strBuf = new StringBuffer();

            // 打开输入流 , 读取服务器返回的数据
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            String line;

            // 一行一行的读取服务器返回的数据
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }

            // 关闭输入流
            reader.close();

            // 打印服务器返回的数据
            Log.i("上传成功",strBuf.toString());
//            utils.logD("上传成功:"+strBuf.toString());

        } catch (Exception e) {
            Log.i("上传图片出错",e.toString());
//            utils.logD("上传图片出错:"+e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

    }
    /**
     * @Description 解析数据
     * @Param [http从服务器获取的数据]
     * @return java.util.ArrayList<java.util.HashMap<java.lang.String,java.lang.Object>>
     **/
    private static ArrayList<HashMap<String,Object>> Analysis(String jsonStr) throws JSONException{
        JSONArray jsonArray = null;
        //初始化list对象
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
//        jsonArray = new JSONArray(jsonStr);
//        for(int i = 0; i < jsonArray.length(); i++){
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            HashMap<String,Object> map = new HashMap<String,Object>();
//            map.put("username",jsonObject.getString("username"));
//            list.add(map);
//        }
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject data = jsonObject.getJSONObject("data");

        System.out.println(jsonObject.getBoolean("success"));
        System.out.println(data.getString("username"));

        return list;
    }

    /**
     * @Description //TOOD 适配数据，返回result
     * @return java.lang.String
     **/
    public static List<HashMap<String,Object>> resultJson(String s, String r, String type){
        try {
//            String u = "http://10.168.14.77:8080/farmmall/manage/doAJAXLogin.do";
//            String url = "http://10.168.14.55:8080/meteorological/sp";
            String url = "http://47.111.134.50:8100";
//            String url = "http://10.168.14.77:8080";
            String path = null;
            if(type.equals("login")){
                path = "/fmall/user/login.do";
                url = url + path;
            }else if(type.equals("register")){          //注册事件
                path = "/auth/regist";
                url = url + path;
            }else if(type.equals("dateWS")){            //气象站数据
//                path = "/farmmall/manage/queryByName.do";
                path = "http://47.111.134.50:8100/app/data";
                url =  path;
            }else if(type.equals("fish")){
//                path = "/farmmall/manage/queryByName.do";
                path = "http://47.111.134.50:8100/app/data";
                url =  path;
            }else if(type.equals("image")){
                url = "/auth/regist";
            }else if(type.equals("dateperson")){         //个人资料按钮
                path="http://47.111.134.50:8100/auth/applogin";
                url = path;
            }else if(type.equals("getAllProduct")){
                url = url + "/fmall/product/show_product.do";
            }else if(type.equals("createOrder")){
                url = url + "/fmall/order/create.do";
            }else if(type.equals("showCart")){
                url = url + "/fmall/cart/show_cart.do";
            }else if(type.equals("addCollection")){
                url = url + "/fmall/product/add_collection.do";
            }else if(type.equals("addCart")){
                url = url + "/fmall/cart/add_cart.do";
            }
            url = url + s;
            String request = r;
            Analysis analysis = new Analysis();

            if(type.equals("login")){
                allData = analysis.AnalysisLogin(send(url,request,type));
            }else if(type.equals("register")){
                String result = send(url,request,type);
                allData = analysis.AnalysisRegister(result);
                Log.i("allData", result);
            }else if(type.equals("dateWS")){
                allData = analysis.AnalysisDateInfo(send(url,request,type));
            }else if(type.equals("fish")){
                allData = analysis.AnalysisfishDateInfo(send(url,request,type));
            }else if(type.equals("image")){
                getImage(url,request);
            }else if(type.equals("dateperson")){
                allData = analysis.AnalysisGetApPerson(send(url,request,type));
            }else if(type.equals("getAllProduct")){
                allData = analysis.AnalysisGetAllProduct(send(url,request,type));
            }else if(type.equals("createOrder")){
                allData = analysis.AnalysisCreateOrder(send(url,request,type));
            }else if(type.equals("showCart")){
                allData = analysis.AnalysisShowCart(send(url,request,type));
            }else if(type.equals("addCollection")){
                allData = analysis.AnalysisWatch(send(url,request,type));
            }else if(type.equals("addCart")){

                allData = analysis.AnalysisWatch(send(url,request,type));
                if(allData == null){
                    System.out.println("111111111111111111111111111111111111111111111111111");
                }
            }

//            Log.i("dingdanxinxi",String.valueOf(allData.get(0).get("name")));
//            Iterator<HashMap<String, Object>> it = allData.iterator();
//
//            while (it.hasNext()) {
//                Map<String, Object> ma = it.next();

//                if (Integer.parseInt((String) ma.get("rid")) == 101) {
//                    System.out.println((String) ma.get("rstate"));
//                    result = (String) ma.get("rstate");
//                }
//                System.out.println(ma.get("stateCode"));
//                result = String.valueOf(ma.get("status"));
//                System.out.println(result);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return allData;
        }
    }



    public static String httpRequest(String requrl) {
        String result = null;// 请求返回的字符串
        try {
            URL url = new URL(requrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            if (session_id != null) {
                con.setRequestProperty("Cookie", session_id);//设置sessionid
            }
            InputStream is = con.getInputStream();
            String cookieval = con.getHeaderField("Set-Cookie");
            if (cookieval != null) {
                session_id = cookieval.substring(0, cookieval.indexOf(";"));//获取sessionid
                Log.i("SESSION", "session_id=" + session_id);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            is.close();
            byte[] resultbyte = bos.toByteArray();
            result = bos.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void getImage(String u, String request){
        try{
            URL url = new URL(u);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(request);
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            if(code == 200){
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
//                System.out.println("222222222222222222222222222222222222222222222222222222222");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
