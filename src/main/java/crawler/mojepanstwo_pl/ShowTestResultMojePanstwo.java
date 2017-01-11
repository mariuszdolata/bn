package crawler.mojepanstwo_pl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dbstructure.PKD;

public class ShowTestResultMojePanstwo {
	private EntityManagerFactory entityManagerFactory;

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public ShowTestResultMojePanstwo(EntityManagerFactory entityManagerFactory) {
		super();
		this.entityManagerFactory = entityManagerFactory;
	}
	
	public void findPkd(String pkdKod){
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		PKD p = entityManager.find(PKD.class, 1L);
		List<PKD> lista = entityManager.createQuery("SELECT p FROM PKD p WHERE kod like ?", PKD.class).setParameter(1, pkdKod)
                .getResultList();
		Long iij = lista.get(0).getId();
		System.out.println("id="+iij);
		for(PKD pp:lista)System.out.println(pp.toString());
		PKD temp = entityManager.find(PKD.class, iij);
		System.out.println("temp="+temp.toString());

		
		entityManager.getTransaction().commit();
		
		entityManager.close();
	}
	

}
