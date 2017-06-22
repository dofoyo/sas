package test.com.rhb.sas.firm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rhb.af.bean.Page;
import com.rhb.af.business.FindBusiness;
import com.rhb.af.util.AppContext;
import com.rhb.sas.evaluate.selector.Selector4;
import com.rhb.sas.firm.Firm;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;
import com.rhb.sas.util.Tools;



public class TestFirm {
	private static String appContextPath = "com/rhb/sas/AppContext.xml";
	private static FindBusiness fb;
	
	@BeforeClass
	public static void initial() {
		fb = (FindBusiness) AppContext.getInstance().getAppContext(appContextPath).getBean("findService");
	}
	
	//@Test
	public void test1(){
		StockQuery sq = new StockQuery();
		sq.empty();
		sq.setCount(10000);
		Page page = fb.findByQuery(sq);
		List<Stock> stocks = page.getList();
		for(int i=0; i<30; i++){
			java.util.Random r=new java.util.Random(); 
			int j = r.nextInt(stocks.size());
			Stock stock = stocks.get(j);
			System.out.println(stock.getStockNo() + stock.getStockName());
		}
	}
	
	//@Test
	public void testGetData(){
		int beginYear = 2006;
		int endYear = 2013;
		String s = "300298,300039,002033,300267,300333,300288";
		String [] ss = s.split(",");
		Firm f = null;
		for(String stockNo : ss){
			f = new Firm(stockNo, fb);
			System.out.print(f.getData(beginYear, endYear));
		}
		System.out.println(f.getDataTitle());
		
	}
	
	//@Test
	public void listValue(){
		String stockNo = "002258";
		int byear = 2010;
		int eyear = 2014;
		int period = 3;
		int future = 10;
		double agr = 0.3;
		Firm f = new Firm(stockNo, fb);
		for(int i=byear; i<=eyear; i++){
			f.init(i, period,"12");
			System.out.println("year: " + i);
			System.out.println(f.getProfit(Integer.toString(i),"12"));
			System.out.println(f.getValue());
		}
	}
	
	@Test
	public void test(){
		String stockNo = "600276";
		int year = 2014;
		int period = 5;
		int future = 10;
		double agr = 0.3;
		
		Firm f = new Firm(stockNo, fb);
		f.init(year, period,"12");
		
		System.out.println(f.getStockInfo());
		//System.out.println(f.toString());
	}
	
	//@Test
	public void doSelect(){
		List<Firm> firms = new ArrayList();
		//int[] years = {2014,2013,2012,2011,2010,2009,2008};
		int[] years = {2014};
		for(int year : years){
			firms.addAll(select(year));
		}
		System.out.println("***********************************");
		System.out.println("result:");
		
		for(Firm f : firms){
			System.out.println(f.getStockInfo());
		}
		
	}
	
	
	private List<Firm> select(int year){
		int period = 3; //��3���ҵ���Ƚϣ��������year=2012,�����2010��2011��2012��������ݽ��м���
		double agr = 0.3;
		
		List<Firm> results = new ArrayList();
		StockQuery sq = new StockQuery();
		sq.empty();
		sq.setCount(10000);
		Page page = fb.findByQuery(sq);
		List<Stock> stocks = page.getList();
		Firm firm = null;
		for(int i=0; i<stocks.size(); i++){
			Stock stock = stocks.get(i);
			firm = new Firm(stock.getStockNo(), fb);
			firm.init(year, period, "12");
			System.out.println(Integer.toString(i+1) + "/" + Integer.toString(stocks.size())  + " " + firm.getStockInfo());
			if(firm.getStars()>6){
				results.add(firm);
			}
		}
		return results;
	}
	
