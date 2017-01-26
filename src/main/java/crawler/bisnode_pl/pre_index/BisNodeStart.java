package crawler.bisnode_pl.pre_index;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import crawler.api.Scrape;
import crawler.api.StartCrawler;
import crawler.bisnode_pl.index.GetIndex;
import crawler.bisnode_pl.index.IndexRepository;
import crawler.bisnode_pl.profil.GetProfile;
import crawler.bisnode_pl.profil.ProfilRepository;
import crawler.mojepanstwo_pl.StartMojePanstwoKRSPodmiot;

public class BisNodeStart extends StartCrawler {
	public final static Logger logger = Logger.getLogger(BisNodeStart.class);

	public static void main(String[] args) {
		// wczytanie ustawien dla crawlera z pliku tekstowego

		try {
			BisNodeStart.entityManagerFactory = Persistence.createEntityManagerFactory("bisnode_pl");
		} catch (Exception e) {
			logger.error("Nie mozna utworzyc obiektu entityManagerFactory!");
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
			e.printStackTrace();
		}
		try {
			BisNodeStart.properties = BisNodeStart
					.loadProperties("c:\\crawlers\\properties\\bisnode_pl.properties");
		} catch (Exception e) {
			logger.error("Nie mozna wczytac properties z c:\\crawlers\\properties\\bisnode_pl.properties");
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
			e.printStackTrace();
		}
		// true - start index
		// false - restart index
		boolean startPreIndex;
		if (!(properties.getProperty("test_index").contains("1") || properties.getProperty("test_profile").contains("1")
				|| properties.getProperty("test_pre_index").contains("1"))) {
			if (properties.getProperty("level1").contains("1")) {
				startPreIndex = true;
				logger.info("level 1 zosta³ uruchomiony");
			} else {
				startPreIndex = false;
				logger.info("level 1 zosta³ pominiêty");
			}
			if (properties.getProperty("level2").contains("1")) {
				startIndex(properties, startPreIndex);
				logger.info("level2 zosta³ uruchomiony");
			} else
				logger.info("level2 zosta³ pominiêty");
			if (properties.getProperty("level3").contains("1")) {
				logger.info("level3 zosta³ uruchomiony");
				startProfile(properties);
			} else
				logger.info("level3 zosta³ pominiêty");
		} else {
			System.out.println("Wykryto sesjê testow¹. Level1, Level2, Level3 zosta³y pominiête");
			if (properties.getProperty("test_pre_index").contains("1")) {
				logger.info("zosta³ uruchomiony test pre indexu");
				PreIndexBisNode preIndex = new PreIndexBisNode(properties);
			} else
				logger.info("test pre indexu zosta³ pominiêty");

			if (properties.getProperty("test_index").contains("1")) {
				logger.info("zosta³ utuchomiony test indexu");
				// 0 dla sesji testowej wczytuje pierwsz¹ podstronê indexu
				Scrape index = new GetIndex(0, BisNodeStart.properties, BisNodeStart.entityManagerFactory, "orl", 0);
			} else
				logger.info("test indexu zosta³ pominiêty");
			if (properties.getProperty("test_profile").contains("1")) {
				logger.info("Zosta³ uruchomiony test profilu");
				Scrape profil = new GetProfile(0, BisNodeStart.properties, BisNodeStart.entityManagerFactory,
						"http://www.bisnode.pl/firma/?id=777814&nazwa=WIS£A_P£OCK_S_A");
			} else
				logger.info("test profilu zosta³ pominiêty");
		}

		// startIndex(properties, startIndex);
		// startProfile(properties);

		// pojedynczy index - do usuniecia. Operacja bêdzie wykonywana z poziomu
		// IndexRepository

	}

	// public static Properties loadProperties() {
	// Properties properties = new Properties();
	// InputStream input = null;
	// int numberOfThread = 99;
	// int idHost = 99;
	//
	// try {
	// input = new
	// FileInputStream("c:\\crawlers\\properties\\bisnode_pl.properties");
	// // load a properties file
	// properties.load(input);
	// // get the property value and print it out
	// logger.info("idHost =" + properties.getProperty("idHost"));
	// idHost = Integer.parseInt(properties.getProperty("idHost"));
	// logger.info("numberOfThreads =" +
	// properties.getProperty("numberOfThreads"));
	// numberOfThread =
	// Integer.parseInt(properties.getProperty("numberOfThreads"));
	//
	// } catch (IOException ex) {
	// ex.printStackTrace();
	// } finally {
	// if (input != null) {
	// try {
	// input.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// return properties;
	// }

	/**
	 * Tworzenie tabeli letters ('aaa', 'zzz') z informacj¹ ile firm dla danego
	 * ci¹gu Tworzenie tabeli index_pages przechowuj¹c¹ wszystkie URL do
	 * scrapowania indexu
	 * 
	 * @param properties
	 */
	public static void startPreIndex(Properties properties) {
		PreIndexBisNode preIndex = new PreIndexBisNode(properties);
	}

	/**
	 * Level 1 i 2 Scraping indexu
	 * 
	 * @param properties
	 */
	public static void startIndex(Properties properties, boolean startIndex) {
		// tylko podczas startu scrapowania indexu uruchom
		if (startIndex)
			startPreIndex(BisNodeStart.properties);
		int numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads"));
		IndexRepository[] indexRepository = new IndexRepository[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			indexRepository[i] = new IndexRepository(i, properties, BisNodeStart.entityManagerFactory);
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
	 * Scraping profili Level3
	 * 
	 * @param properties
	 */
	public static void startProfile(Properties properties) {
		int numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads"));

		ProfilRepository[] profilRepository = new ProfilRepository[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			profilRepository[i] = new ProfilRepository(i, properties, BisNodeStart.entityManagerFactory);
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
