package com.mywork.myapplication.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Analysis
 * @Date 2019-06-17 16:41
 **/

public class Analysis {
//    public static String f_id;

    //登录数据的解析
    public static ArrayList<HashMap<String, Object>> AnalysisLogin(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject data = jsonObject.getJSONObject("user");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("username", data.getInt("username"));
        list.add(map);

        return list;
    }

    public static ArrayList<HashMap<String, Object>> AnalysisRegister(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//        JSONArray jsonArray= null;
//        jsonArray = new JSONArray(jsonStr);
//        for(int i = 0; i < jsonArray.length(); i++){
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            HashMap<String,Object> map = new HashMap<String,Object>();
//            map.put("username",jsonObject.getString("username"));
//            list.add(map);
//        }
        JSONObject jsonObject = new JSONObject(jsonStr);
//        JSONObject data = jsonObject.getJSONObject("data");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("status", jsonObject.getInt("status"));
        map.put("msg", jsonObject.getString("msg"));
        list.add(map);

        return list;
    }

    public static ArrayList<HashMap<String, Object>> AnalysisDateInfo(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONArray jsonArray = new JSONArray(jsonStr);

        JSONObject jsonObject = jsonArray.getJSONObject(0);
        JSONObject jsonObject1 = jsonObject.getJSONObject("met_atm");
        JSONObject jsonObject2 = jsonObject.getJSONObject("met_envir");
        JSONObject jsonObject3 = jsonObject.getJSONObject("met_soil");

        JSONObject json = jsonArray.getJSONObject(1);
        JSONObject jsonObject4 = json.getJSONObject("for_soil");
        JSONObject jsonObject5 = json.getJSONObject("fish");
        JSONObject jsonObject6 = json.getJSONObject("for_atm");
        HashMap<String, Object> map = new HashMap<String, Object>();
//气象站
        map.put("air_temper", jsonObject1.getDouble("atm_tem"));
        map.put("air_templa", jsonObject1.getDouble("atm_humidity"));
        map.put("oxygen", jsonObject1.getDouble("o2"));
        map.put("pressure", jsonObject1.getDouble("atm_pre"));

        map.put("waterLevel",jsonObject2.getDouble("rain"));
        map.put("wind_sp",jsonObject2.getDouble("wind_sp"));
        map.put("wind_dir",jsonObject2.getDouble("wind_dir"));
        map.put("light",jsonObject2.getDouble("illum"));

        map.put("soil_temper",jsonObject3.getDouble("soil_tem"));
        map.put("soil_templa",jsonObject3.getDouble("soil_humidity"));
//        渔林
        map.put("soil_nity",jsonObject4.getDouble("soil_nity"));
        map.put("soil_tem",jsonObject4.getDouble("soil_tem"));
        map.put("soil_hum",jsonObject4.getDouble("soil_hum"));
        map.put("conductivity",jsonObject4.getDouble("conductivity"));

        map.put("water_tem",jsonObject5.getDouble("water_tem"));
        map.put("turbidity",jsonObject5.getDouble("turbidity"));

        map.put("co2",jsonObject6.getDouble("co2"));
        map.put("atm_tem",jsonObject6.getDouble("atm_tem"));
        map.put("smoke",jsonObject6.getDouble("smoke"));
        map.put("atm_humidity",jsonObject6.getDouble("atm_humidity"));
        list.add(map);


        /*JSONObject jsonObject1 = jsonObject.getJSONObject("met_envir");
        JSONObject jsonObject2 = jsonObject.getJSONObject("met_oil");
        JSONObject jsonObject3 = jsonObject.getJSONObject("met_atm");

        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("waterLevel",jsonObject1.getDouble("rain"));
        map.put("wind_sp",jsonObject1.getDouble("wind_sp"));
        map.put("wind_dir",jsonObject1.getDouble("wind_dir"));
        map.put("light",jsonObject1.getDouble("illum"));

        map.put("soil_temper",jsonObject2.getDouble("soil_tem"));
        map.put("soil_templa",jsonObject2.getDouble("soil_humidity"));

        map.put("air_temper",jsonObject3.getDouble("atm_tem"));
        map.put("air_templa",jsonObject3.getDouble("atm_humidity"));
        map.put("oxygen",jsonObject3.getDouble("o2"));
        map.put("pressure",jsonObject3.getDouble("atm_pre"));
        list.add(map);*/
        return list;
    }

