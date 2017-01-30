package proxy;

import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ProxyMulti implements Runnable{

	private Logger logger = Logger.getLogger(ProxyMulti.class);
	private int threadId;
	private String[] proxiesAddr;
	
	
	public String[] getProxiesAddr() {
		return proxiesAddr;
	}


	public void setProxiesAddr(String[] proxiesAddr) {
		this.proxiesAddr = proxiesAddr;
	}


	public ProxyMulti(int threadId, String[] proxiesAddr) {
		super();
		this.threadId = threadId;
		this.setProxiesAddr(proxiesAddr);
	}


	public int getThreadId() {
		return threadId;
	}


	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}


	public void run() {
		logger.info("Thread -"+this.getThreadId()+"- START");
		String PROXY = "http://123.30.238.16:3128";
		

		Proxy p=new Proxy();
		 
		 
		  // Set HTTP Port to 7777
		  p.setHttpProxy(this.getProxiesAddr()[this.getThreadId()]);
		 
		  // Create desired Capability object
		  DesiredCapabilities cap=new DesiredCapabilities();
		 
		 
		  // Pass proxy object p
		  cap.setCapability(CapabilityType.PROXY, p);
		 
		 
		  // Open  firefox browser
//		  WebDriver driver=new FirefoxDriver(cap);
		  WebDriver driver = new ChromeDriver(cap);
		  driver.get("https://pl.linkedin.com/in/annaszarecka");
		  
		  try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  driver.navigate().refresh();
		  try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		driver.quit();
		
	
	}

}