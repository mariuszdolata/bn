package crawler.firmenwissen_com;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;

public class FirmenwissenGetProfil extends ScrapeClass implements Scrape {

	public FirmenwissenGetProfil(int threadId, Properties properties, EntityManagerFactory entityManagerFactory, String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		this.setCurrentPage(this.getPage(this.getUrlToScrape()));
		this.parsing(this.getCurrentPage(), new FirmenwissenProfil());
	}

	public List<String> fetchUrlsToScrape() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object parsing(HtmlPage page, Object mainProfil) {
		logger.info("Wejscie w FirmenwissenGetProfil.parsing()");
		PrintWriter out;
		try {
			out = new PrintWriter("c:\\crawlers\\files\\profil1.txt");
			out.println(this.currentPage.asXml());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<HtmlHeading1> companyName = (List<HtmlHeading1>)this.getCurrentPage().getByXPath("//h1[@class=\"text-center fontSize-xxl\"]");
		List<HtmlDivision> description = (List<HtmlDivision>)this.getCurrentPage().getByXPath("//div[@class=\"toggleBox__itemPanelContent margin-bottom-1\"][1]");
		List<HtmlDivision> data1 = (List<HtmlDivision>)this.getCurrentPage().getByXPath("//div[@class=\"col m-col-4 t-col-6 d-col-6 margin-bottom-1\"]");
		List<HtmlDivision> data2 = (List<HtmlDivision>)this.getCurrentPage().getByXPath("//div[@class=\"col m-col-4 m-col-first t-col-6 d-col-6 margin-bottom-1\"]");
		List<HtmlDivision> data = new ArrayList<HtmlDivision>();
		data.addAll(data1);
		data.addAll(data2);
		FirmenwissenProfil profil = new FirmenwissenProfil();
		for(HtmlDivision div:data){
			System.out.println("text="+div.getTextContent());
			if(div.getTextContent().contains("Street"))profil.setStreet(div.getTextContent().replaceAll("Street", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("City"))profil.setCity(div.getTextContent().replaceAll("City", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Telephone"))profil.setPhone(div.getTextContent().replaceAll("Telephone", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Email"))profil.setEmail(div.getTextContent().replaceAll("Email", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Trade register"))profil.setTradeRegister(div.getTextContent().replaceAll("Trade register", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Management"))profil.setManagment(div.getTextContent().replaceAll("Management", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Zip"))profil.setZip(div.getTextContent().replaceAll("Zip", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Country"))profil.setCountry(div.getTextContent().replaceAll("Country", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Telefax"))profil.setFax(div.getTextContent().replaceAll("Telefax", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Website"))profil.setWebsite(div.getTextContent().replaceAll("Website", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Register number"))profil.setRegisterNumber(div.getTextContent().replaceAll("Register number", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Owners"))profil.setOwners(div.getTextContent().replaceAll("Owners", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
			else if(div.getTextContent().contains("Creditreform number"))profil.setCreditreformNumber(div.getTextContent().replaceAll("Creditreform number", "").replaceAll("\n", "").trim().replaceAll(" +", " "));
		}
		List<HtmlSpan> address = (List<HtmlSpan>)this.getCurrentPage().getByXPath("//div[@class=\"row\"]/div/span[@class=\"addressLabel\"]");
		List<HtmlParagraph> lastModified = (List<HtmlParagraph>)this.getCurrentPage().getByXPath("//p[@class=\"margin-bottom-2\"]");
		profil.setLast_modified(lastModified.get(0).getTextContent().replaceAll("Last modified:", "").trim().replaceAll(" +", " "));
		List<HtmlParagraph> status = (List<HtmlParagraph>) this.getCurrentPage().getByXPath("//p[@style=\"padding-left: 5px; background-color: #efefef;\"]");
		profil.setStatus(status.get(0).getTextContent().replaceAll("Status:", "").trim().replaceAll(" +", " "));
		profil.setCompanyName(companyName.get(0).getTextContent().trim().replaceAll(" +", " "));
		profil.setDescription(description.get(0).getTextContent().trim().replaceAll(" +", " "));
		profil.setMeta(this.urlToScrape);
		this.insertDataEntity(profil);
		System.out.println(profil.toString());
		
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

	public Boolean insertData(Object objectToInsert) {
		// TODO Auto-generated method stub
		return null;
	}

}
