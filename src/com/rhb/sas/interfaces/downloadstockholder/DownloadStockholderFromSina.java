package com.rhb.sas.interfaces.downloadstockholder;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;
import com.rhb.af.util.DateTools;

public class DownloadStockholderFromSina implements DownloadStockholder {

	@Override
	public List<StockholderDTO> doIt(String stockNo) {
		return downloadStockholder(stockNo);
	}
	
	private List<StockholderDTO> downloadStockholder(String stockNo){
		List<StockholderDTO> list = null;
		String url = "http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_StockHolder/stockid/stockNo.phtml";	//主要股东	
		//String url = "http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CirculateStockHolder/stockid/stockNo.phtml";	//流通股股东
		url = url.replace("stockNo", stockNo);
		HttpBrowser hb = new HttpBrowser();
		try {
			String result = hb.Browser(url);
			list = parseStockHolder(result,stockNo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private List<StockholderDTO> parseStockHolder(String str,String stockNo){
		List<StockholderDTO> list = new ArrayList();
		
		String findRegexp = "</a><div align=\"center\"><strong>截至日期</strong></div>.*?分割数据的空行";
		String findTR = "<tr.*?</tr>";
		String findEndDate = "<td colspan=\"4\">|</td>";
		String findReportedDate = "<td colspan=\"4\">|</td>";
		String findStockholder1 = "target=\"_blank\">|</a>";
		String findStockholder2 = "<div align=\"center\">|</div>";
		String findHolderNo = "stockholderid=|\" target=\"_blank\"";
		
		String endDate;
		String reportedDate;
		
		List<String> period = Tools.subStrings(str, findRegexp);
		List<String> trs;
		List<String> stockholders1;
		List<String> stockholders2;
		String holderNo;
		long holdingNumber = 0;
		double holdingRatio = 0.0;
		StockholderDTO sh;
		if(period!=null && period.size()>0){
			for(int i=0; i<period.size(); i++){
				trs = Tools.subStrings("<tr><td>" + period.get(i), findTR);
				if(trs!=null && trs.size()>0){
					endDate = Tools.subString(trs.get(0), findEndDate);
					reportedDate = Tools.subString(trs.get(1), findReportedDate);
					for(int j=6; j<trs.size(); j++){
						stockholders1 = Tools.subStrings(trs.get(j), findStockholder1);
						stockholders2 = Tools.subStrings(trs.get(j), findStockholder2);
						holderNo = Tools.subString(trs.get(j), findHolderNo);

						try{
							holdingNumber = Long.parseLong(stockholders1.get(1));
							holdingRatio = Double.parseDouble(stockholders1.get(2));
						}catch(Exception e){}
						
						if(holdingNumber!=0 && holdingRatio!=0.0){
							sh = new StockholderDTO();
							sh.setEndDate(DateTools.getDate(endDate));
							sh.setReportedDate(DateTools.getDate(reportedDate));
							sh.setStockNo(stockNo);
							//sh.setStockName(stock.getStockName());
							sh.setHolderNo(holderNo);
							sh.setHolderName(stockholders1.get(0));
							sh.setHoldingRatio(holdingRatio);
							sh.setHoldingNumber(holdingNumber);
							sh.setHoldingType(stockholders2.get(4));
							list.add(sh);
						}
					}
				}
			}
		}else{
			System.out.println("************* nothing finded! **********");
		}
		
		return list;
	}
	

	

}
