package crawler.mojepanstwo_pl;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import dbstructure.Gmina;
import dbstructure.Powiat;

public class GminaStart {

	public static void main(String[] args) throws MalformedURLException, IOException {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("polska");
		for (int j = 1; j <= 5; j++) {
			
			List<Gmina> gminy = new ArrayList<Gmina>();
			try {
				saveJson("https://api-v3.mojepanstwo.pl/dane/gminy?limit=500&_type=objects&page="+j);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JsonReader jsonReader = new JsonReader(new FileReader("D:/dane.json"));
			System.out.println(jsonReader);
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(jsonReader);
			if (element.isJsonObject()) {
				JsonObject object = element.getAsJsonObject();
				JsonArray datasets = object.getAsJsonArray("Dataobject");
				for (int i = 0; i < datasets.size(); i++) {
					Gmina gmina = new Gmina();
					JsonObject gm = datasets.get(i).getAsJsonObject();
					gmina.setId(Long.parseLong(gm.get("id").getAsString()));
					gmina.setGlobalId(Long.parseLong(gm.get("global_id").getAsString()));
					JsonObject data = gm.getAsJsonObject("data");
					;
					gmina.setIdPowiat(Long.parseLong(data.get("powiaty.id").getAsString()));
					gmina.setIdWojewodztwo(Long.parseLong(data.get("wojewodztwa.id").getAsString()));
					gmina.setTelefon(data.get("gminy.telefon").getAsString());
					gmina.setFax(data.get("gminy.fax").getAsString());
					gmina.setAdres(data.get("gminy.adres").getAsString());
					gmina.setNazwaUrzedu(data.get("gminy.nazwa_urzedu").getAsString());
					gmina.setWydatkiRoczne(data.get("gminy.wydatki_roczne").getAsBigDecimal());
					gmina.setTeryt(data.get("gminy.teryt").getAsString());
					gmina.setWebsite(data.get("gminy.bip_www").getAsString());
					gmina.setLiczbaLudnosci(data.get("gminy.liczba_ludnosci").getAsInt());
					gmina.setTypId(data.get("gminy.typ_id").getAsInt());
					gmina.setEmail(data.get("gminy.email").getAsString());
					gmina.setPowierzchnia(data.get("gminy.powierzchnia").getAsDouble());
					gmina.setNts(data.get("gminy.nts").getAsString());
					gmina.setNazwa(data.get("gminy.nazwa").getAsString());
					gmina.setZadluzenie(data.get("gminy.zadluzenie_roczne").getAsBigDecimal());
					gmina.setDochody(data.get("gminy.dochody_roczne").getAsBigDecimal());
					gmina.setRadaNazwa(data.get("gminy.rada_nazwa").getAsString());
					gminy.add(gmina);
					System.out.println(i + " - " + gmina.toString());
				}
			} else {
				System.out.println("nie jest elementem JSON");
			}
			saveRecord(entityManagerFactory, gminy);
		}

		entityManagerFactory.close();
	}

	public static void saveJson(String urlString) throws MalformedURLException, IOException {
		// String
		// urlString="http://ellagray.co.uk/wp-content/uploads/2016/11/ice-vapor.jpg";

		String filename = "D:\\dane.json";
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			fout = new FileOutputStream(filename);

			final byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (fout != null) {
				fout.close();
			}
		}
	}

	public static void saveRecord(EntityManagerFactory emf, List<Gmina> lista) {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		for (Gmina gmina : lista)
			entityManager.persist(gmina);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
