package crawler.znanefirmy_com;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;

public class ZnanefirmyGetProfil extends ScrapeClass implements Scrape{
	private ZnanefirmyProfil profil;
	

	public ZnanefirmyProfil getProfil() {
		return profil;
	}

	public void setProfil(ZnanefirmyProfil profil) {
		this.profil = profil;
	}

	public ZnanefirmyGetProfil(int threadId, Properties properties, EntityManagerFactory entityManagerFactory, String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		this.setCurrentPage(this.getPage(this.getUrlToScrape()));
		this.setStatusCode(this.getCurrentPage().getWebResponse().getStatusCode());
		this.parsing(this.getCurrentPage(), new ZnanefirmyProfil());
		this.insertDataEntity(this.getProfil());
//		logger.info(this.getCurrentPage().asXml());
	}

	public List<String> fetchUrlsToScrape() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object parsing(HtmlPage page, Object mainProfil) {
//		logger.info("ZnanefirmyGetProfil.parsing(...)");
		List<HtmlTableRow> data = (List<HtmlTableRow>) this.getCurrentPage().getByXPath("//table[@class=\"table table-bordered table-striped\"]/tbody/tr");
		this.setProfil(new ZnanefirmyProfil());
		profil.setMeta(this.getUrlToScrape());
		for(HtmlTableRow row:data){
//			System.out.println("row[0]="+row.getCell(0).asText()+", row[1]="+row.getCell(1).asText());
			if(row.getCell(0).asText().contains("Nazwa firmy"))profil.setNazwa(row.getCell(1).asText());
			else if (row.getCell(0).asText().contains("W³aœciciel"))profil.setWlasciciel(row.getCell(1).asText());
			else if (row.getCell(0).asText().contains("NIP"))profil.setNip(row.getCell(1).asText());
			else if (row.getCell(0).asText().contains("REGON"))profil.setRegon(row.getCell(1).asText());
			else if (row.getCell(0).asText().contains("Data za³o¿enia"))profil.setDataZalozenia(row.getCell(1).asText());
			else if (row.getCell(0).asText().contains("Wiek firmy"))profil.setWiek(row.getCell(1).asText());
		}
		List<HtmlTableRow> location = (List<HtmlTableRow>) this.getCurrentPage().getByXPath("//table[@class=\"table table-bordered table-striped table-hover\"]/tbody/tr");
		for(HtmlTableRow row:location){
//			System.out.println("row[0]="+row.getCell(0).asText()+", row[1]="+row.getCell(1).asText());
			if(row.getCell(0).asText().contains("Wojewóztwo")) profil.setWojewodztwo(row.getCell(1).asText());
			else if(row.getCell(0).asText().contains("Miasto")) profil.setMiasto(row.getCell(1).asText());
			else if(row.getCell(0).asText().contains("Kod Pocztowy")) profil.setKodPocztowy(row.getCell(1).asText());
			else if(row.getCell(0).asText().contains("Ulica")) profil.setUlica(row.getCell(1).asText());
		}
		List<HtmlAnchor> main_pkd = (List<HtmlAnchor>) this.getCurrentPage().getByXPath("//ul[@id=\"tree-small\"]/li/a");
		List<HtmlAnchor> pkd = (List<HtmlAnchor>) this.getCurrentPage().getByXPath("//ul[@id=\"tree-small\"]/li/ul/li/a");
		for(HtmlAnchor a:main_pkd){
			System.out.println("MAIN_a text="+a.getTextContent() + ",   a url="+a.getHrefAttribute());
			profil.setPkd_main(a.getTextContent());
		}
		for(HtmlAnchor a:pkd){
			System.out.println("a text="+a.getTextContent() + ",   a url="+a.getHrefAttribute());
			profil.setPkd2(a.getTextContent());
		}
		List<HtmlDivision> gps = (List<HtmlDivision>) this.getCurrentPage().getByXPath("//div[@style=\"font-size:18px;border:1px solid silver;padding:15px;margin-bottom:20px;\"]");
		for(HtmlDivision gpsData:gps) profil.setGps(gpsData.asText());
		System.out.println(profil.toString());
//		System.out.println(this.getCurrentPage().asXml());
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

}
