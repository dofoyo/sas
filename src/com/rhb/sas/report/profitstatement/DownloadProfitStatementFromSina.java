package com.rhb.sas.report.profitstatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadProfitStatementFromSina implements DownloadProfitStatement {
	private Sensor sensor;

	@Override
	public List<ProfitStatement> down(String stockNo, Date reportDate) {
		List<ProfitStatement> list = null;
		String url = "http://money.finance.sina.com.cn/corp/go.php/vFD_ProfitStatement/stockid/stockNo/ctrl/part.phtml";
		url = url.replace("stockNo", stockNo);
		HttpBrowser hb = new HttpBrowser();
		try {
			String result = hb.Browser(url);
			//System.out.println(result);
			list = getReportInfo(result,reportDate);
			for(ProfitStatement ri : list){
				//System.out.println(ri);
				//System.out.println("######################");
				ri.setStockNo(stockNo);
				parseProfitStatement(result,ri); 
			}
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.toString());
			//e.printStackTrace();
		}

		return list;
	}

	// ���������
	private void parseProfitStatement(String str,ProfitStatement report) {			
		String findPeriodRegexp = "��������</strong></td>|</tr>";
		
		if(str.indexOf("��������") != -1){
			findPeriodRegexp = "��������</strong></td>|</tr>";			
		}
		if(str.indexOf("������") != -1){
			findPeriodRegexp = "������</strong></td>|</tr>";			
		}
		if(str.indexOf("������") != -1){
			findPeriodRegexp = "������</strong></td>|</tr>";			
		}
		
		List<String> period = Tools.subStrings(str, findPeriodRegexp);
		int i = findTable(period,Tools.getDate(report.getReportDate(), "yyyy-MM-dd"));
		if(i == -1){
			sensor.setMessage("******* parseProfitStatement,  " + Tools.getDate(report.getReportDate(), "yyyy-MM-dd") + " dose NOT exist! ****");
			return;
		}
		
		int j = findTd(period.get(i),Tools.getDate(report.getReportDate(), "yyyy-MM-dd"));
	
		//---------
		
		String strOfAllOperatingRevenue = "һ��Ӫҵ������";
		String findAllOperatingRevenueRegexp = strOfAllOperatingRevenue + "</a></td>|</tr>";
		List<String> allOperatingRevenueList = null;
		if(str.indexOf(strOfAllOperatingRevenue) != -1){
			allOperatingRevenueList = Tools.subStrings(str, findAllOperatingRevenueRegexp);
			report.setAllOperatingRevenue(Tools.getDouble(findTdValue(allOperatingRevenueList.get(i),j)));
		}else{
//			System.out.println("************* error, can not find '" + strOfAllOperatingRevenue + "' ***********");
		}

		String strOfAllOperatingCost = "����Ӫҵ�ܳɱ�";
		String findAllOperatingCostRegexp = strOfAllOperatingCost + "</a></td>|</tr>";
		List<String> allOperatingCostList = null;
		if(str.indexOf(strOfAllOperatingCost) != -1){
			allOperatingCostList = Tools.subStrings(str, findAllOperatingCostRegexp);
			report.setAllOperatingCost(Tools.getDouble(findTdValue(allOperatingCostList.get(i),j)));
		}else{
//			System.out.println("************* error, can not find '" + strOfAllOperatingCost + "' ***********");
		}

		String strOfOperatingRevenue = "Ӫҵ����";
		String findOperatingRevenueRegexp = strOfOperatingRevenue + "</a></td>|</tr>";
		List<String> operatingRevenueList = null;
		if(str.indexOf(strOfOperatingRevenue) != -1){
			operatingRevenueList = Tools.subStrings(str, findOperatingRevenueRegexp);
			report.setOperatingRevenue(Tools.getDouble(findTdValue(operatingRevenueList.get(i),j)));
		}else{
//			System.out.println("************* error, can not find '" + strOfOperatingRevenue + "' ***********");
		}

		String strOfOperatingCost = "Ӫҵ�ɱ�";
		if(str.indexOf("Ӫҵ֧��") != -1){
			strOfOperatingCost = "Ӫҵ֧��";			
		}
		String findOperatingCostRegexp = strOfOperatingCost + "</a></td>|</tr>";
		List<String> operatingCostList = null;
		if(str.indexOf(strOfOperatingCost) != -1){
			operatingCostList = Tools.subStrings(str, findOperatingCostRegexp);
			report.setOperatingCost(Tools.getDouble(findTdValue(operatingCostList.get(i),j)));
		}else{
//			System.out.println("************* error, can not find '" + strOfOperatingCost + "' ***********");
		}
		
		String findSalesExpenseRegexp = "���۷���</a></td>|</tr>"; 
		List<String> salesExpenseList = Tools.subStrings(str, findSalesExpenseRegexp);
		if(salesExpenseList!=null && salesExpenseList.size()>0){
			report.setSalesExpense(Tools.getDouble(findTdValue(salesExpenseList.get(i),j)));			
		}		
		
		String findOperatingExpenseRegexp = "�������</a></td>|</tr>";
		if(str.indexOf("ҵ�񼰹������") != -1){
			strOfAllOperatingCost = "ҵ�񼰹������";			
		}
		
		List<String> operatingExpenseList = Tools.subStrings(str, findOperatingExpenseRegexp);
		if(operatingExpenseList!=null && operatingExpenseList.size()>0){
			report.setOperatingExpense(Tools.getDouble(findTdValue(operatingExpenseList.get(i),j)));			
		}		
		
		String findFinanceExpenseRegexp = "�������</a></td>|</tr>"; 
		List<String> financeExpenseList = Tools.subStrings(str, findFinanceExpenseRegexp);
		if(financeExpenseList!=null && financeExpenseList.size()>0){
			report.setFinanceExpense(Tools.getDouble(findTdValue(financeExpenseList.get(i),j)));			
		}		

		String findTaxRegexp = "Ӫҵ˰�𼰸���</a></td>|</tr>"; 
		List<String> taxList = Tools.subStrings(str, findTaxRegexp);
		if(taxList!=null && taxList.size()>0){
			report.setTax(Tools.getDouble(findTdValue(taxList.get(i),j)));			
		}		
		
		//System.out.println(report.getProfitStatement());
		//System.out.println(ps);
		return;
	}

	
	
	public static List<ProfitStatement> getReportInfo(String str, java.util.Date reportDate) throws Exception{	
		List<ProfitStatement> list = new ArrayList();
		//System.out.println("********** reportDate = " + reportDate);
		ProfitStatement info;
		if(reportDate != null){
			info = new ProfitStatement();
			info.setTheYear(Tools.getDate(reportDate, "yyyy"));
			info.setTheMonth(Tools.getDate(reportDate, "MM"));
			info.setReportDate(reportDate);
			list.add(info);
		}else{		
			String findPeriodRegexp = null;
			if(str.indexOf("������") != -1){
				findPeriodRegexp = "������</strong></td>|</tr>";			
			}else if(str.indexOf("������") != -1){
				findPeriodRegexp = "������</strong></td>|</tr>";			
			}else if(str.indexOf("��������") != -1){
				findPeriodRegexp = "��������</strong></td>|</tr>";			
			}else if(str.indexOf("��������") != -1){
				findPeriodRegexp = "��������</strong></td>|</tr>";			
			}else{
				throw new Exception("can not fix the findperiodregexp");
			}
			//System.out.println("********** findPeriodRegexp = " + findPeriodRegexp);
			List<String> period = Tools.subStrings(str, findPeriodRegexp);
			List<String> theDate = new ArrayList();
			for(String s : period){
				theDate.addAll(Tools.subStrings(s, "<td.*?>|</td>"));
			}
			
			for(String s : theDate){
				info = new ProfitStatement();
				info.setReportDate(Tools.getDate(s));
				info.setTheYear(Tools.getDate(info.getReportDate(), "yyyy"));
				info.setTheMonth(Tools.getDate(info.getReportDate(), "MM"));
				list.add(info);
			}
		}
		return list;
	}
	
	public static String findTdValue(String str, int i){
		String regexp = "<td.*?>|</td>";
		List<String> tds = Tools.subStrings(str, regexp);
		if(i>tds.size()){
			System.out.println("********** error out of size, " +tds.size()+ " < " + i);
			return null;
		}else{
			return tds.get(i);
		}
	}

	public static int findTable(List<String> list, String key){
		int j = -1;
		for(int i=0; i<list.size(); i++){
			if(list.get(i).indexOf(key) != -1){
				j = i;
				//System.out.println(list.get(i));
				break;
			}
		}
		return j;
	}
	
	public static int findTd(String str,String key){
		int i = -1;
		String regexp = "<td.*?>|</td>";
		List<String> tds = Tools.subStrings(str, regexp);
		for(int j=0; j<tds.size(); j++){
			if(key.equals(tds.get(j))){
				i = j;
				break;
			}
		}
		return i;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}


	
}
