package com.rhb.sas.stock.business;
import java.util.List;

import com.rhb.af.exception.*;
import com.rhb.sas.stock.bean.*;

public interface StockBusiness {
	public void save(Stock obj) throws DuplicateException,RequiredException,OutOfRangeException;
	public void delete(Stock obj) throws CanNotBeDeletedException;
	
	public void updateValueAndDiscount();
	public void updateValueAndDiscount(Stock stock);
	public void updateValueAndDiscount(List<Stock> stocks);
	public void updateValueAndDiscount(String stockNo);
	public void updateValueAndDiscount(String[] stockNos);
	
	public void updateFromReports();
	public void updateFromReports(Stock stock);
	public void updateFromReports(List<Stock> stocks);
	public void updateFromReports(String stockNo);
	public void updateFromReports(String[] stockNos);
	
	public void updateStockMainBusiness();
	
}
