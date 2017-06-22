package test.com.rhb.sas.download.mgsy;

import java.util.List;

import org.junit.Test;

import com.rhb.sas.download.mgsy.DownloadMgsyFromEastmoney;
import com.rhb.sas.download.mgsy.MgsyDTO;
import com.rhb.sas.application.console.Sensor;

public class DownloadMgsyTest {
	@Test
	public void test(){
		Sensor s = new Sensor();
		DownloadMgsyFromEastmoney dm = new DownloadMgsyFromEastmoney();
		dm.setSensor(s);
		List<MgsyDTO> list = dm.doIt("201103",22);
		//System.out.println(list.size());
		for(MgsyDTO dto : list){
			System.out.println(dto);
		}
	}
}
