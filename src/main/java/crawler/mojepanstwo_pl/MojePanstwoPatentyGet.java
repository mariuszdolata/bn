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

import crawler.api.Scrape;
import crawler.api.ScrapeClass;
import crawler.mojepanstwo_pl.entites.Patenty;

public class MojePanstwoPatentyGet extends ScrapeClass implements Scrape {

	private String daneMojePanstwo = "";

	public String getDaneMojePanstwo() {
		return daneMojePanstwo;
	}

	public void setDaneMojePanstwo(String daneMojePanstwo) {
		this.daneMojePanstwo = daneMojePanstwo;
	}

	public MojePanstwoPatentyGet(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		this.setDaneMojePanstwo(this.getContent(this.getUrlToScrape()));
		if (this.getStatusCode() == 200 && this.getDaneMojePanstwo().length() > 100) {
			this.parsing();
		}
	}

	private void parsing() {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(this.getDaneMojePanstwo());
		JsonObject object = element.getAsJsonObject();
		JsonArray dataObjectArray = object.getAsJsonArray("Dataobject");
		for (int i = 0; i < dataObjectArray.size(); i++) {
			Patenty p = new Patenty();
			JsonObject dataAll = dataObjectArray.get(i).getAsJsonObject();
			JsonObject data = dataAll.get("data").getAsJsonObject();
			if (data.has("patenty.espacenet_url") && !data.get("patenty.espacenet_url").isJsonNull())
				p.setEspacenetUrl(data.get("patenty.espacenet_url").getAsString());
			if (data.has("patenty.nr_str") && !data.get("patenty.nr_str").isJsonNull())
				p.setNrStr(Long.parseLong(data.get("patenty.nr_str").getAsString()));
			if (data.has("patenty.img") && !data.get("patenty.img").isJsonNull())
				p.setImg(data.get("patenty.img").getAsString());
			if (data.has("patenty.data") && !data.get("patenty.data").isJsonNull())
				p.setDataMojePanstwo(Date.valueOf(data.get("patenty.data").getAsString()));
			if (data.has("patenty.pierwszenstwo") && !data.get("patenty.pierwszenstwo").isJsonNull())
				p.setPierwszenstwo(data.get("patenty.pierwszenstwo").getAsString());
			if (data.has("patenty.data_ogloszenia") && !data.get("patenty.data_ogloszenia").isJsonNull())
				p.setDataOgloszenia(Date.valueOf(data.get("patenty.data_ogloszenia").getAsString()));
			if (data.has("patenty.pelnomocnik") && !data.get("patenty.pelnomocnik").isJsonNull())
				p.setPelnomocnik(data.get("patenty.pelnomocnik").getAsString());
			if (data.has("patenty.id") && !data.get("patenty.id").isJsonNull())
				p.setMojePanstwoId(Long.parseLong(data.get("patenty.id").getAsString()));
			if (data.has("patenty.data_zgloszenia_eur") && !data.get("patenty.data_zgloszenia_eur").isJsonNull())
				p.setDataZgloszeniaEU(Date.valueOf(data.get("patenty.data_zgloszenia_eur").getAsString()));
			if (data.has("patenty.uprawniony_id") && !data.get("patenty.uprawniony_id").isJsonNull())
				p.setUprawnionyId(Long.parseLong(data.get("patenty.uprawniony_id").getAsString()));
			if (data.has("patenty.tytul") && !data.get("patenty.tytul").isJsonNull())
				p.setTytul(data.get("patenty.tytul").getAsString());
			if (data.has("patenty.ogloszenie") && !data.get("patenty.ogloszenie").isJsonNull())
				p.setOgloszenie(data.get("patenty.ogloszenie").getAsString());
			if (data.has("patenty.opis") && !data.get("patenty.opis").isJsonNull())
				p.setOpis(data.get("patenty.opis").getAsString());
			if (data.has("patenty.register_url") && !data.get("patenty.register_url").isJsonNull())
				p.setRegisterUrl(data.get("patenty.register_url").getAsString());
			if (data.has("patenty.dokument_id") && !data.get("patenty.dokument_id").isJsonNull())
				p.setDokumentId(Long.parseLong(data.get("patenty.dokument_id").getAsString()));
			if (data.has("patenty.uprawniony") && !data.get("patenty.uprawniony").isJsonNull())
				p.setUprawniony(data.get("patenty.uprawniony").getAsString());
			if (data.has("patenty.kod") && !data.get("patenty.kod").isJsonNull())
				p.setKod(data.get("patenty.kod").getAsString());
			if (data.has("patenty.tworcy") && !data.get("patenty.tworcy").isJsonNull())
				p.setTworcy(data.get("patenty.tworcy").getAsString());

			// rozdzielenie uprawniony na nazwe firmy, miasto i kraj
			String[] infos = p.getUprawniony().split(",");
			if (infos.length >= 3) {
				p.setKraj(infos[infos.length - 1]);
				p.setMiasto(infos[infos.length - 2]);
				String nazwa = "";
				for (int j = 0; j <= infos.length - 3; j++) {
					nazwa += infos[j];
				}
				p.setNazwa(nazwa);
			}
			this.insertDataEntity(p);

		}
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
