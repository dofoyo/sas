package com.rhb.sas.interfaces.downloadstockholder;

import java.util.List;

public interface DownloadStockholder {
	public List<StockholderDTO> doIt(String stockNo);
}
