package crawler.mojepanstwo_pl.gminy;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
 
import org.apache.commons.io.IOUtils;
 
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class GminaStart {

	public static void main(String[] args) throws MalformedURLException, IOException {

        JsonReader jsonReader = new JsonReader(new FileReader("D:/gminy.json"));
        System.out.println(jsonReader);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonReader);        
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            JsonArray datasets = object.getAsJsonArray("Dataobject");
            showType(object);
            for(int i=0;i<datasets.size();i++){
            	Gmina gmina = new Gmina();
            	JsonObject gm = datasets.get(i).getAsJsonObject();
            	gmina.setId(Long.parseLong(gm.get("id").getAsString()));
            	gmina.setGlobalId(Long.parseLong(gm.get("global_id").getAsString()));
            	JsonObject data = gm.getAsJsonObject("data");;
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
            	
            	System.out.println(gmina.toString());
            }
        }
        else{
        	System.out.println("nie jest elementem JSON");
        }

	}
	public static void showType(JsonObject o){
		if(o.isJsonArray())System.out.println("!array");
		if(o.isJsonNull())System.out.println("!null");
		if(o.isJsonObject())System.out.println("!object");
		if(o.isJsonPrimitive())System.out.println("!primitive");
		System.out.println(o.getClass().getName());
		
	}

}
