package crawler.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mysql.cj.api.jdbc.Statement;

import crawler.bisnode_pl.index.IndexBisNode;

/**
 * Klasa bazowa dla klas GetXXX, gdzie XXX to nazwa portalu. Przechowuje properties, nawi�zuje po��czenie 
 * z baz� danych oraz zawiera jednakowe metody dla wszystkich crawler�w takie jak pobieranie jednej strony,
 * wstawienie jednego obiektu do bazy lub wstawienie listy obiekt�w do bazy.
 * 
 * W konstruktorze klasy �adowany jest sterownik do bazy danych
 * @author mariusz
 *
 */
public class ScrapeClass extends DatabaseAccess {
	protected String urlToScrape;	
	protected HtmlPage currentPage;	
	protected Properties properties;
	protected int numberOfCompanies;

		

	public String getUrlToScrape() {
		return urlToScrape;
	}
	public void setUrlToScrape(String urlToScrape) {
		this.urlToScrape = urlToScrape;
	}
	public HtmlPage getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(HtmlPage currentPage) {
		this.currentPage = currentPage;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public int getNumberOfCompanies() {
		return numberOfCompanies;
	}
	public void setNumberOfCompanies(int numberOfCompanies) {
		this.numberOfCompanies = numberOfCompanies;
	}
	
	public ScrapeClass(int threadId, Properties properties, EntityManagerFactory entityManagerFactory){
		super(threadId, properties, entityManagerFactory);
	}
	/**
	 * Metoda pobiera stron� www o adresie String url. Je�li konieczne jest wy��czenie javaScript lub jakiekolwiek inne ustawienie obiektu client klasy WebClient
	 * wymagane jest nadpisanie metody w kasie dziedzicz�cej.
	 * @param url
	 * @return
	 */
	public HtmlPage getPage(String url){
		WebClient client = new WebClient();
		try {
			return client.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Wstawienie listy obiekt�w. Sprawdz czy w pliku persistence.xml 
	 * znajduje si� persistence-unit z nazw� scraping!!!!!!!<<<<<<<<<<<<<<<<<<<<<
	 * @param list
	 */
	public <T> void insertDataListEntity(List<T> list){
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		try{
			for(T o:list){ 
				entityManager.persist(o);
				System.out.println("Wstawienie warto�ci to tabeli " + o.getClass().getSimpleName());
			}
			
		}catch(IllegalArgumentException e){
			System.err.println("Problem z insertem do tabeli");
			e.printStackTrace();
		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	public void insertDataEntity(Object o){
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(o);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
