package com.downloader.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.downloader.api.Connection;

public class ConnectionImpl implements Connection{
	
	private String url;
	private URL urlObj;
	private static final int BUFFER_SIZE = 1024;
	
	public ConnectionImpl(String url) {
		this.url = url;
		try {
			urlObj = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] read(int start, int end) {
		try {
			HttpURLConnection httpConn = (HttpURLConnection)urlObj.openConnection();
			
			httpConn.setRequestProperty("Range", "bytes=" + start + "-" + end);
			httpConn.setAllowUserInteraction(true);
			
			int totalLen = end - start + 1;
			InputStream is = httpConn.getInputStream();	
			byte[] buff = new byte[BUFFER_SIZE];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			while(baos.size() < totalLen){
				int len = is.read(buff);
				if(len < 0){
					break;
				}
				baos.write(buff, 0, len);
			}
			//System.out.println(Thread.currentThread().getId() +"=====" + baos.size() + "===" + totalLen);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getLength() {
		try {
			URLConnection urlConn = urlObj.openConnection();
			int len = urlConn.getContentLength();
			return len;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void close() {
		
	}

}
