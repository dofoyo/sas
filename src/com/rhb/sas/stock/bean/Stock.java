package com.rhb.sas.stock.bean;

import java.util.Date;

import com.rhb.af.annotation.Caption;
import com.rhb.af.bean.BaseBean;

public class Stock extends BaseBean {
	@Caption("股票代码") private String stockNo = "";
	@Caption("股票名称") private String stockName = "";
	@Caption("主营业务") private String mainBusiness = "";
	@Caption("行业") private String trade = "";
	@Caption("未来10年价值") private Double nextTenYearValue = 0.0;
	@Caption("未来10年后价值") private Double afterTenYearValue = 0.0;
	@Caption("当前股价") private Double pricePerShare = 0.0;
	@Caption("当前市值") private Double marketValue = 0.0;
	@Caption("当前流通市值") private Double currentMarketValue = 0.0;
	@Caption("年度经营现金流") private Double dzhValue = 0.0;  
	@Caption("未来10年折价率") private Double nextTenYearDiscount = 0.0;
	@Caption("总折价率") private Double discount = 0.0;
	@Caption("备注") private String notes = "";
	private String recommendable = "";
	private Date madeDate = new Date();
	private Date reportDate = null;
	private Date noticeDate = null;
	private String quarterPerformance = "";

	private Double latestNetProfitPlus = 0.0;
	private Double latestEarnings = 0.0;
	@Caption("股东收益增长率") private Double earningsGrowthRatio = 0.0;  //近3年年报平均值
	@Caption("股东收益增长率(计算价值用)") private Double decadeEarningsGrowthRatio = 0.0;  //是earningsGrowthRatio的修正值
	
	@Caption("主营业务毛利率") private Double grossProfit = 0.0; //近3年年报平均值  =主营业务利润/主营业务收入
	@Caption("营业毛利率") private Double grossProfitPlus = 0.0;  //近3年年报平均值  =营业利润/主营业务收入

	@Caption("库存率") private Double inventoryRatio = 0.0; //近3年年报平均值  = 存货/主营业务收入
	@Caption("应收帐款率") private Double accountsRreceivableRatio = 0.0; //近3年年报平均值 = 应收帐款(没包括应收票据)/主营业务收入

	@Caption("净资产收益率") private Double roe = 0.0;  //近3年年报平均值
	@Caption("每市值现金流") private Double netCashFlowPerShare = 0.0;  //最近1年年报的经营现金流/当前市值

	private Double latestPrimeOperatingProfitPlus = 0.0;
	private Double latestDepreciationAssets = 0.0;
	private Double latestPurchaseAssets = 0.0;
	
	private Double latestQuarterPrimeOperatingProfitPlus = 0.0;
	private Double latestQuarterDepreciationAssets = 0.0;
	private Double latestQuarterPurchaseAssets = 0.0;

	private Double latestEarnings_quarter = 0.0;
	
	private Double nextTenYearValue_quarter = 0.0;
	private Double afterTenYearValue_quarter = 0.0;
	private Double nextTenYearDiscount_quarter = 0.0;
	private Double discount_quarter = 0.0;
	
	private Double earningsAvg = 0.0;			//  最近三年earnings的移动平均
	
	private int deleted = 0;

	public String getStockNoCaption() {
		return "股票代码";
	}
	public String getStockNameCaption() {
		return "股票名称";
	}
	public String getMainBusinessCaption() {
		return "主营业务";
	}
	public String getTradeCaption() {
		return "行业";
	}
	public String getNextTenYearValueCaption() {
		return "未来10年价值";
	}
	public String getAfterTenYearValueCaption() {
		return "未来10年后价值";
	}
	public String getPricePerShareCaption() {
		return "当前股价";
	}
	public String getMarketValueCaption() {
		return "当前市值";
	}
	public String getCurrentMarketValueCaption() {
		return "当前流通市值";
	}
	public String getDzhValueCaption() {
		return "大智慧市值";
	}
	public String getNextTenYearDiscountCaption() {
		return "未来10年折价率";
	}
	public String getDiscountCaption() {
		return "总折价率";
	}
	public String getNotesCaption() {
		return "备注";
	}

	
	public Double getEarningsAvg() {
		return earningsAvg;
	}

	public void setEarningsAvg(Double earningsAvg) {
		this.earningsAvg = earningsAvg;
	}

	public Double getLatestNetProfitPlus() {
		return latestNetProfitPlus;
	}

	public void setLatestNetProfitPlus(Double latestNetProfitPlus) {
		this.latestNetProfitPlus = latestNetProfitPlus;
	}

