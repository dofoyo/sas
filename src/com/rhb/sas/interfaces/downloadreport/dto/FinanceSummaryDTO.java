package com.rhb.sas.interfaces.downloadreport.dto;



public class FinanceSummaryDTO  extends ReportInfoDTO{

	private double netAssetsPerShare = 0.0;  //����ժҪ.ÿ�ɾ��ʲ�
	private double earningsPerShare = 0.0;  //����ժҪ.ÿ������
	private double netCashFlowPerShare = 0.0;  //����ժҪ.ÿ���ֽ���
	private double capitalFundPerShare = 0.0;   //����ժҪ.ÿ���ʱ�������
	
	public FinanceSummaryDTO(ReportInfoDTO dto){
		this.setBeginDate(dto.getBeginDate());
		this.setDescription(dto.getDescription());
		this.setEndDate(dto.getEndDate());
		this.setPeriodType(dto.getPeriodType());
		this.setStockName(dto.getStockName());
		this.setStockNo(dto.getStockNo());
	}

	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append(",ÿ�ɾ��ʲ�=" +	netAssetsPerShare);
		sb.append(",ÿ������=" +	earningsPerShare);
		sb.append(",ÿ���ֽ���=" +	netCashFlowPerShare);
		sb.append(",ÿ���ʱ�������=" +	capitalFundPerShare);
		return sb.toString();
	}

	
	public double getNetAssetsPerShare() {
		return netAssetsPerShare;
	}
	public void setNetAssetsPerShare(double d) {
		netAssetsPerShare = d;
	}
	public double getEarningsPerShare() {
		return earningsPerShare;
	}
	public void setEarningsPerShare(double d) {
		earningsPerShare = d;
	}
	public double getNetCashFlowPerShare() {
		return netCashFlowPerShare;
	}
	public void setNetCashFlowPerShare(double d) {
		netCashFlowPerShare = d;
	}
	public double getCapitalFundPerShare() {
		return capitalFundPerShare;
	}
	public void setCapitalFundPerShare(double d) {
		capitalFundPerShare = d;
	}


}
