package com.rhb.sas2.repository.dao;

import com.jdon.annotation.Component;
import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import com.rhb.sas2.domain.Stock;
import com.rhb.sas2.repository.StockCrepository;
import com.rhb.sas2.util.PojoMapper;

@Component
@Introduce("modelCache")
public class StockCrepository_Mongodb implements StockCrepository{

	private Mongo mg;
	private DB db;
	private DBCollection stocks;
	
	@Override
	public void update(Stock stock) {
		System.out.println("StockMongodb.update...");
		save(stock);
	}
	
	@Override
	public void create(String stockNo) {
		System.out.println("StockMongodb.create...");
		Stock stock = this.getStock(stockNo);
		if(stock == null){
			stock = new Stock();
			stock.setStockNo(stockNo);
			save(stock);
		}else{
			System.out.println("'" + stockNo + "' has already exists!");
		}
	}
	
	private void save(Stock stock) {
		System.out.println("StockMongodb.save...");
		try {
			String json = PojoMapper.toJson(stock, true);
			System.out.println("json = " + json);
			DBObject stockObj = (DBObject)JSON.parse(json);
		    stockObj.put("_id", stock.getStockNo());

			mg = new Mongo();
			db = mg.getDB("sas");
			stocks = db.getCollection("stocks");
			stocks.save(stockObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(mg != null) mg.close();
			stocks = null;
			db = null;
			mg = null;
		}
	}
	
	@Override
	public void rename(String stockNo, String stockName) {
		System.out.println("StockMongodb.rename...");
		Stock stock = this.getStock(stockNo);
		if(stock != null){
			stock.getOrganization().setName(stockName);
			save(stock);
		}
	}

	@Override
	public void delete(String stockNo) {
		System.out.println("StockMongodb.delete...");

		try {
			mg = new Mongo();
			db = mg.getDB("sas");
			stocks = db.getCollection("stocks");
			
			DBObject query = new BasicDBObject();
			query.put("_id", stockNo);

			DBObject stockObj = stocks.findOne(query);
			if(stockObj != null){
				stocks.remove(stockObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(mg != null) mg.close();
			stocks = null;
			db = null;
			mg = null;
		}
	}
	
	@Around
	@Override
	public Stock getStock(String stockNo) {
		
		System.out.println("StockMongodb.getStock from DB");
		Stock stock = null;
		try {
			mg = new Mongo();
			db = mg.getDB("sas");
			stocks = db.getCollection("stocks");
			
			DBCursor cur = stocks.find();
			while(cur.hasNext()){
				System.out.println(cur.next());
			}
			
			DBObject query = new BasicDBObject();
			query.put("_id", stockNo);

			DBObject stockObj = stocks.findOne(query);
			if(stockObj != null){
				String s = JSON.serialize(stockObj);
				stock = (Stock)PojoMapper.fromJson(s, Stock.class);
				//stock = (Stock)getObject((byte[])stockObj.get("stock"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(mg != null) mg.close();
			stocks = null;
			db = null;
			mg = null;
		}
		
		return stock;
	}

}
