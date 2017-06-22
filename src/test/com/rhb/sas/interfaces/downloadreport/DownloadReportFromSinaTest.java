package test.com.rhb.sas.interfaces.downloadreport;

import java.util.List;

import org.junit.Test;

import com.rhb.af.util.*;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.interfaces.downloadreport.sina.DownloadBalanceSheetFromSina;
import com.rhb.sas.interfaces.downloadreport.sina.DownloadCashFlowFromSina;
import com.rhb.sas.interfaces.downloadreport.sina.DownloadFinanceSummaryFromSina;
import com.rhb.sas.interfaces.downloadreport.sina.DownloadProfitStatementFromSina;

public class DownloadReportFromSinaTest {

	private String stockNo = "300039";
	private java.util.Date reportDate = DateTools.getDate("2013-12-31");
	private Sensor sensor = new Sensor();
	
	@Test
	public void testDownloadBalanceSheet(){
		System.out.println("************** testDownloadBalanceSheet ***********");
		DownloadBalanceSheetFromSina d = new DownloadBalanceSheetFromSina();
		d.setSensor(sensor);
		List list = d.doIt(stockNo,reportDate);
		for(Object obj : list){
			System.out.println(obj);
		}
	}
	
	@Test
	public void testDownloadCashFlow(){
		System.out.println("************** testDownloadCashFlow ***********");
		DownloadCashFlowFromSina d = new DownloadCashFlowFromSina();
		d.setSensor(sensor);
		List list = d.doIt(stockNo,reportDate);
		for(Object obj : list){
			System.out.println(obj);
		}
	}

	@Test
	public void testDownloadProfitStatment(){
		System.out.println("************** testDownloadProfitStatment ***********");
		DownloadProfitStatementFromSina d = new DownloadProfitStatementFromSina();
		d.setSensor(sensor);
		List list = d.doIt(stockNo,reportDate);
		for(Object obj : list){
			System.out.println(obj);
		}
	}

	@Test
	public void testDownloadFinanceSummary(){
		System.out.println("************** testDownloadFinanceSummary ***********");
		DownloadFinanceSummaryFromSina d = new DownloadFinanceSummaryFromSina();
		d.setSensor(sensor);
		List list = d.doIt(stockNo,reportDate);
		for(Object obj : list){
			System.out.println(obj);
		}
	}
	
}
