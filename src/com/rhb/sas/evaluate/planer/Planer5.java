package com.rhb.sas.evaluate.planer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.rhb.af.bean.Page;
import com.rhb.af.business.FindBusiness;
import com.rhb.sas.firm.Firm;
import com.rhb.sas.report.bean.Report;
import com.rhb.sas.report.bean.ReportQuery;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.util.Tools;

/**
 * 	
 * 	买入时机：
	1、年报公布， 符合SAS选股条件
	2、一季度预报不下降
	3、120日均线向上(5天前的120均价与现在的120均价相比，有上升)，股价在120均线上方（现价大于120均价）
	买入时所有股票平均分配金额。

	卖出时机：
	1、业绩预告下降
	2、年报公布后
	卖出时，卖出的钱补仓，只补上升趋势的股票。							

 * @author rhb
 *
 */
public class Planer5 implements Planer {
	List<Order> orders = new ArrayList();
	FindBusiness find = null;
	Map<Integer,List<Firm>> map = null;
	String path = null;
	String yjygFile = "yjyg.xls";
	
	
	public Planer5(Map<Integer,List<Firm>> map, FindBusiness find, String path){
		this.path = path; // "D:\git\sas\sas\src\com\rhb\sas\evaluate\trader\"
		
		this.map = map;
		this.find = find;
	}
	
