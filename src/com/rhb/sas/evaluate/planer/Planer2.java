package com.rhb.sas.evaluate.planer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rhb.af.bean.Page;
import com.rhb.af.business.FindBusiness;
import com.rhb.sas.firm.Firm;
import com.rhb.sas.report.bean.Report;
import com.rhb.sas.report.bean.ReportQuery;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.util.Tools;


/**
 * 	所有股票平均分配金额。
	新上市或新发布年报的股票，只要符合SAS选股条件，即可买入股，不符合了，就卖出
	即：公布年度报告后买入和卖出								

 * @author rhb
 *
 */
public class Planer2 implements Planer {
	
	FindBusiness find = null;
	Map<Integer,List<Firm>> map = null;
	
	public Planer2(Map<Integer,List<Firm>> map, FindBusiness find){
		this.map = map;
		this.find = find;
	}
	
	@Override
	public List<Order> plan() {
		List<Order> orders = new ArrayList();
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			int year = (Integer)it.next();	
			int year_1 = year + 1;
			List<Firm> fs = map.get(year);
			for(Firm firm : fs){
				String stockNo = firm.getStockNo();
				String stockName = firm.getStockName();
				Date date_buy = getReportDate(firm,Integer.toString(year),"12");
				Date date_sell = getReportDate(firm,Integer.toString(year_1),"12");
				Date date_ipo = getIPODate(stockNo);
				
				int year_ipo = Integer.parseInt(Tools.getDate(date_ipo, "yyyy"));
				
				if(year_ipo>year_1){
					System.out.println(stockNo + stockName + "year_ipo=" + year_ipo + ", can NOT operate at " + year_1);
				}else if(year_ipo==year_1){
					System.out.println(stockNo + stockName + "year_ipo=" + year_ipo + ", can operate at " + year_1 + ",buyDate=" + Tools.getDate(date_ipo, "yyyy-MM-dd") + ", sellDate=" + Tools.getDate(date_sell, "yyyy-MM-dd"));
					Order order_buy = new Order();
					order_buy.init(stockNo, stockName, date_ipo, 1, 1, 0); 
					orders.add(order_buy);

					if(date_sell == null){
						System.out.println("can NOT find reportDate," + firm.getStockNo() + firm.getStockName() + ", theyear=" + year_1 + ", themonth=12");
					}
					Order order_sell = new Order();
					order_sell.init(stockNo, stockName, date_sell, -1, 0, 0);
					orders.add(order_sell);
				}else{
					System.out.println(stockNo + stockName + "year_ipo=" + year_ipo + ", can operate at " + year_1 + ",buyDate=" + Tools.getDate(date_buy, "yyyy-MM-dd") + ", sellDate=" + Tools.getDate(date_sell, "yyyy-MM-dd"));

					if(date_buy == null){
						System.out.println("can NOT find reportDate," + firm.getStockNo() + firm.getStockName() + ", theyear=" + year_1 + ", themonth=12");
					}
					Order order_buy = new Order();
					order_buy.init(stockNo, stockName, date_buy, 1, 1, 0); 
					orders.add(order_buy);
					
					if(date_sell == null){
						System.out.println("can NOT find reportDate," + firm.getStockNo() + firm.getStockName() + ", theyear=" + year_1 + ", themonth=12");
					}
					Order order_sell = new Order();
					order_sell.init(stockNo, stockName, date_sell, -1, 0, 0);
					orders.add(order_sell);					
				}
				
			}  
		}
		Collections.sort(orders,new ComparatorOrder());
		return orders;
	}
	
	private Date getIPODate(String stockNo){
		Date date = null;
		
		Stock stock = (Stock)find.findByPK(Stock.class, stockNo);
		if(stock!=null){
			date = stock.getMadeDate();
		}
		
		return date;
	}
	
	private Date getReportDate(Firm firm, String year, String month){
		Date date = null;
		List<Report> reports = firm.getReports();
		for(Report report : reports){
			if(year.equals(report.getTheYear()) && month.equals(report.getTheMonth())){
				date = Tools.getDate(report.getDescription());
				break;
			}
		}
		return date;
	}
	
	class ComparatorOrder implements Comparator{
		public int compare(Object obj1, Object obj2){
			Order order1 = (Order)obj1;
			Order order2 = (Order)obj2;
			if(order1.getDate()==null){
				return -1;
			}else if(order2.getDate()==null){
				return 1;
			}else{
				return order1.getDate().compareTo(order2.getDate());
			}
		}
	}

}
