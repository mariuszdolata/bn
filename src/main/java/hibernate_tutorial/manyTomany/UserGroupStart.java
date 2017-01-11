package hibernate_tutorial.manyTomany;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserGroupStart {

	public static void main(String[] args) {
		try{
			EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("hibernate");
			
		
//		User user = new User("Mat", "ma", "matt@gmail.com");
//		Group group = new Group("Graphics");
//		 
//		UserGroup userGroup = new UserGroup();
//		userGroup.setGroup(group);
//		userGroup.setUser(user);
//		userGroup.setActivated(true);
//		userGroup.setRegisteredDate(new Date());
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		UserGroup userGroup = new UserGroup();
		
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, 14L);
		Group group = entityManager.find(Group.class, 7L);
		userGroup.setGroup(group);
		userGroup.setUser(user);
		userGroup.setRegisteredDate(new Date());
		userGroup.setActivated(false);
		
		entityManager.persist(userGroup);
		entityManager.getTransaction().commit();
		
		
		entityManager.close();
		
		entityManagerFactory.close();
		}
		catch(Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
