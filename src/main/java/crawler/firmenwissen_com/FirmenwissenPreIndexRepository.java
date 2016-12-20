package crawler.firmenwissen_com;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class FirmenwissenPreIndexRepository {
	private List<FirmenwissenPreIndex> index = new ArrayList<FirmenwissenPreIndex>();

	public List<FirmenwissenPreIndex> getIndex() {
		return index;
	}

	public void setIndex(List<FirmenwissenPreIndex> index) {
		this.index = index;
	}
	public FirmenwissenPreIndexRepository(int numberOfSites, EntityManagerFactory entityManagerFactory){
		for(int i=0;i<numberOfSites;i++){
			this.getIndex().add(new FirmenwissenPreIndex(i));
		}
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		int iter=0;
		
		for(FirmenwissenPreIndex firmen:this.getIndex()){
			entityManager.persist(firmen);
			iter++;
			if(iter%100==0){
				System.err.println("COMMIT");
				entityManager.getTransaction().commit();
//				entityManager.flush();
				entityManager.clear();
				entityManager.getTransaction().begin();
			}
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		
	}

}
