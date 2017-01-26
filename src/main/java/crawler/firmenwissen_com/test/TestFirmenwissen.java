package crawler.firmenwissen_com.test;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestFirmenwissen {
	final static Logger logger = Logger.getLogger(TestFirmenwissen.class);

	public static void main(String[] args) {
		logger.info("TEST firmenwissen index");
		test();

	}

	public static void test() {
		System.setProperty("webdriver.gecko.driver", "C:\\crawlers\\geckodriver.exe");
		// WebDriver driver = new FirefoxDriver();
		// firstPage(driver);
		// nextPages(driver);
		int numberOfThreads = 1;
		threads(numberOfThreads);
	}

	public static void firstPage(WebDriver driver) {
		driver.get("http://www.firmenwissen.com/en/results.html?phrase=%25");
	}

	public static void nextPages(WebDriver driver) {
		for (int i = 2; i < 300; i++) {
			// try {
			// Thread.sleep(2500);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			logger.info("Proba nr=" + i);
			driver.get("http://www.firmenwissen.com/en/ergebnis.html?seite=" + i);
		}
	}

	public static void threads(int numberOfThreads) {
		TestIndexMultithread[] tests = new TestIndexMultithread[numberOfThreads];
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			tests[i]=new TestIndexMultithread(i);
		}
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i]=new Thread (tests[i]);
		}
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i].start();
		}
	}

}
