package crawler.mojepanstwo_pl;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import crawler.api.DatabaseAccess;

public class MojePanstwoZamowieniaPubliczneZamawiajacyRepository extends DatabaseAccess implements Runnable {

	public MojePanstwoZamowieniaPubliczneZamawiajacyRepository(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		for (int i = 1; i <= 86; i++) {
			MojePanstwoZamowieniaPubliczneZamawiajacyGet zamawiajacyGet = new MojePanstwoZamowieniaPubliczneZamawiajacyGet(
					1, this.getProperties(), this.getEntityManagerFactory(),
					"https://api-v3.mojepanstwo.pl/dane/zamowienia_publiczne_zamawiajacy?limit=500&_type=objects&page="+i);

		}
	}

}
