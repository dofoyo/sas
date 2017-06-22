package com.rhb.sas.report.bean;

import java.util.Date;

import com.rhb.sas.util.Tools;
import com.rhb.af.annotation.Caption;
import com.rhb.af.bean.BaseBean;
import com.rhb.af.bean.BaseQuery;
import com.rhb.af.util.Format;

public class Report extends BaseBean {
	@Caption("编码") private String reportPk = "";
	@Caption("股票代码") private String stockNo = "";
	@Caption("股票名称") private String stockName = "";
	@Caption("期初期末") private String periodType = "";
	@Caption("数据时间") private String description = "";
	@Caption("会计期间起始") private java.util.Date beginDate = new Date();
	@Caption("会计期间截止") private java.util.Date endDate = new Date();
	@Caption("现金") private Double cash = 0.0;       			//资产负债表.货币资金
	@Caption("存货") private Double inventories = 0.0;  			//资产负债表.存货净额
	@Caption("应收帐款") private Double accountsRreceivable = 0.0; 	//资产负债表.应收账款净额
	@Caption("应收票据") private Double notesReceivable = 0.0;  		//资产负债表.应收票据
	@Caption("其它应收款") private Double otherReceivable = 0.0;  		//资产负债表.其他应收款净额
	@Caption("流动资产合计") private Double totalCurrentAssets = 0.0;  	//资产负债表.流动资产合计
	@Caption("预收款") private Double payables = 0.0;      		//资产负债表.预收帐款
	@Caption("流动负债") private Double currentLiabilities = 0.0;  	//资产负债表.流动负债合计
	@Caption("总资产") private Double totalAssets = 0.0;   		//资产负债表.资产总计
	@Caption("总负债") private Double totalLiabilities = 0.0;		//资产负债表.负债合计
	@Caption("购建固定资产、无形资产及其他长期资产所支付的现金") private Double purchaseAssets = 0.0;		//现金流量表.购建固定资产、无形资产及其他长期资产所支付的现金
	@Caption("固定资产折旧、无形资产和长期待摊费用摊销") private Double depreciationAssets = 0.0;	//现金流量表.固定资产折旧+无形资产摊销+递延资产摊销+长期待摊费用摊销
	@Caption("净利润") private Double netProfit = 0.0;				//利润表.净利润
	@Caption("扣除非经常性损益的净利润") private Double netProfitPlus = 0.0;			//财务指标.扣除非经常性损益的净利润 
	@Caption("经营活动现金流量净额增长率") private Double shareholders = 0.0;			//  相邻2年的经营活动现金流量净额同比增长率
	@Caption("经营活动现金流量净额") private Double netCashFlow = 0.0;			//  经营活动现金流量净额
	@Caption("净资产收益率") private Double roe = 0.0;					//  = earningsPerShare/netAssetsPerShare
	private Double earnings = 0.0;				//  = primeOperatingProfitPlus*0.75+depreciationAssets-purchaseAssets,保险股不一样
	private Double earningsAvg = 0.0;			//  最近三年earnings的移动平均
	private Double earningsGrowthRatio = 0.0;	//  相邻2年的同比增长率
	private String theYear = "";
	private String theMonth = "";
	private int readOnly = 0;
	@Caption("主营业务收入") private Double primeOperatingRevenue = 0.0;   	//利润表.主营业务收入
	@Caption("主营业务利润") private Double primeOperatingProfit = 0.0;		//财务指标.主营业务利润
	@Caption("营业利润") private Double operatingProfit = 0.0;	//利润表.营业利润  营业利润=主营业务利润+其他业务利润-营业费用-管理费用-财务费用
	@Caption("每股净资产") private Double netAssetsPerShare = 0.0;   		//财务摘要.每股净资产
	@Caption("每股收益") private Double earningsPerShare = 0.0;			//财务摘要.每股收益
	@Caption("每股现金含量") private Double netCashFlowPerShare = 0.0;		//财务摘要.每股现金含量
	@Caption("每股资本公积金") private Double capitalFundPerShare = 0.0;		//财务摘要.每股资本公积金
	private double operatingExpense = 0.0;  //利润表.管理费用
	private double salesExpense = 0.0; //利润表.销售费用
	private double financeExpense = 0.0; //利润表.财务费用

	
	public void updateByInput(Report report){
		this.cash = report.cash==0.0 ? this.cash : report.cash;       			//资产负债表.货币资金
		this.inventories = report.inventories==0.0 ? this.inventories : report.inventories;  			//资产负债表.存货净额
		this.accountsRreceivable = report.accountsRreceivable==0.0 ? this.accountsRreceivable : report.accountsRreceivable; 	//资产负债表.应收账款净额
		this.notesReceivable = report.notesReceivable==0.0 ? this.notesReceivable : report.notesReceivable;  		//资产负债表.应收票据
		this.otherReceivable = report.otherReceivable==0.0 ? this.otherReceivable : report.otherReceivable;  		//资产负债表.其他应收款净额
		this.totalCurrentAssets = report.totalCurrentAssets==0.0 ? this.totalCurrentAssets : report.totalCurrentAssets;  	//资产负债表.流动资产合计
		this.payables = report.payables==0.0 ? this.payables : report.payables;      		//资产负债表.预收帐款
		this.currentLiabilities = report.currentLiabilities==0.0 ? this.currentLiabilities : report.currentLiabilities;  	//资产负债表.流动负债合计
		this.totalAssets = report.totalAssets==0.0 ? this.totalAssets : report.totalAssets;   		//资产负债表.资产总计
		this.totalLiabilities = report.totalLiabilities==0.0 ? this.totalLiabilities : report.totalLiabilities;		//资产负债表.负债合计
		this.purchaseAssets = report.purchaseAssets==0.0 ? this.purchaseAssets : report.purchaseAssets;		//现金流量表.购建固定资产、无形资产及其他长期资产所支付的现金
		this.depreciationAssets = report.depreciationAssets==0.0 ? this.depreciationAssets : report.depreciationAssets;	//现金流量表.固定资产折旧+无形资产摊销+递延资产摊销+长期待摊费用摊销
		this.netProfit = report.netProfit==0.0 ? this.netProfit : report.netProfit;				//利润表.净利润
		this.netProfitPlus = report.netProfitPlus==0.0 ? this.netProfitPlus : report.netProfitPlus;			//财务指标.扣除非经常性损益的净利润 
		this.shareholders = report.shareholders==0.0 ? this.shareholders : report.shareholders;			//  相邻2年的经营活动现金流量净额同比增长率
		this.netCashFlow = report.netCashFlow==0.0 ? this.netCashFlow : report.netCashFlow;			//  经营活动现金流量净额
		this.roe = report.roe==0.0 ? this.roe : report.roe;					//  = earningsPerShare/netAssetsPerShare
		this.earnings = report.earnings==0.0 ? this.earnings : report.earnings;				//  = primeOperatingProfitPlus*0.75+depreciationAssets-purchaseAssets,保险股不一样
		this.earningsAvg = report.earningsAvg==0.0 ? this.earningsAvg : report.earningsAvg;			//  最近三年earnings的移动平均
		this.earningsGrowthRatio = report.earningsGrowthRatio==0.0 ? this.earningsGrowthRatio : report.earningsGrowthRatio;	//  相邻2年的同比增长率
		this.primeOperatingRevenue = report.primeOperatingRevenue==0.0 ? this.primeOperatingRevenue : report.primeOperatingRevenue;   	//利润表.主营业务收入
		this.primeOperatingProfit = report.primeOperatingProfit==0.0 ? this.primeOperatingProfit : report.primeOperatingProfit;		//财务指标.主营业务利润
		this.operatingProfit = report.operatingProfit==0.0 ? this.operatingProfit : report.operatingProfit;	//利润表.营业利润  营业利润=主营业务利润+其他业务利润-营业费用-管理费用-财务费用
		this.netAssetsPerShare = report.netAssetsPerShare==0.0 ? this.netAssetsPerShare : report.netAssetsPerShare;   		//财务摘要.每股净资产
		this.earningsPerShare = report.earningsPerShare==0.0 ? this.earningsPerShare : report.earningsPerShare;			//财务摘要.每股收益
		this.netCashFlowPerShare = report.netCashFlowPerShare==0.0 ? this.netCashFlowPerShare : report.netCashFlowPerShare;		//财务摘要.每股现金含量
		this.capitalFundPerShare = report.capitalFundPerShare==0.0 ? this.capitalFundPerShare : report.capitalFundPerShare;		//财务摘要.每股资本公积金
		this.operatingExpense = report.operatingExpense==0.0 ? this.operatingExpense : report.operatingExpense;  //利润表.管理费用
		this.salesExpense = report.salesExpense==0.0 ? this.salesExpense : report.salesExpense; //利润表.销售费用
		this.financeExpense = report.financeExpense==0.0 ? this.financeExpense : report.financeExpense; //利润表.财务费用
		
	}
	
