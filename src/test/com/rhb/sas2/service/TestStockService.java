package test.com.rhb.sas2.service;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

import com.jdon.controller.AppUtil;
import com.rhb.sas2.domain.BalanceSheet;
import com.rhb.sas2.domain.Organization;
import com.rhb.sas2.domain.Report;
import com.rhb.sas2.domain.Stock;
import com.rhb.sas2.service.StockCommandService;
import com.rhb.sas2.util.PojoMapper;

public class TestStockService {

	@Test
	public void test(){
		String stockNo = "300022";
		
		Random r = new Random();
		int i =  r.nextInt();
		String stockName1 = Integer.toString(i);
		
		int j =  r.nextInt();
		String stockName2 = Integer.toString(j);
		
		
		AppUtil au = new AppUtil();
		StockCommandService ss = (StockCommandService)au.getComponentInstance("stockService");
		//ss.create(stockNo);
		
		System.out.println();
		
		//ss.rename(stockNo, stockName1);
		
		System.out.println();
		
		Organization org = new Organization();
		org.setName("Gifore");
		org.setBusiness("nongji");
		org.setIndustry("55");
		org.setNote("fff");
		org.setIpoDate("2009-09-31");
		ss.update(stockNo, org);
		
	}
	
	public static void main(String[] strs){
		TestStockService tss = new TestStockService();
		tss.test();
	}
	
	//@Test
	public void testJson(){
		Stock stock = new Stock();
		stock.setStockNo("300022");
		Organization org = new Organization();
		org.setName("吉峰农机");
		org.setBusiness("农业机械");
		org.setIndustry("流通");
		org.setNote("创业板");
		org.setIpoDate("2009-09-31");
		
		Report report = new Report();
		report.setReportDate("2010-12-31");
		BalanceSheet bs = new BalanceSheet();
		bs.setAccountsRreceivable(33.333);
		report.setBalanceSheet(bs);
		
		
		stock.setOrganization(org);
		stock.getReports().put("2010-12-31", report);
		try {
			String pojoAsString = PojoMapper.toJson(stock, true);
			System.out.println(pojoAsString);
			
			System.out.println("------");
			
			Stock org2 = (Stock)PojoMapper.fromJson(pojoAsString, Stock.class);
			System.out.println(org2);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
