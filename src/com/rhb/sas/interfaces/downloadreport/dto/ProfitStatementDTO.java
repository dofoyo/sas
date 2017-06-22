package com.rhb.sas.interfaces.downloadreport.dto;

import java.util.Date;

import com.rhb.af.annotation.Caption;

/**
 * 
��Ӫҵ������ �� ��Ӫҵ������ - ��Ӫҵ��ɱ� - ��Ӫҵ��˰�𼰸���
Ӫҵ���� �� ��Ӫҵ������ + ����ҵ������ - �ڼ����
�����ܶ Ӫҵ���� + Ͷ������ + �������� + Ӫҵ����֧���
������ �� �����ܶ� - ����˰
�۳��Ǿ����������ľ�����(Ԫ)

 *
 */

public class ProfitStatementDTO  extends ReportInfoDTO{
	@Caption("����") private String reportPk = "";
	@Caption("��Ʊ����") private String stockNo = "";
	@Caption("��Ʊ����") private String stockName = "";
	@Caption("�ڳ���ĩ") private String periodType = "";
	@Caption("����ʱ��") private String description = "";
	@Caption("����ڼ���ʼ") private java.util.Date beginDate = new Date();
	@Caption("����ڼ��ֹ") private java.util.Date endDate = new Date();

	@Caption("��Ӫҵ������")private double primeOperatingRevenue = 0.0;  //�����.��Ӫҵ������
	@Caption("Ӫҵ����")private double operatingProfit = 0.0;  //�����.Ӫҵ����
	@Caption("������")private double netProfit = 0.0;  //�����.������
	@Caption("�������")private double operatingExpense = 0.0;  //�����.�������
	@Caption("���۷���")private double salesExpense = 0.0; //�����.���۷���
	@Caption("�������")private double financeExpense = 0.0; //�����.�������
	
	public ProfitStatementDTO(ReportInfoDTO dto){
		this.setBeginDate(dto.getBeginDate());
		this.setDescription(dto.getDescription());
		this.setEndDate(dto.getEndDate());
		this.setPeriodType(dto.getPeriodType());
		this.setStockName(dto.getStockName());
		this.setStockNo(dto.getStockNo());
	}

	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append(",��Ӫҵ������=" + primeOperatingRevenue);
		sb.append(",Ӫҵ����=" + operatingProfit);
		sb.append(",������=" + netProfit);
		sb.append(",�������=" + operatingExpense);
		sb.append(",���۷���=" + salesExpense);
		sb.append(",�������=" + financeExpense);
		return sb.toString();
	}

	
	public double getOperatingProfit() {
		return operatingProfit;
	}


	public void setOperatingProfit(double operatingProfit) {
		this.operatingProfit = operatingProfit;
	}


	public double getPrimeOperatingRevenue() {
		return primeOperatingRevenue;
	}
	public void setPrimeOperatingRevenue(double primeOperatingRevenue) {
		this.primeOperatingRevenue = primeOperatingRevenue;
	}
	public double getNetProfit() {
		return netProfit;
	}
	public void setNetProfit(double netProfit) {
		this.netProfit = netProfit;
	}


	public double getOperatingExpense() {
		return operatingExpense;
	}


	public void setOperatingExpense(double operatingExpense) {
		this.operatingExpense = operatingExpense;
	}


	public double getSalesExpense() {
		return salesExpense;
	}


	public void setSalesExpense(double salesExpense) {
		this.salesExpense = salesExpense;
	}


	public double getFinanceExpense() {
		return financeExpense;
	}


	public void setFinanceExpense(double financeExpense) {
		this.financeExpense = financeExpense;
	}
	 
	
}