	public double getOperatingExpense() {
		return operatingExpense;
	}
	public void setOperatingExpense(double operatingExpense) {
		this.operatingExpense = operatingExpense;
	}
	public double getSalesExpense() {
		return salesExpense;
	}
	public void setSalesExpense(double salesExpense) {
		this.salesExpense = salesExpense;
	}
	public double getFinanceExpense() {
		return financeExpense;
	}
	public void setFinanceExpense(double financeExpense) {
		this.financeExpense = financeExpense;
	}
	public Double getOperatingProfit() {
		return operatingProfit;
	}
	public void setOperatingProfit(Double operatingProfit) {
		this.operatingProfit = operatingProfit;
	}
	public String getReportPkCaption() {
		return "编码";
	}
	public String getStockNoCaption() {
		return "股票代码";
	}
	public String getStockNameCaption() {
		return "股票名称";
	}
	public String getPeriodTypeCaption() {
		return "期初期末";
	}
	public String getDescriptionCaption() {
		return "数据时间";
	}
	public String getBeginDateCaption() {
		return "会计期间起始";
	}
	public String getEndDateCaption() {
		return "会计期间截止";
	}
	public String getCashCaption() {
		return "现金";
	}
	public String getInventoriesCaption() {
		return "存货";
	}
	public String getAccountsRreceivableCaption() {
		return "应收帐款";
	}
	public String getNotesReceivableCaption() {
		return "应收票据";
	}
	public String getOtherReceivableCaption() {
		return "其它应收款";
	}
	public String getTotalCurrentAssetsCaption() {
		return "流动资产合计";
	}
	public String getPayablesCaption() {
		return "预收款";
	}
	public String getCurrentLiabilitiesCaption() {
		return "流动负债";
	}
	public String getTotalAssetsCaption() {
		return "总资产";
	}
	public String getTotalLiabilitiesCaption() {
		return "总负债";
	}
	public String getPurchaseAssetsCaption() {
		return "购建固定资产、无形资产及其他长期资产所支付的现金";
	}
	public String getDepreciationAssetsCaption() {
		return "固定资产折旧、无形资产和长期待摊费用摊销";
	}
	public String getNetProfitCaption() {
		return "净利润";
	}
	public String getShareholdersCaption() {
		return "股东权益";
	}
	public String getNetCashFlowCaption() {
		return "经营活动现金流量净额";
	}
	public String getRoeCaption() {
		return "净资产收益率";
	}
	public String getNetProfitPlusCaption() {
		return "扣除非经常性损益的净利润";
	}
	public String getPrimeOperatingRevenueCaption() {
		return "主营业务收入";
	}

