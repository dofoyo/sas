package test.com.rhb.sas.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

import com.rhb.sas.util.HttpDownFile;
import com.rhb.sas.util.Tools;

public class TestHttpDownFile {

	@Test
	public void download(){
		String stockNo = "300022";

		Date d = new Date();
		String theYear = Tools.getDate(d, "yyyy");
		String sz = stockNo.startsWith("6") ? "SS" : "SZ";
		String destUrl = "http://ichart.finance.yahoo.com/table.csv?s="+stockNo+"."+sz+"&a=00&b=1&c=1991&d=11&e=31&f="+theYear+"&g=d&ignore=.csv";
		System.out.println(destUrl);
		String file_csv = "C:\\" + stockNo + ".csv";
		String file_xls = "C:\\" + stockNo + ".xls";
		HttpDownFile df = new HttpDownFile();
		try {
			df.saveToFile(destUrl, file_csv);
			saveToExcel(stockNo, file_csv, file_xls);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveToExcel(String stockNo, String file_csv, String file_xls){
		try {
			Map<Date, Double> prices = parseCSV(file_csv);
			
			
			//POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file_xls));
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet1");
			HSSFRow row0 = sheet.createRow((short)0);
			row0.createCell((short)0).setCellValue(stockNo);
			
			HSSFRow row1 = sheet.createRow((short)1);
			row1.createCell((short)0).setCellValue("日期");
			row1.createCell((short)1).setCellValue("开盘");
			row1.createCell((short)2).setCellValue("最高");
			row1.createCell((short)3).setCellValue("最低");
			row1.createCell((short)4).setCellValue("收盘");
			row1.createCell((short)5).setCellValue("成交量");
			row1.createCell((short)6).setCellValue("成交额");
			row1.createCell((short)7).setCellValue("成交笔数");
			row1.createCell((short)8).setCellValue("MA1");
			row1.createCell((short)9).setCellValue("MA2");
			row1.createCell((short)10).setCellValue("MA3");
			row1.createCell((short)11).setCellValue("MA4");
			row1.createCell((short)12).setCellValue("MA5");
			row1.createCell((short)13).setCellValue("MA6");
			
			HSSFCellStyle dateCellStyle=wb.createCellStyle();
			short df=wb.createDataFormat().getFormat("yyyy-MM-dd"); 
			dateCellStyle.setDataFormat(df);
			
			PriceList pl = new PriceList();
			
			Iterator<Date> it = prices.keySet().iterator();
			for(short i=2; it.hasNext(); i++){
				Date date = it.next();
				double price = prices.get(date);
				pl.put(price);
				HSSFRow rowx = sheet.createRow(i);
				//rowx.createCell((short)0).setCellValue(date);
				HSSFCell cell = rowx.createCell((short)0);
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellStyle(dateCellStyle);
				cell.setCellValue(date); 

				rowx.createCell((short)4).setCellValue(price);
				
				rowx.createCell((short)13).setCellValue(pl.getAvarage());
				
				System.out.println(i);
			}
			
			
			FileOutputStream fileOut = new FileOutputStream(file_xls);

			wb.write(fileOut);
			fileOut.close();  
			System.out.println("have write " + file_xls);


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<Date, Double> parseCSV(String file){
		Map<Date, Double> prices = new TreeMap();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String data; 
			double quantity = 0.0;
			int i = 1;
			while((data = br.readLine()) != null){
			      String[] datas = data.split(",");
			      System.out.println(datas[6]);
			      if(isNumeric(datas[6])){
			    	  quantity = new Double(datas[5]);
			    	  if(quantity>0){
					      Double price = new Double(datas[6]);
					      Date date = Tools.getDate(datas[0]);
					      prices.put(date, price);
			    	  }
			      }
			      System.out.println(i++);
			      
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return prices;
	}

	
	public boolean isNumeric(String str)
	{
	Pattern pattern = Pattern.compile("-?[0-9]*.?[0-9]*");
	Matcher isNum = pattern.matcher(str);
	if( !isNum.matches() )
	{
	return false;
	}
	return true;
	} 
	
	private Double getPrice(HSSFCell cell){
		if(cell != null && cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
			return cell.getNumericCellValue();
		}else{
			return 0.0;
		}
	}
	
	class PriceList{
		private List<Double> prices = new ArrayList();
		double total = 0.0;
		public void put(double price){
			total = total + price;
			//System.out.println("put " + price);
			prices.add(price);
			//System.out.println("size: " + prices.size());
			if(prices.size()==121){
				total = total - prices.get(0);
				//System.out.println("remove" + prices.get(0));
				prices.remove(0);
			}
		}
		
		public double getAvarage(){
			if(prices.size()==120){
				return total/prices.size();			
			}else{
				return 0.0;
			}
		}
		
	}
	
}
