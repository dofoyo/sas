package com.rhb.sas.interfaces;

import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.application.console.Console;
import com.rhb.sas.interfaces.downloadmarketinfo.MarketInfoDTO;
import com.rhb.af.util.AppContext;

public class Client_DownloadMarketInfo {

	private String appContextPath = "com/rhb/sas/AppContext.xml";
	private InterfacesFacade facade = (InterfacesFacade) AppContext.getInstance().getAppContext(appContextPath).getBean("interfacesFacade");
	private Sensor sensor = (Sensor) AppContext.getInstance().getAppContext(appContextPath).getBean("sensor");
	private Console console = (Console) AppContext.getInstance().getAppContext(appContextPath).getBean("console");
	

	/**
	 * �õ����µ��г���Ϣ���ɼۡ���ֵ�ȣ�������������
	 * ��������µĹ�Ʊ����׷�Ӹù�Ʊ�������ظù�Ʊ���걨�ͼ���,���Ըù�Ʊ���걨�ͼ������������ʷ���
	 * �������г���Ϣ������Ʊ�ļ�ֵ
	 */
	public void doIt(){
		
		sensor.setMessage("******* begin ***********");
		
		sensor.setMessage("download market date....");
		List<MarketInfoDTO> marketInfs = facade.downloadMarketInfo();
		sensor.setMessage("there are " + marketInfs.size() + " stocks's market data");
		
		sensor.setMessage("save market date....");
		List<String> stockNos = facade.saveMarketInfo(marketInfs);
		if(stockNos.size()>0){
			sensor.setMessage("found "+stockNos.size()+" new stocks.");
			sensor.setMessage("download new stocks' report...");
			facade.downloadReport(stockNos, null, false);
			
//			sensor.setMessage("save new stocks' report...");
//			facade.saveReport(reports);
			
			sensor.setMessage("reCaculate new stock's EarningsGrowthRatio...");
			facade.reCaculateEarningsGrowthRatio(stockNos);
			
			sensor.setMessage("refresh new stock base info from report...");
			facade.refreshStockFromReport(stockNos);
		}
		
		sensor.setMessage("refresh stock's value and discount.....");
		facade.refreshStockValueAndDiscount();
		
		sensor.setMessage("************* over **********");
		
		/*
		for(String str : console.getMessages()){
			System.out.println(str);
		}
		*/
		
	}
	
	public List<String> getMessages(){
		return console.getMessages();
	}
	
	public static void main(String[] args) {
		Client_DownloadMarketInfo client = new Client_DownloadMarketInfo();
		client.doIt();
	}
	
	public void setSyso(boolean flag){
		this.console.setSyso(flag);
	}
	
}
