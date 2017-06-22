package com.rhb.sas.interfaces.downloadreport.sina;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.interfaces.downloadreport.DownloadReport;
import com.rhb.sas.interfaces.downloadreport.dto.FinancialGuideDTO;
import com.rhb.sas.interfaces.downloadreport.dto.ReportInfoDTO;
import com.rhb.sas.util.FileUtil;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadFinancialGuideFromSina implements DownloadReport {
	private Sensor sensor;
	private String path = "d:\\stocks\\stockNo_FinancialGuide.html";
	
	@Override
	public List doIt(String stockNo, java.util.Date reportDate) {
		//System.out.println("*** DownloadProfitStatementFromsina ***");
		return downloadFinancialGuideByFile(stockNo,reportDate);
	}
	private List<FinancialGuideDTO> downloadFinancialGuideByFile(String stockNo, java.util.Date reportDate){
		List<FinancialGuideDTO> list = new ArrayList();
		String url = path.replace("stockNo", stockNo);
		try {
			String result = FileUtil.readTextFile(url);
			List<ReportInfoDTO> ris = SinaTools.getReportInfo(result,reportDate);
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				FinancialGuideDTO dto = parseFinancialGuide(result,ri); 
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
	
	private List<FinancialGuideDTO> downloadFinancialGuideByWeb(String stockNo, java.util.Date reportDate){
		List<FinancialGuideDTO> list = new ArrayList();
		String url = "http://money.finance.sina.com.cn/corp/go.php/vFD_FinancialGuideLine/stockid/stockNo/displaytype/100.phtml"; // 财务指标
		//String url = "http://money.finance.sina.com.cn/corp/go.php/vFD_FinancialGuideLine/stockid/stockNo/displaytype/4.phtml";
		url = url.replace("stockNo", stockNo);
		HttpBrowser hb = new HttpBrowser();
		try {
			String result = hb.Browser(url);
			List<ReportInfoDTO> ris = SinaTools.getReportInfo(result,reportDate);
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				FinancialGuideDTO dto = parseFinancialGuide(result,ri); 
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

	// 解析财务指标
	private FinancialGuideDTO parseFinancialGuide(String str,ReportInfoDTO report) {
		str = str.replace("(元)", "");
		FinancialGuideDTO ps = new FinancialGuideDTO(report);

		String findPeriodRegexp = "报告日期</strong></td>|</tr>";

		List<String> period = Tools.subStrings(str, findPeriodRegexp);
		int i = SinaTools.findTable(period,Tools.getDate(report.getEndDate(), "yyyy-MM-dd"));
		if(i == -1){
			sensor.setMessage("******* parseFinancialGuide,  " + Tools.getDate(report.getEndDate(), "yyyy-MM-dd") + " dose NOT exist! ****");
			return null;
		}
		int j = SinaTools.findTd(period.get(i),Tools.getDate(report.getEndDate(), "yyyy-MM-dd"));
		
		//System.out.println("******* parseProfitStatement, i=" + i + ", j=" + j);

		//>主营业务利润(元)</a></td><td>39034813.56</td><td>217775389.58</td><td>138366021.85</td><td>100306957.73</td><td>--</td></tr>
		String strOfPrimeOperatingProfit = "主营业务利润";
		String findPrimeOperatingProfitRegexp = ">" + strOfPrimeOperatingProfit + "</a></td>|</tr>";
		List<String> primeOperatingProfitList = null;
		if(str.indexOf(strOfPrimeOperatingProfit) != -1){
			primeOperatingProfitList = Tools.subStrings(str, findPrimeOperatingProfitRegexp);
		}else{
			System.out.println("************* error, can not find '" + strOfPrimeOperatingProfit + "' ***********");
		}
		if(primeOperatingProfitList!=null && primeOperatingProfitList.size()>0){
			ps.setPrimeOperatingProfit(Tools.getDouble(SinaTools.findTdValue(primeOperatingProfitList.get(i),j)));			
		}
		
		String strOfNetProfitPlus = "扣除非经常性损益后的净利润";
		String findPrimeNetProfitPlusRegexp = strOfNetProfitPlus + "</a></td>|</tr>";
		List<String> netProfitPlusList = null;
		if(str.indexOf(strOfNetProfitPlus) != -1){
			netProfitPlusList = Tools.subStrings(str, findPrimeNetProfitPlusRegexp);
		}else{
			System.out.println("************* error, can not find '"+strOfNetProfitPlus+"' *******************");
		}
		if(netProfitPlusList!=null && netProfitPlusList.size()>0){
			ps.setNetProfitPlus(Tools.getDouble(SinaTools.findTdValue(netProfitPlusList.get(i),j)));
		}
		
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
