package com.mywork.myapplication.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WSInformationService {
        public  static String result = "";
        /**
         * @Description //TOOD http连接服务器，获取数据
         * @Param [URL地址, 发送请求的类型]
         * @return java.lang.String
         **/
        public static String send(){
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL("http://10.168.14.55:8080/app/data").openConnection();
                //请求类型
                conn.setRequestMethod("GET");
                conn.connect();
                InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
                BufferedReader buffer =new BufferedReader((inStream));//获取输入流对象
                String inputLine = null;

                while ((inputLine = buffer.readLine()) != null) {
                    result += inputLine+"\n";
                }
                inStream.close();
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream inStream= new ByteArrayOutputStream();;
            System.out.println(new String(inStream.toByteArray()));
            //通过out.Stream.toByteArray获取到写的数据
            System.out.println(result);
            return result;

        }

        /**
         * @Description 解析数据
         * @Param [http从服务器获取的数据]
         * @return java.util.ArrayList<java.util.HashMap<java.lang.String,java.lang.Object>>
         **/
        public static ArrayList<HashMap<String,Object>> Analysis(String jsonStr) throws JSONException {
            JSONArray jsonArray = null;
            //初始化list对象
            ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            jsonArray = new JSONArray(jsonStr);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("waterLevel",jsonObject.getString("rain"));
                map.put("light", jsonObject.getString("illum"));
                list.add(map);
            }
//            JSONObject jsonObject = new JSONObject(jsonStr);
//            JSONObject data = jsonObject.getJSONObject("data");


            return list;
        }

        /**
         * @Description //TOOD 适配数据，返回result
         * @return java.lang.String
         **/
        public static List<HashMap<String, Object>> resultJson(){
            List<HashMap<String,Object>> allData = null;
            try {
           /* String url = "http://10.168.14.55:8080/meteorological/sp";

                String path = null;*/
                /*if(type.equals("login")){
                    path = "/fmall/user/login.do";
                    url = url + path;
                }else if(type.equals("register")){
                    path = "/fmall/user/register.do";
                    url = url + path;
                }else if(type.equals("userInfo")){
                    path = "/farmmall/manage/queryByName.do";
                    url = url + path;
                }*/
//                url = url + s;
//                String request = r;
               /* String request = "POST";*/

                allData = Analysis(send());
                /*return send();*/

//                Iterator<HashMap<String, Object>> it = allData.iterator();
//
//                while (it.hasNext()) {
//                    Map<String, Object> ma = it.next();
//                    result = (String) ma.get("value");
//                    System.out.println("气象站");
//                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return allData;
            }
        }
    public static String gethttpresult(String urlStr){
        try {
            URL url=new URL(urlStr);
            HttpURLConnection connect=(HttpURLConnection)url.openConnection();
            InputStream input=connect.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String line = null;
            System.out.println(connect.getResponseCode());
            StringBuffer sb = new StringBuffer();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * 获得搜索结果
     * @param bookName  用户输入的搜索关键字
     * @return
     */
    public static String getsearchresult(String bookName){
        String h="http://122.114.237.201/getbook/search";
        JSONObject object=new JSONObject();

        try {
            object.put("bookName",bookName);

            String content = String.valueOf(object);
            URL url=new URL(h);
            HttpURLConnection connect=(HttpURLConnection)url.openConnection();
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestMethod("POST");
            connect.setUseCaches(false);
            connect.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            OutputStream outputStream = connect.getOutputStream();
            outputStream.write(content.getBytes());
            int response = connect.getResponseCode();
            System.out.println(connect);
            if (response== HttpURLConnection.HTTP_OK)
            {
                System.out.println(response);
                InputStream input=connect.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));
                String line = null;
                System.out.println(connect.getResponseCode());
                StringBuffer sb = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }
            else {
                System.out.println(response);
                return "not exsits";
            }
        } catch (Exception e) {
            Log.e("e:", String.valueOf(e));
            return "internet errar";
        }
    }
}
