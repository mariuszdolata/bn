package crawler.biznesfinder_pl;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import crawler.api.DatabaseAccess;

public class BiznesFinderIndexRepository extends DatabaseAccess implements Runnable {

	public BiznesFinderIndexRepository(int threadId, Properties properties, EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Klasa BiznesFinderIndexGet ma dodatkowy konstruktor, poniewaz konieczne jest przeslanie obiektu WebDriver driver
	 * tak aby iterujac kolejne strony unikn¹æ wielokrotnego okna przegladarki
	 */
	public void run() {
		WebDriver driver = new FirefoxDriver();
		BiznesFinderIndexGet indexGet = new BiznesFinderIndexGet(1, this.getProperties(), this.getEntityManagerFactory(), driver);
		
	}

}
