package crawler.mojepanstwo_pl;

import java.util.Date;

import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import crawler.api.StartCrawler;

public class StartMojePanstwoZamowieniaPubliczne extends StartCrawler {

	final static Logger logger = Logger.getLogger(StartMojePanstwoZamowieniaPubliczne.class);

	public static void main(String[] args) {
		try {
			StartMojePanstwoKRSPodmiot.entityManagerFactory = Persistence.createEntityManagerFactory("mojepanstwo");
			StartMojePanstwoKRSPodmiot.properties = StartMojePanstwoKRSPodmiot
					.loadProperties("c:\\crawlers\\properties\\mojepanstwo_pl.properties");
		} catch (Exception e) {
			logger.error("Nie mozna utworzyc obiektu entityManagerFactory!");
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
			e.printStackTrace();
		} finally {
			// StartMojePanstwoKRSPodmiot.entityManagerFactory.close();
		}
		if (!properties.getProperty("test_level1").contains("1")) {
			logger.info("Pominiêto sesjê testow¹");
			if (properties.getProperty("level1").contains("1")) {
				logger.info("level 1 - scraping - KRSPodmiot");
				startScrapingZamowieniaPubliczne();
			} else {
				logger.info("level 1 - pominiêty - b³ad w properties");
			}
		} else {
			logger.info("Wykryto sesjê testow¹");
			if (properties.getProperty("test_level1").contains("1")) {
				logger.info("level 1 - test");
				logger.info("start:=" + new Date());
				startTestZamowieniaPubliczne();
				logger.info("koniec:=" + new Date());
				StartMojePanstwoKRSPodmiot.entityManagerFactory.close();
			} else
				logger.info("level 1 test - pominiêty");
		}

	}

	private static void startScrapingZamowieniaPubliczne() {
		int threadsNumber = Integer.parseInt(properties.getProperty("numberOfThreads"));
		MojePanstwoZamowieniaPubliczneRepository[] repository = new MojePanstwoZamowieniaPubliczneRepository[threadsNumber];
		Thread[] threads = new Thread[threadsNumber];
		for (int i = 0; i < threadsNumber; i++) {
			repository[i] = new MojePanstwoZamowieniaPubliczneRepository(i, properties, entityManagerFactory);
		}
		for (int i = 0; i < threadsNumber; i++) {
			threads[i] = new Thread(repository[i]);
		}
		for (int i = 0; i < threadsNumber; i++) {
			threads[i].start();
		}
	}

	private static void startTestZamowieniaPubliczne() {
		MojePanstwoZamowieniaPubliczneGet getZamowienie = new MojePanstwoZamowieniaPubliczneGet(1, properties,
				entityManagerFactory, "https://api-v3.mojepanstwo.pl/dane/zamowienia_publiczne/2887293");

	}

}
