package crawler.firmenwissen_com;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import crawler.api.StartCrawler;

public class StartFirmenwissen extends StartCrawler {
	public final static Logger logger = Logger.getLogger(StartFirmenwissen.class);
	public static void main(String[] args) {
		try{
			StartFirmenwissen.entityManagerFactory = Persistence.createEntityManagerFactory("firmenwissen");
			
		}catch(Exception e){
			logger.error("Nie mo¿na stworzyc entityManagerFactory");
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
			e.printStackTrace();
		}
		StartFirmenwissen.properties = loadProperties("c:\\crawlers\\properties\\firmenwissen_com.properties");

		boolean createTables;
		if (!(properties.getProperty("test_level1").contains("1") || properties.getProperty("test_level2").contains("1")
				|| properties.getProperty("test_level3").contains("1"))) {
			if (properties.getProperty("level1").contains("1")) {

				System.out.println("level1 zosta³ uruchomiony");
				startLevel1();
			} else {

				System.out.println("level 1 zosta³ pominiêty");
			}
			if (properties.getProperty("level2").contains("1")) {
				System.out.println("level2 zosta³ uruchomiony");
				StartFirmenwissen.startLevel2();
			} else
				System.out.println("level2 zosta³ pominiêty");
			if (properties.getProperty("level3").contains("1")) {
				System.out.println("level3 zosta³ uruchomiony");

			} else
				System.out.println("level3 zosta³ pominiêty");
		} else {
			System.out.println("Wykryto sesjê testow¹. Level1, Level2, Level3 , Level 4 zosta³y pominiête");
			if (properties.getProperty("test_level1").contains("1")) {
				System.out.println("zosta³ uruchomiony test_level1");
				testLevel1();
			} else
				System.out.println("test_level1 zosta³ pominiêty");

			if (properties.getProperty("test_level2").contains("1")) {
				System.out.println("zosta³ utuchomiony test_level2");
				testLevel2();

			} else
				System.out.println("test_level2 zosta³ pominiêty");
			if (properties.getProperty("test_level3").contains("1")) {
				System.out.println("Zosta³ uruchomiony test_level3");
				testLevel3();
			} else
				System.out.println("test_level3 zosta³ pominiêty");
		}
		// StartFirmenwissen.entityManagerFactory.close();

	}

	// Stworzenie tabeli pomocniczej
	public static void startLevel1() {
		FirmenwissenPreIndexRepository firmenwissenPreIndexRepository = new FirmenwissenPreIndexRepository(465000,
				StartFirmenwissen.entityManagerFactory);
		System.gc();

	}

	// scrape index
	public static void startLevel2() {
		System.out.println("Start level2 - INDEX");
		int threadsNumber = Integer.parseInt(properties.getProperty("numberOfThreads"));
		FirmenwissenIndexRepository[] indexRepository = new FirmenwissenIndexRepository[threadsNumber];
		for (int i = 0; i < threadsNumber; i++)
			indexRepository[i] = new FirmenwissenIndexRepository(i, System.getProperties(),
					StartFirmenwissen.entityManagerFactory);
		Thread[] threads = new Thread[threadsNumber];
		for (int i = 0; i < threadsNumber; i++)
			threads[i] = new Thread(indexRepository[i]);
		for (int i = 0; i < threadsNumber; i++)
			threads[i].start();
		System.out.println("LEVEL 2 END");
	}

	// scrape profile
	public static void startLevel3() {
		System.out.println("Start level3 - PROFILE");
		int threadsNumber = Integer.parseInt(properties.getProperty("numberOfThreads"));
		FirmenwissenProfilRepository[] indexRepository = new FirmenwissenProfilRepository[threadsNumber];
		for (int i = 0; i < threadsNumber; i++)
			indexRepository[i] = new FirmenwissenProfilRepository(i, System.getProperties(),
					StartFirmenwissen.entityManagerFactory);
		Thread[] threads = new Thread[threadsNumber];
		for (int i = 0; i < threadsNumber; i++)
			threads[i] = new Thread(indexRepository[i]);
		for (int i = 0; i < threadsNumber; i++)
			threads[i].start();
	}

	public static void testLevel1() {

	}

	public static void testLevel2() {

	}

	public static void testLevel3() {
		FirmenwissenGetProfil firmenwissenGetProfil = new FirmenwissenGetProfil(1, properties, entityManagerFactory,
				"http://www.firmenwissen.com/en/az/firmeneintrag/39126/3170284185/HONESTA_BAUELEMENTE_VERWALTUNGS_GMBH.html");		
		FirmenwissenGetProfil firmenwissenGetProfil2 = new FirmenwissenGetProfil(1, properties, entityManagerFactory,
				"https://www.firmenwissen.com/en/az/firmeneintrag/65203/6250164822/RHI_REFRACTORIES_SITE_SERVICES_GMBH.html");
		FirmenwissenGetProfil firmenwissenGetProfil3 = new FirmenwissenGetProfil(1, properties, entityManagerFactory,
				"https://www.firmenwissen.com/en/az/firmeneintrag/1700/1422122626/S.html");
		FirmenwissenGetProfil firmenwissenGetProfil4 = new FirmenwissenGetProfil(1, properties, entityManagerFactory,
				"https://www.firmenwissen.com/en/az/firmeneintrag/30625/2190042047/E_S_RUECKVERSICHERUNG_AG.html");
		FirmenwissenGetProfil firmenwissenGetProfil5 = new FirmenwissenGetProfil(1, properties, entityManagerFactory,
				"https://www.firmenwissen.com/en/az/firmeneintrag/63843/8010090101/M_S_ELEKTRONIK_AG.html");
		FirmenwissenGetProfil firmenwissenGetProfil6 = new FirmenwissenGetProfil(1, properties, entityManagerFactory,
				"https://www.firmenwissen.com/en/az/firmeneintrag/20457/2151466638/UNIFEEDER_GERMANY_BRANCH_OF_UNIFEEDER_A_S.html");
		FirmenwissenGetProfil firmenwissenGetProfil7 = new FirmenwissenGetProfil(1, properties, entityManagerFactory,
				"https://www.firmenwissen.com/en/az/firmeneintrag/34131/6150130305/K_S_AKTIENGESELLSCHAFT.html");
		FirmenwissenGetProfil firmenwissenGetProfil8 = new FirmenwissenGetProfil(1, properties, entityManagerFactory,
				"https://www.firmenwissen.com/en/az/firmeneintrag/20354/2150044197/SCHWARZMEER_UND_OSTSEE_VERSICHERUNGS_AKTIENGESELLSCHAFT_S_O_V_A_G.html");
		FirmenwissenGetProfil firmenwissenGetProfil9 = new FirmenwissenGetProfil(1, properties, entityManagerFactory,
				"https://www.firmenwissen.com/en/az/firmeneintrag/27751/2050841377/AMORIM_REVESTIMENTOS_S_A.html");
	}

}
