package crawler.mojepanstwo_pl;

import java.util.Date;

import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import crawler.api.StartCrawler;

/**
 * Klasa sluzy do pobrania danych ze zbioru "ZamowieniaPubliczneZamawiajacy"
 * W styczniu 2017 powinno byæ oko³o 42k rekodów z adresami email oraz website.
 * Program parsuje dane z Json po 500 rekordów na stronie - nie ma potrzeby wykorzystywania 
 * wielow¹tkowoœci (86 stron po 500 wyników)
 * Dane zapisywane s¹ tylko do jednej tabeli "zamowienia_publiczne_zamawiajacy" w bazie "moje_panstwo"
 * @author mariusz
 *
 */
public class StartMojePanstwoZamowieniaPubliczneZamawiajacy extends StartCrawler {
	final static Logger logger = Logger.getLogger(StartMojePanstwoZamowieniaPubliczneZamawiajacy.class);

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
				startScrapingZamowieniaPubliczneZamawiajacy();
			} else {
				logger.info("level 1 - pominiêty - b³ad w properties");
			}
		} else {
			logger.info("Wykryto sesjê testow¹");
			if (properties.getProperty("test_level1").contains("1")) {
				logger.info("level 1 - test");
				logger.info("start:=" + new Date());
				startTestScrapingZamowieniaPubliczneZamawiajacy();
				logger.info("koniec:=" + new Date());
				StartMojePanstwoKRSPodmiot.entityManagerFactory.close();
			} else
				logger.info("level 1 test - pominiêty");
		}
	}
/**
 * metoda testuj¹ca czy parser dzia³a - wczytuje tylko jedn¹ stronê z 50 rekordami
 */
	private static void startTestScrapingZamowieniaPubliczneZamawiajacy() {
		MojePanstwoZamowieniaPubliczneZamawiajacyGet zamawiajacyGet = new MojePanstwoZamowieniaPubliczneZamawiajacyGet(
				1, properties, entityManagerFactory,
				"https://api-v3.mojepanstwo.pl/dane/zamowienia_publiczne_zamawiajacy/?_type=objects&page=20");
	}

	/**
	 * metoda wywo³uj¹ca obiekt Repository dla tej klasy. To w niej nastêpuje wczytanie 84 podstron w petli for
	 * nie ma koniecznosci robienia wielowatkowosci.
	 */
	private static void startScrapingZamowieniaPubliczneZamawiajacy() {

		MojePanstwoZamowieniaPubliczneZamawiajacyRepository repository = new MojePanstwoZamowieniaPubliczneZamawiajacyRepository(
				1, properties, entityManagerFactory);
		repository.run();
	}

}
