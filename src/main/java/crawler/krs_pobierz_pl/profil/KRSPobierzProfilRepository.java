package crawler.krs_pobierz_pl.profil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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
		do {
			try {

				// ApplicationContext context = new
				// AnnotationConfigApplicationContext(DatabaseConfig.class);
				// KrspobierzBean profilRepository =
				// context.getBean("profilRepository",
				// KrspobierzBean.class);
				EntityManager em = this.getEntityManagerFactory().createEntityManager();
				List<KRSProfilIndex> listToScrape = new ArrayList<KRSProfilIndex>();
				do {
					listToScrape.clear();
					// Pobieranie po 10 elementow
					TypedQuery<KRSProfilIndex> q = em
							.createQuery("SELECT i FROM KRSProfilIndex i WHERE status is null order by rand()",
									KRSProfilIndex.class)
							.setMaxResults(20);
					listToScrape = q.getResultList();
					for (KRSProfilIndex i : listToScrape) {
						System.gc();
						logger.info("url=" + i.getUrl());
						try {

							KRSPobierzProfilGet profilGet = new KRSPobierzProfilGet(this.getThreadId(),
									this.getProperties(), this.getEntityManagerFactory(), i.getUrl());
							// KRSPobierzProfilGet profilGet = new
							// KRSPobierzProfilGet(this.getThreadId(),
							// this.getProperties(),
							// this.getEntityManagerFactory(),
							// "http://krs-pobierz.pl/przedsiebiorstwo-wielobranzowe-dabex-i231605");
							em.getTransaction().begin();
							i.setStatus(Integer.toString(profilGet.getStatusCode()));
							em.getTransaction().commit();
						} catch (Exception e) {
							logger.error("Prawdopodobnie profil nie jest zescrapowany!!!");
							logger.error(e.getMessage());
							logger.error(e.getCause());
							logger.error(e.getLocalizedMessage());
						}

						// Opoznienie
						// Random gen = new Random();
						// Enumeration keys = this.getProperties().elements();
						// while(keys.hasMoreElements()){
						// String key=(String)keys.nextElement();
						// String
						// value=(String)this.getProperties().getProperty(key);
						//
						// }
						// Integer nextInt =
						// gen.nextInt(Integer.parseInt(this.getProperties().getProperty("randDelayMax"))+Integer.parseInt(this.getProperties().getProperty("delay")));
						// logger.info("Opoznienie miedzy kolejnymi profilami
						// wynioslo="+nextInt);
						// long start=System.currentTimeMillis();
						// try {
						// Thread.sleep(nextInt);
						// } catch (InterruptedException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						// long end = System.currentTimeMillis();
						// logger.info("Czas wykonania="+(end-start));

					}
				} while (!listToScrape.isEmpty());

				em.close();
			} catch (Exception e) {
				logger.error("Wznowienie watku KRSPobierz Profil Repository");
			}
		} while (!Thread.interrupted());
		this.getEntityManagerFactory().close();

	}

}
