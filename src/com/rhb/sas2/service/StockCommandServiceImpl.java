package com.rhb.sas2.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jdon.annotation.Service;
import com.jdon.domain.dci.RoleAssigner;
import com.rhb.sas2.domain.Organization;
import com.rhb.sas2.domain.Stock;
import com.rhb.sas2.domain.event.StockUpdateEvent;
import com.rhb.sas2.repository.StockCrepository;
import com.rhb.sas2.repository.StockQrepository;
import com.rhb.sas2.util.PojoMapper;

@Service("stockService")
public class StockCommandServiceImpl implements StockCommandService {
	private final StockCrepository stockCrepository;
	
	public StockCommandServiceImpl(StockCrepository stockCrepository){
		super();
		this.stockCrepository = stockCrepository;
	}
	
	public void create(String stockNo){
		System.out.println("StockServiceImpl.create...");
		stockCrepository.create(stockNo);
		
	}

	public void rename(String stockNo, String stockName){
		System.out.println("StockServiceImpl.rename...");
		System.out.println("get stock from ...");
		Stock stock = stockCrepository.getStock(stockNo);
		if(stock != null){
			System.out.println("finded a stock of " + stock.toString());				
			stock.rename(stockName);
		}else{
			System.out.println("can not find stock of " + "'" + stockNo + "'");
		}
	}

	@Override
	public void update(String stockNo, Organization organization) {
		System.out.println("StockServiceImpl.update organization...");
		System.out.println("get stock from ...");
		Stock stock = stockCrepository.getStock(stockNo);
		if(stock != null){
			stock.update(organization);
			try {
				String pojoAsString = PojoMapper.toJson(stock, true);
				System.out.println(pojoAsString);
				
				System.out.println("------");
				
				Stock org2 = (Stock)PojoMapper.fromJson(pojoAsString, Stock.class);
				System.out.println(org2);
				
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}else{
			System.out.println("failed. Can not find stock of " + "'" + stockNo + "'");
		}
	}
}
