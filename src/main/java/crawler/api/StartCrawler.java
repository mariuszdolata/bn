package crawler.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract  class StartCrawler {
	public Properties properties;
	
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public static Properties loadProperties(String filePath){
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
