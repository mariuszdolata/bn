package crawler.mojepanstwo_pl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;
import crawler.mojepanstwo_pl.entites.ZamowieniaPubliczneZamawiajacy;

public class MojePanstwoZamowieniaPubliczneZamawiajacyGet extends ScrapeClass implements Scrape {

	private String daneMojePanstwo = "";

	public String getDaneMojePanstwo() {
		return daneMojePanstwo;
	}

	public void setDaneMojePanstwo(String daneMojePanstwo) {
		this.daneMojePanstwo = daneMojePanstwo;
	}

	public MojePanstwoZamowieniaPubliczneZamawiajacyGet(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory, String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		this.setDaneMojePanstwo(this.getContent(this.getUrlToScrape()));
		if (this.getStatusCode() == 200 && this.getDaneMojePanstwo() != null)
			this.parsing();
		else
			logger.warn("Pominieto parsowanie : statusCode=" + this.getStatusCode());
	}

	public String getValue(JsonObject o, String s) {
		if (o.has(s) && !o.get(s).isJsonNull())
			return o.get(s).getAsString();
		else
			return null;
	}

	private void parsing() {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(this.getDaneMojePanstwo());
		JsonObject object = element.getAsJsonObject();
		JsonArray dataObjectArray = object.getAsJsonArray("Dataobject");
		for (int i = 0; i < dataObjectArray.size(); i++) {
			ZamowieniaPubliczneZamawiajacy zamawiajacy = new ZamowieniaPubliczneZamawiajacy();
			JsonObject dataAll = dataObjectArray.get(i).getAsJsonObject();
			JsonObject data = dataAll.get("data").getAsJsonObject();

			if (data.has("gminy.id") && !data.get("gminy.id").isJsonArray())
				zamawiajacy.setGminaId(Long.parseLong(data.get("gminy.id").getAsString()));
			zamawiajacy.setRegon(this.getValue(data, "zamowienia_publiczne_zamawiajacy.regon"));
			zamawiajacy.setFax(this.getValue(data, "zamowienia_publiczne_zamawiajacy.fax"));
			zamawiajacy.setRodzajInny("zamowienia_publiczne_zamawiajacy.rodzaj_inny");
			// if(data.has("zamowienia_publiczne_zamawiajacy.object_id")&&!data.get("zamowienia_publiczne_zamawiajacy.object_id").isJsonArray())zamawiajacy.setObjectId(Long.parseLong(data.get("zamowienia_publiczne_zamawiajacy.object_id").getAsString()));
			zamawiajacy.setWebsite(this.getValue(data, "zamowienia_publiczne_zamawiajacy.www"));
			zamawiajacy.setTelefon(this.getValue(data, "zamowienia_publiczne_zamawiajacy.telefon"));
			zamawiajacy.setUlica(this.getValue(data, "zamowienia_publiczne_zamawiajacy.ulica"));
			if (data.has("zamowienia_publiczne_zamawiajacy.rodzaj_id")
					&& !data.get("zamowienia_publiczne_zamawiajacy.rodzaj_id").isJsonArray())
				zamawiajacy.setRodzajId(
						Long.parseLong(data.get("zamowienia_publiczne_zamawiajacy.rodzaj_id").getAsString()));
			zamawiajacy.setWojewodztwo(this.getValue(data, "zamowienia_publiczne_zamawiajacy.wojewodztwo"));
			if (data.has("zamowienia_publiczne_zamawiajacy.id")
					&& !data.get("zamowienia_publiczne_zamawiajacy.id").isJsonArray())
				zamawiajacy.setMojepanstwoId(
						Long.parseLong(data.get("zamowienia_publiczne_zamawiajacy.id").getAsString()));
			if (data.has("zamowienia_publiczne_zamawiajacy.kod_pocztowy_id")
					&& !data.get("zamowienia_publiczne_zamawiajacy.kod_pocztowy_id").isJsonArray())
				zamawiajacy.setKodPocztowyId(
						Long.parseLong(data.get("zamowienia_publiczne_zamawiajacy.kod_pocztowy_id").getAsString()));
			zamawiajacy.setMiejscowosc(this.getValue(data, "zamowienia_publiczne_zamawiajacy.miejscowosc"));
			zamawiajacy.setEmail(this.getValue(data, "zamowienia_publiczne_zamawiajacy.email"));
			zamawiajacy.setDataSet(this.getValue(data, "zamowienia_publiczne_zamawiajacy.dataset"));
			zamawiajacy.setKodPocztowy(this.getValue(data, "zamowienia_publiczne_zamawiajacy.kod_pocztowy"));
			zamawiajacy.setNazwa(this.getValue(data, "zamowienia_publiczne_zamawiajacy.nazwa"));
			if (data.has("zamowienia_publiczne_zamawiajacy.instytucja_id")
					&& !data.get("zamowienia_publiczne_zamawiajacy.instytucja_id").isJsonArray())
				zamawiajacy.setInstytucjaId(
						Long.parseLong(data.get("zamowienia_publiczne_zamawiajacy.instytucja_id").getAsString()));
			zamawiajacy.setDomNumer(this.getValue(data, "zamowienia_publiczne_zamawiajacy.dom_numer"));
			zamawiajacy.setRodzaj(this.getValue(data, "zamowienia_publiczne_zamawiajacy.rodzaj"));
//			logger.info(zamawiajacy.toString());
			try {
				this.insertDataEntity(zamawiajacy);
				logger.info("zapisano obiekt zamawiajacy do bazy");
			} catch (Exception e) {
				logger.error("Nie udalo wstawic siê obiektu ZamowieniaPubliczne dla url=" + this.urlToScrape);
				logger.error(e.getCause());
				logger.error(e.getMessage());
				logger.error(e.getClass().getName());
			}
			logger.info("typ=" + data.getClass().getName());
		}
		// JsonObject data = object.getAsJsonObject("data");
	}

	public String getContent(String url) {
		WebClient client = new WebClient();
		client.getOptions().setTimeout(15000);
		// client.setHTMLParserListener(HTMLParserListener.LOG_REPORTER);
		try {
			logger.info("WYKONANIE getPage(" + url + ")");
			// String stream =
			// client.getPage(url).getWebResponse().getContentAsString();
			Page page = client.getPage(url);
			this.setStatusCode(page.getWebResponse().getStatusCode());
			do {
				if (this.getStatusCode() == 200) {

					return client.getPage(url).getWebResponse().getContentAsString();
				} else {
					return null;
				}
			} while (this.getStatusCode() != 200);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
	}

	public List<String> fetchUrlsToScrape() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object parsing(HtmlPage page, Object mainProfil) {
		// TODO Auto-generated method stub
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
