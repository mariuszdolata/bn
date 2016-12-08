package crawler.bisnode_pl.index;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.zip.CheckedInputStream;

import com.mysql.cj.api.jdbc.Statement;

import crawler.api.MainCrawler;

public class IndexRepository extends MainCrawler implements Runnable {

	private int threadId;
	private Properties properties;
	private String DB_DRIVER;
	private String DB_URL;
	private String DB_USER;
	private String DB_PASSWORD;
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public IndexRepository(Properties properties, int threadId) {
		this.threadId = threadId;
		this.properties = properties;
		this.DB_DRIVER = "com.mysql.cj.jdbc.Driver";
		this.DB_URL = "jdbc:mysql://" + properties.getProperty("serverName") + "/"
				+ properties.getProperty("databaseName") + properties.getProperty("databaseProp");
		this.DB_USER = properties.getProperty("user");
		this.DB_PASSWORD = properties.getProperty("password");
		try {
			Class.forName(DB_DRIVER);
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
			while (checkIndex())
				;
			System.out.println("Poziom 1 z 3 strony bisnode.pl jest ju� sko�czony");
		} catch (SQLException e) {
			System.err.println("w�tek nr " + this.threadId + " B��d pobierania danych z tabeli letters");
			e.printStackTrace();
		}
	}

	/**
	 * Sprawdzanie czy crawler wykona� wszystkie kombinacje liter. Je�li nie
	 * wykona� w pierwszej kolejno��i doko�czy je jednocze�nie zapisuj�c za
	 * pomoc� obiektu GetIndex pierwsze 20 wynik�w dla zadanego ci�gu znak�w.
	 * Jesli �adna kombinacja liter nie ma NULL w liczbie firm crawler
	 * przyst�puje do doko�czenia indexu.
	 * 
	 * @return
	 * @throws SQLException
	 */
	private boolean checkIndex() throws SQLException {
		ResultSet unfinishedLetters = null;
		try {
			this.rs = getRecords(
					"SELECT letters, numberOfCompanies FROM bisnode_pl.letters where numberOfCompanies is null order by rand() limit 100");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("M: nie mo�na pobra� danych z tabeli letters. IndexRepository.run().getRecords()");
			e.printStackTrace();
		}
		// wykryto niedoko�czony index
		// rs.beforeFirst();
		if (rs.next()) {
			rs.beforeFirst();
			System.out.println("Wykryto niedoko�czony index");
			// rozpocz�cie procesu doka�czania
			while (rs.next()) {
				System.out.println("watek nr " + this.threadId + ", " + rs.getString("letters"));
				String urlToScrape = "http://www.bisnode.pl/wyniki-wyszukiwania/?nazwa=" + rs.getString("letters");
				// Tutaj nast�puje wczytanie podstony indexu
				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				GetIndex getIndex = new GetIndex(urlToScrape, this.properties);
				
				//Uaktualnienie informacji o liczbie firm dla zadanego ci�gu znak�w
				sqlExecute("UPDATE letters set numberOfCompanies=" + getIndex.getNumberOfCompanies()
						+ "WHERE letters like '" + rs.getString("letters") + "'");
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
			System.err.println("Nie uda�o si� zaktualizowa� informacji o liczbie firm");
		}
	}

	private ResultSet getRecords(String sqlSelect) throws SQLException, ClassNotFoundException {

		try {

			// execute select SQL stetement
			rs = stmt.executeQuery(sqlSelect);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			// if (stmt != null) {
			// stmt.close();
			// }
			// if (conn != null) {
			// conn.close();
			// }
		}
		return rs;
	}
}
