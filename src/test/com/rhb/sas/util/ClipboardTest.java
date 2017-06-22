package test.com.rhb.sas.util;

import java.util.Timer;
import java.util.TimerTask;

import com.rhb.sas.util.ClipboardPicker;

public class ClipboardTest extends TimerTask{

	String page_o = null;
	@Override
	public void run() {
		String page = ClipboardPicker.getSystemClipboard();
		if(!page.equals(page_o)){
			page_o = page;
			System.out.println("----------------");
			String[] lines = page.split("\n");
			System.out.println("thare are " + lines.length + " lines.");
			for(String line : lines){
				String[] cells = line.split("\t");
				for(String cell : cells){
					System.out.print(cell + ",");
				}
				System.out.println("");
			}
		}
	}
	
	
	public static void main(String[] args){
		Timer timer = new Timer();
		ClipboardTest ck = new ClipboardTest();
		timer.schedule(ck, 0, 1*1000);
	}
	
	
}
