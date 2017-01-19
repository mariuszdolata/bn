package crawler.bisnode_pl.profil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.jdbc.PreparedStatement;

import crawler.api.MainCrawler;
import crawler.bisnode_pl.index.GetIndex;

public class ProfilRepository extends MainCrawler implements Runnable {

	private Properties properties;
	private int numberOfThread;
	private Connection conn = null;
	private Statement stmt=null;
	private ResultSet rs = null;
	
	
	public int getNumberOfThread() {
		return numberOfThread;
	}
	public void setNumberOfThread(int numberOfThread) {
		this.numberOfThread = numberOfThread;
	}
	public ProfilRepository(Properties properties, int threadId){
		super(properties, threadId);
		
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
		this.setThreadId(numberOfThread);
	}
	public void run() {
		//while - pobiera liste adresow url oraz wywoluje getProfile dla okreslonego profilu
		try {
			while(this.checkProfile());
		} catch (SQLException e) {
			System.err.println("Wyst¹pi³ b³ad poczas petli while w ProfilRepository.run()");
			e.printStackTrace();
		}
//		String urlTemp="http://www.bisnode.pl/firma/?id=1926254&nazwa=ORGANIKA_CZ%C4%98_STOCHOWA_SP_Z_O_O";
//		this.delay();
//		KRSPobierzProfilGet getProfile = new KRSPobierzProfilGet(urlTemp);

	}
	private boolean checkProfile() throws SQLException {
		ResultSet unfinishedLetters = null;
		List<String> profiles = new ArrayList<String>();
		try {
			this.rs = getRecords(
					"SELECT distinct url, status FROM bisnode_pl.indexbisnode where status is null order by rand() limit 50;", rs, stmt);
			while(rs.next()){
				profiles.add(rs.getString("url"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("M: nie mo¿na pobraæ danych z tabeli indexBisNode metod¹ checkProfile()");
			e.printStackTrace();
		}
		if(profiles.size()>0){
			System.out.println("Wykryto niedokoñczone profile");
			// rozpoczêcie procesu dokañczania
		for(String profil:profiles){
				System.out.println("Kolejna iteracja pêtli while w chechIndex");
				System.out.println("watek nr " + this.getNumberOfThread() + ", " + profil);
				// Tutaj nastêpuje wczytanie podstony indexu. 0 wczytuje pierwsz¹ podstronê
				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				GetProfile getProfile = new GetProfile(profil, properties);
				try{
					String updateSql = "UPDATE indexbisnode SET status = 'done' WHERE url like ?";
					PreparedStatement preparedStatement = (PreparedStatement)conn.prepareStatement(updateSql);
					preparedStatement.setString(1, profil);
					preparedStatement.executeUpdate();
					System.out.println("UPDATE SQL="+updateSql);
					
					
				}catch(Exception e){
					System.err.println("Nie uda³o siê zaktualizowaæ statusu dla profilu "+profil);
					e.printStackTrace();
				}
				this.delay();				
			} 
			return true;
		} else {
			return false;
		}
	}

}
