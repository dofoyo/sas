package com.rhb.sas.interfaces.downloadreport.sina;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.interfaces.downloadreport.DownloadReport;
import com.rhb.sas.interfaces.downloadreport.dto.CashFlowDTO;
import com.rhb.sas.interfaces.downloadreport.dto.FinanceSummaryDTO;
import com.rhb.sas.interfaces.downloadreport.dto.ProfitStatementDTO;
import com.rhb.sas.interfaces.downloadreport.dto.ReportInfoDTO;
import com.rhb.sas.report.bean.Report;
import com.rhb.sas.util.FileUtil;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadFinanceSummaryFromSina implements DownloadReport {
	private Sensor sensor;
	private String path = "d:\\stocks\\stockNo_FinanceSummary.html";

	@Override
	public List doIt(String stockNo, java.util.Date reportDate) {
		//System.out.println("*** DownloadFinanceSummaryFromSina ***");
		return downloadFinanceSummaryByFile(stockNo,reportDate);
	}

	private List<FinanceSummaryDTO> downloadFinanceSummaryByFile(String stockNo, java.util.Date reportDate){
		List<FinanceSummaryDTO> list = new ArrayList();
		String url = path.replace("stockNo", stockNo);
		try {
			String result = FileUtil.readTextFile(url);
			List<ReportInfoDTO> ris = getReportInfo(result, reportDate);
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				FinanceSummaryDTO dto = parseFinanceSummary(result,ri); 
				if(dto != null){
					list.add(dto);					
				}
			}
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.toString());
			//e.printStackTrace();
		}

		return list;
	}
	
	private List<FinanceSummaryDTO> downloadFinanceSummaryByWeb(String stockNo, java.util.Date reportDate){
		List<FinanceSummaryDTO> list = new ArrayList();
		String url = "http://money.finance.sina.com.cn/corp/go.php/vFD_FinanceSummary/stockid/stockNo.phtml"; // 财务摘要
		url = url.replace("stockNo", stockNo);
		HttpBrowser hb = new HttpBrowser();
		try {
			String result = hb.Browser(url);
			List<ReportInfoDTO> ris = getReportInfo(result, reportDate);
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				FinanceSummaryDTO dto = parseFinanceSummary(result,ri); 
				if(dto != null){
					list.add(dto);					
				}
			}
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.toString());
			//e.printStackTrace();
		}

		return list;
	}

	private List<ReportInfoDTO> getReportInfo(String str, java.util.Date reportDate){
		List<ReportInfoDTO> list = new ArrayList();

		ReportInfoDTO info;
		if(reportDate != null){
			info = new ReportInfoDTO();
			info = new ReportInfoDTO();
			info.setBeginDate(Tools.getDate(Tools.getDate(reportDate, "yyyy") + "-01-01"));
			info.setEndDate(reportDate);
			info.setPeriodType("期末");
			info.setDescription(Tools.getDate(reportDate, "yyyy-MM-dd"));
			list.add(info);
		}else{		
			String findRegexp = "<strong>截止日期</strong></td>	  	<td align=\"left\" class=\"tdr\"><strong>|</strong>";
			List<String> l = Tools.subStrings(str, findRegexp);
			for(String s : l){
				info = new ReportInfoDTO();
				info.setBeginDate(Tools.getDate(s.substring(0,4) + "-01-01"));
				info.setEndDate(Tools.getDate(s));
				info.setPeriodType("期末");
				info.setDescription(s);
				list.add(info);
				//System.out.println(info);
			}
		}
		return list;
	}
	
	//解析财务摘要
	private FinanceSummaryDTO parseFinanceSummary(String str,ReportInfoDTO report) {			
		FinanceSummaryDTO fs = new FinanceSummaryDTO(report);
		
		String findRegexp = "<strong>截止日期.*?分割数据的空行";
		String findValueRegexp = "<td align=\"left\" class=\"tdr\">|</td>";
		String findValueRegexp1 = "<a target=\"_blank\" href=\"/corp/view/vFD_FinanceSummaryHistory.php?.*?\">|</a>";
		
		List<String> list = Tools.subStrings(str, findRegexp);
		List<String> values;
		if(list!=null && list.size()>0){
			int i = SinaTools.findTable(list,Tools.getDate(report.getEndDate(), "yyyy-MM-dd"));
			if(i == -1){
				sensor.setMessage("******* parseLatestSummary,  " + Tools.getDate(report.getEndDate(), "yyyy-MM-dd") + " dose NOT exist! ****");
				return null;
			}
			
			//System.out.println("***** endDate = " + Tools.getDate(report.getEndDate(), "yyyy-MM-dd") + ", i=" + i);
			
			values = Tools.subStrings(list.get(i), findValueRegexp);
			if(values!=null && values.size()>0){
				String netAssetsPerShare = values.get(2).replaceAll("万元|元", "");
				String earningsPerShare = values.get(3).replaceAll("万元|元", "");
				String netCashFlowPerShare = values.get(4).replaceAll("万元|元", "");
				String capitalFundPerShare = values.get(5).replaceAll("万元|元", "");
				capitalFundPerShare = capitalFundPerShare.replace("&nbsp;", ""); 
				
				if(netAssetsPerShare.indexOf("</a>") != -1){
					netAssetsPerShare = Tools.subString(netAssetsPerShare, findValueRegexp1);
					earningsPerShare = Tools.subString(earningsPerShare, findValueRegexp1);
					netCashFlowPerShare = Tools.subString(netCashFlowPerShare, findValueRegexp1);
					capitalFundPerShare = Tools.subString(capitalFundPerShare, findValueRegexp1);					
				}
				
				fs.setNetAssetsPerShare(Tools.getDouble(netAssetsPerShare));
				fs.setEarningsPerShare(Tools.getDouble(earningsPerShare));
				fs.setNetCashFlowPerShare(Tools.getDouble(netCashFlowPerShare));
				fs.setCapitalFundPerShare(Tools.getDouble(capitalFundPerShare));
			}			
		}
		
		//System.out.println(report.getFinanceSummary());
		//System.out.println(fs);
		return fs;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}
