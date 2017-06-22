package test.com.rhb.sas.util;

import org.junit.Test;

import com.rhb.sas.util.BuyAndSell;

public class TestBuyAndSell {

	@Test
	public void test(){
		BuyAndSell bas = new BuyAndSell("C:\\601328.xls");
		bas.doIt(5);
	}
	
}
