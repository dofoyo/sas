package com.rhb.sas.evaluate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


import com.rhb.af.bean.Page;
import com.rhb.af.business.FindBusiness;
import com.rhb.sas.download.marketprice.DownloadMarketPrice;
import com.rhb.sas.download.marketprice.DownloadMarketPriceFromYahoo;
import com.rhb.sas.evaluate.filter.Filter;
import com.rhb.sas.evaluate.filter.Filter_NoNew;
import com.rhb.sas.evaluate.planer.Order;
import com.rhb.sas.evaluate.planer.Planer;
import com.rhb.sas.evaluate.planer.Planer1;
import com.rhb.sas.evaluate.planer.Planer2;
import com.rhb.sas.evaluate.planer.Planer3;
import com.rhb.sas.evaluate.planer.Planer4;
import com.rhb.sas.evaluate.planer.Planer5;
import com.rhb.sas.evaluate.selector.Selector;
import com.rhb.sas.evaluate.selector.Selector1;
import com.rhb.sas.evaluate.selector.Selector2;
import com.rhb.sas.evaluate.selector.Selector4;
import com.rhb.sas.evaluate.trader.Trader;
import com.rhb.sas.evaluate.trader.Trader1;
import com.rhb.sas.evaluate.trader.TradingRecord;
import com.rhb.sas.evaluate.util.generateOrderByExcel;
import com.rhb.sas.firm.Firm;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stock.bean.StockQuery;

/**
 * 选股：	
	销售收入	销售收入持续增长，且近2年销售收入增长率大于30%
	利润		利润持续增长，且近2年利润增长率大于30%
	现金流	经营活动现金流为正，且持续增长，且近2年现金流增长率大于30%
	毛利率	且近2年毛利率大于20%
	应收款	不考虑
	负债		不考虑 
	净资产收益率	且近2年平均值大于13%
 */

public class Evaluator5 implements Evaluator {
	String path = null;
	String fileName = null;
	private int[] years = {2009,2010,2011,2012}; 
	private int period = 3; //近3年的业绩比较，即：如果year=2012,则会用2010，2011，2012三年的数据进行计算
	private FindBusiness find;
	private Map<String, Double> results = new LinkedHashMap();
	
	Map<Integer,List<Firm>> selectResult = null;
	
	public Evaluator5(FindBusiness find, String path, String fileName){
		this.path = path;
		this.fileName = fileName;
		this.find = find;
		File file = new File(path+fileName);
		if(file.exists()){
			//读取之前选股结果
			selectResult = readFromFile(path+fileName);
		}else{
			//获得股票清单
			List<Firm> firms = getFirms(find);

			//选股
			selectResult = new TreeMap();
			Selector selector; 
			for(int year : years){
				selector = new Selector4(firms, year, period); 
				selectResult.put(year, selector.select());

			}
			//保存选股结果，避免每次花费大量的时间重新选股
			writeToFile(selectResult,path + fileName);
			

		}
		
		//DownloadMarketPrice dmp = new DownloadMarketPriceFromYahoo(); //从雅虎上download下来的价格有问题
		
		Iterator<Integer> ite = selectResult.keySet().iterator();
		while(ite.hasNext()){
			int year = ite.next();
			List<Firm> firms = selectResult.get(year);
			for(Firm firm : firms){
				File f=new File(path + firm.getStockNo() + ".xls");
		        if(!f.exists()){
		        	System.out.println(path + firm.getStockNo() + ".xls does NOT exist! ");
		        	//dmp.Down(firm.getStockNo(), path);
		        }
			}
			
		}
	}
	
	
	@Override
	public List<String> evaluate() {
		List<String> results = new ArrayList();

		//操作计划1
		//计划1需要过滤
		Filter filter_no_new = new Filter_NoNew(selectResult); 
		Map<Integer,List<Firm>> filter_no_new_result = filter_no_new.result();
		
		Planer planer1 = new Planer1(filter_no_new_result);
		List<Order> orders1 = planer1.plan();
		System.out.println("*****  order1 ********");
		for(Order order : orders1){
			System.out.println(order);
		}
		
		//操作计划2
		Planer planer2 = new Planer2(selectResult,find);
		List<Order> orders2 = planer2.plan();
		System.out.println("*****  order2 ********");
		for(Order order : orders2){
			System.out.println(order);
		}

		//操作计划3
		Planer planer3 = new Planer3(selectResult,find,path);
		List<Order> orders3 = planer3.plan();
		System.out.println("*****  order3 ********");
		for(Order order : orders3){
			System.out.println(order);
		}
		
		//操作计划4
		Planer planer4 = new Planer4(selectResult,find,path);
		List<Order> orders4 = planer4.plan();
		System.out.println("*****  order4 ********");
		for(Order order : orders4){
			System.out.println(order);
		}

		//操作计划5
		Planer planer5 = new Planer5(selectResult,find,path);
		List<Order> orders5 = planer5.plan();
		System.out.println("*****  order5 ********");
		for(Order order : orders5){
			System.out.println(order);
		}
		
		
		Trader trader;

		//执行计划1
		System.out.println("*****  tradingRecord1 ********");
		trader = new Trader1(orders1,2000000,"2013-4-30",path);
		double amount14 = trader.operate();
		results.add("result14:" + amount14);

		//执行计划2
		System.out.println("*****  tradingRecord2 ********");
		trader = new Trader1(orders2,2000000,"2013-4-30",path);
		double amount24 = trader.operate();
		results.add("result24:" + amount24);
		
		//执行计划3
		System.out.println("*****  tradingRecord3 ********");
		trader = new Trader1(orders3,2000000,"2013-4-30",path);
		double amount34 = trader.operate();
		results.add("result34:" + amount34);
		
		//执行计划4
		System.out.println("*****  tradingRecord4 ********");
		trader = new Trader1(orders4,2000000,"2013-4-30",path);
		double amount44 = trader.operate();
		results.add("result44:" + amount44);

		//执行计划5
		System.out.println("*****  tradingRecord5 ********");
		trader = new Trader1(orders5,2000000,"2013-4-30",path);
		double amount54 = trader.operate();
		results.add("result54:" + amount54);
		
		return results;
	}
	
