package com.rhb.sas.interfaces.downloadreport.dto;



public class FinanceSummaryDTO  extends ReportInfoDTO{

	private double netAssetsPerShare = 0.0;  //财务摘要.每股净资产
	private double earningsPerShare = 0.0;  //财务摘要.每股收益
	private double netCashFlowPerShare = 0.0;  //财务摘要.每股现金含量
	private double capitalFundPerShare = 0.0;   //财务摘要.每股资本公积金
	
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
		sb.append(",每股净资产=" +	netAssetsPerShare);
		sb.append(",每股收益=" +	earningsPerShare);
		sb.append(",每股现金含量=" +	netCashFlowPerShare);
		sb.append(",每股资本公积金=" +	capitalFundPerShare);
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
