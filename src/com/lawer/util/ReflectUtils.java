package com.lawer.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;
/**
 * 
 * 反射帮助类
 *
 */
public class ReflectUtils {
	private static Logger logger = Logger.getLogger(ReflectUtils.class);
	public static Object invokeGetMethod(Class claszz, Object o, String name) {
		Object ret = null;
		try {
			String[] names = name.split("/.");
			for (int i = 0; i < names.length; i++) {
				if(names.length>i){
					if(ret != null){
						 o = ret;
					}
					Method method2 = null;
					if(names[i].length()>7 && "(Other)".equals(names[i].substring(0, 7))){
						method2 = claszz.getMethod(names[i].substring(7));
					}else{
						method2 = claszz.getMethod("get" + StringUtils.firstCharUpperCase(names[i]));
					}					
					ret = method2.invoke(o);
					if(ret != null){
						claszz = ret.getClass();
					}else{
						return null;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	public static Object invokeSetMethod(Class claszz, Object o, String name,
			Class[] argTypes, Object[] args) {
		Object ret = null;
		try {
			Method method = claszz.getMethod(
					"set" + StringUtils.firstCharUpperCase(name), argTypes);
			ret = method.invoke(o, args);
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	public static Object invokeSetMethod(Class claszz, Object o, String name,
			Class argType, Object args) {
		Object ret = null;
		try {
			Method method = claszz.getMethod(
					"set" + StringUtils.firstCharUpperCase(name),
					new Class[] { argType });
			ret = method.invoke(o, new Object[] { argType });
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if ((index >= params.length) || (index < 0)) {
			throw new RuntimeException("你输入的索引"
					+ ((index < 0) ? "不能小于0" : "超出了参数的总数"));
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}
}
