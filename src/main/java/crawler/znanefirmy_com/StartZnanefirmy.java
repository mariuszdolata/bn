package crawler.znanefirmy_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import crawler.api.StartCrawler;
import crawler.proby.MainProba;

public class StartZnanefirmy extends StartCrawler {
	public static EntityManagerFactory entityManagerFactory;
	final static Logger logger = Logger.getLogger(StartZnanefirmy.class);

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static void setEntityManagerFactory(String persistenceUnit) {
		StartZnanefirmy.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
	}

	public static void main(String[] args) {
		StartZnanefirmy.setEntityManagerFactory("znanefirmy");
		logger.info("##########################################################################");
		logger.info("Kolejne uruchomienie programu");
		logger.info("##########################################################################");
		entityManagerFactory = Persistence.createEntityManagerFactory("znanefirmy");

		// wczytanie ustawien dla crawlera z pliku tekstowego
		Properties properties = StartZnanefirmy.loadProperties("c:\\crawlers\\properties\\znanefirmy_com.properties");
		// true - start index
		// false - restart index
		boolean createTables;
		if (!(properties.getProperty("test_level1").contains("1") || properties.getProperty("test_level2").contains("1")
				|| properties.getProperty("test_level3").contains("1")
				|| properties.getProperty("test_level4").contains("1"))) {
			if (properties.getProperty("level1").contains("1")) {
				createTables = true;
				System.out.println("level1 zosta³ uruchomiony");
			} else {
				createTables = false;
				System.out.println("level 1 zosta³ pominiêty");
			}
			if (properties.getProperty("level2").contains("1")) {
				startPreIndex(properties, createTables);
				System.out.println("level2 zosta³ uruchomiony");
			} else
				System.out.println("level2 zosta³ pominiêty");
			if (properties.getProperty("level3").contains("1")) {
				System.out.println("level3 zosta³ uruchomiony");
				startIndex(properties, entityManagerFactory);
			} else
				System.out.println("level3 zosta³ pominiêty");
			if (properties.getProperty("level4").contains("1")) {
				System.out.println("level 4 zosta³ uruchomiony");
				startProfile(properties, entityManagerFactory);
			} else
				System.out.println("level 4 zosta³ pominiêty");
		} else {
			System.out.println("Wykryto sesjê testow¹. Level1, Level2, Level3 , Level 4 zosta³y pominiête");
			if (properties.getProperty("test_level1").contains("1")) {
				System.out.println("zosta³ uruchomiony test_level1");

			} else
				System.out.println("test_level1 zosta³ pominiêty");

			if (properties.getProperty("test_level2").contains("1")) {
				System.out.println("zosta³ utuchomiony test_level2");
				// 0 dla sesji testowej wczytuje pierwsz¹ podstronê indexu

			} else
				System.out.println("test_level2 zosta³ pominiêty");
			if (properties.getProperty("test_level3").contains("1")) {
				System.out.println("Zosta³ uruchomiony test_level3");

			} else
				System.out.println("test_level3 zosta³ pominiêty");
			if (properties.getProperty("test_level4").contains("1")) {
				System.out.println("Zosta³ uruchomiony test_level4");
				logger.info("TEST LEVEL4");
				ZnanefirmyGetProfil znanefirmyGetProfil = new ZnanefirmyGetProfil(1, properties, entityManagerFactory, "http://znanefirmy.com/firma/1543/firma-handlowa-abc-dariusz-blicharz.html");

			} else
				System.out.println("test_level4 zosta³ pominiêty");
		}
	}

	/***
	 * level 1 (bool) i 2
	 * 
	 * level 1 - stworzenie wszystkich niezbêdnych tabel pomocniczych (poza
	 * encjami) level 2 - scrap pierwszej strony wszystkich kategorii oraz
	 * zapisanie danych (pierwsze 40 firm dla ka¿dej z kategorii) level 2 -
	 * zapisanie informacji o liczbie firm dla danej kategorii oraz stworzenie
	 * wszystkich mo¿liwych linków
	 * 
	 * @param properties
	 * @param createTables
	 */
	public static void startPreIndex(Properties properties, Boolean createTables) {
		System.out.println("Start pre index");

		if (createTables) {
			ZnanefirmyCreateTables znanefirmyCreateTables = new ZnanefirmyCreateTables(properties);
		} else
			System.out.println("Tworzenie tabel oraz scraping bran¿ zosta³ pominiêty");
		// ZnanefirmyGetIndex znanefirmyGetIndex = new
		// ZnanefirmyGetIndex(properties,
		// "http://znanefirmy.com/branza/33/sprzedaz-hurtowa-wyrobow-tekstylnych.html");
		int numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads"));

		ZnanefirmyIndexRepository[] indexRepository = new ZnanefirmyIndexRepository[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			indexRepository[i] = new ZnanefirmyIndexRepository(i, properties, StartZnanefirmy.entityManagerFactory);
		}
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new Thread(indexRepository[i]);
		}
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i].start();
		}
	}

	/**
	 * level 3 Dokoñczenie indexu niezale¿nie od hosta/liczby w¹tków
	 * 
	 * @param properties
	 */
	public static void startIndex(Properties properties, EntityManagerFactory emf) {
		logger.info("START INDEX - startIndex() method");
		int numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads"));
		EntityManagerFactory entityManagerFactory = emf;
		ZnanefirmyIndexRepository[] indexRepository = new ZnanefirmyIndexRepository[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			indexRepository[i] = new ZnanefirmyIndexRepository(i, properties, entityManagerFactory);
		}
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new Thread(indexRepository[i]);
		}
		for(int i=0;i<numberOfThreads;i++){
			threads[i].start();
		}
	}

	/**
	 * level 4 scraping profili niezale¿nie od hosta/liczby w¹tków
	 * 
	 * @param properties
	 */
	public static void startProfile(Properties properties, EntityManagerFactory emf) {
		logger.info("START PROFIL - startProfile() method");
		int numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads"));
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("znanefirmy");
		ZnanefirmyProfilRepository[] profilRepository = new ZnanefirmyProfilRepository[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			profilRepository[i] = new ZnanefirmyProfilRepository(properties, i, entityManagerFactory);
		}
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new Thread(profilRepository[i]);
		}
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i].start();
		}
	}

}