	private Map<Integer,List<Firm>> readFromFile(String file){
		System.out.println("read from file ......");
		Map<Integer,List<Firm>> firms = new TreeMap();
		Map<Integer,List<String>> stockNos = readFromExcel(file);
		Iterator<Integer> it = stockNos.keySet().iterator();
		while(it.hasNext()){
			int year = it.next();
			List<Firm> fList = new ArrayList();
			List<String> list = (List)stockNos.get(year);
			for(String stockNo : list){
				Firm firm = new Firm(stockNo, find);
				firm.init(year, period,"12");
				fList.add(firm);
				System.out.println(firm.getStockInfo());
			}
			firms.put(year, fList);
					
		}
		return firms;
	}
	
	private Map<Integer,List<String>> readFromExcel(String file){
		Map<Integer,List<String>> stockNos = new TreeMap();
		POIFSFileSystem fs;
		try {
			fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			int lastRowNum = sheet.getLastRowNum();
			for(int i=1; i<lastRowNum+1; i++){
				row = sheet.getRow(i);
				String year = (row.getCell((short)0)).getStringCellValue();
				String stockNo = (row.getCell((short)1)).getStringCellValue();

				List<String> list;
				if(stockNos.containsKey(Integer.parseInt(year))){
					list = stockNos.get(Integer.parseInt(year));
				}else{
					list = new ArrayList();
				}
				list.add(stockNo);
				stockNos.put(Integer.parseInt(year), list);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return stockNos;

	}
	
	
	
	private void writeToFile(Map<Integer,List<Firm>> selectResult, String file){
		try {
			HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象  
			HSSFSheet sheet = wb.createSheet("sheet1");//建立新的sheet对象  
			List<Firm> firms;
			Iterator<Integer> it = selectResult.keySet().iterator();
			int i=0;
			HSSFRow row_title = sheet.createRow((short)i++);//建立新行  
			row_title.createCell((short)0).setCellValue("year");  
			row_title.createCell((short)1).setCellValue("stockNo");
			row_title.createCell((short)2).setCellValue("stockName");
			while(it.hasNext()){
				int year = it.next();
				firms = selectResult.get(year);
				for(Firm firm : firms){
					HSSFRow row = sheet.createRow((short)i++);//建立新行  
					row.createCell((short)0).setCellValue(Integer.toString(year));  
					row.createCell((short)1).setCellValue(firm.getStockNo());
					row.createCell((short)2).setCellValue(firm.getStockName());
				}
			}
		
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);//把Workbook对象输出到文件workbook.xls中  
			fileOut.close();  
			System.out.println("have write " + file);
		} catch (Exception e) {
			e.printStackTrace();
		}  
		  
		  
		
	}
	
	
	private List<Firm> getFirms(FindBusiness find){
		List<Firm> firms  = new ArrayList();
		StockQuery sq = new StockQuery();
		sq.empty();
		sq.setCount(10000);
		Page page = find.findByQuery(sq);
		List<Stock> stocks = page.getList();
		for(int i=0; i<stocks.size(); i++){
			Stock stock = stocks.get(i);
			Firm firm = new Firm(stock.getStockNo(), find);
			System.out.println(Integer.toString(i+1) + "/" + Integer.toString(stocks.size()));
			firms.add(firm);
		}
		return firms;
	}
		

}
