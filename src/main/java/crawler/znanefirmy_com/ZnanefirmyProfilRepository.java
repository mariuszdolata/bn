package crawler.znanefirmy_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import crawler.api.DatabaseAccess;

public class ZnanefirmyProfilRepository extends DatabaseAccess implements Runnable {
	public static EntityManagerFactory entityManagerFactory;

	public ZnanefirmyProfilRepository(Properties properties, int threadId, EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		
	}

	public void run() {
		System.out.println("ProfilRepository watek nr " + this.getThreadId());
		List<String> nazwy = new ArrayList<String>();
		do{
			nazwy.clear();
			EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
			entityManager.getTransaction().begin();
			Query q=entityManager.createNativeQuery("SELECT nazwa FROM branze order by rand() LIMIT 5");
			nazwy = q.getResultList();
			for(String s:nazwy)System.out.println("Watek nr "+this.getThreadId() + "nazwa="+s);
			entityManager.getTransaction().commit();		
			entityManager.close();
			
		}while(false);
	}

	
}
