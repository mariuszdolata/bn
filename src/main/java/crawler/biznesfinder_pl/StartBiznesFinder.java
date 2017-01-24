package crawler.biznesfinder_pl;

import java.io.IOException;
import java.io.StringReader;

import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import crawler.api.StartCrawler;
import crawler.firmenwissen_com.StartFirmenwissen;

public class StartBiznesFinder extends StartCrawler {
	final static Logger logger = Logger.getLogger(StartBiznesFinder.class);

	public static void main(String[] args) {
		try{
			StartBiznesFinder.entityManagerFactory = Persistence.createEntityManagerFactory("biznesfinder_pl");
			
		}catch(Exception e){
			logger.error("Nie mo¿na stworzyc entityManagerFactory");
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
			e.printStackTrace();
		}
		StartBiznesFinder.properties = loadProperties("c:\\crawlers\\properties\\biznesfinder_pl.properties");
		//argument systemowy dla Selenium WebDriver FirefoxDriver. Wymagany poni¿szy plik w podanej lokalizacji!!
		System.setProperty("webdriver.gecko.driver", "C:\\crawlers\\geckodriver.exe");
		if (!(properties.getProperty("test_level1").contains("1") || properties.getProperty("test_level2").contains("1")
				|| properties.getProperty("test_level3").contains("1"))) {
			if (properties.getProperty("level1").contains("1")) {

				System.out.println("level1 zosta³ uruchomiony");
				startLevel1();
			} else {

				System.out.println("level 1 zosta³ pominiêty");
			}
			if (properties.getProperty("level2").contains("1")) {
				System.out.println("level2 zosta³ uruchomiony");
				startLevel2();
			} else
				System.out.println("level2 zosta³ pominiêty");
			if (properties.getProperty("level3").contains("1")) {
				System.out.println("level3 zosta³ uruchomiony");
				startLevel3();
			} else
				System.out.println("level3 zosta³ pominiêty");
		} else {
			System.out.println("Wykryto sesjê testow¹. Level1, Level2, Level3 , Level 4 zosta³y pominiête");
			if (properties.getProperty("test_level1").contains("1")) {
				System.out.println("zosta³ uruchomiony test_level1");
				testLevel1();
			} else
				System.out.println("test_level1 zosta³ pominiêty");

			if (properties.getProperty("test_level2").contains("1")) {
				System.out.println("zosta³ utuchomiony test_level2");
				testLevel2();

			} else
				System.out.println("test_level2 zosta³ pominiêty");
			if (properties.getProperty("test_level3").contains("1")) {
				System.out.println("Zosta³ uruchomiony test_level3");
				testLevel3();
			} else
				System.out.println("test_level3 zosta³ pominiêty");
			StartBiznesFinder.entityManagerFactory.close();
		}
		// StartFirmenwissen.entityManagerFactory.close();
//		selenium();

	}
/**
 * test scrapingu wybranej firmy
 */
	private static void testLevel3() {
		// TODO Auto-generated method stub
		
	}
/**
 * test scrapingu wybranej branzy
 */
	private static void testLevel2() {
		//Testowanie BiznesFinderIndex
		try{
			BiznesFinderIndexGet indexGet = new BiznesFinderIndexGet(1, properties, entityManagerFactory, "http://www.biznesfinder.pl/s,apteki");
		}catch(Exception e){
			logger.error("b³¹d podczas tworzenia/wykonania BiznesFinderIndexGet!");
			logger.error(e.getMessage());
			logger.error(e.getLocalizedMessage());
		}
			
	}

	/**
	 * Test scrapingu indexu branz
	 */
	private static void testLevel1() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Scraping wybranej firmy (profil firmy)
	 */
	private static void startLevel3() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Scraping wybranej branzy (index firm)
	 */
	private static void startLevel2() {
		logger.info("START level2");
		try{
			BiznesFinderIndexRepository repository = new BiznesFinderIndexRepository(1, properties, entityManagerFactory);
			repository.run();
		}catch(Exception e){logger.error("Blad BiznesFinderIndexRepository");}
	}

	/**
	 * LEVEL 1 scrap branz ze strony http://www.biznesfinder.pl/mapa-branzy/a (index branz)
	 * alfabetycznie z podstronami
	 */
	private static void startLevel1() {
		// TODO Auto-generated method stub
		
	}


}
