package test.com.rhb.sas.stock.business;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

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

public class StockBusinessTest{

	static Logger logger = Logger.getLogger(StockBusinessTest.class);
	static String appContextPath = "com/rhb/sas/AppContext.xml";

	private static StockBusiness sb;
	private static ReportBusiness rb;
	private static FindBusiness fb;

	@BeforeClass
	public static void initial() {
		sb = (StockBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("stockService");
		rb = (ReportBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("reportService");
		fb = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");
	}
	
	@Test
	public void testAll() {
		sb.updateFromReports();
		
		sb.updateValueAndDiscount();
	}
	
	//@Test
	public void test() {
		String stockNo = "300363";
		sb.updateFromReports(stockNo);
		sb.updateValueAndDiscount(stockNo);
	}
	
	//@Test
	public void testa() {
		sb.updateStockMainBusiness();
	}

}
