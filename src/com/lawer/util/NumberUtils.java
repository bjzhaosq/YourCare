package com.lawer.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {
	public static double format(double d, String format) {
		DecimalFormat df = new DecimalFormat(format);
		String ds = df.format(d);
		return Double.parseDouble(ds);
	}

	/**
	 * 此方法遇到到5 舍去
	 * @param d
	 * @return
	 */
	public static double format2(double d) {
		// 四舍六入五考虑，五后非零就进一，五后皆零看奇偶，五前为偶应舍去，五前为奇要进一
		BigDecimal big = BigDecimal.valueOf(d);
		big = big.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return big.doubleValue();
	}
	/**
	 * 此方法遇到到5 舍去
	 * @param d
	 * @return
	 */
	public static double formatTo2(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		String ds = df.format(d);
		return Double.parseDouble(ds);
	}
	
	/**
	 * 此方法遇到到5入
	 * @param d
	 * @return
	 */
	public static double formatup2(double d) {
		BigDecimal big = BigDecimal.valueOf(d);
		big = big.setScale(2, BigDecimal.ROUND_HALF_UP);
		return big.doubleValue();
	}
	
	/**
	 * 此方法遇到保留两位，直接入,没有舍
	 * @param d
	 * @return
	 */
	public static double getDoubleFormat2Up(double d) {
		BigDecimal big = BigDecimal.valueOf(d);
		big = big.setScale(2, BigDecimal.ROUND_UP);
		return big.doubleValue();
	}

	public static String format2Str(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		String ds = df.format(d);
		return ds;
	}

	public static String format3Str(double d) {
		DecimalFormat df = new DecimalFormat("0.000");
		String ds = df.format(d);
		return ds;
	}

	public static double format4(double d) {
		DecimalFormat df = new DecimalFormat("0.0000");
		String ds = df.format(d);
		return Double.parseDouble(ds);
	}

	public static double format6(double d) {
		DecimalFormat df = new DecimalFormat("0.000000");
		String ds = df.format(d);
		return Double.parseDouble(ds);
	}

	public static double getDouble(String str) {
		if (str == null || str.equals(""))
			return 0.0;
		double ret = 0.0;
		try {
			ret = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			ret = 0.0;
		}
		return format6(ret);
	}

	public static long getLong(String str) {
		if (str == null || str.equals(""))
			return 0L;
		long ret = 0;
		try {
			ret = Long.parseLong(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	public static Long[] getLongs(String[] str) {

		if (str == null || str.length < 1)
			return new Long[] { 0L };
		Long[] ret = new Long[str.length];
		for (int i = 0; i < str.length; i++) {
			ret[i] = getLong(str[i]);
		}
		return ret;
	}
	
	public static Double[] getDoubles(String[] str) {
		if (str == null || str.length < 1)
			return new Double[] { 0d };
		Double[] ret = new Double[str.length];
		for (int i = 0; i < str.length; i++) {
			ret[i] = getDouble(str[i]);
		}
		return ret;
	}
	
	public static int[] getInts(String[] str) {

		if (str == null || str.length < 1)
			return new int[] { 0 };
		int[] ret = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			ret[i] = getInt(str[i]);
		}
		return ret;
	}

	public static int getInt(String str) {
		if (str == null || str.equals(""))
			return 0;
		int ret = 0;
		try {
			ret = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	public static int compare(double x, double y) {
		BigDecimal val1 = new BigDecimal(x);
		BigDecimal val2 = new BigDecimal(y);
		return val1.compareTo(val2);
	}

	/**
	 * @param d
	 * @param len
	 * @return
	 */
	public static double ceil(double d, int len) {
		String str = Double.toString(d);
		int a = str.indexOf(".");
		if (a + 3 > str.length()) {
			a = str.length();
		} else {
			a = a + 3;
		}
		str = str.substring(0, a);
		return Double.parseDouble(str);
	}

	public static double ceil(double d) {
		return ceil(d, 2);
	}

	public static long getRandom(int len) {
		double r = Math.random();
		for (int i = 0; i < len; i++) {
			r = r * 10;
		}
		long ret = (long) r;
		return ret;
	}

	/**
	 * 用于两个小数相减，使用bigdecimai计算，避免出现误差
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static double subtract(double num1, double num2) {

		BigDecimal numa = new BigDecimal(num1 * 100000);
		BigDecimal numb = new BigDecimal(num2 * 100000);
		double result = numa.subtract(numb).doubleValue() / 100000;
		return result;
	}

	public static double add(double num1, double num2) {

		BigDecimal numa = new BigDecimal(num1 * 100000);
		BigDecimal numb = new BigDecimal(num2 * 100000);
		double result = numa.add(numb).doubleValue() / 100000;
		return result;
	}

	public static double getDouble2(String numStr) {
		double num = NumberUtils.getDouble(numStr);
		return format2(num);
	}
	
	
	/**
	 * 小数点后有则进1
	 * @param num
	 * @return
	 */
	public static int getIntWithDouble(double num){
		long num2 =getLong(String.valueOf(num).replaceAll("\\d+\\.", ""));
		if(num2>0){
			 return (int)num+1;
		}
		return getInt((int)num+"");
	}
	
}
