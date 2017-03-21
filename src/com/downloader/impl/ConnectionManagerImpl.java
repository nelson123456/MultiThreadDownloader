package com.downloader.impl;

import com.downloader.api.Connection;
import com.downloader.api.ConnectionManager;

public class ConnectionManagerImpl implements ConnectionManager{

	@Override
	public Connection open(String url) {
		Connection conn = new ConnectionImpl(url);
		return conn;
	}

}