	private boolean ok(Firm firm){
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		if(firm.getAGROfOperatingRevenue()>0.3){ // �������������ʴ���30%
			sb.append("�������������ʴ���30%. ");
			if(firm.sustainedOfOperatingRevenue()){ //���������������
				sb.append("���������������. ");
				if(firm.getAGROfProfit()>0.3){ // ���������ʴ���30%
					sb.append("���������ʴ���30%. ");
					if(firm.sustainedOfProfit()){ // �����������
						sb.append("�����������. ");
						if(firm.getAGROfOperatingRevenue()>firm.getAGROfReceivable()){ //������������Ӵ���Ӧ�տ������
							sb.append("������������Ӵ���Ӧ�տ������. ");
							//if(firm.getAGROfOperatingRevenue()>firm.getAGROfLiabilities()){ //������������Ӵ��ڸ�ծ������
								//sb.append("������������Ӵ��ڸ�ծ������. ");
								if(firm.getAvarageOfCashflow()>0){ // �ֽ���Ϊ��
									sb.append("�ֽ���Ϊ��. ");
									if(firm.getAGROfCashflow()>0.3){ //�ֽ��������ʴ���30%
										if(firm.sustainedOfCashflow()){ // �ֽ�����������
											sb.append("�ֽ�����������. ");
											if(firm.getAvarageOfGrossProfitRate()>0.2){ // ë���ʴ���20%
												sb.append("ë���ʴ���20%. ");
												flag = true;
											}												
										}
									}
								}
							//}
						}
					}
				}
			}
		}
		System.out.println(sb.toString());
		return flag;
	}
	
	private boolean ok2(Firm firm){
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		if(firm.sustainedOfOperatingRevenue() //���������������
				&& firm.sustainedOfProfit()  // �����������
				&& firm.getAvarageOfCashflow()>0 && firm.sustainedOfCashflow() // �ֽ���Ϊ������������
				&& firm.getAGROfOperatingRevenue()>firm.getAGROfReceivable() //������������Ӵ���Ӧ�տ������
				&& firm.getAvarageOfGrossProfitRate()>0.2 // ë���ʴ���20%
				&& firm.getAGROfOperatingRevenueOfRecent2Year()>0.3  // ��2���������������ʴ���20%
				&& firm.getAGROfProfitOfRecent2Year()>0.3 // ��2�����������ʴ���20%
				&& firm.getAGROfCashflowOfRecent2Year()>0.3	 //��2���ֽ��������ʴ���20%
			){
			flag = true;
		}
		return flag;
	}
	
	private boolean ok3(Firm firm) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		if(firm.sustainedOfOperatingRevenue() && firm.getAGROfOperatingRevenueOfRecent2Year()>0.3){ 
			sb.append("���������������, ��2���������������ʴ���30%. ");
			if(firm.sustainedOfProfit() && firm.getAGROfProfitOfRecent2Year()>0.3){
				sb.append("�����������, ��2�����������ʴ���30%. ");
				if(firm.getAvarageOfCashflow()>0 && firm.sustainedOfCashflow() && firm.getAGROfCashflowOfRecent2Year()>0.3){ 
					sb.append("�ֽ���Ϊ������������, ��2���ֽ��������ʴ���30%. ");
					if(firm.getAvarageOfRecent2YearGrossProfitRate()>0.2){
						sb.append("��2��ë���ʴ���20%, ");
						if(firm.getAvarageOfRecent2YearROE()>0.13){ 
							sb.append("��2�꾻�ʲ�������ƽ��ֵ����13%, ");
							flag = true;
						}
					}
				}
			}
		}
		System.out.println(sb.toString());
		return flag;
	}

	private boolean ok4(Firm firm) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		if(firm.sustainedOfOperatingRevenue()){ 
			sb.append("���������������");
			if(firm.sustainedOfProfit()){
				sb.append("�����������");
				if(firm.getAvarageOfCashflow()>0 && firm.sustainedOfCashflow()){ 
					sb.append("�ֽ���Ϊ������������");
					if(firm.getAvarageOfRecent2YearGrossProfitRate()>0.2){
						sb.append("��2��ë���ʴ���20%, ");
						if(firm.getAvarageOfRecent2YearROE()>0.13){ 
							sb.append("��2�꾻�ʲ�������ƽ��ֵ����13%, ");
							flag = true;
						}
					}
				}
			}
		}
		System.out.println(sb.toString());
		return flag;
	}
	
	private boolean ok5(Firm firm) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		if(firm.sustainedOfOperatingRevenue() && (firm.getAGROfOperatingRevenueOfRecent2Year()>0.20)){ 
			sb.append("���������������, ��2���������������ʴ���20%. ");
			if(firm.getLatestProfit()>0 && firm.sustainedOfProfit() && (firm.getAGROfProfitOfRecent2Year()>0.20)){
				sb.append("ӯ���������������, ��2�����������ʴ���20%. ");
				if(firm.getLatestCashflow()>0 ){ 
					sb.append("��Ӫ�ֽ�����ֵΪ��");
					flag = true;
				}
			}
		}
		System.out.println(sb.toString());
		return flag;
	}
	
	
}
