package com.rhb.sas.interfaces.downloadreport.sina;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import com.rhb.af.business.FindBusiness;
import com.rhb.sas.firm.Firm;
import com.rhb.sas.report.bean.Report;
import com.rhb.sas.report.bean.ReportQuery;
import com.rhb.sas.report.business.ReportBusiness;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadYjygFromEastmoney {
	private FindBusiness fb;
	private ReportBusiness rb;
	private Map<String, String> yjyg = new HashMap();
	private String fileName="C:\\yjyg.xls";

	public void setFb(FindBusiness fb) {
		this.fb = fb;
	}

	public void setRb(ReportBusiness rb) {
		this.rb = rb;
	}

	public void doIt(){
		String url;
		String[] reportDates = {"200803","200806","200809","200812","200903","200906","200909","200912","201003","201006","201009","201012","201103","201106","201109","201112","201203","201206","201209","201212","201303","201306","201309","201312"};
		String[] pages = {		"2",	"3",	"4",		"6",	"2",	"6",		"4",	"4",	"1",		"3",	"3",	"3",	"2",		"3",	"3",	"5",	"4",		"7",	"7",	"8",		"4",	"6",	"6",	"4"};
		//String[] reportDates = {"200803"};
		//String[] pages = {"2"};
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
				url = "http://data.eastmoney.com/bbsj/REPORTDATE/yjyg/down/PAGE.html"; 
				url = url.replace("REPORTDATE", reportDate);
				url = url.replace("PAGE", Integer.toString(j));
				//System.out.println(url);
				HttpBrowser hb = new HttpBrowser();
				try {
					Map<String,String> m = parse(hb.Browser(url),reportDate);
					Iterator it = m.keySet().iterator();
					while(it.hasNext()){
						String stockNo = (String)it.next();
						String date = m.get(stockNo);
						ii++;
						System.out.println(ii + "/" + jj + ", " + stockNo + ", " + date);
					}
					yjyg.putAll(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		writeToFile(yjyg,fileName);
	}
	
	private void writeToFile(Map<String,String> selectResult, String fileName){
		try {
			HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象  
			HSSFSheet sheet = wb.createSheet("sheet1");//建立新的sheet对象  
			int i=0;
			HSSFRow row_title = sheet.createRow((short)i++);//建立新行  
			row_title.createCell((short)0).setCellValue("stockNo");  
			row_title.createCell((short)1).setCellValue("stockName");
			row_title.createCell((short)2).setCellValue("reportDate");
			row_title.createCell((short)3).setCellValue("issueDate");

			Iterator<String> it = selectResult.keySet().iterator();
			while(it.hasNext()){
				String str = it.next();
				String issueDate = selectResult.get(str);
				String[] sss = str.split(",");
				HSSFRow row = sheet.createRow((short)i++);//建立新行  
				row.createCell((short)0).setCellValue(sss[0]);  
				row.createCell((short)1).setCellValue(sss[1]);
				row.createCell((short)2).setCellValue(sss[2]);
				row.createCell((short)3).setCellValue(issueDate);
			}
		
			FileOutputStream fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);//把Workbook对象输出到文件workbook.xls中  
			fileOut.close();  
			System.out.println("have write " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	
	
	private Map<String,String> parse(String html,String reportDate){
		Map<String,String> m = new LinkedHashMap();
		String table = getTable(html);
		List<String> trs = getTrs(table);
		for(String tr : trs){
			List<String> tds = getTds(tr);
			String stockNo = getStockNo(tds.get(1));
			String stockName = getStockNo(tds.get(2));
			String date = getDate(tds.get(8));
			m.put(stockNo+","+stockName+","+reportDate, date);
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
		String s = str.replace("股吧</a>", "股吧</a></td>");
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
