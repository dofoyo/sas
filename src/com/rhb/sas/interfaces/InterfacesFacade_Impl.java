package com.rhb.sas.interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.download.yjyg.DownloadYjyg;
import com.rhb.sas.download.yjyg.YjygDTO;
import com.rhb.sas.interfaces.downloadmarketinfo.DownloadMarketInfo;
import com.rhb.sas.interfaces.downloadmarketinfo.MarketInfoDTO;
import com.rhb.sas.interfaces.downloadreport.DownloadReport;
import com.rhb.sas.interfaces.downloadreport.DownloadReportedStockNos;
import com.rhb.sas.interfaces.downloadreport.dto.BalanceSheetDTO;
import com.rhb.sas.interfaces.downloadreport.dto.CashFlowDTO;
import com.rhb.sas.interfaces.downloadreport.dto.FinanceSummaryDTO;
import com.rhb.sas.interfaces.downloadreport.dto.ProfitStatementDTO;
import com.rhb.sas.interfaces.downloadreport.dto.ReportInfoDTO;
import com.rhb.sas.interfaces.downloadreport.dto.FinancialGuideDTO;
import com.rhb.sas.report.bean.Report;
import com.rhb.sas.report.bean.ReportQuery;
import com.rhb.sas.report.business.ReportBusiness;
import com.rhb.sas.report.profitstatement.DownloadProfitStatement;
import com.rhb.sas.report.profitstatement.ProfitStatement;
import com.rhb.sas.report.profitstatement.ProfitStatementBusiness;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;
import com.rhb.sas.stock.business.StockBusiness;
import com.rhb.sas.util.Tools;
import com.rhb.af.bean.Page;
import com.rhb.af.business.FindBusiness;
import com.rhb.af.exception.DuplicateException;
import com.rhb.af.exception.OutOfRangeException;
import com.rhb.af.exception.RequiredException;

public class InterfacesFacade_Impl implements InterfacesFacade {

	private StockBusiness stockBusiness;
	private ReportBusiness reportBusiness;
	private FindBusiness findBusiness;
	private DownloadMarketInfo downloadMarketInfo;
	private DownloadReport downloadBalanceSheet;
	private DownloadReport downloadCashFlow;
	private DownloadReport downloadFinanceSummary;
	private DownloadReport downloadProfitStatement;
	private DownloadReportedStockNos downloadReportedStockNos;
	private DownloadReport downloadFinancialGuide;
	private DownloadYjyg downloadYjyg;
	private ProfitStatementBusiness pss;
	private DownloadProfitStatement down;

	public DownloadProfitStatement getDown() {
		return down;
	}

	public void setDown(DownloadProfitStatement down) {
		this.down = down;
	}

	public ProfitStatementBusiness getPss() {
		return pss;
	}

	public void setPss(ProfitStatementBusiness pss) {
		this.pss = pss;
	}

	private Sensor sensor;
	
