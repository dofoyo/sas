package test.com.rhb.sas.interfaces.downloadreport.sina;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rhb.af.business.FindBusiness;
import com.rhb.af.util.AppContext;
import com.rhb.sas.interfaces.downloadreport.sina.DownloadReportDateFromEastmoney;
import com.rhb.sas.interfaces.downloadreport.sina.DownloadReportDateFromEastmoneyOneByOne;
import com.rhb.sas.report.business.ReportBusiness;

public class DownloadReportDateFromEastmoneyTest {
	static String appContextPath = "com/rhb/sas/AppContext.xml";

	static ReportBusiness rb;
	static FindBusiness fb;
	
	@BeforeClass
	public static void initial() {
		rb = (ReportBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("reportService");
		fb = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");
	}
	
	//@Test
	public void test(){
		DownloadReportDateFromEastmoney d = new DownloadReportDateFromEastmoney();
		d.setFb(fb);
		d.setRb(rb);
		d.doIt();
	}
	
	@Test
	public void test1(){
		DownloadReportDateFromEastmoneyOneByOne d = new DownloadReportDateFromEastmoneyOneByOne();
		d.setFb(fb);
		d.setRb(rb);
		d.doIt();
		
	}

}
