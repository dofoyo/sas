package com.rhb.sas.tradingrecord.bean;

import com.rhb.af.annotation.Caption;
import com.rhb.af.bean.BaseBean;


public class TradingProfit extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Caption("股票代码") private String stockNo = "";
	@Caption("股票名称") private String stockName = "";
	@Caption("数量") private int quantity = 0;
	@Caption("价格") private Double unitprice = 0.0;
	@Caption("市值") private Double marketValue = 0.0;
	@Caption("利润") private Double profit = 0.0;

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("股票代码：");
		sb.append(stockNo);
		sb.append("，股票名称：");
		sb.append(stockName);
		sb.append("，数量：");
		sb.append(quantity);
		sb.append("，价格：");
		sb.append(unitprice);
		sb.append("，市值：");
		sb.append(marketValue);
		sb.append("，利润：");
		sb.append(profit);
		return sb.toString();
		
	}

	
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}


	public Double getMarketValue() {
		return marketValue;
	}


	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}


	public Double getProfit() {
		return profit;
	}


	public void setProfit(Double profit) {
		this.profit = profit;
	}
	

}
