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

public class MojePanstwoZamowieniaPubliczneGet extends ScrapeClass implements Scrape {

	private String daneMojePanstwo = "";

	public String getDaneMojePanstwo() {
		return daneMojePanstwo;
	}

	public void setDaneMojePanstwo(String daneMojePanstwo) {
		this.daneMojePanstwo = daneMojePanstwo;
	}

	public MojePanstwoZamowieniaPubliczneGet(int threadId, Properties properties,
			EntityManagerFactory entityManagerFactory, String urlToScrape) {
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

	public void parsing() {
		ZamowieniaPubliczne zamowienie = new ZamowieniaPubliczne();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(this.getDaneMojePanstwo());
		JsonObject object = element.getAsJsonObject();
		JsonArray dataObjectArray = object.getAsJsonArray("DataObject");
		for(int i=0;i<dataObjectArray.size();i++){
			JsonPrimitive data = dataObjectArray.get(i).getAsJsonPrimitive();
			if(data.isJsonPrimitive())logger.info("data jest JSON primitive");
		}
		JsonObject data = object.getAsJsonObject("data");

		if (data.has("zamowienia_publiczne_zamawiajacy.nazwa")
				&& !data.get("zamowienia_publiczne_zamawiajacy.nazwa").isJsonNull())
			zamowienie.setZamawiajacyNazwa(data.get("zamowienia_publiczne_zamawiajacy.nazwa").getAsString());
		if (data.has("zamowienia_publiczne_tryby.id") && !data.get("zamowienia_publiczne_tryby.id").isJsonNull())
			zamowienie.setTrybyId(data.get("zamowienia_publiczne_tryby.id").getAsString());
		if (data.has("zamowienia_publiczne_tryby.nazwa") && !data.get("zamowienia_publiczne_tryby.nazwa").isJsonNull())
			zamowienie.setTrybyNazwa(data.get("zamowienia_publiczne_tryby.nazwa").getAsString());
		if (data.has("zamowienia_publiczne.liczba_miesiecy")
				&& !data.get("zamowienia_publiczne.liczba_miesiecy").isJsonNull())
			zamowienie.setLiczbaMiesiecy(data.get("zamowienia_publiczne.liczba_miesiecy").getAsInt());
		if (data.has("zamowienia_publiczne.wartosc_cena_min")
				&& !data.get("zamowienia_publiczne.wartosc_cena_min").isJsonNull())
			zamowienie.setWartoscCenaMin(data.get("zamowienia_publiczne.wartosc_cena_min").getAsBigDecimal());
		if (data.has("zamowienia_publiczne.data_publikacji_zamowienia")
				&& !data.get("zamowienia_publiczne.data_publikacji_zamowienia").isJsonNull())
			zamowienie.setDataPublikacjiZamowienia(
					Date.valueOf(data.get("zamowienia_publiczne.data_publikacji_zamowienia").getAsString()));
		if (data.has("zamowienia_publiczne.zamawiajacy_ulica")
				&& !data.get("zamowienia_publiczne.zamawiajacy_ulica").isJsonNull())
			zamowienie.setZamawiajacyUlica(data.get("zamowienia_publiczne.zamawiajacy_ulica").getAsString());
		if (data.has("zamowienia_publiczne.zamawiajacy_email")
				&& !data.get("zamowienia_publiczne.zamawiajacy_email").isJsonNull())
			zamowienie.setZamawiajacyEmail(data.get("zamowienia_publiczne.zamawiajacy_email").getAsString());
		if (data.has("zamowienia_publiczne.dyn_www") && !data.get("zamowienia_publiczne.dyn_www").isJsonNull())
			zamowienie.setZamawiajacyDynWebsite(data.get("zamowienia_publiczne.dyn_www").getAsString());
		if (data.has("zamowienia_publiczne.dsz_www") && !data.get("zamowienia_publiczne.dsz_www").isJsonNull())
			zamowienie.setZamawiajacyDszWebsite(data.get("zamowienia_publiczne.dsz_www").getAsString());
		if (data.has("zamowienia_publiczne.zamawiajacy_www")
				&& !data.get("zamowienia_publiczne.zamawiajacy_www").isJsonNull())
			zamowienie.setZamawiajacyWebsite(data.get("zamowienia_publiczne.zamawiajacy_www").getAsString());
		if (data.has("zamowienia_publiczne.wartosc_szacunkowa")
				&& !data.get("zamowienia_publiczne.wartosc_szacunkowa").isJsonNull())
			zamowienie.setWartoscSzacunkowa(data.get("zamowienia_publiczne.wartosc_szacunkowa").getAsBigDecimal());
		if (data.has("zamowienia_publiczne.zamawiajacy_nazwa")
				&& !data.get("zamowienia_publiczne.zamawiajacy_nazwa").isJsonNull())
			zamowienie.setZamawiajacyNazwa(data.get("zamowienia_publiczne.zamawiajacy_nazwa").getAsString());
		if (data.has("zamowienia_publiczne.projekt_ue") && !data.get("zamowienia_publiczne.projekt_ue").isJsonNull())
			zamowienie.setProjektUE(data.get("zamowienia_publiczne.projekt_ue").getAsString());
		if (data.has("zamowienia_publiczne.zamawiajacy_miejscowosc")
				&& !data.get("zamowienia_publiczne.zamawiajacy_miejscowosc").isJsonNull())
			zamowienie
					.setZamawiajacyMiejscowosc(data.get("zamowienia_publiczne.zamawiajacy_miejscowosc").getAsString());
		if (data.has("zamowienia_publiczne.zamawiajacy_rodzaj_inny")
				&& !data.get("zamowienia_publiczne.zamawiajacy_rodzaj_inny").isJsonNull())
			zamowienie.setZamawiajacyRodzajInny(data.get("zamowienia_publiczne.zamawiajacy_rodzaj_inny").getAsString());
		if (data.has("zamowienia_publiczne.wartosc_szacowana")
				&& !data.get("zamowienia_publiczne.wartosc_szacowana").isJsonNull())
			zamowienie.setWartoscSzacowana(data.get("zamowienia_publiczne.wartosc_szacowana").getAsBigDecimal());
		if (data.has("zamowienia_publiczne.zamawiajacy_fax")
				&& !data.get("zamowienia_publiczne.zamawiajacy_fax").isJsonNull())
			zamowienie.setZamawiajacyFax(data.get("zamowienia_publiczne.zamawiajacy_fax").getAsString());
		if (data.has("zamowienia_publiczne.zamowienie_ue")
				&& !data.get("zamowienia_publiczne.zamowienie_ue").isJsonNull())
			zamowienie.setZamowienieUE(data.get("zamowienia_publiczne.zamowienie_ue").getAsString());
		if (data.has("zamowienia_publiczne.data_publikacji")
				&& !data.get("zamowienia_publiczne.data_publikacji").isJsonNull())
			zamowienie.setDataPublikacji(Date.valueOf(data.get("zamowienia_publiczne.data_publikacji").getAsString()));
		if (data.has("zamowienia_publiczne.wartosc_cena_max")
				&& !data.get("zamowienia_publiczne.wartosc_cena_max").isJsonNull())
			zamowienie.setWartoscCenaMax(data.get("zamowienia_publiczne.wartosc_cena_max").getAsBigDecimal());
		if (data.has("zamowienia_publiczne.zamawiajacy_wojewodztwo")
				&& !data.get("zamowienia_publiczne.zamawiajacy_wojewodztwo").isJsonNull())
			zamowienie
					.setZamawiajacyWojewodztwo(data.get("zamowienia_publiczne.zamawiajacy_wojewodztwo").getAsString());
		if (data.has("zamowienia_publiczne.wykonawca_str")
				&& !data.get("zamowienia_publiczne.wykonawca_str").isJsonNull())
			zamowienie.setWykonawcaNazwa(data.get("zamowienia_publiczne.wykonawca_str").getAsString());
		if (data.has("zamowienia_publiczne.zamawiajacy_regon")
				&& !data.get("zamowienia_publiczne.zamawiajacy_regon").isJsonNull())
			zamowienie.setZamawiajacyRegon(data.get("zamowienia_publiczne.zamawiajacy_regon").getAsString());
		if (data.has("zamowienia_publiczne.nazwa") && !data.get("zamowienia_publiczne.nazwa").isJsonNull())
			zamowienie.setNazwa(data.get("zamowienia_publiczne.nazwa").getAsString());
		if (data.has("zamowienia_publiczne.zamawiajacy_kod_poczt")
				&& !data.get("zamowienia_publiczne.zamawiajacy_kod_poczt").isJsonNull())
			zamowienie.setZamawiajacyKodPocztowy(data.get("zamowienia_publiczne.zamawiajacy_kod_poczt").getAsString());
		if (data.has("zamowienia_publiczne.wartosc_cena")
				&& !data.get("zamowienia_publiczne.wartosc_cena").isJsonNull())
			zamowienie.setWartoscCena(data.get("zamowienia_publiczne.wartosc_cena").getAsBigDecimal());
		if (data.has("zamowienia_publiczne.zamawiajacy_nr_domu")
				&& !data.get("zamowienia_publiczne.zamawiajacy_nr_domu").isJsonNull())
			zamowienie.setZamawiajacyNumerDomu(data.get("zamowienia_publiczne.zamawiajacy_nr_domu").getAsString());
		if (data.has("zamowienia_publiczne.gmina_id") && !data.get("zamowienia_publiczne.gmina_id").isJsonNull())
			zamowienie.setGminaId(Long.parseLong(data.get("zamowienia_publiczne.gmina_id").getAsString()));
		if (data.has("zamowienia_publiczne.przedmiot") && !data.get("zamowienia_publiczne.przedmiot").isJsonNull())
			zamowienie.setPrzedmiot(data.get("zamowienia_publiczne.przedmiot").getAsString());
		if (data.has("zamowienia_publiczne.powiat_id") && !data.get("zamowienia_publiczne.powiat_id").isJsonNull())
			zamowienie.setPowiatId(Long.parseLong(data.get("zamowienia_publiczne.powiat_id").getAsString()));
		if (data.has("zamowienia_publiczne.zamawiajacy_tel")
				&& !data.get("zamowienia_publiczne.zamawiajacy_tel").isJsonNull())
			zamowienie.setZamawiajacyTelefon(data.get("zamowienia_publiczne.zamawiajacy_tel").getAsString());
		if (data.has("zamowienia_publiczne.kod_pocztowy_id")
				&& !data.get("zamowienia_publiczne.kod_pocztowy_id").isJsonNull())
			zamowienie.setKodPocztowyId(Long.parseLong(data.get("zamowienia_publiczne.kod_pocztowy_id").getAsString()));
		if (data.has("zamowienia_publiczne.liczba_wykonawcow")
				&& !data.get("zamowienia_publiczne.liczba_wykonawcow").isJsonNull())
			zamowienie.setLiczbaWykonawcow(data.get("zamowienia_publiczne.liczba_wykonawcow").getAsInt());
		if (data.has("zamowienia_publiczne.wojewodztwo_id")
				&& !data.get("zamowienia_publiczne.wojewodztwo_id").isJsonNull())
			zamowienie.setWojewodztwoId(Long.parseLong(data.get("zamowienia_publiczne.wojewodztwo_id").getAsString()));
		if (data.has("zamowienia_publiczne.zaliczka") && !data.get("zamowienia_publiczne.zaliczka").isJsonNull())
			zamowienie.setZaliczka(data.get("zamowienia_publiczne.zaliczka").getAsBigDecimal());
		if (data.has("zamowienia_publiczne.zamawiajacy_id")
				&& !data.get("zamowienia_publiczne.zamawiajacy_id").isJsonNull())
			zamowienie.setZamawiajacyId(Long.parseLong(data.get("zamowienia_publiczne.zamawiajacy_id").getAsString()));
		try{
			this.insertDataEntity(zamowienie);
		}catch(Exception e){
			logger.error("Nie udalo wstawic siê obiektu ZamowieniaPubliczne dla url="+this.urlToScrape);
			logger.error(e.getCause());
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
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
