package com.rhb.sas.firm;

import java.util.List;

import com.rhb.af.business.FindBusiness;
import com.rhb.sas.util.Statistics;
import com.rhb.sas.util.Tools;
import com.rhb.sas.util.Value;
import com.rhb.sas.report.bean.Report;
import com.rhb.sas.report.bean.ReportQuery;
import com.rhb.sas.report.profitstatement.ProfitStatement;
import com.rhb.sas.report.profitstatement.ProfitStatementQuery;
import com.rhb.sas.stock.bean.Stock;

public class Firm {
	private Stock stock = null;
	private List<ProfitStatement> profitStatements = null;
	private List<Report> reports = null;
	public List<Report> getReports() {
		return reports;
	}
	private Statistics revenue = null; //销售收入
	private Statistics profit = null; // 销售利润
	private Statistics receivable = null; //应收款
	private Statistics accountsReceivable = null; //应收帐款
	private Statistics liabilities = null; //负债
	private Statistics cashflow = null; // 现金流
	private Statistics purchaseAssets = null; // 固定资产投入
	private Statistics grossProfitRate = null; //毛利率
	private Statistics roes = null; //净资产收益率
	//private int unit = 100000000; //金额以亿为单位；
	private int unit = 1; //金额以万为单位；
	private int endYear = 2012; //
	private double agr = 0.3; //增长率最高为30%
	
	private int stars=0;
	private String starsNote = null;
	
	public Firm(String stockNo, FindBusiness fb){

		stock = (Stock)fb.findByPK(Stock.class, stockNo);
		
		ProfitStatementQuery psq = new ProfitStatementQuery();
		psq.empty();
		psq.setCount(10000);
		psq.setStockNo(stockNo);
		profitStatements = fb.findByQuery(psq).getList();
		
		ReportQuery rq = new ReportQuery();
		rq.empty();
		rq.setCount(10000);
		rq.setStockNo(stockNo);
		reports = fb.findByQuery(rq).getList();
		
	}
	
	public void init(int endYear, int period, String month){
		this.endYear = endYear;
		
		roes = new Statistics(period);
		for(int i=0; i<period; i++){
			roes.setData(i, getROE(Integer.toString(endYear-period+1+i),month));
		}
		System.out.println("roe:");
		System.out.println(roes);
		
		revenue = new Statistics(period);
		for(int i=0; i<period; i++){
			String y = Integer.toString(endYear-period+1+i);
			//System.out.println("endyear=" + endYear);
			//System.out.println("period=" + period);
			//System.out.println("y=" + y);
			revenue.setData(i, getOperatingRevenue(y,month));			
		}
		System.out.println("operatingRevenue:");
		System.out.println(revenue);
		
		profit = new Statistics(period);
		for(int i=0; i<period; i++){
			profit.setData(i, getProfit(Integer.toString(endYear-period+1+i),month));			
		}
		System.out.println("profit:");
		System.out.println(profit);
		
		receivable = new Statistics(period);
		for(int i = 0; i<period; i++){
			receivable.setData(i, getReceivable(Integer.toString(endYear-period+1+i),month));
		}
		System.out.println("receivable:");
		System.out.println(receivable);

		accountsReceivable = new Statistics(period);
		for(int i = 0; i<period; i++){
			accountsReceivable.setData(i, getAccountsReceivable(Integer.toString(endYear-period+1+i),month));
		}
		System.out.println("accountsReceivable:");
		System.out.println(accountsReceivable);

		
		liabilities = new Statistics(period);
		for(int i = 0; i<period; i++){
			liabilities.setData(i, getLiabilities(Integer.toString(endYear-period+1+i),month));
		}
		System.out.println("liabilities:");
		System.out.println(liabilities);

		cashflow = new Statistics(period);
		for(int i = 0; i<period; i++){
			cashflow.setData(i, getCashflow(Integer.toString(endYear-period+1+i),month));
		}
		System.out.println("cashflow:");
		System.out.println(cashflow);

		purchaseAssets = new Statistics(period);
		for(int i = 0; i<period; i++){
			purchaseAssets.setData(i, getPurchaseAssets(Integer.toString(endYear-period+1+i),month));
		}
		System.out.println("purchaseAssets:");
		System.out.println(purchaseAssets);

		
		grossProfitRate = new Statistics(period);
		for(int i = 0; i<period; i++){
			grossProfitRate.setData(i, profit.getDate(i)/revenue.getDate(i));
		}
		System.out.println("grossProfitRate:");
		System.out.println(grossProfitRate);
		
		this.setStars();
		
	}
	
