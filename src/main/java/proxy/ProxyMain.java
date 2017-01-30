package proxy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ProxyMain {
	private final static Logger logger=Logger.getLogger(ProxyMain.class);
	private final static Properties properties = ProxyMain.loadProperties("C:\\crawlers\\properties\\proxies_settings.properties");
	private  static int hostId;
	private  static int numberofThreads;
	


	public static int getHostId() {
		return hostId;
	}

	public static void setHostId(int hostId) {
		ProxyMain.hostId = hostId;
	}

	public static int getNumberofThreads() {
		return numberofThreads;
	}

	public static void setNumberofThreads(int numberofThreads) {
		ProxyMain.numberofThreads = numberofThreads;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void main(String[] args) {
	
		createThreads(ProxyMain.numberofThreads);

	}

	static void createThreads(int threadNumber) {
		String[] proxiesAddr = {"123.30.238.16:3128", "213.240.61.42:80", "112.27.207.38:8118"};
		logger.info("Testowanie proxy START");
		System.setProperty("webdriver.gecko.driver", "C:\\crawlers\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", "C:\\crawlers\\chromedriver.exe");
		ProxyMulti[] proxies = new ProxyMulti[threadNumber];
		Thread[] threads = new Thread[threadNumber];
		
		for (int i = 0; i < threadNumber; i++) {
			proxies[i]= new ProxyMulti(i, proxiesAddr);
		}
		for (int i = 0; i < threadNumber; i++) {
			threads[i] = new Thread(proxies[i]);
		}
		for (int i = 0; i < threadNumber; i++) {
			threads[i].start();
		}
	}
	public static Properties loadProperties(String filePath) {
		Properties properties = new Properties();
		InputStream input = null;
		int numberOfThread = 99;
		int idHost = 99;

		try {
			input = new FileInputStream(filePath);
			// load a properties file
			properties.load(input);
			// get the property value and print it out
			logger.info("idHost =" + properties.getProperty("idHost"));
			ProxyMain.setHostId(Integer.parseInt(properties.getProperty("hostId")));
			logger.info("numberOfThreads =" + properties.getProperty("numberOfThreads"));
			ProxyMain.setNumberofThreads(Integer.parseInt(properties.getProperty("numberOfThread")));

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