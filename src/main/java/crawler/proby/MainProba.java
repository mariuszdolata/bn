package crawler.proby;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
		
		
		try {
			int number = 5;
			Class[] c = new Class[number];
			for(int i=0;i<number;i++){
				c[i]=Class.forName("crawler.proby.Proba");
			}
			for(int i=0;i<number;i++){
				System.out.println("iteracja = "+i+c[i].getSimpleName());
			}
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