	public int getStars(){
		return this.stars;
	}
	
	public String getStockNo(){
		return stock.getStockNo();
	}
	
	public String getStockName(){
		return stock.getStockName();
	}
	
	public double getValueDiscount(){
		return this.getMarketValue()/this.getValue();
	}
	
	public double getValue(){
		return this.getValue(this.getProfit(Integer.toString(endYear),"12"), this.getAGROfProfit()>agr ? agr : this.getAGROfProfit());
	}
	
	public double getValue(int years){
		return this.getValue(this.getProfit(Integer.toString(endYear),"12"), this.getAGROfProfit()>agr ? agr : this.getAGROfProfit(), years);		
	}
	
	public double getAvarageOfProfit(){
		return profit.getAvarage();
	}
	
	public double getAvarageOfCashflow(){
		return cashflow.getAvarage();
	}
	
	public double getAvarageOfGrossProfitRate(){
		return grossProfitRate.getAvarage();
	}

	public double getAvarageOfRecent2YearGrossProfitRate(){
		return grossProfitRate.getAvarageOfRecent2Year();
	}
	
	public double getAGROfGrossProfitRate(){
		return grossProfitRate.getAGR();
	}
	public double getAGROfGrossProfitRateOfRecent2Year(){
		return grossProfitRate.getAGROfRecent2Year();
	}	
	public boolean sustainedOfGrossProfitRate(){
		return grossProfitRate.sustained();
	}
	
	public double getAGROfCashflow(){
		return cashflow.getAGR();
	}
	
	public double getAGROfCashflowOfRecent2Year(){
		return cashflow.getAGROfRecent2Year();
	}
	
	public boolean sustainedOfCashflow(){
		return cashflow.sustained();
	}
	
	public double getAGROfLiabilities(){
		return liabilities.getAGR();
	}
	public double getAGROfLiabilitiesOfRecent2Year(){
		return liabilities.getAGROfRecent2Year();
	}
	
	public boolean sustainedOfLiabilities(){
		return liabilities.sustained();
	}

	
	public double getAGROfReceivable(){
		return receivable.getAGR();
	}
	
	public boolean sustainedOfReceivable(){
		return receivable.sustained();
	}
	
	public double getAGROfOperatingRevenue(){
		return revenue.getAGR();
	}

	public double getAGROfOperatingRevenueOfRecent2Year(){
		return revenue.getAGROfRecent2Year();
	}
	
	public boolean sustainedOfOperatingRevenue(){
		return revenue.sustained();
	}
	
	public double getAvarageOfROE(){
		return roes.getAvarage();
	}

	public double getAvarageOfRecent2YearROE(){
		return roes.getAvarageOfRecent2Year();
	}
	
	public double getAGROfProfit(){
		return profit.getAGR();
	}
	public double getAGROfProfitOfRecent2Year(){
		return profit.getAGROfRecent2Year();
	}	
	public boolean sustainedOfProfit(){
		return profit.sustained();
	}
	
	public double getMarketValue(){
		return stock.getMarketValue();
	}
	
	public double getValue(double profit, double riseRatioOfDecade){
		return Value.calculate(profit, riseRatioOfDecade);
	}
	
	public double getValue(double profit, double riseRatioOfDecade,int years){
		return Value.calculate(profit, riseRatioOfDecade,years);
	}
	
	public double getCashflow(String year, String month){
		double cashflow = 0.0;
		for(Report report : reports){
			if(report != null && year.equals(report.getTheYear()) && month.equals(report.getTheMonth())){
				//System.out.println(report.getTheYear());
				cashflow = report.getNetCashFlow();
				break;
			}
		}
		return cashflow/unit;		
	}


