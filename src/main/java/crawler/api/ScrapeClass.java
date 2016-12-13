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
 * Klasa bazowa dla klas GetXXX, gdzie XXX to nazwa portalu. Przechowuje properties, nawi¹zuje po³¹czenie 
 * z baz¹ danych oraz zawiera jednakowe metody dla wszystkich crawlerów takie jak pobieranie jednej strony,
 * wstawienie jednego obiektu do bazy lub wstawienie listy obiektów do bazy.
 * 
 * W konstruktorze klasy ³adowany jest sterownik do bazy danych
 * @author mariusz
 *
 */
public class ScrapeClass {
	protected String urlToScrape;	
	protected HtmlPage currentPage;	
	protected Properties properties;
	protected int numberOfCompanies;
	protected String DB_DRIVER;
	protected String DB_URL;
	protected String DB_USER;
	protected String DB_PASSWORD;
	protected Connection conn = null;
	protected Statement stmt = null;
	protected ResultSet rs = null;
		

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
	public String getDB_DRIVER() {
		return DB_DRIVER;
	}
	public void setDB_DRIVER(String dB_DRIVER) {
		DB_DRIVER = dB_DRIVER;
	}
	public String getDB_URL() {
		return DB_URL;
	}
	public void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}
	public String getDB_USER() {
		return DB_USER;
	}
	public void setDB_USER(String dB_USER) {
		DB_USER = dB_USER;
	}
	public String getDB_PASSWORD() {
		return DB_PASSWORD;
	}
	public void setDB_PASSWORD(String dB_PASSWORD) {
		DB_PASSWORD = dB_PASSWORD;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	public ScrapeClass(Properties properties){
		this.setProperties(properties);
		this.DB_DRIVER = "com.mysql.cj.jdbc.Driver";
		this.DB_URL = "jdbc:mysql://" + properties.getProperty("serverName") + "/"
				+ properties.getProperty("databaseName") + properties.getProperty("databaseProp");
		this.DB_USER = properties.getProperty("user");
		this.DB_PASSWORD = properties.getProperty("password");
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			this.stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Metoda pobiera stronê www o adresie String url. Jeœli konieczne jest wy³¹czenie javaScript lub jakiekolwiek inne ustawienie obiektu client klasy WebClient
	 * wymagane jest nadpisanie metody w kasie dziedzicz¹cej.
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
	 * Wstawienie listy obiektów. Sprawdz czy w pliku persistence.xml 
	 * znajduje siê persistence-unit z nazw¹ scraping!!!!!!!<<<<<<<<<<<<<<<<<<<<<
	 * @param list
	 */
	public <T> void insertDataListEntity(List<T> list, String persistenceUnitName){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try{
			for(T o:list){ 
				entityManager.persist(o);
				System.out.println("Wstawienie wartoœci to tabeli " + o.getClass().getSimpleName());
			}
			
		}catch(IllegalArgumentException e){
			System.err.println("Problem z insertem do tabeli");
			e.printStackTrace();
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	public void insertDataEntity(Object o){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("scraping");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(o);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}
}
