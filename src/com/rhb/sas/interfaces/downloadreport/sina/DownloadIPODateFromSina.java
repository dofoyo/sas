package com.rhb.sas.interfaces.downloadreport.sina;

import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadIPODateFromSina {
	
	public String doIt(String stockNo){
		String ipoDate = null;
		String url = "http://finance.sina.com.cn/realstock/company/stockNo/nc.shtml"; 
		url = url.replace("stockNo", stockNo);
		HttpBrowser hb = new HttpBrowser();
		try {
			ipoDate = parse(hb.Browser(url));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ipoDate;
	}
	
	private String parse(String html){
		String findRegexp = "<p><b>上市日期：</b>|</p>";
		String str = Tools.subString(html, findRegexp); 
		
		return str;
		
	}
}
