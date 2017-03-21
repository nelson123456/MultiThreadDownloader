package com.downloader;

import com.downloader.api.DownloadListener;

public class Runner {
	private static boolean finished = false;

	public static void main(String[] args) throws Exception {

		String url = "http://images.17173.com/2015/news/2015/11/13/mj1113aad02s.jpg";
		FileDownloader downloader = new FileDownloader(url, "F:\\DownloadTest.jpg");
		
		downloader.setListener(new DownloadListener() {
			public void notifyFinished() {
				//System.out.println("listener: finished!!");
				finished = true;
			}
		});
		
		downloader.execute();
		
		while(!finished){
			try {
				System.out.println("还没有下载完成，请等待");
				//休眠5秒
				Thread.sleep(1000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
		System.out.println("下载完成！");
	}

}
