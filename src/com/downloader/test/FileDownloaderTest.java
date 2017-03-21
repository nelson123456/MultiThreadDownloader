package com.downloader.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.downloader.FileDownloader;
import com.downloader.api.DownloadListener;

public class FileDownloaderTest {
	private boolean finished = false;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDownloader() {
		String url = "http://images.17173.com/2015/news/2015/11/13/mj1113aad02s.jpg";
		FileDownloader downloader = new FileDownloader(url, "F:\\tmp\\DownloadTest.png");
		
		downloader.setListener(new DownloadListener() {
			public void notifyFinished() {
				finished = true;
			}
		});
		
		downloader.execute();
	}

}