	public double getPurchaseAssets(String year, String month){
		double assets = 0.0;
		for(Report report : reports){
			if(report != null && year.equals(report.getTheYear()) && month.equals(report.getTheMonth())){
				//System.out.println(report.getTheYear());
				assets = report.getPurchaseAssets();
				break;
			}
		}
		return assets/unit;		
	}
	
	public double getLiabilities(String year, String month){
		double liabilities = 0.0;
		for(Report report : reports){
			if(report != null && year.equals(report.getTheYear()) && month.equals(report.getTheMonth())){
				liabilities = report.getTotalLiabilities();
				break;
			}
		}
		return liabilities/unit;		
	}

	public double getROE(String year, String month){
		double roe = 0.0;
		for(Report report : reports){
			if(report != null && year.equals(report.getTheYear()) && month.equals(report.getTheMonth())){
				roe = report.getRoe();
				break;
			}		
		}
		return roe;
	}
	
	public double getReceivable(String year, String month){
		double receivable = 0.0;
		for(Report report : reports){
			if(report != null && year.equals(report.getTheYear()) && month.equals(report.getTheMonth())){
				receivable = report.getReceivable();
				break;
			}
		}
		return receivable/unit;		
	}

	public double getAccountsReceivable(String year, String month){
		double receivable = 0.0;
		for(Report report : reports){
			if(report != null && year.equals(report.getTheYear()) && month.equals(report.getTheMonth())){
				receivable = report.getAccountsRreceivable();
				break;
			}
		}
		return receivable/unit;		
	}

	
	public double getOperatingRevenue(String year, String month){
		double operatingRevenue = 0.0;
		for(ProfitStatement ps : profitStatements){
			if(ps != null && year.equals(ps.getTheYear()) && month.equals(ps.getTheMonth())){
				if(ps.getOperatingRevenue()>0){
					operatingRevenue = ps.getOperatingRevenue();
				}else{
					operatingRevenue = ps.getAllOperatingRevenue();						
				}
				break;
			}
		}
		
//		System.out.println("operatingRevenue =  " + operatingRevenue);
		
		return operatingRevenue/unit;		
	}

	public double getLatestOperatingRevenue(){
		return revenue.getLatestData()/unit;
	}

	public double getLatestProfit(){
		return profit.getLatestData()/unit;
	}
	
	public double getLatestCashflow(){
		return cashflow.getLatestData()/unit;
	}
	
	public double getLatestReceivable(){
		return receivable.getLatestData()/unit;
	}
	
	public double getLatestPurchaseAssets(){
		return purchaseAssets.getLatestData()/unit;
	}
	
	public double getProfit(String year, String month){
		double profit = 0.0;
		for(ProfitStatement ps : profitStatements){
			if(ps != null && year.equals(ps.getTheYear()) && month.equals(ps.getTheMonth())){
				if(ps.getOperatingRevenue()>0){
					profit = ((ps.getOperatingRevenue() - ps.getOperatingCost() - ps.getOperatingExpense() - ps.getSalesExpense() - ps.getFinanceExpense() - ps.getTax()));
				}else{
					profit = ((ps.getAllOperatingRevenue() - ps.getAllOperatingCost()));						
				}
				break;
			}
		}
		return profit/unit;
	}
	