	@Override
	public List<Order> plan() {
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			int year = (Integer)it.next();	
			List<Firm> fs = map.get(year);
			for(Firm firm : fs){
				System.out.print(firm.getStockNo() + firm.getStockName());
				orders.addAll(generateOrders(firm,year));
			}  
		}
		Collections.sort(orders,new ComparatorOrder());
		return orders;
	}

	private List<Order> generateOrders(Firm firm, int year){
		int year_1 = year + 1;
		List<Order> orders = new ArrayList();
		Date reportDate = getReportDate(firm,Integer.toString(year),"12");
		Date reportDate_1 = getReportDate(firm,Integer.toString(year_1),"12");
		reportDate_1 = reportDate_1==null ? new Date() : reportDate_1; 
		Date uptrendDate = getUptrendDate(firm.getStockNo(), reportDate,reportDate_1);
		
		String[] yjygDownDate = getYjydDownDate(firm.getStockNo(), Integer.toString(year_1));
		Date yjygDownDate_1 = (yjygDownDate!=null && yjygDownDate[0]!=null) ? Tools.getDate(yjygDownDate[0]) : null;
		Date yjygDownDate_2 = (yjygDownDate!=null && yjygDownDate[1]!=null) ? Tools.getDate(yjygDownDate[1]) : null;				
		Date yjygDownDate_3 = (yjygDownDate!=null && yjygDownDate[2]!=null) ? Tools.getDate(yjygDownDate[2]) : null;
		Date yjygDownDate_4 = (yjygDownDate!=null && yjygDownDate[3]!=null) ? Tools.getDate(yjygDownDate[3]) : null;
		
		Date yjygDwonDate = yjygDownDate_4!=null ? yjygDownDate_4 : null;
		yjygDwonDate = yjygDownDate_3!=null ? yjygDownDate_3 : yjygDwonDate;
		yjygDwonDate = yjygDownDate_2!=null ? yjygDownDate_2 : yjygDwonDate;
		yjygDwonDate = yjygDownDate_1!=null ? yjygDownDate_1 : yjygDwonDate;
		
		
		if((uptrendDate!=null && yjygDwonDate==null) || (uptrendDate!=null && yjygDwonDate!=null && yjygDwonDate.compareTo(uptrendDate)>0)){
			if(yjygDownDate_1!=null){
				System.out.println(" reportDate="+Tools.getDate(reportDate, "yyyy-MM-dd")+",yjygDwonDate=" + yjygDownDate[0] + ", do NOT buy");
			}else {
				System.out.println(" reportDate="+Tools.getDate(reportDate, "yyyy-MM-dd")+",uptrendDate=" + Tools.getDate(uptrendDate, "yyyy-MM-dd"));

				Order order_buy = new Order();
				order_buy.init(firm.getStockNo(), firm.getStockName(), uptrendDate, 1, 1, 0); 
				orders.add(order_buy);
				
				boolean isCover = false;
				if(yjygDownDate!=null && yjygDownDate[1]!=null){
					reportDate_1 = Tools.getDate(yjygDownDate[1]);
					isCover = true;
					System.out.println(" yjyg down, 提前卖出， sellDate1=" + Tools.getDate(reportDate_1, "yyyy-MM-dd"));
				}else if(yjygDownDate!=null && yjygDownDate[2]!=null){
					reportDate_1 = Tools.getDate(yjygDownDate[2]);
					isCover = true;
					System.out.println(" yjyg down, 提前卖出， sellDate2=" + Tools.getDate(reportDate_1, "yyyy-MM-dd"));
				}else if(yjygDownDate!=null && yjygDownDate[3]!=null){
					isCover = true;
					reportDate_1 = Tools.getDate(yjygDownDate[3]);
					System.out.println(" yjyg down, 提前卖出， sellDate3=" + Tools.getDate(reportDate_1, "yyyy-MM-dd"));
				}
				System.out.println(" sellDate=" + Tools.getDate(reportDate_1, "yyyy-MM-dd"));
				Order order_sell = new Order();
				order_sell.init(firm.getStockNo(), firm.getStockName(), reportDate_1, -1, 0, 0);
				orders.add(order_sell);
				
				if(isCover){
					System.out.println("need to cover positon");
					orders.addAll(generateCoverOrder(order_sell));
				}
				
			}
		}else{
			System.out.print(" reportDate="+Tools.getDate(reportDate, "yyyy-MM-dd")+",uptrendDate=" + Tools.getDate(uptrendDate, "yyyy-MM-dd"));
			System.out.print(" yjygDownDate="+Tools.getDate(yjygDwonDate, "yyyy-MM-dd"));
			System.out.println(". reportDate is null, or 120 is NOT uptrend, yjygDownDate is before, do NOT buy!");
		}
		
		return orders;
		
	}
	
	private List<Order> generateCoverOrder(Order order_sell){
		List<Order> covers = new ArrayList();
		Map<String,String> stocks = new HashMap();
		Collections.sort(orders,new ComparatorOrder());
		for(Order order : orders){
			if(order_sell.getDate().compareTo(order.getDate())==1){
				if(order.buyOrSell() == Order.BUY){
					stocks.put(order.getStockNo(), order.getStockName());
				}else{
					stocks.remove(order.getStockNo());
				}
			}
		}
		
		Map<String,String> coversMap = new HashMap();
		Iterator<String> it = stocks.keySet().iterator();
		while(it.hasNext()){
			String stockNo = it.next();
			String stockName = stocks.get(stockNo);
			if(isUptrend(stockNo,order_sell.getDate())){
				System.out.println("**** will cover " + stockNo + stockName);
				coversMap.put(stockNo, stockName);
			}else{
				System.out.println("**** will NOT cover " + stockNo + stockName + " for Downtrend");				
			}
		}
		
		Iterator<String> ite = coversMap.keySet().iterator();
		int occupation = coversMap.size();
		while(ite.hasNext()){
			String stockNo = ite.next();
			String stockName = coversMap.get(stockNo);
			Order order = new Order();
			order.init(stockNo, stockName, order_sell.getDate(), Order.BUY, occupation--, 1);
			covers.add(order);
		}
		
		return covers;
	}
	
	private boolean isUptrend(String stockNo, Date date){
		boolean flag = false;
		Double[] prices = getMarketPrice(stockNo, date);
		if(prices!=null && prices[0] > prices[1] && prices[1]>prices[2]){
			flag = true;
		}
		return flag;
	}
	
	private Date getUptrendDate(String stockNo,Date reportDate, Date reportDate_1){
		if(reportDate==null) return null;
		
		Date date = reportDate;
		while(date.compareTo(reportDate_1)==-1){
			Double[] price = getMarketPrice(stockNo,date);
			
			if(price == null) break;

			if(price[0] > price[1] && price[1]>price[2]){
				break;
			}
			date = Tools.getDate(date,1); //非常重要的参数
		}
		
		if(date.compareTo(reportDate_1)>=0){
			date = null;
		}
		
		return date;
	}

	public String[] getYjydDownDate(String stockNo, String year){
		File file = new File(path + stockNo + ".xls");
		if(!file.exists()) return null;

		String[] date = null;
		POIFSFileSystem fs;
		try {
			fs = new POIFSFileSystem(new FileInputStream(path+yjygFile));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			date = getDate(wb.getSheetAt(0),stockNo,year);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	private String[] getDate(HSSFSheet sheet,String stockNo,String year){
		//System.out.println("the date=" + Tools.getDate(date, "yyyy-MM-dd"));
		String[] date = {null,null,null,null};
		int lastRowNum = sheet.getLastRowNum();
		HSSFRow row;
		String[] month = {"03","06","09","12"};
		for(int i=1; i<lastRowNum; i++){
			row = sheet.getRow(i);
			String sn = row.getCell((short)0).getStringCellValue();
			String reportDate = row.getCell((short)2).getStringCellValue();
			String issueDate = row.getCell((short)3).getStringCellValue();
			if(stockNo.equals(sn)){
				if((year+"03").equals(reportDate)){
					date[0] = issueDate;
				}else if((year+"06").equals(reportDate)){
					date[1] = issueDate;
				}else if((year+"09").equals(reportDate)){
					date[2] = issueDate;
				}else if((year+"12").equals(reportDate)){
					date[3] = issueDate;
				}
			}
		}
		return date;
	}

	
	
	public Double[] getMarketPrice(String stockNo, Date date){
		File file = new File(path + stockNo + ".xls");
		if(!file.exists()) return null;

		Double[] price = null;
		POIFSFileSystem fs;
		try {
			fs = new POIFSFileSystem(new FileInputStream(path + stockNo + ".xls"));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			price = getPrice(wb.getSheetAt(0),date);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	}
	
	private Double[] getPrice(HSSFSheet sheet,Date date){
		//System.out.println("the date=" + Tools.getDate(date, "yyyy-MM-dd"));
		Double[] price = {0.0,0.0,0.0};
		int beginRowNum = 2;
		int lastRowNum = sheet.getLastRowNum();
		int midRowNum = (lastRowNum - beginRowNum)/2;
		HSSFRow row;
		Date theDate;
		int diff = 0;
		int i=0;
		while(true){
			//System.out.println("beginRowNum = " + beginRowNum);
			//System.out.println("midRowNum = " + midRowNum);
			//System.out.println("lastRowNum = " + lastRowNum);
			//if(i++ > 5) break;
			row = sheet.getRow(midRowNum);
			theDate = getDate(row.getCell((short)0));
			//System.out.println("theDate=" + Tools.getDate(theDate, "yyyy-MM-dd"));
			diff = date.compareTo(theDate);
			//System.out.println("diff = " + diff);
			if(lastRowNum-beginRowNum==1){
				midRowNum = lastRowNum;
				break;
			}
			if(diff == 1){
				beginRowNum = midRowNum;
				midRowNum =  midRowNum + (lastRowNum - beginRowNum)/2;
			}else if(diff == -1){
				lastRowNum = midRowNum;
				midRowNum =  midRowNum - (lastRowNum - beginRowNum)/2;
			}else{
				break;
			}
		}
		//System.out.println("midRowNum = " + midRowNum);
		row = sheet.getRow(midRowNum);
		theDate = getDate(row.getCell((short)0));
		//System.out.println("theDate=" + Tools.getDate(theDate, "yyyy-MM-dd"));
		price[0] = getPrice((row.getCell((short)4)));
		price[1] = getPrice((row.getCell((short)13)));
		
		if(midRowNum-5>0){
			row = sheet.getRow(midRowNum-5);
			price[2] = getPrice((row.getCell((short)13)));
		}
		
		return price;
	}
	
	private Object getCellValue(HSSFCell cell){
		Object obj = null;
		if(cell != null){
			switch(cell.getCellType()){
			case HSSFCell.CELL_TYPE_BLANK:
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				obj = cell.getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				obj = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_STRING:
				obj = cell.getRichStringCellValue().getString();
				break;
			}
		}
		return obj;
	}

	private Double getPrice(HSSFCell cell){
		if(cell != null && cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
			return cell.getNumericCellValue();
		}else{
			return 0.0;
		}
	}

	
	private Double getPrice(Object obj){
		if(obj==null){
			return 0.0;
		}
		return(Double)obj;
	}
	
	private Date getDate(HSSFCell cell){
		if(cell==null){
			return null;
		}
		//System.out.println(cell.getRichStringCellValue().getString());
		
		return cell.getDateCellValue();
	}

	
	private Date getReportDate(Firm firm, String year, String month){
		Date date = null;
		List<Report> reports = firm.getReports();
		for(Report report : reports){
			if(year.equals(report.getTheYear()) && month.equals(report.getTheMonth())){
				date = Tools.getDate(report.getDescription());
				break;
			}
		}
		return date;
	}
	
	class ComparatorOrder implements Comparator{
		public int compare(Object obj1, Object obj2){
			Order order1 = (Order)obj1;
			Order order2 = (Order)obj2;
			if(order1.getDate()==null){
				return -1;
			}else if(order2.getDate()==null){
				return 1;
			}else{
				return order1.getDate().compareTo(order2.getDate());
			}
		}
	}

}
