package com.rhb.sas2.domain;

/**
 * �����
 * @author Administrator
 *
 */
public class ProfitStatement{
	private double allOperatingRevenue = 0.0;  	//Ӫҵ����
	private double operatingRevenue = 0.0;  	//Ӫҵ����
	private double allOperatingCost = 0.0; 		//Ӫҵ�ɱ�
	private double operatingCost = 0.0; 		//Ӫҵ�ɱ�
	private double operatingExpense = 0.0;  	//�������
	private double salesExpense = 0.0; 			//���۷���
	private double financeExpense = 0.0; 		//�������
	private double tax = 0.0; 					//Ӫҵ˰�𼰸���
	
	public double getAllOperatingRevenue() {
		return allOperatingRevenue;
	}
	public void setAllOperatingRevenue(double allOperatingRevenue) {
		this.allOperatingRevenue = allOperatingRevenue;
	}
	public double getOperatingRevenue() {
		return operatingRevenue;
	}
	public void setOperatingRevenue(double operatingRevenue) {
		this.operatingRevenue = operatingRevenue;
	}
	public double getAllOperatingCost() {
		return allOperatingCost;
	}
	public void setAllOperatingCost(double allOperatingCost) {
		this.allOperatingCost = allOperatingCost;
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
	@Override
	public String toString() {
		return "ProfitStatement [allOperatingRevenue=" + allOperatingRevenue
				+ ", operatingRevenue=" + operatingRevenue
				+ ", allOperatingCost=" + allOperatingCost + ", operatingCost="
				+ operatingCost + ", operatingExpense=" + operatingExpense
				+ ", salesExpense=" + salesExpense + ", financeExpense="
				+ financeExpense + ", tax=" + tax + "]";
	}
	
}
