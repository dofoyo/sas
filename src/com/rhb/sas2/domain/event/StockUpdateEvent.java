package com.rhb.sas2.domain.event;

import com.rhb.sas2.domain.Stock;


public class StockUpdateEvent {
	private Stock stock;
	
	public StockUpdateEvent(Stock stock){
		super();
		this.stock = stock;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
}

