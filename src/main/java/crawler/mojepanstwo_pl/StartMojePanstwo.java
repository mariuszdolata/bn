package crawler.mojepanstwo_pl;

import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import crawler.api.StartCrawler;
import crawler.firmenwissen_com.StartFirmenwissen;

public class StartMojePanstwo extends StartCrawler {
	public final static Logger logger = Logger.getLogger(StartFirmenwissen.class);

	public static void main(String[] args) {
		try{
			StartMojePanstwo.entityManagerFactory = Persistence.createEntityManagerFactory("polska");
			
		}catch(Exception e){
			logger.error("Nie mo¿na stworzyc entityManagerFactory");
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
			e.printStackTrace();
		}
		testing();
		StartMojePanstwo.entityManagerFactory.close();

	}
	
	public static void testing(){
		ShowTestResultMojePanstwo testResult = new ShowTestResultMojePanstwo(entityManagerFactory);
		testResult.findPkd("01.12.Z");
		testResult.findPkd("01.25.Z");
	}

}
