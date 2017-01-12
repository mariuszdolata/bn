package crawler.mojepanstwo_pl;

import java.util.Date;

import javax.persistence.Persistence;

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
		}finally{
//			StartMojePanstwoKRSPodmiot.entityManagerFactory.close();
		}
		if (!properties.getProperty("test_level1").contains("1")) {
			logger.info("Pominiêto sesjê testow¹");
			if (properties.getProperty("level1").contains("1")) {
				logger.info("level 1 - scraping - KRSPodmiot");
			} else {
				logger.info("level 1 - pominiêty - b³ad w properties");
			}
		} else {
			logger.info("Wykryto sesjê testow¹");
			if (properties.getProperty("test_level1").contains("1")) {
				logger.info("level 1 - test");
				logger.info("start:="+new Date());
				for(int i=23302;i<23303;i++){
					MojePanstwoKRSPodmiotGet getProfil1 = new MojePanstwoKRSPodmiotGet(1, properties, entityManagerFactory,
							"https://api-v3.mojepanstwo.pl/dane/krs_podmioty/"+i+".json?layers[]=dzialalnosci&layers[]=emisje_akcji&layers[]=firmy&layers[]=graph&layers[]=jedynyAkcjonariusz&layers[]=komitetZalozycielski&layers[]=reprezentacja&layers[]=nadzor&layers[]=oddzialy&layers[]=prokurenci&layers[]=wspolnicy");					
				}
				logger.info("koniec:="+new Date());
			StartMojePanstwoKRSPodmiot.entityManagerFactory.close();
			} else
				logger.info("level 1 test - pominiêty");
		}

	}

}
