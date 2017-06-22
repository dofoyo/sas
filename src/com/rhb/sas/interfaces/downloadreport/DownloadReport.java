package com.rhb.sas.interfaces.downloadreport;

import java.util.Date;
import java.util.List;

public interface DownloadReport {
	public List doIt(String stockNo, Date reportDate);
}
