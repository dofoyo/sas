package test.com.rhb.sas.interfaces.downloadmarketinfo;

import org.junit.Test;

import com.rhb.sas.interfaces.downloadmarketinfo.DownloadMarketInfoFromStockstar;
import com.rhb.sas.interfaces.downloadmarketinfo.DownloadMarketInfoFromDzh;

public class DownloadMarketInfoFromStockstarTest {

	//@Test
	public void test(){
		DownloadMarketInfoFromStockstar d = new DownloadMarketInfoFromStockstar();
		d.setFrom(83);
		d.setTo(84);
		d.doIt();
	}
	
	//@Test
	public void test1(){
		DownloadMarketInfoFromDzh d = new DownloadMarketInfoFromDzh();
		d.setFile("D:\\java\\hsqldb\\marketInfo.xls");
		d.doIt();
	}
}
