package com.rhb.sas.evaluate.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.rhb.sas.firm.Firm;
import com.rhb.sas.util.Tools;


/**
 * 过滤掉4月30日后上市的新股
 * 	
 * @author rhb
 *
 */

public class Filter_NoNew implements Filter {
	Map<Integer,List<Firm>> map;
	
	public Filter_NoNew(Map<Integer,List<Firm>> map){
		this.map = map;
	}

	@Override
	public Map<Integer,List<Firm>> result() {
		Map<Integer,List<Firm>> oks = new TreeMap();
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			int year = (Integer)it.next();
			List<Firm> firms_begin = map.get(year);
			List<Firm> firms_end = new ArrayList();
			for(Firm firm : firms_begin){
				Date operateDate = Tools.getDate(Integer.toString(year+1) + "-4-30");
				Date ipoDate = firm.getStock().getMadeDate(); 
				if(operateDate.compareTo(ipoDate)!=-1){
					firms_end.add(firm);
				}else{
					System.out.println("kick off " + firm.getStockInfo() + " for operateDate(" + Tools.getDate(operateDate, "yyyy-MM-dd") + ")<ipoDate(" + Tools.getDate(ipoDate, "yyyy-MM-dd") + ")");
				}
			}
			oks.put(year, firms_end);
		}
		
		return oks;
		
		
	}
	

}
