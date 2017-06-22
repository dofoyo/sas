package com.rhb.sas.download.yjyg;

public class YjygDTO {
	private String stockNo;
	private String stockName;
	private String yglx;  
	private String bdfd; 
	
	public YjygDTO(String stockNo, String stockName, String yglx, String bdfd) {
		super();
		this.stockNo = stockNo;
		this.stockName = stockName;
		this.yglx = yglx;
		this.bdfd = bdfd;
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
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(stockNo + ",	" + stockName + ",	" + yglx + ",	" + bdfd);
		return sb.toString();
	}

	public String getYglx() {
		return yglx;
	}

	public void setYglx(String yglx) {
		this.yglx = yglx;
	}

	public String getBdfd() {
		return bdfd;
	}

	public void setBdfd(String bdfd) {
		this.bdfd = bdfd;
	}
	
}
