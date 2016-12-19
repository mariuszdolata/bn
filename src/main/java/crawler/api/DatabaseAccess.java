package crawler.api;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;

public class DatabaseAccess {
	public final static Logger logger=Logger.getLogger(DatabaseAccess.class);
	private int threadId;
	private Properties properties;
	private static  EntityManagerFactory entityManagerFactory;
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
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
	public void setEntityManagerFactoryl(EntityManagerFactory entityManagerFactoryl) {
		this.entityManagerFactory = entityManagerFactoryl;
	}
	public DatabaseAccess(int threadId, Properties properties, EntityManagerFactory entityManagerFactory) {
		super();
		this.threadId = threadId;
		this.properties = properties;
		this.entityManagerFactory = entityManagerFactory;
	}
	
	

}
