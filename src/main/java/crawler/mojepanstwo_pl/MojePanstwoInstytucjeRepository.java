package crawler.mojepanstwo_pl;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import crawler.api.DatabaseAccess;

public class MojePanstwoInstytucjeRepository extends DatabaseAccess implements Runnable{

	public MojePanstwoInstytucjeRepository(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		for(int i=2;i<=9;i++){
			MojePanstwoInstytucjeGet instytucjeGet = new MojePanstwoInstytucjeGet(1, properties, entityManagerFactory,
					"https://api-v3.mojepanstwo.pl/dane/instytucje?limit=500&_type=objects&page="+i);
		}
	
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

}
