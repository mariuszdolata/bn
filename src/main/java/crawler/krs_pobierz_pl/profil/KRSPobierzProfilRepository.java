package crawler.krs_pobierz_pl.profil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import crawler.api.DatabaseAccess;

public class KRSPobierzProfilRepository extends DatabaseAccess implements Runnable {
	private int number;
	private int idHost;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getIdHost() {
		return idHost;
	}

	public void setIdHost(int idHost) {
		this.idHost = idHost;
	}

	public KRSPobierzProfilRepository(int threadId, Properties properties, EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		// ApplicationContext context = new
		// AnnotationConfigApplicationContext(DatabaseConfig.class);
		// KrspobierzBean profilRepository = context.getBean("profilRepository",
		// KrspobierzBean.class);
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		List<KRSProfilIndex> listToScrape = new ArrayList<KRSProfilIndex>();
//		do {
			listToScrape.clear();
			//Pobieranie po 10 elementow
			TypedQuery<KRSProfilIndex> q = em
					.createQuery("SELECT i FROM KRSProfilIndex i WHERE status is null order by rand()",
							KRSProfilIndex.class)
					.setMaxResults(10);
			listToScrape = q.getResultList();
			for(KRSProfilIndex i:listToScrape){
				logger.info("url="+i.getUrl());
				KRSPobierzProfilGet profilGet = new KRSPobierzProfilGet(getThreadId(), getProperties(), getEntityManagerFactory(), i.getUrl());
				em.getTransaction().begin();
				i.setStatus(Integer.toString(profilGet.getStatusCode()));
				em.getTransaction().commit();
			}
//		} while (!listToScrape.isEmpty());

		em.close();



	}

}
