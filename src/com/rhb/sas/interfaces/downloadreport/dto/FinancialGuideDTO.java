package com.rhb.sas.interfaces.downloadreport.dto;



public class FinancialGuideDTO  extends ReportInfoDTO{
	private double primeOperatingProfit = 0.0;  //财务指标.主营业务利润
	private double netProfitPlus = 0.0;  //财务指标.扣除非经常性损益的净利润
	
	public FinancialGuideDTO(ReportInfoDTO dto){
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
		sb.append(",主营业务利润=" + primeOperatingProfit);
		sb.append(",扣除非经常性损益的净利润=" + netProfitPlus);
		return sb.toString();
	}

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


	
}
