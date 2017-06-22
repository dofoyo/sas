package test.com.rhb.sas.interfaces.downloadreport;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.rhb.sas.interfaces.downloadreport.sina.DownloadReportedStockNosFromSina;
import com.rhb.sas.util.Tools;

public class DownloadReportedStockNosTest {
	@Test
	public void doIt(){
		Date reportDate = Tools.getDate("2009-12-31");
		Date issueDate_begin = Tools.getDate("2010-01-21");
		Date issueDate_end = Tools.getDate("2010-01-23");
		
		DownloadReportedStockNosFromSina download = new DownloadReportedStockNosFromSina();
		List list = download.getReportedStockNo(reportDate, issueDate_begin, issueDate_end);
		System.out.println(list);
		
	}
}
