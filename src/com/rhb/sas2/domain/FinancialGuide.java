package com.rhb.sas2.domain;

/**
 * ����ָ��
 * @author Administrator
 *
 */
public class FinancialGuide{
	private double primeOperatingProfit = 0.0;  //��Ӫҵ������
	private double netProfitPlus = 0.0;  		//�۳��Ǿ���������ľ�����
	
	
	public double getPrimeOperatingProfit() {
		return primeOperatingProfit;
	}
	public void setPrimeOperatingProfit(double primeOperatingProfit) {
		this.primeOperatingProfit = primeOperatingProfit;
	}
	public double getNetProfitPlus() {
		return netProfitPlus;
	}
	public void setNetProfitPlus(double netProfitPlus) {
		this.netProfitPlus = netProfitPlus;
	}
	@Override
	public String toString() {
		return "FinancialGuide [primeOperatingProfit="
				+ primeOperatingProfit + ", netProfitPlus=" + netProfitPlus
				+ "]";
	}
	
}
