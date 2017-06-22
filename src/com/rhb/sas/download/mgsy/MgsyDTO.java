package com.rhb.sas.download.mgsy;

public class MgsyDTO {
	private String stockNo;
	private String stockName;
	private String mgsy;
	private String yysr;
	private String yylr;
	
	public MgsyDTO(String stockNo, String stockName, String mgsy, String yysr, String yylr) {
		super();
		this.stockNo = stockNo;
		this.stockName = stockName;
		this.mgsy = mgsy;
		this.yysr = yysr;
		this.yylr = yylr;
	}

	
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getMgsy() {
		return mgsy;
	}
	public void setMgsy(String mgsy) {
		this.mgsy = mgsy;
	}  
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(stockNo + ",	" + stockName + ",	" + mgsy + ", " + yysr + ", " + yylr);
		return sb.toString();
	}


	public String getYysr() {
		return yysr;
	}


	public void setYysr(String yysr) {
		this.yysr = yysr;
	}


	public String getYylr() {
		return yylr;
	}


	public void setYylr(String yylr) {
		this.yylr = yylr;
	}
	
	
}
