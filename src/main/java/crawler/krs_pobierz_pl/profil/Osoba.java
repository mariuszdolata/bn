package crawler.krs_pobierz_pl.profil;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="krs_osoba")
public class Osoba {
	
	private long id;

	private String imie;
	private String nazwisko;
	private String stanowisko;
	private Date data;
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(columnDefinition="mediumtext")
	public String getImie() {
		return imie;
	}
	public void setImie(String imie) {
		this.imie = imie;
	}
	@Column(columnDefinition="mediumtext")
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	@Column(columnDefinition="mediumtext")
	public String getStanowisko() {
		return stanowisko;
	}
	public void setStanowisko(String stanowisko) {
		this.stanowisko = stanowisko;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Osoba(String imie, String nazwisko, String stanowisko) {
		super();
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.stanowisko = stanowisko;
	}
	public Osoba() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	

}
