package crawler.mojepanstwo_pl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;
import crawler.mojepanstwo_pl.Relationship.RelationshipTyp;

public class MojePanstwoKRSPodmiotGet extends ScrapeClass implements Scrape {

	private String daneKrs;

	public String getDaneKrs() {
		return daneKrs;
	}

	public void setDaneKrs(String daneKrs) {
		this.daneKrs = daneKrs;
	}

	public MojePanstwoKRSPodmiotGet(int threadId, Properties properties, EntityManagerFactory entityManagerFactory,
			String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		// pobranie strony www
		this.daneKrs = this.getContent(urlToScrape);
//		logger.info(this.getDaneKrs());
		// logger.info(this.daneKrs);
		if (this.getStatusCode() == 200)
			this.parsing();
		else {
			logger.info("status code = " + this.getStatusCode() + " dla url=" + this.getUrlToScrape());
		}

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
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<String> fetchUrlsToScrape() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object parsing() {
		KRSPodmiot podmiot = new KRSPodmiot();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(this.daneKrs);
		if (element.isJsonObject())
			logger.info("element - jsonObject!");
		// Obiekty z ktorych beda parsowane dane
		JsonObject jsonObject = element.getAsJsonObject();
		JsonObject data = jsonObject.getAsJsonObject("data");
		JsonObject layers = jsonObject.getAsJsonObject("layers");
		JsonObject graph = layers.getAsJsonObject("graph");
		JsonArray dzialalnosci = layers.getAsJsonArray("dzialalnosci");
		JsonArray emisje_akcji = layers.getAsJsonArray("emisje_akcji");
		JsonArray firmy = layers.getAsJsonArray("firmy");
		JsonArray relationships = graph.getAsJsonArray("relationships");
		JsonArray jedynyAkcjonariusz = layers.getAsJsonArray("jedynyAkcjonariusz");
		JsonArray komitetZalozycielski = layers.getAsJsonArray("komitetZalozycielski");
		JsonArray reprezentacja = layers.getAsJsonArray("reprezentacja");
		JsonArray nadzor = layers.getAsJsonArray("nadzor");
		JsonArray oddzialy = layers.getAsJsonArray("oddzialy");
		JsonArray prokurenci = layers.getAsJsonArray("prokurenci");
		JsonArray wspolnicy = layers.getAsJsonArray("wspolnicy");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(element);
//		logger.info(prettyJson);

		// Parsowanie KRSPodmiot
		if (data.has("krs_podmioty.nazwa_organu_reprezentacji"))
			podmiot.setNazwaOrganuReprezentacji(data.get("krs_podmioty.nazwa_organu_reprezentacji").getAsString());
		if (data.has("krs_podmioty.dotacje_ue_beneficjent_id"))
			podmiot.setDotacjeUEBeneficjentId(data.get("krs_podmioty.dotacje_ue_beneficjent_id").getAsInt());
		if (data.has("krs_podmioty.oznaczenie_sadu"))
			podmiot.setOznaczenieSadu(data.get("krs_podmioty.oznaczenie_sadu").getAsString());
		if (data.has("krs_podmioty.regon"))
			podmiot.setRegon(data.get("krs_podmioty.regon").getAsString());
		if (data.has("krs_podmioty.adres_kod_pocztowy"))
			podmiot.setAdresKodPocztowy(data.get("krs_podmioty.adres_kod_pocztowy").getAsString());
		if (data.has("krs_podmioty.opp"))
			podmiot.setOop(data.get("krs_podmioty.opp").getAsString());
		if (data.has("krs_podmioty.forma_prawna_str"))
			podmiot.setFormaPrawnaStr(data.get("krs_podmioty.forma_prawna_str").getAsString());
		if (data.has("krs_podmioty.siedziba"))
			podmiot.setSiedziba(data.get("krs_podmioty.siedziba").getAsString());
		if (data.has("krs_podmioty.gpw_spolka_id"))
			podmiot.setGpwSpolkaId(Long.parseLong(data.get("krs_podmioty.gpw_spolka_id").getAsString()));
		if (data.has("krs_podmioty.numer_wpisu"))
			podmiot.setNumerWpisu(Long.parseLong(data.get("krs_podmioty.numer_wpisu").getAsString()));
		if (data.has("krs_podmioty.adres_ulica"))
			podmiot.setAdresUlica(data.get("krs_podmioty.adres_ulica").getAsString());
		if (data.has("krs_podmioty.forma_prawna_typ_id"))
			podmiot.setFormaPrawnaTypId(Long.parseLong(data.get("krs_podmioty.forma_prawna_typ_id").getAsString()));
		if (data.has("krs_podmioty.data_ostatni_wpis"))
			podmiot.setDataOstatniWpis(Date.valueOf(data.get("krs_podmioty.data_ostatni_wpis").getAsString()));
		if (data.has("krs_podmioty.adres_lokal"))
			podmiot.setAdresLokal(data.get("krs_podmioty.adres_lokal").getAsString());
		if (data.has("krs_podmioty.rejestr"))
			podmiot.setRejestr(data.get("krs_podmioty.rejestr").getAsInt());
		if (data.has("krs_podmioty.forma_prawna_id"))
			podmiot.setFormaPrawnaId(Long.parseLong(data.get("krs_podmioty.forma_prawna_id").getAsString()));
		if (data.has("krs_podmioty.firma"))
			podmiot.setFirma(data.get("krs_podmioty.firma").getAsString());
		if (data.has("krs_podmioty.knf_ostrzezenie_id"))
			podmiot.setKnfOstrzezenieId(Long.parseLong(data.get("krs_podmioty.knf_ostrzezenie_id").getAsString()));
		if (data.has("krs_podmioty.wartosc_nominalna_podwyzszenia_kapitalu"))
			podmiot.setWartoscNominalnaPodwyzszeniaKapitalu(
					data.get("krs_podmioty.wartosc_nominalna_podwyzszenia_kapitalu").getAsBigDecimal());
		if (data.has("krs_podmioty.adres_poczta"))
			podmiot.setAdresPoczta(data.get("krs_podmioty.adres_poczta").getAsString());
		if (data.has("krs_podmioty.wartosc_nominalna_akcji"))
			podmiot.setWartoscNominalnaAkcji(data.get("krs_podmioty.wartosc_nominalna_akcji").getAsBigDecimal());
		if (data.has("krs_podmioty.data_dokonania_wpisu"))
			podmiot.setDataDokonaniaWpisu(Date.valueOf(data.get("krs_podmioty.data_dokonania_wpisu").getAsString()));
		if (data.has("krs_podmioty.sygnatura_akt"))
			podmiot.setSygnaturaAkt(data.get("krs_podmioty.sygnatura_akt").getAsString());
		if (data.has("krs_podmioty.data_rejestracji"))
			podmiot.setDataRejestracji(Date.valueOf(data.get("krs_podmioty.data_rejestracji").getAsString()));
		if (data.has("krs_podmioty.adres_numer"))
			podmiot.setAdresNumer(data.get("krs_podmioty.adres_numer").getAsString());
		if (data.has("krs_podmioty.umowa_spolki_cywilnej"))
			podmiot.setUmowaSpolkiCywilnej(data.get("krs_podmioty.umowa_spolki_cywilnej").getAsString());
		if (data.has("krs_podmioty.nazwa_organu_nadzoru"))
			podmiot.setNazwaOrganuNadzoru(data.get("krs_podmioty.nazwa_organu_nadzoru").getAsString());
		if (data.has("krs_podmioty.wykreslony"))
			podmiot.setWykreslony(data.get("krs_podmioty.wykreslony").getAsBoolean());
		if (data.has("krs_podmioty.wartosc_czesc_kapitalu_wplaconego"))
			podmiot.setWartoscCzescKapitaluWplaconego(
					data.get("krs_podmioty.wartosc_czesc_kapitalu_wplaconego").getAsBigDecimal());
		if (data.has("krs_podmioty.email"))
			podmiot.setEmail(data.get("krs_podmioty.email").getAsString());
		if (data.has("krs_podmioty.krs"))
			podmiot.setKrs(data.get("krs_podmioty.krs").getAsString());
		if (data.has("krs_podmioty.liczba_akcji_wszystkich_emisji"))
			podmiot.setLiczbaAkcjiWszystkichEmisji(
					data.get("krs_podmioty.liczba_akcji_wszystkich_emisji").getAsBigDecimal());
		// if(data.has("krs_podmioty.data_sprawdzenia"))podmiot.setDataSprawdzenia(Date.valueOf(data.get("krs_podmioty.data_sprawdzenia").getAsString()));
		if (data.has("krs_podmioty.sposob_reprezentacji"))
			podmiot.setSposobReprezentacji(data.get("krs_podmioty.sposob_reprezentacji").getAsString());
		if (data.has("krs_podmioty.wczesniejsza_rejestracja_str"))
			podmiot.setWczesniejszaRejestracjaStr(data.get("krs_podmioty.wczesniejsza_rejestracja_str").getAsString());
		if (data.has("krs_podmioty.www"))
			podmiot.setWebsite(data.get("krs_podmioty.www").getAsString());
		if (data.has("krs_podmioty.ostatni_wpis_id"))
			podmiot.setOstatniWpisId(Long.parseLong(data.get("krs_podmioty.ostatni_wpis_id").getAsString()));
		if (data.has("krs_podmioty.wartosc_kapital_docelowy"))
			podmiot.setWartoscKapitalDocelowy(data.get("krs_podmioty.wartosc_kapital_docelowy").getAsBigDecimal());
		if (data.has("krs_podmioty.cel_dzialania"))
			podmiot.setCelDzialania(data.get("krs_podmioty.cel_dzialania").getAsString());
		if (data.has("krs_podmioty.adres_miejscowosc"))
			podmiot.setAdresMiejscowosc(data.get("krs_podmioty.adres_miejscowosc").getAsString());
		if (data.has("krs_podmioty.nazwa"))
			podmiot.setNazwa(data.get("krs_podmioty.nazwa").getAsString());
		if (data.has("krs_podmioty.adres_kraj"))
			podmiot.setAdresKraj(data.get("krs_podmioty.adres_kraj").getAsString());
		if (data.has("krs_podmioty.gmina_id"))
			podmiot.setGminaId(Long.parseLong(data.get("krs_podmioty.gmina_id").getAsString()));
		if (data.has("krs_podmioty.miejscowosc_id"))
			podmiot.setMiejscowoscId(Long.parseLong(data.get("krs_podmioty.miejscowosc_id").getAsString()));
		if (data.has("krs_podmioty.adres"))
			podmiot.setAdres(data.get("krs_podmioty.adres").getAsString());
		if (data.has("krs_podmioty.rejestr_stowarzyszen"))
			podmiot.setRejestrStowarzyszen(data.get("krs_podmioty.rejestr_stowarzyszen").getAsString());
		if (data.has("krs_podmioty.powiat_id"))
			podmiot.setPowiatId(Long.parseLong(data.get("krs_podmioty.powiat_id").getAsString()));
		if (data.has("krs_podmioty.wartosc_kapital_zakladowy"))
			podmiot.setWartoscKapitalZakladowy(data.get("krs_podmioty.wartosc_kapital_zakladowy").getAsBigDecimal());
		if (data.has("krs_podmioty.kod_pocztowy_id"))
			podmiot.setKodPocztowyId(Long.parseLong(data.get("krs_podmioty.kod_pocztowy_id").getAsString()));
		if (data.has("krs_podmioty.wojewodztwo_id"))
			podmiot.setWojewodztwoId(Long.parseLong(data.get("krs_podmioty.wojewodztwo_id").getAsString()));
		if (data.has("krs_podmioty.nieprzedsiebiorca"))
			podmiot.setNieprzedsiebiorca(data.get("krs_podmioty.nieprzedsiebiorca").getAsString());
		if (data.has("krs_podmioty.nazwa_skrocona"))
			podmiot.setNazwaSkrocona(data.get("krs_podmioty.nazwa_skrocona").getAsString());
		if (data.has("krs_podmioty.nip"))
			podmiot.setNip(data.get("krs_podmioty.nip").getAsString());
		if (data.has("gpw"))
			podmiot.setGpw(data.get("gpw").getAsBoolean());

		// Parsowanie PKD
		for (int i = 0; i < dzialalnosci.size(); i++) {
			PKD pkd = new PKD();
			JsonObject pkdObject = dzialalnosci.get(i).getAsJsonObject();
			try {
				if (pkdObject.has("str")) {

					String[] dane = pkdObject.get("str").getAsString().split(",");
					pkd.setKod(dane[0].replaceAll(" ", "") + "." + dane[1].replaceAll(" ", "") + "."
							+ dane[2].replaceAll(" ", ""));
					pkd.setNazwa(dane[3]);
					if (pkdObject.has("przewazajaca"))
						pkd.setPrzewazajaca(pkdObject.get("przewazajaca").getAsBoolean());
					podmiot.getDzialalnosci().add(pkd);
				}
			} catch (Exception e) {

			}
		}

		// Parsowanie EmisjaAkcji
		for (int i = 0; i < emisje_akcji.size(); i++) {
			EmisjaAkcji ea = new EmisjaAkcji();
			JsonObject eaJ = emisje_akcji.get(i).getAsJsonObject();
			if (eaJ.has("seria"))
				ea.setSeria(eaJ.get("seria").getAsString());
			if (eaJ.has("liczba"))
				ea.setLiczba(Long.parseLong(eaJ.get("liczba").getAsString()));
			if (eaJ.has("rodzaj_uprzywilejowania"))
				ea.setRodzajUprzywilejowania(eaJ.get("rodzaj_uprzywilejowania").getAsString());
			podmiot.getEmisjeAkcji().add(ea);
		}

		// Parsowanie Firma
		for (int i = 0; i < firmy.size(); i++) {
			Firma f = new Firma();
			JsonObject firma = firmy.get(i).getAsJsonObject();
			if (firma.has("id")) {
				f.setKrs(Strings.padStart(firma.get("id").getAsString(), 10, '0'));
			}
			if (firma.has("nazwa"))
				f.setNazwa(firma.get("nazwa").getAsString());
			if (firma.has("udzialy_str"))
				f.setUdzialyStr(firma.get("udzialy_str").getAsString());
			if (firma.has("udzialy_liczba"))
				f.setUdzialyLiczba(firma.get("udzialy_liczba").getAsInt());
			if (firma.has("udzialy_wartosc_jedn"))
				f.setUdzialyWartoscJedn(firma.get("udzialy_wartosc_jedn").getAsBigDecimal());
			if (firma.has("udzialy_wartosc"))
				f.setUdzialyWartosc(firma.get("udzialy_wartosc").getAsBigDecimal());
			if (firma.has("udzialy_status"))
				f.setUdzialyStatus(firma.get("udzialy_status").getAsInt());
			podmiot.getFirmy().add(f);
		}


		if (relationships != null) {
			logger.error("relationship != NULL");
			// Parsowanie Relationship
			for (int i = 0; i < relationships.size(); i++) {
				Relationship r = new Relationship();
				JsonObject rJ = relationships.get(i).getAsJsonObject();
				if (rJ.has("type"))
					r.setType(rJ.get("type").getAsString());
				if (rJ.has("start")) {
					String start = rJ.get("start").getAsString();
					if (start.contains("osoba"))
						r.setStartTyp(Relationship.RelationshipTyp.OSOBA);
					else if (start.contains("podmiot"))
						r.setStartTyp(RelationshipTyp.PODMIOT);
					start=start.replaceAll("osoba", "").replaceAll("podmiot", "");
					r.setStartId(Long.parseLong(start));
				}
				if (rJ.has("end")) {
					String end = rJ.get("end").getAsString();
					if (end.contains("osoba"))
						r.setEndTyp(RelationshipTyp.OSOBA);
					else if (end.contains("podmiot"))
						r.setEndTyp(RelationshipTyp.PODMIOT);
					end=end.replaceAll("osoba", "").replaceAll("podmiot", "");
					r.setEndId(Long.parseLong(end));
				}
				podmiot.getRelationships().add(r);
			}
		}
		else logger.info("relationships == NULL");

		//Parsowanie Reprezentacja
		for(int i=0;i<reprezentacja.size();i++){
			JsonObject rJ = reprezentacja.get(i).getAsJsonObject();
			Reprezentacja r = new Reprezentacja();
			if(rJ.has("nazwa"))r.setNazwa(rJ.get("nazwa").getAsString());
			if(rJ.has("funkcja"))r.setFunkcja(rJ.get("funkcja").getAsString());
			if(rJ.has("data_urodzenia"))r.setDataUrodzenia(Date.valueOf(rJ.get("data_urodzenia").getAsString()));
			if(rJ.has("privacy_level"))r.setPrivacyLevel(rJ.get("privacy_level").getAsInt());
			if(rJ.has("osoba_id"))r.setOsobaId(Long.parseLong(rJ.get("osoba_id").getAsString()));
			podmiot.getReprezentacja().add(r);
		}
		//Parsowanie Nadzor
		for(int i=0;i<nadzor.size();i++){
			JsonObject nJ = nadzor.get(i).getAsJsonObject();
			Osoba o = new Osoba();
			if(nJ.has("nazwa"))o.setNazwa(nJ.get("nazwa").getAsString());
			if(nJ.has("data_urodzenia"))o.setDataUrodzenia(Date.valueOf(nJ.get("data_urodzenia").getAsString()));
			if(nJ.has("privacy_level"))o.setPrivacyLevel(nJ.get("privacy_level").getAsInt());
			if(nJ.has("osoba_id"))o.setOsobaId(Long.parseLong(nJ.get("osoba_id").getAsString()));
			podmiot.getNadzor().add(o);
		}
		
		//Parsowanie Oddzialy
		for(int i=0;i<oddzialy.size();i++){
			JsonObject oJ = oddzialy.get(i).getAsJsonObject();
			Oddzial o = new Oddzial();
			if(oJ.has("nazwa"))o.setNazwa(oJ.get("nazwa").getAsString());
			if(oJ.has("adres"))o.setAdres(oJ.get("adres").getAsString());
			podmiot.getOddzial().add(o);
		}
		
		//Parsowanie KomitetZalozycielski
		for(int i=0;i<komitetZalozycielski.size();i++){
			JsonObject kJ = komitetZalozycielski.get(i).getAsJsonObject();
			Osoba o = new Osoba();
			if(kJ.has("nazwa"))o.setNazwa(kJ.get("nazwa").getAsString());
			if(kJ.has("data_urodzenia"))o.setDataUrodzenia(Date.valueOf(kJ.get("data_urodzenia").getAsString()));
			if(kJ.has("privacy_level"))o.setPrivacyLevel(kJ.get("privacy_level").getAsInt());
			if(kJ.has("osoba_id"))o.setOsobaId(Long.parseLong(kJ.get("osoba_id").getAsString()));
			podmiot.getKomitetZalozycielski().add(o);
		}
		
		
		try {
			if (MojePanstwoKRSPodmiotGet.this.getEntityManagerFactory() != null) {
				logger.info("EntityManagerFactory dzia³a");
				EntityManager entityManager = MojePanstwoKRSPodmiotGet.this.getEntityManagerFactory()
						.createEntityManager();
				entityManager.getTransaction().begin();
				entityManager.persist(podmiot);
				entityManager.getTransaction().commit();
				entityManager.close();
			}
		} catch (Exception e) {
			logger.error(e.getCause());
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
		}

		return null;
	}

	public Object parsing(HtmlPage page, Object mainProfil) {
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
