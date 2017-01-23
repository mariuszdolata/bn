package crawler.krs_pobierz_pl.profil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;

public class KRSPobierzProfilGet extends ScrapeClass implements Scrape {
	private String urlToScrape;
	private String idHost;
	public enum Miesiac {stycznia, lutego, marca, kwietnia, maja, czerwca, lipca, sierpnia, wrzeœnia, paŸdziernika, listopada, grudnia}

	public String getUrlToScrape() {
		return urlToScrape;
	}

	public void setUrlToScrape(String urlToScrape) {
		this.urlToScrape = urlToScrape;
	}

	public KRSPobierzProfilGet(int idThread, Properties properties, EntityManagerFactory entityManagerFactory,
			String urlToScrape) {

		super(idThread, properties, entityManagerFactory);
		this.urlToScrape = urlToScrape;
		// this.testStrony();
		logger.info("pobieranie strony...");
		this.setCurrentPage(this.getPage(this.getUrlToScrape()));
		logger.info("ustawianie statusCode");
		this.setStatusCode(this.getCurrentPage().getWebResponse().getStatusCode());
		logger.info("sprawdzanie czy status code==200, statusCode=" + this.getStatusCode());

		if (this.getStatusCode() == 200) {
			this.parsing(idThread);
		} else

			logger.warn("STATUS=" + this.getStatusCode());

	}

	@Override
	public HtmlPage getPage(String url) {
		WebClient client = new WebClient();
		client.getOptions().setThrowExceptionOnScriptError(false);
//		client.setJavaScriptTimeout(100);
		// client.getOptions().setJavaScriptEnabled(true);
		logger.info("proba pobrania strony URL=" + this.getUrlToScrape());
		try {
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
			return client.getPage(this.getUrlToScrape());
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			logger.error("FailingHtmlStatusCodeException");
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("MalformedURLException");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException");
			e.printStackTrace();
			return null;
		}
	}

	public void testStrony() {
		WebClient client = new WebClient();
		client.getOptions().setThrowExceptionOnScriptError(false);
		
		logger.info("proba pobrania strony URL=" + this.getUrlToScrape());
		try {
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
			HtmlPage page = client.getPage(this.getUrlToScrape());
			logger.info("udalo sie pobraæ stronê");
			logger.info("statusCode=" + page.getWebResponse().getStatusCode());
			logger.info("statusMessage=" + page.getWebResponse().getStatusMessage());
			logger.info("HTML CODE=" + page.getWebResponse().getContentAsString());
			logger.info("HTML CODE dlugosc=" + page.getWebResponse().getContentAsString().length());
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			logger.error("FailingHtmlStatusCodeException");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("MalformedURLException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException");
			e.printStackTrace();
		}
	}

	public List<String> fetchUrlsToScrape() {
		// TODO Auto-generated method stub
		return null;
	}

