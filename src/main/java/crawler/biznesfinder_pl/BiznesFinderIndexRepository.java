package crawler.biznesfinder_pl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import crawler.api.DatabaseAccess;

public class BiznesFinderIndexRepository extends DatabaseAccess implements Runnable {

	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public BiznesFinderIndexRepository(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			WebDriver driver) {
		super(threadId, properties, entityManagerFactory);
		this.setDriver(driver);
	}

	/**
	 * Klasa BiznesFinderIndexGet ma dodatkowy konstruktor, poniewaz konieczne
	 * jest przeslanie obiektu WebDriver driver tak aby iterujac kolejne strony
	 * unikn¹æ wielokrotnego okna przegladarki
	 */
	public void run() {
		// WebDriver driver = new FirefoxDriver();
		// level2
		if(this.getProperties().getProperty("level2").contains("1"))level2();
		// level3
		if(this.getProperties().getProperty("level3").contains("1"))level3();

	}

	public void level2() {
		logger.info("START LEVEL 2");
		List<BiznesFinderBranza> branze = new ArrayList<BiznesFinderBranza>();
		do {
			branze.clear();
			EntityManager em = this.getEntityManagerFactory().createEntityManager();
			TypedQuery<BiznesFinderBranza> q = em
					.createQuery("SELECT b FROM BiznesFinderBranza b WHERE status is null order by rand()",
							BiznesFinderBranza.class)
					.setMaxResults(1);
			branze = q.getResultList();
			if (!branze.isEmpty()) {
				for (BiznesFinderBranza branza : branze) {
					em.getTransaction().begin();
					BiznesFinderIndexGet indexGet = new BiznesFinderIndexGet(this.getThreadId(), this.getProperties(),
							this.getEntityManagerFactory(), driver, branza.getUrl());
					branza.setStatus(Integer.toString(indexGet.getNumberOfCompanies()));
					em.getTransaction().commit();
				}
			}
			em.close();
		} while (!branze.isEmpty());
	}

	public void level3() {
		logger.info("START LEVEL 3");
		List<BiznesFinderDokonczenieIndexu> urls = new ArrayList<BiznesFinderDokonczenieIndexu>();
		do {
			urls.clear();
			EntityManager em = this.getEntityManagerFactory().createEntityManager();
			TypedQuery<BiznesFinderDokonczenieIndexu> q = em
					.createQuery("SELECT b FROM BiznesFinderDokonczenieIndexu b WHERE status is null order by rand()",
							BiznesFinderDokonczenieIndexu.class)
					.setMaxResults(1);
			urls=q.getResultList();
			if(!urls.isEmpty()){
				for(BiznesFinderDokonczenieIndexu url:urls){
					em.getTransaction().begin();
					BiznesFinderIndexGet indexGet = new BiznesFinderIndexGet(this.getThreadId(), this.getProperties(), this.getEntityManagerFactory(), this.getDriver(), url.getUrl());
					url.setStatus(Integer.toString(indexGet.getNumberOfCompanies()));
					em.getTransaction().commit();
				}
			}

			em.close();
		} while (!urls.isEmpty());

	}

}
