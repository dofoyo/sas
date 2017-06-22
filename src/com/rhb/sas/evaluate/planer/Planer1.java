package com.rhb.sas.evaluate.planer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rhb.sas.firm.Firm;
import com.rhb.sas.util.Tools;


/**
 *  	���й�Ʊƽ�������
	ÿ��4�����һ��������Ϊ�����գ������ڼ䷢��ʲô�������һֱ�ֹɲ�����		
 * @author rhb
 *
 */
public class Planer1 implements Planer {

	Map<Integer,List<Firm>> map = null;
	
	public Planer1(Map<Integer,List<Firm>> map){
		this.map = map;
	}
	
	@Override
	public List<Order> plan() {
		List<Order> orders = new ArrayList();
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			int year = (Integer)it.next();
			//System.out.println("********** " + year + " **********");
			List<Firm> fs = map.get(year);
			int i = fs.size();
			for(Firm firm : fs){
				String stockNo = firm.getStockNo();
				String stockName = firm.getStockName();
				Date date = Tools.getDate(Integer.toString(year+1) + "-4-30");
				int buyorsell = 1;
				int occupation = i--;
				int onlyCash = 1;
				Order order = new Order();
				order.init(stockNo, stockName, date, buyorsell, occupation, onlyCash); //ÿ��4��30������
				orders.add(order);
			}
			
			for(Firm firm : fs){
				String stockNo = firm.getStockNo();
				String stockName = firm.getStockName();
				Date date = Tools.getDate(Integer.toString(year+2) + "-4-30");
				int buyorsell = -1;
				int occupation = 0;
				int onlyCash = 0;
				Order order = new Order();
				order.init(stockNo, stockName, date, buyorsell, occupation, onlyCash); //ÿ��4��30�ջ���ǰ������
				orders.add(order);
			}
		}
		return orders;
	}

}
