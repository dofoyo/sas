package com.rhb.sas.util;

/**
 * Ҫ��˳����datas�������
 * ��Ҫ��2009��2010��2011��2012�������������ͳ�ƣ�datas[0]��ŵ�Ӧ����2009��ġ�
 */

public class Statistics {
	private double[] datas;
	
	public Statistics(int i){
		datas = new double[i];
	}
	
	public double getLatestData(){
		return datas[datas.length-1];
	}
	
	public void setData(int i, double value){
		datas[i] = value;
	}
	
	public double getDate(int i){
		return datas[i];
	}
	
	public double getAvarage(){
		double d = 0.0;
		for(double dd : datas){
			d = d + dd;
		}
		return d/datas.length;
	}

	public double getAvarageOfRecent2Year(){
		double d0 = datas[datas.length-2];
		double dn = datas[datas.length-1];
		return (d0+dn)/2;
	}
	
	
	/*
	 * ƽ��������
	 * ARG = avarage growth ratio
	 */
	public double getAGR(){
		double d0 = datas[0];
		double dn = datas[datas.length-1];
		int n = datas.length-1;
		double pow = Math.pow(dn/d0, 1.00/n)-1.00;
		//System.out.println("Math.pow("+dn+"/"+d0+", 1.00/"+n+")-1.00 = " + pow);
		return pow;
	}

	public double getAGROfRecent2Year(){
		double d0 = datas[datas.length-2];
		double dn = datas[datas.length-1];
		//double pow = (dn-d0)/d0;
		int n = 1;
		double pow = Math.pow(dn/d0, 1.00/n)-1.00;
		//System.out.println("Math.pow("+dn+"/"+d0+", 1.00/"+n+")-1.00 = " + pow);
		return pow;
	}
	
	/*
	 * �ж��Ƿ��ǳ�������
	 */
	public boolean sustained(){
		boolean flag = true;
		double[] ds = new double[datas.length-1];
		for(int i=0; i<datas.length-1; i++){
			ds[i] = (datas[i+1]-datas[i])/datas[i];
		}
		
		for(double d : ds){
			if(d<0){
				flag = false;
				break;
			}
		}
		
//		double d1 = datas[0];
//		double d2 = datas[datas.length-1];
//		if(d2>d1){
//			flag = true;
//		}
		return flag;			
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<datas.length; i++){
			sb.append("datas[" + i + "] = " + datas[i] + "\n");	
		}
		return sb.toString(); 
	}
	
}
