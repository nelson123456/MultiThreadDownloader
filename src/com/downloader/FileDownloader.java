package com.downloader;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.CyclicBarrier;

import com.downloader.api.Connection;
import com.downloader.api.DownloadListener;
import com.downloader.impl.ConnectionManagerImpl;

//main thread
public class FileDownloader{
	
	private final int THREAD_NUM = 5;
	private ConnectionManagerImpl connMana;
	private String url;
	private String localFile;
	private DownloadListener listener;
	private Connection conn = null;

	public FileDownloader(String url, String fileName){
		this.url = url;
		this.localFile = fileName;
		this.connMana = new ConnectionManagerImpl();
	}

	public void execute(){
		CyclicBarrier barrier = new CyclicBarrier(THREAD_NUM, new Runnable() {
			public void run() {
				listener.notifyFinished();
			}
		});
		
		
		try {
			conn = connMana.open(url);
			int length = conn.getLength();
			RandomAccessFile file = createLocalFile(localFile, length);
			int blockSize = length%THREAD_NUM==0? length/THREAD_NUM : length/THREAD_NUM+1;
			for(int i=0; i<THREAD_NUM; i++){
				int startPoint = i * blockSize;
				int endPoint = startPoint + blockSize - 1;
				DownloadThread thread = new DownloadThread(conn, startPoint, endPoint, localFile, barrier);
				Thread t = new Thread(thread);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn != null){
				conn.close();
			}
		}
		
	}

	private RandomAccessFile createLocalFile(String localFile2, int length) {
		try {
			RandomAccessFile file = new RandomAccessFile(localFile2, "rw");
			for(int i=0; i<length; i++){
				file.write(0);
			}
			file.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DownloadListener getListener() {
		return listener;
	}

	public void setListener(DownloadListener listener) {
		this.listener = listener;
	}

}
