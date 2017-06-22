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
	
	@Caption("����") private String recordPk = "";
	@Caption("��Ʊ����") private String stockNo = "";
	@Caption("��Ʊ����") private String stockName = "";
	@Caption("��������") private java.util.Date madeDate = new Date();
	@Caption("��������") private String action = "";
	@Caption("����") private int quantity = 0;
	@Caption("�۸�") private Double unitprice = 0.0;
	@Caption("���") private Double amount = 0.0;
	@Caption("�ϼ�") private Double total = 0.0;
	@Caption("ӡ��˰") private Double tax = 0.0;
	@Caption("���׷�") private Double fee = 0.0;
	@Caption("��������") private Double cost = 0.0;

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("���룺");
		sb.append(recordPk);
		sb.append("����Ʊ���룺");
		sb.append(stockNo);
		sb.append("����Ʊ���ƣ�");
		sb.append(stockName);
		sb.append("���������ڣ�");
		sb.append(Tools.getDate(madeDate,"yyyy-MM-dd"));
		sb.append("���������ʣ�");
		sb.append(action);
		sb.append("��������");
		sb.append(quantity);
		sb.append("���۸�");
		sb.append(unitprice);
		sb.append("����");
		sb.append(amount);
		sb.append("���ϼƣ�");
		sb.append(total);
		sb.append("��ӡ��˰��");
		sb.append(tax);
		sb.append("�����׷ѣ�");
		sb.append(fee);
		sb.append("���������ã�");
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
		int i = action.indexOf("����");
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
