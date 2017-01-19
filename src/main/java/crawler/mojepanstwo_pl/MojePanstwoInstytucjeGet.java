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

import crawler.api.Scrape;
import crawler.api.ScrapeClass;
import crawler.mojepanstwo_pl.entites.Instytucje;

public class MojePanstwoInstytucjeGet extends ScrapeClass implements Scrape {
	private String daneMojePanstwo = "";

	public String getDaneMojePanstwo() {
		return daneMojePanstwo;
	}

	public void setDaneMojePanstwo(String daneMojePanstwo) {
		this.daneMojePanstwo = daneMojePanstwo;
	}

	public MojePanstwoInstytucjeGet(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		logger.info("Konstruktor MojePanstwoInstytucjeGet");
		this.setUrlToScrape(urlToScrape);
		this.setDaneMojePanstwo(this.getContent(this.getUrlToScrape()));
		if (this.getStatusCode() == 200 && this.getDaneMojePanstwo() != null){
			logger.info("Wszystkie warunki zosta³y spe³nione - parsowanie instutucji");
			this.parsing();
		}else logger.warn("warunki nie zostaly spelnione - statusCode="+this.getStatusCode()+", dlugoœæ danych="+this.getDaneMojePanstwo().length());
	}

	private void parsing() {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(this.getDaneMojePanstwo());
		JsonObject object = element.getAsJsonObject();
		JsonArray dataObjectArray = object.getAsJsonArray("Dataobject");
		for (int i = 0; i < dataObjectArray.size(); i++) {
			Instytucje ins = new Instytucje();
			JsonObject dataAll = dataObjectArray.get(i).getAsJsonObject();
			JsonObject data = dataAll.get("data").getAsJsonObject();
			if (data.has("instytucje.budzet_dotacje_i_subwencje")
					&& !data.get("instytucje.budzet_dotacje_i_subwencje").isJsonNull())
				ins.setBudzetDotacjeSubwencje(data.get("instytucje.budzet_dotacje_i_subwencje").getAsBigDecimal());
			if (data.has("instytucje.phone") && !data.get("instytucje.phone").isJsonNull())
				ins.setTelefon(data.get("instytucje.phone").getAsString());
			if (data.has("instytucje.twitter_account_id") && !data.get("instytucje.twitter_account_id").isJsonNull())
				ins.setTwitter(data.get("instytucje.twitter_account_id").getAsString());
			if (data.has("instytucje.www") && !data.get("instytucje.www").isJsonNull())
				ins.setWebsite(data.get("instytucje.www").getAsString());
			if (data.has("instytucje.adres_str") && !data.get("instytucje.adres_str").isJsonNull())
				ins.setAdres(data.get("instytucje.adres_str").getAsString());
			if (data.has("instytucje.id") && !data.get("instytucje.id").isJsonNull())
				ins.setMojePanstwoId(Long.parseLong(data.get("instytucje.id").getAsString()));
			if (data.has("instytucje.budzet_wydatki_na_obsluge_dlugu")
					&& !data.get("instytucje.budzet_wydatki_na_obsluge_dlugu").isJsonNull())
				ins.setBudzetWydatkiObslugaDlugu(
						data.get("instytucje.budzet_wydatki_na_obsluge_dlugu").getAsBigDecimal());
			if (data.has("instytucje.opis_html") && !data.get("instytucje.opis_html").isJsonNull())
				ins.setOpisHtml(data.get("instytucje.opis_html").getAsString());
			if (data.has("instytucje.budzet_wspolfinansowanie_ue")
					&& !data.get("instytucje.budzet_wspolfinansowanie_ue").isJsonNull())
				ins.setBudzetWspolfinansowanieUE(data.get("instytucje.budzet_wspolfinansowanie_ue").getAsBigDecimal());
			if (data.has("instytucje.nazwa") && !data.get("instytucje.nazwa").isJsonNull())
				ins.setNazwa(data.get("instytucje.nazwa").getAsString());
			if (data.has("instytucje.gmina_id") && !data.get("instytucje.gmina_id").isJsonNull())
				ins.setGminaId(Long.parseLong(data.get("instytucje.gmina_id").getAsString()));
			if (data.has("instytucje.parent_id") && !data.get("instytucje.parent_id").isJsonNull())
				ins.setParentId(Long.parseLong(data.get("instytucje.parent_id").getAsString()));
			if (data.has("instytucje.budzet_wydatki_biezace_jednostek_budzetowych")
					&& !data.get("instytucje.budzet_wydatki_biezace_jednostek_budzetowych").isJsonNull())
				ins.setBudzetWydatkiBiezaceJednostekBudzetowych(
						data.get("instytucje.budzet_wydatki_biezace_jednostek_budzetowych").getAsBigDecimal());
			if (data.has("instytucje.fax") && !data.get("instytucje.fax").isJsonNull())
				ins.setFax(data.get("instytucje.fax").getAsString());
			if (data.has("instytucje.budzet_plan") && !data.get("instytucje.budzet_plan").isJsonNull())
				ins.setBudzetPlan(data.get("instytucje.budzet_plan").getAsBigDecimal());
			if (data.has("instytucje.avatar") && !data.get("instytucje.avatar").isJsonNull())
				ins.setAvatar(data.get("instytucje.avatar").getAsString());
			if (data.has("instytucje.budzet_swiadczenia_na_rzecz_osob_fizycznych")
					&& !data.get("instytucje.budzet_swiadczenia_na_rzecz_osob_fizycznych").isJsonNull())
				ins.setBudzetSwiadczeniaNaRzeczOsobFizycznych(
						data.get("instytucje.budzet_swiadczenia_na_rzecz_osob_fizycznych").getAsBigDecimal());
			if (data.has("instytucje.kod_pocztowy_id") && !data.get("instytucje.kod_pocztowy_id").isJsonNull())
				ins.setKodPocztowyId(Long.parseLong(data.get("instytucje.kod_pocztowy_id").getAsString()));
			if (data.has("instytucje.liczba_pozycji_budzetowych")
					&& !data.get("instytucje.liczba_pozycji_budzetowych").isJsonNull())
				ins.setLiczbaPozycjiBudzetowych(
						Long.parseLong(data.get("instytucje.liczba_pozycji_budzetowych").getAsString()));
			if (data.has("instytucje.budzet_wydatki_majatkowe")
					&& !data.get("instytucje.budzet_wydatki_majatkowe").isJsonNull())
				ins.setBudzetWydatkiMajatkowe(data.get("instytucje.budzet_wydatki_majatkowe").getAsBigDecimal());
			if (data.has("instytucje.email") && !data.get("instytucje.email").isJsonNull())
				ins.setEmail(data.get("instytucje.email").getAsString());
			if (data.has("instytucje.kod_pocztowy_str") && !data.get("instytucje.kod_pocztowy_str").isJsonNull())
				ins.setKodPocztowy(data.get("instytucje.kod_pocztowy_str").getAsString());
			if (data.has("instytucje.budzet_srodki_wlasne_ue")
					&& !data.get("instytucje.budzet_srodki_wlasne_ue").isJsonNull())
				ins.setBudzetSrodkiWlasneUE(data.get("instytucje.budzet_srodki_wlasne_ue").getAsBigDecimal());
			this.insertDataEntity(ins);
		}
	}

	public String getContent(String url) {
		WebClient client = new WebClient();
		client.getOptions().setTimeout(15000);
		// client.setHTMLParserListener(HTMLParserListener.LOG_REPORTER);
		try {
			do {
				logger.info("WYKONANIE getPage(" + url + ")");
				Page page = client.getPage(url);
				this.setStatusCode(page.getWebResponse().getStatusCode());
				if (this.getStatusCode() == 200) {
					return client.getPage(url).getWebResponse().getContentAsString();
				}
				logger.warn("PONOWNE POBRANIE STRONY url=" + this.getUrlToScrape());
			} while (this.getStatusCode() != 200);
			return null;
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
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