	public Double getLatestEarnings() {
		/*
		double d = 0.0;
		if(this.latestPrimeOperatingProfitPlus!=null && this.latestDepreciationAssets!=null && this.latestPurchaseAssets!=null){
			d = (this.latestPrimeOperatingProfitPlus + this.latestDepreciationAssets-this.latestPurchaseAssets);
		}
		
		//System.out.println("*************** d=" + d);
		//System.out.println("*************** this.earningsAvg=" + this.earningsAvg);
		
		if(this.earningsAvg == null){
			return d;
		}else{
			return d>0 ? d : this.earningsAvg;
		}
		*/

		
		/*
		if(this.earningsAvg == null){
			return latestEarnings;
		}else{
			return latestEarnings>0 ? latestEarnings : this.earningsAvg;
		}
		*/

		return latestEarnings;

	}

	public void setLatestEarnings(Double latestEarnings) {
		this.latestEarnings = latestEarnings;
	}

	public Double getEarningsGrowthRatio() {
		return earningsGrowthRatio;
	}

	public void setEarningsGrowthRatio(Double earningsGrowthRatio) {
		this.earningsGrowthRatio = earningsGrowthRatio;
	}

	public Double getDecadeEarningsGrowthRatio() {
		return decadeEarningsGrowthRatio;
	}

	public void setDecadeEarningsGrowthRatio(Double decadeEarningsGrowthRatio) {
		this.decadeEarningsGrowthRatio = decadeEarningsGrowthRatio;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Stock() {
	}


	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockNo() {
		return this.stockNo;
	}
	
	public String getStockNoWithPrefix(){
		String prefix = "sh";
		if(stockNo.startsWith("0")
				|| stockNo.startsWith("3")
		){
			prefix = "sz";
		}
		return prefix + stockNo;
	}


	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockName() {
		return this.stockName;
	}


	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getMainBusiness() {
		return this.mainBusiness;
	}


	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getTrade() {
		return this.trade;
	}


	public void setNextTenYearValue(Double nextTenYearValue) {
		this.nextTenYearValue = nextTenYearValue;
	}

	public Double getNextTenYearValue() {
		return this.nextTenYearValue;
	}


	public void setAfterTenYearValue(Double afterTenYearValue) {
		this.afterTenYearValue = afterTenYearValue;
	}

	public Double getAfterTenYearValue() {
		return this.afterTenYearValue;
	}


	public void setPricePerShare(Double pricePerShare) {
		this.pricePerShare = pricePerShare;
	}

	public Double getPricePerShare() {
		return this.pricePerShare;
	}


	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public Double getMarketValue() {
		return this.marketValue;
	}

	
	public Double getCurrentMarketValue() {
		return currentMarketValue;
	}

	public void setCurrentMarketValue(Double currentMarketValue) {
		this.currentMarketValue = currentMarketValue;
	}
	

	public void setDzhValue(Double dzhValue) {
		this.dzhValue = dzhValue;
	}

	public Double getDzhValue() {
		return this.dzhValue;
	}


	public void setNextTenYearDiscount(Double nextTenYearDiscount) {
		this.nextTenYearDiscount = nextTenYearDiscount;
	}

	public Double getNextTenYearDiscount() {
		return this.nextTenYearDiscount;
	}


	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getDiscount() {
		return this.discount;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return this.notes;
	}


	public String requiredValidate() {
		StringBuffer errors = new StringBuffer();
		if (this.stockNo == null || this.stockNo.trim().length() == 0) {
			errors.append(this.getStockNoCaption() + "不能为空。");
		}
		if (this.stockName == null || this.stockName.trim().length() == 0) {
			errors.append(this.getStockNameCaption() + "不能为空。");
		}
		return errors.toString();
	}

	public String outOfRangeValidate() {
		StringBuffer errors = new StringBuffer();
		if (this.stockNo.length() > 24) {
			errors.append(this.getStockNoCaption() + "字符数为"
					+ this.stockNo.length() + ",超过了24个字符的最大限制！");
		}
		if (this.stockName.length() > 24) {
			errors.append(this.getStockNameCaption() + "字符数为"
					+ this.stockName.length() + ",超过了24个字符的最大限制！");
		}
		if (this.mainBusiness.length() > 24) {
			errors.append(this.getMainBusinessCaption() + "字符数为"
					+ this.mainBusiness.length() + ",超过了24个字符的最大限制！");
		}
		if (this.trade.length() > 24) {
			errors.append(this.getTradeCaption() + "字符数为" + this.trade.length()
					+ ",超过了24个字符的最大限制！");
		}
		if (this.notes.length() > 250) {
			errors.append(this.getNotesCaption() + "字符数为" + this.notes.length()
					+ ",超过了250个字符的最大限制！");
		}
		return errors.toString();
	}

	public Date getMadeDate() {
		return madeDate;
	}

	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}
	
	public String toString(){
		return "no=" + this.stockNo + 
				",name=" + this.stockName + 
				",price=" + Double.toString(this.pricePerShare) +
				",value=" + Double.toString(this.marketValue);
	}

	public String getRecommendable() {
		return recommendable;
	}

	public void setRecommendable(String recommendable) {
		this.recommendable = recommendable;
	}

	public Double getRoe() {
		return roe;
	}

