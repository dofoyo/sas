package com.rhb.sas.tradingrecord.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.rhb.af.bean.BaseQuery;
import com.rhb.af.bean.Page;
import com.rhb.af.dao.GeneralDAO;
import com.rhb.af.exception.*;
import com.rhb.af.util.ListComparator;
import com.rhb.af.util.UID;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.tradingrecord.bean.*;

import com.rhb.sas.tradingrecord.bean.TradingRecord;

public class TradingRecordBusiness_Impl implements TradingRecordBusiness {
	static Logger logger = Logger.getLogger(TradingRecordBusiness_Impl.class);
	
	private GeneralDAO generalDAO;
	private Sensor sensor;
	
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	public GeneralDAO getGeneralDAO() {
		return generalDAO;
	}

	public void setGeneralDAO(GeneralDAO generalDAO) {
		this.generalDAO = generalDAO;
	}
	
	@Override
	public void delete(TradingRecord ex) throws CanNotBeDeletedException{
		this.generalDAO.delete(ex);
	}
	
	@Override
	public void save(TradingRecord ex)  throws DuplicateException,RequiredException,OutOfRangeException{
		String required = ex.requiredValidate();
		if(!"".equals(required)){
			throw new RequiredException(required);
		}
		String outOfRange = ex.outOfRangeValidate();
		if(!"".equals(outOfRange)){
			throw new OutOfRangeException(outOfRange);
		}
		
		String pk = ex.getRecordPk();
		if(pk==null || "".equals(pk)){
			pk = UID.getUID();
			ex.setRecordPk(pk);
			this.generalDAO.create(ex);
		}else{	
			this.generalDAO.update(ex);
		}
	}


	public List findByQuery(TradingRecordQuery qe, int start, int count) {	
		qe.setStart(start);
		qe.setCount(count);
		return this.generalDAO.findByQuery(qe);
	}
	
	public int getAllCount_findByQuery(TradingRecordQuery qe) {	
		return this.generalDAO.countByQuery(qe);
	}

	@Override
	public Page getReport(BaseQuery query) {
		List<TradingProfit> report = new ArrayList<TradingProfit>();
		StringBuffer sql = new StringBuffer();
		sql.append("select s.stockNo,s.stockName,t.quantity,s.pricePerShare,t.total from stock s inner join tradingrecord t on s.stockNo=t.stockNo");
		
		//System.out.println(sql.toString());
		
		List<Object[]> list = generalDAO.findBySql(sql.toString(), 0, 10000);
		
		HashMap<String,TradingProfit> map = new HashMap<String, TradingProfit>();
		if(list!= null && list.size()!=0){
			for(Object[] obj : list){
				map = putin(map,obj);
			}			
		}
		
		TradingProfit tp = null;
		Iterator<String> i = map.keySet().iterator();
		while(i.hasNext()){
			String s = (String)i.next();
			tp = map.get(s);
			tp.setMarketValue(tp.getQuantity() * tp.getUnitprice());
			tp.setProfit(tp.getMarketValue() + tp.getProfit());
			report.add(tp);
		}
		
		Page page = new Page(report.size(),0,report.size(),report);
		
		return page;
	}
	
	private HashMap<String,TradingProfit> putin(HashMap<String,TradingProfit> hh,Object[] obj){
		TradingProfit tp = hh.get((String)obj[0]);
		if(tp == null){
			tp = new TradingProfit();
			tp.setStockNo((String)obj[0]);
			tp.setStockName((String)obj[1]);
			tp.setUnitprice((Double)obj[3]);
		}
		
		tp.setQuantity(tp.getQuantity() + (Integer)obj[2]);
		tp.setProfit(tp.getProfit() + (Double)obj[4]);
		
		hh.put(tp.getStockNo(), tp);

		return hh;
	}
	
}

