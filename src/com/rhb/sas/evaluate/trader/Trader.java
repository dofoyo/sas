package com.rhb.sas.evaluate.trader;

import java.util.List;

public interface Trader {

	public double operate();
	public List<TradingRecord> getTradingRecords();
}
