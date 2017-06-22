package com.rhb.sas.download.mgsy;

import java.util.List;

public interface DownloadMgsy {
	public List<MgsyDTO> doIt(String theyearandmonth, int thepage);
}
