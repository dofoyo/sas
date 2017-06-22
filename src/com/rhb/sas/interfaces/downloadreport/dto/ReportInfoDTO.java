package com.rhb.sas.interfaces.downloadreport.dto;

import java.util.Date;

import com.rhb.sas.util.Tools;

public class ReportInfoDTO {
	private String stockNo;
	private String stockName;
	private Date beginDate;
	private Date endDate;
	private String periodType;
	private String description;
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(stockNo + "," + 
					stockName + "," + 
					Tools.getDate(beginDate,"yyyy-MM-dd") + "," + 
					Tools.getDate(endDate,"yyyy-MM-dd")
					);
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
