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
		// sprawdzenie czy nie trzeba doko�czy� pierwszej strony w indexie
		try {
			//doko�czenie pierwszej cz�ci indexu - wczytania wszystkich kombinacji 3 liter oraz zapisania pierwszych 20 rezultat�w
			while (checkIndex());
			logger.info("metoda checkIndex() zosta�a skonczona");
			//doko�czenie drugiej cz�ci indexu - wczytanie danych z tabeli index_pages oraz zapisania reszty rezultat�w z indexu
			while(checkRestIndex());
			logger.info("metoda checkRestIndex() zosta�a skonczona");
			logger.info("Poziom 1 z 3 strony bisnode.pl jest ju� sko�czony");
		} catch (SQLException e) {
			logger.error("w�tek nr " + this.threadId + " B��d pobierania danych z tabeli letters lub tabeli index_pages");
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
		logger.info("M: nie mo�na pobra� danych z tabeli letters. IndexRepository.run().getRecords()");
		e.printStackTrace();
	}
	//index jest niedoko�czony. Wczytanie pojedynczych stron oraz aktualizacja statusu dla tabeli index_page
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
	//index jest sko�czony
	else{
		logger.info("Index zosta� sko�czony");
		return false;
	}
}
/**
 * Wstawianie rekord�w do tabeli index_pages. Dzi�ki nim program b�dzie widzia�, kt�re podstrony indexu
 * nie zosta�y jeszcze zescrapowane. Ta metoda umo�liwia wznawianie scrapowania indexu bez serializacji
 * oraz bez podzia�u na crawlere ze wzgl�du na id. Daje swobodny dost�p wszytkim w�tkom do niedoko�czonego indexu.
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
				logger.info("b��d metody InsertUrlsToScrapeForIndex() nie uda�o si� wstawi� wynik�w do db");
				e.printStackTrace();
			}
		}
	}
	/**
	 * Sprawdzanie czy crawler wykona� wszystkie kombinacje liter. Je�li nie
	 * wykona� w pierwszej kolejno��i doko�czy je jednocze�nie zapisuj�c za
	 * pomoc� obiektu GetIndex pierwsze 20 wynik�w dla zadanego ci�gu znak�w.
	 * Jesli �adna kombinacja liter nie ma NULL w liczbie firm crawler
	 * przyst�puje do doko�czenia indexu.
	 * Ponad to metoda zapisujke informacj� o liczbie firm dla danych liter
	 * oraz tworzy (po�rednio) rekordy w tabeli index_pages, kt�re b�d�
	 * wykorzystane do doko�czenia indexu.
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
			logger.info("M: nie mo�na pobra� danych z tabeli letters. IndexRepository.run().getRecords()");
			e.printStackTrace();
		}
		if(letters.size()>0){
			logger.info("Wykryto niedoko�czony index");
			// rozpocz�cie procesu doka�czania
		for(String letter:letters){
				logger.info("Kolejna iteracja p�tli while w chechIndex");
				logger.info("watek nr " + this.threadId + ", " + letter);
				// Tutaj nast�puje wczytanie podstony indexu. 0 wczytuje pierwsz� podstron�
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
					logger.error("Nie uda�o si� zaktualizowa� informacji o liczbie firm dla podanego ci�gu wej�ciowego "+letter);
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
	 * Uniwersalna metoda zwracaj�ca zapytanie SELECT w postaci obiektu
	 * ResultSet. Celowo na ko�cu nie ma pozamykanych stmt oraz conn. Jest to
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
			logger.error("Nie uda�o si� zaktualizowa� informacji o liczbie firm");
		}
	}

//	private ResultSet getRecords(String sqlSelect) throws SQLException, ClassNotFoundException {
//
//		try {
//			logger.info("Wywo�anie funkcji GETRECORD");
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
