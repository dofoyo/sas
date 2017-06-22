package com.rhb.sas.interfaces.downloadmarketinfo;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadMarketInfoFromStockstar implements DownloadMarketInfo {

	private int from;
	private int to;
	
	@Override
	public List<MarketInfoDTO> doIt() {
		List<MarketInfoDTO> dtos = new ArrayList();
		MarketInfoDTO dto;
		
		String baseUrl = "http://quote.stockstar.com/stock/external_rank_list.aspx?pagenum=ppp&part=3&reporttype=60&col=20&tag=desc";
		String findRegexp0 = "<tr class=\"td3\".*?</tr>";
		String findRegexp1 = "<tr class=\"td4\".*?</tr>";
		String replaceStockRegexp = "<td align=\"center\"><a href=\"/stock/.*?.htm\">|</a></td>";
		String replacePriceAndValueRegexp = "<td align=\"right\">|</td>";
		String replacePriceRegexp = "<span class=\"font_color_.*?\">|</span>";

		HttpBrowser hb = new HttpBrowser();

		try{
		for(int i=from; i<=to; i++){
			System.out.println("***** page " + i);
			String url = baseUrl.replace("ppp", Integer.toString(i));
			String str = hb.Browser(url);
			List<String> list = Tools.subStrings(str, findRegexp0);
			list.addAll(Tools.subStrings(str, findRegexp1));
			for(int j=0; j<list.size(); j++){
				List<String> stockInfos = Tools.subStrings(list.get(j), replaceStockRegexp);
		
				List<String> priceAndValues = Tools.subStrings(list.get(j), replacePriceAndValueRegexp);
				String price = priceAndValues.get(0);
				if(priceAndValues.get(0).indexOf("<span") != -1){
					price = (String)(Tools.subStrings(priceAndValues.get(0),replacePriceRegexp)).get(0);
				}
					
				double pricePerShare = Tools.getDouble(price);
				double marketValue = Tools.getDouble(priceAndValues.get(7))/10000;
				double currentMarketValue = Tools.getDouble(priceAndValues.get(8))/10000;
				
				dto = new MarketInfoDTO(
						stockInfos.get(0),
						stockInfos.get(1),
						pricePerShare,
						marketValue,
						currentMarketValue
						);
				dtos.add(dto);
				System.out.println(dto);
			}
			/*
			try { Thread.sleep ( 5000 ) ; 
	        } catch (InterruptedException ie){}
	        */
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return dtos;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}
}
