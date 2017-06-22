package com.rhb.sas.util;

public class Value {
	/* 
	 * 计算价值
	 * riseRatioOfDecade 近10年的利润增长率,一般不超过30%;
	 */
	public static double calculate(double profit,double riseRatioOfDecade){
		//System.out.println("riseRatioOfDecade = " + riseRatioOfDecade);
		double riseRatioAfterTenYear = 0.05;  //10年后的增长率，一般为5%;
		double longTermInterestRate	 = 0.09;  // 折现率,一般为9%;         
		double[] ratio = new double[10]; //未来10年每一年的复利现值系数, 相对于折现率longTermInterestRate=0.09时
		ratio[0] = 0.9174;
		ratio[1] = 0.8417;
		ratio[2] = 0.7722;
		ratio[3] = 0.7084;
		ratio[4] = 0.6499;
		ratio[5] = 0.5963;
		ratio[6] = 0.5470;
		ratio[7] = 0.5019;
		ratio[8] = 0.4604;
		ratio[9] = 0.4224;
		
		double[] earnings = new double[10]; //未来10年每一年的股东权益现金
		double[] value = new double[10]; //未来10年每一年的股东权益现金 现值
		
		double nextTenYearValue = 0; //未来10年股东权益现金现值合计
		double afterTenYearValue = 0; //未来10年 后 股东权益现金现值
		
		
		//计算未来10年股东权益现金现值合计
		for(int i=0; i<10; i++){
			if(i==0){
				earnings[0] = profit*(1 + riseRatioOfDecade);				
			}else{
				earnings[i] = earnings[i-1]*(1 + riseRatioOfDecade);
			}
			value[i] = earnings[i] * ratio[i];
			nextTenYearValue += value[i];
		}
		
		//未来10年 后 股东权益现金现值
		afterTenYearValue = (earnings[9]*(1+riseRatioAfterTenYear))/(longTermInterestRate-riseRatioAfterTenYear)*ratio[9];
		
		return nextTenYearValue+afterTenYearValue;
			
	}

	/* 
	 * 计算价值
	 * riseRatioOfDecade 未来年利润增长率,一般不超过30%;
	 * years 未来年数
	 */
	public static double calculate(double profit,double riseRatioOfDecade,int years){
		System.out.println("profit=" + profit + ", ratio=" + riseRatioOfDecade + ", years=" + years);
		
		
		//System.out.println("riseRatioOfDecade = " + riseRatioOfDecade);
		double[] ratio = new double[10]; //未来10年每一年的复利现值系数, 相对于折现率longTermInterestRate=0.09时
		ratio[0] = 0.9174;
		ratio[1] = 0.8417;
		ratio[2] = 0.7722;
		ratio[3] = 0.7084;
		ratio[4] = 0.6499;
		ratio[5] = 0.5963;
		ratio[6] = 0.5470;
		ratio[7] = 0.5019;
		ratio[8] = 0.4604;
		ratio[9] = 0.4224;
		
		double[] earnings = new double[10]; //未来每一年的股东权益现金
		double[] value = new double[10]; //未来每一年的股东权益现金 现值
		
		double nextTenYearValue = 0; //未来股东权益现金现值合计
		
		
		//计算未来股东权益现金现值合计
		for(int i=0; i<years; i++){
			if(i==0){
				earnings[0] = profit*(1 + riseRatioOfDecade);				
			}else{
				earnings[i] = earnings[i-1]*(1 + riseRatioOfDecade);
			}
			value[i] = earnings[i] * ratio[i];
			nextTenYearValue += value[i];
		}
		
		
		return nextTenYearValue;
			
	}

	
}
