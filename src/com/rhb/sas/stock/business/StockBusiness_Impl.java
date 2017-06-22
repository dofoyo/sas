package com.rhb.sas.stock.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.rhb.af.dao.GeneralDAO;
import com.rhb.af.exception.*;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.report.bean.Report;
import com.rhb.sas.report.business.ReportBusiness;
import com.rhb.sas.stock.bean.*;
import com.rhb.sas.util.*;


public class StockBusiness_Impl implements StockBusiness {
	static Logger logger = Logger.getLogger(StockBusiness_Impl.class);
	
	private GeneralDAO generalDAO;
	private ReportBusiness reportBusiness;
	private Sensor sensor;

	public GeneralDAO getGeneralDAO() {
		return generalDAO;
	}

	public void setGeneralDAO(GeneralDAO generalDAO) {
		this.generalDAO = generalDAO;
	}
	

	public void delete(Stock ex) throws CanNotBeDeletedException{
		this.generalDAO.delete(ex);
	}

	public void save(Stock ex)  throws DuplicateException,RequiredException,OutOfRangeException{
		String required = ex.requiredValidate();
		if(!"".equals(required)){
			throw new RequiredException(required);
		}
		String outOfRange = ex.outOfRangeValidate();
		if(!"".equals(outOfRange)){
			throw new OutOfRangeException(outOfRange);
		}
		
		this.generalDAO.save(ex);
	}


	public List findByQuery(StockQuery qe, int start, int count) {	
		qe.setStart(start);
		qe.setCount(count);
		return this.generalDAO.findByQuery(qe);
	}
	
	public int getAllCount_findByQuery(StockQuery qe) {	
		return this.generalDAO.countByQuery(qe);
	}

	public void updateFromReports(){
		StockQuery sq = new StockQuery();
		sq.empty();
		sq.setStart(0);
		sq.setCount(10000);
		sq.setDeleted("0");
		List stocks = this.generalDAO.findByQuery(sq);
		updateFromReports(stocks);
	}

	public void updateFromReports(String[] stockNos){
		for(int i=0; i<stockNos.length; i++){
			System.out.println(Integer.toString(i+1) + "/" + stockNos.length);
			updateFromReports(stockNos[i]);
		}
		
	}
	
	public void updateFromReports(String stockNo){
		updateFromReports((Stock)this.generalDAO.findByPK(Stock.class,stockNo));
	}

	public void updateFromReports(List<Stock> stocks){
		for(int i=0; i<stocks.size(); i++){
			System.out.println(Integer.toString(i+1) + "/" + stocks.size());
			updateFromReports(stocks.get(i));
		}
	}

	
	
	/*
	 * 根据年报计算以下3年的移动平均值
	 * latestEarnings 股东收益(上一期的， 是年度的)
	 * latestEarnings_quarter 股东收益(当前的，可能是根据季度推算的，也可能是最新年度的)
	 * earningsGrowthRatio -- 股东收益增长率  
	 * decadeEarningsGrowthRatio -- 股东收益增长率(计算价值用),是earningsGrowthRatio的修正值
	 * roe   -- 净资产收益率 
	 * netCashFlowPerShare -- 每股现金含量
	 * grossProfit  -- 主营业务毛利率
	 * grossProfitPlus -- 营业毛利率
	 * inventoryRatio -- 库存率
	 * accountsRreceivableRatio -- 应收帐款率
	 */
	
