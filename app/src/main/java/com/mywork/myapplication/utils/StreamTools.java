package com.mywork.myapplication.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTools {
	
	public static String readInputStream(InputStream is ) {
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len=is.read(buffer))!=-1) {
				baos.write(buffer,0,len);
			}
			is.close();
			baos.close();
			byte[] result = baos.toByteArray();
			//试着解析result里面的内容
			String temp = new String(result);
//			if(temp.contains("utf-8")) {
//				return temp;
//			}else if(temp.contains("gbk2312")){
//				return new String(result,"gbk2312");
//			}
			return temp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "获取失败";
		}
		
	}

}