	public void parsing(int idThread) {
		Profil mainProfil = new Profil();
		mainProfil.setIdHost(Integer.parseInt(properties.getProperty("idHost")));
		mainProfil.setIdThread(idThread);
		try {
			String htmlCode = this.getCurrentPage().asText();
			HtmlPage mainPage = this.getCurrentPage();
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
			DomElement contactData = mainPage.getElementById("contactData");
			DomElement basicDataTable = mainPage.getElementById("basicDataTable");
			DomElement pageHeader = mainPage.getFirstByXPath("//div[@class=\"page-header\"]");
			List<Object> osoby = (List<Object>) mainPage
					.getByXPath("//tr[@itemtype=\"http://data-vocabulary.org/Person\"]");

			String[] header;
			String[] basicDataTableLines;
			String[] contactDataLines;
			try {
				header = pageHeader.asText().split("\n");
				basicDataTableLines = basicDataTable.asText().split("\n");
				contactDataLines = contactData.asText().split("\n");
			} catch (Exception e) {
				System.err.println("M:prawdopodobnie nie pobralo kodu zrodlowego dla strony " + urlToScrape);
				header = new String[1];
				header[0] = "";
				basicDataTableLines = new String[1];
				basicDataTableLines[0] = "";
				contactDataLines = new String[1];
				contactDataLines[0] = "";
			}

			for (int i = 0; i < contactDataLines.length; i++) {
				if (contactDataLines[i].contains("Adres strony WWW:"))
					mainProfil.setWebsite(contactDataLines[i].substring(18));
				if (contactDataLines[i].contains("Adres email:	"))
					mainProfil.setEmail(contactDataLines[i].substring(13));
			}
			mainProfil.setMeta(urlToScrape);
			mainProfil.setNazwa(header[0]);
			try{
				
			
			header[1]=header[1].replaceAll("Ostatnia aktualizacja danych: ", "");
			mainProfil.setDataOstatniaAktualizacjaDanychStr(header[1]);
			mainProfil.setDataOstatniaAktualizacjaDanych(this.getDateFormat(header[1]));
			for (int i = 0; i < basicDataTableLines.length; i++) {

				if (basicDataTableLines[i].contains("KRS:	"))
					mainProfil.setKrs(basicDataTableLines[i].substring(5));
				if (basicDataTableLines[i].contains("NIP:	"))
					mainProfil.setNip(basicDataTableLines[i].substring(5));
				if (basicDataTableLines[i].contains("Regon:	"))
					mainProfil.setRegon(basicDataTableLines[i].substring(6));
				if (basicDataTableLines[i].contains("Kapita³ zak³adowy:	"))
					mainProfil.setKapitalZakladowy(basicDataTableLines[i].substring(18));
				if (basicDataTableLines[i].contains("Forma prawna:	"))
					mainProfil.setFormaPrawna(basicDataTableLines[i].substring(13));
				if (basicDataTableLines[i].contains("Adres:	 ")) {
					mainProfil.setAdresLinia1(basicDataTableLines[i].substring(7));
					mainProfil.setAdresLinia2(basicDataTableLines[i + 1]);
					mainProfil.setWojewodztwo(basicDataTableLines[i + 2]);
				}
				if (basicDataTableLines[i].contains("Data rejestracji KRS	")) {
					mainProfil.setDataRejestracjiKrsStr(basicDataTableLines[i].substring(21));
					mainProfil.setDataRejestracjiKrs(this.getDateFormat(basicDataTableLines[i].substring(21)));

				}
				if (basicDataTableLines[i].contains("Ostatnia zmiana w KRS	")) {
					mainProfil.setDataOstatniaZmianaKrsStr(basicDataTableLines[i].substring(22));
					mainProfil.setDataOstatniaZmianaKrs(this.getDateFormat(basicDataTableLines[i].substring(22)));
//					logger.info("ostatnia zmiana KRS DATA="+mainProfil.getDataOstatniaZmianaKrs());
				}
				if (basicDataTableLines[i].contains("Data rejestracji Regon")) {
					mainProfil.setDataRejestrtacjiRegonStr(basicDataTableLines[i].substring(23));
					String test = basicDataTableLines[i].substring(23);
//					logger.info("Data rejestracji regon:");
//					for(int a=0;a<test.length();a++){
//						logger.info("TEST="+test.charAt(a));
//					}
					mainProfil.setDataRejestrtacjiRegon(this.getDateFormat(basicDataTableLines[i].substring(23)));
//					logger.info("Data rejestracji regon="+mainProfil.getDataRejestrtacjiRegonStr()+"\nData="+mainProfil.getDataRejestrtacjiRegon());
				}
				if (basicDataTableLines[i].contains("Reprezentacja	"))
					mainProfil.setReprezentacja(basicDataTableLines[i].substring(13));
				if (basicDataTableLines[i].contains("Sposób reprezentacji	"))
					mainProfil.setSposobReprezentacji(basicDataTableLines[i].substring(20));
				if (basicDataTableLines[i].contains("S¹d	"))
					mainProfil.setSad(basicDataTableLines[i].substring(4));
				if (basicDataTableLines[i].contains("Sygnatura	"))
					mainProfil.setSygnatura(basicDataTableLines[i].substring(10));
				if (basicDataTableLines[i].contains("Przewa¿aj¹ca dzia³alnoœæ gospodarcza	"))
					mainProfil.setPrzewazajacaDzialalnoscGospodarcza(basicDataTableLines[i].substring(37));

			}
//			List<DomNode> osobyNode = (List<DomNode>) mainPage.getByXPath("//th[@itemprop=\"name\"]");
			List<DomNode> osobyNode = (List<DomNode>) mainPage.getByXPath("//tr[@itemtype=\"http://data-vocabulary.org/Person\"]/th[@itemprop=\"name\"]");
			List<DomNode> onlyPersonNode = (List<DomNode>)mainPage.getByXPath("//tr[@itemtype=\"http://data-vocabulary.org/Person\"]/th[@itemprop=\"name\"]");
			List<DomNode> osobyRole = (List<DomNode>) mainPage.getByXPath("//div[@itemprop=\"role\"]");
			List<DomNode> osobyAffil = (List<DomNode>) mainPage.getByXPath("//td/span[@style=\"display:none\"]");

//			logger.info(mainProfil.toString());
//			for(DomNode d: onlyPersonNode) logger.info("PERSON>>"+d.getTextContent());
			try {
				for (int iter = 0; iter < osobyNode.size(); iter++) {
//					System.out.println("OsobyNode.size()=" + osobyNode.size());
//					System.out.println("osoba= " + osobyNode.get(iter).asText() + ", stanowisko= "
//							+ osobyRole.get(iter).asText() + ", affil= " + osobyAffil.get(iter).asText());
					// String danePersonalne=
					String[] imieNazwisko = osobyNode.get(iter).asText().toString().split(" ");
					if (imieNazwisko.length >= 2) {
						mainProfil.getOsoby()
								.add(new Osoba(imieNazwisko[0], imieNazwisko[1], osobyRole.get(iter).asText()));
					} else {
						mainProfil.getOsoby()
								.add(new Osoba("", osobyNode.get(iter).asText(), osobyRole.get(iter).asText()));
					}
				}
			} catch (IndexOutOfBoundsException e) {
				logger.info("Liczba elementow:\nosobyNode=" + osobyNode.size() + "\nosobyRole=" + osobyRole.size()
						+ "\nosobyAffil=" + osobyAffil.size());
//				logger.info("osobyNode:");
//				for (DomNode node : osobyNode)
//					logger.info(node.asText());
//				logger.info("osobyRole:");
//				for (DomNode node : osobyRole)
//					logger.info(node.asText());
//				logger.info("osobyAffil:");
//				for (DomNode node : osobyAffil)
//					logger.info(node.asText());
				logger.error("prawdopodobnie brak spojnosci danych FIRM oraz OSOB!");
				logger.error(e.getMessage());
				logger.error(e.getCause());
			}

			if (mainProfil.getNazwa().length() > 2) {

				EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
				entityManager.getTransaction().begin();
				entityManager.persist(mainProfil);
				entityManager.getTransaction().commit();
				entityManager.close();
			}
		} catch (Exception e) {
			logger.error("M: problem z wczytaniem strony");
			logger.error(e.getMessage());
			logger.error(e.getCause());
			e.printStackTrace();
		}
		}catch(Exception e){
			logger.error("prawdopodobnie BAN!");
		}
	}