	public void updateFromReports(Stock stock){
		if(stock == null){
			return;
		}
		
		sensor.setMessage("updateFromReports: " + stock.getStockNo());
		/*
		ReportQuery rq = new ReportQuery();
		rq.setStart(0);
		rq.setCount(1000);
		rq.setStockNo(stock.getStockNo());

		List reports = this.generalDAO.findByQuery(rq);
		*/
		List reports = reportBusiness.getAnnualReports(stock.getStockNo());
		
		if(reports==null || reports.size()==0){
			return;
		}
		
		Collections.sort(reports, new Comparator(){
			public int compare(Object object1, Object object2){
				Date date1 = ((Report)object1).getEndDate();
				Date date2 = ((Report)object2).getEndDate();
				return -date1.compareTo(date2);
			}
		});
		
		double latestEarnings = 0.0;
		if(reports.size()>2){
			Report before = (Report)reports.get(1);
			if(before.getEarnings()!=null && before.getEarningsAvg()!=null){
				latestEarnings = (before.getEarnings()>0 ? before.getEarnings() : before.getEarningsAvg())/100000000;
			}
		}
		
		Report report = (Report)reports.get(0);
		if(report.getEarnings()==null || report.getEarningsAvg()==null){
			return;
		}
		//System.out.println("latest report = "  + report.getProfitStatement());
		
		stock.setReportDate(report.getEndDate());
		
		//double latestNetProfitPlus = report.getNetProfitPlus()/100000000;
		//double earningsAvg = report.getEarningsAvg()/100000000;
		double latestEarnings_quarter = (report.getEarnings()>0 ? report.getEarnings() : report.getEarningsAvg())/100000000;
		

		double earningsGrowthRatio = 0.0;  //只需最近3年的移动平均值
		double latestEarningsGrowthRatio = 0.0; //最近1年的移动平均值
		double roe = 0.0;
		double dzhValue = 0.0;  //最近的年度经营现金流
		double grossProfit = 0.0;
		double grossProfitPlus = 0.0;
		double inventoryRatio = 0.0;
		double accountsRreceivableRatio = 0.0;
		
		double temp_earningsGrowthRatio;
		double temp_roe;
		double temp_dzhValue;
		int temp_dzhValue_flag = 0;
		double temp_grossProfit;
		double temp_grossProfitPlus;
		double temp_inventoryRaito;
		double temp_accountsRreceivableRatio;
		
		for(int i=0; i<reports.size() && i<3; i++){
			temp_earningsGrowthRatio = ((Report)reports.get(i)).getEarningsGrowthRatio()==null ? 0.0 : ((Report)reports.get(i)).getEarningsGrowthRatio();
			temp_roe = ((Report)reports.get(i)).getRoe()==null ? 0.0 : ((Report)reports.get(i)).getRoe();
			//temp_dzhValue = ((Report)reports.get(i)).getShareholders_year()==null? 0.0 : ((Report)reports.get(i)).getShareholders_year();
			temp_dzhValue = ((Report)reports.get(i)).getNetCashFlow();
			temp_grossProfit  = ((Report)reports.get(i)).getGrossProfit();
			temp_grossProfitPlus  = ((Report)reports.get(i)).getGrossProfitPlus();
			temp_inventoryRaito = ((Report)reports.get(i)).getInventoryRatio();
			temp_accountsRreceivableRatio = ((Report)reports.get(i)).getAccountsRreceivableRatio();		
			if(i==0){
				earningsGrowthRatio = temp_earningsGrowthRatio;
				latestEarningsGrowthRatio = temp_earningsGrowthRatio;
				roe = temp_roe;
				grossProfit = temp_grossProfit;
				grossProfitPlus = temp_grossProfitPlus;
				inventoryRatio = temp_inventoryRaito;
				accountsRreceivableRatio = temp_accountsRreceivableRatio;							
			}else{
				earningsGrowthRatio = (earningsGrowthRatio + temp_earningsGrowthRatio)/2;
				roe = (roe + temp_roe)/2;
				grossProfit = (grossProfit + temp_grossProfit)/2;
				grossProfitPlus = (grossProfitPlus + temp_grossProfitPlus)/2;
				inventoryRatio = (inventoryRatio + temp_inventoryRaito)/2;
				accountsRreceivableRatio = (accountsRreceivableRatio + temp_accountsRreceivableRatio)/2;											
			}
			if("12".equals(((Report)reports.get(i)).getTheMonth()) && temp_dzhValue_flag == 0){
				dzhValue = temp_dzhValue;
				temp_dzhValue_flag = 1;
			}
		}
		
		//double decadeEarningsGrowthRatio = earningsGrowthRatio;
		double decadeEarningsGrowthRatio = earningsGrowthRatio<latestEarningsGrowthRatio ? earningsGrowthRatio : latestEarningsGrowthRatio;
		if(stock.getStockNo().indexOf("300")==0){  //针对创业板
			if(earningsGrowthRatio > 0.5){
				decadeEarningsGrowthRatio = 0.5;  //10年的年均增长率50%封顶
			}else if(earningsGrowthRatio < 0){
				decadeEarningsGrowthRatio = 0.001;
			}			
		}else if(stock.getStockNo().indexOf("002")==0){  //针对创业板
			if(earningsGrowthRatio > 0.3){
				decadeEarningsGrowthRatio = 0.3;  //10年的年均增长率30%封顶
			}else if(earningsGrowthRatio < 0){
				decadeEarningsGrowthRatio = 0.001;
			}			
		}else{
			if(earningsGrowthRatio > 0.2){
				decadeEarningsGrowthRatio = 0.2;   //10年的年均增长率20%封顶
			}else if(earningsGrowthRatio < 0){
				decadeEarningsGrowthRatio = 0.001;
			}			
		}

		
		//stock.setLatestNetProfitPlus(latestNetProfitPlus);
		stock.setLatestEarnings(latestEarnings);
		stock.setLatestEarnings_quarter(latestEarnings_quarter);
		stock.setEarningsGrowthRatio(earningsGrowthRatio);
		stock.setDecadeEarningsGrowthRatio(decadeEarningsGrowthRatio);
		stock.setRoe(roe);
		stock.setDzhValue(dzhValue);
		stock.setGrossProfit(grossProfit);
		stock.setGrossProfitPlus(grossProfitPlus);
		stock.setInventoryRatio(inventoryRatio);
		stock.setAccountsRreceivableRatio(accountsRreceivableRatio);
		//stock.setEarningsAvg(earningsAvg);
		
		
		//----计算现金流
		double[] temp_netcashflow = {0.0,0.0,0.0};
		double[] temp_netprofitplus  = {0.0,0.0,0.0};
		
		if(!((Report)reports.get(0)).getTheMonth().equals("12")){
			//String theyear = ((Report)reports.get(0)).getTheYear();
			//String themonth = ((Report)reports.get(0)).getTheMonth();
			//System.out.println("removed " + theyear + themonth);
			reports.remove(0);  //只需要年度的；
		}
		
		for(int i=0; i<reports.size() && i<3; i++){
			temp_netcashflow[i] = ((Report)reports.get(i)).getNetCashFlow();
			temp_netprofitplus[i] = ((Report)reports.get(i)).getNetProfitPlus();
			//System.out.println(((Report)reports.get(i)).getTheYear()) ;
			//System.out.println(((Report)reports.get(i)).getTheMonth());
			//System.out.println(temp_netcashflow[i]);
			//System.out.println(temp_netprofitplus[i]);
		}
		
		double netcashflow  = temp_netcashflow[0] + temp_netcashflow[1] + temp_netcashflow[2];
		double netprofitplus = temp_netprofitplus[0] + temp_netprofitplus[1] + temp_netprofitplus[2];
		String trade = "-";
		if(netcashflow > 0 && netcashflow > netprofitplus*0.9){
			trade = "现金流C";
		}
		if(netcashflow > 0 && netcashflow > netprofitplus){
			if(temp_netcashflow[0]>temp_netcashflow[1] && temp_netcashflow[1]>temp_netcashflow[2]){
				trade = "现金流A";
			}else{
				trade = "现金流B";
			}
		}
		
		//System.out.println("trade = " + trade);
		stock.setTrade(trade);
		stock.setMainBusiness(Convert.toString(netprofitplus/netcashflow,"#.##"));
		
		
		//System.out.println("earningsAvg=" + stock.getEarningsAvg());
		
		this.generalDAO.save(stock);
		
	}

