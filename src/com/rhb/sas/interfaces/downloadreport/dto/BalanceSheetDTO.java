package com.rhb.sas.interfaces.downloadreport.dto;


public class BalanceSheetDTO extends ReportInfoDTO{
	private Double cash = 0.0;       				//�ʲ���ծ��.�����ʽ�
	private Double inventories = 0.0;  			//�ʲ���ծ��.�������
	private Double accountsRreceivable = 0.0; 	//�ʲ���ծ��.Ӧ���˿��
	private Double notesReceivable = 0.0;  		//�ʲ���ծ��.Ӧ��Ʊ��
	private Double otherReceivable = 0.0 ;  		//�ʲ���ծ��.����Ӧ�տ��
	private Double totalCurrentAssets = 0.0;  	//�ʲ���ծ��.�����ʲ��ϼ�
	private Double payables = 0.0;      			//�ʲ���ծ��.Ԥ���ʿ�
	private Double currentLiabilities = 0.0;  	//�ʲ���ծ��.������ծ�ϼ�
	private Double totalAssets = 0.0;   			//�ʲ���ծ��.�ʲ��ܼ�
	private Double totalLiabilities = 0.0 ;		//�ʲ���ծ��.��ծ�ϼ�
	
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
		sb.append(",�����ʽ�=" +	cash);
		sb.append(",�������=" + inventories);
		sb.append(",Ӧ���˿��=" + accountsRreceivable);
		sb.append(",Ӧ��Ʊ��=" + notesReceivable);
		sb.append(",����Ӧ�տ��=" + otherReceivable);
		sb.append(",�����ʲ��ϼ�=" + totalCurrentAssets);
		sb.append(",Ԥ���ʿ�=" + payables);
		sb.append(",������ծ�ϼ�=" + currentLiabilities);
		sb.append(",�ʲ��ܼ�=" + totalAssets);
		sb.append(",��ծ�ϼ�=" + totalLiabilities);
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
