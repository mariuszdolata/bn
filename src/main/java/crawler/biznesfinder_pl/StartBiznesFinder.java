package crawler.biznesfinder_pl;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import crawler.api.StartCrawler;

public class StartBiznesFinder extends StartCrawler {
	final static Logger logger = Logger.getLogger(StartBiznesFinder.class);

	public static void main(String[] args) {
		// unit();
		selenium();

	}

	private static void selenium() {
		System.setProperty("webdriver.gecko.driver", "C:\\crawlers\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();

		System.out.println("start driver");
		driver.get("http://biznesfinder.pl/s,apteki");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info(driver.getPageSource());
		InputSource source = new InputSource(new StringReader(driver.getPageSource()));
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document document = null;
		try {
			db = dbf.newDocumentBuilder();
			document = db.parse(source);
			logger.info("koniec tworzenia obiektu");

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//
		System.out.println("end driver");
	}

}
