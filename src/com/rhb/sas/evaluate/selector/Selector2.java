package com.rhb.sas.evaluate.selector;

import java.util.ArrayList;
import java.util.List;

import com.rhb.af.bean.Page;
import com.rhb.af.business.FindBusiness;
import com.rhb.sas.firm.Firm;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;


/**
 * ѡ�ɣ�	
	��������	������������������ҽ�2���������������ʴ���30%
	����		��������������ҽ�2�����������ʴ���30%
	�ֽ���	��Ӫ��ֽ���Ϊ�����ҳ����������ҽ�2���ֽ��������ʴ���30%
	ë����	ë���ʴ���20%
	Ӧ�տ�	Ӧ�տ������С���������������
	��ծ		������ 

 * @author rhb
 *
 */

public class Selector2 implements Selector {
	List<Firm> firms;
	int year;
	int period;
	
	public Selector2(List<Firm> firms, int year, int period){
		this.firms = firms;
		this.year = year;
		this.period = period;
	}

	@Override
	public List<Firm> select() {
		List<Firm> oks = new ArrayList();
		int i=0;
		for(Firm firm : firms){
			firm.init(year, period,"12");
			System.out.println(Integer.toString(i++) + "/" + Integer.toString(firms.size()));
			if(isOK(firm)){
				oks.add(firm);
			}
		}
		System.out.println("result: *******************");
		
		if(oks.size()>0){
			Firm ff = oks.get(0);
			System.out.println(ff.toStringTitle());
		}
		
		for(Firm f : oks){
			System.out.println(f.getStockInfo());
		}
		
		return oks;
		
		
	}
	
	private boolean isOK(Firm firm) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		if(firm.sustainedOfOperatingRevenue() //���������������
				&& firm.sustainedOfProfit()  // �����������
				&& firm.getAvarageOfCashflow()>0 && firm.sustainedOfCashflow() // �ֽ���Ϊ������������
				&& firm.getAGROfOperatingRevenue()>firm.getAGROfReceivable() //������������Ӵ���Ӧ�տ������
				&& firm.getAvarageOfGrossProfitRate()>0.2 // ë���ʴ���20%
				&& firm.getAGROfOperatingRevenueOfRecent2Year()>0.3  // ��2���������������ʴ���30%
				&& firm.getAGROfProfitOfRecent2Year()>0.3 // ��2�����������ʴ���30%
				&& firm.getAGROfCashflowOfRecent2Year()>0.3	 //��2���ֽ��������ʴ���30%
			){
			flag = true;
		}
		return flag;
	}


}
