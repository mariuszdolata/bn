package crawler.mojepanstwo_pl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import crawler.api.Scrape;
import crawler.api.ScrapeClass;

public class MojePanstwoKRSPodmiotGet extends ScrapeClass implements Scrape{

	private String daneKrs;
	
	public String getDaneKrs() {
		return daneKrs;
	}
	public void setDaneKrs(String daneKrs) {
		this.daneKrs = daneKrs;
	}
	public MojePanstwoKRSPodmiotGet(int threadId, Properties properties, EntityManagerFactory entityManagerFactory, String urlToScrape) {
		super(threadId, properties, entityManagerFactory);
		this.setUrlToScrape(urlToScrape);
		//pobranie strony www
		this.daneKrs=this.getContent(urlToScrape);
		logger.info(this.daneKrs);
		this.parsing();
	}
	public String getContent(String url){
		WebClient client = new WebClient();
//		client.setHTMLParserListener(HTMLParserListener.LOG_REPORTER);
		try {
			logger.info("WYKONANIE getPage("+url+")");
			return client.getPage(url).getWebResponse().getContentAsString();
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
	public Object parsing(){
		KRSPodmiot podmiot = new KRSPodmiot();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(this.daneKrs);
		if(element.isJsonObject()) logger.info("element - jsonObject!");
		//Obiekty z ktorych beda parsowane dane
		JsonObject jsonObject = element.getAsJsonObject();
		JsonObject data = jsonObject.getAsJsonObject("data");
		JsonObject layers = jsonObject.getAsJsonObject("layers");
		JsonArray dzialalnosci = layers.getAsJsonArray("dzialalnosci");
		
		
		
		podmiot.setNazwaOrganuReprezentacji(data.get("krs_podmioty.nazwa_organu_reprezentacji").getAsString());
//		podmiot.setDotacjeUEBeneficjentId(data.get("krs_podmioty.dotacje_ue_beneficjent_id").getAsInt());
		podmiot.setOznaczenieSadu(data.get("krs_podmioty.oznaczenie_sadu").getAsString());
		podmiot.setRegon(data.get("krs_podmioty.regon").getAsString());
		podmiot.setAdresKodPocztowy(data.get("krs_podmioty.adres_kod_pocztowy").getAsString());
//		podmiot.setOop(data.get("krs_podmioty.oop").getAsBoolean());
		podmiot.setFormaPrawnaStr(data.get("krs_podmioty.forma_prawna_str").getAsString());
		podmiot.setSiedziba(data.get("krs_podmioty.siedziba").getAsString());
		podmiot.setGpwSpolkaId(Long.parseLong(data.get("krs_podmioty.gpw_spolka_id").getAsString()));
		podmiot.setNumerWpisu(Long.parseLong(data.get("krs_podmioty.numer_wpisu").getAsString()));
		podmiot.setAdresUlica(data.get("krs_podmioty.adres_ulica").getAsString());
		podmiot.setFormaPrawnaTypId(Long.parseLong(data.get("krs_podmioty.forma_prawna_typ_id").getAsString()));
		podmiot.setDataOstatniWpis(Date.valueOf(data.get("krs_podmioty.data_ostatni_wpis").getAsString()));
		podmiot.setAdresLokal(data.get("krs_podmioty.adres_lokal").getAsString());
//		podmiot.setRejestr(data.get("krs_podmioty.rejestr").getAsInt());
		podmiot.setFormaPrawnaId(Long.parseLong(data.get("krs_podmioty.forma_prawna_id").getAsString()));
		podmiot.setFirma(data.get("krs_podmioty.firma").getAsString());
		podmiot.setKnfOstrzezenieId(Long.parseLong(data.get("krs_podmioty.knf_ostrzezenie_id").getAsString()));
		podmiot.setWartoscNominalnaPodwyzszeniaKapitalu(data.get("krs_podmioty.wartosc_nominalna_podwyzszenia_kapitalu").getAsBigDecimal());
		podmiot.setAdresPoczta(data.get("krs_podmioty.adres_poczta").getAsString());
		podmiot.setWartoscNominalnaAkcji(data.get("krs_podmioty.wartosc_nominalna_akcji").getAsBigDecimal());
		podmiot.setDataDokonaniaWpisu(Date.valueOf(data.get("krs_podmioty.data_dokonania_wpisu").getAsString()));
		podmiot.setSygnaturaAkt(data.get("krs_podmioty.sygnatura_akt").getAsString());
		podmiot.setDataRejestracji(Date.valueOf(data.get("krs_podmioty.data_rejestracji").getAsString()));
		podmiot.setAdresNumer(data.get("krs_podmioty.adres_numer").getAsString());
		podmiot.setUmowaSpolkiCywilnej(data.get("krs_podmioty.umowa_spolki_cywilnej").getAsString());
		podmiot.setNazwaOrganuNadzoru(data.get("krs_podmioty.nazwa_organu_nadzoru").getAsString());
		podmiot.setWykreslony(data.get("krs_podmioty.wykreslony").getAsBoolean());
		podmiot.setWartoscCzescKapitaluWplaconego(data.get("krs_podmioty.wartosc_czesc_kapitalu_wplaconego").getAsBigDecimal());
		podmiot.setEmail(data.get("krs_podmioty.email").getAsString());
		podmiot.setKrs(data.get("krs_podmioty.krs").getAsString());
		podmiot.setLiczbaAkcjiWszystkichEmisji(data.get("krs_podmioty.liczba_akcji_wszystkich_emisji").getAsBigDecimal());
//		podmiot.setDataSprawdzenia(Date.valueOf(data.get("krs_podmioty.data_sprawdzenia").getAsString()));
		podmiot.setSposobReprezentacji(data.get("krs_podmioty.sposob_reprezentacji").getAsString());
		podmiot.setWczesniejszaRejestracjaStr(data.get("krs_podmioty.wczesniejsza_rejestracja_str").getAsString());
		podmiot.setWebsite(data.get("krs_podmioty.www").getAsString());
		podmiot.setOstatniWpisId(Long.parseLong(data.get("krs_podmioty.ostatni_wpis_id").getAsString()));
		podmiot.setWartoscKapitalDocelowy(data.get("krs_podmioty.wartosc_kapital_docelowy").getAsBigDecimal());
		podmiot.setCelDzialania(data.get("krs_podmioty.cel_dzialania").getAsString());
		podmiot.setAdresMiejscowosc(data.get("krs_podmioty.adres_miejscowosc").getAsString());
		podmiot.setNazwa(data.get("krs_podmioty.nazwa").getAsString());
		podmiot.setAdresKraj(data.get("krs_podmioty.adres_kraj").getAsString());
		podmiot.setGminaId(Long.parseLong(data.get("krs_podmioty.gmina_id").getAsString()));
		podmiot.setMiejscowoscId(Long.parseLong(data.get("krs_podmioty.miejscowosc_id").getAsString()));
		podmiot.setAdres(data.get("krs_podmioty.adres").getAsString());
		podmiot.setRejestrStowarzyszen(data.get("krs_podmioty.rejestr_stowarzyszen").getAsString());
		podmiot.setPowiatId(Long.parseLong(data.get("krs_podmioty.powiat_id").getAsString()));
		podmiot.setWartoscKapitalZakladowy(data.get("krs_podmioty.wartosc_kapital_zakladowy").getAsBigDecimal());
		podmiot.setKodPocztowyId(Long.parseLong(data.get("krs_podmioty.kod_pocztowy_id").getAsString()));
		podmiot.setWojewodztwoId(Long.parseLong(data.get("krs_podmioty.wojewodztwo_id").getAsString()));
		podmiot.setNieprzedsiebiorca(data.get("krs_podmioty.nieprzedsiebiorca").getAsString());
		podmiot.setNazwaSkrocona(data.get("krs_podmioty.nazwa_skrocona").getAsString());
		podmiot.setNip(data.get("krs_podmioty.nip").getAsString());
		podmiot.setGpw(data.get("gpw").getAsBoolean());
		
		for(int i=0;i<dzialalnosci.size();i++){
			PKD pkd = new PKD();
			JsonObject pkdObject = dzialalnosci.get(i).getAsJsonObject();
			String[] dane = pkdObject.get("str").getAsString().split(",");
			pkd.setKod(dane[0].replaceAll(" ", "")+"."+dane[1].replaceAll(" ", "")+"."+dane[2].replaceAll(" ", ""));
			pkd.setNazwa(dane[3]);
			pkd.setPrzewazajaca(pkdObject.get("przewazajaca").getAsBoolean());
			podmiot.getDzialalnosci().add(pkd);
//			for(int j=0;j<dane.length;j++) logger.info(">"+dane[j]+"<");
		}

		try{
			if(MojePanstwoKRSPodmiotGet.this.getEntityManagerFactory() !=null){
				logger.info("EntityManagerFactory dzia³a");
				EntityManager entityManager = MojePanstwoKRSPodmiotGet.this.getEntityManagerFactory().createEntityManager();
				entityManager.getTransaction().begin();
				entityManager.persist(podmiot);
				entityManager.getTransaction().commit();
				entityManager.close();
			}
		}catch(Exception e){
			logger.error(e.getCause());
			logger.error(e.getMessage());
			logger.error(e.getClass().getName());
		}
		logger.info(podmiot.toString());
		
		
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
