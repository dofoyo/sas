package com.rhb.sas.interfaces.downloadreport.sina;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.interfaces.downloadreport.DownloadReport;
import com.rhb.sas.interfaces.downloadreport.dto.BalanceSheetDTO;
import com.rhb.sas.interfaces.downloadreport.dto.ReportInfoDTO;
import com.rhb.sas.util.FileUtil;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadBalanceSheetFromSina implements DownloadReport {
	private Sensor sensor;
	private String path = "d:\\stocks\\stockNo_BalanceSheet.html";
	
	public List doIt(String stockNo, java.util.Date reportDate){
		//System.out.println("*** DownloadBalanceSheetFromSina ***");
		return downloadBalanceSheetByFile(stockNo, reportDate);
	}

	public List<BalanceSheetDTO> downloadBalanceSheetByFile(String stockNo, java.util.Date reportDate) {
		List<BalanceSheetDTO> list = new ArrayList();
		String url = path.replace("stockNo", stockNo);
		try {
			String result = FileUtil.readTextFile(url);
			//System.out.println(result);
			List<ReportInfoDTO> ris = SinaTools.getReportInfo(result, reportDate);
			//System.out.println("ris.size() = " + ris.size());
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				BalanceSheetDTO dto = parseBalanceSheet(result,ri); 
				if(dto != null){
					list.add(dto);					
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.toString());
		}

		return list;
	}
	
	public List<BalanceSheetDTO> downloadBalanceSheetByWeb(String stockNo, java.util.Date reportDate) {
		List<BalanceSheetDTO> list = new ArrayList();
		String url = "http://money.finance.sina.com.cn/corp/go.php/vFD_BalanceSheet/stockid/stockNo/ctrl/part.phtml"; // 资产负债表
		url = url.replace("stockNo", stockNo);
		HttpBrowser hb = new HttpBrowser();
		try {
			String result = hb.Browser(url);
			//System.out.println(result);
			List<ReportInfoDTO> ris = SinaTools.getReportInfo(result, reportDate);
			//System.out.println("ris.size() = " + ris.size());
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				BalanceSheetDTO dto = parseBalanceSheet(result,ri); 
				if(dto != null){
					list.add(dto);					
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.toString());
		}

		return list;
	}

	// 解析资产负债表
	private BalanceSheetDTO parseBalanceSheet(String str,ReportInfoDTO report) throws Exception{	
		BalanceSheetDTO dto = new BalanceSheetDTO(report);

		String findPeriodRegexp = null;
		if(str.indexOf("报告期") != -1){
			findPeriodRegexp = "报告期</strong></td>|</tr>";			
		}else if(str.indexOf("公告期") != -1){
			findPeriodRegexp = "公告期</strong></td>|</tr>";			
		}else if(str.indexOf("报表日期") != -1){
			findPeriodRegexp = "报表日期</strong></td>|</tr>";			
		}else{
			throw new Exception("can not fix the findperiodregexp");
		}
		
		/*
		String findPeriodRegexp = "报表日期</strong></td>|</tr>";
		
		if(str.indexOf("报告期") != -1){
			findPeriodRegexp = "报告期</strong></td>|</tr>";			
		}
		if(str.indexOf("公告期") != -1){
			findPeriodRegexp = "公告期</strong></td>|</tr>";			
		}
		
		*/
		List<String> period = Tools.subStrings(str, findPeriodRegexp);
		int i = SinaTools.findTable(period,Tools.getDate(report.getEndDate(), "yyyy-MM-dd"));
		if(i == -1){
			sensor.setMessage("******* parseBalanceSheet,  " + Tools.getDate(report.getEndDate(), "yyyy-MM-dd") + " dose NOT exist! ****");
			//System.out.println("******* parseBalanceSheet,  " + Tools.getDate(report.getEndDate(), "yyyy-MM-dd") + " dose NOT exist! ****");
			return null;
		}
		int j = SinaTools.findTd(period.get(i),Tools.getDate(report.getEndDate(), "yyyy-MM-dd"));
		
		String findCashRegexp = "货币资金</a></td>|</tr>";
		String findInventoriesRegexp = "存货</a></td>|</tr>";
		String findAccountsRreceivableRegexp = "应收账款</a></td>|</tr>";
		String findNotesReceivableRegexp = "应收票据</a></td>|</tr>";
		String findOtherReceivableRegexp = "其他应收款</a></td>|</tr>";
		String findTotalCurrentAssetsRegexp = "流动资产合计</a></td>|</tr>";
		String findPayablesRegexp = "预收款项</a></td>|</tr>";
		String findCurrentLiabilitiesRegexp = "流动负债合计</a></td>|</tr>";
		String findTotalAssetsRegexp = "资产总计</a></td>|</tr>";
		String findTotalLiabilitiesRegexp = ">负债合计</a></td>|</tr>";

		List<String> cash = Tools.subStrings(str, findCashRegexp);
		List<String> inventories = Tools.subStrings(str, findInventoriesRegexp);
		List<String> accountsRreceivable = Tools.subStrings(str, findAccountsRreceivableRegexp);
		List<String> notesReceivable = Tools.subStrings(str, findNotesReceivableRegexp);
		List<String> otherReceivable = Tools.subStrings(str, findOtherReceivableRegexp);
		List<String> totalCurrentAssets = Tools.subStrings(str, findTotalCurrentAssetsRegexp);
		List<String> payables = Tools.subStrings(str, findPayablesRegexp);
		List<String> currentLiabilities = Tools.subStrings(str, findCurrentLiabilitiesRegexp);
		List<String> totalAssets = Tools.subStrings(str, findTotalAssetsRegexp);
		List<String> totalLiabilities = Tools.subStrings(str, findTotalLiabilitiesRegexp);
		
		
		if (cash!=null && cash.size()>0) {
			dto.setCash(Tools.getDouble(SinaTools.findTdValue(cash.get(i), j)));
		}
		if(inventories!=null && inventories.size()>0){
			dto.setInventories(Tools.getDouble(SinaTools.findTdValue(inventories.get(i),j)));
		}
		if (accountsRreceivable!=null && accountsRreceivable.size()>0) {
			dto.setAccountsRreceivable(Tools.getDouble(SinaTools.findTdValue(
					accountsRreceivable.get(i), j)));
		}
		if(notesReceivable!=null && notesReceivable.size()>0){
			dto.setNotesReceivable(Tools.getDouble(SinaTools.findTdValue(notesReceivable.get(i),j)));
		}
		if (otherReceivable!=null && otherReceivable.size()>0) {
			dto.setOtherReceivable(Tools.getDouble(SinaTools.findTdValue(
					otherReceivable.get(i), j)));
		}
		if(totalCurrentAssets!=null && totalCurrentAssets.size()>0){
			dto.setTotalCurrentAssets(Tools.getDouble(SinaTools.findTdValue(totalCurrentAssets.get(i),j)));
		}
		if(payables!=null && payables.size()>0){
			dto.setPayables(Tools.getDouble(SinaTools.findTdValue(payables.get(i),j)));
		}
		if(currentLiabilities!=null && currentLiabilities.size()>0){
			dto.setCurrentLiabilities(Tools.getDouble(SinaTools.findTdValue(currentLiabilities.get(i),j)));
		}
		if (totalAssets!=null && totalAssets.size()>0) {
			dto.setTotalAssets(Tools.getDouble(SinaTools.findTdValue(
					totalAssets.get(i), j)));
		}
		if (totalLiabilities!=null && totalLiabilities.size()>0) {
			dto.setTotalLiabilities(Tools.getDouble(SinaTools.findTdValue(
					totalLiabilities.get(i), j)));
		}
		//System.out.println("***************");
		//System.out.println(dto);
		return dto;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}
