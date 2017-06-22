package com.rhb.sas.download.mgsy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadMgsyFromEastmoney implements DownloadMgsy {
	private Sensor sensor;
	
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	@Override
	public List<MgsyDTO> doIt(String theyearandmonth, int thepage) {
		List<MgsyDTO> list = new ArrayList();
		HttpBrowser hb = new HttpBrowser();
		String url;
		String result;
		List<MgsyDTO> l;
		for(int i=1; i<=thepage; i++){
			sensor.setMessage("parse page " + i + "/" + thepage);
			url = "http://data.eastmoney.com/bbsj/theyearandmonth/yjbb/ggrq/thepage.html";
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
	private List<MgsyDTO> parseYjyg(String result){
		String table = getTable(result);
		List<String> trs = getTr(table);
		List<String> tds;
		MgsyDTO dto;
		List<MgsyDTO> list = new ArrayList();
		for(String tr : trs){
			tds = getTd(tr);
			dto = new MgsyDTO(getStockNo(tds.get(1)),getStockName(tds.get(2)),getMgsy(tds.get(4)),getMgsy(tds.get(5)),getMgsy(tds.get(8)));
			list.add(dto);
			sensor.setMessage("parse " + dto.toString());
		}
		return list;
	}
	
	private String getTable(String result){
		String findRegexp = "<tbody>|</tbody>";
		String s = Tools.subString(result, findRegexp);
		return s.replace("股吧</a>", "股吧</a></td>");
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

	private String getMgsy(String result){
		String findRegexp = "<span>|</span>";
		String s = Tools.subString(result, findRegexp);
		return s;
	}

	
	private String getStockName(String result){
		return getStockNo(result);
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
