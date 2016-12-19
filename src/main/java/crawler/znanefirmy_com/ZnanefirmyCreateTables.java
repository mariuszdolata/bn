package crawler.znanefirmy_com;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mysql.cj.api.jdbc.Statement;

public class ZnanefirmyCreateTables {
	public final static Logger logger = Logger.getLogger(ZnanefirmyCreateTables.class);

	public ZnanefirmyCreateTables(Properties properties) {
		final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
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

			// Usuniecie istniej¹cych tabel i stworzenie nowych
			System.out.println("Creating table in given database...");
			stmt = (Statement) conn.createStatement();
			String sql = "DROP TABLE IF EXISTS `" + System.getProperty("database.name") + "`.`branze`;";
			stmt.execute(sql);
			sql = "DROP TABLE IF EXISTS `" + System.getProperty("database.name") + "`.`pre_index`;";
			stmt.execute(sql);

			sql = "CREATE TABLE `" + System.getProperty("database.name")
					+ "`.`branze` (  `nazwa` MEDIUMTEXT NULL,  `liczba_firm` INT(10) NULL DEFAULT NULL,  `url_branza` VARCHAR(500) NULL);";

			try {
				stmt.executeUpdate(sql);
				logger.info("Stworzono tabelê BRANZE");
			} catch (Exception e) {
				logger.error("Blad przy tworzeniu tabeli BRANZE" + e.getMessage());
			}
			System.out.println("Created table letters in given database...");
//			sql = "CREATE TABLE `" + System.getProperty("database.name")
//					+ "`.`pre_index` (  `nazwa` MEDIUMTEXT NULL,  `url` VARCHAR(1000) NULL,  `data` TIMESTAMP CURRENT_TIMESTAMP,  `status` VARCHAR(10) NULL);";
			sql = "CREATE TABLE `" + System.getProperty("database.name")
			+ "`.`pre_index` (  `nazwa` MEDIUMTEXT NULL,  `url` VARCHAR(1000) NULL,  `data` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,  `status` VARCHAR(10) NULL, INDEX `index1` (`url` ASC));";
			try{
				stmt.executeUpdate(sql);
				logger.info("Stworzono tabele PRE_INDEX");
			}catch(Exception e){
				logger.error("BLAD przy tworzeniu tabeli PRE_INDEX" + e.getMessage());
			}
			System.out.println("Created table index_pages in given database...");

			HtmlPage page = getPage("http://znanefirmy.com/produkty-i-uslugi/");
			List<HtmlAnchor> urls =(List<HtmlAnchor>) page.getByXPath("//a");
			StringBuilder branze = new StringBuilder(
					"INSERT INTO `" + System.getProperty("database.name") + "`.`branze`(nazwa, url_branza)  VALUES  ");
			for(HtmlAnchor a:urls){
				if(a.getHrefAttribute().contains("/branza/")){
					branze.append("('"+a.getTextContent()+"','http://znanefirmy.com"+a.getHrefAttribute()+"'),");
				}
			}
			//Wstawienie nazw oraz adresow do branz
			String sqlInsert = branze.substring(0, branze.length()-1);
			stmt.execute(sqlInsert);

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
	public HtmlPage getPage(String url){
		WebClient client = new WebClient();
		try {
			return client.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