	public void setRoe(Double roe) {
		this.roe = roe;
	}

	public Double getNetCashFlowPerShare() {
		return netCashFlowPerShare;
	}

	public void setNetCashFlowPerShare(Double netCashFlowPerShare) {
		this.netCashFlowPerShare = netCashFlowPerShare;
	}

	public Double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(Double grossProfit) {
		this.grossProfit = grossProfit;
	}

	public Double getGrossProfitPlus() {
		return grossProfitPlus;
	}

	public void setGrossProfitPlus(Double grossProfitPlus) {
		this.grossProfitPlus = grossProfitPlus;
	}

	public Double getInventoryRatio() {
		return inventoryRatio;
	}

	public void setInventoryRatio(Double inventoryRatio) {
		this.inventoryRatio = inventoryRatio;
	}

	public Double getAccountsRreceivableRatio() {
		return accountsRreceivableRatio;
	}

	public void setAccountsRreceivableRatio(Double accountsRreceivableRatio) {
		this.accountsRreceivableRatio = accountsRreceivableRatio;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getQuarterPerformance() {
		return quarterPerformance;
	}

	public void setQuarterPerformance(String quarterPerformance) {
		this.quarterPerformance = quarterPerformance;
	}


	public Double getLatestPrimeOperatingProfitPlus() {
		return latestPrimeOperatingProfitPlus;
	}

	public void setLatestPrimeOperatingProfitPlus(
			Double latestPrimeOperatingProfitPlus) {
		this.latestPrimeOperatingProfitPlus = latestPrimeOperatingProfitPlus;
	}

	public Double getLatestDepreciationAssets() {
		return latestDepreciationAssets;
	}

	public void setLatestDepreciationAssets(Double latestDepreciationAssets) {
		this.latestDepreciationAssets = latestDepreciationAssets;
	}

	public Double getLatestPurchaseAssets() {
		return latestPurchaseAssets;
	}

	public void setLatestPurchaseAssets(Double latestPurchaseAssets) {
		this.latestPurchaseAssets = latestPurchaseAssets;
	}

	public Double getLatestQuarterPrimeOperatingProfitPlus() {
		return latestQuarterPrimeOperatingProfitPlus;
	}

	public void setLatestQuarterPrimeOperatingProfitPlus(
			Double latestQuarterPrimeOperatingProfitPlus) {
		this.latestQuarterPrimeOperatingProfitPlus = latestQuarterPrimeOperatingProfitPlus;
	}

	public Double getLatestQuarterDepreciationAssets() {
		return latestQuarterDepreciationAssets;
	}

	public void setLatestQuarterDepreciationAssets(
			Double latestQuarterDepreciationAssets) {
		this.latestQuarterDepreciationAssets = latestQuarterDepreciationAssets;
	}

	public Double getLatestQuarterPurchaseAssets() {
		return latestQuarterPurchaseAssets;
	}

	public void setLatestQuarterPurchaseAssets(Double latestQuarterPurchaseAssets) {
		this.latestQuarterPurchaseAssets = latestQuarterPurchaseAssets;
	}

	public Double getNextTenYearValue_quarter() {
		return nextTenYearValue_quarter;
	}

	public void setNextTenYearValue_quarter(Double nextTenYearValue_quarter) {
		this.nextTenYearValue_quarter = nextTenYearValue_quarter;
	}

	public Double getAfterTenYearValue_quarter() {
		return afterTenYearValue_quarter;
	}

	public void setAfterTenYearValue_quarter(Double afterTenYearValue_quarter) {
		this.afterTenYearValue_quarter = afterTenYearValue_quarter;
	}

	public Double getNextTenYearDiscount_quarter() {
		return nextTenYearDiscount_quarter;
	}

	public void setNextTenYearDiscount_quarter(Double nextTenYearDiscount_quarter) {
		this.nextTenYearDiscount_quarter = nextTenYearDiscount_quarter;
	}

	public Double getDiscount_quarter() {
		return discount_quarter;
	}

	public void setDiscount_quarter(Double discount_quarter) {
		this.discount_quarter = discount_quarter;
	}

	public Double getLatestEarnings_quarter() {
		/*
		if(this.latestQuarterPrimeOperatingProfitPlus==null || this.latestQuarterDepreciationAssets==null || this.latestQuarterPurchaseAssets==null){
			return 0.0;
		}else{
			return (this.latestQuarterPrimeOperatingProfitPlus + this.latestQuarterDepreciationAssets-this.latestQuarterPurchaseAssets);
		}
		*/
		return latestEarnings_quarter;
	}

	public void setLatestEarnings_quarter(Double latestEarnings_quarter) {
		this.latestEarnings_quarter = latestEarnings_quarter;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	public double getEarnings(){
		double earnings = latestEarnings > 0 ? latestEarnings : latestEarnings_quarter;
		return earnings>0 ? earnings : 0.0;
	}

}
