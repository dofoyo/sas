package com.rhb.sas.tradingrecord.bean;

import com.rhb.af.annotation.Clazz;
import com.rhb.af.annotation.Track;
import com.rhb.af.bean.BaseQuery;

@Clazz(TradingRecord.class)
public class TradingRecordQuery extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Track("stockNo") private String stockNo;
	@Track("stockName") private String stockName;
	@Track("madeDate") private String madeDate_begin;
	@Track("madeDate") private String madeDate_end;
	
	public TradingRecordQuery(){
		this.stockNo = "";
		this.stockName = "";
		this.madeDate_begin = "";
		this.madeDate_end = "";
		this.setOrderBy("madeDate");
		this.setDesc(true);
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

	public String getMadeDate_begin() {
		return madeDate_begin;
	}

	public void setMadeDate_begin(String madeDate_begin) {
		this.madeDate_begin = madeDate_begin;
	}

	public String getMadeDate_end() {
		return madeDate_end;
	}

	public void setMadeDate_end(String madeDate_end) {
		this.madeDate_end = madeDate_end;
	}

	

}
