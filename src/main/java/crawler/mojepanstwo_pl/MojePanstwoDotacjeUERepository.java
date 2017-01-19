package crawler.mojepanstwo_pl;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import crawler.api.DatabaseAccess;

public class MojePanstwoDotacjeUERepository extends DatabaseAccess implements Runnable {

	public MojePanstwoDotacjeUERepository(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		for (int i = 179; i <= 180; i++) {
			MojePanstwoDotacjeUEGet ueGet = new MojePanstwoDotacjeUEGet(1, this.getProperties(),
					this.getEntityManagerFactory(),
					"https://api-v3.mojepanstwo.pl/dane/dotacje_ue?limit=500&_type=objects&page=" + i);

		}
		

	}

}