	public String toStringTitle(){
		StringBuffer sb = new StringBuffer();
		sb.append("stockNo");
		sb.append(",");
		sb.append("stockName");
		sb.append(",");
		sb.append("stars");
		sb.append(",");
		sb.append("MarketValue");
		sb.append(",");
		sb.append("OperatingRevenue");
		sb.append(",");
		sb.append("Profit");
		sb.append(",");
		sb.append("Cashflow");
		sb.append(",");
		sb.append("Receivable");
		sb.append(",");
		sb.append("PurchaseAssets");
		sb.append(",");
		sb.append("MarketValue/Profit");
		sb.append(",");
		sb.append("Profit/OperatingRevenue"); 
		sb.append(",");
		sb.append("PurchaseAssets/Profit");
		sb.append(",");
		sb.append("Receivable/OperatingRevenue");
		sb.append(",");
		sb.append("Cashflow/OperatingRevenue");
		return sb.toString();
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(stock.getStockNo());
		sb.append(",");
		sb.append(stock.getStockName());
		sb.append(",");
		sb.append(this.stars);
		sb.append(",");
		sb.append(this.getMarketValue());
		sb.append(",");
		sb.append(this.getLatestOperatingRevenue());
		sb.append(",");
		sb.append(this.getLatestProfit());
		sb.append(",");
		sb.append(this.getLatestCashflow());
		sb.append(",");
		sb.append(this.getLatestReceivable());
		sb.append(",");
		sb.append(this.getLatestPurchaseAssets());
		sb.append(",");
		sb.append(this.getMarketValue()/this.getLatestProfit()*10000);
		sb.append(",");
		sb.append(this.getLatestProfit()/this.getLatestOperatingRevenue());
		sb.append(",");
		sb.append(this.getLatestPurchaseAssets()/this.getLatestProfit());
		sb.append(",");
		sb.append(this.getLatestReceivable()/this.getLatestOperatingRevenue());
		sb.append(",");
		sb.append(this.getLatestCashflow()/this.getLatestOperatingRevenue());
		
		return sb.toString();
	}
		
	public void setStars(){
		StringBuffer sb = new StringBuffer();
		int stars = 0;
		//在有盈利和现金流为正的情况下，评星级才有意义
		if(this.getLatestProfit()>0 && this.getLatestCashflow()>0){
			//销售收入持续增长而且利润也持续增长，加一颗星； 近2年销售收入和利润增长率大于20%，加二颗星
			if(this.sustainedOfOperatingRevenue() && this.sustainedOfProfit()){
				stars ++;
				sb.append("销售收入持续增长而且利润也持续增长加一颗星；");
				if(this.getAGROfOperatingRevenueOfRecent2Year()>0.2 && this.getAGROfProfitOfRecent2Year()>0.2){
					stars ++;
					sb.append("销售收入和利润的增长大于20%再加一颗星；");
				}
			}
//			
//			
//			//市盈率：越低越好。越低，买入的风险越低。低于20倍的加一个星，超过50倍的减一颗星，超过100倍减二星
//			if(this.getMarketValue()/this.getLatestProfit()*10000<20){
//				stars ++;
//				sb.append("市盈率低于20倍加一个星；");
//			}
//			if(this.getMarketValue()/this.getLatestProfit()*10000>50){
//				stars --;
//				sb.append("市盈率超过50倍减一颗星；");
//				if(this.getMarketValue()/this.getLatestProfit()*10000>100){
//					stars --;
//					sb.append("市盈率超过100倍再减一颗星；");
//				}
//			}
			
			//营业利润率：越高越好，高于10%加一个星，高于30%加二星
			if(this.getLatestProfit()/this.getLatestOperatingRevenue()>0.1){
				stars ++;
				sb.append("营业利润率高于10%加一个星；");
				if(this.getLatestProfit()/this.getLatestOperatingRevenue()>0.3){
					stars ++;
					sb.append("营业利润率高于30%再加一个星；");
				}
			}
			
			
			//固定资产投入占利润的比例：越小越好。越小，可用于分红的利润就越多；越高，说明投入是一个无底洞。低于10%的加二个星，低于20%的加一星，超过50%，减一星，超过80%的要减二颗星
			if(this.getLatestPurchaseAssets()/this.getLatestProfit()<0.2){
				stars ++;
				sb.append("固定资产投入占利润的比例低于20%加一个星；");
				if(this.getLatestPurchaseAssets()/this.getLatestProfit()<0.1){
					stars ++;
					sb.append("固定资产投入占利润的比例低于10%再加一个星；");
				}
			}
			if(this.getLatestPurchaseAssets()/this.getLatestProfit()>0.5){
				stars --;
				sb.append("固定资产投入占利润的比例超过50%减一颗星；");
				if(this.getLatestPurchaseAssets()/this.getLatestProfit()>0.8){
					stars --;
					sb.append("固定资产投入占利润的比例超过80%再减一颗星；");
				}
			}

			//客户欠款率：越小越好。越小，说明市场地位越强，利润越有效；越高，企业经营风险越大。低于10%的加二颗星，低于20%加一颗星，超过50%的要减一颗星，超过80%的减二星
			if(this.getLatestReceivable()/this.getLatestOperatingRevenue()<0.2){
				stars ++;
				sb.append("客户欠款率低于20%加一颗星；");
				if(this.getLatestReceivable()/this.getLatestOperatingRevenue()<0.1){
					stars ++;
					sb.append("客户欠款率低于10%再加一颗星；");
				}			}
			if(this.getLatestReceivable()/this.getLatestOperatingRevenue()>0.5){
				stars --;
				sb.append("客户欠款率超过50%减一颗星；");
				if(this.getLatestReceivable()/this.getLatestOperatingRevenue()>0.8){
					stars --;
					sb.append("客户欠款率超过80%再减一颗星；");
				}
			}
			
			//销售现金比率：越大越好。越大，说明利润有现金流的保证，越可靠。高于10%的加一个星，高于30%的，再加一颗星
			if(this.getLatestCashflow()/this.getLatestOperatingRevenue()>0.1){
				stars ++;
				sb.append("销售现金比率10%加一个星；");
				if(this.getLatestCashflow()/this.getLatestOperatingRevenue()>0.3){
					stars ++;
					sb.append("销售现金比率30%再加一个星；");
				}		
			}
		}else{
				stars = -99;
				sb.append("亏损或现金流为负，无法评星级！");
				
		}
		
		this.stars = stars;
		this.starsNote = sb.toString();
	}
	
