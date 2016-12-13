package crawler.api;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.mysql.cj.api.jdbc.Statement;

public class MainCrawler {
	
	
	protected int threadId;
	protected Properties properties;
	protected String DB_DRIVER;
	protected String DB_URL;
	protected String DB_USER;
	protected String DB_PASSWORD;
	protected Connection conn = null;
	protected Statement stmt = null;
	protected ResultSet rs = null;
	
	public int getThreadId() {
		return threadId;
	}
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public String getDB_DRIVER() {
		return DB_DRIVER;
	}
	public void setDB_DRIVER(String dB_DRIVER) {
		this.DB_DRIVER = dB_DRIVER;
	}
	public String getDB_URL() {
		return DB_URL;
	}
	public void setDB_URL(String dB_URL) {
		this.DB_URL = dB_URL;
	}
	public String getDB_USER() {
		return DB_USER;
	}
	public void setDB_USER(String dB_USER) {
		this.DB_USER = dB_USER;
	}
	public String getDB_PASSWORD() {
		return DB_PASSWORD;
	}
	public void setDB_PASSWORD(String dB_PASSWORD) {
		this.DB_PASSWORD = dB_PASSWORD;
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
	public MainCrawler(Properties properties, int threadId) {
			this.setThreadId(threadId);
			this.setProperties(properties);
			this.setDB_DRIVER("com.mysql.cj.jdbc.Driver"); 
			this.setDB_URL( "jdbc:mysql://" + properties.getProperty("serverName") + "/"
					+ properties.getProperty("databaseName") + properties.getProperty("databaseProp"));
			
			this.setDB_USER(properties.getProperty("user").toString());
			this.setDB_PASSWORD(properties.getProperty("password"));
	}
	public void delay(){
		Random generator = new Random();		
		long delayTime = Long.valueOf(this.getProperties().getProperty("delay")) + generator.nextInt(Integer.valueOf(this.getProperties().getProperty("randDelayMax")));
		try {
			Thread.sleep(delayTime);
		} catch (InterruptedException e) {
			System.err.println("Problem z metod¹ delay() w klasie bazowej MainCrawler. Sprawdz czy prawidlowo podane sa properties o nazwach delay oraz randDelaymax");
			e.printStackTrace();
		}
		
	}
	public <T> List<T> getObjectList(T object, String sql){
		List<T> objects = new ArrayList<T>();
		Field[] fields = object.getClass().getDeclaredFields();
		for(Field field:fields){
			
		}
		
		return objects;
	}
	protected ResultSet getRecords(String sqlSelect, ResultSet rs, Statement stmt) throws SQLException, ClassNotFoundException {

		try {
			System.out.println("Wywo³anie funkcji GETRECORD");
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