	@SuppressWarnings("deprecation")
	public Date getDateFormat(String str) {
		str = str.replaceAll("\r", "");
//		logger.info("Data text = " + str);
		String[] strTab = str.split(" ");
//		for (String s : strTab)
//			logger.info("linia=" + s + "<<");
		if (strTab.length >= 3) {
			Miesiac miesiac = Miesiac.valueOf(strTab[1]);
			switch (miesiac) {
			case stycznia:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 0, Integer.parseInt(strTab[0]));
			case lutego:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 1, Integer.parseInt(strTab[0]));
			case marca:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 2, Integer.parseInt(strTab[0]));
			case kwietnia:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 3, Integer.parseInt(strTab[0]));
			case maja:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 4, Integer.parseInt(strTab[0]));
			case czerwca:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 5, Integer.parseInt(strTab[0]));
			case lipca:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 6, Integer.parseInt(strTab[0]));
			case sierpnia:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 7, Integer.parseInt(strTab[0]));
			case wrzeœnia:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 8, Integer.parseInt(strTab[0]));
			case paŸdziernika:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 9, Integer.parseInt(strTab[0]));
			case listopada:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 10, Integer.parseInt(strTab[0]));
			case grudnia:
				return new Date(Integer.parseInt(strTab[2]) - 1900, 11, Integer.parseInt(strTab[0]));
			}
		} else
			return null;
		return null;

	}

	public Boolean insertData(Object objectToInsert) {
		// TODO Auto-generated method stub
		return null;
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

	public Object parsing(HtmlPage page, Object mainProfil) {
		// TODO Auto-generated method stub
		return null;
	}

}
