package com.rhb.sas.report.profitstatement;

import com.rhb.af.annotation.Clazz;
import com.rhb.af.annotation.Track;
import com.rhb.af.bean.BaseQuery;

@Clazz(ProfitStatement.class)
public class ProfitStatementQuery extends BaseQuery {
	@Track("stockNo") 		private String stockNo = "";
	@Track("stockName")		private String stockName = "";
	@Track("theYear") 		private String theYear = "";
	@Track("theMonth") 		private String theMonth = "";
	
	public void empty(){
		stockNo = "";
		stockName = "";
		theYear = "";
		theMonth = "";
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

}
