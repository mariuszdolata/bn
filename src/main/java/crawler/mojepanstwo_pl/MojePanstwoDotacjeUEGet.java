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
import crawler.mojepanstwo_pl.entites.DotacjeUE;

public class MojePanstwoDotacjeUEGet extends ScrapeClass implements Scrape {

	private String daneMojePanstwo = "";

	public String getDaneMojePanstwo() {
		return daneMojePanstwo;
	}

	public void setDaneMojePanstwo(String daneMojePanstwo) {
		this.daneMojePanstwo = daneMojePanstwo;
	}

	public MojePanstwoDotacjeUEGet(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			String urlToScrape) {
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
			DotacjeUE d = new DotacjeUE();
			JsonObject dataAll = dataObjectArray.get(i).getAsJsonObject();
			JsonObject data = dataAll.get("data").getAsJsonObject();
			d.setSymbol(this.getValue(data, "dotacje_ue.symbol"));
			d.setFormaPrawnaStr(this.getValue(data, "dotacje_ue.forma_prawna_str"));
			if (data.has("dotacje_ue.data_rozpoczecia") && !data.get("dotacje_ue.data_rozpoczecia").isJsonNull())
				d.setDataRozpoczecia(Date.valueOf(data.get("dotacje_ue.data_rozpoczecia").getAsString()));
			d.setBeneficjentNazwa(this.getValue(data, "dotacje_ue.beneficjent_nazwa"));
			if (data.has("dotacje_ue.wartosc_ogolem") && !data.get("dotacje_ue.wartosc_ogolem").isJsonNull())
				d.setWartoscOgolem(data.get("dotacje_ue.wartosc_ogolem").getAsBigDecimal());
			if (data.has("dotacje_ue.data_zakonczenia") && !data.get("dotacje_ue.data_zakonczenia").isJsonNull())
				d.setDataZakonczenia(Date.valueOf(data.get("dotacje_ue.data_zakonczenia").getAsString()));
			d.setMojePanstwoId(Long.parseLong(this.getValue(data, "dotacje_ue.id")));
			d.setTytul(this.getValue(data, "dotacje_ue.tytul"));
			if (data.has("dotacje_ue.data_utworzenia_ksi") && !data.get("dotacje_ue.data_utworzenia_ksi").isJsonNull())
				d.setDataUtworzeniaKsi(Date.valueOf(data.get("dotacje_ue.data_utworzenia_ksi").getAsString()));
			d.setUmowaNumer(this.getValue(data, "dotacje_ue.umowa_nr"));
			d.setProjektZakonczony(this.getValue(data, "dotacje_ue.projekt_zakonczony"));
			if (data.has("dotacje_ue.os_id") && !data.get("dotacje_ue.os_id").isJsonNull())
				d.setOsId(Long.parseLong(data.get("dotacje_ue.os_id").getAsString()));
			if (data.has("dotacje_ue.wartosc_dofinansowanie_ue")
					&& !data.get("dotacje_ue.wartosc_dofinansowanie_ue").isJsonNull())
				d.setWartoscDofinansowaniaEU(data.get("dotacje_ue.wartosc_dofinansowanie_ue").getAsBigDecimal());
			if (data.has("dotacje_ue.data_podpisania") && !data.get("dotacje_ue.data_podpisania").isJsonNull())
				d.setDataPodpisania(Date.valueOf(data.get("dotacje_ue.data_podpisania").getAsString()));
			if (data.has("dotacje_ue.wartosc_wydatki_kwalifikowane")
					&& !data.get("dotacje_ue.wartosc_wydatki_kwalifikowane").isJsonNull())
				d.setWartoscWydatkiKwalfikowane(data.get("dotacje_ue.wartosc_wydatki_kwalifikowane").getAsBigDecimal());
			if (data.has("dotacje_ue.dzialanie_id") && !data.get("dotacje_ue.dzialanie_id").isJsonNull())
				d.setDzialanieId(Long.parseLong(data.get("dotacje_ue.dzialanie_id").getAsString()));
			if (data.has("dotacje_ue.forma_prawna_id") && !data.get("dotacje_ue.forma_prawna_id").isJsonNull())
				d.setFormaPrawnaId(Long.parseLong(data.get("dotacje_ue.forma_prawna_id").getAsString()));
			if (data.has("dotacje_ue.program_id") && !data.get("dotacje_ue.program_id").isJsonNull())
				d.setProgramId(Long.parseLong(data.get("dotacje_ue.program_id").getAsString()));
			if (data.has("dotacje_ue.beneficjent_id") && !data.get("dotacje_ue.beneficjent_id").isJsonNull())
				d.setBeneficjentId(Long.parseLong(data.get("dotacje_ue.beneficjent_id").getAsString()));
			if (data.has("dotacje_ue.wartosc_dofinansowanie")
					&& !data.get("dotacje_ue.wartosc_dofinansowanie").isJsonNull())
				d.setWartoscDofinansowania(data.get("dotacje_ue.wartosc_dofinansowanie").getAsBigDecimal());
//			logger.info("Dotacja =>"+d.toString());
			try{
				this.insertDataEntity(d);
			}catch(Exception e){
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
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
