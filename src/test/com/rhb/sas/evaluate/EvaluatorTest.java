package test.com.rhb.sas.evaluate;


import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rhb.af.business.FindBusiness;
import com.rhb.af.util.AppContext;
import com.rhb.sas.evaluate.Evaluator;
import com.rhb.sas.evaluate.Evaluator1;
import com.rhb.sas.evaluate.Evaluator2;
import com.rhb.sas.evaluate.Evaluator3;
import com.rhb.sas.evaluate.Evaluator4;
import com.rhb.sas.evaluate.Evaluator5;

public class EvaluatorTest {
	private static String appContextPath = "com/rhb/sas/AppContext.xml";
	private static FindBusiness find;
	private static String path = "D:\\workspace\\stockdatas\\";
	
	@BeforeClass
	public static void initial() {
		find = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");
	}
	
	
	
	//@Test
	/*
	//销售收入	销售收入持续增长，且增长率大于30%
	//利润		利润持续增长，且增长率大于30%
	//现金流		经营活动现金流为正，且持续增长，且增长率大于30%
	//毛利率		毛利率大于20%
	//应收款		应收款的增加小于销售收入的增加
	//负债		负债的增加小于销售收入的增加
	 */
	public void test1(){
		Evaluator er = new Evaluator1(find, path,"selectResult1.xls");
		List<String> results = er.evaluate();
		for(String s : results){
			System.out.println(s);
		}

	}

	//@Test
	/*
	 * 选股：	
		销售收入	销售收入持续增长，且近2年销售收入增长率大于30%
		利润		利润持续增长，且近2年利润增长率大于30%
		现金流	经营活动现金流为正，且持续增长，且近2年现金流增长率大于30%
		毛利率	毛利率大于20%
		应收款	不考虑
		负债		不考虑 
		净资产收益率	平均值大于20%
	 * 
	 */
	public void test2(){
		Evaluator er = new Evaluator2(find, path, "selectResult2.xls");
		List<String> results = er.evaluate();
		for(String s : results){
			System.out.println(s);
		}
	}

	//@Test
	/*
	 * 选股：随机
	 */
	public void test3(){
		Evaluator er = new Evaluator3(find, path, "selectResult3.xls");
		List<String> results = er.evaluate();
		for(String s : results){
			System.out.println(s);
		}
	}
	
	//@Test
	/*
	 * 选股：随机
	 */
	public void test4(){
		Evaluator er = new Evaluator3(find, path, "selectResult4.xls");
		List<String> results = er.evaluate();
		for(String s : results){
			System.out.println(s);
		}
	}
	
	//@Test
	/*
	 * 选股：随机
	 */
	public void test5(){
		Evaluator er = new Evaluator3(find, path, "selectResult5.xls");
		List<String> results = er.evaluate();
		for(String s : results){
			System.out.println(s);
		}
	}
	
	//@Test
	/*
	 *  以三季度的业绩为筛选
	 */
	public void test6(){
		Evaluator er = new Evaluator4(find, path, "selectResult6.xls");
		/*
		List<String> results = er.evaluate();
		for(String s : results){
			System.out.println(s);
		}
		*/
	}
	
	
	/*
	 *  * 选股：	
	销售收入	销售收入持续增长，且近2年销售收入增长率大于30%
	利润		利润持续增长，且近2年利润增长率大于30%
	现金流	经营活动现金流为正，且持续增长，且近2年现金流增长率大于30%
	毛利率	且近2毛利率大于20%
	应收款	不考虑
	负债		不考虑 
	净资产收益率	且近2平均值大于20%

	 */
	@Test
	public void test7(){
		Evaluator er = new Evaluator5(find, path, "selectResult9.xls");
		List<String> results = er.evaluate();
		for(String s : results){
			System.out.println(s);
		}
	}
}
