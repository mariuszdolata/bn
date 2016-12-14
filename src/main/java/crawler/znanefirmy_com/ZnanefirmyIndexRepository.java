package crawler.znanefirmy_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import crawler.api.DatabaseAccess;

public class ZnanefirmyIndexRepository extends DatabaseAccess implements Runnable {

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
		List<Object[]> results = new ArrayList<Object[]>();
		System.out.println("START watek numer ="+this.getThreadId());
	
		do{
			results.clear();
			EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
			entityManager.getTransaction().begin();
			//pobranie listy URL do scrapowania
			Query query = entityManager.createNativeQuery("SELECT b.url_branza, b.nazwa FROM branze b WHERE b.liczba_firm is null ORDER BY rand()").setMaxResults(5);
			
			results = query.getResultList();
			for(Object[] o:results){ 
				System.out.println("url index = "+o[0]);
				ZnanefirmyGetIndex znanefirmyGetIndex = new ZnanefirmyGetIndex(this.getThreadId(), this.getProperties(), this.getEntityManagerFactory(), o[0].toString());
				//Aktualizacja liczby firm dla danej branzy
				String sqlUpdate = "Update branze SET liczba_firm=? WHERE url_branza like ?";
				int updatesRecords = entityManager.createNativeQuery(sqlUpdate).setParameter(1, znanefirmyGetIndex.getMaxPagination()).setParameter(2, o[0]).executeUpdate();
				for(int i=2;i<=znanefirmyGetIndex.getMaxPagination();i++){
					String sqlInsert = "INSERT INTO pre_index (nazwa, url) VALUE (?,?)";
					String urlAddress = o[0].toString().substring(0, o[0].toString().length()-5);
					urlAddress=urlAddress+","+Integer.toString(i)+".html";
					int insertRecords = entityManager.createNativeQuery(sqlInsert).setParameter(1, o[1]).setParameter(2, urlAddress).executeUpdate();
					System.out.println("Pagination = "+insertRecords);
					entityManager.flush();
				}
				System.out.println("Liczba rekordów uaktualniona : "+updatesRecords +"\n"+sqlUpdate);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			entityManager.getTransaction().commit();
			entityManager.close();
			System.out.println("==============================================");
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}while(!results.isEmpty());
	}while(false);
		
		
		
		
		
	}
	

}