	public String getPrimeOperatingProfitCaption() {
		return "主营业务利润";
	}

	public Double getNetAssetsPerShare() {
		return netAssetsPerShare;
	}

	public void setNetAssetsPerShare(Double netAssetsPerShare) {
		this.netAssetsPerShare = netAssetsPerShare;
	}

	public Double getEarningsPerShare() {
		return earningsPerShare;
	}

	public void setEarningsPerShare(Double earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}

	public Double getNetCashFlowPerShare() {
		return netCashFlowPerShare;
	}

	public void setNetCashFlowPerShare(Double netCashFlowPerShare) {
		this.netCashFlowPerShare = netCashFlowPerShare;
	}

	public Double getCapitalFundPerShare() {
		return capitalFundPerShare;
	}

	public void setCapitalFundPerShare(Double capitalFundPerShare) {
		this.capitalFundPerShare = capitalFundPerShare;
	}

	public int getReadOnly() {
		return readOnly;
	}
	
	public double getReceivable(){
		double notesReceivable = 0.0;
		if(this.getNotesReceivable() != null){
			notesReceivable = this.getNotesReceivable();
		}
		
		double otherReceivable = 0.0;
		if(this.getOtherReceivable() != null){
			otherReceivable = this.getOtherReceivable();
		}
		return this.getAccountsRreceivable()
				+ notesReceivable 
				+ otherReceivable;
	}

