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
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadReportDateFromEastmoneyOneByOne {
	private FindBusiness fb;
	private ReportBusiness rb;

	public void setFb(FindBusiness fb) {
		this.fb = fb;
	}

	public void setRb(ReportBusiness rb) {
		this.rb = rb;
	}

	public void doIt(){
		String uri = "http://data.eastmoney.com/bbsj/STOCKNO/yjbb.html"; 
				
		StockQuery sq = new StockQuery();
		sq.empty();
		//sq.setStockNo("600794");
		sq.setCount(100000);
		List<Stock> stocks = fb.findByQuery(sq).getList();
		Stock stock;
		HttpBrowser hb = new HttpBrowser();
		for(int i=0; i<stocks.size(); i++){
			stock = (Stock)stocks.get(i);
			System.out.println(i + "/" + stocks.size() + ", " + stock.getStockNo());
			String u = uri.replace("STOCKNO", stock.getStockNo());
			Map<String, String> m;
			try {
				m = parse(hb.Browser(u));
				Iterator it = m.keySet().iterator();
				while(it.hasNext()){
					String reportDate = (String)it.next();
					String theYear = reportDate.substring(0, 4);
					String theYear_1 = Integer.toString(Integer.parseInt(theYear) + 1);
					String theMonth = reportDate.substring(5, 7);
					String issueDate = theMonth.equals("12") ? theYear_1+"-"+ m.get(reportDate) : theYear+"-"+ m.get(reportDate);
					
					System.out.println("reportDate=" + reportDate + ",issueDate=" + issueDate);
					
					ReportQuery rq = new ReportQuery();
					rq.setStockNo(stock.getStockNo());
					rq.setTheYear(reportDate.substring(0, 4));//2013-09-30
					rq.setTheMonth(reportDate.substring(5, 7));
					List<Report> reports = fb.findByQuery(rq).getList();
					if(reports!=null && reports.size()>0){
						Report r = reports.get(0);
						r.setDescription(issueDate);
						//System.out.println(r);
						rb.save(r);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	private Map<String,String> parse(String html){
		Map<String,String> m = new LinkedHashMap();
		String table = getTable(html);
		List<String> trs = getTrs(table);
		for(String tr : trs){
			List<String> tds = getTds(tr);
			String reportDate = tds.get(1);
			String issueDate = getIssueDate(tds.get(17));
			m.put(reportDate, issueDate);
		}
		
		return m;
	}

	private String getIssueDate(String str){
		String find = "<nobr>|</nobr>";
		return Tools.subString(str, find);
	}	
	
	private List<String> getTds(String str){
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
