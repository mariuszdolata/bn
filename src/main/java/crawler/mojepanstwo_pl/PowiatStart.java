package crawler.mojepanstwo_pl;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
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

import dbstructure.Powiat;

public class PowiatStart {

	public static void main(String[] args) throws FileNotFoundException {
		try{
			saveJson("https://api-v3.mojepanstwo.pl/dane/powiaty?limit=400");
		}catch(Exception e){ e.printStackTrace();}
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("polska");
		List<Powiat> powiaty = new ArrayList<Powiat>();
		JsonReader jsonReader = new JsonReader(new FileReader("D:/dane.json"));
		System.out.println(jsonReader);
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonReader);
		if (element.isJsonObject()) {
			JsonObject object = element.getAsJsonObject();
			JsonArray datasets = object.getAsJsonArray("Dataobject");

			for (int i = 0; i < datasets.size(); i++) {
				Powiat powiat = new Powiat();
				JsonObject pow = datasets.get(i).getAsJsonObject();
				powiat.setIdPowiat(Long.parseLong(pow.get("id").getAsString()));
				powiat.setGlobalId(Long.parseLong(pow.get("global_id").getAsString()));

				JsonObject data = pow.getAsJsonObject("data");

				powiat.setTypId(data.get("powiaty.typ_id").getAsInt());
				powiat.setWojewodztwoId(data.get("powiaty.wojewodztwo_id").getAsInt());
				powiat.setNts(data.get("powiaty.nts").getAsString());
				powiat.setNazwa(data.get("powiaty.nazwa").getAsString());
				powiat.setSenatOkregId(data.get("powiaty.senat_okreg_id").getAsInt());
				powiat.setSejmOkregId(data.get("powiaty.sejm_okreg_id").getAsInt());
				powiaty.add(powiat);
				System.out.println(i + " - " + powiat.toString());
			}
		} else {
			System.out.println("nie jest elementem JSON");
		}
		saveRecord(entityManagerFactory, powiaty);
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

	public static void saveRecord(EntityManagerFactory emf, List<Powiat> lista){
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		for(Powiat powiat:lista) entityManager.persist(powiat);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
