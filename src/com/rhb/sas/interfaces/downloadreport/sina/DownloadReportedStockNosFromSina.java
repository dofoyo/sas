package com.rhb.sas.interfaces.downloadreport.sina;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.interfaces.downloadreport.DownloadReportedStockNos;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadReportedStockNosFromSina implements
		DownloadReportedStockNos {
	private Sensor sensor;
	
	@Override
	public List<String> getReportedStockNo(Date reportDate, Date issueDate_begin,Date issueDate_end) {
		List<String> results = getPages(reportDate);
		List<String> stockNos = parseStockNo(results, issueDate_begin,issueDate_end);
		return stockNos;
	}
	
	private List<String> parseStockNo(List<String> results, Date issueDate_begin, Date issueDate_end){
		List<String> list = new ArrayList();
		
		Map<String, String> stockNos = new HashMap(); 
		for(String str : results){
			String table = getTable(str);
			List<String> trs = getTrs(table);
			for(String tr : trs){
				List<String> tds = getTds(tr);
				if(tds.size()>3){
					//System.out.println(tds);
					stockNos.put(getStockNo(tds.get(1)), tds.get(3));
				}
			}
		}
		
		//System.out.println(stockNos);
		
		Iterator i = stockNos.keySet().iterator();
		String stockNo;
		String issueDate;
		Date date;
		while(i.hasNext()){
			stockNo = (String)i.next();
			issueDate = (String)stockNos.get(stockNo);
			date = Tools.getDate(issueDate);
			if(date!=null && date.compareTo(issueDate_begin)>=0 && date.compareTo(issueDate_end)<=0){
				list.add(stockNo);
			}
		}
		
		//System.out.println(list);
		
		return list;
	}
	
	private String getStockNo(String str){
		String find = ">|</a>";
		return Tools.subString(str, find);
	}
	
	private List<String> getTds(String str){
		String findTd = "<td>|</td>";
		return Tools.subStrings(str, findTd);
	}
	
	
	private List<String> getTrs(String str){
		List<String> trs = new ArrayList();
		String findTr = "<tr>|</tr>";
		String findTr_ = "<tr style='background:#F1F6FC;'>|</tr>";
		trs.addAll(Tools.subStrings(str, findTr));
		trs.addAll(Tools.subStrings(str, findTr_));
		return trs;
	}
	
	private String getTable(String str){
		String findTable = "<table>|</table>";
		return Tools.subString(str, findTable);
	}
	
	
	private List<String> getPages(Date reportDate){
		String reportDate_str = Tools.getDate(reportDate, "yyyy-MM-dd");
		
		//String url = "http://finance.sina.com.cn/realstock/income_statement/reportDate/issued_predict_date_de_pageNo.html";
		String url = "http://finance.sina.com.cn/realstock/income_statement/reportDate/issued_pdate_ac_pageNo.html";
		url = url.replace("reportDate", reportDate_str);
		
		//String findNoRegexp = "<a href='http://finance.sina.com.cn/realstock/company/|/nc.shtml' target='_blank'>";
		
		List<String> results = new ArrayList();
		HttpBrowser hb = new HttpBrowser();
		try {
			for(int i=1; i<20; i++){
				String urlWithPageNo = url.replace("pageNo", Integer.toString(i));	
				results.add(hb.Browser(urlWithPageNo));
			}
		} catch (Exception e) {
			sensor.setMessage("********** error(it's maybe ok) **********");
			sensor.setMessage(e.toString());
			//e.printStackTrace();
		}
		//System.out.println(results);
		return results;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}
