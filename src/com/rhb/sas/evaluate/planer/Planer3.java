package com.rhb.sas.evaluate.planer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
 * 	所有股票平均分配金额。
 * 	买入时机：
	1、年报公布， 符合SAS选股条件
	2、120日均线向上(5天前的120均价与现在的120均价相比，有上升)
	3、股价在120均线上方（现价大于120均价）
	卖出时机：
	年报公布后，卖出
								

 * @author rhb
 *
 */
public class Planer3 implements Planer {
	
	FindBusiness find = null;
	Map<Integer,List<Firm>> map = null;
	String path = null;
	
	public Planer3(Map<Integer,List<Firm>> map, FindBusiness find, String path){
		this.path = path; // "D:\git\sas\sas\src\com\rhb\sas\evaluate\trader\"
		this.map = map;
		this.find = find;
	}
	
	@Override
	public List<Order> plan() {
		List<Order> orders = new ArrayList();
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			int year = (Integer)it.next();	
			int year_1 = year + 1;
			List<Firm> fs = map.get(year);
			for(Firm firm : fs){
				String stockNo = firm.getStockNo();
				String stockName = firm.getStockName();
				System.out.print(stockNo + stockName);
				Date reportDate = getReportDate(firm,Integer.toString(year),"12");
				Date reportDate_1 = getReportDate(firm,Integer.toString(year_1),"12");
				reportDate_1 = reportDate_1==null ? new Date() : reportDate_1; 
				Date uptrendDate = getUptrendDate(stockNo, reportDate,reportDate_1);
				if(uptrendDate!=null){
					System.out.println(" reportDate="+Tools.getDate(reportDate, "yyyy-MM-dd")+",uptrendDate=" + Tools.getDate(uptrendDate, "yyyy-MM-dd"));

					Order order_buy = new Order();
					order_buy.init(stockNo, stockName, uptrendDate, 1, 1, 0); 
					orders.add(order_buy);
					
					System.out.println(" sellDate=" + Tools.getDate(reportDate_1, "yyyy-MM-dd"));
					
					Order order_sell = new Order();
					order_sell.init(stockNo, stockName, reportDate_1, -1, 0, 0);
					orders.add(order_sell);								
				}else{
					System.out.print(" reportDate="+Tools.getDate(reportDate, "yyyy-MM-dd")+",uptrendDate=" + Tools.getDate(uptrendDate, "yyyy-MM-dd"));
					System.out.println(". reportDate is null or 120 is NOT uptrend, do NOT buy!");
				}
			}  
		}
		Collections.sort(orders,new ComparatorOrder());
		return orders;
	}

	private Date getUptrendDate(String stockNo,Date reportDate, Date reportDate_1){
		if(reportDate==null) return null;
		
		Date date = reportDate;
		while(date.compareTo(reportDate_1)==-1){
			Double[] price = getMarketPrice(stockNo,date);
			if(price == null){
				break;
			}
			if(price[0] > price[1] && price[1]>price[2]){
				break;
			}
			date = Tools.getDate(date,1);
		}
		
		if(date.compareTo(reportDate_1)>=0){
			date = null;
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
		} catch (Exception e) {
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
