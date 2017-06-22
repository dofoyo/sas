package com.rhb.sas.util;

import java.io.BufferedReader;   
import java.io.IOException;   
import java.io.InputStream;   
import java.io.InputStreamReader;   
import java.net.HttpURLConnection;   
import java.net.MalformedURLException;   
import java.net.ProtocolException;
import java.net.URL;   
import java.net.URLConnection;   
import java.util.ArrayList;
import java.util.List;
  
public class HttpBrowser {   

	private static final String USER_AGENT_VALUE = "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)";   
	private static int connectTimeout = 48000;
	private static int readTimeout = 48000;
	
    private String cookie = null;   
    private String encoding = "GBK";
    
    public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String Browser(String url) throws Exception {   
		return this.getMethod(url, false);
    }   

	
	public List<String> Browser(List<String> urls, int ms) throws Exception {   

    	List responses = new ArrayList();
    	for(int i=0; i<urls.size(); i++){
    		String url = urls.get(i);
    		String s = this.getMethod(url, true);
    		//System.out.println(s);
    		responses.add(s);
	        try { Thread.sleep ( ms ) ; 
	        } catch (InterruptedException ie){}        		
    	}
        
        return responses;
 
    }   
  
    /**  
     * Request specifid url with 'GET' method. And return HTTP response content.  
     *   
     * @param url  
     * @return  
     * @throws Exception 
     */  
    private String getMethod(String url, boolean keepCookie) throws Exception {   
        if (url == null || url.length() == 0) {   
            return "Requst url could not be null or empty.";   
        }   
  
        StringBuffer result = new StringBuffer();   
        HttpURLConnection httpURLConnection = this.getHttpURLConnection(url);   
        httpURLConnection.setConnectTimeout(connectTimeout);
        httpURLConnection.setReadTimeout(readTimeout);
  
        // Set request properties.   
        this.settingHttpRequestHeader(httpURLConnection);   
  
        httpURLConnection.setRequestMethod("GET");   
  
        // Getting or setting cookie   
        this.gettingOrSettingCookie(httpURLConnection, keepCookie);   
  
        InputStream httpInputStream = httpURLConnection.getInputStream();   
        BufferedReader httpBufferedReader = new BufferedReader(new InputStreamReader(httpInputStream, encoding));   
        result.append(this.readBufferedContent(httpBufferedReader));   
  
        // Connect to host.   
        httpURLConnection.connect();   
        return result.toString();   
    }   
   
    /**  
     * Setting HTTP request header properties  
     *   
     * @param httpURLConnection  
     */  
    private void settingHttpRequestHeader(HttpURLConnection httpURLConnection) {   
        if (httpURLConnection == null)   
            return;   
        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT_VALUE);   
        // TODO setting some other properties here . . .   
    }   
  
    /**  
     * Get HttpURLConnection by specified url string.  
     *   
     * @param url  
     * @return  
     * @throws IOException  
     */  
    private HttpURLConnection getHttpURLConnection(String url) throws IOException {   
        URL urlObj = new URL(url);   
        URLConnection urlConnection = urlObj.openConnection();   
        if (urlConnection instanceof HttpURLConnection)   
            return (HttpURLConnection) urlConnection;   
        throw new MalformedURLException();   
    }   
  
    /**  
     * Read bufferedReader buffered content.  
     *   
     * @param bufferedReader  
     * @return  
     * @throws IOException 
     */  
    private String readBufferedContent(BufferedReader bufferedReader) throws IOException {   
        if (bufferedReader == null)   
            return null;   
        StringBuffer result = new StringBuffer();   
        String line = null;   
        while ((line = bufferedReader.readLine()) != null) {   
            result.append(line);   
        }   
        return result.toString();   
    }   
  
  
    /**  
     * Get or set cookie.  
     *   
     * @param httpURLConnection  
     * @param keepCookie  
     */  
    private void gettingOrSettingCookie(HttpURLConnection httpURLConnection,boolean keepCookie) {   
        // Getting or setting cookie.   
        if (cookie == null || cookie.length() == 0) {   
            String setCookie = httpURLConnection.getHeaderField("Set-Cookie");   
            if(setCookie!=null){
                cookie = setCookie.substring(0, setCookie.indexOf(";"));               	
            }
        } else if (keepCookie) {   
            httpURLConnection.setRequestProperty("Cookie", cookie);   
        }   
    }   
}  