	public void updateValueAndDiscount(){
		StockQuery sq = new StockQuery();
		sq.empty();
		sq.setStart(0);
		sq.setCount(10000);
		sq.setDeleted("0");
		List stocks = this.generalDAO.findByQuery(sq);
		updateValueAndDiscount(stocks);
	}
	
	public void updateValueAndDiscount(List<Stock> stocks){
		for(int i=0; i<stocks.size(); i++){
			System.out.println(Integer.toString(i+1) + "/" + stocks.size());
			updateValueAndDiscount(stocks.get(i));
			updateValueAndDiscountFromQuarterData(stocks.get(i));	
		}
	}

	
	public void updateValueAndDiscount(String[] stockNos){
		for(int i=0; i<stockNos.length; i++){
			System.out.println(Integer.toString(i+1) + "/" + stockNos.length);
			updateValueAndDiscount((Stock)this.generalDAO.findByPK(Stock.class,stockNos[i]));			
			updateValueAndDiscountFromQuarterData((Stock)this.generalDAO.findByPK(Stock.class,stockNos[i]));
		}
	}

	
	public void updateValueAndDiscount(String stockNo){
		updateValueAndDiscount((Stock)this.generalDAO.findByPK(Stock.class,stockNo));
		updateValueAndDiscountFromQuarterData((Stock)this.generalDAO.findByPK(Stock.class,stockNo));
	}
	
