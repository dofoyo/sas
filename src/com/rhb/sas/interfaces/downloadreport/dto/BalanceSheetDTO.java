package com.rhb.sas.interfaces.downloadreport.dto;


public class BalanceSheetDTO extends ReportInfoDTO{
	private Double cash = 0.0;       				//资产负债表.货币资金
	private Double inventories = 0.0;  			//资产负债表.存货净额
	private Double accountsRreceivable = 0.0; 	//资产负债表.应收账款净额
	private Double notesReceivable = 0.0;  		//资产负债表.应收票据
	private Double otherReceivable = 0.0 ;  		//资产负债表.其他应收款净额
	private Double totalCurrentAssets = 0.0;  	//资产负债表.流动资产合计
	private Double payables = 0.0;      			//资产负债表.预收帐款
	private Double currentLiabilities = 0.0;  	//资产负债表.流动负债合计
	private Double totalAssets = 0.0;   			//资产负债表.资产总计
	private Double totalLiabilities = 0.0 ;		//资产负债表.负债合计
	
	public BalanceSheetDTO(ReportInfoDTO dto){
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
		sb.append(",货币资金=" +	cash);
		sb.append(",存货净额=" + inventories);
		sb.append(",应收账款净额=" + accountsRreceivable);
		sb.append(",应收票据=" + notesReceivable);
		sb.append(",其他应收款净额=" + otherReceivable);
		sb.append(",流动资产合计=" + totalCurrentAssets);
		sb.append(",预收帐款=" + payables);
		sb.append(",流动负债合计=" + currentLiabilities);
		sb.append(",资产总计=" + totalAssets);
		sb.append(",负债合计=" + totalLiabilities);
		return sb.toString();
	}
	
	public Double getCash() {
		return cash;
	}
	public void setCash(Double cash) {
		this.cash = cash;
	}
	public Double getInventories() {
		return inventories;
	}
	public void setInventories(Double inventories) {
		this.inventories = inventories;
	}
	public Double getAccountsRreceivable() {
		return accountsRreceivable;
	}
	public void setAccountsRreceivable(Double accountsRreceivable) {
		this.accountsRreceivable = accountsRreceivable;
	}
	public Double getNotesReceivable() {
		return notesReceivable;
	}
	public void setNotesReceivable(Double notesReceivable) {
		this.notesReceivable = notesReceivable;
	}
	public Double getOtherReceivable() {
		return otherReceivable;
	}
	public void setOtherReceivable(Double otherReceivable) {
		this.otherReceivable = otherReceivable;
	}
	public Double getTotalCurrentAssets() {
		return totalCurrentAssets;
	}
	public void setTotalCurrentAssets(Double totalCurrentAssets) {
		this.totalCurrentAssets = totalCurrentAssets;
	}
	public Double getPayables() {
		return payables;
	}
	public void setPayables(Double payables) {
		this.payables = payables;
	}
	public Double getCurrentLiabilities() {
		return currentLiabilities;
	}
	public void setCurrentLiabilities(Double currentLiabilities) {
		this.currentLiabilities = currentLiabilities;
	}
	public Double getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(Double totalAssets) {
		this.totalAssets = totalAssets;
	}
	public Double getTotalLiabilities() {
		return totalLiabilities;
	}
	public void setTotalLiabilities(Double totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	
	
}
