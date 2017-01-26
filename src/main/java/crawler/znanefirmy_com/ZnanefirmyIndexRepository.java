package crawler.znanefirmy_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crawler.api.DatabaseAccess;

public class ZnanefirmyIndexRepository extends DatabaseAccess implements Runnable {

	public ZnanefirmyIndexRepository(int threadId, Properties properties, EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);

	}
	

	/**
	 * Metoda w pierwszej kolejnoœci sprawdza czy wszystkie pierwsze strony
	 * bran¿ zosta³y zescrapowane. Scraping pierwszej strony: - zapisanie danych
	 * do ZnanefirmyIndex - aktualizacja informacji o liczbie firm - generowanie
	 * linków dla poszczególnych bran¿ W nastêpnym kroku scrapuje pozosta³oœæ
	 * indexu - pobranie niezescrapowanych stron indexu - zapisanie dancyh do
	 * ZnanefirmyIndex
	 */
	public void run() {
		logger.info("START znanefirmyIndexRepository, watek nr=" + this.getThreadId());
//		 this.startFirstPage();
		this.startNextPages();
	}

	public void startFirstPage() {
		logger.info("START znanefirmyIndexRepository[" + this.getThreadId() + "].startFirstPage()");
		List<Object[]> results = new ArrayList<Object[]>();
		do {
			results.clear();
			EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
			entityManager.getTransaction().begin();
			// pobranie listy URL do scrapowania - tylko 5 wyników na jedno
			// zapytanie - zapêtlenie
			Query query = entityManager
					.createNativeQuery(
							"SELECT b.url_branza, b.nazwa FROM branze b WHERE b.liczba_firm is null ORDER BY rand()")
					.setMaxResults(5);
			results = query.getResultList();
			entityManager.getTransaction().commit();
			try{
			for (Object[] o : results) {
				entityManager.getTransaction().begin();
				logger.info("url=" + o[0] + ", nazwa=" + o[1]);
				// pobranie jednej strony
				ZnanefirmyGetIndex znanefirmyGetIndex = new ZnanefirmyGetIndex(this.getThreadId(), this.getProperties(),
						this.getEntityManagerFactory(), o[0].toString());
				// Aktualizacja liczby firm dla danej branzy
				String sqlUpdate = "Update branze SET liczba_firm=? WHERE url_branza like ?";
				if (znanefirmyGetIndex.getMaxPagination() >= 2) {
					int updatesRecords = entityManager.createNativeQuery(sqlUpdate)
							.setParameter(1, znanefirmyGetIndex.getMaxPagination()).setParameter(2, o[0])
							.executeUpdate();
					logger.info("Liczba rekordow uaktualniona w tabeli BRANZE=" + updatesRecords + ", idThread="+this.getThreadId()+", zapytanie ="
							+ sqlUpdate + ", par1=" + znanefirmyGetIndex.getMaxPagination() + ", par2=" + o[0]);
				}
				for (int i = 2; i <= znanefirmyGetIndex.getMaxPagination(); i++) {
					try {
						String sqlInsert = "INSERT INTO pre_index (nazwa, url) VALUE (?,?)";
						String urlAddress = o[0].toString().substring(0, o[0].toString().length() - 5);
						urlAddress = urlAddress + "," + Integer.toString(i) + ".html";
						int insertRecords = entityManager.createNativeQuery(sqlInsert).setParameter(1, o[1])
								.setParameter(2, urlAddress).executeUpdate();
						System.out.println("Pagination = " + insertRecords);
						entityManager.flush();
						logger.info("PRE_INDEX - wstawienie wartoœæi i=" + i + ", url=" + o[0]);
					} catch (Exception e) {
						logger.error("ERROR - PRE_INDEX - wstawienie wartoœæi i=" + i + ", url=" + o[0]);
						e.printStackTrace();
					}

				}
				try {
					Thread.sleep(Integer.parseInt(this.getProperties().getProperty("delay")));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				entityManager.getTransaction().commit();
			}
			entityManager.getTransaction().commit();
			entityManager.close();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}catch(Exception e){logger.error("B³¹d ZnanefirmyIndexRepository - blad podczas wczytania/parsowania strony");}
		} while (!results.isEmpty());
		// } while (false);
	}

	public void startNextPages() {
		logger.info("START znanefirmyIndexRepository[" + this.getThreadId() + "].startNextPage()");
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();

		List<String> results = new ArrayList<String>();
		do {
			results.clear();
			entityManager.getTransaction().begin();
			String sqlSelect = "SELECT url FROM znanefirmy_com.pre_index WHERE status is null ORDER by rand() LIMIT 5";
			results = entityManager.createNativeQuery(sqlSelect).getResultList();
			entityManager.getTransaction().commit();
			for (String o : results) {
				entityManager.getTransaction().begin();
				ZnanefirmyGetIndex znanefirmyGetIndex = new ZnanefirmyGetIndex(this.getThreadId(), this.getProperties(),
						this.getEntityManagerFactory(), o.toString());
				String sqlUpdate = "UPDATE pre_index SET status='done' WHERE url like ?";
				try {
					int numberOfResults = entityManager.createNativeQuery(sqlUpdate).setParameter(1, o).executeUpdate();
					logger.info("ZnanefirmyIndexRepository.startNextPage() - > status=DONE dla url=" + o);
				} catch (Exception e) {
					logger.error("BLAD ZnanefirmyIndexRepository.startNextPage() - > status=NULL dla url=" + o + "\n"
							+ e.getMessage());
				}
				entityManager.getTransaction().commit();

			}
		} while (!results.isEmpty());
		// }while(false);
	}

}
