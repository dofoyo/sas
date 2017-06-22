package com.rhb.sas.interfaces.downloadreport.dto;


public class CashFlowDTO  extends ReportInfoDTO{
	private double purchaseAssets = 0.0;  //现金流量表.购建固定资产、无形资产及其他长期资产所支付的现金
	private double depreciationAssets = 0.0; //现金流量表.固定资产折旧+无形资产摊销+递延资产摊销+长期待摊费用摊销
	private double netCashFlow = 0.0; ////  经营活动现金流量净额
	
	public CashFlowDTO(ReportInfoDTO dto){
		this.setBeginDate(dto.getBeginDate());
		this.setDescription(dto.getDescription());
		this.setEndDate(dto.getEndDate());
		this.setPeriodType(dto.getPeriodType());
		this.setStockName(dto.getStockName());
		this.setStockNo(dto.getStockNo());
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()+ ",购建固定资产、无形资产及其他长期资产所支付的现金=" +
				purchaseAssets  + ",固定资产折旧=" +
				depreciationAssets + ",经营活动现金流量净额=" + netCashFlow
					);
		return sb.toString();
	}
	
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
}
