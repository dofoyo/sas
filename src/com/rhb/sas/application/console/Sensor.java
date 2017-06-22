package com.rhb.sas.application.console;

import java.util.Observable;

public class Sensor extends Observable {
	private String message = "";
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String str){
		message = str;
		setChanged();
		notifyObservers();
	}
}
