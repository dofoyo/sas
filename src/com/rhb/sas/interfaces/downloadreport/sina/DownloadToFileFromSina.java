package com.rhb.sas.interfaces.downloadreport.sina;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.util.FileUtil;
import com.rhb.sas.util.HttpBrowser;

public class DownloadToFileFromSina {

	public void doIt(List<Stock> stocks, String path){
		// 资产负债表 BalanceSheet
		//http://money.finance.sina.com.cn/corp/go.php/vDOWN_BalanceSheet/displaytype/4/stockid/300022/ctrl/all.phtml
		String url0 = "http://money.finance.sina.com.cn/corp/go.php/vFD_BalanceSheet/stockid/stockNo/ctrl/part.phtml";
		
		 // 现金流量表 CashFlow
		//http://money.finance.sina.com.cn/corp/go.php/vDOWN_CashFlow/displaytype/4/stockid/300022/ctrl/all.phtml
		String url1 = "http://money.finance.sina.com.cn/corp/go.php/vFD_CashFlow/stockid/stockNo/ctrl/part.phtml";
		
		 // 利润表  ProfitStatement
		//http://money.finance.sina.com.cn/corp/go.php/vDOWN_ProfitStatement/stockid/300022/ctrl/part.phtml
		String url2 = "http://money.finance.sina.com.cn/corp/go.php/vFD_ProfitStatement/stockid/stockNo/ctrl/part.phtml";

		// 财务摘要 FinanceSummary
		String url3 = "http://money.finance.sina.com.cn/corp/go.php/vFD_FinanceSummary/stockid/stockNo.phtml"; 
		
		//流通股股东
		String url4 = "http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CirculateStockHolder/stockid/stockNo.phtml";	
		
		//主要股东	
		String url5 = "http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_StockHolder/stockid/stockNo.phtml";	
		
		// 主要财务指标
		String url6 = "http://money.finance.sina.com.cn/corp/go.php/vFD_FinancialGuideLine/stockid/stockNo/displaytype/100.phtml"; 
	
		HttpBrowser hb = new HttpBrowser();
		
		for(int i=0; i<stocks.size();){
			Stock stock = stocks.get(i);
			System.out.print(Integer.toString(i+1) + "/" + Integer.toString(stocks.size()) + " " + stock.getStockNo() + ", " + stock.getStockName() + ".........");
			if(FileUtil.isExists(path + stock.getStockNo() + "_FinancialGuide.html")){
				System.out.println(" has done.");
				i++;
			}else{
				long t1 = System.currentTimeMillis();

				List<String> urls = new ArrayList<String>();
//				urls.add(url0.replace("stockNo", stock.getStockNo()));
//				urls.add(url1.replace("stockNo", stock.getStockNo()));
//				urls.add(url2.replace("stockNo", stock.getStockNo()));
//				urls.add(url3.replace("stockNo", stock.getStockNo()));
//				urls.add(url4.replace("stockNo", stock.getStockNo()));
//				urls.add(url5.replace("stockNo", stock.getStockNo()));
				urls.add(url6.replace("stockNo", stock.getStockNo()));
				List<String> result;
				try {
					result = hb.Browser(urls, 0);
//					if(result.get(0)!=null && result.get(1)!=null && result.get(2)!=null && result.get(3)!=null &&  result.get(4)!=null &&  result.get(5)!=null &&  result.get(6)!=null){
					if(result.get(0)!=null){
//						FileUtil.writeTextFile(path + stock.getStockNo() + "_BalanceSheet.html", result.get(0), true);
//						FileUtil.writeTextFile(path + stock.getStockNo() + "_CashFlow.html", result.get(1), true);
//						FileUtil.writeTextFile(path + stock.getStockNo() + "_ProfitStatement.html", result.get(2), true);
//						FileUtil.writeTextFile(path + stock.getStockNo() + "_FinanceSummary.html", result.get(3), true);
//						FileUtil.writeTextFile(path + stock.getStockNo() + "_CirculateStockHolder.html", result.get(4), true);
//						FileUtil.writeTextFile(path + stock.getStockNo() + "_StockHolder.html", result.get(5), true);
						FileUtil.writeTextFile(path + stock.getStockNo() + "_FinancialGuide.html", result.get(0), true);
					}
					long t2 = System.currentTimeMillis();
					System.out.println("  done. " + getTimeString(t1,t2));
					i++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					long t2 = System.currentTimeMillis();
					System.out.println("  error. " + getTimeString(t1,t2));
					System.out.println("................... 继续............");
				}
			}
		}
	}
	
	private String getTimeString(long t1, long t2){
		  Calendar c = Calendar.getInstance();  
	        c.setTimeInMillis(t2 - t1);  
	  
	        return ("耗时: " +
	        		  c.get(Calendar.MINUTE) + "分 "  
	                + c.get(Calendar.SECOND) + "秒 " 
	        		+ c.get(Calendar.MILLISECOND) + " 毫秒");  

	}

}
