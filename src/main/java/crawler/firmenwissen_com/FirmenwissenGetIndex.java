package crawler.firmenwissen_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;

public class FirmenwissenGetIndex extends ScrapeClass implements Scrape {

	/**
	 * Liczba rekordów indexu na pojedynczej stronie
	 */
	private int totalNumberOfRecords;
//	private static final String[][] String = null;
	private List<FirmenwissenIndex> companies = new ArrayList<FirmenwissenIndex>();

	public List<FirmenwissenIndex> getCompanies() {
		return companies;
	}

	public void setCompanies(List<FirmenwissenIndex> companies) {
		this.companies = companies;
	}

	
	public int getTotalNumberOfRecords() {
		return totalNumberOfRecords;
	}

	public void setTotalNumberOfRecords(int totalNumberOfRecords) {
		this.totalNumberOfRecords = totalNumberOfRecords;
	}

	public FirmenwissenGetIndex(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		// this.setUrlToScrape(urlToScrape);
		// this.setCurrentPage(this.getPage(this.getUrlToScrape()));
		// this.setStatusCode(this.getCurrentPage().getWebResponse().getStatusCode());
		// this.parsing(this.getCurrentPage(), new ZnanefirmyIndex());
	}

	public List<String> fetchUrlsToScrape() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object parsing(HtmlPage page, Object mainProfil) {
//		logger.info(this.getCurrentPage().asXml());
		logger.info("PARSING - index");
		String[] addressLine1 = new String[10];
		String[] addressLine2 = new String[10];
		List<HtmlDivision> records = (List<HtmlDivision>) this.getCurrentPage()
				.getByXPath("//div[@class=\"container container--border companyListItem__resultItem\"]");
		List<HtmlAnchor> firmenUrl = (List<HtmlAnchor>) this.getCurrentPage()
				.getByXPath("//a[@class=\"companyListItem__companyTitle\"]");
		List<HtmlDivision> addresses = (List<HtmlDivision>) this.getCurrentPage()
				.getByXPath("//div[@class=\"featureBox--content\"]");
		List<HtmlDivision> branches = (List<HtmlDivision>) this.getCurrentPage().getByXPath(
				"//div[@class=\"container container--border companyListItem__resultItem\"]/div[@class=\"margin-bottom-1\"][2]");
		List<HtmlDivision> updatess = (List<HtmlDivision>) this.getCurrentPage()
				.getByXPath("//div[@class=\"container container--border companyListItem__resultItem\"]/div[1]");
		int iter = 0;
		for (HtmlDivision div : addresses) {
			String[] add = div.asText().split("\n");
			if (add.length > 1) {
				addressLine1[iter] = add[0];
				addressLine2[iter] = add[1];

			}
			iter++;
		}
		this.setTotalNumberOfRecords(records.size());
		int numberOfRecord = records.size();
		System.out.println("Liczba rekordow w indexie to = " + numberOfRecord);
		for (int i = 0; i < numberOfRecord; i++) {
			this.getCompanies()
					.add(new FirmenwissenIndex(firmenUrl.get(i).getTextContent(), firmenUrl.get(i).getHrefAttribute(),
							addressLine1[i], addressLine2[i], branches.get(i).asText(), updatess.get(i).asText(), this.getUrlToScrape()));
		}
		
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
