package com.rhb.sas.interfaces.downloadreport.sina;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.interfaces.downloadreport.dto.ReportInfoDTO;
import com.rhb.sas.util.Tools;

public class SinaTools {
	public static List<ReportInfoDTO> getReportInfo(String str, java.util.Date reportDate) throws Exception{	
		List<ReportInfoDTO> list = new ArrayList();
		//System.out.println("********** reportDate = " + reportDate);
		ReportInfoDTO info;
		if(reportDate != null){
			info = new ReportInfoDTO();
			info.setBeginDate(Tools.getDate(Tools.getDate(reportDate, "yyyy") + "-01-01"));
			info.setEndDate(reportDate);
			info.setPeriodType("期末");
			info.setDescription(Tools.getDate(reportDate, "yyyy-MM-dd"));
			//System.out.println("**************");
			//System.out.println(info.toString());
			list.add(info);
		}else{		
			String findPeriodRegexp = null;
			if(str.indexOf("报告期") != -1){
				findPeriodRegexp = "报告期</strong></td>|</tr>";			
			}else if(str.indexOf("公告期") != -1){
				findPeriodRegexp = "公告期</strong></td>|</tr>";			
			}else if(str.indexOf("报表日期") != -1){
				findPeriodRegexp = "报表日期</strong></td>|</tr>";			
			}else if(str.indexOf("报告日期") != -1){
				findPeriodRegexp = "报告日期</strong></td>|</tr>";			
			}else{
				throw new Exception("can not fix the findperiodregexp");
			}
			//System.out.println("********** findPeriodRegexp = " + findPeriodRegexp);
			List<String> period = Tools.subStrings(str, findPeriodRegexp);
			List<String> endDate = new ArrayList();
			for(String s : period){
				endDate.addAll(Tools.subStrings(s, "<td.*?>|</td>"));
			}
			
			for(String s : endDate){
				//System.out.println("报告日期 = " + s);
				info = new ReportInfoDTO();
				info.setBeginDate(Tools.getDate(s.substring(0,4) + "-01-01"));
				info.setEndDate(Tools.getDate(s));
				info.setPeriodType("期末");
				info.setDescription(s);
				list.add(info);
			}
		}
		return list;
	}
	
	public static String findTdValue(String str, int i){
		String regexp = "<td.*?>|</td>";
		List<String> tds = Tools.subStrings(str, regexp);
		if(i>tds.size()){
			System.out.println("********** error out of size, " +tds.size()+ " < " + i);
			return null;
		}else{
			return tds.get(i);
		}
	}

	public static int findTable(List<String> list, String key){
		int j = -1;
		for(int i=0; i<list.size(); i++){
			if(list.get(i).indexOf(key) != -1){
				j = i;
				//System.out.println(list.get(i));
				break;
			}
		}
		return j;
	}
	
	public static int findTd(String str,String key){
		int i = -1;
		String regexp = "<td.*?>|</td>";
		List<String> tds = Tools.subStrings(str, regexp);
		for(int j=0; j<tds.size(); j++){
			if(key.equals(tds.get(j))){
				i = j;
				break;
			}
		}
		return i;
	}



}
