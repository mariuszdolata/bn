package crawler.znanefirmy_com;

import java.util.List;
import java.util.Properties;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import crawler.api.MainCrawler;
import crawler.api.Scrape;

public class ZnanefirmyProfilRepository extends MainCrawler implements Runnable {

	public ZnanefirmyProfilRepository(Properties properties, int threadId) {
		super(properties, threadId);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		System.out.println("ProfilRepository watek nr " + this.getThreadId());
		
	}

	
}
