package com.rhb.sas.stock.bean;

import com.rhb.af.annotation.Clazz;
import com.rhb.af.annotation.Track;
import com.rhb.af.bean.BaseQuery;

@Clazz(Stock.class)
public class StockQuery extends BaseQuery {
	
	@Track("stockNo") private String stockNo;
	@Track("stockName") private String stockName;
	@Track("mainBusiness") private String mainBusiness;
	@Track("trade") private String trade;
	@Track("recommendable") private String recommendable;
	@Track("quarterPerformance") private String quarterPerformance;
	@Track("afterTenYearValue") private String afterTenYearValue;
	@Track("marketValue") private String marketValue;
	@Track("roe") private String roe;
	@Track("netCashFlowPerShare") private String netCashFlowPerShare;
	@Track("grossProfitPlus") private String grossProfitPlus;
	@Track("nextTenYearDiscount") private String nextTenYearDiscount;
	@Track("discount") private String discount;
	@Track("nextTenYearDiscount_quarter") private String nextTenYearDiscount_quarter;
	@Track("discount_quarter") private String discount_quarter;
	@Track("deleted") private String deleted;
	@Track("notes") private String notes;
	@Track("reportDate") private String reportDate;
	
	public StockQuery(){
		stockNo="";
		stockName="";
		mainBusiness="";
		trade="ÏÖ½ðÁ÷%";
		recommendable="";
		quarterPerformance="";
		afterTenYearValue = "";
		this.setOrderBy("discount_quarter");
		this.setDesc(false);
		marketValue="<300";
		roe=">0.12";
		netCashFlowPerShare="";
		grossProfitPlus=">0.10";
		discount="";
		nextTenYearDiscount="";
		nextTenYearDiscount_quarter = "<-0.5";
		discount_quarter="";
		deleted = "=0";
		notes = "";
		reportDate = "";
	}
	
	public void empty(){
		stockNo="";
		stockName="";
		mainBusiness="";
		trade="";
		recommendable="";
		quarterPerformance="";
		marketValue="";
		roe="";
		netCashFlowPerShare="";
		grossProfitPlus="";
		discount="";
		nextTenYearDiscount="";
		nextTenYearDiscount_quarter="";
		discount_quarter="";
		deleted = "";
		notes = "";
		reportDate = "";
			
	}

	public String getQuarterPerformance() {
		return quarterPerformance;
	}

	public void setQuarterPerformance(String quarterPerformance) {
		this.quarterPerformance = quarterPerformance;
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

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getRecommendable() {
		return recommendable;
	}

	public void setRecommendable(String recommendable) {
		this.recommendable = recommendable;
	}

	public String getAfterTenYearValue() {
		return afterTenYearValue;
	}

	public void setAfterTenYearValue(String afterTenYearValue) {
		this.afterTenYearValue = afterTenYearValue;
	}

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getRoe() {
		return roe;
	}

	public void setRoe(String roe) {
		this.roe = roe;
	}

	public String getNetCashFlowPerShare() {
		return netCashFlowPerShare;
	}

	public void setNetCashFlowPerShare(String netCashFlowPerShare) {
		this.netCashFlowPerShare = netCashFlowPerShare;
	}

	public String getGrossProfitPlus() {
		return grossProfitPlus;
	}

	public void setGrossProfitPlus(String grossProfitPlus) {
		this.grossProfitPlus = grossProfitPlus;
	}

	public String getNextTenYearDiscount() {
		return nextTenYearDiscount;
	}

	public void setNextTenYearDiscount(String nextTenYearDiscount) {
		this.nextTenYearDiscount = nextTenYearDiscount;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getNextTenYearDiscount_quarter() {
		return nextTenYearDiscount_quarter;
	}

	public void setNextTenYearDiscount_quarter(String nextTenYearDiscount_quarter) {
		this.nextTenYearDiscount_quarter = nextTenYearDiscount_quarter;
	}

	public String getDiscount_quarter() {
		return discount_quarter;
	}

	public void setDiscount_quarter(String discount_quarter) {
		this.discount_quarter = discount_quarter;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

}
