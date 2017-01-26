package crawler.bisnode_pl.profil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;

public class GetProfile extends ScrapeClass implements Scrape {

	private String urlToScrape;
	private HtmlPage currentPage;
	private ProfilBisNode profil;
	private Properties properties;
	public Logger logger = Logger.getLogger(GetProfile.class);
	
	
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public String getUrlToScrape() {
		return urlToScrape;
	}
	public void setUrlToScrape(String urlToScrape) {
		this.urlToScrape = urlToScrape;
	}
	public HtmlPage getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(HtmlPage currentPage) {
		this.currentPage = currentPage;
	}
	public ProfilBisNode getProfil() {
		return profil;
	}
	public void setProfil(ProfilBisNode profil) {
		this.profil = profil;
	}
	public GetProfile(int threadId, Properties properties, EntityManagerFactory entityManagerFactory, String urlToScrape){
		super(threadId, properties, entityManagerFactory);
		this.setProperties(properties);
		logger.info("Konstruktor KRSPobierzProfilGet dla url="+urlToScrape);
		this.urlToScrape=urlToScrape;
		try{
			this.currentPage = this.getPage(urlToScrape);
//			logger.info(this.getCurrentPage().asXml());
			//jeœli strona wczytana nie jest pusta
			if(this.currentPage!=null){
				this.profil = (ProfilBisNode) this.parsing(this.currentPage, new ProfilBisNode());
//				if(this.insertData(this.profil))logger.info("Profil zapisany");
//				else logger.error("Nie udalo sie zapisac profilu");
				insertDataEntity(this.getProfil());
			}	
		}catch(Exception e){
			logger.error("M: Nie uda³o siê pobraæ strony "+this.urlToScrape);
			e.printStackTrace();
		}
	}
	public List<String> fetchUrlsToScrape() {
		// TODO Auto-generated method stub
		return null;
	}

