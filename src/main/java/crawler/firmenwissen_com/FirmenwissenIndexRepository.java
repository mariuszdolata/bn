package crawler.firmenwissen_com;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import crawler.api.DatabaseAccess;

public class FirmenwissenIndexRepository extends DatabaseAccess implements Runnable {
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
		this.logger.info("FirmenwissenIndexRepository - START thread="+this.getThreadId());
		do{
			//pobranie losowych 50 stron indexu ze statusem NULL
			this.fetchUrls();
				String urlToScrape = this.getUrlsToScrape().get(0);
				WebClient client = new WebClient();
				client.getOptions().setJavaScriptEnabled(true);
				HtmlPage page = null;
				try {
					//Pobranie pierwszej strony tworzacej sesje
					page = client.getPage("http://www.firmenwissen.com/en/results.html?phrase=%25");
					
					
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
			FirmenwissenGetIndex firmenwissenGetIndex = new FirmenwissenGetIndex(this.getThreadId(),
					this.getProperties(), this.getEntityManagerFactory(), urlToScrape);
			for(int i=0;i<this.getUrlsToScrape().size();i++){
				try {
					page = client.getPage(this.getUrlsToScrape().get(i));
//					PrintWriter out = new PrintWriter("C:\\crawlers\\files\\firmenwissen_["+this.getThreadId()+"]["+i+"].txt");
//					out.println(page.asXml());
//					out.close();
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
				//UPDATE STATUSU
				EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
				entityManager.getTransaction().begin();
				String sqlUpdate = "UPDATE firmenwissenpreindex SET status='done' WHERE url like ?";
				int resultsNumber = entityManager.createNativeQuery(sqlUpdate).setParameter(1, this.getUrlsToScrape().get(i)).executeUpdate();
				entityManager.getTransaction().commit();
				entityManager.close();
			}
			firmenwissenGetIndex.insertDataListEntity(firmenwissenGetIndex.getCompanies());
				
				
		}while(false);
//		}while(!this.getUrlsToScrape().isEmpty());
		
		this.logger.info("FirmenwissenIndexRepository - END thread="+this.getThreadId());
	}
	public List<String> fetchUrls(){
		this.getUrlsToScrape().clear();
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
		String sqlSelect="SELECT distinct url FROM firmenwissen_com.firmenwissenpreindex WHERE status is null order by rand() limit 10";
		this.setUrlsToScrape(entityManager.createNativeQuery(sqlSelect).getResultList());
		entityManager.close();
		return this.getUrlsToScrape();
	}
}
