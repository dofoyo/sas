package com.rhb.sas.evaluate.selector;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.firm.Firm;


/**
 * 选股：	
	销售收入	销售收入持续增长，且增长率大于30%
	利润		利润持续增长，且增长率大于30%
	现金流	经营活动现金流为正，且持续增长，且增长率大于30%
	毛利率	毛利率大于20%
	应收款	应收款的增加小于销售收入的增加
	负债		负债的增加小于销售收入的增加

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
		if(firm.getAGROfOperatingRevenue()>0.3){ // 销售收入增长率大于30%
			sb.append("销售收入增长率大于30%. ");
			if(firm.sustainedOfOperatingRevenue()){ //销售收入持续增长
				sb.append("销售收入持续增长. ");
				if(firm.getAGROfProfit()>0.3){ // 利润增长率大于30%
					sb.append("利润增长率大于30%. ");
					if(firm.sustainedOfProfit()){ // 利润持续增长
						sb.append("利润持续增长. ");
						if(firm.getAGROfOperatingRevenue()>firm.getAGROfReceivable()){ //销售收入的增加大于应收款的增加
							sb.append("销售收入的增加大于应收款的增加. ");
							if(firm.getAGROfOperatingRevenue()>firm.getAGROfLiabilities()){ //销售收入的增加大于负债的增加
								sb.append("销售收入的增加大于负债的增加. ");
								if(firm.getAvarageOfCashflow()>0){ // 现金流为正
									sb.append("现金流为正. ");
									if(firm.getAGROfCashflow()>0.3){ //现金流增长率大于30%
										if(firm.sustainedOfCashflow()){ // 现金流持续增长
											sb.append("现金流持续增长. ");
											if(firm.getAvarageOfGrossProfitRate()>0.2){ // 毛利率大于20%
												sb.append("毛利率大于20%. ");
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
