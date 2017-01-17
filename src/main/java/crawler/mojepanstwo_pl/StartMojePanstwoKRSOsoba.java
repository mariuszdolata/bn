package crawler.mojepanstwo_pl;

import java.util.Date;

import javax.persistence.Persistence;
import org.apache.log4j.Logger;
import crawler.api.StartCrawler;

public class StartMojePanstwoKRSOsoba extends StartCrawler {
	final static Logger logger = Logger.getLogger(StartMojePanstwoKRSOsoba.class);

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
				startScrapingKRSOsoba();
			} else {
				logger.info("level 1 - pominiêty - b³ad w properties");
			}
		} else {
			logger.info("Wykryto sesjê testow¹");
			if (properties.getProperty("test_level1").contains("1")) {
				logger.info("level 1 - test");
				logger.info("start:=" + new Date());
				startTestScrapingKRSOsoba();
				logger.info("koniec:=" + new Date());
				StartMojePanstwoKRSPodmiot.entityManagerFactory.close();
			} else
				logger.info("level 1 test - pominiêty");
		}
	}

	private static void startScrapingKRSOsoba() {
		int threadsNumber = Integer.parseInt(properties.getProperty("numberOfThreads"));
		MojePanstwoKRSOsobaRepository[] repository = new MojePanstwoKRSOsobaRepository[threadsNumber];
		Thread[] threads = new Thread[threadsNumber];
		for (int i = 0; i < threadsNumber; i++) {
			repository[i]= new MojePanstwoKRSOsobaRepository(i, properties, entityManagerFactory);
		}
		for (int i = 0; i < threadsNumber; i++) {
			threads[i] = new Thread(repository[i]);
		}
		for (int i = 0; i < threadsNumber; i++) {
			threads[i].start();
		}
	}
	private static void startTestScrapingKRSOsoba(){
		MojePanstwoKRSOsobaGet osobaGet = new MojePanstwoKRSOsobaGet(1, properties, entityManagerFactory,"https://api-v3.mojepanstwo.pl/dane/krs_osoby/383828");
		
	}

}
