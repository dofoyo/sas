package com.rhb.sas.interfaces.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import com.rhb.sas.interfaces.Client_DownloadYjyg;

import java.io.*;
import java.util.*;

public class DownloadYjygServlet  extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=GB2312";
  private Client_DownloadYjyg client = null;
  private Thread thread = null;
  
  //Initialize global variables
  public void init() throws ServletException {
	  client = new Client_DownloadYjyg();  
  }

  //处理get方法
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
	  doIt(request,response);
  }
  
  public void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
  	//信息
  	List messages = client.getMessages();
  
	int all = client.getMessageNumber();  
	  
    //得到任务类型等参数 
    String task = request.getParameter("task");
    String index = request.getParameter("index");
    String yearandmonth = request.getParameter("yearandmonth");
    String page=request.getParameter("page");
    int thepage = 1;

    //System.out.println("task=" + task + ",index=" + index + ",stockNo=" + stockNo + ",issueDate_begin=" + issueDate_begin + ",issueDate_end=" + issueDate_end + ",reportDate=" + reportDate);
    
    //返回的xml字符串
    StringBuffer res = new StringBuffer();
    res.append("<msg>");

    boolean flag = true;
    if (task.equals("create")){
       	res.append("***  create ****");
       	try{
       		thepage = Integer.parseInt(page);
       	}catch(Exception e){
			res.append(" the page must be int. ");
			e.printStackTrace();
       	}
    }else{
		//System.out.println("have created");
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
				e.printStackTrace();
		}
		//System.out.println("have created");

    }
    res.append("</msg>");

	System.out.println("**************** " + res.toString());
    
    PrintWriter out = response.getWriter();
    response.setContentType("text/xml");
    response.setHeader("Cache-Control","no-cache");
    out.println("<response>");
    out.println(res.toString());
    out.println("</response>");
    out.close();
    
    
	if(flag && task.equals("create") && (thread==null || !thread.isAlive())){
		//System.out.println("thread.start()");
		client.setThepage(thepage);
		client.setTheyearandmonth(yearandmonth);
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
	  client = null;
	  thread = null;
  }
  
}


