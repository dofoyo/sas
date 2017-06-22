package com.rhb.sas.interfaces;

import java.util.Date;
import java.util.List;

import com.rhb.sas.interfaces.downloadmarketinfo.MarketInfoDTO;
import com.rhb.sas.stock.bean.Stock;

public interface InterfacesFacade {
	
	public List<MarketInfoDTO> downloadMarketInfo();
	public List<String> saveMarketInfo(List<MarketInfoDTO> list);
	
	public List downloadReport(String stockNo, Date reportDate, boolean overwrite);
	public List downloadReport(List<String> stockNos, Date reportDate, boolean overwrite);
	public List<String> getReportedStockNo(Date reportDate,Date issueDate_begin, Date issueDate_end);
	public List<String> getNotReportedStockNo(Date reportDate);
	public List<String> getStockNos();
	
	public void reCaculateEarningsGrowthRatio(List<String> stockNos);
	public void refreshStockFromReport(List<String> stockNos);
	
	public void refreshStockValueAndDiscount();
	public void refreshStockValueAndDiscount(List<String> stockNos);
	
	public void downloadYjyg(String theyearandmonth, int thepage);
	
	
}
