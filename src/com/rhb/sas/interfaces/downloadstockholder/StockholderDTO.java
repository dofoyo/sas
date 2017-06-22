package com.rhb.sas.interfaces.downloadstockholder;

import java.util.Date;

public class StockholderDTO {
	private String stockNo;
	private String stockName;
	private String holderNo;
	private String holderName;
	private Date endDate;
	private Date reportedDate;
	private Long holdingNumber;
	private Double holdingRatio;
	private String holdingType;

	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("stockNo = " + stockNo);
		//sb.append(",stockName = " + stockName);
		sb.append(",holderNo = " + holderNo);
		sb.append(",holderName = " + holderName);
		sb.append(",holdingNumber = " + holdingNumber);
		sb.append(",holdingRatio = " + holdingRatio);
		sb.append(",holdingType = " + holdingType);
		sb.append(",endDate=" + endDate.toString());
		return sb.toString();
	}


	public String getStockNo() {
		return stockNo;
	}


	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}


	public String getStockName() {
		return stockName;
	}


	public void setStockName(String stockName) {
		this.stockName = stockName;
	}


	public String getHolderNo() {
		return holderNo;
	}


	public void setHolderNo(String holderNo) {
		this.holderNo = holderNo;
	}


	public String getHolderName() {
		return holderName;
	}


	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Long getHoldingNumber() {
		return holdingNumber;
	}


	public void setHoldingNumber(Long holdingNumber) {
		this.holdingNumber = holdingNumber;
	}


	public Double getHoldingRatio() {
		return holdingRatio;
	}


	public void setHoldingRatio(Double holdingRatio) {
		this.holdingRatio = holdingRatio;
	}


	public String getHoldingType() {
		return holdingType;
	}


	public void setHoldingType(String holdingType) {
		this.holdingType = holdingType;
	}


	public Date getReportedDate() {
		return reportedDate;
	}


	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}

}
