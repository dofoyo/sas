package com.rhb.sas2.domain;

import java.util.Map;
import java.util.TreeMap;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.rhb.sas2.domain.event.StockRenameEvent;
import com.rhb.sas2.domain.event.StockUpdateEvent;
import com.rhb.sas2.event.publisher.StockEvent;

@Model
public class Stock{
	private String stockNo;
	private Organization organization;
	Map<String,Report> reports;
	
	@Inject
	public StockEvent domainEvent;
	
	public Stock(){
		super();
		this.stockNo = null;
		this.organization = new Organization();
		this.reports = new TreeMap<String,Report>();
	}

	public void rename(String stockName){
		System.out.println("StockModel.rename...");
		this.organization.setName(stockName);
		domainEvent.rename(new StockRenameEvent(stockNo, stockName));
	}

	public void update(Organization organization){
		System.out.println("StockModel.update...");
		this.organization = organization;
		System.out.println(this.toString());
		domainEvent.update(new StockUpdateEvent(this));		
	}
	
	public Map<String, Report> getReports() {
		return reports;
	}


	public void setReports(Map<String, Report> reports) {
		this.reports = reports;
	}


	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}


	public Organization getOrganization() {
		return organization;
	}


	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	
	public String getStockNo() {
		return stockNo;
	}


	@Override
	public String toString() {
		return "Stock [stockNo=" + stockNo + ", organization=" + organization
				+ ", reports=" + reports + "]";
	}
}
