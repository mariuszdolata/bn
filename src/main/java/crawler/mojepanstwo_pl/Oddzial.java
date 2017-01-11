package crawler.mojepanstwo_pl;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Oddzial {

	@Id
	private long id;
	private String nazwa;
	private String adres;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	@Override
	public String toString() {
		return "Oddzial [id=" + id + ", nazwa=" + nazwa + ", adres=" + adres + "]";
	}
	public Oddzial(long id, String nazwa, String adres) {
		super();
		this.id = id;
		this.nazwa = nazwa;
		this.adres = adres;
	}
	public Oddzial() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
