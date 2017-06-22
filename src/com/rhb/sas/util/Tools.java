package com.rhb.sas.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	
	public static Date getDate(Date date, int day){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + day);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return getDate(dayBefore);
	}
	
	public static Date getDate(String date,String format) {
		Date d = null;
		try {
			java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat(format);
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			d = dformat.parse(date);
		} catch (Exception e1) {
		}
		return d;
	}
	
	public static boolean isDate(String date, String format){
		boolean flag = true;
		try {
			java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat(format);
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			Date d = dformat.parse(date);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	} 
	
	public static Date getDate(String date) {
		return getDate(date,"yyyy-MM-dd");
	}
	
	public static String getDate(Date d, String format) {
		if(d == null) return null;
		
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(d);
	}

	public static Double getDouble(String str) {
		if(str==null || str.trim().length()==0){
			return 0.0;
		}
		if(str.indexOf(",") != -1){
			str = str.replaceAll(",", "");
		}
		Double d = 0.0;
		try {
			d = parseDouble(str);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("*********" + str + "*************");
		}
		return d;
	}
	
	private static Double parseDouble(String str){
		str = str.replace("--", "0");
		str = str.replace("&nbsp;", "0");
		int i = 1;
		if(str.indexOf("百万") != -1){
			str = str.replaceAll("百万", "");
			i = 1000000;
		}else if(str.indexOf("万元") != -1){
			str = str.replaceAll("万元", "");
			i = 10000;			
		}else if(str.indexOf("千元") != -1){
			str = str.replaceAll("千元", "");
			i = 1000;			
		}else{
			str = str.replaceAll("元", "");
		}
		return Double.parseDouble(str)*i;
	}

	public static Float getFloat(String str) {
		Float f = new Float(0);
		try {
			f = Float.parseFloat(str);
		} catch (Exception e) {}
		return f;
	}
	
	public static Integer getInteger(String str){
		Integer i = 0;
		try{
			i = Integer.parseInt(str);
		}catch(Exception e){}
		return i;
	}
	
	/*
	public static List parse(String str, String findRegexp, String replaceRegexp){
		//System.out.println(str);
		List list = new ArrayList();
		Pattern pt = Pattern.compile(findRegexp);
		Matcher mt = pt.matcher(str);
		while (mt.find()) {
			String s = mt.group().replaceAll(replaceRegexp, "");
			//System.out.println("..."+s);
			list.add(s);
		}
		return list;
	}
	
	public static List parse(String str, String findRegexp){
		List list = new ArrayList();
		Pattern pt = Pattern.compile(findRegexp);
		Matcher mt = pt.matcher(str);
		while (mt.find()) {
			list.add(mt.group());
		}
		return list;
	}
	*/

	public static String subString(String str, String regexp){
		List<String> list = subStrings(str,regexp);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public static List<String> subStrings(String str, String regexp){
		List list = new ArrayList();

		if(str == null || regexp==null || "".equals(regexp.trim())){
			return list;
		}
		String findRegexp = regexp.replace("|", ".*?");
		boolean replace = regexp.indexOf("|")==-1 ? false : true;
		
		Pattern pt = Pattern.compile(findRegexp);
		Matcher mt = pt.matcher(str);
		//System.out.println(mt.group());
		while (mt.find()){
			if(replace){
				list.add(mt.group().replaceAll(regexp, ""));				
			}else{
				list.add(mt.group());
			}
		}
		return list;
	}
	
	public static String subString(String str, String begin, String end){
		if(str==null || begin==null || end==null){
			return null;
		}
		int i = str.indexOf(begin);
		int j = str.indexOf(end);
		
		return str.substring(i+begin.length(), j);
		
	}
	
	
}
