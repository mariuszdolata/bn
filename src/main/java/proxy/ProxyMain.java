package proxy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import crawler.api.StartCrawler;

public class ProxyMain extends StartCrawler{
	private final static Logger logger=Logger.getLogger(ProxyMain.class);
	private final static Properties properties = ProxyMain.loadProperties("C:\\crawlers\\properties\\proxies_settings.properties");
	private  static int hostId;
	private  static int numberofThreads;
	private static List<String> proxies;
	


	public static List<String> getProxies() {
		return proxies;
	}

	public static void setProxies(List<String> proxies) {
		ProxyMain.proxies = proxies;
	}

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

	public Properties getProperties() {
		return properties;
	}

	public static void main(String[] args) {
	
		String filePath="c:\\crawlers\\proxies\\proxies.txt";
		try {
			ProxyMain.setProxies(readProxiesFromFile(filePath));
		} catch (IOException e) {
			logger.error("Problem z wczytaniem pliku "+filePath);
			e.printStackTrace();
		}
		createThreads(ProxyMain.numberofThreads);

	}

	static void createThreads(int threadNumber) {
		String[] proxiesAddr = {"123.30.238.16:3128", "213.240.61.42:80", "112.27.207.38:8118"};
		logger.info("Testowanie proxy START");
		System.setProperty("webdriver.gecko.driver", "C:\\crawlers\\drivers\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", "C:\\crawlers\\drivers\\chromedriver.exe");
		ProxyMulti[] proxies = new ProxyMulti[threadNumber];
		Thread[] threads = new Thread[threadNumber];
		try{
			ProxyMain.entityManagerFactory=Persistence.createEntityManagerFactory("proxy");
		}catch(Exception e){logger.error("Nie mozna utworzyc entityManagerFactory");}
		for (int i = 0; i < threadNumber; i++) {
//			proxies[i]= new ProxyMulti(i, proxiesAddr);
			proxies[i] = new ProxyMulti(i, properties, entityManagerFactory, ProxyMain.getProxies());
		}
		for (int i = 0; i < threadNumber; i++) {
			threads[i] = new Thread(proxies[i]);
		}
		for (int i = 0; i < threadNumber; i++) {
//			do{
				try{
					threads[i].start();					
				}catch(Exception e){
					logger.error("Restart watku numer " +i);
				}
				
//			}while(true);
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
			logger.info("numberOfThreads =" + properties.getProperty("numberOfThreads"));
			ProxyMain.setNumberofThreads(Integer.parseInt(properties.getProperty("numberOfThreads")));

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
	public static List<String> readProxiesFromFile(String filePath) throws FileNotFoundException, IOException{
		List<String> proxies = new ArrayList<String>();
//		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//		    StringBuilder sb = new StringBuilder();
//		    String line = br.readLine();
//
//		    while (line != null) {
//		    	proxies.add(line);
//		        line = br.readLine();
//		    }
//		}
		return proxies;
	}


}