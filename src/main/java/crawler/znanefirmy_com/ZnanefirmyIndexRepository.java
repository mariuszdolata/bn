package crawler.znanefirmy_com;

import java.util.List;
import java.util.Properties;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import crawler.api.MainCrawler;
import crawler.api.Scrape;

public class ZnanefirmyIndexRepository extends MainCrawler implements Runnable {

	public ZnanefirmyIndexRepository(Properties properties, int threadId) {
		super(properties, threadId);
		this.setThreadId(threadId);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("START watek numer ="+this.getThreadId());
	}
	

}
