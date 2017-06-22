package com.rhb.sas.report.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rhb.af.dao.GeneralDAO;
import com.rhb.af.exception.*;
import com.rhb.af.util.UID;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.report.bean.*;
import com.rhb.sas.util.Tools;


public class ReportBusiness_Impl implements ReportBusiness {
	static Logger logger = Logger.getLogger(ReportBusiness_Impl.class);
	
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

	public String save(Report ex)  throws DuplicateException,RequiredException,OutOfRangeException{
		String required = ex.requiredValidate();
		if(!"".equals(required)){
			throw new RequiredException(required);
		}
		String outOfRange = ex.outOfRangeValidate();
		if(!"".equals(outOfRange)){
			throw new OutOfRangeException(outOfRange);
		}
		
		String pk = ex.getReportPk();
		if(pk==null || "".equals(pk)){
			pk = UID.getUID();
			ex.setReportPk(pk);
			this.create(ex);
		}else{	
			this.update(ex);
		}
		
		return pk;
	}

	public void create(Report ex) throws DuplicateException,RequiredException,OutOfRangeException{
			this.generalDAO.create(ex);
	}

	public void update(Report ex)  throws DuplicateException,RequiredException,OutOfRangeException{
			this.generalDAO.update(ex);
	}
	
	public void caculateEarningsGrowthRatio(String[] stockNos){
		for(int i=0; i<stockNos.length; i++){
			System.out.println(Integer.toString(i+1) + "/" + stockNos.length);
			caculateEarningsGrowthRatio(stockNos[i]);
		}
	}
	
	public void caculateEarningsGrowthRatio(String stockNo){
		sensor.setMessage("caculateEarningsGrowthRatio: " + stockNo);
		
		List<Report> reports = getAnnualReports(stockNo);
		estimateDepreciationAssets(reports);
		
		double a = 0.0;
		double b = 0.0;
		double c = 0.0;
		
		//计算平均收益 earningsAvg
		for(int i=0; i<reports.size(); i++){
				//System.out.println(((Report)reports.get(i)).getTheYear() + ((Report)reports.get(i)).getTheMonth() + ": "+ ((Report)reports.get(i)).getEarnings()/100000000);
				if(i == 0){
					((Report)reports.get(i)).setEarningsAvg(((Report)reports.get(i)).getEarnings());				
				}else if(i == 1){
					a = ((Report)reports.get(i-1)).getEarnings();
					b = ((Report)reports.get(i)).getEarnings();
					((Report)reports.get(i)).setEarningsAvg((a+b)/2);				
				}else if(i > 1){
					a = ((Report)reports.get(i-2)).getEarnings();
					b = ((Report)reports.get(i-1)).getEarnings();
					c = ((Report)reports.get(i)).getEarnings();
					((Report)reports.get(i)).setEarningsAvg((a+b+c)/3);				
				}
				
				//System.out.println("a=" + a + ",b=" + b  + ",c=" + c );
		}
		
		//计算收益增长率和现金增长率 earningsGrowthRatio, shareholders
		for(int i=0; i<reports.size(); i++){
				if(i>0 && ((Report)reports.get(i-1)).getEarningsAvg()!=null){
					//计算收益增长率
					a = ((Report)reports.get(i-1)).getEarningsAvg();  //
					b = ((Report)reports.get(i)).getEarningsAvg();  //  如果a是2007年的，b就是2008年
					c = this.getRatio(a, b);
					((Report)reports.get(i)).setEarningsGrowthRatio(c);
					
					//计算现金增长率
					a = ((Report)reports.get(i-1)).getNetCashFlow();  //
					b = ((Report)reports.get(i)).getNetCashFlow();  //  如果a是2007年的，b就是2008年
					c = this.getRatio(a, b);
					((Report)reports.get(i)).setShareholders(c);
				}
		}
		
		//save
		for(Report report : reports){
			try {
				//System.out.println(report.getProfitStatement());
				this.update(report);
			} catch (Exception e) {
				sensor.setMessage("********** error **********");
				sensor.setMessage(e.toString());
				e.printStackTrace();
			}
		}
		
		
	}
	
	//b相对于a的增长率
	private double getRatio(double a, double b){
		double ratio = 0.00;
		if(a>0 && b>0){
			ratio = (b-a)/a;
		}else if(a<0 && b<0){
			double c = -1 * a;
			a = -1 * b;
			b = c;
			ratio = (b-a)/a;
		}else if(a<0 && b>0){
			double c = -1 * a;
			ratio = (b-a)/c;
		}else if(a>0 && b<0){
			ratio = (b-a)/a;
		}
		return ratio;
	}
	
	
	/**
	 * 1季度和3季度的年报中DepreciationAssets=0，即没有固定资产折旧
	 * 这时要根据上一年的年报进行估计
	 */
	private void estimateDepreciationAssets(List reports){
		if(reports==null || reports.size()<2) return;
		
		int size = reports.size();
		
		Report report = (Report)reports.get(size-1);
		Report before = (Report)reports.get(size-2);
		
		if("03".equals(report.getTheMonth())){
			report.setDepreciationAssets(before.getDepreciationAssets()/4);
		}else if("09".equals(report.getTheMonth())){
			report.setDepreciationAssets(before.getDepreciationAssets()/4 * 3);
		}
	}

	
	//获得年报，包括最后一期的季报
	public List getAnnualReports(String stockNo){
		ReportQuery rq = new ReportQuery();
		rq.empty();
		rq.setStockNo(stockNo);
		rq.setStart(0);
		rq.setCount(100);
		rq.setPeriodType("期末");
		rq.setTheMonth("12");
		
		List reports = generalDAO.findByQuery(rq);
		
		Object obj = this.getLatestQuarterReport(stockNo); //年报没出时得到最新的一个季度报, 如果已有年报就不用季报了
		if(obj!=null){
			reports.add(obj);
		}
		
		Collections.sort(reports, new Comparator(){
			public int compare(Object object1, Object object2){
				Date date1 = ((Report)object1).getEndDate();
				Date date2 = ((Report)object2).getEndDate();
				return date1.compareTo(date2);
			}
		});
		
		return reports;
	}
	
	

