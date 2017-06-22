package com.rhb.sas2.domain;

/**
 * 资产负债表
 * @author Administrator
 *
 */
public class BalanceSheet{
	private Double cash = 0.0;       			//货币资金
	private Double inventories = 0.0;  			//存货净额
	private Double accountsRreceivable = 0.0; 	//应收账款净额
	private Double notesReceivable = 0.0;  		//应收票据
	private Double otherReceivable = 0.0 ;  	//其他应收款净额
	private Double totalCurrentAssets = 0.0;  	//流动资产合计
	private Double payables = 0.0;      		//预收帐款
	private Double currentLiabilities = 0.0;  	//流动负债合计
	private Double totalAssets = 0.0;   		//资产总计
	private Double totalLiabilities = 0.0 ;		//负债合计
	
	
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
	@Override
	public String toString() {
		return "BalanceSheet [cash=" + cash + ", inventories=" + inventories
				+ ", accountsRreceivable=" + accountsRreceivable
				+ ", notesReceivable=" + notesReceivable + ", otherReceivable="
				+ otherReceivable + ", totalCurrentAssets="
				+ totalCurrentAssets + ", payables=" + payables
				+ ", currentLiabilities=" + currentLiabilities
				+ ", totalAssets=" + totalAssets + ", totalLiabilities="
				+ totalLiabilities + "]";
	}
	
	
	
}
