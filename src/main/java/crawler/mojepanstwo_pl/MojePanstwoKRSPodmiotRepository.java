package crawler.mojepanstwo_pl;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import crawler.api.DatabaseAccess;

public class MojePanstwoKRSPodmiotRepository extends DatabaseAccess implements Runnable {

	public MojePanstwoKRSPodmiotRepository(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		Boolean listToScrape = false;
		do {
			listToScrape = false;
			EntityManager em = StartMojePanstwoKRSPodmiot.entityManagerFactory.createEntityManager();
			TypedQuery<ID> q = em
					.createQuery("SELECT i FROM ID i WHERE id between 10000 and 1000000 ORDER BY rand()", ID.class)
					.setMaxResults(50);
			List<ID> lista = q.getResultList();
			if (!lista.isEmpty())
				listToScrape = true;
			for (ID i : lista) {
				em.getTransaction().begin();
				logger.info("ID i=" + i.toString());
				MojePanstwoKRSPodmiotGet getProfil1 = new MojePanstwoKRSPodmiotGet(1, this.getProperties(),
						this.getEntityManagerFactory(), "https://api-v3.mojepanstwo.pl/dane/krs_podmioty/" + i.getId()
								+ ".json?layers[]=dzialalnosci&layers[]=emisje_akcji&layers[]=firmy&layers[]=graph&layers[]=jedynyAkcjonariusz&layers[]=komitetZalozycielski&layers[]=reprezentacja&layers[]=nadzor&layers[]=oddzialy&layers[]=prokurenci&layers[]=wspolnicy");

				i.setStatus(Integer.toString(getProfil1.getStatusCode()));
				em.getTransaction().commit();
			}
			em.close();
		} while (listToScrape);

	}

}
