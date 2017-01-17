package crawler.mojepanstwo_pl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
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
import crawler.mojepanstwo_pl.KRSOsoba.Plec;

public class MojePanstwoKRSOsobaGet extends ScrapeClass implements Scrape {

	private String daneMojePanstwo="";

	public String getDaneMojePanstwo() {
		return daneMojePanstwo;
	}

	public void setDaneMojePanstwo(String daneMojePanstwo) {
		this.daneMojePanstwo = daneMojePanstwo;
	}

	public MojePanstwoKRSOsobaGet(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		this.setDaneMojePanstwo(getContent(this.getUrlToScrape()));
		if (this.getStatusCode() == 200 && this.getDaneMojePanstwo() != null)
			this.parsing();
		else
			logger.warn("Pominieto parsowanie : statusCode=" + this.getStatusCode());

	}

	public String getContent(String url) {
		WebClient client = new WebClient();
		// client.setHTMLParserListener(HTMLParserListener.LOG_REPORTER);
		try {
			logger.info("WYKONANIE getPage(" + url + ")");
			// String stream =
			// client.getPage(url).getWebResponse().getContentAsString();
			Page page = client.getPage(url);
			this.setStatusCode(page.getWebResponse().getStatusCode());
			if (this.getStatusCode() == 200) {
				return client.getPage(url).getWebResponse().getContentAsString();
			} else {
				return null;
			}
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

	public void parsing() {
		KRSOsoba osoba = new KRSOsoba();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(this.getDaneMojePanstwo());
		JsonObject object = element.getAsJsonObject();
		JsonObject data = object.getAsJsonObject("data");
		if (data.has("krs_osoby.id") && !data.get("krs_osoby.id").isJsonNull())
			osoba.setOsobaId(Long.parseLong(data.get("krs_osoby.id").getAsString()));
		// STR
		if (data.has("krs_osoby.str") && !data.get("krs_osoby.str").isJsonNull()) {
			String[] str = data.get("krs_osoby.str").getAsString().split("<br/>");
			for (int i = 0; i < str.length; i++) {
				String line = str[i];
				int endStanowisko = line.indexOf("<a href");
				int startKrs = line.indexOf("/dane/krs_podmioty/");
				int endKrs = line.indexOf("\">");
				try {
					Stanowisko stanowisko = new Stanowisko();
					stanowisko.setStanowisko(line.substring(0, endStanowisko - 2));
					stanowisko.setKrs(line.substring(startKrs + 19, endKrs));
					stanowisko.setNazwaFirmy(line.substring(endKrs + 2).replaceAll("</a>", ""));
					osoba.getStanowiska().add(stanowisko);
				} catch (Exception e) {
					logger.warn("nie mo¿na dodaæ stanowiska dla osoby url=" + this.getUrlToScrape());
					logger.warn(e.getMessage());
					logger.warn(e.getCause());
				}
			}
		}
		if (data.has("krs_osoby.data_urodzenia") && !data.get("krs_osoby.data_urodzenia").isJsonNull())
			osoba.setDataUrodzenia(Date.valueOf(data.get("krs_osoby.data_urodzenia").getAsString()));
		if (data.has("krs_osoby.privacy") && !data.get("krs_osoby.privacy").isJsonNull())
			osoba.setPrivacy(data.get("krs_osoby.privacy").getAsInt());
		if (data.has("krs_osoby.imie_pierwsze") && !data.get("krs_osoby.imie_pierwsze").isJsonNull())
			osoba.setImiePierwsze(data.get("krs_osoby.imie_pierwsze").getAsString());
		if (data.has("krs_osoby.imie_drugie") && !data.get("krs_osoby.imie_drugie").isJsonNull())
			osoba.setImieDrugie(data.get("krs_osoby.imie_drugie").getAsString());
		if (data.has("krs_osoby.nazwisko") && !data.get("krs_osoby.nazwisko").isJsonNull())
			osoba.setNazwisko(data.get("krs_osoby.nazwisko").getAsString());
		if (data.has("krs_osoby.plec") && !data.get("krs_osoby.plec").isJsonNull()) {
			if (data.get("krs_osoby.plec").getAsString().contains("M"))
				osoba.setPlec(Plec.Mezczyzna);
			if (data.get("krs_osoby.plec").getAsString().contains("K"))
				osoba.setPlec(Plec.Kobieta);
		}
		JsonArray gminy = data.getAsJsonArray("krs_osoby.gmina_id");
		for (int i = 0; i < gminy.size(); i++) {
			KRSOsobaGmina osobaGmina = new KRSOsobaGmina();
			JsonPrimitive gmina = gminy.get(i).getAsJsonPrimitive();
			if (!gmina.isJsonNull())
				osobaGmina.setOsobaGminaMojePanstwo(Long.parseLong(gmina.getAsString()));
			osoba.getGminy().add(osobaGmina);
		}
		try{
			this.insertDataEntity(osoba);			
		}catch(Exception e){
			logger.error("Nie udalo wstawic sie obiektu Osoba dla url="+this.getUrlToScrape());
			logger.error(e.getCause());
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
		}

	}

	public Object parsing(HtmlPage page, Object mainProfil) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean insertData(Object objectToInsert) {
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
