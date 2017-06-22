package com.rhb.sas.util;

import java.util.ArrayList;
import java.util.List;

public class Download {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Download down = new Download();
		try {
			down.doIt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doIt() throws Exception{
		HttpBrowser hb = new HttpBrowser();
		String url = "http://www.siyuetian.net/xianjing/ShowNew.asp?page=";
		String regexp = "]<a class=\"listA\" href=\"|\" title=.*? target=\"_self\">.*?</a></td>";
		List<String> urls = new ArrayList();
		for(int i=9; i>0; i--){
			System.out.println("pageNo = " + i);
			urls.addAll(Tools.subStrings(hb.Browser(url+Integer.toString(i)), regexp));
		}
		
		for(int i=0; i<urls.size(); i++){
			System.out.println(Integer.toString(i) + " - " + urls.get(i));
			writeToFile(hb,"http://www.siyuetian.net/" + urls.get(i));
		}
		
	}
	
	private void writeToFile(HttpBrowser hb,String url) throws Exception{
		String path="C:\\Downloads\\d\\ÆåÆ×\\²¼¾ÖÏÝÚå";
		String regexp = "<div id=\"dhtmlxq_.*?\" style=\"display:none;\">|ÆåÆ×ÓÉ";
		String regexp1 = "<DIV id=dhtmlxq_.*? style=\"DISPLAY: none\">|ÆåÆ×ÓÉ";
		String str = Tools.subString(hb.Browser(url), regexp);
		if(str == null){
			str = Tools.subString(hb.Browser(url), regexp1);
		}
		String fileName = Tools.subString(str,"[DhtmlXQ_title]","[/DhtmlXQ_title]" );
		
		//System.out.println(str);
		//System.out.println("fileName = " + fileName);
		
		FileUtil.writeTextFile(path + "\\\\" + fileName + ".html", str, true);
	}

}
