package crawler.biznesfinder_pl;

import java.util.ArrayList;

//import static com.jayway.restassured.RestAssured.given;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import com.jayway.restassured.response.Response;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;

public class BiznesFinderIndexGet extends ScrapeClass implements Scrape {

	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
/**
 * Niestandardowy konstruktor wymagaj¹cy obiektu klasy WebDriver (FirefoxDriver). W celu zapewnienia poprawnosci konieczne jest umieszczenie pliku geckodriver w folderze
 * c:\crawlers oraz umieszczenie Property w System.Properties z informacj¹ o lokalizacji pliku
 * @param threadId
 * @param properties
 * @param entityManagerFactory
 * @param driver
 */
	public BiznesFinderIndexGet(int threadId, Properties properties, EntityManagerFactory entityManagerFactory, WebDriver driver, String urlToScrape){
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		this.setDriver(driver);
		this.getPageSelenium(this.getUrlToScrape());
		this.parsing();
	}
	/**
	 * Konstruktor do testu konkretnego url
	 * @param threadId
	 * @param properties
	 * @param entityManagerFactory
	 * @param urlToScrape
	 */
	public BiznesFinderIndexGet(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		this.getPageSelenium(this.getUrlToScrape());
		logger.info("Dane zostaly pobrane");
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// this.setResponseStatusCode();
		// logger.info("statusCode="+this.getStatusCode());
		// driver.quit();
		// logger.info(this.getDriver().getPageSource());
		this.parsing();
	}

/**
 * Metoda parsujaca strone www.
 * W przypadku braku znalezienia WebElement za pomoc¹ metody findElement(By.xpath...) wyrzucany jest b³¹d.
 * Wszelkie informacje parsowane koniecznie musz¹ byæ w blokach try/catch
 */
	
	public void parsing(){
		//Blok parsuj¹cy liczbê firm w danej kategorii
		try{
			WebElement liczbaFirm = driver.findElement(By.xpath(".//section[contains(@id, 'box-companies')]/header/h1/strong[1]"));
			String[] liczbaFirmString = liczbaFirm.getText().split(" ");
			int liczbaFirmInt=Integer.parseInt(liczbaFirmString[0]);
			this.setNumberOfCompanies(liczbaFirmInt);
			logger.info("LICZBA FIRM="+liczbaFirmInt);
			if(liczbaFirmInt>25&&this.getProperties().getProperty("level2").contains("1")){
				List<BiznesFinderDokonczenieIndexu> indexy = new ArrayList<BiznesFinderDokonczenieIndexu>();
				for(int i=25, j=1;i<liczbaFirmInt;i+=25, j++){
					BiznesFinderDokonczenieIndexu index = new BiznesFinderDokonczenieIndexu();
					index.setUrl(this.getDriver().getCurrentUrl()+",p,"+(j+1));
					index.setMeta(this.getDriver().getCurrentUrl());
					indexy.add(index);
				}
				this.insertDataListEntity(indexy);
			}
			
		}catch(Exception e){logger.warn("Nie znaleziono liczby firm dla kategorii url="+driver.getCurrentUrl());}
		
		//Blok parsuj¹cy dane firm na stronie
		List<WebElement> parent = driver.findElements(By.xpath("//li[contains(@itemtype, 'http://schema.org/LocalBusiness')]"));
		logger.error("parent="+parent.size());
//		for(WebElement e:parent) logger.error(e.getText());
		logger.info("#################################");
		List<BiznesFinderIndex> companies = new ArrayList<BiznesFinderIndex>();
		for(int i=0;i<parent.size();i++){
			BiznesFinderIndex bfi = new BiznesFinderIndex();
//			logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+parent.get(i).getText()+"\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			try{
				WebElement nazwa = parent.get(i).findElement(By.xpath(".//h2[contains(@class, 'company-name')]/a"));
				bfi.setNazwa(nazwa.getText());
				bfi.setUrl(nazwa.getAttribute("href"));
			}catch(Exception e) {
//				logger.error("Brak nazwy oraz adresu URL");
				}
			try{
				WebElement branza = parent.get(i).findElement(By.xpath(".//li[contains(@class, 'company-category')]/a"));
				bfi.setBranza(branza.getText());
			}catch(Exception e) {
//				logger.error("Brak branzy");
				}
			try{
				WebElement miasto = parent.get(i).findElement(By.xpath(".//span[contains(@class, 'company-address-city')]"));
				bfi.setMiasto(miasto.getText());
			}catch(Exception e) {
//				logger.error("Brak miasta");
				}
			try{
				WebElement ulica = parent.get(i).findElement(By.xpath(".//span[contains(@class, 'company-address-street')]"));
				bfi.setUlica(ulica.getText());
			}catch(Exception e) {
//				logger.error("Brak ulicy");
				}
			try{
				WebElement numer = parent.get(i).findElement(By.xpath(".//span[contains(@class, 'company-address-building')]"));
				bfi.setNumer(numer.getText());
			}catch(Exception e) {
//				logger.error("Brak numeru");
				}
			try{
				WebElement telefon=parent.get(i).findElement(By.xpath(".//meta[contains(@itemprop, 'telephone')]"));
				bfi.setTelefon(telefon.getAttribute("content"));
			}catch(Exception e) {
//				logger.error("Brak telefonu");
				}
			try{
				WebElement website = parent.get(i).findElement(By.xpath(".//li[contains(@class, 'company-www')]/a"));
				bfi.setWebsite(website.getAttribute("href"));
			}catch(Exception e) {
//				logger.error("Brak website");
				}
			try{
				WebElement email = parent.get(i).findElement(By.xpath(".//li[contains(@class, 'company-email')]/a"));
				bfi.setEmail(email.getAttribute("href").replaceAll("mailto:", ""));
			}catch(Exception e) {
//				logger.error("Brak email");
				}
			bfi.setMeta(this.getUrlToScrape());
			companies.add(bfi);
//			logger.error(bfi.toString());
//			this.insertDataEntity(bfi);
		}
		this.insertDataListEntity(companies);

	}
/**
 * Metoda pobieraj¹ca strone o podanym url do przegladarki
 * @param urlToScrape
 */
	public void getPageSelenium(String urlToScrape) {

		int i=0;
		logger.info("Wczytanie strony");
		do {
			try {
				driver.get(urlToScrape);
				 Thread.sleep((i*1000+1500));
				 Thread.yield();
			} catch (Exception e) {
				logger.error("Nieudana próba pobrania BiznesFinderIndex url=" + this.getUrlToScrape());
			}
			this.setDriver(driver);
//			logger.warn("Kolejna proba wczytania strony i="+i);
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