    public static ArrayList<HashMap<String, Object>> AnalysisfishDateInfo(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject jsonObject1 = jsonObject.getJSONObject("envir");
        JSONObject jsonObject2 = jsonObject.getJSONObject("soil");
        JSONObject jsonObject3 = jsonObject.getJSONObject("atm");

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("waterLevel", jsonObject1.getDouble("rain"));
        map.put("wind_sp", jsonObject1.getDouble("wind_sp"));

        map.put("wind_dir", jsonObject1.getDouble("wind_dir"));
        map.put("light", jsonObject1.getDouble("illum"));

        map.put("soil_temper", jsonObject2.getDouble("soil_tem"));
        map.put("soil_templa", jsonObject2.getDouble("soil_humidity"));

        map.put("air_temper", jsonObject3.getDouble("atm_tem"));
        map.put("air_templa", jsonObject3.getDouble("atm_humidity"));
        map.put("oxygen", jsonObject3.getDouble("o2"));
        map.put("pressure", jsonObject3.getDouble("atm_pre"));

        list.add(map);
        return list;
    }

    public static ArrayList<HashMap<String, Object>> AnalysisGetApPerson(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject jsonObject1 = jsonObject.getJSONObject("user");
        /* JSONArray jsonArray= null;*/

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("username", jsonObject1.getString("username"));
        map.put("name", jsonObject1.getString("name"));
        /*jsonArray = jsonObject.getJSONArray("data");
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",jsonObject1.getInt("id"));
            map.put("name",jsonObject1.getString("name"));
            list.add(map);
        }*/
        return list;
    }

    public static ArrayList<HashMap<String, Object>> AnalysisGetAllProduct(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray jsonArray = null;

        jsonArray = jsonObject1.getJSONArray("list");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", jsonObject2.getInt("id"));
            map.put("userId", jsonObject2.getInt("userId"));
            map.put("categoryId", jsonObject2.getInt("categoryId"));
            map.put("livestock", jsonObject2.getInt("livestock"));
            map.put("name", jsonObject2.getString("name"));
            map.put("subtitle", jsonObject2.getString("subtitle"));
            map.put("mainImage", jsonObject2.getString("mainImage"));
            map.put("detail", jsonObject2.getString("detail"));
            map.put("stock", jsonObject2.getInt("stock"));
            map.put("status", jsonObject2.getInt("status"));
            map.put("price", jsonObject2.getDouble("price"));
//            map.put("price",jsonObject2.getBig)
            list.add(map);
        }
        return list;
    }

    public static ArrayList<HashMap<String, Object>> AnalysisCreateOrder(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        JSONObject jsonObject = new JSONObject(jsonStr);
//        JSONObject data = jsonObject.getJSONObject("data");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("status", jsonObject.getInt("status"));
        map.put("data", jsonObject.getString("data"));
        list.add(map);

        return list;
    }

    public static ArrayList<HashMap<String, Object>> AnalysisWatch(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        JSONObject jsonObject = new JSONObject(jsonStr);
//        JSONObject data = jsonObject.getJSONObject("data");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("status", jsonObject.getInt("status"));
        map.put("msg", jsonObject.getString("msg"));
        list.add(map);

        return list;
    }

    /**
     * @Description //TOOD 查看订单
     **/
    public static ArrayList<HashMap<String, Object>> AnalysisShowCart(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        System.out.println("23222222222222222222222222223");
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray jsonArray = null;

        jsonArray = jsonObject1.getJSONArray("list");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", jsonObject2.getInt("id"));
            map.put("userId", jsonObject2.getInt("userId"));
            map.put("productId", jsonObject2.getInt("productId"));
            map.put("quantity", jsonObject2.getInt("quantity"));
            map.put("checked", jsonObject2.getInt("checked"));
            map.put("mainImage", jsonObject2.getString("mainImage"));
            list.add(map);
        }
        return list;
    }

    public static ArrayList<HashMap<String, Object>> AnalysisUserInfo(String jsonStr) throws JSONException {
        //初始化list对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        System.out.println("23222222222222222222222222223");
        JSONObject jsonObject1 = jsonObject.getJSONObject("user");

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("identity", jsonObject1.getString("identity"));
        map.put("sex", jsonObject1.getString("sex"));
        map.put("name", jsonObject1.getString("name"));
        map.put("id", jsonObject1.getInt("id"));
        map.put("email", jsonObject1.getString("email"));
        map.put("hobby", jsonObject1.getString("hobby"));
        map.put("username", jsonObject1.getString("username"));
        map.put("face", jsonObject1.getString("face"));

        list.add(map);
        return list;
    }
}
