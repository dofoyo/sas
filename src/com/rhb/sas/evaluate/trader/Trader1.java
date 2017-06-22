package com.rhb.sas.evaluate.trader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

import com.rhb.sas.evaluate.planer.Order;
import com.rhb.sas.util.Tools;

/**
 * 交易操作员
 * @author rhb
 *
 */

public class Trader1 implements Trader {
	String path = null;
	private List<TradingRecord> tradingRecords = new ArrayList();
	private List<Order> orders = new ArrayList();
	private Holdon holdon = new Holdon();
	
	public Trader1(List<Order> orderList, double cash, String endDate, String path){
		this.path = path;
		holdon.cash = cash;
		holdon.endDate = Tools.getDate(endDate);
		
		for(Order order : orderList){
			if(order.getDate()!=null && order.getDate().compareTo(holdon.endDate) != 1){
				orders.add(order);
			}else{
				System.out.println(order + " is out of " + endDate);
			}
		}
	
	}

	@Override
	public double operate() {
		for(Order order : orders){
			operate(order);
		}
		
		System.out.println("*****************");
		
		for(TradingRecord tr : tradingRecords){
			System.out.println(tr.toCVS());
		}
		
		return holdon.getHoldOnValue();
	}
	
	
	private void operate(Order order){
		List<TradingRecord> tradingPlans = null;
		if(order.buyOrSell() == Order.BUY){
			System.out.println(Tools.getDate(order.getDate(), "yyyy-MM-dd") + " buy " + order.getStockNo() + order.getStockName());
			//System.out.println(holdon.toString(order.getDate()));
			tradingPlans = buyPlan(order);
		}else if(order.buyOrSell() == Order.SELL){
			System.out.println(Tools.getDate(order.getDate(), "yyyy-MM-dd") + " sell " + order.getStockNo() + order.getStockName());
			//System.out.println(holdon.toString(order.getDate()));
			tradingPlans = sellPlan(order);
		}
		holdon.trading(tradingPlans);
		tradingRecords.addAll(tradingPlans);
		System.out.println(holdon.toString(order.getDate()));
	}

	private List<TradingRecord> buyPlan(Order order){
		List<TradingRecord> records = new ArrayList();
		if(order.getOnlyCash() == 0){
			double cash = holdon.cash;
			double avail = holdon.getAvailForBuy(order.getDate());
			double wanted = avail - cash;
			System.out.println("marketValue is " + holdon.getMarketValue(order.getDate()));
			System.out.println("avail is " + avail);
			System.out.println("cash is " + cash);
	
			records = holdon.sellforbuyPlan(order.getDate(),wanted);
			double price = this.getMarketPrice(order.getStockNo(), order.getDate());
			//System.out.println(avail);
			if(wanted<0){
				avail = cash;
			}
			int quantity =  (new Double(avail/price/100)).intValue()*100*order.buyOrSell();
			
			TradingRecord tr = new TradingRecord();
			tr.init(order.getStockNo(),order.getStockName(), order.getDate(), price, quantity,0.0);
			records.add(tr);
			System.out.println("buy " + order.getStockNo() + order.getStockName() + ",quantity=" + quantity + ",price=" + price + ". wanted is " + wanted);
		}else{
			double cash = holdon.cash;
			double price = this.getMarketPrice(order.getStockNo(), order.getDate());
			int quantity =  (new Double(cash/order.occupation()/price/100)).intValue()*100;
			
			TradingRecord tr = new TradingRecord();
			tr.init(order.getStockNo(),order.getStockName(), order.getDate(), price, quantity,0.0);
			records.add(tr);
			System.out.println("buy " + order.getStockNo() + order.getStockName() + ",quantity=" + quantity + ",price=" + price + ". only cash, cash is " + cash);
			//System.out.println("buy " + order.getStockNo() + order.getStockName() + " only cash, cash is " + cash);
			
		}
		
		return records;
	}

	private List<TradingRecord> sellPlan(Order order){
		double price = this.getMarketPrice(order.getStockNo(), order.getDate());
		int quantity = 0;
		Integer iq = this.holdon.position.get(order.getStockNo());
		if(iq != null){
			quantity = this.holdon.position.get(order.getStockNo());			
		}
			
		//int quantity = holdon.getAvailForSell();
		
		List<TradingRecord> records = new ArrayList();
		
		TradingRecord tr = new TradingRecord();
		tr.init(order.getStockNo(),order.getStockName(), order.getDate(), price, -1*quantity,0.0);
		System.out.println("sell " + order.getStockNo() + order.getStockName() + ", " + quantity);
		records.add(tr);
		return records;
	}
	

	
	public List<Order> getOrders(){
		return this.orders;
	}
	
