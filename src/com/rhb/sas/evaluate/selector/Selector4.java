package com.rhb.sas.evaluate.selector;

import java.util.ArrayList;
import java.util.List;

import com.rhb.af.bean.Page;
import com.rhb.af.business.FindBusiness;
import com.rhb.sas.firm.Firm;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;


/**
 * 选股：	
	销售收入	销售收入持续增长，且近2年销售收入增长率大于30%
	利润		利润持续增长，且近2年利润增长率大于30%
	现金流	经营活动现金流为正，且持续增长，且近2年现金流增长率大于30%
	毛利率	且近2毛利率大于20%
	应收款	不考虑
	负债		不考虑 
	净资产收益率	且近2平均值大于20%

 * @author rhb
 *
 */

public class Selector4 implements Selector {
	List<Firm> firms;
	int year;
	int period;
	
	public Selector4(List<Firm> firms, int year, int period){
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
	
	public static boolean isOK(Firm firm) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		/*
		if(firm.sustainedOfOperatingRevenue() //销售收入持续增长
				&& firm.sustainedOfProfit()  // 利润持续增长
				&& firm.getAvarageOfCashflow()>0 && firm.sustainedOfCashflow() // 现金流为正并持续增长
				&& firm.getAvarageOfGrossProfitRate()>0.2 // 毛利率大于20%
				&& firm.getAGROfOperatingRevenueOfRecent2Year()>0.3  // 近2年销售收入增长率大于30%
				&& firm.getAGROfProfitOfRecent2Year()>0.3 // 近2年利润增长率大于30%
				&& firm.getAGROfCashflowOfRecent2Year()>0.3	 //近2年现金流增长率大于30%
				&& firm.getAvarageOfROE()>0.2 // 净资产收益率平均值大于30%
			){
			flag = true;
		}
		*/
		
		if(firm.sustainedOfOperatingRevenue() && firm.getAGROfOperatingRevenueOfRecent2Year()>0.3){ 
			sb.append("销售收入持续增长, 近2年销售收入增长率大于30%. ");
			if(firm.sustainedOfProfit() && firm.getAGROfProfitOfRecent2Year()>0.3){  
				sb.append("利润持续增长, 近2年利润增长率大于30%. ");
				if(firm.getAvarageOfCashflow()>0 && firm.sustainedOfCashflow() && firm.getAGROfCashflowOfRecent2Year()>0.3){
					sb.append("现金流为正并持续增长, 近2年现金流增长率大于30%. ");
					if(firm.getAvarageOfRecent2YearGrossProfitRate()>0.2){
						sb.append("近2年毛利率大于20%, ");
						if(firm.getAvarageOfRecent2YearROE()>0.13){
							sb.append("近2年净资产收益率平均值大于13%, ");
							flag = true;
						}
					}
				}
			}
		}
		System.out.println(sb.toString());
		
		return flag;
	}


}
