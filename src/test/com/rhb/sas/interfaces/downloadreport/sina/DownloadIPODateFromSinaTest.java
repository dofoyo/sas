package test.com.rhb.sas.interfaces.downloadreport.sina;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rhb.af.bean.Page;
import com.rhb.af.business.FindBusiness;
import com.rhb.af.exception.DuplicateException;
import com.rhb.af.exception.OutOfRangeException;
import com.rhb.af.exception.RequiredException;
import com.rhb.af.util.AppContext;
import com.rhb.sas.interfaces.downloadreport.sina.DownloadIPODateFromSina;
import com.rhb.sas.report.business.ReportBusiness;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;
import com.rhb.sas.stock.business.StockBusiness;
import com.rhb.sas.util.Tools;

public class DownloadIPODateFromSinaTest {
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

	//@Test
	public void test(){
		String stockNo="sz300022";
		System.out.println(getDate(stockNo));
	}
	
	private String getDate(String stockNo){
		DownloadIPODateFromSina download = new DownloadIPODateFromSina();
		return download.doIt(stockNo);
	}
	
	@Test
	public void down(){
		StockQuery sq = new StockQuery();
		sq.empty();
		sq.setCount(10000);
		//sq.setStockNo("300022");
		Page page = fb.findByQuery(sq);
		int i=1;
		for(Object obj : page.getList()){
			Stock stock = (Stock)obj;
			System.out.print(i++);
			System.out.print("/");
			System.out.println(page.getList().size());
			//System.out.println(stock.getStockNoWithPrefix());
			Date d = Tools.getDate(getDate(stock.getStockNoWithPrefix()));
			stock.setMadeDate(d);
			System.out.println(stock.getStockNo() + stock.getStockName() + ":" + Tools.getDate(d, "yyyy-MM-dd"));
			try {
				sb.save(stock);
			} catch (DuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RequiredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OutOfRangeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