	public Double getMarketPrice(String stockNo, Date date){
		File file = new File(path + stockNo + ".xls");
		if(!file.exists()) return null;

		Double price = null;
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
	
	private double getPrice(HSSFSheet sheet,Date date){
		//System.out.println("date=" + Tools.getDate(date, "yyyy-MM-dd"));
		double price = 0.0;
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
		price = getPrice(getCellValue(row.getCell((short)4)));
		return price;
	}
	
	

	public Holdon getHoldon(){
		return this.holdon;
	}
	
	public class Holdon{
		private Date endDate = null;
		private double cash = 0.0;
		private Map<String,Integer> position = new HashMap();
		
		public double getHoldOnValue(){
			return cash + getMarketValue(endDate);
			
		}
		
		public void trading(List<TradingRecord> tradingRecords){
			for(TradingRecord tr : tradingRecords){
				//System.out.println(tr);
				cash += tr.getQuantity() * tr.getPrice()* -1;
				if(position.containsKey(tr.getStockNo())){
					int quantity = position.get(tr.getStockNo());
					position.remove(tr.getStockNo());
					if(quantity+tr.getQuantity()>0){
						position.put(tr.getStockNo(), quantity+tr.getQuantity());						
					}
				}else{
					position.put(tr.getStockNo(), tr.getQuantity());
				}
			}
		}

		public List<TradingRecord> sellPlan(Date date){
			List<TradingRecord> plans = new ArrayList();
			double d = getMarketValue(date)/(position.size()+1);
			Iterator it = position.keySet().iterator();
			while(it.hasNext()){
				String stockNo = (String)it.next();
				int quantity_before = position.get(stockNo);
				double price = getMarketPrice(stockNo, date);
				int quantity_after = (new Double(d/price)).intValue();	
				TradingRecord tr = new TradingRecord();
				tr.init(stockNo,"", date, price, -1*(quantity_before-quantity_after),0.0);
				plans.add(tr);
			}
			return plans;
		}
		
		public List<TradingRecord> sellforbuyPlan(Date date, double wanted){
			List<TradingRecord> plans = new ArrayList();
			if(wanted > 0){
				Iterator it = position.keySet().iterator();
				while(it.hasNext()){
					String stockNo = (String)it.next();
					double allMarketValue = getMarketValue(date);
					double theMarketValue = getMarketValue(stockNo,date);
					double ratio = theMarketValue/allMarketValue;
					double price = getMarketPrice(stockNo, date);
					int quantity = new Double(ratio*wanted/price).intValue();	
					TradingRecord tr = new TradingRecord();
					tr.init(stockNo,"", date, price, -1*quantity,0.0);
					System.out.println("will sell " + stockNo + ",quantity=" + quantity + ",price=" + price + ",amount= "+quantity*price+", for wanted=" + wanted);
					plans.add(tr);
				}
			}
			return plans;
		}
		
		/*
		public int getAvailForSell(String stock){
			Integer i = position.get(stock);
			if(i==null){
				return position.get(stock);				
			}else{
				return 0;
			}
		}*/
		
		public double getAvailForBuy(Date date){
			double d = (getMarketValue(date)+cash)/(position.size()+1);
			return d;
		}
		
		public double getMarketValue(String stockNo,Date date){
			int quantity = position.get(stockNo);
			double price = getMarketPrice(stockNo, date);
			return price * quantity;
		}

		
		public double getMarketValue(Date date){
			double value = 0.0;
			String stock;
			int quantity;
			double price;
			Iterator it = position.keySet().iterator();
			while(it.hasNext()){
				stock = (String)it.next();
				//System.out.println(stock);
				quantity = position.get(stock);
				price = getMarketPrice(stock.substring(0, 6), date);
				value += quantity * price;
			}
			return value;
		}
		
		public String toString(Date date){
			StringBuffer sb = new StringBuffer();
			sb.append("holdon: date=" + Tools.getDate(date, "yyyy-MM-dd") + "*****************");
			sb.append("\nmarketValue = " + getMarketValue(date));
			sb.append("\ncash = " + cash);
			Iterator it = position.keySet().iterator();
			while(it.hasNext()){
				String stockNo = (String)it.next();
				int quantity = position.get(stockNo);
				double price = getMarketPrice(stockNo.substring(0, 6), date);
				sb.append("\n" + stockNo + "=" + quantity + ", " + price + "," + quantity*price);
			}
			return sb.toString();
		}
	}
	
	
	public class MarketPrice{
		private String stockNo;
		private String stockName;
		private Date date;
		private double price;
		
		public void init(String stockNo,String stockName, Date date, double price){
			this.stockNo = stockNo;
			this.stockName = stockName;
			this.date = date;
			this.price = price;
		}
		public String getStockNo(){
			return this.stockNo;
		}
		public Date getDate(){
			return this.date;
		}
		public double getPrice(){
			return this.price;
		}
		
	
		public String toString(){
			StringBuffer sb = new StringBuffer();
			sb.append("stockNo:" + stockNo);
			sb.append(",stockName:" + stockName);
			sb.append(",date:" + Tools.getDate(date, "yyyy-MM-dd"));
			sb.append(",price:" + price);
			return sb.toString();
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
	
	private Date getDate(HSSFCell cell){
		if(cell==null){
			return null;
		}
		//System.out.println(cell.getRichStringCellValue().getString());
		
		return cell.getDateCellValue();
	}
	
	private Double getPrice(Object obj){
		if(obj==null){
			return 0.0;
		}
		return(Double)obj;
	}
	
	@Override
	public List<TradingRecord> getTradingRecords(){
		return this.tradingRecords;
	}
	
}
