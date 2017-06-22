package com.rhb.sas.report.profitstatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rhb.af.dao.GeneralDAO;
import com.rhb.af.exception.DuplicateException;
import com.rhb.af.exception.OutOfRangeException;
import com.rhb.af.exception.RequiredException;
import com.rhb.af.util.UID;
import com.rhb.sas.application.console.Sensor;

public class ProfitStatementBusinessImpl implements ProfitStatementBusiness {
	private GeneralDAO generalDAO;
	private Sensor sensor;
	public GeneralDAO getGeneralDAO() {
		return generalDAO;
	}

	public void setGeneralDAO(GeneralDAO generalDAO) {
		this.generalDAO = generalDAO;
	}
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	@Override
	public String save(ProfitStatement ps) throws DuplicateException,
			RequiredException, OutOfRangeException {
		String required = ps.requiredValidate();
		if(!"".equals(required)){
			throw new RequiredException(required);
		}
		String outOfRange = ps.outOfRangeValidate();
		if(!"".equals(outOfRange)){
			throw new OutOfRangeException(outOfRange);
		}
		
		String pk = ps.getStatementPk();
		if(pk==null || "".equals(pk)){
			pk = UID.getUID();
			ps.setStatementPk(pk);
			this.generalDAO.create(ps);
		}else{	
			this.generalDAO.update(ps);
		}
		//System.out.println("########### pk = " + pk);

		return pk;
	}

	@Override
	public Map<String,ProfitStatement[]> getQuarters(String stockNo, String theMonth, String beginYear, String endYear) {
		int by = Integer.parseInt(beginYear);
		int ey = Integer.parseInt(endYear);
		int i = ey - by + 1;
		
		Map m = new HashMap();
		
		ProfitStatementQuery psq = new ProfitStatementQuery();
		psq.setOrderBy("theYear desc");
		psq.setTheMonth(theMonth);
		if(stockNo==null){
			psq.setCount(1000000);
		}else{
			psq.setStockNo(stockNo);
		}
		List l = this.generalDAO.findByQuery(psq);
		for(Object obj : l){
			ProfitStatement ps = (ProfitStatement)obj;
			int j = Integer.parseInt(ps.getTheYear());
			if(j<=ey && j>=by){
				if(m.containsKey(ps.getStockNo())){
					ProfitStatement[] pss = (ProfitStatement[])m.get(ps.getStockNo()); 
					pss[ey-j] = ps;
				}else{
					ProfitStatement[] pss = new ProfitStatement[i];
					pss[ey-j] = ps;
					m.put(ps.getStockNo(), pss);
				}
			}
		}
		
		return m;
	}
	
	

}
