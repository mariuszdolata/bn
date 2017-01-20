package crawler.krs_pobierz_pl.profil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import crawler.api.StartCrawler;
import crawler.mojepanstwo_pl.StartMojePanstwoKRSPodmiot;

public class StartKRSPobierzProfil extends StartCrawler {
	final static Logger logger = Logger.getLogger(StartKRSPobierzProfil.class);

	public static void main(String[] args) {
		logger.info("START KRS_pobierz->PROFIL (tabele profil i osoba)");

		try {
			StartKRSPobierzProfil.entityManagerFactory = Persistence
					.createEntityManagerFactory("krs_pobierz_pl_profil");
			StartKRSPobierzProfil.properties = StartMojePanstwoKRSPodmiot
					.loadProperties("c:\\crawlers\\properties\\krs_pobierz_pl.properties");
		} catch (Exception e) {
			logger.error("Nie mozna utworzyc obiektu entityManagerFactory!");
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
			e.printStackTrace();
		} 
		if (!properties.getProperty("test_level1").contains("1")) {
			logger.info("Pomini�to sesj� testow�");
			if (properties.getProperty("level1").contains("1")) {
				logger.info("level 1 - scraping - KRS_POBIERZ >> profil firmy");
				startScrapingKRSPobierz_profil();
			} else {
				logger.info("level 1 - pomini�ty - b�ad w properties");
			}
		} else {
			logger.info("Wykryto sesj� testow�");
			if (properties.getProperty("test_level1").contains("1")) {
				logger.info("level 1 - test");
				logger.info("start:=" + new Date());
				startTestScrapingKRSPobierz_profil();
				logger.info("koniec:=" + new Date());
				StartMojePanstwoKRSPodmiot.entityManagerFactory.close();
			} else
				logger.info("level 1 test - pomini�ty");
		}

	}

	private static void startTestScrapingKRSPobierz_profil() {
		// TODO Auto-generated method stub

	}

	private static void startScrapingKRSPobierz_profil() {
		int threadsNumber = Integer.parseInt(properties.getProperty("numberOfThreads"));
		int idHost = Integer.parseInt(properties.getProperty("idHost"));
		KRSPobierzProfilRepository[] repository = new KRSPobierzProfilRepository[threadsNumber];
		Thread[] threads = new Thread[threadsNumber];
		for (int i = 0; i < threadsNumber; i++) {
			repository[i] = new KRSPobierzProfilRepository(i, properties, entityManagerFactory);
		}
		for (int i = 0; i < threadsNumber; i++) {
			threads[i] = new Thread(repository[i]);
		}
		for (int i = 0; i < threadsNumber; i++) {
			threads[i].start();
		}

	}

}
