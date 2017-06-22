package com.rhb.sas.interfaces.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import com.rhb.sas.interfaces.Client_DownloadReport;
import com.rhb.sas.util.Tools;

import java.io.*;
import java.util.*;

public class DownloadReportServlet  extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=GB2312";
  private Client_DownloadReport client = null;
  private Thread thread = null;
  
  //Initialize global variables
  public void init() throws ServletException {
	  client = new Client_DownloadReport();  
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
    String stockNo = request.getParameter("stockNo");
    String issueDate_begin=request.getParameter("issueDate_begin");
    String issueDate_end=request.getParameter("issueDate_end");
    String reportDate=request.getParameter("reportDate");
    String owrite = request.getParameter("owrite");

   // System.out.println("task=" + task + ",index=" + index + ",stockNo=" + stockNo + ",issueDate_begin=" + issueDate_begin + ",issueDate_end=" + issueDate_end + ",reportDate=" + reportDate + ",owrite=" + owrite);
    
    //返回的xml字符串
    StringBuffer res = new StringBuffer();
    res.append("<msg>");

    boolean flag = true;
    if (task.equals("create")){
		//System.out.println("create");
    	try{
    		if(isEmpty(stockNo) && (isEmpty(issueDate_begin) || isEmpty(issueDate_end)) && isEmpty(reportDate)){
        		flag = false;
        		res.append("All of stockNo, reportDate, issueDate_begin and issueDate_end can NOT all be null.\n");
        		res.append("******** over ********");
        		System.out.println("All of stockNo, issueDate_begin and issueDate_end can NOT be null.");
        	}else if(!isEmpty(reportDate) && !isBeforeToday(reportDate)){
        		flag = false;
        		res.append("report must be before today.\n");
        		res.append("******** over ********");  
        		System.out.println("report must be before today.");
        	}else if(!isEmpty(reportDate) && !isReportDate(reportDate)){
        		flag = false;
        		res.append("report must be one of follow list: xxxx-03-31, xxxx-06-30, xxxx-09-30, xxxx-12-31.\n");
        		res.append("******** over ********");    	
        		System.out.println("report must be one of follow list: xxxx-03-31, xxxx-06-30, xxxx-09-30, xxxx-12-31.");
        	}else{
        		res.append("***  create ****");
        	}
    	}catch(Exception e){
			res.append(" **** error ***** index="+ index);
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

	//System.out.println(" *********88************ ");
    
    PrintWriter out = response.getWriter();
    response.setContentType("text/xml");
    response.setHeader("Cache-Control","no-cache");
    out.println("<response>");
    out.println(res.toString());
    out.println("</response>");
    out.close();
    
    
	if(flag && task.equals("create") && (thread==null || !thread.isAlive())){
		//System.out.println("thread.start()");
		client.setStockNo(request.getParameter("stockNo"));
		client.setIssueDate_begin(Tools.getDate(request.getParameter("issueDate_begin")));
		client.setIssueDate_end(Tools.getDate(request.getParameter("issueDate_end")));
		client.setReportDate(Tools.getDate(request.getParameter("reportDate")));
		client.setOverwrite(Boolean.parseBoolean(request.getParameter("owrite")));
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
  
  private boolean isEmpty(String str){
	  return str==null || str.trim().length()==0;
  }
  
  private boolean isBeforeToday(String date){
	  Date d = Tools.getDate(date);
	  if(d==null){
		  return false;
	  }else{
		  return Tools.getDate(date).before(new Date());
	  }
  }
  
  private boolean isReportDate(String reportDate){
	  if(isEmpty(reportDate)){
		  return true;
	  }
	  
	  Date d = Tools.getDate(reportDate, "yyyy-MM-dd");
	  if(d == null){
		  return false;
	  }
	  
	  boolean flag = false;
	  String md = Tools.getDate(d, "MM-dd");
	  if("03-31".equals(md)){
		  flag = true;
	  }else if("06-30".equals(md)){
		  flag = true;
	  }else if("09-30".equals(md)){
		  flag = true;
	  }else if("12-31".equals(md)){
		  flag = true;			  
	  }
	  return flag;
	  
  }
}


