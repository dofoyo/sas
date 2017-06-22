package com.rhb.sas.tradingrecord.bean;

import com.rhb.af.annotation.Caption;
import com.rhb.af.bean.BaseBean;


public class TradingProfit extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Caption("��Ʊ����") private String stockNo = "";
	@Caption("��Ʊ����") private String stockName = "";
	@Caption("����") private int quantity = 0;
	@Caption("�۸�") private Double unitprice = 0.0;
	@Caption("��ֵ") private Double marketValue = 0.0;
	@Caption("����") private Double profit = 0.0;

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("��Ʊ���룺");
		sb.append(stockNo);
		sb.append("����Ʊ���ƣ�");
		sb.append(stockName);
		sb.append("��������");
		sb.append(quantity);
		sb.append("���۸�");
		sb.append(unitprice);
		sb.append("����ֵ��");
		sb.append(marketValue);
		sb.append("������");
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
