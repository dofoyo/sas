package test.com.rhb.sas.report.business;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rhb.af.util.AppContext;
import com.rhb.sas.report.bean.Report;
import com.rhb.sas.report.bean.ReportQuery;
import com.rhb.sas.report.business.ReportBusiness;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;
import com.rhb.sas.stock.business.StockBusiness;
import com.rhb.sas.util.Tools;

import com.rhb.af.business.FindBusiness;
import com.rhb.af.exception.DuplicateException;
import com.rhb.af.exception.OutOfRangeException;
import com.rhb.af.exception.RequiredException;

public class ReportBusinessTest{

	static String appContextPath = "com/rhb/sas/AppContext.xml";

	static ReportBusiness rb;
	static FindBusiness fb;
	private static StockBusiness sb;

	@BeforeClass
	public static void initial() {
		sb = (StockBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("stockService");
		rb = (ReportBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("reportService");
		fb = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");
	}
	
	@Test
	public void test(){
		StockQuery rq = new StockQuery();
		rq.empty();
		rq.setStart(0);
		rq.setCount(10000);
		rq.setDeleted("0");
		//rq.setStockNo("601328");
		List stocks = fb.findByQuery(rq).getList();
		//System.out.println(stocks.size());
		int i=1;
		for(Object obj : stocks){
			Stock stock = (Stock)obj;
			System.out.println(i++ + "/" + stocks.size() + "," + stock.getStockNo());
			rb.caculateEarningsGrowthRatio(stock.getStockNo());
		}
	}
	
	//@Test
	public void getNotReportedStockNo(){
		List<String> stockNos = rb.getNotReportedStockNo(Tools.getDate("2010-03-31"));
		for(String stockNo : stockNos){
			System.out.println(stockNo);
		}
	}

	//@Test
	public void test1(){
			rb.caculateEarningsGrowthRatio("601328");
	}
	
	//@Test
	public void testRebuildReport(){
		rb.rebuildReport();
	}
	
}
