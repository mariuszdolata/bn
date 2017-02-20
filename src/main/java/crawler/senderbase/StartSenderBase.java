package crawler.senderbase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class StartSenderBase {

	public static void main(String[] args) {
		start();
	}
	public static void start(){
		System.setProperty("webdriver.gecko.driver", "C://crawlers//drivers//geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.senderbase.org/lookup/?search_string=mail.sendit01.pl");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement element = driver.findElement(By.xpath("//input[contains(name, 'tos_accepted')]"));
		if(element.isSelected())System.out.println("Element wybrany");
		else System.out.println("Element niewybrany");
		element.click();
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get("http://www.senderbase.org/lookup/?search_string=mail.sendit01.pl");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(driver.getPageSource());
	}
}
