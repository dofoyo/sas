package com.rhb.sas2.domain;

/**
 * 财务指标
 * @author Administrator
 *
 */
public class FinancialGuide{
	private double primeOperatingProfit = 0.0;  //主营业务利润
	private double netProfitPlus = 0.0;  		//扣除非经常性损益的净利润
	
	
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
