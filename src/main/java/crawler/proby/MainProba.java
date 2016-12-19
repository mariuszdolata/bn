package crawler.proby;

import java.lang.reflect.Field;
import org.apache.log4j.Logger;

public class MainProba {

	final static Logger logger = Logger.getLogger(MainProba.class);
	public static void main(String[] args) {
		System.out.println("Main proba");
		logger.info("#################################################################################");
		logger.debug("logger debug");
		logger.info("logger info");
		logger.error("logger error");
		logger.fatal("logger fatal");
		logger.getLevel();

		MainProba mainProba = new MainProba();
		try{
			int	score = mainProba.devide(0);
			logger.info("Wynik = "+score);
		}catch(Exception e){
			logger.error("Devide error" + e.getMessage());
			logger.error(mainProba.logger.hashCode());
		}
		

	}
	public static int devide(int mianownik){
		return 10/mianownik;
	}

}
