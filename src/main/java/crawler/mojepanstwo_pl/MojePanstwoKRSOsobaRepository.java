package crawler.mojepanstwo_pl;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import crawler.api.DatabaseAccess;

public class MojePanstwoKRSOsobaRepository extends DatabaseAccess implements Runnable {

	public MojePanstwoKRSOsobaRepository(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		Boolean listToScrape = false;
		do {
			listToScrape = false;
			EntityManager em = StartMojePanstwoKRSPodmiot.entityManagerFactory.createEntityManager();
			//KRS powinno zawierac sie w przedziale <1, 700k) - sprawdz po scrapingu slusznosc tego wpisu
			TypedQuery<ID> q = em
					.createQuery("SELECT i FROM ID i WHERE id <2000000 AND status_osoba is null ORDER BY rand()", ID.class)
					.setMaxResults(50);
			List<ID> lista = q.getResultList();
			if (!lista.isEmpty())
				listToScrape = true;
			for (ID i : lista) {
				em.getTransaction().begin();
				logger.info("ID i=" + i.toString());
				MojePanstwoKRSOsobaGet getOsoba = new MojePanstwoKRSOsobaGet(1, this.getProperties(),
						this.getEntityManagerFactory(), "https://api-v3.mojepanstwo.pl/dane/krs_osoby/"+i.getId());

				i.setStatus_osoba(Integer.toString(getOsoba.getStatusCode()));
				em.getTransaction().commit();
			}
			em.close();
		} while (listToScrape);

		
	}

}
