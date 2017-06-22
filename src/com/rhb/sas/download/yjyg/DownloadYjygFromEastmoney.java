package com.rhb.sas.download.yjyg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadYjygFromEastmoney implements DownloadYjyg {
	private Sensor sensor;
	
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	@Override
	public List<YjygDTO> doIt(String theyearandmonth, int thepage) {
		
		List<YjygDTO> list = new ArrayList();
		HttpBrowser hb = new HttpBrowser();
		String url;
		String result;
		List<YjygDTO> l;
		for(int i=1; i<=thepage; i++){
			sensor.setMessage("parse page " + i + "/" + thepage);
			url = "http://data.eastmoney.com/bbsj/theyearandmonth/yjyg/thepage.html";
			url = url.replace("theyearandmonth", theyearandmonth);
			url = url.replace("thepage", Integer.toString(i));
			sensor.setMessage(url);
			try{
				result = hb.Browser(url);
				list.addAll(parseYjyg(result));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private List<YjygDTO> parseYjyg(String result){
		String table = getTable(result);
		List<String> trs = getTr(table);
		List<String> tds;
		YjygDTO dto;
		List<YjygDTO> list = new ArrayList();
		for(String tr : trs){
			tds = getTd(tr);
			dto = new YjygDTO(getStockNo(tds.get(1)),getStockName(tds.get(2)),tds.get(6),getBdfd(tds.get(5)));
			list.add(dto);
			sensor.setMessage("parse " + dto.toString());
		}
		return list;
	}
	
	private String getTable(String result){
		String findRegexp = "<tbody>|</tbody>";
		String s = Tools.subString(result, findRegexp);
		return s;
	}

	private List<String> getTr(String result){
		String findRegexp = "<tr.*?>|</tr>";
		List<String> list = Tools.subStrings(result, findRegexp);
		return list;
	}
	
	private List<String> getTd(String result){
		String findRegexp = "<td.*?>|</td>";
		List<String> list = Tools.subStrings(result, findRegexp);
		return list;		
	}
	
	private String getStockNo(String result){
		String findRegexp = "<a.*?>|</a>";
		String s = Tools.subString(result, findRegexp);
		return s;
	}

	private String getStockName(String result){
		return getStockNo(result);
	}

	private String getBdfd(String result){
		String findRegexp = "<span class=\"red\">|</span>";
		String s = Tools.subString(result, findRegexp);
		return s;
	}
	
	private boolean isReportDate(String reportDate){
		  
		  if(reportDate == null){
			  return false;
		  }
		  
		  Date d = Tools.getDate(reportDate, "yyyyMM");
		  if(d == null){
			  return false;
		  }
		  return true;
		  
	  }

}
