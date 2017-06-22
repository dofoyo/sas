package test.com.rhb.sas.interfaces.downloadreport.sina;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rhb.af.business.FindBusiness;
import com.rhb.af.util.AppContext;
import com.rhb.af.util.DateTools;
import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.interfaces.InterfacesFacade;
import com.rhb.sas.interfaces.downloadreport.sina.DownloadFinancialGuideFromSina;
import com.rhb.sas.report.business.ReportBusiness;
import com.rhb.sas.stock.bean.StockQuery;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.business.StockBusiness;

import java.util.List;

public class DownloadFinancialGuideFromsinaTest {
	static String appContextPath = "com/rhb/sas/AppContext.xml";

	static ReportBusiness rb;
	static FindBusiness fb;
	private static StockBusiness sb;
	private static InterfacesFacade facade;

	@BeforeClass
	public static void initial() {
		sb = (StockBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("stockService");
		rb = (ReportBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("reportService");
		fb = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");
		facade = (InterfacesFacade) AppContext.getInstance().getAppContext(appContextPath).getBean("interfacesFacade");	
	}

	@Test
	public void doit(){
		StockQuery q = new StockQuery();
		q.empty();
		q.setCount(10000);
		List<Stock> stocks = fb.findByQuery(q).getList();
		int i=0;
		for(Stock stock: stocks){
			i++;
			System.out.println(i + "/" + stocks.size() + ", " + stock.getStockNo());
			facade.downloadReport(stock.getStockNo(),null,false);
		}
		System.out.println("********************** over ***********************");
	}
	
}
