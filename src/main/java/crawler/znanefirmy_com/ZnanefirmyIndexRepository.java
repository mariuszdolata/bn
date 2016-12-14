package crawler.znanefirmy_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import crawler.api.Repository;

public class ZnanefirmyIndexRepository extends Repository implements Runnable {

	public ZnanefirmyIndexRepository(int threadId, Properties properties, EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		
	}

	/**
	 * Metoda w pierwszej kolejnoœci sprawdza czy wszystkie pierwsze strony bran¿ zosta³y zescrapowane.
	 * Scraping pierwszej strony:
	 *     - zapisanie danych do ZnanefirmyIndex
	 *     - aktualizacja informacji o liczbie firm
	 *     - generowanie linków dla poszczególnych bran¿
	 * W nastêpnym kroku scrapuje pozosta³oœæ indexu
	 * 	   - pobranie niezescrapowanych stron indexu
	 * 	   - zapisanie dancyh do ZnanefirmyIndex
	 */
	public void run() {
		// TODO Auto-generated method stub
		List<String> results = new ArrayList<String>();
		System.out.println("START watek numer ="+this.getThreadId());
	
		do{
			results.clear();
			EntityManager entityManager = this.getEntityManagerFactoryl().createEntityManager();
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery("SELECT b.url_branza FROM branze b WHERE b.liczba_firm is null ORDER BY rand() LIMIT 5");
			results = query.getResultList();
			entityManager.getTransaction().commit();
			entityManager.close();
			for(String s:results) System.out.println("url="+s);
			System.out.println("==============================================");
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(!results.isEmpty());
		
		
		
		
		
	}
	

}
