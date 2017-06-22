package com.rhb.sas.report.profitstatement;

import java.util.Date;

import com.rhb.af.annotation.Caption;
import com.rhb.af.bean.BaseBean;
import com.rhb.sas.util.Tools;

public class ProfitStatement extends BaseBean {
	@Caption("编码") private String statementPk = "";
	@Caption("股票代码") private String stockNo = "";
	@Caption("股票名称") private String stockName = "";
	@Caption("年分") private String theYear = "";
	@Caption("月份") private String theMonth = "";
	@Caption("报告期") private Date reportDate = null;
	@Caption("营业总收入") private double allOperatingRevenue = 0.0;  //利润表.营业收入
	@Caption("营业收入") private double operatingRevenue = 0.0;  //利润表.营业收入
	@Caption("营业总成本") private double allOperatingCost = 0.0; //利润表.营业成本
	@Caption("营业成本") private double operatingCost = 0.0; //利润表.营业成本
	@Caption("管理费用") private double operatingExpense = 0.0;  //利润表.管理费用
	@Caption("销售费用") private double salesExpense = 0.0; //利润表.销售费用
	@Caption("财务费用") private double financeExpense = 0.0; //利润表.财务费用
	@Caption("营业税金") private double tax = 0.0; //利润表营业税金及附加
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("股票代码:");
		sb.append(stockNo);
		sb.append(",");
		sb.append("股票名称:");
		sb.append(stockName);
		sb.append(",");
		sb.append("年分:");
		sb.append(theYear);
		sb.append(",");
		sb.append("月份:");
		sb.append(theMonth);
		sb.append(",");
		sb.append("报告期:");
		sb.append(Tools.getDate(reportDate, "yyyy-MM-dd"));
		sb.append(",");
		sb.append("营业总收入:");
		sb.append(allOperatingRevenue);
		sb.append(",");
		sb.append("营业收入:");
		sb.append(operatingRevenue);
		sb.append(",");
		sb.append("营业总成本:");
		sb.append(allOperatingCost);
		sb.append(",");
		sb.append("营业成本:");
		sb.append(operatingCost);
		sb.append(",");
		sb.append("管理费用:");
		sb.append(operatingExpense);
		sb.append(",");
		sb.append("销售费用:");
		sb.append(salesExpense);
		sb.append(",");
		sb.append("财务费用:");
		sb.append(financeExpense);
		sb.append(",");
		sb.append("营业税金:");
		sb.append(tax);
		return sb.toString();
	}
	
	
	public String getStatementPk() {
		return statementPk;
	}
	public void setStatementPk(String statementPk) {
		this.statementPk = statementPk;
	}
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getTheYear() {
		return theYear;
	}
	public void setTheYear(String theYear) {
		this.theYear = theYear;
	}
	public String getTheMonth() {
		return theMonth;
	}
	public void setTheMonth(String theMonth) {
		this.theMonth = theMonth;
	}
	public double getOperatingRevenue() {
		return operatingRevenue;
	}
	public void setOperatingRevenue(double operatingRevenue) {
		this.operatingRevenue = operatingRevenue;
	}
	public double getOperatingCost() {
		return operatingCost;
	}
	public void setOperatingCost(double operatingCost) {
		this.operatingCost = operatingCost;
	}
	public double getOperatingExpense() {
		return operatingExpense;
	}
	public void setOperatingExpense(double operatingExpense) {
		this.operatingExpense = operatingExpense;
	}
	public double getSalesExpense() {
		return salesExpense;
	}
	public void setSalesExpense(double salesExpense) {
		this.salesExpense = salesExpense;
	}
	public double getFinanceExpense() {
		return financeExpense;
	}
	public void setFinanceExpense(double financeExpense) {
		this.financeExpense = financeExpense;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	
	public String requiredValidate() {
		return "";
	}
	
	public String outOfRangeValidate() {
		return "";
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}


	public double getAllOperatingRevenue() {
		return allOperatingRevenue;
	}


	public void setAllOperatingRevenue(double allOperatingRevenue) {
		this.allOperatingRevenue = allOperatingRevenue;
	}


	public double getAllOperatingCost() {
		return allOperatingCost;
	}


	public void setAllOperatingCost(double allOperatingCost) {
		this.allOperatingCost = allOperatingCost;
	}
	

}
