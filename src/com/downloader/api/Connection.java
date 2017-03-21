package com.downloader.api;


public interface Connection {
	public abstract byte[] read(int start, int end);
	
	public abstract int getLength();
	
	public abstract void close();
}
