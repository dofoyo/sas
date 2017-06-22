package com.rhb.sas.evaluate.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.rhb.sas.evaluate.planer.Order;

public class generateOrderByExcel {
	public static List<Order> doIt(String file){
		List<Order> orders = new ArrayList();
		try {
			
			//POIFSFileSystem fs = new POIFSFileSystem(this.getClass().getResourceAsStream(file));
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			HSSFRow row;
			for(int i=1; i<=lastRowNum; i++){
				//String stockNo = 
				//Price p = new Price();
				row = sheet.getRow(i);	
				if(row != null){
					String stockNo = getStock(getCellValue(row.getCell((short)0)));
					String stockName = getStock(getCellValue(row.getCell((short)1)));
					Date date = getDate((row.getCell((short)2)));
					int buyorsell = getBuyOrSell(getCellValue(row.getCell((short)3))); 
					int occupation = getBuyOrSell(getCellValue(row.getCell((short)4))); 
					int onlyCash = getBuyOrSell(getCellValue(row.getCell((short)5))); 
					
					if(stockNo!=null && !stockNo.isEmpty() && date!=null){
						Order order = new Order();
						order.init(stockNo,stockName, date, buyorsell,occupation,onlyCash);
						orders.add(order);					
					}				
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return orders;

	}
	
	
	private static Object getCellValue(HSSFCell cell){
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

	private static Date getDate(HSSFCell cell){
		if(cell==null){
			return null;
		}
		//System.out.println(cell.getRichStringCellValue().getString());
		
		return cell.getDateCellValue();
	}
	
	private static Double getPrice(Object obj){
		if(obj==null){
			return 0.0;
		}
		return(Double)obj;
	}
	
	private static  String getStockNo(Object obj){
		if(obj == null) return null;
		
		String str = (String)obj;
		return str.substring(1,7);
	}
	private  static String getStock(Object obj){
		if(obj == null) return null;
		
		String str = (String)obj;
		return str;
	}
	private  static int getBuyOrSell(Object obj){
		if(obj == null) return 0;
		
		Double d = (Double)obj;
		return d.intValue();
		
	}

	

}
