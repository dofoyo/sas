package test.rhb.sas.tradingrecord.business;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.rhb.sas.tradingrecord.bean.*;
import com.rhb.sas.util.Tools;

public class CreateRecordsFromExcel{

	private String file;
	
	public List<TradingRecord> doIt() {
		List<TradingRecord> list = new ArrayList<TradingRecord>();
		System.out.println("file = " + file);
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			HSSFRow row;
			
			Date madeDate;
			String stockNo;
			String stockName;
			String action;
			int quantity;
			double unitprice;
			double amount;
			double total;
			double fee;
			double tax;
			double cost;
			
			TradingRecord tr;
			
			for(int i=1; i<=lastRowNum; i++){
				row = sheet.getRow(i);	
				madeDate = getDate(row,0);
				stockNo = getStockNo(row,1);
				stockName = getString(row,2);
				action = getString(row,3);
				quantity = getInteger(row,4);
				unitprice = getDouble(row,5);
				amount = getDouble(row,6);
				total = getDouble(row,12);
				fee = getDouble(row,9);
				tax = getDouble(row,10);
				cost = getDouble(row,11);
				
				tr = new TradingRecord();
				tr.setMadeDate(madeDate);
				tr.setStockNo(stockNo);
				tr.setStockName(stockName);
				tr.setAction(action);
				tr.setQuantity(quantity);
				tr.setUnitprice(unitprice);
				tr.setAmount(amount);
				tr.setTotal(total);
				tr.setFee(fee);
				tr.setTax(tax);
				tr.setCost(cost);
				
				if(stockNo != null){
					list.add(tr);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	

	private int getInteger(HSSFRow row,int i){
		return (int)(double)getDouble(row,i);
	}
	

	private Double getDouble(HSSFRow row,int i){
		String obj = getString(row,i);
		return obj.length()==0 ? 0.0 : Double.parseDouble(obj);
	}

	private String getString(HSSFRow row,int i){
		HSSFCell cell = row.getCell((short)i);
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
				obj = cell.getCellFormula();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				obj = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_STRING:
				obj = cell.getRichStringCellValue();
				break;
			}
		}
		return obj.toString();
	}

	private String getStockNo(HSSFRow row,int i){
		HSSFCell cell = row.getCell((short)i);
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
				obj = cell.getCellFormula();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				obj = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_STRING:
				obj = cell.getRichStringCellValue();
				break;
			}
		}
		
		String s = obj==null ? "" : obj.toString();
		s = s.substring(0,s.length()-2);
		if(s.length()==1){
			s = "00000" + s;
		}else if(s.length()==2){
			s = "0000" + s;		
		}else if(s.length()==3){
			s = "000" + s;		
		}else if(s.length()==4){
			s = "00" + s;		
		}

		return s;
	}
	
	private Date getDate(HSSFRow row,int i){
		HSSFCell cell = row.getCell((short)i);
		
		if(cell == null) return null;

		/*
		Date d = null;
		int dataFormat = cell.getCellStyle().getDataFormat();
			
		if (dataFormat == 14 || dataFormat == 178 || dataFormat == 180 || dataFormat == 181  || dataFormat == 182) {
			d = cell.getDateCellValue();
		}
		
		System.out.println(Tools.getDate(d, "yyyyMMdd"));
		*/
		Object obj = cell.getRichStringCellValue();
		
		Date dd = Tools.getDate(obj.toString());
	
		return dd;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
