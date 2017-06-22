package com.rhb.sas.interfaces.downloadmarketinfo;

public class MarketInfoDTO {
	private String stockNo;
	private String stockName;
	private Double price;
	private Double currentMarketValue;
	private Double marketValue;
	
	public MarketInfoDTO(String stockNo, String stockName, Double price,
			Double marketValue, Double currentMarketValue) {
		super();
		this.stockNo = stockNo;
		this.stockName = stockName;
		this.price = price;
		this.currentMarketValue = currentMarketValue;
		this.marketValue = marketValue;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public Double getCurrentMarketValue() {
		return currentMarketValue;
	}

	public void setCurrentMarketValue(Double currentMarketValue) {
		this.currentMarketValue = currentMarketValue;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(stockNo + "	" + stockName + "	" + price + "	" + currentMarketValue + "		" + marketValue);
		return sb.toString();
	}
	
	
}
