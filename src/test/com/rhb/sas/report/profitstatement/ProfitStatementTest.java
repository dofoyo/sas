package test.com.rhb.sas.report.profitstatement;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rhb.af.bean.Page;
import com.rhb.af.business.FindBusiness;
import com.rhb.af.exception.DuplicateException;
import com.rhb.af.exception.OutOfRangeException;
import com.rhb.af.exception.RequiredException;
import com.rhb.af.util.AppContext;
import com.rhb.sas.report.profitstatement.*;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;
import com.rhb.sas.util.Tools;

public class ProfitStatementTest {
	static String appContextPath = "com/rhb/sas/AppContext.xml";
	
	private static FindBusiness fb;
	private static ProfitStatementBusiness pss;
	private static DownloadProfitStatement down;

	@BeforeClass
	public static void initial() {
		fb = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");
		pss = (ProfitStatementBusiness)AppContext.getInstance().getAppContext(appContextPath).getBean("profitStatementBusiness");
		down = (DownloadProfitStatement)AppContext.getInstance().getAppContext(appContextPath).getBean("downloadProfitStatement");
	}
	
	//@Test
	public void showProfitStatement(){
		Map<String,ProfitStatement[]> map = pss.getQuarters("300001", "12", "2010", "2013");
		StringBuffer sb = new StringBuffer();
		sb.append("there are ");
		sb.append(map.size());
		sb.append(" records.\n");
		Iterator i = map.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<String,ProfitStatement[]> entry =  (Map.Entry<String,ProfitStatement[]>)i.next();
			String stockNo = entry.getKey();
			String stockName = ((Stock)fb.findByPK(Stock.class, stockNo)).getStockName();
			sb.append(stockNo+stockName);
			ProfitStatement[] pss = entry.getValue();
			for(ProfitStatement ps : pss){
				//System.out.println(ps);
				if(ps != null){
					if(ps.getOperatingRevenue()>0){
						sb.append(",");
						sb.append(ps.getOperatingRevenue()/10000);
						sb.append(",");
						sb.append((ps.getOperatingRevenue() - ps.getOperatingCost() - ps.getOperatingExpense() - ps.getSalesExpense() - ps.getFinanceExpense() - ps.getTax())/10000);
					}else{
						sb.append(",");
						sb.append(ps.getAllOperatingRevenue()/10000);
						sb.append(",");
						sb.append((ps.getAllOperatingRevenue() - ps.getAllOperatingCost())/10000);						
					}
				}
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	//@Test
	public void testDown1(){
		List<ProfitStatement> l = down.down("300039",null);
		for(ProfitStatement ps : l){
			System.out.println(ps);
		}			
	}
	
	
	//@Test
	public void testDown2(){
		
		Date reportDate = Tools.getDate("2012-12-31","yyyy-MM-dd");
		
		StockQuery sq = new StockQuery();
		sq.empty();
		sq.setCount(10000);
		sq.setStockNo("600892");
		Page page = fb.findByQuery(sq);
		int i=1;
		for(Object obj : page.getList()){
			Stock stock = (Stock)obj;
			System.out.print(i++);
			System.out.print("/");
			System.out.println(page.getList().size());
			
			System.out.println(stock);
			List<ProfitStatement> l = down.down(stock.getStockNo(),reportDate);
			//List<ProfitStatement> l = down.down("300359",null);
			if(l != null){
				for(ProfitStatement ps : l){
					try {
						System.out.print(ps);
						pss.save(ps);
						System.out.println(",has saved.");
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
	}

	//@Test
	public void testDown3(){
		String stockNo = "300039";
	
		List<ProfitStatement> l = down.down(stockNo,null);
		if(l != null){
			for(ProfitStatement ps : l){
				try {
					System.out.print(ps);
					pss.save(ps);
					System.out.println(",has saved.");
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

	
	
	//@Test
	public void testCreate(){
		ProfitStatement ps = new ProfitStatement();
		ps.setStockNo("300022");
		ps.setStockNo("¼ª·åÅ©»ú");
		ps.setTheYear("2013");
		ps.setTheMonth("03");
		ps.setAllOperatingRevenue(1234567890.12);
		ps.setAllOperatingCost(1234567890.12);
		ps.setOperatingRevenue(1234567890.12);
		ps.setOperatingCost(1234567890.12);
		ps.setSalesExpense(1234567890.12);
		ps.setOperatingExpense(1234567890.12);
		ps.setFinanceExpense(1234567890.12);
		ps.setTax(1234567890.12);
		try {
			pss.save(ps);
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
		
		
		ProfitStatementQuery q = new ProfitStatementQuery();
		
		Page p = fb.findByQuery(q);
		System.out.println(p.getAllCount());
		
	}

}
