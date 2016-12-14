package crawler.znanefirmy_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import crawler.api.StartCrawler;

public class StartZnanefirmy extends StartCrawler {
	public static EntityManagerFactory entityManagerFactory;
	

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static void setEntityManagerFactory(String persistenceUnit) {
		StartZnanefirmy.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
	}

	public static void main(String[] args) {
		StartZnanefirmy.setEntityManagerFactory("znanefirmy");
//		test();
		

		// wczytanie ustawien dla crawlera z pliku tekstowego
		Properties properties = StartZnanefirmy.loadProperties("c:\\crawlers\\properties\\znanefirmy_com.properties");
		// true - start index
		// false - restart index
		boolean createTables;
		if (!(properties.getProperty("test_level1").contains("1") || properties.getProperty("test_level2").contains("1")
				|| properties.getProperty("test_level3").contains("1")|| properties.getProperty("test_level4").contains("1"))) {
			if (properties.getProperty("level1").contains("1")) {
				createTables = true;
				System.out.println("level1 zosta� uruchomiony");
			} else {
				createTables = false;
				System.out.println("level 1 zosta� pomini�ty");
			}
			if (properties.getProperty("level2").contains("1")) {
				startPreIndex(properties, createTables);
				System.out.println("level2 zosta� uruchomiony");
			} else
				System.out.println("level2 zosta� pomini�ty");
			if (properties.getProperty("level3").contains("1")) {
				System.out.println("level3 zosta� uruchomiony");
				startIndex(properties);
			} else
				System.out.println("level3 zosta� pomini�ty");
			if(properties.getProperty("level4").contains("1")){
				System.out.println("level 4 zosta� uruchomiony");
				startProfile(properties);
			}else
				System.out.println("level 4 zosta� pomini�ty");
		} else {
			System.out.println("Wykryto sesj� testow�. Level1, Level2, Level3 , Level 4 zosta�y pomini�te");
			if (properties.getProperty("test_level1").contains("1")) {
				System.out.println("zosta� uruchomiony test_level1");
				
			} else
				System.out.println("test_level1 zosta� pomini�ty");

			if (properties.getProperty("test_level2").contains("1")) {
				System.out.println("zosta� utuchomiony test_level2");
				// 0 dla sesji testowej wczytuje pierwsz� podstron� indexu
				
			} else
				System.out.println("test_level2 zosta� pomini�ty");
			if (properties.getProperty("test_level3").contains("1")) {
				System.out.println("Zosta� uruchomiony test_level3");
				
			} else
				System.out.println("test_level3 zosta� pomini�ty");
			if (properties.getProperty("test_level4").contains("1")) {
				System.out.println("Zosta� uruchomiony test_level4");
				
			} else
				System.out.println("test_level4 zosta� pomini�ty");
		}
		//////////////////////
//		StartZnanefirmy.getEntityManagerFactory().close();
		

	}

	/***
	 * level 1 (bool) i 2
	 * 
	 * level 1 - stworzenie wszystkich niezb�dnych tabel pomocniczych (poza encjami)
	 * level 2 - scrap pierwszej strony wszystkich kategorii oraz zapisanie danych (pierwsze
	 * 40 firm dla ka�dej z kategorii)
	 * level 2 - zapisanie informacji o liczbie firm dla danej kategorii oraz stworzenie wszystkich mo�liwych link�w 
	 * @param properties
	 * @param createTables
	 */
	public static void startPreIndex(Properties properties, Boolean createTables) {
		System.out.println("Start pre index");
		if(createTables){		
			ZnanefirmyCreateTables znanefirmyCreateTables = new ZnanefirmyCreateTables(properties);
		}else System.out.println("Tworzenie tabel oraz scraping bran� zosta� pomini�ty");
//		ZnanefirmyGetIndex znanefirmyGetIndex = new ZnanefirmyGetIndex(properties, "http://znanefirmy.com/branza/33/sprzedaz-hurtowa-wyrobow-tekstylnych.html");
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
	 *  level 3
	 *  Doko�czenie indexu niezale�nie od hosta/liczby w�tk�w
	 * @param properties
	 */
	public static void startIndex(Properties properties) {
		System.out.println("Start index");
		
	}

	/**
	 *  level 4
	 *  scraping profili niezale�nie od hosta/liczby w�tk�w
	 * @param properties
	 */
	public static void startProfile(Properties properties) {
		System.out.println("Start profile");
		int numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads"));
		EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("znanefirmy");
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
	
	public static void test(){
		List<String> results = new ArrayList<String>();
		do{
			System.gc();
			results.clear();
			EntityManager em = StartZnanefirmy.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			Query query = em.createNativeQuery("SELECT nazwa from branze order by rand() limit 5");
			results=query.getResultList();
			for(String s:results)System.out.println("globalny EMF="+s);
			em.getTransaction().commit();
			em.close();
		}while(!results.isEmpty());
		
		System.out.println("koniec");
	}

}
