package com.rhb.sas.stock.bean;

import java.util.Date;

import com.rhb.af.annotation.Caption;
import com.rhb.af.bean.BaseBean;

public class Stock extends BaseBean {
	@Caption("��Ʊ����") private String stockNo = "";
	@Caption("��Ʊ����") private String stockName = "";
	@Caption("��Ӫҵ��") private String mainBusiness = "";
	@Caption("��ҵ") private String trade = "";
	@Caption("δ��10���ֵ") private Double nextTenYearValue = 0.0;
	@Caption("δ��10����ֵ") private Double afterTenYearValue = 0.0;
	@Caption("��ǰ�ɼ�") private Double pricePerShare = 0.0;
	@Caption("��ǰ��ֵ") private Double marketValue = 0.0;
	@Caption("��ǰ��ͨ��ֵ") private Double currentMarketValue = 0.0;
	@Caption("��Ⱦ�Ӫ�ֽ���") private Double dzhValue = 0.0;  
	@Caption("δ��10���ۼ���") private Double nextTenYearDiscount = 0.0;
	@Caption("���ۼ���") private Double discount = 0.0;
	@Caption("��ע") private String notes = "";
	private String recommendable = "";
	private Date madeDate = new Date();
	private Date reportDate = null;
	private Date noticeDate = null;
	private String quarterPerformance = "";

	private Double latestNetProfitPlus = 0.0;
	private Double latestEarnings = 0.0;
	@Caption("�ɶ�����������") private Double earningsGrowthRatio = 0.0;  //��3���걨ƽ��ֵ
	@Caption("�ɶ�����������(�����ֵ��)") private Double decadeEarningsGrowthRatio = 0.0;  //��earningsGrowthRatio������ֵ
	
	@Caption("��Ӫҵ��ë����") private Double grossProfit = 0.0; //��3���걨ƽ��ֵ  =��Ӫҵ������/��Ӫҵ������
	@Caption("Ӫҵë����") private Double grossProfitPlus = 0.0;  //��3���걨ƽ��ֵ  =Ӫҵ����/��Ӫҵ������

	@Caption("�����") private Double inventoryRatio = 0.0; //��3���걨ƽ��ֵ  = ���/��Ӫҵ������
	@Caption("Ӧ���ʿ���") private Double accountsRreceivableRatio = 0.0; //��3���걨ƽ��ֵ = Ӧ���ʿ�(û����Ӧ��Ʊ��)/��Ӫҵ������

	@Caption("���ʲ�������") private Double roe = 0.0;  //��3���걨ƽ��ֵ
	@Caption("ÿ��ֵ�ֽ���") private Double netCashFlowPerShare = 0.0;  //���1���걨�ľ�Ӫ�ֽ���/��ǰ��ֵ

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
	
	private Double earningsAvg = 0.0;			//  �������earnings���ƶ�ƽ��
	
	private int deleted = 0;

	public String getStockNoCaption() {
		return "��Ʊ����";
	}
	public String getStockNameCaption() {
		return "��Ʊ����";
	}
	public String getMainBusinessCaption() {
		return "��Ӫҵ��";
	}
	public String getTradeCaption() {
		return "��ҵ";
	}
	public String getNextTenYearValueCaption() {
		return "δ��10���ֵ";
	}
	public String getAfterTenYearValueCaption() {
		return "δ��10����ֵ";
	}
	public String getPricePerShareCaption() {
		return "��ǰ�ɼ�";
	}
	public String getMarketValueCaption() {
		return "��ǰ��ֵ";
	}
	public String getCurrentMarketValueCaption() {
		return "��ǰ��ͨ��ֵ";
	}
	public String getDzhValueCaption() {
		return "���ǻ���ֵ";
	}
	public String getNextTenYearDiscountCaption() {
		return "δ��10���ۼ���";
	}
	public String getDiscountCaption() {
		return "���ۼ���";
	}
	public String getNotesCaption() {
		return "��ע";
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
			errors.append(this.getStockNoCaption() + "����Ϊ�ա�");
		}
		if (this.stockName == null || this.stockName.trim().length() == 0) {
			errors.append(this.getStockNameCaption() + "����Ϊ�ա�");
		}
		return errors.toString();
	}

	public String outOfRangeValidate() {
		StringBuffer errors = new StringBuffer();
		if (this.stockNo.length() > 24) {
			errors.append(this.getStockNoCaption() + "�ַ���Ϊ"
					+ this.stockNo.length() + ",������24���ַ���������ƣ�");
		}
		if (this.stockName.length() > 24) {
			errors.append(this.getStockNameCaption() + "�ַ���Ϊ"
					+ this.stockName.length() + ",������24���ַ���������ƣ�");
		}
		if (this.mainBusiness.length() > 24) {
			errors.append(this.getMainBusinessCaption() + "�ַ���Ϊ"
					+ this.mainBusiness.length() + ",������24���ַ���������ƣ�");
		}
		if (this.trade.length() > 24) {
			errors.append(this.getTradeCaption() + "�ַ���Ϊ" + this.trade.length()
					+ ",������24���ַ���������ƣ�");
		}
		if (this.notes.length() > 250) {
			errors.append(this.getNotesCaption() + "�ַ���Ϊ" + this.notes.length()
					+ ",������250���ַ���������ƣ�");
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
