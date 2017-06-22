package com.rhb.sas.interfaces.downloadmarketinfo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.rhb.sas.application.console.Sensor;

public class DownloadMarketInfoFromDzh implements DownloadMarketInfo {

	private String file;
	private Sensor sensor;
	
	
	public List<MarketInfoDTO> doIt() {
		List<MarketInfoDTO> list = new ArrayList();
		System.out.println("file = " + file);
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			HSSFRow row;
			MarketInfoDTO dto;
			String stockNo;
			String stockName;
			double price;
			double marketValue;
			double currentMarketValue;
			for(int i=1; i<=lastRowNum; i++){
				row = sheet.getRow(i);	
				stockNo = getStockNo(getCellValue(row.getCell((short)1)));
				stockName = (String)getCellValue(row.getCell((short)2));
				price = getPrice(getCellValue(row.getCell((short)3)));
				marketValue = getMarketValue(getCellValue(row.getCell((short)31)));
				currentMarketValue = getMarketValue(getCellValue(row.getCell((short)32)));
				dto = new MarketInfoDTO(
						stockNo,
						stockName,
						price,
						marketValue,
						currentMarketValue
					);
				if(stockNo!=null){
					list.add(dto);					
				}
				System.out.println(dto);
			}

		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.toString());
			e.printStackTrace();
		}

		return list;
	}
	
	private Double getPrice(Object obj){
		if(obj==null){
			return 0.0;
		}
		return(Double)obj;
		
	}
	
	private Double getMarketValue(Object obj){
		if(obj==null) return 0.0;
		Double d = (Double)obj;
		return d/10000;
	}
	
	private String getStockNo(Object obj){
		if(obj == null) return null;
		
		String str = (String)obj;
		return str.substring(1,7);
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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}
