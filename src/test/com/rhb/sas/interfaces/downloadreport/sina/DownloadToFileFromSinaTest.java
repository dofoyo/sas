package test.com.rhb.sas.interfaces.downloadreport.sina;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rhb.af.business.FindBusiness;
import com.rhb.af.util.AppContext;
import com.rhb.sas.interfaces.downloadreport.sina.DownloadToFileFromSina;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;

public class DownloadToFileFromSinaTest {

	static String appContextPath = "com/rhb/sas/AppContext.xml";

	static FindBusiness fb;

	@BeforeClass
	public static void initial() {
		fb = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");
	}
	
	@Test
	public void test(){
		StockQuery q = new StockQuery();
		q.empty();
		q.setCount(10000);
		List<Stock> stocks = fb.findByQuery(q).getList();
		int i=0;
		
		DownloadToFileFromSina down = new DownloadToFileFromSina();
		try {
			down.doIt(stocks, "d:\\stocks\\");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("********************** over ***********************");
	}
	
}
