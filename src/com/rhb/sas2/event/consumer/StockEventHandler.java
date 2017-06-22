package com.rhb.sas2.event.consumer;

import com.jdon.annotation.Component;
import com.jdon.annotation.model.OnEvent;
import com.rhb.sas2.domain.event.StockUpdateEvent;
import com.rhb.sas2.domain.event.StockRenameEvent;
import com.rhb.sas2.repository.StockCrepository;

@Component
public class StockEventHandler {
	private final StockCrepository stockCrepository;
	
	public StockEventHandler(StockCrepository stockCrepository){
		super();
		this.stockCrepository = stockCrepository;
	}

	@OnEvent("update")
	public void update(StockUpdateEvent stockUpdateEvent){
		System.out.println("StockEventHandler.update...");
		stockCrepository.update(stockUpdateEvent.getStock());
		System.out.println("??" + stockUpdateEvent.getStock());
	}

	@OnEvent("rename")
	public void rename(StockRenameEvent stockRenameEvent){
		System.out.println("StockEventHandler.rename...");
		stockCrepository.rename(stockRenameEvent.getStockNo(),stockRenameEvent.getStockName());
	}
}
