package com.rhb.sas2.repository.dao;

import com.jdon.annotation.Component;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.rhb.sas2.repository.StockQrepository;

@Component
public class StockQrepository_Mongodb implements StockQrepository{

	private Mongo mg;
	private DB db;
	private DBCollection stocks;
	

}
