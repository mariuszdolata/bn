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
		// logger.info(this.getDaneKrs());
		// logger.info(this.daneKrs);
		if (this.getStatusCode() == 200)
			if(this.getDaneKrs()!=null)this.parsing();
			else logger.warn("PUSTE DANE DLA url="+this.getUrlToScrape()+", statusCode="+this.getStatusCode());
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

	public Object parsing() {
		KRSPodmiot podmiot = new KRSPodmiot();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(this.daneKrs);
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
		// logger.info(prettyJson);

		// Parsowanie KRSPodmiot
		if (data.has("krs_podmioty.nazwa_organu_reprezentacji")
				&& !data.get("krs_podmioty.nazwa_organu_reprezentacji").isJsonNull())
			podmiot.setNazwaOrganuReprezentacji(data.get("krs_podmioty.nazwa_organu_reprezentacji").getAsString());
		if (data.has("krs_podmioty.dotacje_ue_beneficjent_id")
				&& !data.get("krs_podmioty.dotacje_ue_beneficjent_id").isJsonNull())
			podmiot.setDotacjeUEBeneficjentId(data.get("krs_podmioty.dotacje_ue_beneficjent_id").getAsInt());
		if (data.has("krs_podmioty.oznaczenie_sadu") && !data.get("krs_podmioty.oznaczenie_sadu").isJsonNull())
			podmiot.setOznaczenieSadu(data.get("krs_podmioty.oznaczenie_sadu").getAsString());
		if (data.has("krs_podmioty.regon") && !data.get("krs_podmioty.regon").isJsonNull())
			podmiot.setRegon(data.get("krs_podmioty.regon").getAsString());
		if (data.has("krs_podmioty.adres_kod_pocztowy") && !data.get("krs_podmioty.adres_kod_pocztowy").isJsonNull())
			podmiot.setAdresKodPocztowy(data.get("krs_podmioty.adres_kod_pocztowy").getAsString());
		if (data.has("krs_podmioty.opp") && !data.get("krs_podmioty.opp").isJsonNull())
			podmiot.setOop(data.get("krs_podmioty.opp").getAsString());
		if (data.has("krs_podmioty.forma_prawna_str") && !data.get("krs_podmioty.forma_prawna_str").isJsonNull())
			podmiot.setFormaPrawnaStr(data.get("krs_podmioty.forma_prawna_str").getAsString());
		if (data.has("krs_podmioty.siedziba") && !data.get("krs_podmioty.siedziba").isJsonNull())
			podmiot.setSiedziba(data.get("krs_podmioty.siedziba").getAsString());
		if (data.has("krs_podmioty.gpw_spolka_id") && !data.get("krs_podmioty.gpw_spolka_id").isJsonNull())
			podmiot.setGpwSpolkaId(Long.parseLong(data.get("krs_podmioty.gpw_spolka_id").getAsString()));
		if (data.has("krs_podmioty.numer_wpisu") && !data.get("krs_podmioty.numer_wpisu").isJsonNull())
			podmiot.setNumerWpisu(Long.parseLong(data.get("krs_podmioty.numer_wpisu").getAsString()));
		if (data.has("krs_podmioty.adres_ulica") && !data.get("krs_podmioty.adres_ulica").isJsonNull())
			podmiot.setAdresUlica(data.get("krs_podmioty.adres_ulica").getAsString());
		if (data.has("krs_podmioty.forma_prawna_typ_id") && !data.get("krs_podmioty.forma_prawna_typ_id").isJsonNull())
			podmiot.setFormaPrawnaTypId(Long.parseLong(data.get("krs_podmioty.forma_prawna_typ_id").getAsString()));
		if (data.has("krs_podmioty.data_ostatni_wpis") && !data.get("krs_podmioty.data_ostatni_wpis").isJsonNull())
			podmiot.setDataOstatniWpis(Date.valueOf(data.get("krs_podmioty.data_ostatni_wpis").getAsString()));
		if (data.has("krs_podmioty.adres_lokal") && !data.get("krs_podmioty.adres_lokal").isJsonNull())
			podmiot.setAdresLokal(data.get("krs_podmioty.adres_lokal").getAsString());
		if (data.has("krs_podmioty.rejestr") && !data.get("krs_podmioty.rejestr").isJsonNull())
			podmiot.setRejestr(data.get("krs_podmioty.rejestr").getAsInt());
		if (data.has("krs_podmioty.forma_prawna_id") && !data.get("krs_podmioty.forma_prawna_id").isJsonNull())
			podmiot.setFormaPrawnaId(Long.parseLong(data.get("krs_podmioty.forma_prawna_id").getAsString()));
		if (data.has("krs_podmioty.firma") && !data.get("krs_podmioty.firma").isJsonNull())
			podmiot.setFirma(data.get("krs_podmioty.firma").getAsString());
		if (data.has("krs_podmioty.knf_ostrzezenie_id") && !data.get("krs_podmioty.knf_ostrzezenie_id").isJsonNull())
			podmiot.setKnfOstrzezenieId(Long.parseLong(data.get("krs_podmioty.knf_ostrzezenie_id").getAsString()));
		if (data.has("krs_podmioty.wartosc_nominalna_podwyzszenia_kapitalu")
				&& !data.get("krs_podmioty.wartosc_nominalna_podwyzszenia_kapitalu").isJsonNull())
			podmiot.setWartoscNominalnaPodwyzszeniaKapitalu(
					data.get("krs_podmioty.wartosc_nominalna_podwyzszenia_kapitalu").getAsBigDecimal());
		if (data.has("krs_podmioty.adres_poczta") && !data.get("krs_podmioty.adres_poczta").isJsonNull())
			podmiot.setAdresPoczta(data.get("krs_podmioty.adres_poczta").getAsString());
		if (data.has("krs_podmioty.wartosc_nominalna_akcji")
				&& !data.get("krs_podmioty.wartosc_nominalna_akcji").isJsonNull())
			podmiot.setWartoscNominalnaAkcji(data.get("krs_podmioty.wartosc_nominalna_akcji").getAsBigDecimal());
		if (data.has("krs_podmioty.data_dokonania_wpisu")
				&& !data.get("krs_podmioty.data_dokonania_wpisu").isJsonNull())
			podmiot.setDataDokonaniaWpisu(Date.valueOf(data.get("krs_podmioty.data_dokonania_wpisu").getAsString()));
		if (data.has("krs_podmioty.sygnatura_akt") && !data.get("krs_podmioty.sygnatura_akt").isJsonNull())
			podmiot.setSygnaturaAkt(data.get("krs_podmioty.sygnatura_akt").getAsString());
		if (data.has("krs_podmioty.data_rejestracji") && !data.get("krs_podmioty.data_rejestracji").isJsonNull())
			podmiot.setDataRejestracji(Date.valueOf(data.get("krs_podmioty.data_rejestracji").getAsString()));
		if (data.has("krs_podmioty.adres_numer") && !data.get("krs_podmioty.adres_numer").isJsonNull())
			podmiot.setAdresNumer(data.get("krs_podmioty.adres_numer").getAsString());
		if (data.has("krs_podmioty.umowa_spolki_cywilnej")
				&& !data.get("krs_podmioty.umowa_spolki_cywilnej").isJsonNull())
			podmiot.setUmowaSpolkiCywilnej(data.get("krs_podmioty.umowa_spolki_cywilnej").getAsString());
		if (data.has("krs_podmioty.nazwa_organu_nadzoru")
				&& !data.get("krs_podmioty.nazwa_organu_nadzoru").isJsonNull())
			podmiot.setNazwaOrganuNadzoru(data.get("krs_podmioty.nazwa_organu_nadzoru").getAsString());
		if (data.has("krs_podmioty.wykreslony") && !data.get("krs_podmioty.wykreslony").isJsonNull())
			podmiot.setWykreslony(data.get("krs_podmioty.wykreslony").getAsBoolean());
		if (data.has("krs_podmioty.wartosc_czesc_kapitalu_wplaconego")
				&& !data.get("krs_podmioty.wartosc_czesc_kapitalu_wplaconego").isJsonNull())
			podmiot.setWartoscCzescKapitaluWplaconego(
					data.get("krs_podmioty.wartosc_czesc_kapitalu_wplaconego").getAsBigDecimal());
		if (data.has("krs_podmioty.email") && !data.get("krs_podmioty.email").isJsonNull())
			podmiot.setEmail(data.get("krs_podmioty.email").getAsString());
		if (data.has("krs_podmioty.krs") && !data.get("krs_podmioty.krs").isJsonNull())
			podmiot.setKrs(data.get("krs_podmioty.krs").getAsString());
		if (data.has("krs_podmioty.liczba_akcji_wszystkich_emisji")
				&& !data.get("krs_podmioty.liczba_akcji_wszystkich_emisji").isJsonNull())
			podmiot.setLiczbaAkcjiWszystkichEmisji(
					data.get("krs_podmioty.liczba_akcji_wszystkich_emisji").getAsBigDecimal());
		// if(data.has("krs_podmioty.data_sprawdzenia"))podmiot.setDataSprawdzenia(Date.valueOf(data.get("krs_podmioty.data_sprawdzenia").getAsString()));
		if (data.has("krs_podmioty.sposob_reprezentacji")
				&& !data.get("krs_podmioty.sposob_reprezentacji").isJsonNull())
			podmiot.setSposobReprezentacji(data.get("krs_podmioty.sposob_reprezentacji").getAsString());
		if (data.has("krs_podmioty.wczesniejsza_rejestracja_str")
				&& !data.get("krs_podmioty.wczesniejsza_rejestracja_str").isJsonNull())
			podmiot.setWczesniejszaRejestracjaStr(data.get("krs_podmioty.wczesniejsza_rejestracja_str").getAsString());
		if (data.has("krs_podmioty.www") && !data.get("krs_podmioty.www").isJsonNull())
			podmiot.setWebsite(data.get("krs_podmioty.www").getAsString());
		if (data.has("krs_podmioty.ostatni_wpis_id") && !data.get("krs_podmioty.ostatni_wpis_id").isJsonNull())
			podmiot.setOstatniWpisId(Long.parseLong(data.get("krs_podmioty.ostatni_wpis_id").getAsString()));
		if (data.has("krs_podmioty.wartosc_kapital_docelowy")
				&& !data.get("krs_podmioty.wartosc_kapital_docelowy").isJsonNull())
			podmiot.setWartoscKapitalDocelowy(data.get("krs_podmioty.wartosc_kapital_docelowy").getAsBigDecimal());
		if (data.has("krs_podmioty.cel_dzialania") && !data.get("krs_podmioty.cel_dzialania").isJsonNull())
			podmiot.setCelDzialania(data.get("krs_podmioty.cel_dzialania").getAsString());
		if (data.has("krs_podmioty.adres_miejscowosc") && !data.get("krs_podmioty.adres_miejscowosc").isJsonNull())
			podmiot.setAdresMiejscowosc(data.get("krs_podmioty.adres_miejscowosc").getAsString());
		if (data.has("krs_podmioty.nazwa") && !data.get("krs_podmioty.nazwa").isJsonNull())
			podmiot.setNazwa(data.get("krs_podmioty.nazwa").getAsString());
		if (data.has("krs_podmioty.adres_kraj") && !data.get("krs_podmioty.adres_kraj").isJsonNull())
			podmiot.setAdresKraj(data.get("krs_podmioty.adres_kraj").getAsString());
		if (data.has("krs_podmioty.gmina_id") && !data.get("krs_podmioty.gmina_id").isJsonNull())
			podmiot.setGminaId(Long.parseLong(data.get("krs_podmioty.gmina_id").getAsString()));
		if (data.has("krs_podmioty.miejscowosc_id") && !data.get("krs_podmioty.miejscowosc_id").isJsonNull())
			podmiot.setMiejscowoscId(Long.parseLong(data.get("krs_podmioty.miejscowosc_id").getAsString()));
		if (data.has("krs_podmioty.adres") && !data.get("krs_podmioty.adres").isJsonNull())
			podmiot.setAdres(data.get("krs_podmioty.adres").getAsString());
		if (data.has("krs_podmioty.rejestr_stowarzyszen")
				&& !data.get("krs_podmioty.rejestr_stowarzyszen").isJsonNull())
			podmiot.setRejestrStowarzyszen(data.get("krs_podmioty.rejestr_stowarzyszen").getAsString());
		if (data.has("krs_podmioty.powiat_id") && !data.get("krs_podmioty.powiat_id").isJsonNull())
			podmiot.setPowiatId(Long.parseLong(data.get("krs_podmioty.powiat_id").getAsString()));
		if (data.has("krs_podmioty.wartosc_kapital_zakladowy")
				&& !data.get("krs_podmioty.wartosc_kapital_zakladowy").isJsonNull())
			podmiot.setWartoscKapitalZakladowy(data.get("krs_podmioty.wartosc_kapital_zakladowy").getAsBigDecimal());
		if (data.has("krs_podmioty.kod_pocztowy_id") && !data.get("krs_podmioty.kod_pocztowy_id").isJsonNull())
			podmiot.setKodPocztowyId(Long.parseLong(data.get("krs_podmioty.kod_pocztowy_id").getAsString()));
		if (data.has("krs_podmioty.wojewodztwo_id") && !data.get("krs_podmioty.wojewodztwo_id").isJsonNull())
			podmiot.setWojewodztwoId(Long.parseLong(data.get("krs_podmioty.wojewodztwo_id").getAsString()));
		if (data.has("krs_podmioty.nieprzedsiebiorca") && !data.get("krs_podmioty.nieprzedsiebiorca").isJsonNull())
			podmiot.setNieprzedsiebiorca(data.get("krs_podmioty.nieprzedsiebiorca").getAsString());
		if (data.has("krs_podmioty.nazwa_skrocona") && !data.get("krs_podmioty.nazwa_skrocona").isJsonNull())
			podmiot.setNazwaSkrocona(data.get("krs_podmioty.nazwa_skrocona").getAsString());
		if (data.has("krs_podmioty.nip") && !data.get("krs_podmioty.nip").isJsonNull())
			podmiot.setNip(data.get("krs_podmioty.nip").getAsString());
		if (data.has("gpw") && !data.get("gpw").isJsonNull())
			podmiot.setGpw(data.get("gpw").getAsBoolean());

		// Parsowanie PKD
		for (int i = 0; i < dzialalnosci.size(); i++) {
			PKD pkd = new PKD();
			JsonObject pkdObject = dzialalnosci.get(i).getAsJsonObject();
			try {
				if (pkdObject.has("str") && !pkdObject.get("str").isJsonNull()) {

					String[] dane = pkdObject.get("str").getAsString().split(",");
					pkd.setKod(dane[0].replaceAll(" ", "") + "." + dane[1].replaceAll(" ", "") + "."
							+ dane[2].replaceAll(" ", ""));
					pkd.setNazwa(dane[3]);
					if (pkdObject.has("przewazajaca") && !pkdObject.get("przewazajaca").isJsonNull())
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
			if (eaJ.has("seria") && !eaJ.get("seria").isJsonNull())
				ea.setSeria(eaJ.get("seria").getAsString());
			if (eaJ.has("liczba") && !eaJ.get("liczba").isJsonNull())
				ea.setLiczba(Long.parseLong(eaJ.get("liczba").getAsString()));
			if (eaJ.has("rodzaj_uprzywilejowania") && !eaJ.get("rodzaj_uprzywilejowania").isJsonNull())
				ea.setRodzajUprzywilejowania(eaJ.get("rodzaj_uprzywilejowania").getAsString());
			podmiot.getEmisjeAkcji().add(ea);
		}

		// Parsowanie Firma
		for (int i = 0; i < firmy.size(); i++) {
			Firma f = new Firma();
			JsonObject firma = firmy.get(i).getAsJsonObject();
			if (firma.has("id") && !firma.get("id").isJsonNull())
				f.setKrs(Strings.padStart(firma.get("id").getAsString(), 10, '0'));
			if (firma.has("nazwa") && !firma.get("nazwa").isJsonNull())
				f.setNazwa(firma.get("nazwa").getAsString());
			if (firma.has("udzialy_str") && !firma.get("udzialy_str").isJsonNull())
				f.setUdzialyStr(firma.get("udzialy_str").getAsString());
			if (firma.has("udzialy_liczba") && !firma.get("udzialy_liczba").isJsonNull())
				f.setUdzialyLiczba(firma.get("udzialy_liczba").getAsInt());
			if (firma.has("udzialy_wartosc_jedn") && !firma.get("udzialy_wartosc_jedn").isJsonNull())
				f.setUdzialyWartoscJedn(firma.get("udzialy_wartosc_jedn").getAsBigDecimal());
			if (firma.has("udzialy_wartosc") && !firma.get("udzialy_wartosc").isJsonNull())
				f.setUdzialyWartosc(firma.get("udzialy_wartosc").getAsBigDecimal());
			if (firma.has("udzialy_status") && !firma.get("udzialy_status").isJsonNull())
				f.setUdzialyStatus(firma.get("udzialy_status").getAsInt());
			podmiot.getFirmy().add(f);
		}

		// Parsowanie Relationship
		for (int i = 0; i < relationships.size(); i++) {
			Relationship r = new Relationship();
			JsonObject rJ = relationships.get(i).getAsJsonObject();
			if (rJ.has("type") && !rJ.get("type").isJsonNull())
				r.setType(rJ.get("type").getAsString());
			if (rJ.has("start") && !rJ.get("start").isJsonNull()) {
				String start = rJ.get("start").getAsString();
				if (start.contains("osoba"))
					r.setStartTyp(Relationship.RelationshipTyp.OSOBA);
				else if (start.contains("podmiot"))
					r.setStartTyp(RelationshipTyp.PODMIOT);
				start = start.replaceAll("osoba", "").replaceAll("podmiot", "");
				r.setStartId(Long.parseLong(start));
			}
			if (rJ.has("end") && !rJ.get("end").isJsonNull()) {
				String end = rJ.get("end").getAsString();
				if (end.contains("osoba"))
					r.setEndTyp(RelationshipTyp.OSOBA);
				else if (end.contains("podmiot"))
					r.setEndTyp(RelationshipTyp.PODMIOT);
				end = end.replaceAll("osoba", "").replaceAll("podmiot", "");
				r.setEndId(Long.parseLong(end));
			}
			podmiot.getRelationships().add(r);
		}
		// Parsowanie Reprezentacja
		for (int i = 0; i < reprezentacja.size(); i++) {
			JsonObject rJ = reprezentacja.get(i).getAsJsonObject();
			Reprezentacja r = new Reprezentacja();
			if (rJ.has("nazwa") && !rJ.get("nazwa").isJsonNull())
				r.setNazwa(rJ.get("nazwa").getAsString());
			if (rJ.has("funkcja") && !rJ.get("funkcja").isJsonNull())
				r.setFunkcja(rJ.get("funkcja").getAsString());
			if (rJ.has("data_urodzenia") && !rJ.get("data_urodzenia").isJsonNull())
				r.setDataUrodzenia(Date.valueOf(rJ.get("data_urodzenia").getAsString()));
			if (rJ.has("privacy_level") && !rJ.get("privacy_level").isJsonNull())
				r.setPrivacyLevel(rJ.get("privacy_level").getAsInt());
			if (rJ.has("osoba_id") && !rJ.get("osoba_id").isJsonNull())
				r.setOsobaId(Long.parseLong(rJ.get("osoba_id").getAsString()));
			podmiot.getReprezentacja().add(r);
		}
		// Parsowanie Nadzor
		for (int i = 0; i < nadzor.size(); i++) {
			JsonObject nJ = nadzor.get(i).getAsJsonObject();
			Osoba o = this.scrapeOsoba(nJ);
			podmiot.getNadzor().add(o);
		}
		// Parsowanie KomitetZalozycielski
		for (int i = 0; i < komitetZalozycielski.size(); i++) {
			JsonObject kJ = komitetZalozycielski.get(i).getAsJsonObject();
			Osoba o = this.scrapeOsoba(kJ);
			podmiot.getKomitetZalozycielski().add(o);
		}
		// Parsowanie JedynyAkcjonariusz
		for (int i = 0; i < jedynyAkcjonariusz.size(); i++) {
			JsonObject jO = jedynyAkcjonariusz.get(i).getAsJsonObject();
			Osoba o = this.scrapeOsoba(jO);
			podmiot.getJedynyAkcjonariusz().add(o);

		}

		// Parsowanie Oddzialy
		for (int i = 0; i < oddzialy.size(); i++) {
			JsonObject oJ = oddzialy.get(i).getAsJsonObject();
			Oddzial o = new Oddzial();
			if (oJ.has("nazwa") && !oJ.get("nazwa").isJsonNull())
				o.setNazwa(oJ.get("nazwa").getAsString());
			if (oJ.has("adres") && !oJ.get("adres").isJsonNull())
				o.setAdres(oJ.get("adres").getAsString());
			podmiot.getOddzial().add(o);
		}

		// Parsowanie Wspolnik
		for (int i = 0; i < wspolnicy.size(); i++) {
			JsonObject wJ = wspolnicy.get(i).getAsJsonObject();
			Wspolnik w = new Wspolnik();
			if (wJ.has("nazwa") && !wJ.get("nazwa").isJsonNull())
				w.setNazwa(wJ.get("nazwa").getAsString());
			if (wJ.has("data_urodzenia") && !wJ.get("data_urodzenia").isJsonNull())
				w.setDataUrodzenia(Date.valueOf(wJ.get("data_urodzenia").getAsString()));
			if (wJ.has("privacy_level") && !wJ.get("privacy_level").isJsonNull())
				w.setPrivacyLevel(wJ.get("privacy_level").getAsInt());
			if (wJ.has("osoba_id") && !wJ.get("osoba_id").isJsonNull())
				w.setOsobaId(Long.parseLong(wJ.get("osoba_id").getAsString()));
			if (wJ.has("krs_id") && !wJ.get("krs_id").isJsonNull())
				w.setKrsId(Long.parseLong(wJ.get("krs_id").getAsString()));
			if (wJ.has("id") && !wJ.get("id").isJsonNull())
				w.setWspolnikId(Long.parseLong(wJ.get("id").getAsString()));
			if (wJ.has("funkcja") && !wJ.get("funkcja").isJsonNull())
				w.setFunkcja(wJ.get("funkcja").getAsString());
			if (wJ.has("udzialy_liczba") && !wJ.get("udzialy_liczba").isJsonNull())
				w.setUdzialyLiczba(wJ.get("udzialy_liczba").getAsInt());
			if (wJ.has("udzialy_wartosc_jedn") && !wJ.get("udzialy_wartosc_jedn").isJsonNull())
				w.setUdzialyWartoscJedn(wJ.get("udzialy_wartosc_jedn").getAsBigDecimal());
			if (wJ.has("udzialy_wartosc") && !wJ.get("udzialy_wartosc").isJsonNull())
				w.setUdzialyWartosc(wJ.get("udzialy_wartosc").getAsBigDecimal());
			podmiot.getWspolnicy().add(w);

		}

		// Parsowanie Prokurenci
		for (int i = 0; i < prokurenci.size(); i++) {
			JsonObject pJ = prokurenci.get(i).getAsJsonObject();
			Reprezentacja p = new Reprezentacja();
			if (pJ.has("nazwa") && !pJ.get("nazwa").isJsonNull())
				p.setNazwa(pJ.get("nazwa").getAsString());
			if (pJ.has("funkcja") && !pJ.get("funkcja").isJsonNull())
				p.setFunkcja(pJ.get("funkcja").getAsString());
			if (pJ.has("data_urodzenia") && !pJ.get("data_urodzenia").isJsonNull())
				p.setDataUrodzenia(Date.valueOf(pJ.get("data_urodzenia").getAsString()));
			if (pJ.has("privacy_level") && !pJ.get("privacy_level").isJsonNull())
				p.setPrivacyLevel(pJ.get("privacy_level").getAsInt());
			if (pJ.has("osoba_id") && !pJ.get("osoba_id").isJsonNull())
				p.setOsobaId(Long.parseLong(pJ.get("osoba_id").getAsString()));
			podmiot.getProkurenci().add(p);

		}

		try {
			if (MojePanstwoKRSPodmiotGet.this.getEntityManagerFactory() != null) {
				// logger.info("EntityManagerFactory dzia³a");
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

	public Osoba scrapeOsoba(JsonObject jO) {
		Osoba o = new Osoba();
		if (jO.has("nazwa") && !jO.get("nazwa").isJsonNull())
			o.setNazwa(jO.get("nazwa").getAsString());
		if (jO.has("data_urodzenia") && !jO.get("data_urodzenia").isJsonNull())
			o.setDataUrodzenia(Date.valueOf(jO.get("data_urodzenia").getAsString()));
		if (jO.has("privacy_level") && !jO.get("privacy_level").isJsonNull())
			o.setPrivacyLevel(jO.get("privacy_level").getAsInt());
		if (jO.has("osoba_id") && !jO.get("osoba_id").isJsonNull())
			o.setOsobaId(Long.parseLong(jO.get("osoba_id").getAsString()));
		return o;
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
