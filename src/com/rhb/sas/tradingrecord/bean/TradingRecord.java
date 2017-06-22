package com.rhb.sas.tradingrecord.bean;

import java.math.BigDecimal;
import java.util.Date;
import com.rhb.af.annotation.Caption;
import com.rhb.af.bean.BaseBean;
import com.rhb.sas.util.Tools;

/**
 * 
 * 
create table tradingrecord
(
recordPk varchar(30) primary key,
stockNo varchar(6),
stockName varchar(10),
madeDate timestamp,
action varchar(10),
quantity int,
unitprice double,
amount double,
total double,
tax double,
fee double,
cost double
)
 *
 */

public class TradingRecord extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Caption("编码") private String recordPk = "";
	@Caption("股票代码") private String stockNo = "";
	@Caption("股票名称") private String stockName = "";
	@Caption("交易日期") private java.util.Date madeDate = new Date();
	@Caption("交易性质") private String action = "";
	@Caption("数量") private int quantity = 0;
	@Caption("价格") private Double unitprice = 0.0;
	@Caption("金额") private Double amount = 0.0;
	@Caption("合计") private Double total = 0.0;
	@Caption("印花税") private Double tax = 0.0;
	@Caption("交易费") private Double fee = 0.0;
	@Caption("其他费用") private Double cost = 0.0;

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("编码：");
		sb.append(recordPk);
		sb.append("，股票代码：");
		sb.append(stockNo);
		sb.append("，股票名称：");
		sb.append(stockName);
		sb.append("，交易日期：");
		sb.append(Tools.getDate(madeDate,"yyyy-MM-dd"));
		sb.append("，交易性质：");
		sb.append(action);
		sb.append("，数量：");
		sb.append(quantity);
		sb.append("，价格：");
		sb.append(unitprice);
		sb.append("，金额：");
		sb.append(amount);
		sb.append("，合计：");
		sb.append(total);
		sb.append("，印花税：");
		sb.append(tax);
		sb.append("，交易费：");
		sb.append(fee);
		sb.append("，其他费用：");
		sb.append(cost);
		return sb.toString();
		
	}

	
	public String getRecordPk() {
		return recordPk;
	}
	public void setRecordPk(String recordPk) {
		this.recordPk = recordPk;
	}
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public java.util.Date getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(java.util.Date madeDate) {
		this.madeDate = madeDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public String requiredValidate() {
		Double d1 = new BigDecimal(amount+fee+tax+cost).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double d2 = new BigDecimal(amount-fee-tax-cost).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		String s = "";
		int i = action.indexOf("购回");
		//System.out.println("stockName = " + stockName + ",i=" + i);
		if(quantity > 0 && i==-1){
			s = d1.equals(Math.abs(total)) ? "" : "amount+fee+tax+cost="+ d1 + ", total=" + total;
		}else if(quantity < 0 && i==-1){
			s = d2.equals(Math.abs(total)) ? "" : "amount-fee-tax-cost="+ d2 + ", total=" + total;
		}
		return s;
	}
	public String outOfRangeValidate() {
		return "";
	}

}
