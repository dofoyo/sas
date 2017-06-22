package com.rhb.sas.report.profitstatement;

import java.util.Date;
import java.util.List;

public interface DownloadProfitStatement {
	public List<ProfitStatement> down(String stockNo, Date reportDate); 

}
