package com.rhb.sas.interfaces.downloadreport.dto;

import java.util.Date;

import com.rhb.af.annotation.Caption;

/**
 * 
主营业务利润 ＝ 主营业务收入 - 主营业务成本 - 主营业务税金及附加
营业利润 ＝ 主营业务利润 + 其他业务利润 - 期间费用
利润总额＝ 营业利润 + 投资收益 + 补贴收入 + 营业外收支差额
净利润 ＝ 利润总额 - 所得税
扣除非经常性损益后的净利润(元)

 *
 */

public class ProfitStatementDTO  extends ReportInfoDTO{
	@Caption("编码") private String reportPk = "";
	@Caption("股票代码") private String stockNo = "";
	@Caption("股票名称") private String stockName = "";
	@Caption("期初期末") private String periodType = "";
	@Caption("数据时间") private String description = "";
	@Caption("会计期间起始") private java.util.Date beginDate = new Date();
	@Caption("会计期间截止") private java.util.Date endDate = new Date();

	@Caption("主营业务收入")private double primeOperatingRevenue = 0.0;  //利润表.主营业务收入
	@Caption("营业利润")private double operatingProfit = 0.0;  //利润表.营业利润
	@Caption("净利润")private double netProfit = 0.0;  //利润表.净利润
	@Caption("管理费用")private double operatingExpense = 0.0;  //利润表.管理费用
	@Caption("销售费用")private double salesExpense = 0.0; //利润表.销售费用
	@Caption("财务费用")private double financeExpense = 0.0; //利润表.财务费用
	
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
		sb.append(",主营业务收入=" + primeOperatingRevenue);
		sb.append(",营业利润=" + operatingProfit);
		sb.append(",净利润=" + netProfit);
		sb.append(",管理费用=" + operatingExpense);
		sb.append(",销售费用=" + salesExpense);
		sb.append(",财务费用=" + financeExpense);
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
