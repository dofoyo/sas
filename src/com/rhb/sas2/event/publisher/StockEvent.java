package com.rhb.sas2.event.publisher;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.model.Send;
import com.jdon.domain.message.DomainMessage;
import com.rhb.sas2.domain.Stock;
import com.rhb.sas2.domain.event.StockUpdateEvent;
import com.rhb.sas2.domain.event.StockRenameEvent;

@Introduce("message")
public class StockEvent {
	@Send("update")
	public DomainMessage update(StockUpdateEvent stockUpdateEvent){
		System.out.println("StockEvent.update...");
		System.out.println(stockUpdateEvent.getStock());
		return new DomainMessage(stockUpdateEvent);
	}
	
	@Send("rename")
	public DomainMessage rename(StockRenameEvent stockRenameEvent){
		System.out.println("StockEvent.rename...");
		return new DomainMessage(stockRenameEvent);
	}
}