	public void setReadOnly(int readOnly) {
		this.readOnly = readOnly;
	}

	public String getTheYear() {
		return Tools.getDate(endDate, "yyyy");
	}

	public void setTheYear(String theYear) {
		this.theYear = theYear;
	}

	public String getTheMonth() {
		return Tools.getDate(endDate, "MM");
	}

	public void setTheMonth(String theMonth) {
		this.theMonth = theMonth;
	}

	public Report() {
	}

	public void setReportPk(String reportPk) {
		this.reportPk = reportPk;
	}

	public String getReportPk() {
		return this.reportPk;
	}


	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockNo() {
		return this.stockNo;
	}


	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockName() {
		return this.stockName;
	}


	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getPeriodType() {
		return this.periodType;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}


	public void setBeginDate(java.util.Date beginDate) {
		this.beginDate = beginDate;
	}

	public java.util.Date getBeginDate() {
		return this.beginDate;
	}


	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public java.util.Date getEndDate() {
		return this.endDate;
	}


	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getCash() {
		return this.cash;
	}


	public void setInventories(Double inventories) {
		this.inventories = inventories;
	}

	public Double getInventories() {
		return this.inventories;
	}


	public void setAccountsRreceivable(Double accountsRreceivable) {
		this.accountsRreceivable = accountsRreceivable;
	}

	public Double getAccountsRreceivable() {
		return this.accountsRreceivable;
	}


	public void setNotesReceivable(Double notesReceivable) {
		this.notesReceivable = notesReceivable;
	}

	public Double getNotesReceivable() {
		return this.notesReceivable;
	}


	public void setOtherReceivable(Double otherReceivable) {
		this.otherReceivable = otherReceivable;
	}

	public Double getOtherReceivable() {
		return this.otherReceivable;
	}


	public void setTotalCurrentAssets(Double totalCurrentAssets) {
		this.totalCurrentAssets = totalCurrentAssets;
	}

	public Double getTotalCurrentAssets() {
		return this.totalCurrentAssets;
	}

	public void setPayables(Double payables) {
		this.payables = payables;
	}

	public Double getPayables() {
		return this.payables;
	}


	public void setCurrentLiabilities(Double currentLiabilities) {
		this.currentLiabilities = currentLiabilities;
	}

	public Double getCurrentLiabilities() {
		return this.currentLiabilities;
	}


	public void setTotalAssets(Double totalAssets) {
		this.totalAssets = totalAssets;
	}

	public Double getTotalAssets() {
		return this.totalAssets;
	}


