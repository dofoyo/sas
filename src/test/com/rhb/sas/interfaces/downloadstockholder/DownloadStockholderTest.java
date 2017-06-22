package test.com.rhb.sas.interfaces.downloadstockholder;

import java.util.List;

import org.junit.Test;

import com.rhb.sas.interfaces.downloadstockholder.DownloadStockholderFromSina;

public class DownloadStockholderTest {
	@Test
	public void testDownloadStockholder(){
		System.out.println("************** testDownloadStockholder ***********");
		DownloadStockholderFromSina d = new DownloadStockholderFromSina();
		List list = d.doIt("002241");
		for(Object obj : list){
			System.out.println(obj);
		}
	}

}
