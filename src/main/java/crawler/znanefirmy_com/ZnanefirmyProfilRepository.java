package crawler.znanefirmy_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import crawler.api.DatabaseAccess;

public class ZnanefirmyProfilRepository extends DatabaseAccess implements Runnable {
	
	public final static Logger logger=Logger.getLogger(ZnanefirmyProfilRepository.class);

	public ZnanefirmyProfilRepository(Properties properties, int threadId, EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		
	}

	public void run() {
		System.out.println("ProfilRepository watek nr " + this.getThreadId());
		List<String> results = new ArrayList<String>();
		do{
			results.clear();
			EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
			entityManager.getTransaction().begin();
			Query q=entityManager.createNativeQuery("SELECT distinct url FROM znanefirmy_com.znanefirmyindex order by rand() limit 50");
			results = q.getResultList();
			entityManager.getTransaction().commit();		
			for(String urlToScrape:results){
				try{
//					logger.info("Watek nr "+this.getThreadId() + " urlToScrape="+urlToScrape);
					ZnanefirmyGetProfil znanefirmyGetProfil = new ZnanefirmyGetProfil(this.getThreadId(), this.getProperties(), this.getEntityManagerFactory(), urlToScrape);
					if(znanefirmyGetProfil.getStatusCode()==200){
//						logger.info("ProfiurlToScrapepisany dla url="+urlToScrape);
						//aktualizacja STATUS w tabeli znanefirmyIndex
						String sqlUpdate="UPDATE ZnanefirmyIndex set status='done' WHERE url like ?";
						entityManager.getTransaction().begin();
						try{
							int updates = entityManager.createNativeQuery(sqlUpdate).setParameter(1, urlToScrape).executeUpdate();
//							logger.info("zaktualizowano "+updates + " statusow w tabeli ZnanefirmyIndex");
							logger.info("WSTAWIONO PROFIL idHost="+this.getProperties().getProperty("idHost")+", threadId="+this.getThreadId()+", url="+urlToScrape);
						}catch(Exception e) {
							logger.error("BLAD aktualizacji statusu dla url="+urlToScrape+"\n"+e.getMessage());
						}
						entityManager.getTransaction().commit();
					}
					else{
						logger.warn("BLAD POBRANIA PROFILU dla url="+urlToScrape+"\n HTML Code Response = "+znanefirmyGetProfil.getStatusCode());
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
				}catch(Exception e){
					logger.error("blad profila dla url="+urlToScrape);
				}

			}
			entityManager.close();
		}while(!results.isEmpty());	
//		}while(false);
	}

	
}
