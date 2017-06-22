package com.rhb.sas2.service;

import com.rhb.sas2.domain.Organization;

public interface StockCommandService {

	public void create(String stockNo);
	
	public void rename(String stockNo, String stockName);
	
	public void update(String stockNo, Organization organziation);
		
}
