package crawler.mojepanstwo_pl.gminy;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Gmina {

	@Id
	private long id;
	private long globalId;
	private long idPowiat;
	private long idWojewodztwo;
	private String telefon;
	private String fax;
	private String adres;
	private String nazwaUrzedu;
	private BigDecimal wydatkiRoczne;
	private String teryt;
	private String website;
	private int liczbaLudnosci;
	private int typId;
	private String email;
	private double powierzchnia;
	private String nts;
	private String nazwa;
	private BigDecimal zadluzenie;
	private BigDecimal dochody;
	private String radaNazwa;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getGlobalId() {
		return globalId;
	}
	public void setGlobalId(long globalId) {
		this.globalId = globalId;
	}
	public long getIdPowiat() {
		return idPowiat;
	}
	public void setIdPowiat(long idPowiat) {
		this.idPowiat = idPowiat;
	}
	public long getIdWojewodztwo() {
		return idWojewodztwo;
	}
	public void setIdWojewodztwo(long idWojewodztwo) {
		this.idWojewodztwo = idWojewodztwo;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getNazwaUrzedu() {
		return nazwaUrzedu;
	}
	public void setNazwaUrzedu(String nazwaUrzedu) {
		this.nazwaUrzedu = nazwaUrzedu;
	}
	public BigDecimal getWydatkiRoczne() {
		return wydatkiRoczne;
	}
	public void setWydatkiRoczne(BigDecimal wydatkiRoczne) {
		this.wydatkiRoczne = wydatkiRoczne;
	}
	public String getTeryt() {
		return teryt;
	}
	public void setTeryt(String teryt) {
		this.teryt = teryt;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public int getLiczbaLudnosci() {
		return liczbaLudnosci;
	}
	public void setLiczbaLudnosci(int liczbaLudnosci) {
		this.liczbaLudnosci = liczbaLudnosci;
	}
	public int getTypId() {
		return typId;
	}
	public void setTypId(int typId) {
		this.typId = typId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getPowierzchnia() {
		return powierzchnia;
	}
	public void setPowierzchnia(double powierzchnia) {
		this.powierzchnia = powierzchnia;
	}
	public String getNts() {
		return nts;
	}
	public void setNts(String nts) {
		this.nts = nts;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public BigDecimal getZadluzenie() {
		return zadluzenie;
	}
	public void setZadluzenie(BigDecimal zadluzenie) {
		this.zadluzenie = zadluzenie;
	}
	public BigDecimal getDochody() {
		return dochody;
	}
	public void setDochody(BigDecimal dochody) {
		this.dochody = dochody;
	}
	public String getRadaNazwa() {
		return radaNazwa;
	}
	public void setRadaNazwa(String radaNazwa) {
		this.radaNazwa = radaNazwa;
	}
	public Gmina() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Gmina [id=" + id + ", globalId=" + globalId + ", idPowiat=" + idPowiat + ", idWojewodztwo="
				+ idWojewodztwo + ", telefon=" + telefon + ", fax=" + fax + ", adres=" + adres + ", nazwaUrzedu="
				+ nazwaUrzedu + ", wydatkiRoczne=" + wydatkiRoczne + ", teryt=" + teryt + ", website=" + website
				+ ", liczbaLudnosci=" + liczbaLudnosci + ", typId=" + typId + ", email=" + email + ", powierzchnia="
				+ powierzchnia + ", nts=" + nts + ", nazwa=" + nazwa + ", zadluzenie=" + zadluzenie + ", dochody="
				+ dochody + ", radaNazwa=" + radaNazwa + "]";
	}
	
	
}
