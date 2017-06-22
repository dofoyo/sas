package com.rhb.sas.evaluate.planer;

import java.util.Date;

import com.rhb.sas.util.Tools;

public class Order{
	public static final int BUY = 1;
	public static final int SELL = -1;
	private String stockNo;
	private String stockName;
	private Date date;
	private int buyorsell;
	private int occupation;
	private int onlyCash;
	public void init(String stockNo,String stockName, Date date, int buyorsell,int occupation,int onlyCash){
		this.stockNo = stockNo;
		this.stockName = stockName;
		this.date = date;
		this.buyorsell = buyorsell;
		this.occupation = occupation;
		this.onlyCash = onlyCash;
	}
	public String getStockNo(){
		return stockNo;
	}

	public Date getDate(){
		return date;
	}
	public int buyOrSell(){
		return buyorsell;
	}
	public int occupation(){
		return this.occupation;
	}
	public int onlyCash(){
		return this.onlyCash;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("stockNo:" + stockNo);
		sb.append(",stockName:" + stockName);
		sb.append(",date:" + Tools.getDate(date, "yyyy-MM-dd"));
		sb.append(",buyorsell:" + buyorsell);
		sb.append(",occupation:" + occupation);
		sb.append(",onlyCash:" + onlyCash);
		return sb.toString();
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public int getBuyorsell() {
		return buyorsell;
	}
	public void setBuyorsell(int buyorsell) {
		this.buyorsell = buyorsell;
	}
	public int getOccupation() {
		return occupation;
	}
	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}
	public int getOnlyCash() {
		return onlyCash;
	}
	public void setOnlyCash(int onlyCash) {
		this.onlyCash = onlyCash;
	}
	public static int getBuy() {
		return BUY;
	}
	public static int getSell() {
		return SELL;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}