	public void setTotalLiabilities(Double totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public Double getTotalLiabilities() {
		return this.totalLiabilities;
	}


	public void setPurchaseAssets(Double purchaseAssets) {
		this.purchaseAssets = purchaseAssets;
	}

	public Double getPurchaseAssets() {
		return this.purchaseAssets;
	}


	public void setDepreciationAssets(Double depreciationAssets) {
		this.depreciationAssets = depreciationAssets;
	}

	public Double getDepreciationAssets() {
		return this.depreciationAssets;
	}


	public void setNetProfit(Double netProfit) {
		this.netProfit = netProfit;
	}

	public Double getNetProfit() {
		return this.netProfit;
	}

	
	public void setNetProfitPlus(Double netProfitPlus) {
		this.netProfitPlus = netProfitPlus;
	}

	public Double getNetProfitPlus() {
		return this.netProfitPlus;
	}

	public void setShareholders(Double shareholders) {
		this.shareholders = shareholders;
	}

	public Double getShareholders() {
		return this.shareholders;
	}
	
	public Double getShareholders_year(){
		return this.shareholders * getAb();
	}


	public void setNetCashFlow(Double netCashFlow) {
		this.netCashFlow = netCashFlow;
	}

	public Double getNetCashFlow() {
		return this.netCashFlow;
	}


	public void setRoe(Double roe) {
		this.roe = roe;
	}

	public Double getRoe() {
		if(this.netAssetsPerShare!=null && this.netAssetsPerShare>0){
			return this.earningsPerShare/this.netAssetsPerShare;
		}else{
			return this.roe;			
		}
	}


	public String requiredValidate() {
		StringBuffer errors = new StringBuffer();
		if (this.stockNo == null || this.stockNo.trim().length() == 0) {
			errors.append(this.getStockNoCaption() + "不能为空。");
		}
		/*
		if (this.stockName == null || this.stockName.trim().length() == 0) {
			errors.append(this.getStockNameCaption() + "不能为空。");
		}
		*/
		if (this.periodType == null || this.periodType.trim().length() == 0) {
			errors.append(this.getPeriodTypeCaption() + "不能为空。");
		}
		if (this.description == null || this.description.trim().length() == 0) {
			errors.append(this.getDescriptionCaption() + "不能为空。");
		}
		if (this.beginDate == null) {
			errors.append(this.getBeginDateCaption() + "不能为空或不是日期格式。");
		}
		if (this.endDate == null) {
			errors.append(this.getEndDateCaption() + "不能为空或不是日期格式。");
		}
		return errors.toString();
	}

	public String outOfRangeValidate() {
		StringBuffer errors = new StringBuffer();
		if (this.reportPk.length() > 24) {
			errors.append(this.getReportPkCaption() + "字符数为"
					+ this.reportPk.length() + ",超过了24个字符的最大限制！");
		}
		if (this.stockNo.length() > 24) {
			errors.append(this.getStockNoCaption() + "字符数为"
					+ this.stockNo.length() + ",超过了24个字符的最大限制！");
		}
		/*
		if (this.stockName.length() > 24) {
			errors.append(this.getStockNameCaption() + "字符数为"
					+ this.stockName.length() + ",超过了24个字符的最大限制！");
		}
		*/
		if (this.periodType.length() > 24) {
			errors.append(this.getPeriodTypeCaption() + "字符数为"
					+ this.periodType.length() + ",超过了24个字符的最大限制！");
		}
		if (this.description.length() > 24) {
			errors.append(this.getDescriptionCaption() + "字符数为"
					+ this.description.length() + ",超过了24个字符的最大限制！");
		}
		return errors.toString();
	}

	public Double getEarnings() {
		double d = 0;
		/*
		if(this.netProfitPlus < 0.0000001){
			double op = 0;
			if(isFinance(stockNo) == -1){
				//对于非金融企业收入 = 主营业务利润 - 销售费用 - 管理费用 - 财务费用
				op = primeOperatingProfit-salesExpense-operatingExpense-financeExpense;
			}else{
				//对于金融企业，主营业务利润为负数
				//收入 = 营业利润 = 主营业务利润 + 其他业务利润 - 营业费用 - 管理费用 - 财务费用
				op = operatingProfit;
			}
			d = op*0.75;
		}else{
			d = this.netProfitPlus;
		}
		*/
		
		//因为自2011-08-06后，从新浪无法下载主营业务利润和扣除非经常性损益的利润，只能就用营业利润替代
		
		if(this.netProfitPlus < 0.0000001){
			d = this.operatingProfit * 0.75;
		}else{
			d = this.netProfitPlus;
		}		
		/*
		 * 对于季报，用a和b来评估全年的收益
		 */
		
		double earns = (d + this.depreciationAssets - this.purchaseAssets) * getAb();
		
		
		//System.out.println(this.getTheYear() + this.getTheMonth() + " earns = " + earns/100000000);
		//System.out.println("(" + d/100000000 + "+" + this.depreciationAssets/100000000 + "-" + this.purchaseAssets/100000000+ ")*" + a +"/" + b);
		//System.out.println(""); 
		
		return earns;
	}
	
	private double getAb(){
		int a=4;
		int b=4;   
		if("03".equals(theMonth)){  
			b = 1;
		}else if("06".equals(theMonth)){
			b = 2;
		}else if("09".equals(theMonth)){
			b = 3;
		}
		return a/b;
	}

	public void setEarnings(Double earnings) {
		this.earnings = earnings;
	}

	public Double getEarningsAvg() {
		return earningsAvg;
	}

	public void setEarningsAvg(Double earningsAvg) {
		this.earningsAvg = earningsAvg;
	}

	public Double getEarningsGrowthRatio() {
		return earningsGrowthRatio;
	}

	public void setEarningsGrowthRatio(Double earningsGrowthRatio) {
		this.earningsGrowthRatio = earningsGrowthRatio;
	}
	
	public String getFinanceSummary(){
		StringBuffer sb = new StringBuffer();
		sb.append("netAssetsPerShare = " + this.netAssetsPerShare + "\n");
		sb.append("earningsPerShare = " + this.earningsPerShare + "\n");
		sb.append("netCashFlowPerShare = " + this.netCashFlowPerShare + "\n");
		sb.append("capitalFundPerShare = " + this.capitalFundPerShare + "\n");
		return sb.toString();
	}

	public String getBalanceSheet(){
		StringBuffer sb = new StringBuffer();
		sb.append("reportPk = " + this.getReportPk() + "\n");
		sb.append("stockNo = " + this.getStockNo() + "\n");
		sb.append("stockName = " + this.getStockName() + "\n");
		sb.append("periodType = " + this.getPeriodType() + "\n");
		sb.append("description = " + this.getDescription() + "\n");
		sb.append("beginDate = " + this.getBeginDate() + "\n");
		sb.append("endDate = " + this.getEndDate() + "\n");
		sb.append("cash = " + this.getCash() + "\n");
		sb.append("inventories = " + this.getInventories() + "\n");
		sb.append("accountsReceivable = " + this.getAccountsRreceivable() + "\n");
		sb.append("notesReceivable = " + this.getNotesReceivable() + "\n");
		sb.append("otherReceivable = " + this.getOtherReceivable() + "\n");
		sb.append("totalCurrentAssets = " + this.getTotalCurrentAssets() + "\n");
		sb.append("payables = " + this.getPayables() + "\n");
		sb.append("currentLiabilities = " + this.getCurrentLiabilities() + "\n");
		sb.append("totalAssets = " + this.getTotalAssets() + "\n");
		sb.append("totalLiabilities = " + this.getTotalLiabilities() + "\n");
		
		return sb.toString();
	}

	public String getCashFlow(){
		StringBuffer sb = new StringBuffer();
		sb.append("reportPk = " + this.getReportPk() + "\n");
		sb.append("stockNo = " + this.getStockNo() + "\n");
		sb.append("stockName = " + this.getStockName() + "\n");
		sb.append("periodType = " + this.getPeriodType() + "\n");
		sb.append("description = " + this.getDescription() + "\n");
		sb.append("beginDate = " + this.getBeginDate() + "\n");
		sb.append("endDate = " + this.getEndDate() + "\n");
		sb.append("purchaseAssets = " + this.getPurchaseAssets() + "\n");
		sb.append("depreciationAssets = " + this.getDepreciationAssets() + "\n");
		
		return sb.toString();
	}

	public String getProfitStatement(){
		StringBuffer sb = new StringBuffer();
		sb.append("reportPk = " + this.getReportPk() + "\n");
		sb.append("stockNo = " + this.getStockNo() + "\n");
		sb.append("stockName = " + this.getStockName() + "\n");
		sb.append("periodType = " + this.getPeriodType() + "\n");
		sb.append("description = " + this.getDescription() + "\n");
		sb.append("beginDate = " + this.getBeginDate() + "\n");
		sb.append("endDate = " + this.getEndDate() + "\n");
		sb.append("primeOperatingRevenue = " + this.getPrimeOperatingRevenue() + "\n");
		sb.append("operatingProfit = " + this.operatingProfit + "\n");
		sb.append("netProfit = " + this.getNetProfit() + "\n");
		
		return sb.toString();
	}

	public String getFinancialGuide(){
		StringBuffer sb = new StringBuffer();
		sb.append("reportPk = " + this.getReportPk() + "\n");
		sb.append("stockNo = " + this.getStockNo() + "\n");
		sb.append("stockName = " + this.getStockName() + "\n");
		sb.append("periodType = " + this.getPeriodType() + "\n");
		sb.append("description = " + this.getDescription() + "\n");
		sb.append("beginDate = " + this.getBeginDate() + "\n");
		sb.append("endDate = " + this.getEndDate() + "\n");
		sb.append("primeOperatingProfit = " + this.getPrimeOperatingProfit() + "\n");
		sb.append("netProfitPlus = " + this.getNetProfitPlus() + "\n");
		
		return sb.toString();
	}
	
	
	
	public Double getPrimeOperatingRevenue() {
		return primeOperatingRevenue;
	}

	public void setPrimeOperatingRevenue(Double primeOperatingRevenue) {
		this.primeOperatingRevenue = primeOperatingRevenue;
	}

	public Double getPrimeOperatingProfit() {
		return primeOperatingProfit;
	}

	public Double getGrossProfit() {
		if(this.primeOperatingRevenue != null && this.primeOperatingRevenue > 0){
			return this.primeOperatingProfit/this.primeOperatingRevenue;			
		}else{
			return 0.0;
		}
	}

	public Double getGrossProfitPlus() {
		if(this.primeOperatingRevenue != null && this.primeOperatingRevenue > 0){
			return this.operatingProfit/this.primeOperatingRevenue;			
		}else{
			return 0.0;
		}
	}
	
	public void setPrimeOperatingProfit(Double primeOperatingProfit) {
		this.primeOperatingProfit = primeOperatingProfit;
	}


	public Double getInventoryRatio(){
		if(this.primeOperatingRevenue != null && this.primeOperatingRevenue > 0){
			return this.inventories/this.primeOperatingRevenue;			
		}else{
			return 0.0;
		}
	}
	public Double getAccountsRreceivableRatio(){
		if(this.primeOperatingRevenue != null && this.primeOperatingRevenue > 0){
			return this.accountsRreceivable/this.primeOperatingRevenue;			
		}else{
			return 0.0;
		}
	}
	
	public String toString(){
		return this.reportPk + "," + this.stockNo + "," + this.theYear + "," + this.theMonth + "," + this.description;
	}
	
}
