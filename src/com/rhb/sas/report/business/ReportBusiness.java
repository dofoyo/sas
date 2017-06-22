package com.rhb.sas.report.business;

import java.util.Date;
import java.util.List;

import com.rhb.af.exception.*;
import com.rhb.sas.report.bean.*;


public interface ReportBusiness {
	public String save(Report obj) throws DuplicateException,RequiredException,OutOfRangeException;

	public void caculateEarningsGrowthRatio(String stockNo);
	public void caculateEarningsGrowthRatio(String[] stockNos);
	
	public List getAnnualReports(String stockNo);
	
	public List<String> getNotReportedStockNo(Date reportdate);
	
	public void rebuildReport();
	
}
