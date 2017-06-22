package com.rhb.sas.report.bean;

import com.rhb.af.annotation.Clazz;
import com.rhb.af.annotation.Track;
import com.rhb.af.bean.BaseQuery;

@Clazz(Report.class)
public class ReportQuery extends BaseQuery {
	@Track("stockNo") 		private String stockNo = "";
	@Track("stockName")		private String stockName = "";
	@Track("periodType") 	private String periodType = "ÆÚÄ©";
	@Track("description") 	private String description = "";
	@Track("theYear") 		private String theYear = "";
	@Track("theMonth") 		private String theMonth = "";
	
	public void empty(){
		stockNo = "";
		stockName = "";
		periodType = "";
		description = "";
		theYear = "";
		theMonth = "";
	}

	public String getStockNo() {
		return this.stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getPeriodType() {
		return this.periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTheYear() {
		return theYear;
	}

	public void setTheYear(String theYear) {
		this.theYear = theYear;
	}

	public String getTheMonth() {
		return theMonth;
	}

	public void setTheMonth(String theMonth) {
		this.theMonth = theMonth;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(stockNo);
		sb.append(",");
		sb.append(theYear);
		sb.append(",");
		sb.append(theMonth);
		return sb.toString();
		
	}
	
}
