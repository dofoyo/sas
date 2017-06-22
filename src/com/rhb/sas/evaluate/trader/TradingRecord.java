package com.rhb.sas.evaluate.trader;

import java.util.Date;

import com.rhb.sas.util.Tools;


public class TradingRecord{
	private String stockNo;
	private String stockName;
	private Date date;
	private double price;
	private int quantity;
	private double cash;
	public void init(String stockNo,String stockName, Date date, double price, int quantity, double cash){
		this.stockNo = stockNo;
		this.stockName = stockName;
		this.date = date;
		this.price = price;
		this.quantity = quantity;
		this.cash = cash;
	}
	public String getStockNo(){
		return this.stockNo;
	}
	public String getStockName(){
		return this.stockName;
	}		
	public Date getDate(){
		return this.date;
	}
	public double getPrice(){
		return this.price;
	}
	public int getQuantity(){
		return this.quantity;
	}
	public double getCash(){
		return this.cash;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("stockNo:" + stockNo);
		sb.append(",stockName:" + stockName);
		sb.append(",date:" + Tools.getDate(date, "yyyy-MM-dd"));
		sb.append(",price:" + price);
		sb.append(",quantity:" + quantity);
		sb.append(",cash:" + cash);
		return sb.toString();
	}
	
	public String toCVS(){
		StringBuffer sb = new StringBuffer();
		sb.append(stockNo);
		sb.append("," + stockName);
		sb.append("," + Tools.getDate(date, "yyyy-MM-dd"));
		sb.append("," + price);
		sb.append("," + quantity);
		sb.append("," + cash);
		return sb.toString();
	}		
}