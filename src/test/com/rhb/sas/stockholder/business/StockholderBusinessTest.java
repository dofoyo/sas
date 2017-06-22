package test.com.rhb.sas.stockholder.business;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.rhb.af.util.AppContext;

import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;
import com.rhb.sas.stockholder.bean.Stockholder;
import com.rhb.sas.stockholder.business.StockholderBusiness;

import com.rhb.af.business.FindBusiness;

public class StockholderBusinessTest extends TestCase {

	static Logger logger = Logger.getLogger(StockholderBusinessTest.class);
	static String appContextPath = "com/rhb/sas/AppContext.xml";

	private StockholderBusiness sb;
	private FindBusiness fb;

	protected void setUp() {
		sb = (StockholderBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("stockholderService");
		fb = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");

	}

	public void test() {
		try {
			logger.info("********** testing StockholderBusiness begin............ ");
			String path = "C:\\Downloads\\StockHolder\\";
			
			StockQuery sq = new StockQuery();
			sq.empty();
			sq.setStart(0);
			sq.setCount(10000);
			sq.setDeleted("0");
			List<Stock> stocks = fb.findByQuery(sq).getList();
			/*
			Stock s = new Stock();
			s.setStockNo("600027");
			List<Stock> stocks = new ArrayList();
			stocks.add(s);
			*/
			for(int i=0; i<stocks.size(); i++){
				System.out.println((i+1) + "/" + stocks.size() + ", " + stocks.get(i).getStockNo() + ", " + stocks.get(i).getStockName());
				sb.readFromFile(stocks.get(i),path);				
			}
			
			
			logger.info("********** testing StockholderBusiness end............ ");
		} catch (Exception e) {
			e.printStackTrace();
			fail("************* test StockholderBusiness error *****************");
		}
	}
}
