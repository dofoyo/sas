package com.rhb.sas.application.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Console implements Observer {
	List<String> messages = new ArrayList();
	private boolean syso = false;
	
	public List<String> getMessages(){
		return messages;
	}
	
	public boolean isSyso() {
		return syso;
	}

	public void setSyso(boolean syso) {
		this.syso = syso;
	}

	public Console(Sensor s){
		s.addObserver(this);
	}
	
	@Override
	public void update(Observable sensor, Object obj) {
		if(syso) System.out.println("[console]" + ((Sensor)sensor).getMessage());
		messages.add(((Sensor)sensor).getMessage());
	}
}