	private Object getLatestQuarterReport(String stockNo){
		Date endDate = getMaxEndDate(stockNo);
		//System.out.println("endDate = " + endDate);
		if(endDate == null || "12".equals(Tools.getDate(endDate, "MM"))) return null;
		
		ReportQuery rq = new ReportQuery();
		rq.empty();
		rq.setStockNo(stockNo);
		rq.setStart(0);
		rq.setCount(100);
		rq.setPeriodType("期末");
		rq.setTheMonth(Tools.getDate(endDate,"MM"));
		rq.setTheYear(Tools.getDate(endDate, "yyyy"));
		
		List reports = generalDAO.findByQuery(rq);
		if(reports!=null && reports.size()>0){
			return reports.get(0);
		}else{
			return null;
		}
	}
	
	private java.util.Date getMaxEndDate(String stockNo){
		String sql = "select endDate from report where stockNo='"+stockNo+"' order by endDate desc";
		List<Object[]> list = generalDAO.findBySql(sql, 0, 1);
		//System.out.println(list);
		if(list!=null && list.size()>0){
			Object obj = list.get(0);
			return (java.util.Date)obj;
		}else{
			return null;
		}
	}

	@Override
	public List<String> getNotReportedStockNo(Date reportDate) {
		List<String> stockNos = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select stockNo from stock where stockNo not in(select stockNo from report where theyear='");
		sql.append(Tools.getDate(reportDate, "yyyy"));
		sql.append("' and theMonth='");
		sql.append(Tools.getDate(reportDate, "MM"));
		sql.append("')");
		List<Object[]> list = generalDAO.findBySql(sql.toString(), 0, 10000);
		System.out.println(sql.toString());
		if(list!= null && list.size()!=0){
			for(int i=0; i<list.size(); i++){
				Object obj = list.get(i);
				//System.out.println(obj.toString());
				stockNos.add(obj.toString());
			}			
		}
		return stockNos;
	}

	@Override
	public void rebuildReport() {
		List<String> stockNos = getStockNos();
//		List<String> stockNos = new ArrayList();
//		stockNos.add("000651");
		int i=1;
		for(String stockNo : stockNos){
			System.out.println(i++ + "/" + stockNos.size());
			long t1 = System.currentTimeMillis();
			Map<String,Report> reports = rebuildReport(getReports(stockNo));
			for(String key : reports.keySet()){
				try {
					this.save(reports.get(key));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			long t2 = System.currentTimeMillis();
			System.out.println("  done. " + getTimeString(t1,t2,stockNos.size()-i));
			
		}
		System.out.println("******** rebuild report over ********");
	}
	
	private List<String> getStockNos(){
		StringBuffer sql = new StringBuffer();
		sql.append("select stockNo from stock");
		List<Object[]> list = generalDAO.findBySql(sql.toString(), 0, 10000);

		List<String> stockNos = new ArrayList();
		if(list!= null && list.size()!=0){
			for(int i=0; i<list.size(); i++){
				Object obj = list.get(i);
				//System.out.println(obj.toString());
				stockNos.add(obj.toString());
			}			
		}
		return stockNos;
	}
	
	private List<Report> getReports(String stockNo){
		ReportQuery rq = new ReportQuery();
		rq.empty();
		rq.setStockNo(stockNo);
		rq.setStart(0);
		rq.setCount(10000);
		List list = generalDAO.findByQuery(rq);

		List<Report> reports = new ArrayList();
		if(list!= null && list.size()!=0){
			for(int i=0; i<list.size(); i++){
				Object obj = list.get(i);
				//System.out.println(obj.toString());
				reports.add((Report)obj);
			}			
		}

		return reports;
	}
	
	private Map<String,Report> rebuildReport(List<Report> reports){
		System.out.println("before rebuild: reports.size() = " + reports.size());
		Map<String,Report> mReports = new HashMap<String, Report>();
		for(Report report : reports){
			if(!mReports.containsKey(report.getDescription())){
				report.setStockName(report.getStockNo());
				mReports.put(report.getDescription(), report);
				
			}else{
				Report mReport =mReports.get(report.getDescription());
				mReport.updateByInput(report);
			}
		}
		System.out.println("after rebuild: reports.size() = " + mReports.size());
		return mReports;
	}

	private String getTimeString(long t1, long t2, int total){
		
		  Calendar c = Calendar.getInstance();  
	        c.setTimeInMillis(t2 - t1);
	        
	        Calendar c2 = Calendar.getInstance();  
	        c2.setTimeInMillis((t2 - t1)*total);
	        
	        return ("耗时: " 
	        		+ c.get(Calendar.MINUTE) + "分 "  
	                + c.get(Calendar.SECOND) + "秒 " 
	        		+ c.get(Calendar.MILLISECOND) + " 毫秒."
	        		+ " 还需 : "
	        		+ c2.get(Calendar.MINUTE) + "分 "  
	                + c2.get(Calendar.SECOND) + "秒 " 
	        		+ c2.get(Calendar.MILLISECOND) + " 毫秒."
	        		);  

	}
}
