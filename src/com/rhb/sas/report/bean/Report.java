package com.rhb.sas.report.bean;

import java.util.Date;

import com.rhb.sas.util.Tools;
import com.rhb.af.annotation.Caption;
import com.rhb.af.bean.BaseBean;
import com.rhb.af.bean.BaseQuery;
import com.rhb.af.util.Format;

public class Report extends BaseBean {
	@Caption("����") private String reportPk = "";
	@Caption("��Ʊ����") private String stockNo = "";
	@Caption("��Ʊ����") private String stockName = "";
	@Caption("�ڳ���ĩ") private String periodType = "";
	@Caption("����ʱ��") private String description = "";
	@Caption("����ڼ���ʼ") private java.util.Date beginDate = new Date();
	@Caption("����ڼ��ֹ") private java.util.Date endDate = new Date();
	@Caption("�ֽ�") private Double cash = 0.0;       			//�ʲ���ծ��.�����ʽ�
	@Caption("���") private Double inventories = 0.0;  			//�ʲ���ծ��.�������
	@Caption("Ӧ���ʿ�") private Double accountsRreceivable = 0.0; 	//�ʲ���ծ��.Ӧ���˿��
	@Caption("Ӧ��Ʊ��") private Double notesReceivable = 0.0;  		//�ʲ���ծ��.Ӧ��Ʊ��
	@Caption("����Ӧ�տ�") private Double otherReceivable = 0.0;  		//�ʲ���ծ��.����Ӧ�տ��
	@Caption("�����ʲ��ϼ�") private Double totalCurrentAssets = 0.0;  	//�ʲ���ծ��.�����ʲ��ϼ�
	@Caption("Ԥ�տ�") private Double payables = 0.0;      		//�ʲ���ծ��.Ԥ���ʿ�
	@Caption("������ծ") private Double currentLiabilities = 0.0;  	//�ʲ���ծ��.������ծ�ϼ�
	@Caption("���ʲ�") private Double totalAssets = 0.0;   		//�ʲ���ծ��.�ʲ��ܼ�
	@Caption("�ܸ�ծ") private Double totalLiabilities = 0.0;		//�ʲ���ծ��.��ծ�ϼ�
	@Caption("�����̶��ʲ��������ʲ������������ʲ���֧�����ֽ�") private Double purchaseAssets = 0.0;		//�ֽ�������.�����̶��ʲ��������ʲ������������ʲ���֧�����ֽ�
	@Caption("�̶��ʲ��۾ɡ������ʲ��ͳ��ڴ�̯����̯��") private Double depreciationAssets = 0.0;	//�ֽ�������.�̶��ʲ��۾�+�����ʲ�̯��+�����ʲ�̯��+���ڴ�̯����̯��
	@Caption("������") private Double netProfit = 0.0;				//�����.������
	@Caption("�۳��Ǿ���������ľ�����") private Double netProfitPlus = 0.0;			//����ָ��.�۳��Ǿ���������ľ����� 
	@Caption("��Ӫ��ֽ���������������") private Double shareholders = 0.0;			//  ����2��ľ�Ӫ��ֽ���������ͬ��������
	@Caption("��Ӫ��ֽ���������") private Double netCashFlow = 0.0;			//  ��Ӫ��ֽ���������
	@Caption("���ʲ�������") private Double roe = 0.0;					//  = earningsPerShare/netAssetsPerShare
	private Double earnings = 0.0;				//  = primeOperatingProfitPlus*0.75+depreciationAssets-purchaseAssets,���չɲ�һ��
	private Double earningsAvg = 0.0;			//  �������earnings���ƶ�ƽ��
	private Double earningsGrowthRatio = 0.0;	//  ����2���ͬ��������
	private String theYear = "";
	private String theMonth = "";
	private int readOnly = 0;
	@Caption("��Ӫҵ������") private Double primeOperatingRevenue = 0.0;   	//�����.��Ӫҵ������
	@Caption("��Ӫҵ������") private Double primeOperatingProfit = 0.0;		//����ָ��.��Ӫҵ������
	@Caption("Ӫҵ����") private Double operatingProfit = 0.0;	//�����.Ӫҵ����  Ӫҵ����=��Ӫҵ������+����ҵ������-Ӫҵ����-�������-�������
	@Caption("ÿ�ɾ��ʲ�") private Double netAssetsPerShare = 0.0;   		//����ժҪ.ÿ�ɾ��ʲ�
	@Caption("ÿ������") private Double earningsPerShare = 0.0;			//����ժҪ.ÿ������
	@Caption("ÿ���ֽ���") private Double netCashFlowPerShare = 0.0;		//����ժҪ.ÿ���ֽ���
	@Caption("ÿ���ʱ�������") private Double capitalFundPerShare = 0.0;		//����ժҪ.ÿ���ʱ�������
	private double operatingExpense = 0.0;  //�����.�������
	private double salesExpense = 0.0; //�����.���۷���
	private double financeExpense = 0.0; //�����.�������

	
	public void updateByInput(Report report){
		this.cash = report.cash==0.0 ? this.cash : report.cash;       			//�ʲ���ծ��.�����ʽ�
		this.inventories = report.inventories==0.0 ? this.inventories : report.inventories;  			//�ʲ���ծ��.�������
		this.accountsRreceivable = report.accountsRreceivable==0.0 ? this.accountsRreceivable : report.accountsRreceivable; 	//�ʲ���ծ��.Ӧ���˿��
		this.notesReceivable = report.notesReceivable==0.0 ? this.notesReceivable : report.notesReceivable;  		//�ʲ���ծ��.Ӧ��Ʊ��
		this.otherReceivable = report.otherReceivable==0.0 ? this.otherReceivable : report.otherReceivable;  		//�ʲ���ծ��.����Ӧ�տ��
		this.totalCurrentAssets = report.totalCurrentAssets==0.0 ? this.totalCurrentAssets : report.totalCurrentAssets;  	//�ʲ���ծ��.�����ʲ��ϼ�
		this.payables = report.payables==0.0 ? this.payables : report.payables;      		//�ʲ���ծ��.Ԥ���ʿ�
		this.currentLiabilities = report.currentLiabilities==0.0 ? this.currentLiabilities : report.currentLiabilities;  	//�ʲ���ծ��.������ծ�ϼ�
		this.totalAssets = report.totalAssets==0.0 ? this.totalAssets : report.totalAssets;   		//�ʲ���ծ��.�ʲ��ܼ�
		this.totalLiabilities = report.totalLiabilities==0.0 ? this.totalLiabilities : report.totalLiabilities;		//�ʲ���ծ��.��ծ�ϼ�
		this.purchaseAssets = report.purchaseAssets==0.0 ? this.purchaseAssets : report.purchaseAssets;		//�ֽ�������.�����̶��ʲ��������ʲ������������ʲ���֧�����ֽ�
		this.depreciationAssets = report.depreciationAssets==0.0 ? this.depreciationAssets : report.depreciationAssets;	//�ֽ�������.�̶��ʲ��۾�+�����ʲ�̯��+�����ʲ�̯��+���ڴ�̯����̯��
		this.netProfit = report.netProfit==0.0 ? this.netProfit : report.netProfit;				//�����.������
		this.netProfitPlus = report.netProfitPlus==0.0 ? this.netProfitPlus : report.netProfitPlus;			//����ָ��.�۳��Ǿ���������ľ����� 
		this.shareholders = report.shareholders==0.0 ? this.shareholders : report.shareholders;			//  ����2��ľ�Ӫ��ֽ���������ͬ��������
		this.netCashFlow = report.netCashFlow==0.0 ? this.netCashFlow : report.netCashFlow;			//  ��Ӫ��ֽ���������
		this.roe = report.roe==0.0 ? this.roe : report.roe;					//  = earningsPerShare/netAssetsPerShare
		this.earnings = report.earnings==0.0 ? this.earnings : report.earnings;				//  = primeOperatingProfitPlus*0.75+depreciationAssets-purchaseAssets,���չɲ�һ��
		this.earningsAvg = report.earningsAvg==0.0 ? this.earningsAvg : report.earningsAvg;			//  �������earnings���ƶ�ƽ��
		this.earningsGrowthRatio = report.earningsGrowthRatio==0.0 ? this.earningsGrowthRatio : report.earningsGrowthRatio;	//  ����2���ͬ��������
		this.primeOperatingRevenue = report.primeOperatingRevenue==0.0 ? this.primeOperatingRevenue : report.primeOperatingRevenue;   	//�����.��Ӫҵ������
		this.primeOperatingProfit = report.primeOperatingProfit==0.0 ? this.primeOperatingProfit : report.primeOperatingProfit;		//����ָ��.��Ӫҵ������
		this.operatingProfit = report.operatingProfit==0.0 ? this.operatingProfit : report.operatingProfit;	//�����.Ӫҵ����  Ӫҵ����=��Ӫҵ������+����ҵ������-Ӫҵ����-�������-�������
		this.netAssetsPerShare = report.netAssetsPerShare==0.0 ? this.netAssetsPerShare : report.netAssetsPerShare;   		//����ժҪ.ÿ�ɾ��ʲ�
		this.earningsPerShare = report.earningsPerShare==0.0 ? this.earningsPerShare : report.earningsPerShare;			//����ժҪ.ÿ������
		this.netCashFlowPerShare = report.netCashFlowPerShare==0.0 ? this.netCashFlowPerShare : report.netCashFlowPerShare;		//����ժҪ.ÿ���ֽ���
		this.capitalFundPerShare = report.capitalFundPerShare==0.0 ? this.capitalFundPerShare : report.capitalFundPerShare;		//����ժҪ.ÿ���ʱ�������
		this.operatingExpense = report.operatingExpense==0.0 ? this.operatingExpense : report.operatingExpense;  //�����.�������
		this.salesExpense = report.salesExpense==0.0 ? this.salesExpense : report.salesExpense; //�����.���۷���
		this.financeExpense = report.financeExpense==0.0 ? this.financeExpense : report.financeExpense; //�����.�������
		
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
	public Double getOperatingProfit() {
		return operatingProfit;
	}
	public void setOperatingProfit(Double operatingProfit) {
		this.operatingProfit = operatingProfit;
	}
	public String getReportPkCaption() {
		return "����";
	}
	public String getStockNoCaption() {
		return "��Ʊ����";
	}
	public String getStockNameCaption() {
		return "��Ʊ����";
	}
	public String getPeriodTypeCaption() {
		return "�ڳ���ĩ";
	}
	public String getDescriptionCaption() {
		return "����ʱ��";
	}
	public String getBeginDateCaption() {
		return "����ڼ���ʼ";
	}
	public String getEndDateCaption() {
		return "����ڼ��ֹ";
	}
	public String getCashCaption() {
		return "�ֽ�";
	}
	public String getInventoriesCaption() {
		return "���";
	}
	public String getAccountsRreceivableCaption() {
		return "Ӧ���ʿ�";
	}
	public String getNotesReceivableCaption() {
		return "Ӧ��Ʊ��";
	}
	public String getOtherReceivableCaption() {
		return "����Ӧ�տ�";
	}
	public String getTotalCurrentAssetsCaption() {
		return "�����ʲ��ϼ�";
	}
	public String getPayablesCaption() {
		return "Ԥ�տ�";
	}
	public String getCurrentLiabilitiesCaption() {
		return "������ծ";
	}
	public String getTotalAssetsCaption() {
		return "���ʲ�";
	}
	public String getTotalLiabilitiesCaption() {
		return "�ܸ�ծ";
	}
	public String getPurchaseAssetsCaption() {
		return "�����̶��ʲ��������ʲ������������ʲ���֧�����ֽ�";
	}
	public String getDepreciationAssetsCaption() {
		return "�̶��ʲ��۾ɡ������ʲ��ͳ��ڴ�̯����̯��";
	}
	public String getNetProfitCaption() {
		return "������";
	}
	public String getShareholdersCaption() {
		return "�ɶ�Ȩ��";
	}
	public String getNetCashFlowCaption() {
		return "��Ӫ��ֽ���������";
	}
	public String getRoeCaption() {
		return "���ʲ�������";
	}
	public String getNetProfitPlusCaption() {
		return "�۳��Ǿ���������ľ�����";
	}
	public String getPrimeOperatingRevenueCaption() {
		return "��Ӫҵ������";
	}

	public String getPrimeOperatingProfitCaption() {
		return "��Ӫҵ������";
	}

	public Double getNetAssetsPerShare() {
		return netAssetsPerShare;
	}

	public void setNetAssetsPerShare(Double netAssetsPerShare) {
		this.netAssetsPerShare = netAssetsPerShare;
	}

	public Double getEarningsPerShare() {
		return earningsPerShare;
	}

	public void setEarningsPerShare(Double earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}

	public Double getNetCashFlowPerShare() {
		return netCashFlowPerShare;
	}

	public void setNetCashFlowPerShare(Double netCashFlowPerShare) {
		this.netCashFlowPerShare = netCashFlowPerShare;
	}

	public Double getCapitalFundPerShare() {
		return capitalFundPerShare;
	}

	public void setCapitalFundPerShare(Double capitalFundPerShare) {
		this.capitalFundPerShare = capitalFundPerShare;
	}

	public int getReadOnly() {
		return readOnly;
	}
	
	public double getReceivable(){
		double notesReceivable = 0.0;
		if(this.getNotesReceivable() != null){
			notesReceivable = this.getNotesReceivable();
		}
		
		double otherReceivable = 0.0;
		if(this.getOtherReceivable() != null){
			otherReceivable = this.getOtherReceivable();
		}
		return this.getAccountsRreceivable()
				+ notesReceivable 
				+ otherReceivable;
	}

	public void setReadOnly(int readOnly) {
		this.readOnly = readOnly;
	}

	public String getTheYear() {
		return Tools.getDate(endDate, "yyyy");
	}

	public void setTheYear(String theYear) {
		this.theYear = theYear;
	}

	public String getTheMonth() {
		return Tools.getDate(endDate, "MM");
	}

	public void setTheMonth(String theMonth) {
		this.theMonth = theMonth;
	}

	public Report() {
	}

	public void setReportPk(String reportPk) {
		this.reportPk = reportPk;
	}

	public String getReportPk() {
		return this.reportPk;
	}


	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockNo() {
		return this.stockNo;
	}


	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockName() {
		return this.stockName;
	}


	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getPeriodType() {
		return this.periodType;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}


	public void setBeginDate(java.util.Date beginDate) {
		this.beginDate = beginDate;
	}

	public java.util.Date getBeginDate() {
		return this.beginDate;
	}


	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public java.util.Date getEndDate() {
		return this.endDate;
	}


	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getCash() {
		return this.cash;
	}


	public void setInventories(Double inventories) {
		this.inventories = inventories;
	}

	public Double getInventories() {
		return this.inventories;
	}


	public void setAccountsRreceivable(Double accountsRreceivable) {
		this.accountsRreceivable = accountsRreceivable;
	}

	public Double getAccountsRreceivable() {
		return this.accountsRreceivable;
	}


	public void setNotesReceivable(Double notesReceivable) {
		this.notesReceivable = notesReceivable;
	}

	public Double getNotesReceivable() {
		return this.notesReceivable;
	}


	public void setOtherReceivable(Double otherReceivable) {
		this.otherReceivable = otherReceivable;
	}

	public Double getOtherReceivable() {
		return this.otherReceivable;
	}


	public void setTotalCurrentAssets(Double totalCurrentAssets) {
		this.totalCurrentAssets = totalCurrentAssets;
	}

	public Double getTotalCurrentAssets() {
		return this.totalCurrentAssets;
	}

	public void setPayables(Double payables) {
		this.payables = payables;
	}

	public Double getPayables() {
		return this.payables;
	}


	public void setCurrentLiabilities(Double currentLiabilities) {
		this.currentLiabilities = currentLiabilities;
	}

	public Double getCurrentLiabilities() {
		return this.currentLiabilities;
	}


	public void setTotalAssets(Double totalAssets) {
		this.totalAssets = totalAssets;
	}

	public Double getTotalAssets() {
		return this.totalAssets;
	}


	public void setTotalLiabilities(Double totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public Double getTotalLiabilities() {
		return this.totalLiabilities;
	}


	public void setPurchaseAssets(Double purchaseAssets) {
		this.purchaseAssets = purchaseAssets;
	}

	public Double getPurchaseAssets() {
		return this.purchaseAssets;
	}


	public void setDepreciationAssets(Double depreciationAssets) {
		this.depreciationAssets = depreciationAssets;
	}

	public Double getDepreciationAssets() {
		return this.depreciationAssets;
	}


	public void setNetProfit(Double netProfit) {
		this.netProfit = netProfit;
	}

	public Double getNetProfit() {
		return this.netProfit;
	}

	
	public void setNetProfitPlus(Double netProfitPlus) {
		this.netProfitPlus = netProfitPlus;
	}

	public Double getNetProfitPlus() {
		return this.netProfitPlus;
	}

	public void setShareholders(Double shareholders) {
		this.shareholders = shareholders;
	}

	public Double getShareholders() {
		return this.shareholders;
	}
	
	public Double getShareholders_year(){
		return this.shareholders * getAb();
	}


	public void setNetCashFlow(Double netCashFlow) {
		this.netCashFlow = netCashFlow;
	}

	public Double getNetCashFlow() {
		return this.netCashFlow;
	}


	public void setRoe(Double roe) {
		this.roe = roe;
	}

	public Double getRoe() {
		if(this.netAssetsPerShare!=null && this.netAssetsPerShare>0){
			return this.earningsPerShare/this.netAssetsPerShare;
		}else{
			return this.roe;			
		}
	}


	public String requiredValidate() {
		StringBuffer errors = new StringBuffer();
		if (this.stockNo == null || this.stockNo.trim().length() == 0) {
			errors.append(this.getStockNoCaption() + "����Ϊ�ա�");
		}
		/*
		if (this.stockName == null || this.stockName.trim().length() == 0) {
			errors.append(this.getStockNameCaption() + "����Ϊ�ա�");
		}
		*/
		if (this.periodType == null || this.periodType.trim().length() == 0) {
			errors.append(this.getPeriodTypeCaption() + "����Ϊ�ա�");
		}
		if (this.description == null || this.description.trim().length() == 0) {
			errors.append(this.getDescriptionCaption() + "����Ϊ�ա�");
		}
		if (this.beginDate == null) {
			errors.append(this.getBeginDateCaption() + "����Ϊ�ջ������ڸ�ʽ��");
		}
		if (this.endDate == null) {
			errors.append(this.getEndDateCaption() + "����Ϊ�ջ������ڸ�ʽ��");
		}
		return errors.toString();
	}

	public String outOfRangeValidate() {
		StringBuffer errors = new StringBuffer();
		if (this.reportPk.length() > 24) {
			errors.append(this.getReportPkCaption() + "�ַ���Ϊ"
					+ this.reportPk.length() + ",������24���ַ���������ƣ�");
		}
		if (this.stockNo.length() > 24) {
			errors.append(this.getStockNoCaption() + "�ַ���Ϊ"
					+ this.stockNo.length() + ",������24���ַ���������ƣ�");
		}
		/*
		if (this.stockName.length() > 24) {
			errors.append(this.getStockNameCaption() + "�ַ���Ϊ"
					+ this.stockName.length() + ",������24���ַ���������ƣ�");
		}
		*/
		if (this.periodType.length() > 24) {
			errors.append(this.getPeriodTypeCaption() + "�ַ���Ϊ"
					+ this.periodType.length() + ",������24���ַ���������ƣ�");
		}
		if (this.description.length() > 24) {
			errors.append(this.getDescriptionCaption() + "�ַ���Ϊ"
					+ this.description.length() + ",������24���ַ���������ƣ�");
		}
		return errors.toString();
	}

	public Double getEarnings() {
		double d = 0;
		/*
		if(this.netProfitPlus < 0.0000001){
			double op = 0;
			if(isFinance(stockNo) == -1){
				//���ڷǽ�����ҵ���� = ��Ӫҵ������ - ���۷��� - ������� - �������
				op = primeOperatingProfit-salesExpense-operatingExpense-financeExpense;
			}else{
				//���ڽ�����ҵ����Ӫҵ������Ϊ����
				//���� = Ӫҵ���� = ��Ӫҵ������ + ����ҵ������ - Ӫҵ���� - ������� - �������
				op = operatingProfit;
			}
			d = op*0.75;
		}else{
			d = this.netProfitPlus;
		}
		*/
		
		//��Ϊ��2011-08-06�󣬴������޷�������Ӫҵ������Ϳ۳��Ǿ��������������ֻ�ܾ���Ӫҵ�������
		
		if(this.netProfitPlus < 0.0000001){
			d = this.operatingProfit * 0.75;
		}else{
			d = this.netProfitPlus;
		}		
		/*
		 * ���ڼ�������a��b������ȫ�������
		 */
		
		double earns = (d + this.depreciationAssets - this.purchaseAssets) * getAb();
		
		
		//System.out.println(this.getTheYear() + this.getTheMonth() + " earns = " + earns/100000000);
		//System.out.println("(" + d/100000000 + "+" + this.depreciationAssets/100000000 + "-" + this.purchaseAssets/100000000+ ")*" + a +"/" + b);
		//System.out.println(""); 
		
		return earns;
	}
	
	private double getAb(){
		int a=4;
		int b=4;   
		if("03".equals(theMonth)){  
			b = 1;
		}else if("06".equals(theMonth)){
			b = 2;
		}else if("09".equals(theMonth)){
			b = 3;
		}
		return a/b;
	}

	public void setEarnings(Double earnings) {
		this.earnings = earnings;
	}

	public Double getEarningsAvg() {
		return earningsAvg;
	}

	public void setEarningsAvg(Double earningsAvg) {
		this.earningsAvg = earningsAvg;
	}

	public Double getEarningsGrowthRatio() {
		return earningsGrowthRatio;
	}

	public void setEarningsGrowthRatio(Double earningsGrowthRatio) {
		this.earningsGrowthRatio = earningsGrowthRatio;
	}
	
	public String getFinanceSummary(){
		StringBuffer sb = new StringBuffer();
		sb.append("netAssetsPerShare = " + this.netAssetsPerShare + "\n");
		sb.append("earningsPerShare = " + this.earningsPerShare + "\n");
		sb.append("netCashFlowPerShare = " + this.netCashFlowPerShare + "\n");
		sb.append("capitalFundPerShare = " + this.capitalFundPerShare + "\n");
		return sb.toString();
	}

	public String getBalanceSheet(){
		StringBuffer sb = new StringBuffer();
		sb.append("reportPk = " + this.getReportPk() + "\n");
		sb.append("stockNo = " + this.getStockNo() + "\n");
		sb.append("stockName = " + this.getStockName() + "\n");
		sb.append("periodType = " + this.getPeriodType() + "\n");
		sb.append("description = " + this.getDescription() + "\n");
		sb.append("beginDate = " + this.getBeginDate() + "\n");
		sb.append("endDate = " + this.getEndDate() + "\n");
		sb.append("cash = " + this.getCash() + "\n");
		sb.append("inventories = " + this.getInventories() + "\n");
		sb.append("accountsReceivable = " + this.getAccountsRreceivable() + "\n");
		sb.append("notesReceivable = " + this.getNotesReceivable() + "\n");
		sb.append("otherReceivable = " + this.getOtherReceivable() + "\n");
		sb.append("totalCurrentAssets = " + this.getTotalCurrentAssets() + "\n");
		sb.append("payables = " + this.getPayables() + "\n");
		sb.append("currentLiabilities = " + this.getCurrentLiabilities() + "\n");
		sb.append("totalAssets = " + this.getTotalAssets() + "\n");
		sb.append("totalLiabilities = " + this.getTotalLiabilities() + "\n");
		
		return sb.toString();
	}

	public String getCashFlow(){
		StringBuffer sb = new StringBuffer();
		sb.append("reportPk = " + this.getReportPk() + "\n");
		sb.append("stockNo = " + this.getStockNo() + "\n");
		sb.append("stockName = " + this.getStockName() + "\n");
		sb.append("periodType = " + this.getPeriodType() + "\n");
		sb.append("description = " + this.getDescription() + "\n");
		sb.append("beginDate = " + this.getBeginDate() + "\n");
		sb.append("endDate = " + this.getEndDate() + "\n");
		sb.append("purchaseAssets = " + this.getPurchaseAssets() + "\n");
		sb.append("depreciationAssets = " + this.getDepreciationAssets() + "\n");
		
		return sb.toString();
	}

	public String getProfitStatement(){
		StringBuffer sb = new StringBuffer();
		sb.append("reportPk = " + this.getReportPk() + "\n");
		sb.append("stockNo = " + this.getStockNo() + "\n");
		sb.append("stockName = " + this.getStockName() + "\n");
		sb.append("periodType = " + this.getPeriodType() + "\n");
		sb.append("description = " + this.getDescription() + "\n");
		sb.append("beginDate = " + this.getBeginDate() + "\n");
		sb.append("endDate = " + this.getEndDate() + "\n");
		sb.append("primeOperatingRevenue = " + this.getPrimeOperatingRevenue() + "\n");
		sb.append("operatingProfit = " + this.operatingProfit + "\n");
		sb.append("netProfit = " + this.getNetProfit() + "\n");
		
		return sb.toString();
	}

	public String getFinancialGuide(){
		StringBuffer sb = new StringBuffer();
		sb.append("reportPk = " + this.getReportPk() + "\n");
		sb.append("stockNo = " + this.getStockNo() + "\n");
		sb.append("stockName = " + this.getStockName() + "\n");
		sb.append("periodType = " + this.getPeriodType() + "\n");
		sb.append("description = " + this.getDescription() + "\n");
		sb.append("beginDate = " + this.getBeginDate() + "\n");
		sb.append("endDate = " + this.getEndDate() + "\n");
		sb.append("primeOperatingProfit = " + this.getPrimeOperatingProfit() + "\n");
		sb.append("netProfitPlus = " + this.getNetProfitPlus() + "\n");
		
		return sb.toString();
	}
	
	
	
	public Double getPrimeOperatingRevenue() {
		return primeOperatingRevenue;
	}

	public void setPrimeOperatingRevenue(Double primeOperatingRevenue) {
		this.primeOperatingRevenue = primeOperatingRevenue;
	}

	public Double getPrimeOperatingProfit() {
		return primeOperatingProfit;
	}

	public Double getGrossProfit() {
		if(this.primeOperatingRevenue != null && this.primeOperatingRevenue > 0){
			return this.primeOperatingProfit/this.primeOperatingRevenue;			
		}else{
			return 0.0;
		}
	}

	public Double getGrossProfitPlus() {
		if(this.primeOperatingRevenue != null && this.primeOperatingRevenue > 0){
			return this.operatingProfit/this.primeOperatingRevenue;			
		}else{
			return 0.0;
		}
	}
	
	public void setPrimeOperatingProfit(Double primeOperatingProfit) {
		this.primeOperatingProfit = primeOperatingProfit;
	}


	public Double getInventoryRatio(){
		if(this.primeOperatingRevenue != null && this.primeOperatingRevenue > 0){
			return this.inventories/this.primeOperatingRevenue;			
		}else{
			return 0.0;
		}
	}
	public Double getAccountsRreceivableRatio(){
		if(this.primeOperatingRevenue != null && this.primeOperatingRevenue > 0){
			return this.accountsRreceivable/this.primeOperatingRevenue;			
		}else{
			return 0.0;
		}
	}
	
	public String toString(){
		return this.reportPk + "," + this.stockNo + "," + this.theYear + "," + this.theMonth + "," + this.description;
	}
	
}
