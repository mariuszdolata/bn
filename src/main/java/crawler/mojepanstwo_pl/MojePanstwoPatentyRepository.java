package crawler.mojepanstwo_pl;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import crawler.api.DatabaseAccess;

public class MojePanstwoPatentyRepository extends DatabaseAccess implements Runnable {

	public MojePanstwoPatentyRepository(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
	}

	/**
	 * Pojedyncze wczytywanie id. Strona zawiera 500 rekordów i odpowiedz serwera moze zajaæ do kilku sekund.
	 */
	public void run() {
		Boolean listToScrape = false;
		do {
			listToScrape = false;
			EntityManager em = StartMojePanstwoKRSPodmiot.entityManagerFactory.createEntityManager();
			TypedQuery<ID> q = em
					.createQuery("SELECT i FROM ID i WHERE id <=201 AND status_patenty is null ORDER BY rand()",
							ID.class)
					.setMaxResults(1);
			List<ID> lista = q.getResultList();
			if (!lista.isEmpty())
				listToScrape = true;
			for (ID i : lista) {
				em.getTransaction().begin();
				logger.info("ID i=" + i.toString());
				MojePanstwoPatentyGet patentyGet = new MojePanstwoPatentyGet(1, this.getProperties(),
						this.getEntityManagerFactory(), "https://api-v3.mojepanstwo.pl/dane/patenty?limit=500&_type=objects&page="+i.getId());

				i.setStatus_patenty(Integer.toString(patentyGet.getStatusCode()));
				em.getTransaction().commit();
			}
			em.close();
		} while (listToScrape);

	}

}
