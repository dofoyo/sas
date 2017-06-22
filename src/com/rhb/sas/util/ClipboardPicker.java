package com.rhb.sas.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Observable;
import java.util.TimerTask;

public class ClipboardPicker {

	public static String getSystemClipboard(){//��ȡϵͳ���а���ı�����[���ϵͳ���а帴�Ƶ��������ı�]
		Clipboard sysClb=null;
		sysClb = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = sysClb.getContents(null);
		//Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);  //���������д���һ��
		try { 
			if (null != t && t.isDataFlavorSupported(DataFlavor.stringFlavor)) { 
			String text = (String)t.getTransferData(DataFlavor.stringFlavor); 
			return text; 
			} 
		} catch (UnsupportedFlavorException e) {
			//System.out.println("Error tip: "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 	//System.out.println("Error tip: "+e.getMessage());
		
		return null; 
	}
	
	

	
}