	/*
	 * 计算价值和折价率
	 * 输入：
	 * stock.getLatestEarnings()
	 * stock.getDecadeEarningsGrowthRatio()
	 * stock.getMarketValue()
	 * 输出：
	 * stock.setNextTenYearValue
	 * stock.setAfterTenYearValue
	 * stock.setNextTenYearDiscount
	 * stock.setDiscount
	 */
	public void updateValueAndDiscount(Stock stock){
		if(stock == null){
			return;
		}

		sensor.setMessage("updateValueAndDiscount: " + stock.getStockNo());

		if(stock.getMarketValue() != 0){
			stock.setNetCashFlowPerShare(stock.getDzhValue()/stock.getMarketValue()/100000000);
		}

		sensor.setMessage("marketValue=" + stock.getMarketValue());
		sensor.setMessage("dzhValue=" + stock.getDzhValue());
		sensor.setMessage("cashflowPerShare=" + stock.getNetCashFlowPerShare());

		
		
		if(stock.getDecadeEarningsGrowthRatio()==null ||
				stock.getEarnings()<0 || 
				stock.getDecadeEarningsGrowthRatio()<0){
			stock.setNextTenYearValue(0.0);
			stock.setAfterTenYearValue(0.0);
			stock.setNextTenYearDiscount(0.0);
			stock.setDiscount(0.0);
			this.generalDAO.save(stock);
			return;
		}
		
		double riseRatioAfterTenYear = 0.05;  //未来10年 后 股东权益年均增长率
		
		double longTermInterestRate = 0.09; //未来10年的折现率
		double[] ratio = new double[10]; //未来10年每一年的复利现值系数, 相对于折现率longTermInterestRate
		ratio[0] = 0.9174;
		ratio[1] = 0.8417;
		ratio[2] = 0.7722;
		ratio[3] = 0.7084;
		ratio[4] = 0.6499;
		ratio[5] = 0.5963;
		ratio[6] = 0.5470;
		ratio[7] = 0.5019;
		ratio[8] = 0.4604;
		ratio[9] = 0.4224;
		
		double[] earnings = new double[10]; //未来10年每一年的股东权益现金
		double[] value = new double[10]; //未来10年每一年的股东权益现金 现值
		
		double nextTenYearValue = 0; //未来10年股东权益现金现值合计
		double afterTenYearValue = 0; //未来10年 后 股东权益现金现值
		
		
		//计算未来10年股东权益现金现值合计
		for(int i=0; i<10; i++){
			if(i==0){
				earnings[0] = stock.getEarnings()*(1 + stock.getDecadeEarningsGrowthRatio());				
			}else{
				earnings[i] = earnings[i-1]*(1 + stock.getDecadeEarningsGrowthRatio());
			}
			value[i] = earnings[i] * ratio[i];
			nextTenYearValue += value[i];
		}
		
		//未来10年 后 股东权益现金现值
		afterTenYearValue = (earnings[9]*(1+riseRatioAfterTenYear))/(longTermInterestRate-riseRatioAfterTenYear)*ratio[9];
		
		stock.setNextTenYearValue(nextTenYearValue);
		stock.setAfterTenYearValue(afterTenYearValue);
		
		//System.out.println("nextTenYearValue = " + nextTenYearValue);
		//System.out.println("afterTenYearValue = " + afterTenYearValue);
		double nextTenYearDiscount = 0.0;
		double totalValue = 0.0;
		double discount = 0.0;

		if(nextTenYearValue>0 && afterTenYearValue>0 ){
			nextTenYearDiscount = (stock.getMarketValue()-stock.getNextTenYearValue())/stock.getNextTenYearValue();
			totalValue = stock.getNextTenYearValue() + stock.getAfterTenYearValue();
			discount = (stock.getMarketValue()-totalValue)/totalValue;			
		}
		stock.setNextTenYearDiscount(nextTenYearDiscount);
		stock.setDiscount(discount);


		//System.out.println("nextTenYearDiscount=" + stock.getNextTenYearDiscount());
		//System.out.println("discount=" + stock.getDiscount());
		
		this.generalDAO.save(stock);		
	}
	
