package crawler.firmenwissen_com.test;

import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestIndexMultithread implements Runnable{

	public Logger logger = Logger.getLogger(TestIndexMultithread.class);
	private int idThread;
	private FirefoxDriver driver = new FirefoxDriver();
	
	public int getIdThread() {
		return idThread;
	}

	public void setIdThread(int idThread) {
		this.idThread = idThread;
	}
	

	

	public FirefoxDriver getDriver() {
		return driver;
	}

	public void setDriver(FirefoxDriver driver) {
		this.driver = driver;
	}

	

	public TestIndexMultithread(int i) {
		this.idThread=i;
	}

	public void run() {
		//Strona poczatkowa
		this.getDriver().get("http://www.firmenwissen.com/en/results.html?phrase=%25");
		//iteracja stron
		for(int i=this.getIdThread()*1000;i<(this.getIdThread()*1000)+1000;i++){
			if(i%20==0){
				driver.manage().deleteAllCookies();
				driver.get("http://www.firmenwissen.com/en/results.html?phrase=%25");
				logger.info("WYCZYSZCZONE CIASTKA ORAZ WCZYTANA STRONA STARTOWA");
			}
			try{
				Thread.sleep(2500);
				this.getDriver().get("http://www.firmenwissen.com/en/ergebnis.html?seite="+i);
				logger.info("Thread="+this.getIdThread()+", proba="+i+", dlugosc HTML="+driver.getPageSource().length());
				if(this.getDriver().getPageSource().length()<1000){
					Thread.sleep(10000);
					this.getDriver().get("http://www.firmenwissen.com/en/results.html?phrase=%25");
				}
			}catch(Exception e){
				logger.error("b³¹d pobierania strony dla thread="+this.getIdThread()+", proba nr="+i);
			}
		}
	}

}
