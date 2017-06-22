package test.com.rhb.sas.application;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Just {
	
	private String file = "d:\\0912_1.xls";
	
	public void doIt(){
		try{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			int numberOfSheets = wb.getNumberOfSheets();
			HSSFSheet sheet = null;
			HSSFRow row = null;
			Object obj = null;
			for(int i=5; i<numberOfSheets; i++ ){
				sheet = wb.getSheetAt(i);
				row = sheet.getRow(47);
				if(row!=null){
					obj = getCellValue(row.getCell((short)1));
					if(obj != null){
						row  = sheet.getRow(0);
						System.out.println(getCellValue(row.getCell((short)0)));
						System.out.println(obj);											
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
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
	
	public static void main(String[] args) {
		System.out.println("***************");
		Just just = new Just();
		just.doIt();
	}


}
