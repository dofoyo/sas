package com.rhb.sas.interfaces.downloadreport.dto;


public class CashFlowDTO  extends ReportInfoDTO{
	private double purchaseAssets = 0.0;  //�ֽ�������.�����̶��ʲ��������ʲ������������ʲ���֧�����ֽ�
	private double depreciationAssets = 0.0; //�ֽ�������.�̶��ʲ��۾�+�����ʲ�̯��+�����ʲ�̯��+���ڴ�̯����̯��
	private double netCashFlow = 0.0; ////  ��Ӫ��ֽ���������
	
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
		sb.append(super.toString()+ ",�����̶��ʲ��������ʲ������������ʲ���֧�����ֽ�=" +
				purchaseAssets  + ",�̶��ʲ��۾�=" +
				depreciationAssets + ",��Ӫ��ֽ���������=" + netCashFlow
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
