package com.rhb.sas.interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.application.console.Console;
import com.rhb.sas.interfaces.downloadmarketinfo.MarketInfoDTO;
import com.rhb.sas.util.Tools;
import com.rhb.af.util.AppContext;

public class Client_DownloadReport {

	private String appContextPath = "com/rhb/sas/AppContext.xml";
	private InterfacesFacade facade = (InterfacesFacade) AppContext.getInstance().getAppContext(appContextPath).getBean("interfacesFacade");
	private Sensor sensor = (Sensor) AppContext.getInstance().getAppContext(appContextPath).getBean("sensor");
	private Console console = (Console) AppContext.getInstance().getAppContext(appContextPath).getBean("console");
	
	private String stockNo;
	private Date issueDate_begin;
	private Date issueDate_end;
	public void setReDownAll(boolean reDownAll) {
		this.reDownAll = reDownAll;
	}

	private Date reportDate;
	private boolean overwrite = false;
	private boolean reDownAll = false;
	
	private int messageNumber = 100;
	/**
	 * 用于下载年报和季报
	 * 根据股票代码下载报告并保存
	 * 并对该股票的年报和季报进行增长率分析
	 * 最后更新股票的价值和折价率
	 */
	public void doIt(){
		console.setSyso(true);
		sensor.setMessage("******* begin ***********");
		sensor.setMessage("stockNo = " + stockNo);
		sensor.setMessage("reportDate = " + Tools.getDate(reportDate,"yyyy-MM-dd"));
		sensor.setMessage("issueDate_begin = " + Tools.getDate(issueDate_begin,"yyyy-MM-dd"));
		sensor.setMessage("issueDate_end = " + Tools.getDate(issueDate_end,"yyyy-MM-dd"));
		sensor.setMessage("owrite = " + overwrite);
		
		List<String> stockNos;
		if(!isEmpty(stockNo) && reportDate!=null && issueDate_begin==null && issueDate_end==null){
			stockNos = new ArrayList();
			stockNos.add(stockNo);
		}else if(isEmpty(stockNo) && reportDate!=null && issueDate_begin!=null && issueDate_end!=null){
			stockNos = facade.getReportedStockNo(reportDate, issueDate_begin, issueDate_end);
		}else if(isEmpty(stockNo) && reportDate!=null && issueDate_begin==null && issueDate_end==null){
			stockNos = facade.getNotReportedStockNo(reportDate);
		}else if(!isEmpty(stockNo) && reportDate==null && issueDate_begin==null && issueDate_end==null){
			stockNos = new ArrayList();
			stockNos.add(stockNo);
		}else if(reDownAll){
			stockNos = facade.getStockNos();	
		}else {
			sensor.setMessage("there are no stocks will to be done." );
			return ;
		}
		
		sensor.setMessage("there are " + stockNos.size()  + " stocks will to be done." );
		
		messageNumber = stockNos.size()* 4;
		
		sensor.setMessage("download reports ....");
		List reports = facade.downloadReport(stockNos, reportDate, overwrite);
		//sensor.setMessage("found "+reports.size()+" report date....");
		
		messageNumber = stockNos.size()* 4 + reports.size()*3;		
		
		sensor.setMessage("reCaculateEarningsGrowthRatio ....");
		facade.reCaculateEarningsGrowthRatio(stockNos);
		
		sensor.setMessage("update stock information from report ....");
		facade.refreshStockFromReport(stockNos);
		
		sensor.setMessage("update stock's value and discount ....");
		facade.refreshStockValueAndDiscount(stockNos);		

		sensor.setMessage("************* over **********");
	}
	
	private boolean isEmpty(String str){
		return (str==null || "".equals(str) || "null".equals(stockNo.toLowerCase())) ? true : false;
	}
	
	public List<String> getMessages(){
		return console.getMessages();
	}

	
	/**
	 * 根据报告期和报告发布的时间段得到股票代码
	 */
	public static void main(String[] args) {
		//Date reportDate = Tools.getDate("2014-12-31");
		//Date reportDate = null;
		//Date issueDate_begin = Tools.getDate("2010-04-05");
		//Date issueDate_end = Tools.getDate("2010-04-05");
		//String stockNo = "000001";
		//String stockNo = null;
		
		
		Client_DownloadReport client = new Client_DownloadReport();
		client.setOverwrite(false);
		client.setReDownAll(true);
		
		client.console.setSyso(true);
		
		//client.setStockNo(stockNo);
		//client.setReportDate(reportDate);
		//client.setIssueDate_begin(issueDate_begin);
		//client.setIssueDate_end(issueDate_end);
		client.doIt();
	}


	public String getStockNo() {
		return stockNo;
	}


	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}


	public Date getIssueDate_begin() {
		return issueDate_begin;
	}


	public void setIssueDate_begin(Date issueDate_begin) {
		this.issueDate_begin = issueDate_begin;
	}


	public Date getIssueDate_end() {
		return issueDate_end;
	}


	public void setIssueDate_end(Date issueDate_end) {
		this.issueDate_end = issueDate_end;
	}


	public Date getReportDate() {
		return reportDate;
	}


	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	public int getMessageNumber(){
		return messageNumber;
	}
	
	public void setSyso(boolean flag){
		this.console.setSyso(flag);
	}

	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}
	
}