	public HtmlPage getPage(String url) {
		WebClient client = new WebClient();
//		client.getOptions().setThrowExceptionOnScriptError(false);
		client.getOptions().setJavaScriptEnabled(true);
		try {
			return client.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			logger.error("FailingHttpStatusException<<<<<<<<<<<<<<");
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			logger.error("MalformedURLException<<<<<<<<<<<<<<");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public Object parsing(HtmlPage page, Object mainProfil) {
		logger.info("this.parsing() - START");
		ProfilBisNode profil = new ProfilBisNode();
		//nazwa
    	List<HtmlSpan> nazwy = (List<HtmlSpan>) page.getByXPath("//span[@itemprop='legalName']");
     	for(HtmlSpan nazwa:nazwy){profil.setNazwa(nazwa.asText());}
     	//dzialalnosc
    	List<HtmlSpan> dzialalnosci = (List<HtmlSpan>) page.getByXPath("//span[@itemprop='description']");
     	for(HtmlSpan dzialalnosc:dzialalnosci){profil.setDzialalnosc(dzialalnosc.asText());}
     	//data rozpoczecia dzialalnosci
    	List<HtmlSpan> daty = (List<HtmlSpan>) page.getByXPath("//span[@itemprop='foundingDate']");
     	for(HtmlSpan data:daty){profil.setDataRozpoczeciaDzialalnosci(data.asText());}
		//email
		List<HtmlSpan> emails = (List<HtmlSpan>) page.getByXPath("//span[@itemprop='email']");
     	for(HtmlSpan email:emails){profil.setEmail(email.asText());}
     	//website
    	List<HtmlSpan> websites = (List<HtmlSpan>) page.getByXPath("//span[@itemprop='url']");
     	for(HtmlSpan website:websites){profil.setWebsite(website.asText());}
     	//nip
    	List<HtmlSpan> nipy = (List<HtmlSpan>) page.getByXPath("//span[@itemprop='vatID']");
     	for(HtmlSpan nip:nipy){profil.setNip(nip.asText());}
     	//regon
    	List<HtmlSpan> regony = (List<HtmlSpan>) page.getByXPath("//span[@itemprop='taxID']");
     	for(HtmlSpan regon:regony){profil.setRegon(regon.asText());}
     	//lokalizacja
    	List<HtmlSpan> lokalizacje = (List<HtmlSpan>) page.getByXPath("//span[@itemprop='address']");
     	for(HtmlSpan lokalizacja:lokalizacje){profil.setLokalizacja(lokalizacja.asText());}
     	//telefon
    	List<HtmlSpan> telefony = (List<HtmlSpan>) page.getByXPath("//span[@itemprop='telephone']");
     	for(HtmlSpan telefon:telefony){profil.setPhone(telefon.asText());}
     	List<HtmlDivision> opisy = (List<HtmlDivision>) page.getByXPath("//div[@itemtype='http://schema.org/LocalBusiness']");
     	for(HtmlDivision opis:opisy){profil.setOpis(opis.asText());}
     	String opis = profil.getOpis();
     	String keyKRS="KRS";
     	try{
     		int krsPosition = opis.indexOf(keyKRS);
     		if(!opis.substring(krsPosition+4, krsPosition+14).contains(","))profil.setKrs(opis.substring(krsPosition+4, krsPosition+14));
     	}catch(Exception e){
     		logger.error("Nie znaleziono numeru KRS");
     		profil.setKrs("NULL");
     	}
     	
     	     	
     	//forma prawna
     	try{
     		String formaPrawnaKey="Forma prawna firmy";
         	int formaPrawna = opis.indexOf(formaPrawnaKey);
         	String formaPrawnaS=opis.substring(formaPrawna+19);
         	logger.error(formaPrawnaS);
         	int toPosition=formaPrawnaS.indexOf("to");
         	int dotPosition = formaPrawnaS.indexOf(".");
    	    profil.setFormaPrawna(formaPrawnaS.substring(toPosition+3, dotPosition));
     	}catch(Exception e){
     		logger.info("Nie uda³o wydzieliæ siê formy prawnej");
     		profil.setFormaPrawna("NULL");
     	}
     	
	    logger.info(profil.toString());
	    //DUNS
	    Object object = page.getByXPath("//a[@id='linkduns']").get(0);
	   /**
	    * Wymagane client.getOptions().setJavaScriptEnabled(true); w public HtmlPage getPage(String url)
	    */
	    try {
	    	HtmlAnchor dunsAnchor = page.getAnchorByText("Poka¿ numer DUNS");
			HtmlPage pageDuns = dunsAnchor.click();
			HtmlTableDataCell duns = (HtmlTableDataCell) pageDuns.getByXPath("//td[@id=\"duns\"]").get(0);
			profil.setDuns(duns.asText());
			if(page.equals(pageDuns)) logger.info("Strony identyczne");
			else logger.info("strony siê ró¿ni¹");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    profil.setMeta(this.urlToScrape);
		return profil;
	}

	public Boolean insertData(Object objectToInsert) {
		
		try{
			EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
			logger.info("utworzono entityManagera");
			entityManager.getTransaction().begin();
			crawler.bisnode_pl.profil.ProfilBisNode profilTemp=(crawler.bisnode_pl.profil.ProfilBisNode) objectToInsert;
			entityManager.persist(profilTemp);
			entityManager.getTransaction().commit();
			logger.info("zapisano  entityManagera");
			entityManager.close();
			return true;
		}catch(Exception e){
			logger.error("M: nie mozna zapisac obiektu do tabeli");
			e.printStackTrace();
			return false;
		}
		
	}

	public void supportFetchUrlsToScrape() {
		// TODO Auto-generated method stub

	}

	public void supportGetPage() {
		// TODO Auto-generated method stub

	}

	public void supportParsing() {
		// TODO Auto-generated method stub

	}

	public void supportInsertData() {
		// TODO Auto-generated method stub

	}

	public void mainProcess() {
		// TODO Auto-generated method stub

	}

	public void logger() {
		// TODO Auto-generated method stub

	}
	public void insertDataEntity(Object o) {
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();		
		entityManager.getTransaction().begin();
		entityManager.persist(o);
		entityManager.getTransaction().commit();		
		entityManager.close();
		
	}
	public <T> void insertDataListEntity(List<T> list) {
		EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();		
		entityManager.getTransaction().begin();
		for(T object:list)entityManager.persist(object);
		entityManager.getTransaction().commit();		
		entityManager.close();
		
	}
	

}
