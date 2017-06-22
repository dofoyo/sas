package com.rhb.sas2.domain.event;


public class StockRenameEvent {
	private String stockNo;
	private String stockName;
	
	public StockRenameEvent(String stockNo, String stockName){
		super();
		this.stockNo = stockName;
		this.stockName = stockName;
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

}
