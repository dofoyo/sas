package com.rhb.sas2.domain;

/**
 * ����ժҪ
 * @author Administrator
 *
 */
public class FinanceSummary{

	private double netAssetsPerShare = 0.0;  	//ÿ�ɾ��ʲ�
	private double earningsPerShare = 0.0;  	//ÿ������
	private double netCashFlowPerShare = 0.0;  	//ÿ���ֽ���
	private double capitalFundPerShare = 0.0;   //ÿ���ʱ�������
	
	public double getNetAssetsPerShare() {
		return netAssetsPerShare;
	}
	public void setNetAssetsPerShare(double netAssetsPerShare) {
		this.netAssetsPerShare = netAssetsPerShare;
	}
	public double getEarningsPerShare() {
		return earningsPerShare;
	}
	public void setEarningsPerShare(double earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}
	public double getNetCashFlowPerShare() {
		return netCashFlowPerShare;
	}
	public void setNetCashFlowPerShare(double netCashFlowPerShare) {
		this.netCashFlowPerShare = netCashFlowPerShare;
	}
	public double getCapitalFundPerShare() {
		return capitalFundPerShare;
	}
	public void setCapitalFundPerShare(double capitalFundPerShare) {
		this.capitalFundPerShare = capitalFundPerShare;
	}
	@Override
	public String toString() {
		return "FinanceSummary [netAssetsPerShare=" + netAssetsPerShare
				+ ", earningsPerShare=" + earningsPerShare
				+ ", netCashFlowPerShare=" + netCashFlowPerShare
				+ ", capitalFundPerShare=" + capitalFundPerShare + "]";
	}
	
	
}
