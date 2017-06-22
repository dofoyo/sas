package com.rhb.sas.util;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownFile {
	private static int BUFFER_SIZE = 8096;

	public void saveToFile(String destUrl, String file) throws IOException{
		
		FileOutputStream fos = new FileOutputStream(file);
		
		URL url = new URL(destUrl);
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
		httpUrl.connect();
		BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
		
		int size = 0;
		byte[] buf = new byte[BUFFER_SIZE];
		int i = 1;
		while((size=bis.read(buf)) != -1){
			fos.write(buf, 0, size);
			System.out.println(i++);
		}
		fos.close();
		bis.close();
		httpUrl.disconnect();
	}
	
}
