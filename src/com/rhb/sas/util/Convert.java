package com.rhb.sas.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Convert {

	public static String toString(Object obj, String format){
		String str = "";
		if(obj == null){
		}else if(obj instanceof java.lang.String){
			if(isNumber((String)obj) && format!=null){  
				try{
					Double d = new Double(obj.toString()); 
					str = doubleParse(d,format);
				}catch(Exception e){}
			}else{
				str = obj.toString();
			}
		}else if(obj instanceof java.lang.Integer){
			str = ((Integer)obj).toString();
		}else if(obj instanceof java.math.BigDecimal){
			str = bdParse((BigDecimal)obj,format);
		}else if(obj instanceof java.lang.Double){
			str = doubleParse((Double)obj,format);
		}else if(obj instanceof java.lang.Float){
			str = floatParse((Float)obj,format);
		}else if(obj instanceof java.util.Date){
			str = dateParse((Date)obj,format);
		}else{
			str = obj.toString();
		}
		return str;
	}

	public static Date toDate(Object obj, String format){
		Date dd = null;
		if(obj == null){
			
		}else if(obj instanceof java.util.Date){
			dd = (Date)obj;
		}
		if(format == null){
			dd = getDate(obj.toString());
		}else{
			dd = getDate(obj.toString(),format);
		}
		
		return dd;
	}
	
	public static int toInt(Object obj){
		int i = 0;
		if(obj == null){
		}else if(obj instanceof java.lang.String){
			if(isNumber((String)obj)){
				Double dd = new Double((String)obj);
				i = dd.intValue();
			}
		}else if(obj instanceof java.lang.Integer){
			i = (Integer)obj;
		}else if(obj instanceof java.math.BigDecimal){
			BigDecimal bd = (BigDecimal)obj;
			i = bd.intValue();
		}else if(obj instanceof java.lang.Double){
			Double dd = (Double)obj;
			i = dd.intValue();
		}else if(obj instanceof java.lang.Float){
			Float ff = (Float)obj;
			i = ff.intValue();
		}
		return i;
	}
	
	public static double toDouble(Object obj){
		double d = 0;
		if(obj == null){
		}else if(obj instanceof java.lang.String){
			if(isNumber((String)obj)){
				try{
					d = new Double(obj.toString()); 
				}catch(Exception e){}
			}
		}else if(obj instanceof java.lang.Integer){
			d = new Double((Integer)obj);
		}else if(obj instanceof java.math.BigDecimal){
			BigDecimal bd = (BigDecimal)obj;
			d = bd.doubleValue();
		}else if(obj instanceof java.lang.Double){
			d = (Double)obj;
		}else if(obj instanceof java.lang.Float){
			d = new Double((Float)obj);
		}
		return d;
	}
	
	public static float toFloat(Object obj){
		float f = 0;
		if(obj == null){
		}else if(obj instanceof java.lang.String){
			if(isNumber((String)obj)){
				try{
					f = new Float(obj.toString()); 
				}catch(Exception e){}
			}
		}else if(obj instanceof java.lang.Integer){
			f = new Float((Integer)obj);
		}else if(obj instanceof java.math.BigDecimal){
			BigDecimal bd = (BigDecimal)obj;
			f = bd.floatValue();
		}else if(obj instanceof java.lang.Double){
			f = new Float((Double)obj);
		}else if(obj instanceof java.lang.Float){
			f = (Float)obj;
		}
		return f;
	}
	
	private static Date getDate(String date, String format){
		Date d = null;
		
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			d = df.parse(date);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return d;
	}
	
	private static Date getDate(String date){
		Date d = null;
		String[] formats = {"yyyy-MM-dd hh:mm:ss",
							"yyyy-MM-dd hh:mm",
							"yyyy-MM-dd hh",
							"yyyy-MM-dd",
							"E MMM dd hh:mm:ss z yyyy"};
		for(int i=0; i<formats.length; i++){
			d = getDate(date,formats[i],Locale.US);
			if(d!=null){
				break;
			}
		}
		return d;
	}

	private static Date getDate(String date, String format, Locale locale){
		Date d = null;
		SimpleDateFormat df = new SimpleDateFormat(format,locale);
		try {
			d = df.parse(date);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return d;
	}
	
	private static String doubleParse(Double d, String format){
		if(format==null){
			format = "";
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(d);
	}
	
	private static String bdParse(BigDecimal bd, String format){
		if(format==null){
			format = "";
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(bd.doubleValue());
	}

	private static String floatParse(Float f, String format){
		if(format==null){
			format = "";
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(f);
	}
	
	private static String dateParse(Date d, String format){
		if(format==null){
			format = "yyyy-MM-dd hh:mm:ss";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(d);
	}
	
	private static boolean isNumber(String s)
	{
	   if((s == null)||(s.length() == 0)||!s.matches("[0-9]*(\\.?)[0-9]*")){
		   return false;
	   }
	   else{
	     return true;
	   }
	}
}
