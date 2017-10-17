package com.lawer.context;

import org.apache.log4j.Logger;

import com.lawer.model.SystemInfo;
import com.lawer.util.NumberUtils;
import com.lawer.util.StringUtils;

public class Global {
	
	private static Logger logger = Logger.getLogger(Global.class);

	public static SystemInfo SYSTEMINFO;
	
	public static final String TABLE_PREFIX="";
	
	public static ThreadLocal ipThreadLocal= new ThreadLocal();
	
	public static String[] SYSTEMNAME=new String[]{"webname","meta_keywords","meta_description",
		"beian","copyright","fuwutel","address","weburl","theme_dir","version","weibo","wechat"};
	
	public static String VERSION="v1.0_rx";
	public static String getVersion(){
		return Global.getString("version");
	}
	
	public static String getValue(String key){
		Object o=null;
		if(SYSTEMINFO!=null){
			o=SYSTEMINFO.getValue(key);
		}
		if(o==null){
			return "";
		}
		return o.toString();
	}
	
	public static String getString(String key){
		String s=StringUtils.isNull(getValue(key));
		return s;
	}
	
	public static int getInt(String key){
		int i=NumberUtils.getInt(getValue(key));
		return i;
	}
	
	public static double getDouble(String key){
		double i=NumberUtils.getDouble(getValue(key));
		return i;
	}
	
	public static String getWebid(){
		return StringUtils.isNull(Global.getValue("webid"));
	}
	
	public static String getIP(){
		Object retObj=Global.ipThreadLocal.get();
		logger.debug("Set Ip:"+retObj);
		return retObj==null?"":retObj.toString();
	}
	
}
