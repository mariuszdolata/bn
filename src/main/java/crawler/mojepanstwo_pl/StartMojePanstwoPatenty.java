package crawler.mojepanstwo_pl;

import java.util.Date;

import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import crawler.api.StartCrawler;

public class StartMojePanstwoPatenty extends StartCrawler{
	final static Logger logger = Logger.getLogger(StartMojePanstwoPatenty.class);

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
				logger.info("level 1 - scraping - Patenty");
				startScrapingPatenty();
			} else {
				logger.info("level 1 - pominiêty - b³ad w properties");
			}
		} else {
			logger.info("Wykryto sesjê testow¹");
			if (properties.getProperty("test_level1").contains("1")) {
				logger.info("level 1 - test");
				logger.info("start:=" + new Date());
				startTestScrapingPatenty();
				logger.info("koniec:=" + new Date());
				StartMojePanstwoKRSPodmiot.entityManagerFactory.close();
			} else
				logger.info("level 1 test - pominiêty");
		}
	}

	private static void startTestScrapingPatenty() {
		MojePanstwoPatentyGet patentyGet = new MojePanstwoPatentyGet(1, properties, entityManagerFactory, "http://api-v3.mojepanstwo.pl/dane/patenty?limit=500");
		StartMojePanstwoPatenty.entityManagerFactory.close();		
	}

	/**
	 * Repozytorium wczytuje tylko 201 stron po 500 wynikow. Nie bylo potrzeby tworzenia wielowatkowosci.
	 * Wykorzystywana jest tabela ID (kolumna status_patenty). Zosta³o na³o¿one ograniczenie w repozytorium dla
	 * id E <1,201>
	 */
	private static void startScrapingPatenty() {
		MojePanstwoPatentyRepository repository = new MojePanstwoPatentyRepository(1, properties, entityManagerFactory);
		repository.run();
		StartMojePanstwoPatenty.entityManagerFactory.close();		
	}

}
