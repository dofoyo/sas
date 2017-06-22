package com.rhb.sas2.domain;

public class Report{
	private ProfitStatement profitStatement;
	private BalanceSheet balanceSheet;
	private CashFlow cashFlow;
	private FinanceSummary financeSummary;
	private FinancialGuide financialGuide;
	private String reportDate;
	private String issueDate;
	
	public Report(){
		super();
		this.reportDate = "";
		this.issueDate = "";
		this.profitStatement = new ProfitStatement();
		this.balanceSheet = new BalanceSheet();
		this.cashFlow = new CashFlow();
		this.financeSummary = new FinanceSummary();
		this.financialGuide = new FinancialGuide();		
	}
	

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}


	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}


	public ProfitStatement getProfitStatement() {
		return profitStatement;
	}
	public void setProfitStatement(ProfitStatement profitStatement) {
		this.profitStatement = profitStatement;
	}
	public BalanceSheet getBalanceSheet() {
		return balanceSheet;
	}
	public void setBalanceSheet(BalanceSheet balanceSheet) {
		this.balanceSheet = balanceSheet;
	}
	public CashFlow getCashFlow() {
		return cashFlow;
	}
	public void setCashFlow(CashFlow cashFlow) {
		this.cashFlow = cashFlow;
	}
	public FinanceSummary getFinanceSummary() {
		return financeSummary;
	}
	public void setFinanceSummary(FinanceSummary financeSummary) {
		this.financeSummary = financeSummary;
	}
	public FinancialGuide getFinancialGuide() {
		return financialGuide;
	}
	public void setFinancialGuide(FinancialGuide financialGuide) {
		this.financialGuide = financialGuide;
	}
	public String getReportDate() {
		return reportDate;
	}
	public String getIssueDate() {
		return issueDate;
	}

	@Override
	public String toString() {
		return "Report [profitStatement=" + profitStatement + ", balanceSheet="
				+ balanceSheet + ", cashFlow=" + cashFlow + ", financeSummary="
				+ financeSummary + ", financialGuide=" + financialGuide
				+ ", reportDate=" + reportDate + ", issueDate=" + issueDate
				+ "]";
	}
	
	
	
	
	
}
