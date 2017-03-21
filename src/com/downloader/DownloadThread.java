package com.downloader;

import java.io.RandomAccessFile;
import java.util.concurrent.CyclicBarrier;

import com.downloader.api.Connection;

public class DownloadThread implements Runnable{
	
	private Connection conn;
	private int start;
	private int end;
	private String localfile;
	private CyclicBarrier barrier;

	public DownloadThread(Connection conn, int startPoint, int endPoint, String file, CyclicBarrier barrier) {
		this.conn = conn;
		this.start = startPoint;
		this.end = endPoint;
		this.localfile = file;
		this.barrier = barrier;
	}

	@Override
	public void run(){
		
			try {
				//System.out.println(Thread.currentThread().getId());
				byte[] content = conn.read(start, end);
				RandomAccessFile file = new RandomAccessFile(localfile, "rw");
				file.seek(start);
				file.write(content);
				file.close();
				conn.close();
				//System.out.println(Thread.currentThread().getId() + "finished");
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}

}
