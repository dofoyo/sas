package com.rhb.sas.util;

public class Value {
	/* 
	 * �����ֵ
	 * riseRatioOfDecade ��10�������������,һ�㲻����30%;
	 */
	public static double calculate(double profit,double riseRatioOfDecade){
		//System.out.println("riseRatioOfDecade = " + riseRatioOfDecade);
		double riseRatioAfterTenYear = 0.05;  //10���������ʣ�һ��Ϊ5%;
		double longTermInterestRate	 = 0.09;  // ������,һ��Ϊ9%;         
		double[] ratio = new double[10]; //δ��10��ÿһ��ĸ�����ֵϵ��, �����������longTermInterestRate=0.09ʱ
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
		
		double[] earnings = new double[10]; //δ��10��ÿһ��Ĺɶ�Ȩ���ֽ�
		double[] value = new double[10]; //δ��10��ÿһ��Ĺɶ�Ȩ���ֽ� ��ֵ
		
		double nextTenYearValue = 0; //δ��10��ɶ�Ȩ���ֽ���ֵ�ϼ�
		double afterTenYearValue = 0; //δ��10�� �� �ɶ�Ȩ���ֽ���ֵ
		
		
		//����δ��10��ɶ�Ȩ���ֽ���ֵ�ϼ�
		for(int i=0; i<10; i++){
			if(i==0){
				earnings[0] = profit*(1 + riseRatioOfDecade);				
			}else{
				earnings[i] = earnings[i-1]*(1 + riseRatioOfDecade);
			}
			value[i] = earnings[i] * ratio[i];
			nextTenYearValue += value[i];
		}
		
		//δ��10�� �� �ɶ�Ȩ���ֽ���ֵ
		afterTenYearValue = (earnings[9]*(1+riseRatioAfterTenYear))/(longTermInterestRate-riseRatioAfterTenYear)*ratio[9];
		
		return nextTenYearValue+afterTenYearValue;
			
	}

	/* 
	 * �����ֵ
	 * riseRatioOfDecade δ��������������,һ�㲻����30%;
	 * years δ������
	 */
	public static double calculate(double profit,double riseRatioOfDecade,int years){
		System.out.println("profit=" + profit + ", ratio=" + riseRatioOfDecade + ", years=" + years);
		
		
		//System.out.println("riseRatioOfDecade = " + riseRatioOfDecade);
		double[] ratio = new double[10]; //δ��10��ÿһ��ĸ�����ֵϵ��, �����������longTermInterestRate=0.09ʱ
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
		
		double[] earnings = new double[10]; //δ��ÿһ��Ĺɶ�Ȩ���ֽ�
		double[] value = new double[10]; //δ��ÿһ��Ĺɶ�Ȩ���ֽ� ��ֵ
		
		double nextTenYearValue = 0; //δ���ɶ�Ȩ���ֽ���ֵ�ϼ�
		
		
		//����δ���ɶ�Ȩ���ֽ���ֵ�ϼ�
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
