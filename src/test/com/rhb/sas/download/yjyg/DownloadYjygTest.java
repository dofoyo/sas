package test.com.rhb.sas.download.yjyg;

import java.util.List;

import org.junit.Test;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.download.yjyg.DownloadYjygFromEastmoney;
import com.rhb.sas.download.yjyg.YjygDTO;



public class DownloadYjygTest {

	@Test
	public void test(){
		Sensor s = new Sensor();

		DownloadYjygFromEastmoney d = new DownloadYjygFromEastmoney();
		
		d.setSensor(s);
		
		List<YjygDTO> list = null;
		
		list = d.doIt("201306",12);
		for(YjygDTO dto : list){
			System.out.println(dto);
		}
	}
}
