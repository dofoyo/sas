package test.rhb.sas.tradingrecord.business;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rhb.af.util.AppContext;
import com.rhb.sas.tradingrecord.business.TradingRecordBusiness;
import com.rhb.sas.tradingrecord.bean.*;

import com.rhb.af.business.FindBusiness;
import com.rhb.af.exception.DuplicateException;
import com.rhb.af.exception.OutOfRangeException;
import com.rhb.af.exception.RequiredException;


public class TradingRecordBusinessTest{

	static String appContextPath = "com/rhb/sas/AppContext.xml";

	static FindBusiness fb;
	static TradingRecordBusiness rb;

	@BeforeClass
	public static void initial() {
		rb = (TradingRecordBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("tradingRecordService");
		fb = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");
	}
	
	//@Test
	public void getList(){
		List<TradingProfit> list = rb.getReport(new TradingProfitQuery()).getList();
		for(TradingProfit tp: list){
			System.out.println(tp.toString());
		}
	}
	
	
	
	@Test
	public void createFromExcel(){
		String file = "D:\\java\\hsqldb\\tradingrecords.xls";
		CreateRecordsFromExcel crfe = new CreateRecordsFromExcel();
		crfe.setFile(file);
		List<TradingRecord> list = crfe.doIt();
		
		for(TradingRecord tr : list){
			System.out.println(tr.toString());
			try {
				rb.save(tr);
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

	
	//@Test
	public void findByQuery(){
		TradingRecordQuery rq = new TradingRecordQuery();
		rq.setStart(0);
		rq.setCount(10000);
		List<Object> records = fb.findByQuery(rq).getList();
		int i=0;
		for(Object obj : records){
			TradingRecord record = (TradingRecord)obj;
			System.out.println(i++ + "/" + records.size() + "," + record.toString());
		}
		
		System.out.println("--------- over --------------");
	}

	
}
