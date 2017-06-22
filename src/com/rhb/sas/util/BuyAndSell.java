package com.rhb.sas.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class BuyAndSell {
	private String file;
	double amount = 100000;
	double quantity = 0.0;
	
	public BuyAndSell(String file){
		this.file = file;
	}
	
	public void doIt(int span){
		POIFSFileSystem fs;
		try {
			fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			double[] price_120_s = new double[span];
			int point_old = -1;
			int point_new = 0;
			int lastRowNum = sheet.getLastRowNum();
			for(int i=span; i<lastRowNum; i++){
				row = sheet.getRow(i);
				double price_now = getPrice(row.getCell((short)4));
				for(int j=0; j<span-1; j++){
					price_120_s[j] = price_120_s[j+1];
				}
				price_120_s[span-1] = getPrice(row.getCell((short)10));
				
				int pNow = price_now>price_120_s[span-1] ? 1 : -1;
				int p120 = price_120_s[span-1]>price_120_s[0] ? 1 : -1;
				
				point_new = pNow * p120;
				if(pNow==1 && point_new==1 && point_old==-1){
					//buy
					row.createCell((short)15).setCellValue("buy");
					row.createCell((short)16).setCellValue(-1*price_now);
					row.createCell((short)17).setCellValue(price_120_s[span-1]);
					point_old = 1;
					quantity = amount/price_now;
				}else if(pNow==-1 && point_old==1){
					//sell
					row.createCell((short)15).setCellValue("sell");
					row.createCell((short)16).setCellValue(price_now);
					row.createCell((short)17).setCellValue(price_120_s[span-1]);
					point_old = -1;
					amount = quantity * price_now;
					System.out.println("amount = " + amount);
				}
			}
			FileOutputStream fileOut = new FileOutputStream(file);

			wb.write(fileOut);
			fileOut.close();  
			System.out.println("have write " + file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private Double getPrice(HSSFCell cell){
		if(cell != null && cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
			return cell.getNumericCellValue();
		}else{
			return 0.0;
		}
	}

	
	
	
}