	//根据季报计算价值和折价率
	public void updateValueAndDiscountFromQuarterData(Stock stock){
		if(stock == null){
			return;
		}

		//System.out.println("updateValueAndDiscountFromQuarter: " + stock.getStockNo());
		//System.out.println("getLatestEarnings_quarter = " + stock.getLatestEarnings_quarter());
		
		if(stock.getLatestEarnings_quarter()==null || 
				stock.getDecadeEarningsGrowthRatio()==null ||
				stock.getLatestEarnings_quarter()<0 || 
				stock.getDecadeEarningsGrowthRatio()<0){
			stock.setNextTenYearValue_quarter(0.0);
			stock.setAfterTenYearValue_quarter(0.0);
			stock.setNextTenYearDiscount_quarter(0.0);
			stock.setDiscount_quarter(0.0);
			this.generalDAO.save(stock);
			return;
		}
		
		double riseRatioAfterTenYear = 0.05;  //未来10年 后 股东权益年均增长率
		
		double longTermInterestRate = 0.09; //未来10年的折现率
		double[] ratio = new double[10]; //未来10年每一年的复利现值系数, 相对于折现率longTermInterestRate
		ratio[0] = 0.9174;
		ratio[1] = 0.8417;
		ratio[2] = 0.7722;
		ratio[3] = 0.7084;
		ratio[4] = 0.6499;
		ratio[5] = 0.5963;
		ratio[6] = 0.5470;
		ratio[7] = 0.5019;
		ratio[8] = 0.4604;
		ratio[9] = 0.4224;
		
		double[] earnings = new double[10]; //未来10年每一年的股东权益现金
		double[] value = new double[10]; //未来10年每一年的股东权益现金 现值
		
		double nextTenYearValue = 0; //未来10年股东权益现金现值合计
		double afterTenYearValue = 0; //未来10年 后 股东权益现金现值
		
		
		//计算未来10年股东权益现金现值合计
		for(int i=0; i<10; i++){
			if(i==0){
				earnings[0] = stock.getLatestEarnings_quarter()*(1 + stock.getDecadeEarningsGrowthRatio());				
			}else{
				earnings[i] = earnings[i-1]*(1 + stock.getDecadeEarningsGrowthRatio());
			}
			value[i] = earnings[i] * ratio[i];
			nextTenYearValue += value[i];
		}
		
		//未来10年 后 股东权益现金现值
		afterTenYearValue = (earnings[9]*(1+riseRatioAfterTenYear))/(longTermInterestRate-riseRatioAfterTenYear)*ratio[9];
		
		stock.setNextTenYearValue_quarter(nextTenYearValue);
		stock.setAfterTenYearValue_quarter(afterTenYearValue);
		
		//System.out.println("nextTenYearValue = " + nextTenYearValue);
		//System.out.println("afterTenYearValue = " + afterTenYearValue);
		
		double nextTenYearDiscount = 0.0;
		double totalValue = 0.0;
		double discount = 0.0;

		if(nextTenYearValue>0 && afterTenYearValue>0 ){
			nextTenYearDiscount = (stock.getMarketValue()-stock.getNextTenYearValue_quarter())/stock.getNextTenYearValue_quarter();
			totalValue = stock.getNextTenYearValue_quarter() + stock.getAfterTenYearValue_quarter();
			discount = (stock.getMarketValue()-totalValue)/totalValue;			
		}
		stock.setNextTenYearDiscount_quarter(nextTenYearDiscount);
		stock.setDiscount_quarter(discount);
		
		//System.out.println("nextTenYearDiscount_quarter=" + stock.getNextTenYearDiscount_quarter());
		//System.out.println("discount_quarter=" + stock.getDiscount_quarter());
		
		
		this.generalDAO.save(stock);		
	}

	public ReportBusiness getReportBusiness() {
		return reportBusiness;
	}

	public void setReportBusiness(ReportBusiness reportBusiness) {
		this.reportBusiness = reportBusiness;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
	public void updateStockMainBusiness(){
		String sql = "select REPORTPK,stockNo from report where theyear='2012' and themonth='12' and netprofitplus>0 and netcashflow>netprofitplus";
		List<Object[]> list = this.generalDAO.findBySql(sql, 0, 1000);
		if(list==null || list.size()==0) return;
		Report report;
		Stock stock;
		Object[] obj;
		String cashflow;
		for(int i=0; i<list.size(); i++){
			obj = list.get(i);
			report = (Report)this.generalDAO.findByPK(Report.class, obj[0].toString());
			cashflow = Convert.toString(report.getNetProfitPlus()/report.getNetCashFlow(),"#.##");
			
			
			StockQuery sq = new StockQuery();
			sq.empty();
			sq.setStart(0);
			sq.setCount(1);
			sq.setDeleted("0");
			sq.setStockNo(obj[1].toString());
			List stocks = this.generalDAO.findByQuery(sq);
			if(stocks.size()==1){
				stock = (Stock)stocks.get(0);
				//stock = (Stock)this.generalDAO.findByPK(Stock.class, obj[1].toString());
				
				stock.setMainBusiness(cashflow);
				this.generalDAO.save(stock);
				System.out.println(cashflow);
				System.out.println(report);
				System.out.println(stock);
			}
		}
	}
	

	

}

