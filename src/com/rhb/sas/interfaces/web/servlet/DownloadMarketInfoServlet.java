package com.rhb.sas.interfaces.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import com.rhb.sas.interfaces.Client_DownloadMarketInfo;

import java.io.*;
import java.util.*;

public class DownloadMarketInfoServlet  extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=GBK";
  private Client_DownloadMarketInfo client;  
  private Thread thread = null;
  
  //Initialize global variables
  public void init() throws ServletException {
	  client = new Client_DownloadMarketInfo();  
  }

  //处理get方法
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
	  doIt(request,response);
  }
  
  public void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
  	//信息
  	List messages = client.getMessages();
  
	int all = 3500;  
	  
    //得到任务类型 
    String task = request.getParameter("task");
    String index = request.getParameter("index");
    
    //返回的xml字符串
    StringBuffer res = new StringBuffer();
    res.append("<msg>");

    if (task.equals("create")){
     	res.append("***  create ****");
    }else{
    	try {
    	    if(Integer.parseInt(index)>=0 && Integer.parseInt(index)<messages.size()){
    	    	int size = messages.size();
    	    	for(int i=Integer.parseInt(index); i<size; i++){
            	    res.append("["+ i + "/" + all +"]");
        	    	res.append(messages.get(i) + "\n");    	    		
    	    	}
    	    }else{
    	    	res.append("--");
    	    }
		} catch (Exception e) {
				res.append(" **** error ***** index="+ index);
		}
    }
    res.append("</msg>");
    
    PrintWriter out = response.getWriter();
    response.setContentType("text/xml");
    response.setHeader("Cache-Control","no-cache");
    out.println("<response>");
    out.println(res.toString());
    out.println("</response>");
    out.close();
    
	if(task.equals("create") && (thread==null || !thread.isAlive())){
		thread = getThread();
		thread.start();
	}
  }
  
  private synchronized Thread getThread(){
	  return new Thread(new Runnable(){
		  public void run(){
			  client.getMessages().clear();
			  client.doIt();
		  }
	  }
	  );
  }
  

  //Clean up resources
  public void destroy() {
  }
}


