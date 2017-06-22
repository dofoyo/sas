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
	//��������	������������������������ʴ���30%
	//����		��������������������ʴ���30%
	//�ֽ���		��Ӫ��ֽ���Ϊ�����ҳ����������������ʴ���30%
	//ë����		ë���ʴ���20%
	//Ӧ�տ�		Ӧ�տ������С���������������
	//��ծ		��ծ������С���������������
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
	 * ѡ�ɣ�	
		��������	������������������ҽ�2���������������ʴ���30%
		����		��������������ҽ�2�����������ʴ���30%
		�ֽ���	��Ӫ��ֽ���Ϊ�����ҳ����������ҽ�2���ֽ��������ʴ���30%
		ë����	ë���ʴ���20%
		Ӧ�տ�	������
		��ծ		������ 
		���ʲ�������	ƽ��ֵ����20%
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
	 * ѡ�ɣ����
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
	 * ѡ�ɣ����
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
	 * ѡ�ɣ����
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
	 *  �������ȵ�ҵ��Ϊɸѡ
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
	 *  * ѡ�ɣ�	
	��������	������������������ҽ�2���������������ʴ���30%
	����		��������������ҽ�2�����������ʴ���30%
	�ֽ���	��Ӫ��ֽ���Ϊ�����ҳ����������ҽ�2���ֽ��������ʴ���30%
	ë����	�ҽ�2ë���ʴ���20%
	Ӧ�տ�	������
	��ծ		������ 
	���ʲ�������	�ҽ�2ƽ��ֵ����20%

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
