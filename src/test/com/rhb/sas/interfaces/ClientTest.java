package test.com.rhb.sas.interfaces;


import org.junit.Test;

import com.rhb.sas.interfaces.Client_DownloadMarketInfo;
import com.rhb.sas.interfaces.Client_DownloadReport;
import com.rhb.sas.interfaces.Client_DownloadYjyg;
import com.rhb.sas.interfaces.InterfacesFacade;
import com.rhb.af.business.FindBusiness;
import com.rhb.sas.report.business.ReportBusiness;
import com.rhb.sas.stock.business.StockBusiness;
import com.rhb.af.util.AppContext;
import com.rhb.sas.stock.bean.*;

import java.util.*;

public class ClientTest {
	
	//@Test
	public void downloadMarketInfo(){
		Client_DownloadMarketInfo client = new Client_DownloadMarketInfo();  
		client.setSyso(true);
		client.doIt();
	}
	
	//@Test
	public void downloadReport(){
		ReportBusiness rb = (ReportBusiness) AppContext.getInstance().getAppContext("com/rhb/sas/AppContext.xml").getBean("reportService");
		StockBusiness sb = (StockBusiness) AppContext.getInstance().getAppContext("com/rhb/sas/AppContext.xml").getBean("stockService");
		FindBusiness fb = (FindBusiness) AppContext.getInstance().getAppContext("com/rhb/sas/AppContext.xml").getBean("findService");
		StockQuery sq = new StockQuery();
		sq.empty();
		sq.setStart(0);
		sq.setCount(10000);
		List<Stock> stocks = fb.findByQuery(sq).getList();
		int i=0;
		for(Stock stock: stocks){
			i++;
			System.out.println(i + "/" + stocks.size() + " " + stock.getStockNo() + stock.getStockName());
			rb.caculateEarningsGrowthRatio(stock.getStockNo());
			sb.updateFromReports(stock.getStockNo());
			sb.updateValueAndDiscount(stock.getStockNo());
		}
	}
	
	//@Test
	public void downloadYjyg(){
		Client_DownloadYjyg d = new Client_DownloadYjyg();
		d.setTheyearandmonth("201206");
		d.setThepage(1);
		d.setSyso(true);
		d.doIt();
	}
	
}
