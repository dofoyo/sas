package com.rhb.sas.stockholder.bean;

import java.util.Date;

import com.rhb.af.bean.BaseQuery;

public class StockholderQuery extends BaseQuery {
	private String stockNo;
	private String holderName;
	private Date endDate_begin;
	private Date endDate_end;
	private Date reportedDate_begin;
	private Date reportedDate_end;
	
	public StockholderQuery(){
		stockNo = "";
		holderName = "";
		endDate_begin = null;
		endDate_end = null;
		reportedDate_begin = null;
		reportedDate_end = null;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public Date getEndDate_begin() {
		return endDate_begin;
	}

	public void setEndDate_begin(Date endDate_begin) {
		this.endDate_begin = endDate_begin;
	}

	public Date getEndDate_end() {
		return endDate_end;
	}

	public void setEndDate_end(Date endDate_end) {
		this.endDate_end = endDate_end;
	}

	public Date getReportedDate_begin() {
		return reportedDate_begin;
	}

	public void setReportedDate_begin(Date reportedDate_begin) {
		this.reportedDate_begin = reportedDate_begin;
	}

	public Date getReportedDate_end() {
		return reportedDate_end;
	}

	public void setReportedDate_end(Date reportedDate_end) {
		this.reportedDate_end = reportedDate_end;
	}
}
