package crawler.mojepanstwo_pl;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import crawler.api.DatabaseAccess;

public class MojePanstwoKRSPodmiotRepository extends DatabaseAccess implements Runnable{

	public MojePanstwoKRSPodmiotRepository(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

}
