package com.rhb.sas.report.profitstatement;

import java.util.Date;

import com.rhb.af.annotation.Caption;
import com.rhb.af.bean.BaseBean;
import com.rhb.sas.util.Tools;

public class ProfitStatement extends BaseBean {
	@Caption("����") private String statementPk = "";
	@Caption("��Ʊ����") private String stockNo = "";
	@Caption("��Ʊ����") private String stockName = "";
	@Caption("���") private String theYear = "";
	@Caption("�·�") private String theMonth = "";
	@Caption("������") private Date reportDate = null;
	@Caption("Ӫҵ������") private double allOperatingRevenue = 0.0;  //�����.Ӫҵ����
	@Caption("Ӫҵ����") private double operatingRevenue = 0.0;  //�����.Ӫҵ����
	@Caption("Ӫҵ�ܳɱ�") private double allOperatingCost = 0.0; //�����.Ӫҵ�ɱ�
	@Caption("Ӫҵ�ɱ�") private double operatingCost = 0.0; //�����.Ӫҵ�ɱ�
	@Caption("�������") private double operatingExpense = 0.0;  //�����.�������
	@Caption("���۷���") private double salesExpense = 0.0; //�����.���۷���
	@Caption("�������") private double financeExpense = 0.0; //�����.�������
	@Caption("Ӫҵ˰��") private double tax = 0.0; //�����Ӫҵ˰�𼰸���
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("��Ʊ����:");
		sb.append(stockNo);
		sb.append(",");
		sb.append("��Ʊ����:");
		sb.append(stockName);
		sb.append(",");
		sb.append("���:");
		sb.append(theYear);
		sb.append(",");
		sb.append("�·�:");
		sb.append(theMonth);
		sb.append(",");
		sb.append("������:");
		sb.append(Tools.getDate(reportDate, "yyyy-MM-dd"));
		sb.append(",");
		sb.append("Ӫҵ������:");
		sb.append(allOperatingRevenue);
		sb.append(",");
		sb.append("Ӫҵ����:");
		sb.append(operatingRevenue);
		sb.append(",");
		sb.append("Ӫҵ�ܳɱ�:");
		sb.append(allOperatingCost);
		sb.append(",");
		sb.append("Ӫҵ�ɱ�:");
		sb.append(operatingCost);
		sb.append(",");
		sb.append("�������:");
		sb.append(operatingExpense);
		sb.append(",");
		sb.append("���۷���:");
		sb.append(salesExpense);
		sb.append(",");
		sb.append("�������:");
		sb.append(financeExpense);
		sb.append(",");
		sb.append("Ӫҵ˰��:");
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
