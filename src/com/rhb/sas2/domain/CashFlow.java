package com.rhb.sas2.domain;

/**
 * 现金流量表
 * @author Administrator
 *
 */
public class CashFlow{
	private double purchaseAssets = 0.0;  		//购建固定资产、无形资产及其他长期资产所支付的现金
	private double depreciationAssets = 0.0; 	//固定资产折旧+无形资产摊销+递延资产摊销+长期待摊费用摊销
	private double netCashFlow = 0.0; 			//经营活动现金流量净额
	
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
