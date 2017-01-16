package crawler.mojepanstwo_pl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import crawler.api.StartCrawler;

public class StartMojePanstwoKRSPodmiot extends StartCrawler {

	final static Logger logger = Logger.getLogger(StartMojePanstwoKRSPodmiot.class);

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
				startScrapingKRSPodmiot();
			} else {
				logger.info("level 1 - pominiêty - b³ad w properties");
			}
		} else {
			logger.info("Wykryto sesjê testow¹");
			if (properties.getProperty("test_level1").contains("1")) {
				logger.info("level 1 - test");
				logger.info("start:=" + new Date());
				// insertIds();
				// for(int i=23350;i<23700;i++){
				// MojePanstwoKRSPodmiotGet getProfil1 = new
//				 MojePanstwoKRSPodmiotGet(1, properties, entityManagerFactory,
//				 "https://api-v3.mojepanstwo.pl/dane/krs_podmioty/"+i+".json?layers[]=dzialalnosci&layers[]=emisje_akcji&layers[]=firmy&layers[]=graph&layers[]=jedynyAkcjonariusz&layers[]=komitetZalozycielski&layers[]=reprezentacja&layers[]=nadzor&layers[]=oddzialy&layers[]=prokurenci&layers[]=wspolnicy");
//				 }
//				 MojePanstwoKRSPodmiotGet getProfil1 = new
//				 MojePanstwoKRSPodmiotGet(1, properties, entityManagerFactory,
//				 "https://api-v3.mojepanstwo.pl/dane/krs_podmioty/23305.json?layers[]=dzialalnosci&layers[]=emisje_akcji&layers[]=firmy&layers[]=graph&layers[]=jedynyAkcjonariusz&layers[]=komitetZalozycielski&layers[]=reprezentacja&layers[]=nadzor&layers[]=oddzialy&layers[]=prokurenci&layers[]=wspolnicy");

				logger.info("koniec:=" + new Date());
				StartMojePanstwoKRSPodmiot.entityManagerFactory.close();
			} else
				logger.info("level 1 test - pominiêty");
		}

	}

	public static void insertIds() {
		EntityManager em = StartMojePanstwoKRSPodmiot.entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		for (int i = 1; i <= 3000000; i++) {
			ID ii = new ID(i);
			em.persist(ii);
			if (i % 50 == 0) {
				em.flush();
				em.clear();
			}
		}

		em.getTransaction().commit();

		em.close();
	}

	public static void startScrapingKRSPodmiot() {
		int threadsNumber = Integer.parseInt(properties.getProperty("numberOfThreads"));
		MojePanstwoKRSPodmiotRepository[] repository = new MojePanstwoKRSPodmiotRepository[threadsNumber]; 
		Thread[] threads = new Thread[threadsNumber];
		for (int i = 0; i < threadsNumber; i++) {
			repository[i] = new MojePanstwoKRSPodmiotRepository(i, StartMojePanstwoKRSPodmiot.properties, StartMojePanstwoKRSPodmiot.entityManagerFactory);
		}
		for (int i = 0; i < threadsNumber; i++) {
			threads[i]=new Thread(repository[i]);
		}
		for (int i = 0; i < threadsNumber; i++) {
			threads[i].start();
		}

		
	}

}
