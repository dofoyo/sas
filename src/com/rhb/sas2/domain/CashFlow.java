package com.rhb.sas2.domain;

/**
 * �ֽ�������
 * @author Administrator
 *
 */
public class CashFlow{
	private double purchaseAssets = 0.0;  		//�����̶��ʲ��������ʲ������������ʲ���֧�����ֽ�
	private double depreciationAssets = 0.0; 	//�̶��ʲ��۾�+�����ʲ�̯��+�����ʲ�̯��+���ڴ�̯����̯��
	private double netCashFlow = 0.0; 			//��Ӫ��ֽ���������
	
	public double getPurchaseAssets() {
		return purchaseAssets;
	}
	public void setPurchaseAssets(double purchaseAssets) {
		this.purchaseAssets = purchaseAssets;
	}
	public double getDepreciationAssets() {
		return depreciationAssets;
	}
	public void setDepreciationAssets(double depreciationAssets) {
		this.depreciationAssets = depreciationAssets;
	}
	public double getNetCashFlow() {
		return netCashFlow;
	}
	public void setNetCashFlow(double netCashFlow) {
		this.netCashFlow = netCashFlow;
	}
	@Override
	public String toString() {
		return "CashFlow [purchaseAssets=" + purchaseAssets
				+ ", depreciationAssets=" + depreciationAssets
				+ ", netCashFlow=" + netCashFlow + "]";
	}
	
	
	
}
