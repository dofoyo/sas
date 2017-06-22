package com.rhb.sas.tradingrecord.bean;

import com.rhb.af.annotation.Clazz;
import com.rhb.af.annotation.Track;
import com.rhb.af.bean.BaseQuery;

@Clazz(TradingProfit.class)
public class TradingProfitQuery extends BaseQuery {
	@Track("stockNo") 		private String stockNo = "";
	@Track("stockName")		private String stockName = "";

	
	public void empty(){
		stockNo = "";
		stockName = "";
	}

	public String getStockNo() {
		return this.stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

}
