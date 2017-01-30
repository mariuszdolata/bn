package crawler.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

public abstract class StartCrawler {
	public static Properties properties;
	public static EntityManagerFactory entityManagerFactory;

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public static Properties loadProperties(String filePath) {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
		System.setProperty("webdriver.gecko.driver", "C:\\crawlers\\drivers\\geckodriver.exe");
		System.setProperty("webdriver.opera.driver", "C:\\crawlers\\drivers\\operadriver.exe");
		System.setProperty("webdriver.chrome.driver", "C:\\crawlers\\drivers\\chromedriver.exe");
		Properties properties = new Properties();
		InputStream input = null;
		int numberOfThread = 99;
		int idHost = 99;

		try {
			input = new FileInputStream(filePath);
			// load a properties file
			properties.load(input);
			// get the property value and print it out
			System.out.println("idHost =" + properties.getProperty("idHost"));
			idHost = Integer.parseInt(properties.getProperty("idHost"));
			System.out.println("numberOfThreads =" + properties.getProperty("numberOfThreads"));
			numberOfThread = Integer.parseInt(properties.getProperty("numberOfThreads"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

	
	
}
