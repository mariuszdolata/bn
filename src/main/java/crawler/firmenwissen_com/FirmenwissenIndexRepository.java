package crawler.firmenwissen_com;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import crawler.api.DatabaseAccess;

public class FirmenwissenIndexRepository extends DatabaseAccess implements Runnable {
	private Logger logger = Logger.getLogger(FirmenwissenIndexRepository.class);
	private List<String> urlsToScrape = new ArrayList<String>();

	public List<String> getUrlsToScrape() {
		return urlsToScrape;
	}

	public void setUrlsToScrape(List<String> urlsToScrape) {
		this.urlsToScrape = urlsToScrape;
	}

	public FirmenwissenIndexRepository(int threadId, Properties properties, EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);

	}

	public void run() {
		this.logger.info("FirmenwissenIndexRepository - START thread=" + this.getThreadId());
		do {
			// pobranie losowych 5 stron indexu ze statusem NULL
			this.fetchUrls();
			String urlToScrape = this.getUrlsToScrape().get(0);
			WebClient client = new WebClient(BrowserVersion.FIREFOX_45);
			client.getOptions().setJavaScriptEnabled(true);
			client.getOptions().setThrowExceptionOnScriptError(false);
			client.getOptions().setThrowExceptionOnFailingStatusCode(false);
			HtmlPage page = null;
			try {
				// Pobranie pierwszej strony tworzacej sesje
				page = client.getPage("http://www.firmenwissen.com/en/results.html?phrase=%25");
				Thread.sleep(2500);

			} catch (FailingHttpStatusCodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FirmenwissenGetIndex firmenwissenGetIndex = new FirmenwissenGetIndex(this.getThreadId(),
					this.getProperties(), this.getEntityManagerFactory(), urlToScrape);
			for (int i = 0; i < this.getUrlsToScrape().size(); i++) {
				/**
				 * Co 25 stron puszczana jest poczatkowa strona (wymagane dla
				 * zmiany adresu IP
				 */
				if (i % 25 == 0) {
					try {
						page = client.getPage("http://www.firmenwissen.com/en/results.html?phrase=%25");
						Thread.sleep(2500);
					} catch (FailingHttpStatusCodeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					page = client.getPage(this.getUrlsToScrape().get(i));
					/**
					 * Sprawdzanie czy odpowiedz HTML jest dluzsza niz 1000
					 * znakow. Jesli nie serwer zablokowal IP
					 */
					if (page.getWebResponse().getContentAsString().length() < 1000) {
						logger.error("BAN - oczekiwanie 15 sek");
						try {
							Thread.sleep(15000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// PrintWriter out = new
					// PrintWriter("C:\\crawlers\\files\\firmenwissen_["+this.getThreadId()+"]["+i+"].txt");
					// out.println(page.asXml());
					// out.close();
				} catch (FailingHttpStatusCodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				firmenwissenGetIndex.setCurrentPage(page);
				firmenwissenGetIndex.parsing(firmenwissenGetIndex.getCurrentPage(), FirmenwissenIndex.class);
				// UPDATE STATUSU

				if (firmenwissenGetIndex.getTotalNumberOfRecords() == 10) {
					EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
					entityManager.getTransaction().begin();
					String sqlUpdate = "UPDATE firmenwissenpreindex SET status='done' WHERE url like ?";
					int resultsNumber = entityManager.createNativeQuery(sqlUpdate)
							.setParameter(1, this.getUrlsToScrape().get(i)).executeUpdate();
					entityManager.getTransaction().commit();
					entityManager.close();
					logger.info(sqlUpdate);
				} else
					logger.warn("0 obiektow dla INDEX URL=" + this.getUrlsToScrape().get(i));
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/**
			 * Wstawienie do bazy danych listy obiekt�w
			 */
			firmenwissenGetIndex.insertDataListEntity(firmenwissenGetIndex.getCompanies());

			System.gc();
			// }while(false);
		} while (!this.getUrlsToScrape().isEmpty());

		this.logger.info("FirmenwissenIndexRepository - END thread=" + this.getThreadId());
	}

	public List<String> fetchUrls() {
		System.gc();
		this.getUrlsToScrape().clear();
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
		String sqlSelect = "SELECT distinct url FROM firmenwissen_com.firmenwissenpreindex WHERE status is null order by rand() limit 50";
		this.setUrlsToScrape(entityManager.createNativeQuery(sqlSelect).getResultList());
		entityManager.close();
		return this.getUrlsToScrape();
	}
}
