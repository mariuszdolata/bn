package crawler.bisnode_pl.pre_index;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.api.jdbc.Statement;

/**
 * 
 * Klasa usuwa (je�li istniej�) pomocnicze tabele letters i index_pages oraz tworzy je od nowa.
 * Dodatkowo wype�nia tabele letters warto�ciami startowymi dla scrapingu indexu (kombinacje 3 liter)
 * Parametry baz danych pobierane s� systemowo
 * @author mariusz
 *
 */
public class PreIndexBisNode {
	
	public PreIndexBisNode(Properties properties){
		final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//		final String DB_URL = "jdbc:mysql://" + properties.getProperty("serverName") + "/"
//				+ properties.getProperty("databaseName") + properties.getProperty("databaseProp");
		final String DB_URL = System.getProperty("database.url");
		final String USER = System.getProperty("database.user");
		final String PASS = System.getProperty("database.password");
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// Usuniecie istniej�cych tabel i stworzenie nowych
			System.out.println("Creating table in given database...");
			stmt = (Statement) conn.createStatement();
			String sql = "DROP TABLE IF EXISTS `" + System.getProperty("database.name") + "`.`letters`;";
			stmt.execute(sql);
			sql = "DROP TABLE IF EXISTS `" + System.getProperty("database.name") + "`.`index_pages`;";
			stmt.execute(sql);
			sql = "CREATE TABLE `" + System.getProperty("database.name")
					+ "`.`letters` (`letters` VARCHAR(3) NULL,`numberOfCompanies` INT NULL DEFAULT NULL);";
			stmt.executeUpdate(sql);
			System.out.println("Created table letters in given database...");
			sql = "CREATE TABLE `" + System.getProperty("database.name")
					+ "`.`index_pages` (  `letters` VARCHAR(3) NULL,  `url` VARCHAR(500) NULL,  `status` VARCHAR(5) NULL DEFAULT NULL);";
			stmt.executeUpdate(sql);
			System.out.println("Created table index_pages in given database...");
			StringBuilder letters = new StringBuilder(
					"INSERT INTO `" + System.getProperty("database.name") + "`.`letters`(letters)  VALUES  ");
			for (char first = 'a'; first <= 'z'; first++) {
				for (char second = 'a'; second <= 'z'; second++) {
					for (char third = 'a'; third <= 'z'; third++) {
						letters.append(
								"('" + String.valueOf(first) + String.valueOf(second) + String.valueOf(third) + "'),");
					}
				}
			}
			String sqlInsert = letters.substring(0, letters.length() - 1);
			stmt.execute(sqlInsert);
			// usuni�cie tabeli dla indexu
			sql = "DROP TABLE IF EXISTS " + System.getProperty("database.name") + ".`index`;";
			System.err.println(sql);
			stmt.executeUpdate(sql);
			// stworzenie nowej tabeli dla indexu
//			sql = "CREATE TABLE `" + System.getProperty("database.name")
//					+ "`.`index` (  `nazwa` MEDIUMTEXT NULL,  `url` VARCHAR(1000) NULL,  `adres` MEDIUMTEXT NULL,  `krs` VARCHAR(45) NULL,  `nip` VARCHAR(45) NULL,  `data` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, `meta` VARCHAR(1000) NULL,  `status` VARCHAR(45) NULL DEFAULT NULL,  INDEX `index1` (`url` ASC),  INDEX `indexNip` (`nip` ASC),  INDEX `indexKrs` (`krs` ASC));";
//			System.err.println(sql);
//			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		
	}

}
