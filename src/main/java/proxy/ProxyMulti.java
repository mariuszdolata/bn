package proxy;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import crawler.api.DatabaseAccess;

public class ProxyMulti extends DatabaseAccess implements Runnable {

	private Logger logger = Logger.getLogger(ProxyMulti.class);
	private int threadId;
	private String[] proxiesAddr;
	private List<String> proxies;

	public List<String> getProxies() {
		return proxies;
	}

	public void setProxies(List<String> proxies) {
		this.proxies = proxies;
	}

	public String[] getProxiesAddr() {
		return proxiesAddr;
	}

	public void setProxiesAddr(String[] proxiesAddr) {
		this.proxiesAddr = proxiesAddr;
	}

	/**
	 * Konstruktor do wczytania proxy z bazy danych
	 * 
	 * @param threadId
	 * @param properties
	 * @param entityManagerFactory
	 */
	public ProxyMulti(int threadId, Properties properties, EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
	}

	public ProxyMulti(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			String[] proxiesAddr) {
		super(threadId, properties, entityManagerFactory);
		this.threadId = threadId;
		this.setProxiesAddr(proxiesAddr);
	}

	public ProxyMulti(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			List<String> proxies) {
		super(threadId, properties, entityManagerFactory);
		this.setThreadId(threadId);
		this.setProxies(proxies);
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public void run() {
		logger.info("Thread -" + this.getThreadId() + "- START");
		String condition = null;

		do {
			condition = null;
			EntityManager em = this.getEntityManagerFactory().createEntityManager();

			ProxyEntity pe = new ProxyEntity();
			pe.setParse(".");
			WebDriver driver = null;
			try {
				em.getTransaction().begin();
				//pobranie jednego IP
				TypedQuery<ProxyList> query = em.createQuery("SELECT p FROM ProxyList p WHERE status is null order by rand()", ProxyList.class).setMaxResults(1);
				
				ProxyList proxy = (ProxyList)query.getSingleResult();
				em.getTransaction().commit();
				condition=proxy.getIp_port();
				logger.info("Proxy z DB=" + proxy.getIp_port());
				Proxy p = new Proxy();
				p.setHttpProxy(proxy.getIp_port());
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(CapabilityType.PROXY, p);
				driver = new ChromeDriver(cap);
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				int iter = 0;
				Boolean continueWhile = true;
				do {
					iter++;
					long start = System.currentTimeMillis();
					driver.get("http://www.moje-ip.eu");
					long stop = System.currentTimeMillis();
					long time = stop - start;
					WebElement ipNumber = null;
					try {
						ipNumber = driver.findElement(By.xpath(".//div[contains(@class, 'mjip2')]"));
						// jeœli znajdzie ipNumber wyjdz z do-while
						continueWhile = false;
						pe.setIpPort(proxy.getIp_port());
						String ipNumberString = ipNumber.getText();
						pe.setParse(ipNumberString);
						pe.setMs(time);
						pe.setRetry(iter);
						logger.info("Thread nr " + this.getThreadId() + " = " + time + "[ms], ip=" + ipNumberString);
					} catch (Exception e) {
						logger.warn("nie uda³o siê wczytaæ strony");
					}
				} while (iter <= 3 && continueWhile);
				em.getTransaction().begin();
				proxy.setStatus("checked");
				em.getTransaction().commit();
			} catch (Exception e) {
				logger.warn("sprawdzenie ip zakonczone niepowodzeniem ");
				logger.warn(e.getLocalizedMessage());				
			}
			driver.quit();
				
			
			// nie wstawiaj do bazy danych niedzia³aj¹cych IP
//			if (pe.getParse().length()>8) {
				em.getTransaction().begin();
				em.persist(pe);
				em.getTransaction().commit();
//			}

			em.close();

		} while (condition.length() > 0);

	}

}