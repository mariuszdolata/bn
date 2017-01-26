package crawler.bisnode_pl.index;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;

import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.jdbc.PreparedStatement;

import crawler.api.MainCrawler;

public class IndexRepository extends MainCrawler implements Runnable {
	public static Logger logger = Logger.getLogger(IndexRepository.class);


	public IndexRepository(int threadId, Properties properties, EntityManagerFactory entityManagerFactory) {
		super(threadId, properties, entityManagerFactory);
		 
		this.setThreadId(threadId);
		
		try {
			Class.forName(this.getDB_DRIVER());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			this.stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		// sprawdzenie czy nie trzeba dokoñczyæ pierwszej strony w indexie
		try {
			//dokoñczenie pierwszej czêœci indexu - wczytania wszystkich kombinacji 3 liter oraz zapisania pierwszych 20 rezultatów
			while (checkIndex());
			logger.info("metoda checkIndex() zosta³a skonczona");
			//dokoñczenie drugiej czêœci indexu - wczytanie danych z tabeli index_pages oraz zapisania reszty rezultatów z indexu
			while(checkRestIndex());
			logger.info("metoda checkRestIndex() zosta³a skonczona");
			logger.info("Poziom 1 z 3 strony bisnode.pl jest ju¿ skoñczony");
		} catch (SQLException e) {
			logger.error("w¹tek nr " + this.threadId + " B³¹d pobierania danych z tabeli letters lub tabeli index_pages");
			e.printStackTrace();
		}
	}
private boolean checkRestIndex() throws SQLException{
	class IndexPage{
		public String letters;
		public String url;
		public int numberOfCompanies;
		public String status;
		public IndexPage(String letters, String url, int numberOfCompanies, String status) {
			super();
			this.letters = letters;
			this.url = url;
			this.numberOfCompanies = numberOfCompanies;
			this.status = status;
		}
		
	}
	List<IndexPage> recordsToScrape = new ArrayList<IndexPage>();
	try {
		this.rs = getRecords(
				"SELECT letters, url, number, status FROM bisnode_pl.index_pages where status is null order by rand() limit 1;", this.getRs(), this.getStmt());
		while(rs.next()){
			recordsToScrape.add(new IndexPage(rs.getString("letters"), rs.getString("url"), rs.getInt("number"), rs.getString("status")));
		}
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		logger.info("M: nie mo¿na pobraæ danych z tabeli letters. IndexRepository.run().getRecords()");
		e.printStackTrace();
	}
	//index jest niedokoñczony. Wczytanie pojedynczych stron oraz aktualizacja statusu dla tabeli index_page
	if(recordsToScrape.size()>0){
		for(IndexPage indexPage:recordsToScrape){
			GetIndex index=new GetIndex(this.getThreadId(), this.getProperties(), this.getEntityManagerFactory(), indexPage.letters, indexPage.numberOfCompanies);
			String updateSql="UPDATE index_pages SET status='done' WHERE letters like ? AND number =?";
			PreparedStatement preparedStatement = (PreparedStatement)conn.prepareStatement(updateSql);
			preparedStatement.setString(1, indexPage.letters);
			preparedStatement.setInt(2, indexPage.numberOfCompanies);
			preparedStatement.executeUpdate();
			this.delay();
		}
		
		return true;
	}
	//index jest skoñczony
	else{
		logger.info("Index zosta³ skoñczony");
		return false;
	}
}
/**
 * Wstawianie rekordów do tabeli index_pages. Dziêki nim program bêdzie widzia³, które podstrony indexu
 * nie zosta³y jeszcze zescrapowane. Ta metoda umo¿liwia wznawianie scrapowania indexu bez serializacji
 * oraz bez podzia³u na crawlere ze wzglêdu na id. Daje swobodny dostêp wszytkim w¹tkom do niedokoñczonego indexu.
 * @param letters
 * @param numberOfCompanies
 */
	private void insertUrlsToScrapeForIndex(String letters, int numberOfCompanies){
		if(numberOfCompanies>=20){
			StringBuilder sqlInsert = new StringBuilder("INSERT INTO index_pages (letters, url, number) VALUES ");
			for(int i=20;i<numberOfCompanies;i+=20){
				sqlInsert.append("('"+letters+"','"+"http://www.bisnode.pl/wyniki-wyszukiwania/?nazwa="+letters+"&strona="+Integer.toString(i)+"','"+Integer.toString(i)+"'),");
			}
			logger.info("sql insert: "+sqlInsert.toString().substring(0, sqlInsert.length()-1));
			try {
				stmt.executeUpdate(sqlInsert.toString().substring(0, sqlInsert.length()-1));
			} catch (SQLException e) {
				logger.info("b³¹d metody InsertUrlsToScrapeForIndex() nie uda³o siê wstawiæ wyników do db");
				e.printStackTrace();
			}
		}
	}
	/**
	 * Sprawdzanie czy crawler wykona³ wszystkie kombinacje liter. Jeœli nie
	 * wykona³ w pierwszej kolejnoœæi dokoñczy je jednoczeœnie zapisuj¹c za
	 * pomoc¹ obiektu GetIndex pierwsze 20 wyników dla zadanego ci¹gu znaków.
	 * Jesli ¿adna kombinacja liter nie ma NULL w liczbie firm crawler
	 * przystêpuje do dokoñczenia indexu.
	 * Ponad to metoda zapisujke informacjê o liczbie firm dla danych liter
	 * oraz tworzy (poœrednio) rekordy w tabeli index_pages, które bêd¹
	 * wykorzystane do dokoñczenia indexu.
	 * 
	 * @return
	 * @throws SQLException
	 */
	private boolean checkIndex() throws SQLException {
		ResultSet unfinishedLetters = null;
		List<String> letters = new ArrayList<String>();
		try {
			this.rs = getRecords(
					"SELECT letters, numberOfCompanies FROM bisnode_pl.letters where numberOfCompanies is null order by rand() limit 1", this.getRs(), this.getStmt());
			while(rs.next()){
				letters.add(rs.getString("letters"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			logger.info("M: nie mo¿na pobraæ danych z tabeli letters. IndexRepository.run().getRecords()");
			e.printStackTrace();
		}
		if(letters.size()>0){
			logger.info("Wykryto niedokoñczony index");
			// rozpoczêcie procesu dokañczania
		for(String letter:letters){
				logger.info("Kolejna iteracja pêtli while w chechIndex");
				logger.info("watek nr " + this.threadId + ", " + letter);
				// Tutaj nastêpuje wczytanie podstony indexu. 0 wczytuje pierwsz¹ podstronê
				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				GetIndex getIndex = new GetIndex(this.getThreadId(), this.getProperties(), this.getEntityManagerFactory(), letter, 0);
				try{
					String updateSql = "UPDATE letters SET numberOfCompanies = ?  WHERE letters like ?";
					PreparedStatement preparedStatement = (PreparedStatement)conn.prepareStatement(updateSql);
					preparedStatement.setInt(1, getIndex.getNumberOfCompanies());
					preparedStatement.setString(2, letter);
					preparedStatement.executeUpdate();
					logger.info("UPDATE SQL="+updateSql);
					insertUrlsToScrapeForIndex(letter, getIndex.getNumberOfCompanies());
					
				}catch(Exception e){
					logger.error("Nie uda³o siê zaktualizowaæ informacji o liczbie firm dla podanego ci¹gu wejœciowego "+letter);
					e.printStackTrace();
				}
				this.delay();				
			} 
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Uniwersalna metoda zwracaj¹ca zapytanie SELECT w postaci obiektu
	 * ResultSet. Celowo na koñcu nie ma pozamykanych stmt oraz conn. Jest to
	 * nieestetyczne ale praktyczne ;]
	 * 
	 * @param sqlSelect
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void sqlExecute(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			logger.error("Nie uda³o siê zaktualizowaæ informacji o liczbie firm");
		}
	}

//	private ResultSet getRecords(String sqlSelect) throws SQLException, ClassNotFoundException {
//
//		try {
//			logger.info("Wywo³anie funkcji GETRECORD");
//			// execute select SQL stetement
//			rs = stmt.executeQuery(sqlSelect);
//
//		} catch (SQLException e) {
//			logger.info(e.getMessage());
//		} finally {
//			// if (stmt != null) {
//			// stmt.close();
//			// }
//			// if (conn != null) {
//			// conn.close();
//			// }
//		}
//		return rs;
//	}
}
