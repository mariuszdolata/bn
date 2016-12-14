package crawler.api;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

public class Repository {
	private int threadId;
	private Properties properties;
	private EntityManagerFactory entityManagerFactory;
	public int getThreadId() {
		return threadId;
	}
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public EntityManagerFactory getEntityManagerFactoryl() {
		return entityManagerFactory;
	}
	public void setEntityManagerFactoryl(EntityManagerFactory entityManagerFactoryl) {
		this.entityManagerFactory = entityManagerFactoryl;
	}
	public Repository(int threadId, Properties properties, EntityManagerFactory entityManagerFactory) {
		super();
		this.threadId = threadId;
		this.properties = properties;
		this.entityManagerFactory = entityManagerFactory;
	}
	
	

}
