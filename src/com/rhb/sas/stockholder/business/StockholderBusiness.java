package com.rhb.sas.stockholder.business;

import com.rhb.af.exception.*;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stockholder.bean.*;

public interface StockholderBusiness {
	public String save(Stockholder obj) throws DuplicateException,RequiredException,OutOfRangeException;
	public void delete(Stockholder obj) throws CanNotBeDeletedException;
	
	public void readFromFile(Stock stock, String path) throws Exception;


}
