package com.rhb.sas.report.profitstatement;

import java.util.Map;

import com.rhb.af.exception.DuplicateException;
import com.rhb.af.exception.OutOfRangeException;
import com.rhb.af.exception.RequiredException;

public interface ProfitStatementBusiness {
	public String save(ProfitStatement obj) throws DuplicateException,RequiredException,OutOfRangeException;
	public Map<String,ProfitStatement[]> getQuarters(String stockNo, String theMonth, String beginYear, String endYear);
	

}
