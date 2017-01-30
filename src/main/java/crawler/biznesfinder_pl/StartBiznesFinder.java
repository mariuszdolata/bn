package crawler.biznesfinder_pl;

import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import crawler.api.StartCrawler;

public class StartBiznesFinder extends StartCrawler {
	final static Logger logger = Logger.getLogger(StartBiznesFinder.class);

	public static void main(String[] args) {
		try {
			StartBiznesFinder.entityManagerFactory = Persistence.createEntityManagerFactory("biznesfinder_pl");

		} catch (Exception e) {
			logger.error("Nie mo¿na stworzyc entityManagerFactory");
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
			e.printStackTrace();
		}
		StartBiznesFinder.properties = loadProperties("c:\\crawlers\\properties\\biznesfinder_pl.properties");
		// argument systemowy dla Selenium WebDriver FirefoxDriver. Wymagany
		// poni¿szy plik w podanej lokalizacji!!
		
		if (!(properties.getProperty("test_level1").contains("1") || properties.getProperty("test_level2").contains("1")
				|| properties.getProperty("test_level3").contains("1")
				|| properties.getProperty("test_level4").contains("1"))) {
			if (properties.getProperty("level1").contains("1")) {

				logger.info("level1 zosta³ uruchomiony");
				startLevel1();
			} else {

				logger.info("level 1 zosta³ pominiêty");
			}
			if (properties.getProperty("level2").contains("1")||properties.getProperty("level3").contains("1")) {
				logger.info("level2 i level3 zosta³ uruchomiony");
				startLevel23();
			} else
				logger.info("level2 i level3 zosta³ pominiêty");
			if (properties.getProperty("level4").contains("1")) {
				logger.info("level4 zosta³ uruchomiony");
				startLevel4();
			} else
				logger.info("level4 zosta³ pominiêty");
		} else {
			logger.info("Wykryto sesjê testow¹. Level1, Level2, Level3 , Level 4 zosta³y pominiête");
			if (properties.getProperty("test_level1").contains("1")) {
				logger.info("zosta³ uruchomiony test_level1");
				testLevel1();
			} else
				logger.info("test_level1 zosta³ pominiêty");

			if (properties.getProperty("test_level2").contains("1")) {
				logger.info("zosta³ utuchomiony test_level2");
				testLevel2();

			} else
				logger.info("test_level2 zosta³ pominiêty");
			if (properties.getProperty("test_level3").contains("1")) {
				logger.info("Zosta³ uruchomiony test_level3");
				testLevel3();
			} else
				logger.info("test_level4 zosta³ pominiêty");
			if (properties.getProperty("test_level4").contains("1")) {
				logger.info("Zosta³ uruchomiony test_level4");
				testLevel4();
			} else
				logger.info("test_level4 zosta³ pominiêty");
			StartBiznesFinder.entityManagerFactory.close();
		}
		// StartFirmenwissen.entityManagerFactory.close();
		// selenium();

	}

	private static void testLevel4() {
		// TODO Auto-generated method stub

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
		// Testowanie BiznesFinderIndex
		try {
			WebDriver driver = new FirefoxDriver();
			BiznesFinderIndexGet indexGet = new BiznesFinderIndexGet(1, properties, entityManagerFactory, driver, "http://www.biznesfinder.pl/b,administracja+dom%C3%B3w+mieszkalnych");
		} catch (Exception e) {
			logger.error("b³¹d podczas tworzenia/wykonania BiznesFinderIndexGet! - metoda testlevel2()");
			logger.error(e.getMessage());
			logger.error(e.getLocalizedMessage());
		}

	}

	/**
	 * Test scrapingu indexu branz
	 */
	private static void testLevel1() {
		logger.info("TEST LEVEL 1");
		BiznesFinderBranzaGet branza = new BiznesFinderBranzaGet(1, properties, entityManagerFactory, "http://www.biznesfinder.pl/mapa-branzy/a", new FirefoxDriver());
	}

	/**
	 * Scraping profili firmy Index zawiera email, website. Profil ma dodatkowe
	 * dane.
	 */
	private static void startLevel4() {
		// TODO Auto-generated method stub

	}

	/**
	 * Dokoñczenie scrapingu indexu (scraping url z tabeli index_uzupelnienie)
	 * Index zawiera email, website <<<<<<<<<<<<
	 */
	private static void startLevel3() {
		// TODO Auto-generated method stub

	}

	/**
	 * Czsciowy scraping indexu . Wczytanie tylko pierwszej strony dla danej
	 * branzy. Zapisanie pierwszych 25 wyników firm do tabeli index Zapisanie
	 * pozosta³ych url do scrapingu do tabeli index_uzupelnienie na podstawie
	 * informacji o liczbie firm dla danej branzy
	 */
	private static void startLevel23() {
		logger.info("START level2");
		int numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads"));
		logger.info("liczba watkow="+numberOfThreads);
		BiznesFinderIndexRepository[] repositories = new BiznesFinderIndexRepository[numberOfThreads];
		Thread[] threads = new Thread[numberOfThreads];
		WebDriver[] drivers = new ChromeDriver[numberOfThreads];
		for(int i=0;i<numberOfThreads;i++){
			drivers[i] = new ChromeDriver();
			repositories[i] = new BiznesFinderIndexRepository(1, properties,
					entityManagerFactory, drivers[i]);
		}
		for(int i=0;i<numberOfThreads;i++){
			threads[i] = new Thread(repositories[i]);
		}
		for(int i=0;i<numberOfThreads;i++){
			try{
				threads[i].start();
			}catch(Exception e){
				logger.error("Thread nr "+i+", blad obiektu klasy BiznesFinderIndexRepository - ponowne wywolanie metory run()");
				threads[i].start();
			}
		}
		
	}

	/**
	 * LEVEL 1 scrap branz ze strony http://www.biznesfinder.pl/mapa-branzy/a
	 * (index branz) alfabetycznie z podstronami
	 * wymagany 1 w¹tek
	 */
	private static void startLevel1() {
		logger.info("START LEVEL 1 z 4 - scraping listy branz");
		BiznesFinderBranzaRepository repository = new BiznesFinderBranzaRepository(1, properties, entityManagerFactory);
		repository.run();
	}

}
