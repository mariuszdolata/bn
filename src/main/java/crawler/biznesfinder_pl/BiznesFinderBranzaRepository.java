package crawler.biznesfinder_pl;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import crawler.api.DatabaseAccess;

/**
 * Zaleca siê zrobienie indexu branz jednym watkiem.
 * 
 * @author mariusz
 *
 */
public class BiznesFinderBranzaRepository extends DatabaseAccess implements Runnable {

	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public BiznesFinderBranzaRepository(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		// jeden driver do obslugi wszystkich branz
		this.setDriver(new FirefoxDriver());

	}

	public void run() {
//		for (char a = 'a'; a <= 'z'; a++) {
//			// dla poni¿szych 3 liter nie ma strony z branzami. Dodaj za to
//			// litery £, Œ, ¯!!!
//			scrape(a);
//		}
		
		//DODAJ JESZCZE 3 LITERY !!!
		scrape('³');
		scrape('œ');
		scrape('¿');
	}
	public void scrape(char a){
		if (a != 'x' && a != 'y' && a != 'v') {
			String urlPage="http://www.biznesfinder.pl/mapa-branzy/"+String.valueOf(a);
			do{					
				BiznesFinderBranzaGet branzaGet = new BiznesFinderBranzaGet(this.getThreadId(), this.getProperties(), this.getEntityManagerFactory(), urlPage, this.getDriver());
				urlPage=branzaGet.getNextPageUrl();
			}while(urlPage!=null);
		}
	}

}
