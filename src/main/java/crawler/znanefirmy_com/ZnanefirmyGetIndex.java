package crawler.znanefirmy_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlStrong;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;

public class ZnanefirmyGetIndex extends ScrapeClass implements Scrape {

	private List<ZnanefirmyIndex> companies = new ArrayList<ZnanefirmyIndex>();
	private int maxPagination;
	
	
	public int getMaxPagination() {
		return maxPagination;
	}

	public void setMaxPagination(int maxPagination) {
		this.maxPagination = maxPagination;
	}

	public List<ZnanefirmyIndex> getCompanies() {
		return companies;
	}

	public void setCompanies(List<ZnanefirmyIndex> companies) {
		this.companies = companies;
	}

	public ZnanefirmyGetIndex(int threadId, Properties properties, EntityManagerFactory entityManagerFactory, String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		this.setCurrentPage(this.getPage(this.getUrlToScrape()));
		this.setStatusCode(this.getCurrentPage().getWebResponse().getStatusCode());
		this.parsing(this.getCurrentPage(), new ZnanefirmyIndex());
		this.insertDataListEntity(this.getCompanies());
	}

	public List<String> fetchUrlsToScrape() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object parsing(HtmlPage page, Object mainProfil) {
		System.out.println("Wejœcie w parsowanie indexu");
		List<HtmlAnchor> urls = (List<HtmlAnchor>)this.getCurrentPage().getByXPath("//div[@class=\"well\"]/h4/a");
		List<HtmlStrong> adresy = (List<HtmlStrong>) this.getCurrentPage().getByXPath("//div[@class=\"well\"]/strong");
		List<HtmlAnchor> pagination = (List<HtmlAnchor>) this.getCurrentPage().getByXPath("//ul[@class=\"pagination\"]/li/a");
		for(int i=0;i<pagination.size();i++){
			if(!(pagination.get(i).getTextContent().contains("Poprzednia") || pagination.get(i).getTextContent().contains("Nastêpna"))){
				this.setMaxPagination(Integer.parseInt(pagination.get(i).getTextContent()));
			}
		}
		System.out.println("Pagination = "+maxPagination);
		if(urls.size()==adresy.size()){			
			for(int i=0;i<urls.size();i++){
				companies.add(new ZnanefirmyIndex(urls.get(i).getTextContent(), "http://znanefirmy.com"+urls.get(i).getHrefAttribute(),adresy.get(i).asText(), this.getUrlToScrape()));
			}
		}
		
		return null;
	}

	public Boolean insertData(Object objectToInsert) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertDataEntity(Object o) {
		// TODO Auto-generated method stub

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
