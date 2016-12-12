package crawler.bisnode_pl.pre_index;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import crawler.api.Scrape;
import crawler.bisnode_pl.index.GetIndex;
import crawler.bisnode_pl.index.IndexRepository;
import crawler.bisnode_pl.profil.GetProfile;
import crawler.bisnode_pl.profil.ProfilRepository;

public class BisNodeStart {

	public static void main(String[] args) {
		// wczytanie ustawien dla crawlera z pliku tekstowego
		Properties properties = loadProperties();
		// true - start index
		// false - restart index
		boolean startPreIndex;
		if (!(properties.getProperty("test_index").contains("1") || properties.getProperty("test_profile").contains("1")
				|| properties.getProperty("test_pre_index").contains("1"))) {
			if (properties.getProperty("level1").contains("1")) {
				startPreIndex = true;
				System.out.println("level 1 zosta� uruchomiony");
			} else {
				startPreIndex = false;
				System.out.println("level 1 zosta� pomini�ty");
			}
			if (properties.getProperty("level2").contains("1")) {
				startIndex(properties, startPreIndex);
				System.out.println("level2 zosta� uruchomiony");
			} else
				System.out.println("level2 zosta� pomini�ty");
			if (properties.getProperty("level3").contains("1")) {
				System.out.println("level3 zosta� uruchomiony");
				startProfile(properties);
			} else
				System.out.println("level3 zosta� pomini�ty");
		} else {
			System.out.println("Wykryto sesj� testow�. Level1, Level2, Level3 zosta�y pomini�te");
			if (properties.getProperty("test_pre_index").contains("1")) {
				System.out.println("zosta� uruchomiony test pre indexu");
				PreIndexBisNode preIndex = new PreIndexBisNode(properties);
			} else
				System.out.println("test pre indexu zosta� pomini�ty");

			if (properties.getProperty("test_index").contains("1")) {
				System.out.println("zosta� utuchomiony test indexu");
				//0 dla sesji testowej wczytuje pierwsz� podstron� indexu
				Scrape index = new GetIndex("orl", 0,properties);
			} else
				System.out.println("test indexu zosta� pomini�ty");
			if (properties.getProperty("test_profile").contains("1")) {
				System.out.println("Zosta� uruchomiony test profilu");
				Scrape profil = new GetProfile("http://www.bisnode.pl/firma/?id=777814&nazwa=WIS�A_P�OCK_S_A", properties);
			} else
				System.out.println("test profilu zosta� pomini�ty");
		}

		// startIndex(properties, startIndex);
		// startProfile(properties);

		// pojedynczy index - do usuniecia. Operacja b�dzie wykonywana z poziomu
		// IndexRepository

	}

	public static Properties loadProperties() {
		Properties properties = new Properties();
		InputStream input = null;
		int numberOfThread = 99;
		int idHost = 99;

		try {
			input = new FileInputStream("c:\\crawlers\\properties\\bisnode_pl.properties");
			// load a properties file
			properties.load(input);
			// get the property value and print it out
			System.out.println("idHost =" + properties.getProperty("idHost"));
			idHost = Integer.parseInt(properties.getProperty("idHost"));
			System.out.println("numberOfThreads =" + properties.getProperty("numberOfThreads"));
			numberOfThread = Integer.parseInt(properties.getProperty("numberOfThreads"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

	/**
	 * Tworzenie tabeli letters ('aaa', 'zzz') z informacj� ile firm dla danego
	 * ci�gu Tworzenie tabeli index_pages przechowuj�c� wszystkie URL do
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
			startPreIndex(properties);
		int numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads"));
		IndexRepository[] indexRepository = new IndexRepository[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			indexRepository[i] = new IndexRepository(properties, i);
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
			profilRepository[i] = new ProfilRepository(properties, i);
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
