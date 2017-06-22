package test.com.rhb.sas.util;

import java.util.List;

import org.junit.Test;

import com.rhb.sas.util.Statistics;

public class StatisticsTest {

	@Test
	public void test1(){
		Statistics s = new Statistics(4);
		s.setData(0, 54500000);
		s.setData(1, 101000000);
		s.setData(2, 147000000);
		s.setData(3, 216000000);
		
		System.out.println("avarage = " + s.getAvarage());
		System.out.println("agr = " + s.getAGR());
		System.out.println("sustained = " + s.sustained());
		System.out.println("if agr = 0.5825316929954738, then it is right.");
	}
	
	@Test
	public void test2(){
		Statistics s = new Statistics(4);
		s.setData(0, 216000000);
		s.setData(1, 101000000);
		s.setData(2, 147000000);
		s.setData(3, 54500000);
		
		System.out.println("avarage = " + s.getAvarage());
		System.out.println("agr = " + s.getAGR());
		System.out.println("sustained = " + s.sustained());
		System.out.println("if agr = -0.368101123, then it is right.");
	}

}
