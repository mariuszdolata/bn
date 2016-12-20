package crawler.firmenwissen_com;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import crawler.api.DatabaseAccess;

public class FirmenwissenProfilRepository extends DatabaseAccess implements Runnable{

	public FirmenwissenProfilRepository(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

}
