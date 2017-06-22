package com.rhb.sas.util;

import java.util.List;

import com.rhb.af.business.FindBusiness;
import com.rhb.af.util.AppContext;

import com.rhb.sas.report.business.ReportBusiness;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;
import com.rhb.sas.stock.business.StockBusiness;

public class RereadAndRecaculate {
	private static String appContextPath = "com/rhb/sas/AppContext.xml";
	private static ReportBusiness reportBusiness = (ReportBusiness) AppContext
			.getInstance().getAppContext(appContextPath).getBean(
					"reportService");
	private static StockBusiness stockBusiness = (StockBusiness) AppContext
			.getInstance().getAppContext(appContextPath)
			.getBean("stockService");
	private static FindBusiness findBusiness = (FindBusiness) AppContext
			.getInstance().getAppContext(appContextPath).getBean("findService");

	/**
	 * 有7种方式： 0-1-2-3-5.1 0-1-2-3-4-5.2 0-4-5.2 6 7 8 9
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */

	public static void main(String[] args) throws Exception {
		System.out.println("*****  BEGIN *****");

		//newStock(findBusiness,stockBusiness, reportBusiness);
		getLatestPrice(stockBusiness);
		
		
		//-------------------
		
		// stockBusiness.readNoticeFromSina("20090630"); //0

		String[] stockNos = null;
		for (int i = 1; i < 19; i++) {
			// System.out.println("********** page " + i + " *********** ");
			// stockNos =
			// reportBusiness.readReportFromSina("2009","06","30",Integer.toString(i));//1
			// reportBusiness.caculateEarningsGrowthRatio(stockNos); //2
			// stockBusiness.updateFromReports(stockNos); //3
			// stockBusiness.updateValueAndDiscount(stockNos); //5.1
		}



		// writeReportFromSina(); //7

		// readReportFromFile(); //8 重新导入时用到


		System.out.println("*****  OVER *****");
	}

	
	public static void getLatestPrice(StockBusiness stockBusiness) throws Exception{
		//List<Stock> stocks = stockBusiness.readPriceAndMarketValueFromStockstar(1, 83); 
		//readReportFromSina(stocks);
		stockBusiness.updateValueAndDiscount(); 
	}
	/*

	public static void readReportFromSina(List<Stock> stocks) throws Exception{
		for(Stock stock: stocks){
			reportBusiness.readReportFromSina("2008", "12", "31", stock, null);
			reportBusiness.readReportFromSina("2007", "12", "31", stock, null);
			reportBusiness.readReportFromSina("2006", "12", "31", stock, null);
			reportBusiness.readReportFromSina("2005", "12", "31", stock, null);
			reportBusiness.caculateEarningsGrowthRatio(stock.getStockNo());
			stockBusiness.updateFromReports(stock);
		}
	}

	public static void newStock(FindBusiness findBusiness,StockBusiness stockBusiness, ReportBusiness reportBusiness) throws Exception{
		
		String[] stockNos = {
				"300040",
				"300039",
				"300042",
				"002331",
				"002329",
				"002330"
							};

		for(int i=0; i<stockNos.length; i++){
			Stock stock = (Stock)findBusiness.findByPK(Stock.class, stockNos[i]);
			reportBusiness.readReportFromSina("2008", "12", "31", stock, null);
			reportBusiness.readReportFromSina("2007", "12", "31", stock, null);
			reportBusiness.readReportFromSina("2006", "12", "31", stock, null);
			reportBusiness.readReportFromSina("2005", "12", "31", stock, null);
		}
		reportBusiness.caculateEarningsGrowthRatio(stockNos); 
		stockBusiness.updateFromReports(stockNos); 
		stockBusiness.updateValueAndDiscount(stockNos);
	}
	
	
	
	
	public static void readReportFromSina() throws Exception {
		StockQuery sq = new StockQuery();
		sq.setStart(0);
		sq.setCount(10000);
		sq.clean();
		List stocks = findBusiness.findByQuery(sq).getList();
		reportBusiness.readReportFromSina("2009", "09", "30", stocks);
	}

	public static void caculateEarningsGrowthRatio() throws Exception {
		StockQuery sq = new StockQuery();
		sq.setStart(0);
		sq.setCount(10000);
		sq.clean();

		List<Stock> stocks = findBusiness.findByQuery(sq).getList();
		String[] stockNos = new String[stocks.size()];
		for (int i = 0; i < stocks.size(); i++) {
			stockNos[i] = stocks.get(i).getStockNo();
		}
		reportBusiness.caculateEarningsGrowthRatio(stockNos); // 2
		stockBusiness.updateFromReports(stockNos); // 3
		stockBusiness.updateValueAndDiscount(stockNos); // 5.1

	}

	
	
	public static void writeReportFromSina() throws Exception {
		StockQuery sq = new StockQuery();
		sq.setStart(0);
		sq.setCount(10000);
		sq.clean();
		List<Stock> stocks = findBusiness.findByQuery(sq).getList();
		String path = "C:\\Downloads\\StockHolder\\";
		reportBusiness.writeReportFromSina1(stocks, path);
	}

	public static void readReportFromFile() throws Exception {
		StockQuery sq = new StockQuery();
		sq.setStart(0);
		sq.setCount(10000);
		List<Stock> stocks = findBusiness.findByQuery(sq).getList();
		String path = "C:\\Downloads\\";
		int i = 2003;
		System.out.println("***** " + Integer.toString(i) + "  ****");
		reportBusiness.readReportFromSina(Integer.toString(i), "12", "31",
				stocks, path);
		reportBusiness.readReportFromSina(Integer.toString(i), "09", "30",
				stocks, path);
		reportBusiness.readReportFromSina(Integer.toString(i), "06", "30",
				stocks, path);
		reportBusiness.readReportFromSina(Integer.toString(i), "03", "31",
				stocks, path);
	}
	*/

}
