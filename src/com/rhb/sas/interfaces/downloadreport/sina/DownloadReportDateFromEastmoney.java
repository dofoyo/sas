package com.rhb.sas.interfaces.downloadreport.sina;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.rhb.af.business.FindBusiness;
import com.rhb.sas.report.bean.Report;
import com.rhb.sas.report.bean.ReportQuery;
import com.rhb.sas.report.business.ReportBusiness;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadReportDateFromEastmoney {
	private FindBusiness fb;
	private ReportBusiness rb;

	public void setFb(FindBusiness fb) {
		this.fb = fb;
	}

	public void setRb(ReportBusiness rb) {
		this.rb = rb;
	}

	public void doIt(){
		String url;
		//String[] reportDates = {"201212","201112","201012","200912","200812","200712"};
		//String[] pages = {"27","26","23","20","15","14"};
		String[] reportDates = {"201012"};
		String[] pages = {"23"};
		String reportDate;
		String page;
		int ii = 0;
		int jj = 100*(27+26+23+20+15+14);
		for(int i=0; i<reportDates.length; i++){
		//for(int i=0; i<1; i++){
			reportDate = reportDates[i];
			page = pages[i];
			for(int j=1; j<=Integer.parseInt(page); j++){
			//for(int j=1; j<2; j++){
				url = "http://data.eastmoney.com/bbsj/REPORTDATE/yjbb/ggrq/PAGE.html"; 
				url = url.replace("REPORTDATE", reportDate);
				url = url.replace("PAGE", Integer.toString(j));
				//System.out.println(url);
				HttpBrowser hb = new HttpBrowser();
				try {
					Map<String,String> m = parse(hb.Browser(url));
					Iterator it = m.keySet().iterator();
					while(it.hasNext()){
						String stockNo = (String)it.next();
						ReportQuery rq = new ReportQuery();
						rq.setStockNo(stockNo);
						rq.setTheYear(reportDate.substring(0, 4));
						rq.setTheMonth(reportDate.substring(4));
						List<Report> reports = fb.findByQuery(rq).getList();
						if(reports!=null && reports.size()>0){
							Report r = reports.get(0);
							int year = Integer.parseInt(reportDate.substring(0, 4)) + 1;
							r.setDescription(Integer.toString(year)+"-"+m.get(stockNo));
							//System.out.println(r);
							rb.save(r);
						}
						ii++;
						System.out.println(ii + "/" + jj + ", " + stockNo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	
	
	
	private Map<String,String> parse(String html){
		Map<String,String> m = new LinkedHashMap();
		String table = getTable(html);
		List<String> trs = getTrs(table);
		for(String tr : trs){
			List<String> tds = getTds(tr);
			String stockNo = getStockNo(tds.get(1));
			String date = getDate(tds.get(15));
			m.put(stockNo, date);
		}
		
		return m;
	}

	private String getStockNo(String str){
		String find = "<a href=.*?>|</a>";
		return Tools.subString(str, find);
	}

	private String getDate(String str){
		String find = "<nobr>|</nobr>";
		return Tools.subString(str, find);
	}	
	
	private List<String> getTds(String str){
		String s = str.replace("¹É°É</a>", "¹É°É</a></td>");
		String findTd = "<td.*?>|</td>";
		return Tools.subStrings(str, findTd);
	}
	
	
	private List<String> getTrs(String str){
		List<String> trs = new ArrayList();
		String findTr = "<tr.*?>|</tr>";
		//String findTr_ = "<tr class=\"odd\">|</tr>";
		trs.addAll(Tools.subStrings(str, findTr));
		//trs.addAll(Tools.subStrings(str, findTr_));
		return trs;
	}
	
	private String getTable(String str){
		String findTable = "<tbody>|</tbody>";
		return Tools.subString(str, findTable);
	}

	
}
