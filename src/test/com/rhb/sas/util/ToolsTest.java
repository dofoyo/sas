package test.com.rhb.sas.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.rhb.sas.util.Tools;

public class ToolsTest {
	//@Test
	public void test(){
		System.out.println(Tools.isDate(null, "yyyyMM"));
		List<String> list = null;
		for(String s : list){
			System.out.println("---");
		}
	}
	
	//@Test
	public void test1(){
		int i = 2;
		int j = 2;
		int k = i/j;
		Date d1 = Tools.getDate("2009-04-14");
		Date d2 = Tools.getDate("2009-03-21");
		System.out.println("d1-d2=" + d1.compareTo(d2));
		System.out.println("d2-d1=" + d2.compareTo(d1));
		
		System.out.println(k);
	}
	
	//@Test
	public void test2(){
		Date date = Tools.getDate("2012-12-20");
		Date newDate = Tools.getDate(date, 15);
		System.out.println(Tools.getDate(newDate, "yyyy-MM-dd"));
		
	}
	
	
	@Test
	public void test3(){
		Map<String, Integer> m = new HashMap();
		m.put("300022", 1);
		m.put("300022", -1);
		System.out.println(m.size());
	}
}
