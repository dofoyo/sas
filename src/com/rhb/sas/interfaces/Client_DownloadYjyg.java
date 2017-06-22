package com.rhb.sas.interfaces;

import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.application.console.Console;
import com.rhb.af.util.AppContext;

public class Client_DownloadYjyg {

	private String appContextPath = "com/rhb/sas/AppContext.xml";
	private InterfacesFacade facade = (InterfacesFacade) AppContext.getInstance().getAppContext(appContextPath).getBean("interfacesFacade");
	private Sensor sensor = (Sensor) AppContext.getInstance().getAppContext(appContextPath).getBean("sensor");
	private Console console = (Console) AppContext.getInstance().getAppContext(appContextPath).getBean("console");
	
	private String theyearandmonth;
	private int thepage;
	
	private int messageNumber = 100;

	
	public void doIt(){
		
		console.setSyso(false);
		sensor.setMessage("******* begin ***********");
		
		sensor.setMessage("there are " + thepage*100  + " stocks's yjyg will to be done." );
		
		messageNumber = thepage*100;
		
		sensor.setMessage("download yjyg ....");

		facade.downloadYjyg(theyearandmonth, thepage);

		sensor.setMessage("************* over **********");
	}
	
	
	public List<String> getMessages(){
		return console.getMessages();
	}

	
	
	public int getMessageNumber(){
		return messageNumber;
	}
	
	public void setSyso(boolean flag){
		this.console.setSyso(flag);
	}


	public String getTheyearandmonth() {
		return theyearandmonth;
	}


	public void setTheyearandmonth(String theyearandmonth) {
		this.theyearandmonth = theyearandmonth;
	}


	public int getThepage() {
		return thepage;
	}


	public void setThepage(int thepage) {
		this.thepage = thepage;
	}


	
}
