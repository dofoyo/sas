package com.rhb.sas.interfaces.downloadreport;

import java.util.Date;
import java.util.List;

public interface DownloadReportedStockNos {
	public List<String> getReportedStockNo(Date reportDate, Date issueDate_begin,
			Date issueDate_end);
}
