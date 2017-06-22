package com.rhb.sas.evaluate.selector;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.firm.Firm;


/**
 * ѡ�ɣ�	
	��������	������������������������ʴ���30%
	����		��������������������ʴ���30%
	�ֽ���	��Ӫ��ֽ���Ϊ�����ҳ����������������ʴ���30%
	ë����	ë���ʴ���20%
	Ӧ�տ�	Ӧ�տ������С���������������
	��ծ		��ծ������С���������������

 * @author rhb
 *
 */

public class Selector1 implements Selector {
	List<Firm> firms;
	int year;
	int period;
	
	public Selector1(List<Firm> firms, int year, int period){
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
	
	private  boolean isOK(Firm firm) {
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
							if(firm.getAGROfOperatingRevenue()>firm.getAGROfLiabilities()){ //������������Ӵ��ڸ�ծ������
								sb.append("������������Ӵ��ڸ�ծ������. ");
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
							}
						}
					}
				}
			}
		}
		System.out.println(sb.toString());
		return flag;
	}


}
