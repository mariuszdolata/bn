package crawler.biznesfinder_pl;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;

public class BiznesFinderBranzaGet extends ScrapeClass implements Scrape {
	private WebDriver driver;
	/**
	 * Jeœli nextPageUrl!=null to konieczne jest wczytanie kolejnej podstony indexu branz
	 */
	private String nextPageUrl=null;
	

	public String getNextPageUrl() {
		return nextPageUrl;
	}

	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public BiznesFinderBranzaGet(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			String urlToScrape, WebDriver driver) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		this.setDriver(driver);
		this.getPageSelenium(this.getUrlToScrape());
		this.parsing();
	}

	public void parsing() {
		try{
			List<WebElement> urls = this.getDriver().findElements(By.xpath(".//li/a[contains(@class, 'text-bold')]"));			
			for (WebElement url : urls) {
				BiznesFinderBranza branza = new BiznesFinderBranza();
				branza.setNazwa(url.getText());
				branza.setUrl(url.getAttribute("href"));
				branza.setMeta(driver.getCurrentUrl());
				
				logger.info(url.getText() + ":::" + url.getAttribute("href"));
				this.insertDataEntity(branza);
			}
		}catch(Exception e){logger.warn("Brak rezultatow dla branz url="+this.getUrlToScrape());}
		
		try{
			WebElement nextPage = this.getDriver().findElement(By.xpath(".//li/a[contains(@title, 'nastêpna')]"));
			logger.info("nextPage="+nextPage.getAttribute("href"));
			this.setNextPageUrl(nextPage.getAttribute("href"));
		}catch(Exception e){logger.warn("nie znaleziono nastêpnej strony");}

	}

	public void getPageSelenium(String urlToScrape) {
		int i=1;
		logger.info("Wczytanie strony");
		do {
			try {
				driver.get(urlToScrape);
				 Thread.sleep((i*1000+1500));
			} catch (Exception e) {
				logger.error("Nieudana próba pobrania BiznesFinderIndexBranze url=" + this.getUrlToScrape());
			}
			this.setDriver(driver);
			logger.warn("Kolejna proba wczytania strony i="+i);
			i++;
		} while (this.getDriver().getTitle() == "Loading...");

	}

	public List<String> fetchUrlsToScrape() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object parsing(HtmlPage page, Object mainProfil) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean insertData(Object objectToInsert) {
		// TODO Auto-generated method stub
		return null;
	}

	public void supportFetchUrlsToScrape() {
		// TODO Auto-generated method stub

	}

	public void supportGetPage() {
		// TODO Auto-generated method stub

	}

	public void supportParsing() {
		// TODO Auto-generated method stub

	}

	public void supportInsertData() {
		// TODO Auto-generated method stub

	}

	public void mainProcess() {
		// TODO Auto-generated method stub

	}

	public void logger() {
		// TODO Auto-generated method stub

	}

}
