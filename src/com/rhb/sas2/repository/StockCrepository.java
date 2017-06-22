package com.rhb.sas2.repository;

import com.rhb.sas2.domain.Stock;

public interface StockCrepository {
	public abstract void create(String stockNo);
	public abstract void rename(String stockNo, String stockName);
	public abstract void update(Stock stock);
	public abstract void delete(String stockNo);
	public abstract Stock getStock(String stockNo);

}
