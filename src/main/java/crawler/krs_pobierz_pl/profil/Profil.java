package crawler.krs_pobierz_pl.profil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Profil {
	
	private long id;

	private String nazwa;
	private String krs;
	private String nip;
	private String regon;
	private String kapitalZakladowy;
	private String formaPrawna;
	private String adresLinia1;
	private String adresLinia2;
	private String wojewodztwo;
	private String dataRejestracjiKrs;
	private String ostatniaZmianaKrs;
	private String reprezentacja;
	private String sposobReprezentacji;
	private String sad;
	private String sygnatura;
	private String przewazajacaDzialalnoscGospodarcza;
	private String website;
	private String email;
	private String ostatniaAktualizacjaDanych;
	private String meta;
	private int idHost;
	private int idThread;
	private Date data;

	
	public void setIdThread(int idThread) {
		this.idThread = idThread;
	}

	private List<Osoba> osoby = new ArrayList<Osoba>();

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Column(columnDefinition="mediumtext")
	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	@Column(columnDefinition="mediumtext")
	public String getKrs() {
		return krs;
	}

	public void setKrs(String krs) {
		this.krs = krs;
	}
	@Column(columnDefinition="mediumtext")
	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}
	@Column(columnDefinition="mediumtext")
	public String getRegon() {
		return regon;
	}

	public void setRegon(String regon) {
		this.regon = regon;
	}
	@Column(columnDefinition="mediumtext")
	public String getKapitalZakladowy() {
		return kapitalZakladowy;
	}

	public void setKapitalZakladowy(String kapitalZakladowy) {
		this.kapitalZakladowy = kapitalZakladowy;
	}
	@Column(columnDefinition="mediumtext")
	public String getFormaPrawna() {
		return formaPrawna;
	}

	public void setFormaPrawna(String formaPrawna) {
		this.formaPrawna = formaPrawna;
	}
	@Column(columnDefinition="mediumtext")
	public String getAdresLinia1() {
		return adresLinia1;
	}

	public void setAdresLinia1(String adresLinia1) {
		this.adresLinia1 = adresLinia1;
	}
	@Column(columnDefinition="mediumtext")
	public String getAdresLinia2() {
		return adresLinia2;
	}

	public void setAdresLinia2(String adresLinia2) {
		this.adresLinia2 = adresLinia2;
	}
	@Column(columnDefinition="mediumtext")
	public String getWojewodztwo() {
		return wojewodztwo;
	}

	public void setWojewodztwo(String wojewodztwo) {
		this.wojewodztwo = wojewodztwo;
	}
	@Column(columnDefinition="mediumtext")
	public String getDataRejestracjiKrs() {
		return dataRejestracjiKrs;
	}

	public void setDataRejestracjiKrs(String dataRejestracjiKrs) {
		this.dataRejestracjiKrs = dataRejestracjiKrs;
	}
	@Column(columnDefinition="mediumtext")
	public String getOstatniaZmianaKrs() {
		return ostatniaZmianaKrs;
	}

	public void setOstatniaZmianaKrs(String ostatniaZmianaKrs) {
		this.ostatniaZmianaKrs = ostatniaZmianaKrs;
	}
	@Column(columnDefinition="mediumtext")
	public String getReprezentacja() {
		return reprezentacja;
	}

	public void setReprezentacja(String reprezentacja) {
		this.reprezentacja = reprezentacja;
	}
	@Column(columnDefinition="mediumtext")
	public String getSposobReprezentacji() {
		return sposobReprezentacji;
	}

	public void setSposobReprezentacji(String sposobReprezentacji) {
		this.sposobReprezentacji = sposobReprezentacji;
	}
	@Column(columnDefinition="mediumtext")
	public String getSad() {
		return sad;
	}

	public void setSad(String sad) {
		this.sad = sad;
	}
	@Column(columnDefinition="mediumtext")
	public String getSygnatura() {
		return sygnatura;
	}

	public void setSygnatura(String sygnatura) {
		this.sygnatura = sygnatura;
	}
	@Column(columnDefinition="mediumtext")
	public String getPrzewazajacaDzialalnoscGospodarcza() {
		return przewazajacaDzialalnoscGospodarcza;
	}

	public void setPrzewazajacaDzialalnoscGospodarcza(String przewazajacaDzialalnoscGospodarcza) {
		this.przewazajacaDzialalnoscGospodarcza = przewazajacaDzialalnoscGospodarcza;
	}
	@Column(columnDefinition="mediumtext")
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	@Column(columnDefinition="mediumtext")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id")
	public List<Osoba> getOsoby() {
		return osoby;
	}

	public void setOsoby(List<Osoba> osoby) {
		this.osoby = osoby;
	}

	public String getOstatniaAktualizacjaDanych() {
		return ostatniaAktualizacjaDanych;
	}

	public void setOstatniaAktualizacjaDanych(String ostatniaAktualizacjaDanych) {
		this.ostatniaAktualizacjaDanych = ostatniaAktualizacjaDanych;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public int getIdHost() {
		return idHost;
	}

	public void setIdHost(int idHost) {
		this.idHost = idHost;
	}

	public int getIdThread() {
		return idThread;
	}

	public void idThread(int idThread) {
		this.idThread = idThread;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Profil() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	
	

}