	public void downloadYjyg(String theyearandmonth, int thepage){
		if(theyearandmonth==null){
			sensor.setMessage("the yearandmonth can NOT be null");
			return;
		}
		
		if(thepage <= 0){
			sensor.setMessage("the page can NOT be zero or a negative number");
			return;
		}
		
		if(!Tools.isDate(theyearandmonth, "yyyyMM")){
			sensor.setMessage("yearandmonth must be year and month, just like: 201206");
			return;
		}
		
		
		List<YjygDTO> list = downloadYjyg.doIt(theyearandmonth, thepage);
		if(list == null){
			sensor.setMessage("there are nothing to download.");
			return;
		}

		Stock stock;
		for(YjygDTO dto : list){
			stock = (Stock)findBusiness.findByPK(Stock.class, dto.getStockNo());
			if(stock != null){
				stock.setMainBusiness(theyearandmonth + dto.getYglx());
				try {
					stockBusiness.save(stock);
					sensor.setMessage("save " + stock.getStockNo() + ", " + stock.getStockName());
				} catch (Exception e) {
					sensor.setMessage("********** error **********");
					sensor.setMessage(e.toString());
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<MarketInfoDTO> downloadMarketInfo(){
		return downloadMarketInfo.doIt();
	}
	
	public List<String> saveMarketInfo(List<MarketInfoDTO> list){
		List<String> stocks = new ArrayList();
		if(list != null){
			Stock stock;
			for(MarketInfoDTO dto: list){
				//System.out.println(dto.getStockNo());
				stock = (Stock)findBusiness.findByPK(Stock.class, dto.getStockNo());
				if(stock == null){
					stock = new Stock();
					stock.setStockNo(dto.getStockNo());
					stock.setStockName(dto.getStockName());
					//stock.setReportDate(null);
					//stock.setNoticeDate(null);
					if(dto.getPrice()!=null && dto.getPrice()>0){
						stock.setPricePerShare(dto.getPrice());						
					}
					if(dto.getMarketValue()!=null && dto.getMarketValue()>0){
						stock.setMarketValue(dto.getMarketValue());						
					}
					if(dto.getCurrentMarketValue()!=null && dto.getCurrentMarketValue()>0){
						stock.setCurrentMarketValue(dto.getCurrentMarketValue());						
					}
					stocks.add(stock.getStockNo());
				}else{
					stock.setStockName(dto.getStockName());
					if(dto.getPrice()!=null && dto.getPrice()>0){
						stock.setPricePerShare(dto.getPrice());						
					}
					if(dto.getMarketValue()!=null && dto.getMarketValue()>0){
						stock.setMarketValue(dto.getMarketValue());						
					}
					if(dto.getCurrentMarketValue()!=null && dto.getCurrentMarketValue()>0){
						stock.setCurrentMarketValue(dto.getCurrentMarketValue());						
					}
				}
				try {
					stockBusiness.save(stock);
					sensor.setMessage("save " + stock);
				} catch (Exception e) {
					sensor.setMessage("********** error **********");
					sensor.setMessage(e.toString());
					e.printStackTrace();
				}
			}
		}
		
		return stocks;
	}

	public void reCaculateEarningsGrowthRatio(List<String> stockNos){
		reportBusiness.caculateEarningsGrowthRatio((String[])stockNos.toArray(new String[stockNos.size()]));
	}
	public void refreshStockFromReport(List<String> stockNos){
		stockBusiness.updateFromReports((String[])stockNos.toArray(new String[stockNos.size()]));
	}

	public StockBusiness getStockBusiness() {
		return stockBusiness;
	}

	public void setStockBusiness(StockBusiness stockBusiness) {
		this.stockBusiness = stockBusiness;
	}

	public ReportBusiness getReportBusiness() {
		return reportBusiness;
	}

	public void setReportBusiness(ReportBusiness reportBusiness) {
		this.reportBusiness = reportBusiness;
	}

	public FindBusiness getFindBusiness() {
		return findBusiness;
	}

	public void setFindBusiness(FindBusiness findBusiness) {
		this.findBusiness = findBusiness;
	}

	public DownloadMarketInfo getDownloadMarketInfo() {
		return downloadMarketInfo;
	}

	public void setDownloadMarketInfo(DownloadMarketInfo downloadMarketInfo) {
		this.downloadMarketInfo = downloadMarketInfo;
	}


	@Override
	public List downloadReport(List<String> stockNos, Date reportDate, boolean overwrite) {
		List list = new ArrayList();
		for(int i=0; i<stockNos.size(); i++){
			String stockNo = stockNos.get(i);
			sensor.setMessage((i+1) + "/"  + stockNos.size());
			list.addAll(downloadReport(stockNo, reportDate, overwrite));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	
	@Override
	public List downloadReport(String stockNo, Date reportDate, boolean overwrite) {
		
		String str = reportDate==null ? " all " : Tools.getDate(reportDate, "yyyy-MM-dd");
		
		sensor.setMessage("****** download "+ stockNo + " " +  str +"  reports *******");

		List list = new ArrayList();

		boolean exist = false;

		if(!overwrite){
			String theyear = Tools.getDate(reportDate, "yyyy");
			String themonth = Tools.getDate(reportDate, "MM");
			ReportQuery rq = new ReportQuery();
			rq.setStockNo(stockNo);
			rq.setTheYear(theyear);
			rq.setTheMonth(themonth);
			int i = findBusiness.findByQuery(rq).getAllCount();
			if(i>0){
				exist = true;
				sensor.setMessage("****** report has exist *******");
			}
		}
		
		if(overwrite || !exist){
			List balanceSheets = downloadBalanceSheet.doIt(stockNo, reportDate);
			if(balanceSheets.size() != 0){
				System.out.println("balanceSheets = " + balanceSheets.size());
				saveBalanceSheets(balanceSheets);
		
				List cashFlows = downloadCashFlow.doIt(stockNo, reportDate);
				System.out.println("cashFlows = " + cashFlows.size());
				saveCashFlows(cashFlows);
				
				List financeSummarys  = downloadFinanceSummary.doIt(stockNo, reportDate);
				System.out.println("financeSummarys = " + financeSummarys.size());
				saveFinanceSummarys(financeSummarys);
				
				List profitStatements = downloadProfitStatement.doIt(stockNo, reportDate);
				System.out.println("profitStatements = " + profitStatements.size());
				saveProfitStatements(profitStatements);
		
				List financialGuides = downloadFinancialGuide.doIt(stockNo, reportDate);
				System.out.println("financialGuides = " + financialGuides.size());
				saveFinacialGuides(financialGuides);

				list.addAll(balanceSheets);
				list.addAll(cashFlows);
				list.addAll(financeSummarys);
				list.addAll(profitStatements);
				list.addAll(financialGuides);
				
				List<ProfitStatement> l = down.down(stockNo,reportDate);
				if(l != null){
					for(ProfitStatement ps : l){
						try {
							//System.out.print(ps);
							pss.save(ps);
							System.out.println(",has saved.");
						} catch (DuplicateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (RequiredException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (OutOfRangeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}						
				}
				
			}
		}
		
		
		//System.out.println("all together = " + list.size());
		
		return list;
	}
	
	private void saveFinacialGuides(List list){
		for(Object obj : list){
			saveFinancialGuide(obj);
		}
	}

	private void saveFinancialGuide(Object obj){
		List reports = findBusiness.findByQuery(getQuery((ReportInfoDTO)obj)).getList();
		Report report;
		if(reports!=null && reports.size()>0){
			report = (Report)reports.get(0);
		}else{
			report = new Report();
			reportInfoDTOtoReport((ReportInfoDTO)obj,report);
		}
		
		financialGuideDTOtoReport((FinancialGuideDTO)obj, report);
		
		//System.out.println(report.getBalanceSheet());
		
		try {
			//System.out.println(report.getFinancialGuide());
			reportBusiness.save(report);
			
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	private void financialGuideDTOtoReport(FinancialGuideDTO dto, Report report){
		report.setPrimeOperatingProfit(dto.getPrimeOperatingProfit());
		report.setNetProfitPlus(dto.getNetProfitPlus());
	}
	
	private void saveBalanceSheets(List list){
		for(Object obj : list){
			saveBalanceSheet(obj);
		}
	}
	
	private void saveBalanceSheet(Object obj){
		List reports = findBusiness.findByQuery(getQuery((ReportInfoDTO)obj)).getList();
		Report report;
		if(reports!=null && reports.size()>0){
			report = (Report)reports.get(0);
		}else{
			report = new Report();
			reportInfoDTOtoReport((ReportInfoDTO)obj,report);
		}
		
		balanceSheetDTOtoReport((BalanceSheetDTO)obj, report);
		
		//System.out.println(report.getBalanceSheet());
		
		try {
			reportBusiness.save(report);
			
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	
	private void saveCashFlows(List list){
		for(Object obj : list){
			saveCashFlow(obj);
		}
	}
	
	private void saveCashFlow(Object obj){
		List reports = findBusiness.findByQuery(getQuery((ReportInfoDTO)obj)).getList();
		Report report;
		if(reports!=null && reports.size()>0){
			report = (Report)reports.get(0);
		}else{
			report = new Report();
			reportInfoDTOtoReport((ReportInfoDTO)obj,report);
		}
		
		cashFlowDTOtoReport((CashFlowDTO)obj, report);
		
		try {
			reportBusiness.save(report);
			
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	private void saveFinanceSummarys(List list){
		for(Object obj : list){
			saveFinanceSummary(obj);
		}
	}
	
	private void saveFinanceSummary(Object obj){
		List reports = findBusiness.findByQuery(getQuery((ReportInfoDTO)obj)).getList();

		Report report;
		if(reports!=null && reports.size()>0){
			report = (Report)reports.get(0);
		}else{
			report = new Report();
			reportInfoDTOtoReport((ReportInfoDTO)obj,report);
		}
		
		financeSummaryDTOtoReport((FinanceSummaryDTO)obj, report);
		
		try {
			reportBusiness.save(report);
			
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	
	private void saveProfitStatements(List list){
		for(Object obj : list){
			saveProfitStatement(obj);
		}
	}
	
	private void saveProfitStatement(Object obj){
		List reports = findBusiness.findByQuery(getQuery((ReportInfoDTO)obj)).getList();
		
		Report report;
		if(reports!=null && reports.size()>0){
			report = (Report)reports.get(0);
		}else{
			report = new Report();
			reportInfoDTOtoReport((ReportInfoDTO)obj,report);
		}
		
		profitStatementDTOtoReport((ProfitStatementDTO)obj, report);
		
		try {
			reportBusiness.save(report);
			
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	
	private ReportQuery getQuery(Object obj){
		ReportInfoDTO dto;
		try {
			dto = (ReportInfoDTO)obj;
			ReportQuery query = new ReportQuery();
			query.empty();			
			query.setStockNo(dto.getStockNo());
			query.setPeriodType(dto.getPeriodType());
			query.setTheYear(Tools.getDate(dto.getEndDate(), "yyyy"));
			query.setTheMonth(Tools.getDate(dto.getEndDate(), "MM"));
			return query;
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	private void reportInfoDTOtoReport(ReportInfoDTO dto, Report report){
		report.setStockNo(dto.getStockNo());
		report.setStockName(dto.getStockName());
		report.setBeginDate(dto.getBeginDate());
		report.setDescription(dto.getDescription());
		report.setEndDate(dto.getEndDate());
		report.setPeriodType(dto.getPeriodType());
		//System.out.println(dto);
	}
	
	private void balanceSheetDTOtoReport(BalanceSheetDTO dto, Report report){
		report.setAccountsRreceivable(dto.getAccountsRreceivable());
		report.setCash(dto.getCash());
		report.setCurrentLiabilities(dto.getCurrentLiabilities());
		report.setInventories(dto.getInventories());
		report.setNotesReceivable(dto.getNotesReceivable());
		report.setOtherReceivable(dto.getOtherReceivable());
		report.setPayables(dto.getPayables());
		report.setTotalAssets(dto.getTotalAssets());
		report.setTotalCurrentAssets(dto.getTotalCurrentAssets());
		report.setTotalLiabilities(dto.getTotalLiabilities());
	}
	
	private void cashFlowDTOtoReport(CashFlowDTO dto, Report report){
		report.setDepreciationAssets(dto.getDepreciationAssets());
		report.setPurchaseAssets(dto.getPurchaseAssets());
		report.setNetCashFlow(dto.getNetCashFlow());
	}
	
	private void financeSummaryDTOtoReport(FinanceSummaryDTO dto, Report report){
		report.setCapitalFundPerShare(dto.getCapitalFundPerShare());
		report.setEarningsPerShare(dto.getEarningsPerShare());
		report.setNetAssetsPerShare(dto.getNetAssetsPerShare());
		report.setNetCashFlowPerShare(dto.getNetCashFlowPerShare());
	}
	
	private void profitStatementDTOtoReport(ProfitStatementDTO dto, Report report){
		report.setNetProfit(dto.getNetProfit());
		report.setOperatingProfit(dto.getOperatingProfit());
		report.setPrimeOperatingRevenue(dto.getPrimeOperatingRevenue());
		report.setFinanceExpense(dto.getFinanceExpense());
		report.setSalesExpense(dto.getSalesExpense());
		report.setOperatingExpense(dto.getOperatingExpense());
	}

	public DownloadReport getDownloadBalanceSheet() {
		return downloadBalanceSheet;
	}

	public void setDownloadBalanceSheet(DownloadReport downloadBalanceSheet) {
		this.downloadBalanceSheet = downloadBalanceSheet;
	}

	public DownloadReport getDownloadCashFlow() {
		return downloadCashFlow;
	}

	public void setDownloadCashFlow(DownloadReport downloadCashFlow) {
		this.downloadCashFlow = downloadCashFlow;
	}

	public DownloadReport getDownloadFinanceSummary() {
		return downloadFinanceSummary;
	}

	public void setDownloadFinanceSummary(DownloadReport downloadFinanceSummary) {
		this.downloadFinanceSummary = downloadFinanceSummary;
	}

	public DownloadReport getDownloadProfitStatement() {
		return downloadProfitStatement;
	}

	public void setDownloadProfitStatement(DownloadReport downloadProfitStatement) {
		this.downloadProfitStatement = downloadProfitStatement;
	}

	@Override
	public List<String> getReportedStockNo(Date reportDate, Date issueDate_begin,
			Date issueDate_end) {
		return downloadReportedStockNos.getReportedStockNo(reportDate, issueDate_begin, issueDate_end);
	}

	public DownloadReportedStockNos getDownloadReportedStockNos() {
		return downloadReportedStockNos;
	}

	public void setDownloadReportedStockNos(
			DownloadReportedStockNos downloadReportedStockNos) {
		this.downloadReportedStockNos = downloadReportedStockNos;
	}

	@Override
	public void refreshStockValueAndDiscount() {
		stockBusiness.updateValueAndDiscount();
		
	}

	@Override
	public void refreshStockValueAndDiscount(List<String> stockNos) {
		stockBusiness.updateValueAndDiscount((String[])stockNos.toArray(new String[stockNos.size()]));
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	@Override
	public List<String> getNotReportedStockNo(Date reportDate) {
		return reportBusiness.getNotReportedStockNo(reportDate);
	}

	public DownloadReport getDownloadFinancialGuide() {
		return downloadFinancialGuide;
	}

	public void setDownloadFinancialGuide(DownloadReport downloadFinancialGuide) {
		this.downloadFinancialGuide = downloadFinancialGuide;
	}

	public DownloadYjyg getDownloadYjyg() {
		return downloadYjyg;
	}

	public void setDownloadYjyg(DownloadYjyg downloadYjyg) {
		this.downloadYjyg = downloadYjyg;
	}

	@Override
	public List<String> getStockNos() {
		List stockNos = new ArrayList();
		StockQuery sq = new StockQuery();
		sq.empty();
		sq.setCount(10000);
		Page page = findBusiness.findByQuery(sq);
		List<Stock> stocks = page.getList();
		for(Stock stock : stocks){
			stockNos.add(stock.getStockNo());
		}
		return stockNos;
	}


}
