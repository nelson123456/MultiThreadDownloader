package com.downloader.api;

public interface ConnectionManager {
	public abstract Connection open(String url);
}
