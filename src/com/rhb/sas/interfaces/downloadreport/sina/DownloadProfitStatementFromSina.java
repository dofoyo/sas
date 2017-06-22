package com.rhb.sas.interfaces.downloadreport.sina;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.interfaces.downloadreport.DownloadReport;
import com.rhb.sas.interfaces.downloadreport.dto.FinanceSummaryDTO;
import com.rhb.sas.interfaces.downloadreport.dto.ProfitStatementDTO;
import com.rhb.sas.interfaces.downloadreport.dto.ReportInfoDTO;
import com.rhb.sas.util.FileUtil;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadProfitStatementFromSina implements DownloadReport {
	private Sensor sensor;
	private String path = "d:\\stocks\\stockNo_ProfitStatement.html";
	
	@Override
	public List doIt(String stockNo, java.util.Date reportDate) {
		//System.out.println("*** DownloadProfitStatementFromsina ***");
		return downloadProfitStatmentByFile(stockNo,reportDate);
	}

	private List<ProfitStatementDTO> downloadProfitStatmentByFile(String stockNo, java.util.Date reportDate){
		List<ProfitStatementDTO> list = new ArrayList();
		String url = path.replace("stockNo", stockNo);
		try {
			String result = FileUtil.readTextFile(url);
			List<ReportInfoDTO> ris = SinaTools.getReportInfo(result,reportDate);
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				ProfitStatementDTO dto = parseProfitStatement(result,ri); 
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
	
	private List<ProfitStatementDTO> downloadProfitStatment(String stockNo, java.util.Date reportDate){
		List<ProfitStatementDTO> list = new ArrayList();
		String url = "http://money.finance.sina.com.cn/corp/go.php/vFD_ProfitStatement/stockid/stockNo/ctrl/part.phtml"; // 利润表
		url = url.replace("stockNo", stockNo);
		HttpBrowser hb = new HttpBrowser();
		try {
			String result = hb.Browser(url);
			List<ReportInfoDTO> ris = SinaTools.getReportInfo(result,reportDate);
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				ProfitStatementDTO dto = parseProfitStatement(result,ri); 
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

	// 解析利润表
	private ProfitStatementDTO parseProfitStatement(String str,ReportInfoDTO report) {			
		ProfitStatementDTO ps = new ProfitStatementDTO(report);

		String findPeriodRegexp = "报表日期</strong></td>|</tr>";
		
		if(str.indexOf("报告期") != -1){
			findPeriodRegexp = "报告期</strong></td>|</tr>";			
		}
		if(str.indexOf("公告期") != -1){
			findPeriodRegexp = "公告期</strong></td>|</tr>";			
		}

		List<String> period = Tools.subStrings(str, findPeriodRegexp);
		int i = SinaTools.findTable(period,Tools.getDate(report.getEndDate(), "yyyy-MM-dd"));
		if(i == -1){
			sensor.setMessage("******* parseProfitStatement,  " + Tools.getDate(report.getEndDate(), "yyyy-MM-dd") + " dose NOT exist! ****");
			return null;
		}
		int j = SinaTools.findTd(period.get(i),Tools.getDate(report.getEndDate(), "yyyy-MM-dd"));
		
		//一、营业总收入</a></td><td>316,048,000.000元</td><td>1,603,390,000.000元</td><td>1,098,300,000.000元</td><td>816,976,000.000元</td><td>793,604,000.000元</td></tr>
		String strOfPrimeOperatingRevenue = "一、营业收入";
		String findPrimeOperatingRevenueRegexp = strOfPrimeOperatingRevenue + "</a></td>|</tr>";
		String strOfPrimeOperatingRevenue1 = "一、营业总收入";
		String findPrimeOperatingRevenueRegexp1 = strOfPrimeOperatingRevenue1 + "</a></td>|</tr>";
		List<String> primeOperatingRevenueList = null;
		if(str.indexOf(strOfPrimeOperatingRevenue) != -1){
			primeOperatingRevenueList = Tools.subStrings(str, findPrimeOperatingRevenueRegexp);
		}else if(str.indexOf(strOfPrimeOperatingRevenue1) != -1){
			primeOperatingRevenueList = Tools.subStrings(str, findPrimeOperatingRevenueRegexp1);
		}else{
			System.out.println("************* error, can not find '" + strOfPrimeOperatingRevenue + "' or '" + strOfPrimeOperatingRevenue1 + "' ***********");
		}
		ps.setPrimeOperatingRevenue(Tools.getDouble(SinaTools.findTdValue(primeOperatingRevenueList.get(i),j)));
		
		String findOperatingProfitRegexp = "三、营业利润</a></td>|</tr>"; 
		List<String> operatingProfitList = Tools.subStrings(str, findOperatingProfitRegexp);
		if(operatingProfitList!=null && operatingProfitList.size()>0){
			ps.setOperatingProfit(Tools.getDouble(SinaTools.findTdValue(operatingProfitList.get(i),j)));			
		}		
		
		String strOfNetProfit = "净利润";
		String findNetProfitRegexp = strOfNetProfit + "</a></td>|</tr>";
		List<String> netProfitList = null;
		if(str.indexOf(strOfNetProfit) != -1){
			netProfitList = Tools.subStrings(str, findNetProfitRegexp);
		}else{
			System.out.println("************* error, can not find '"+strOfNetProfit+"' *******************");
		}
		if(netProfitList!=null && netProfitList.size()>0){
			ps.setNetProfit(Tools.getDouble(SinaTools.findTdValue(netProfitList.get(i),j)));
		}
		
		String findSalesExpenseRegexp = "销售费用</a></td>|</tr>"; 
		List<String> salesExpenseList = Tools.subStrings(str, findSalesExpenseRegexp);
		if(salesExpenseList!=null && salesExpenseList.size()>0){
			ps.setSalesExpense(Tools.getDouble(SinaTools.findTdValue(salesExpenseList.get(i),j)));			
		}		
		
		String findOperatingExpenseRegexp = "管理费用</a></td>|</tr>"; 
		List<String> operatingExpenseList = Tools.subStrings(str, findOperatingExpenseRegexp);
		if(operatingExpenseList!=null && operatingExpenseList.size()>0){
			ps.setOperatingExpense(Tools.getDouble(SinaTools.findTdValue(operatingExpenseList.get(i),j)));			
		}		
		
		String findFinanceExpenseRegexp = "财务费用</a></td>|</tr>"; 
		List<String> financeExpenseList = Tools.subStrings(str, findFinanceExpenseRegexp);
		if(financeExpenseList!=null && financeExpenseList.size()>0){
			ps.setFinanceExpense(Tools.getDouble(SinaTools.findTdValue(financeExpenseList.get(i),j)));			
		}		

		//System.out.println(report.getProfitStatement());
		//System.out.println(ps);
		return ps;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}
