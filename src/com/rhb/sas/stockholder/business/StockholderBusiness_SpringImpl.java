package com.rhb.sas.stockholder.business;

import java.io.IOException;
import java.util.List;
import com.rhb.af.util.UID;
import com.rhb.af.util.DateTools;
import com.rhb.af.util.Format;
import com.rhb.af.exception.*;
import com.rhb.af.dao.GeneralDAO;
import com.rhb.sas.stock.bean.Stock;
import com.rhb.sas.stockholder.bean.*;
//import com.rhb.sas.stockholder.dao.StockholderDAO;
import com.rhb.sas.util.FileUtil;
import com.rhb.sas.util.Tools;


public class StockholderBusiness_SpringImpl implements StockholderBusiness {
	
	private GeneralDAO generalDAO;
	
	public void delete(Stockholder ex) throws CanNotBeDeletedException{
		this.generalDAO.delete(ex);
	}

	public String save(Stockholder ex)  throws DuplicateException,RequiredException,OutOfRangeException{
		String required = ex.requiredValidate();
		if(!"".equals(required)){
			throw new RequiredException(required);
		}
		String outOfRange = ex.outOfRangeValidate();
		if(!"".equals(outOfRange)){
			throw new OutOfRangeException(outOfRange);
		}
		String pk = ex.getPk();
		if(pk==null || "".equals(pk)){
			return this.create(ex);
		}else{	
			this.update(ex);
			return pk;
		}
	}

	private String create(Stockholder ex) throws DuplicateException,RequiredException,OutOfRangeException{
			String pk = UID.getUID();
		ex.setPk(pk);
				this.generalDAO.create(ex);
		return pk;
	}

	private void update(Stockholder ex)  throws DuplicateException,RequiredException,OutOfRangeException{
						this.generalDAO.update(ex);
	}



	
	public GeneralDAO getGeneralDAO() {
		return generalDAO;
	}

	public void setGeneralDAO(GeneralDAO generalDAO) {
		this.generalDAO = generalDAO;
	}
	
	
	public void readFromFile(Stock stock, String path) throws Exception{
		if(stock == null){
			System.out.println("*******  stock is NULL ******");
			return;			
		}
		String url = path + stock.getStockNo() + "_StockHolder.html";
		String result = FileUtil.readTextFile(url);
		parseStockHolder(result,stock);
		//this.save(obj);
		
		
	}
	
	private void parseStockHolder(String str,Stock stock){
		String findRegexp = "</a><div align=\"center\"><strong>截至日期</strong></div>.*?分割数据的空行";
		String findTR = "<tr.*?</tr>";
		String findEndDate = "<td colspan=\"4\">|</td>";
		String findReportedDate = "<td colspan=\"4\">|</td>";
		String findStockholder1 = "target=\"_blank\">|</a>";
		String findStockholder2 = "<div align=\"center\">|</div>";
		String findHolderNo = "stockholderid=|\" target=\"_blank\"";
		
		String endDate;
		String reportedDate;
		
		List<String> period = Tools.subStrings(str, findRegexp);
		List<String> trs;
		List<String> stockholders1;
		List<String> stockholders2;
		String holderNo;
		long holdingNumber = 0;
		double holdingRatio = 0.0;
		if(period!=null && period.size()>0){
			for(int i=0; i<period.size(); i++){
				trs = Tools.subStrings("<tr><td>" + period.get(i), findTR);
				if(trs!=null && trs.size()>0){
					endDate = Tools.subString(trs.get(0), findEndDate);
					reportedDate = Tools.subString(trs.get(1), findReportedDate);
					for(int j=6; j<trs.size(); j++){
						stockholders1 = Tools.subStrings(trs.get(j), findStockholder1);
						stockholders2 = Tools.subStrings(trs.get(j), findStockholder2);
						holderNo = Tools.subString(trs.get(j), findHolderNo);

						try{
							holdingNumber = Long.parseLong(stockholders1.get(1));
							holdingRatio = Double.parseDouble(stockholders1.get(2));
						}catch(Exception e){}
						
						if(holdingNumber!=0 && holdingRatio!=0.0){
							Stockholder sh = new Stockholder();
							sh.setEndDate(DateTools.getDate(endDate));
							sh.setReportedDate(DateTools.getDate(reportedDate));
							sh.setStockNo(stock.getStockNo());
							sh.setStockName(stock.getStockName());
							sh.setHolderNo(holderNo);
							sh.setHolderName(stockholders1.get(0));
							sh.setHoldingRatio(holdingRatio);
							sh.setHoldingNumber(holdingNumber);
							sh.setHoldingType(stockholders2.get(4));
							
							//System.out.println(sh);
							
							doSave(sh);							
						}
					}
				}
			}
		}else{
			System.out.println("************* nothing finded! **********");
		}
	}
	
	private void doSave(Stockholder sh){
		Object obj = null;
		
		/*
		StockholderQuery shq = new StockholderQuery();
		shq.setStockNo(sh.getStockNo());
		shq.setHolderName(sh.getHolderName());
		shq.setEndDate_begin(sh.getEndDate());
		shq.setEndDate_end(sh.getEndDate());
		
		try{
			//obj = this.generalDAO.findByQuery(shq);
			List<Object> l = this.generalDAO.findByQuery(shq, 0, 10);
			System.out.println("********** l.size() = " + l.size());
		}catch(Exception e){}
		*/
		if(obj==null){
			try {
				this.save(sh);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("just done ");
		}else{
			//System.out.println("has  done ");
		}
	}
	

}