	public String getStockInfo(){
		StringBuffer sb = new StringBuffer();
		sb.append(stock.getStockNo());
		sb.append(",");
		sb.append(stock.getStockName());
		sb.append(",");
		sb.append(this.stars);
		sb.append(",");
		sb.append(this.getMarketValue()/this.getLatestProfit()*10000);
		sb.append(",");
		sb.append(this.getMarketValue());
		sb.append(",");
		sb.append(Tools.getDate(stock.getMadeDate(),"yyyy-MM-dd"));
		sb.append(",");
		sb.append(this.endYear);
		sb.append(",");
		sb.append(this.starsNote);
		return sb.toString();
	}
	
	public Stock getStock() {
		return stock;
	}

	public String getData(int beginYear, int endYear){
		StringBuffer sb = new StringBuffer();
		String month = "";
		String year = "";
		for(int i = beginYear; i<=endYear; i++){
			year = Integer.toString(i);	
			for(int j=3; j<=12; j=j+3){
				if(j==3) month="03";
				if(j==6) month="06";
				if(j==9) month="09";
				if(j==12) month="12";
				sb.append(stock.getStockNo());
				sb.append(",");
				sb.append(stock.getStockName());
				sb.append(",");
				sb.append(year);
				sb.append(",");
				sb.append(month);
				sb.append(",");
				sb.append(this.getOperatingRevenue(year, month));
				sb.append(",");
				sb.append(this.getProfit(year,month));
				sb.append(",");
				sb.append(this.getReceivable(year, month));
				sb.append(",");
				sb.append(this.getLiabilities(year, month));
				sb.append(",");
				sb.append(this.getCashflow(year, month));
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	public String getDataTitle(){
		StringBuffer sb = new StringBuffer();
		sb.append("stockNo");
		sb.append(",");
		sb.append("stockName");
		sb.append(",");
		sb.append("year");
		sb.append(",");
		sb.append("month");
		sb.append(",");
		sb.append("OperatingRevenue");
		sb.append(",");
		sb.append("Profit");
		sb.append(",");
		sb.append("Receivable");
		sb.append(",");
		sb.append("Liabilities");
		sb.append(",");
		sb.append("Cashflow");
		sb.append("\n");
		return sb.toString();
	}
	
	public void getMarketValueAndOperatingRevenue(){
		/* 获得股票的市值和营收
		 * select stock.stockNo,stock.marketValue,a.primeOperatingRevenue/10000 as revenue,a.operatingprofit/10000 as profit,a.netprofit/10000 as netProfit from stock inner join (select stockNo,primeOperatingRevenue,operatingprofit,netprofit from report where theyear='2014') as a on stock.stockNo=a.stockNo
		 */
	}

}
