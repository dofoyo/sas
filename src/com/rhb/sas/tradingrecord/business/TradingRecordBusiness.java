package com.rhb.sas.tradingrecord.business;
import java.util.List;

import com.rhb.af.bean.BaseQuery;
import com.rhb.af.bean.Page;
import com.rhb.af.exception.*;
import com.rhb.sas.tradingrecord.bean.*;

public interface TradingRecordBusiness {
	public void save(TradingRecord obj) throws DuplicateException,RequiredException,OutOfRangeException;
	public void delete(TradingRecord obj) throws CanNotBeDeletedException;
	public Page getReport(BaseQuery query);

	
}
