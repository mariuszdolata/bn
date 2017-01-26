package crawler.bisnode_pl.pre_index;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mysql.cj.api.jdbc.Statement;

/**
 * 
 * Klasa usuwa (jeœli istniej¹) pomocnicze tabele letters i index_pages oraz tworzy je od nowa.
 * Dodatkowo wype³nia tabele letters wartoœciami startowymi dla scrapingu indexu (kombinacje 3 liter)
 * Parametry baz danych pobierane s¹ systemowo
 * @author mariusz
 *
 */
public class PreIndexBisNode {
	public Logger logger = Logger.getLogger(PreIndexBisNode.class);
	public PreIndexBisNode(Properties properties){
		final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		final String DB_URL = properties.getProperty("url");
		final String USER = properties.getProperty("user");
		final String PASS = properties.getProperty("password");
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			logger.info("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			logger.info("Connected database successfully...");

			// Usuniecie istniej¹cych tabel i stworzenie nowych
			logger.info("Creating table in given database...");
			stmt = (Statement) conn.createStatement();
			String sql = "DROP TABLE IF EXISTS `" + properties.getProperty("database.name") + "`.`letters`;";
			stmt.execute(sql);
			sql = "DROP TABLE IF EXISTS `" + properties.getProperty("database.name") + "`.`index_pages`;";
			stmt.execute(sql);
			sql = "CREATE TABLE `" + properties.getProperty("database.name")
					+ "`.`letters` (`letters` VARCHAR(3) NULL,`numberOfCompanies` INT NULL DEFAULT NULL);";
			stmt.executeUpdate(sql);
			logger.info("Created table letters in given database...");
			sql = "CREATE TABLE `" + properties.getProperty("database.name")
					+ "`.`index_pages` ( `letters` VARCHAR(45) NULL,  `url` VARCHAR(500) NULL,  `number` INT(10) NULL,  `status` VARCHAR(45) NULL,  INDEX `index1` (`url` ASC));";
			stmt.executeUpdate(sql);
			logger.info("Created table index_pages in given database...");
			StringBuilder letters = new StringBuilder(
					"INSERT INTO `" + properties.getProperty("database.name") + "`.`letters`(letters)  VALUES  ");
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
			// usuniêcie tabeli dla indexu
			sql = "DROP TABLE IF EXISTS " + properties.getProperty("database.name") + ".`index`;";
			logger.error(sql);
			stmt.executeUpdate(sql);
			// stworzenie nowej tabeli dla indexu
//			sql = "CREATE TABLE `" + properties.getProperty("database.name")
//					+ "`.`index` (  `nazwa` MEDIUMTEXT NULL,  `url` VARCHAR(1000) NULL,  `adres` MEDIUMTEXT NULL,  `krs` VARCHAR(45) NULL,  `nip` VARCHAR(45) NULL,  `data` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, `meta` VARCHAR(1000) NULL,  `status` VARCHAR(45) NULL DEFAULT NULL,  INDEX `index1` (`url` ASC),  INDEX `indexNip` (`nip` ASC),  INDEX `indexKrs` (`krs` ASC));";
//			logger.error(sql);
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
