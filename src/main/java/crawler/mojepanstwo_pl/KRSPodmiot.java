package crawler.mojepanstwo_pl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Type;

@Entity
public class KRSPodmiot {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idKRSPodmiot")
	private long idKRSPodmiot;
	@Type(type="text")
	private String firma;
	@Type(type="text")
	private String nazwa;
	@Type(type="text")
	private String nazwaSkrocona;
	@Type(type="text")
	private String nazwaOrganuReprezentacji;
	private Boolean wykreslony;
	@Type(type="text")
	private String formaPrawnaStr;
	private String regon;
	private String krs;
	private String nip;
	private Boolean oop;
	@Type(type="text")
	private String siedziba;
	@Type(type="text")
	private String adresUlica;
	private String adresNumer;
	private String adresLokal;
	private String adresKodPocztowy;
	private String adresMiejscowosc;
	private String adresPoczta;
	private String adresKraj;
	@Type(type="text")
	private String adres;
	private int rejestr;
	private long formaPrawnaTypId;
	private long formaPrawnaId;
	private Date dataDokonaniaWpisu;
	private Date dataOstatniWpis;
	private Date dataRejestracji;
	private Date dataSprawdzenia;
	@Type(type="text")
	private String email;
	@Type(type="text")
	private String website;
	@Type(type="text")
	private String celDzialania;
	private BigDecimal wartoscNominalnaPodwyzszeniaKapitalu;
	private BigDecimal wartoscNominalnaAkcji;
	private BigDecimal wartoscCzescKapitaluWplaconego;
	private BigDecimal liczbaAkcjiWszystkichEmisji;
	private BigDecimal wartoscKapitalDocelowy;
	private BigDecimal wartoscKapitalZakladowy;
	private long powiatId;
	private long gminaId;
	private long miejscowoscId;
	private long kodPocztowyId;
	private long wojewodztwoId;
	private String nieprzedsiebiorca;
	private String rejestrStowarzyszen;
	private long numerWpisu;
	private long ostatniWpisId;
	private int dotacjeUEBeneficjentId;
	@Type(type="text")
	private String oznaczenieSadu;
	@Type(type="text")
	private String sygnaturaAkt;
	@Type(type="text")
	private String wczesniejszaRejestracjaStr;
	@Type(type="text")
	private String sposobReprezentacji;
	@Type(type="text")
	private String nazwaOrganuNadzoru;
	@Type(type="text")
	private String umowaSpolkiCywilnej;
	private long knfOstrzezenieId;
	private long gpwSpolkaId;
	private Boolean gpw;
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
	private Set<PKD> dzialalnosci = new HashSet<PKD>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<EmisjaAkcji> emisjeAkcji = new HashSet<EmisjaAkcji>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<Firma> firmy = new HashSet<Firma>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<Relationship> relationships = new HashSet<Relationship>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<Reprezentacja> reprezentacja = new HashSet<Reprezentacja>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<Osoba> nadzor = new HashSet<Osoba>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<Oddzial> oddzial = new HashSet<Oddzial>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<Osoba> komitetZalozycielski = new HashSet<Osoba>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<Wspolnik> wspolnicy = new HashSet<Wspolnik>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<Reprezentacja> prokurenci = new HashSet<Reprezentacja>();
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="kRSPodmioty")
//	private Set<Osoba> jedynyAkcjonariusz = new HashSet<Osoba>();
	public long getIdKRSPodmiot() {
		return idKRSPodmiot;
	}
	public void setIdKRSPodmiot(long idKRSPodmiot) {
		this.idKRSPodmiot = idKRSPodmiot;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getNazwaSkrocona() {
		return nazwaSkrocona;
	}
	public void setNazwaSkrocona(String nazwaSkrocona) {
		this.nazwaSkrocona = nazwaSkrocona;
	}
	public String getNazwaOrganuReprezentacji() {
		return nazwaOrganuReprezentacji;
	}
	public void setNazwaOrganuReprezentacji(String nazwaOrganuReprezentacji) {
		this.nazwaOrganuReprezentacji = nazwaOrganuReprezentacji;
	}
	public Boolean getWykreslony() {
		return wykreslony;
	}
	public void setWykreslony(Boolean wykreslony) {
		this.wykreslony = wykreslony;
	}
	public String getFormaPrawnaStr() {
		return formaPrawnaStr;
	}
	public void setFormaPrawnaStr(String formaPrawnaStr) {
		this.formaPrawnaStr = formaPrawnaStr;
	}
	public String getRegon() {
		return regon;
	}
	public void setRegon(String regon) {
		this.regon = regon;
	}
	public String getKrs() {
		return krs;
	}
	public void setKrs(String krs) {
		this.krs = krs;
	}
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public Boolean getOop() {
		return oop;
	}
	public void setOop(Boolean oop) {
		this.oop = oop;
	}
	public String getSiedziba() {
		return siedziba;
	}
	public void setSiedziba(String siedziba) {
		this.siedziba = siedziba;
	}
	public String getAdresUlica() {
		return adresUlica;
	}
	public void setAdresUlica(String adresUlica) {
		this.adresUlica = adresUlica;
	}
	public String getAdresNumer() {
		return adresNumer;
	}
	public void setAdresNumer(String adresNumer) {
		this.adresNumer = adresNumer;
	}
	public String getAdresLokal() {
		return adresLokal;
	}
	public void setAdresLokal(String adresLokal) {
		this.adresLokal = adresLokal;
	}
	public String getAdresKodPocztowy() {
		return adresKodPocztowy;
	}
	public void setAdresKodPocztowy(String adresKodPocztowy) {
		this.adresKodPocztowy = adresKodPocztowy;
	}
	public String getAdresMiejscowosc() {
		return adresMiejscowosc;
	}
	public void setAdresMiejscowosc(String adresMiejscowosc) {
		this.adresMiejscowosc = adresMiejscowosc;
	}
	public String getAdresPoczta() {
		return adresPoczta;
	}
	public void setAdresPoczta(String adresPoczta) {
		this.adresPoczta = adresPoczta;
	}
	public String getAdresKraj() {
		return adresKraj;
	}
	public void setAdresKraj(String adresKraj) {
		this.adresKraj = adresKraj;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public int getRejestr() {
		return rejestr;
	}
	public void setRejestr(int rejestr) {
		this.rejestr = rejestr;
	}
	public long getFormaPrawnaTypId() {
		return formaPrawnaTypId;
	}
	public void setFormaPrawnaTypId(long formaPrawnaTypId) {
		this.formaPrawnaTypId = formaPrawnaTypId;
	}
	public long getFormaPrawnaId() {
		return formaPrawnaId;
	}
	public void setFormaPrawnaId(long formaPrawnaId) {
		this.formaPrawnaId = formaPrawnaId;
	}
	public Date getDataDokonaniaWpisu() {
		return dataDokonaniaWpisu;
	}
	public void setDataDokonaniaWpisu(Date dataDokonaniaWpisu) {
		this.dataDokonaniaWpisu = dataDokonaniaWpisu;
	}
	public Date getDataOstatniWpis() {
		return dataOstatniWpis;
	}
	public void setDataOstatniWpis(Date dataOstatniWpis) {
		this.dataOstatniWpis = dataOstatniWpis;
	}
	public Date getDataRejestracji() {
		return dataRejestracji;
	}
	public void setDataRejestracji(Date dataRejestracji) {
		this.dataRejestracji = dataRejestracji;
	}
	public Date getDataSprawdzenia() {
		return dataSprawdzenia;
	}
	public void setDataSprawdzenia(Date dataSprawdzenia) {
		this.dataSprawdzenia = dataSprawdzenia;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getCelDzialania() {
		return celDzialania;
	}
	public void setCelDzialania(String celDzialania) {
		this.celDzialania = celDzialania;
	}
	public BigDecimal getWartoscNominalnaPodwyzszeniaKapitalu() {
		return wartoscNominalnaPodwyzszeniaKapitalu;
	}
	public void setWartoscNominalnaPodwyzszeniaKapitalu(BigDecimal wartoscNominalnaPodwyzszeniaKapitalu) {
		this.wartoscNominalnaPodwyzszeniaKapitalu = wartoscNominalnaPodwyzszeniaKapitalu;
	}
	public BigDecimal getWartoscNominalnaAkcji() {
		return wartoscNominalnaAkcji;
	}
	public void setWartoscNominalnaAkcji(BigDecimal wartoscNominalnaAkcji) {
		this.wartoscNominalnaAkcji = wartoscNominalnaAkcji;
	}
	public BigDecimal getWartoscCzescKapitaluWplaconego() {
		return wartoscCzescKapitaluWplaconego;
	}
	public void setWartoscCzescKapitaluWplaconego(BigDecimal wartoscCzescKapitaluWplaconego) {
		this.wartoscCzescKapitaluWplaconego = wartoscCzescKapitaluWplaconego;
	}
	public BigDecimal getLiczbaAkcjiWszystkichEmisji() {
		return liczbaAkcjiWszystkichEmisji;
	}
	public void setLiczbaAkcjiWszystkichEmisji(BigDecimal liczbaAkcjiWszystkichEmisji) {
		this.liczbaAkcjiWszystkichEmisji = liczbaAkcjiWszystkichEmisji;
	}
	public BigDecimal getWartoscKapitalDocelowy() {
		return wartoscKapitalDocelowy;
	}
	public void setWartoscKapitalDocelowy(BigDecimal wartoscKapitalDocelowy) {
		this.wartoscKapitalDocelowy = wartoscKapitalDocelowy;
	}
	public BigDecimal getWartoscKapitalZakladowy() {
		return wartoscKapitalZakladowy;
	}
	public void setWartoscKapitalZakladowy(BigDecimal wartoscKapitalZakladowy) {
		this.wartoscKapitalZakladowy = wartoscKapitalZakladowy;
	}
	public long getPowiatId() {
		return powiatId;
	}
	public void setPowiatId(long powiatId) {
		this.powiatId = powiatId;
	}
	public long getGminaId() {
		return gminaId;
	}
	public void setGminaId(long gminaId) {
		this.gminaId = gminaId;
	}
	public long getMiejscowoscId() {
		return miejscowoscId;
	}
	public void setMiejscowoscId(long miejscowoscId) {
		this.miejscowoscId = miejscowoscId;
	}
	public long getKodPocztowyId() {
		return kodPocztowyId;
	}
	public void setKodPocztowyId(long kodPocztowyId) {
		this.kodPocztowyId = kodPocztowyId;
	}
	public long getWojewodztwoId() {
		return wojewodztwoId;
	}
	public void setWojewodztwoId(long wojewodztwoId) {
		this.wojewodztwoId = wojewodztwoId;
	}
	public String getNieprzedsiebiorca() {
		return nieprzedsiebiorca;
	}
	public void setNieprzedsiebiorca(String nieprzedsiebiorca) {
		this.nieprzedsiebiorca = nieprzedsiebiorca;
	}
	public String getRejestrStowarzyszen() {
		return rejestrStowarzyszen;
	}
	public void setRejestrStowarzyszen(String rejestrStowarzyszen) {
		this.rejestrStowarzyszen = rejestrStowarzyszen;
	}
	public long getNumerWpisu() {
		return numerWpisu;
	}
	public void setNumerWpisu(long numerWpisu) {
		this.numerWpisu = numerWpisu;
	}
	public long getOstatniWpisId() {
		return ostatniWpisId;
	}
	public void setOstatniWpisId(long ostatniWpisId) {
		this.ostatniWpisId = ostatniWpisId;
	}
	public int getDotacjeUEBeneficjentId() {
		return dotacjeUEBeneficjentId;
	}
	public void setDotacjeUEBeneficjentId(int dotacjeUEBeneficjentId) {
		this.dotacjeUEBeneficjentId = dotacjeUEBeneficjentId;
	}
	public String getOznaczenieSadu() {
		return oznaczenieSadu;
	}
	public void setOznaczenieSadu(String oznaczenieSadu) {
		this.oznaczenieSadu = oznaczenieSadu;
	}
	public String getSygnaturaAkt() {
		return sygnaturaAkt;
	}
	public void setSygnaturaAkt(String sygnaturaAkt) {
		this.sygnaturaAkt = sygnaturaAkt;
	}
	public String getWczesniejszaRejestracjaStr() {
		return wczesniejszaRejestracjaStr;
	}
	public void setWczesniejszaRejestracjaStr(String wczesniejszaRejestracjaStr) {
		this.wczesniejszaRejestracjaStr = wczesniejszaRejestracjaStr;
	}
	public String getSposobReprezentacji() {
		return sposobReprezentacji;
	}
	public void setSposobReprezentacji(String sposobReprezentacji) {
		this.sposobReprezentacji = sposobReprezentacji;
	}
	public String getNazwaOrganuNadzoru() {
		return nazwaOrganuNadzoru;
	}
	public void setNazwaOrganuNadzoru(String nazwaOrganuNadzoru) {
		this.nazwaOrganuNadzoru = nazwaOrganuNadzoru;
	}
	public String getUmowaSpolkiCywilnej() {
		return umowaSpolkiCywilnej;
	}
	public void setUmowaSpolkiCywilnej(String umowaSpolkiCywilnej) {
		this.umowaSpolkiCywilnej = umowaSpolkiCywilnej;
	}
	public long getKnfOstrzezenieId() {
		return knfOstrzezenieId;
	}
	public void setKnfOstrzezenieId(long knfOstrzezenieId) {
		this.knfOstrzezenieId = knfOstrzezenieId;
	}
	public long getGpwSpolkaId() {
		return gpwSpolkaId;
	}
	public void setGpwSpolkaId(long gpwSpolkaId) {
		this.gpwSpolkaId = gpwSpolkaId;
	}
	public Boolean getGpw() {
		return gpw;
	}
	public void setGpw(Boolean gpw) {
		this.gpw = gpw;
	}
	public Set<PKD> getDzialalnosci() {
		return dzialalnosci;
	}
	public void setDzialalnosci(Set<PKD> dzialalnosci) {
		this.dzialalnosci = dzialalnosci;
	}
//	public Set<EmisjaAkcji> getEmisjeAkcji() {
//		return emisjeAkcji;
//	}
//	public void setEmisjeAkcji(Set<EmisjaAkcji> emisjeAkcji) {
//		this.emisjeAkcji = emisjeAkcji;
//	}
//	public Set<Firma> getFirmy() {
//		return firmy;
//	}
//	public void setFirmy(Set<Firma> firmy) {
//		this.firmy = firmy;
//	}
//	public Set<Relationship> getRelationships() {
//		return relationships;
//	}
//	public void setRelationships(Set<Relationship> relationships) {
//		this.relationships = relationships;
//	}
//	public Set<Reprezentacja> getReprezentacja() {
//		return reprezentacja;
//	}
//	public void setReprezentacja(Set<Reprezentacja> reprezentacja) {
//		this.reprezentacja = reprezentacja;
//	}
//	public Set<Osoba> getNadzor() {
//		return nadzor;
//	}
//	public void setNadzor(Set<Osoba> nadzor) {
//		this.nadzor = nadzor;
//	}
//	public Set<Oddzial> getOddzial() {
//		return oddzial;
//	}
//	public void setOddzial(Set<Oddzial> oddzial) {
//		this.oddzial = oddzial;
//	}
//	public Set<Osoba> getKomitetZalozycielski() {
//		return komitetZalozycielski;
//	}
//	public void setKomitetZalozycielski(Set<Osoba> komitetZalozycielski) {
//		this.komitetZalozycielski = komitetZalozycielski;
//	}
//	public Set<Wspolnik> getWspolnicy() {
//		return wspolnicy;
//	}
//	public void setWspolnicy(Set<Wspolnik> wspolnicy) {
//		this.wspolnicy = wspolnicy;
//	}
//	public Set<Reprezentacja> getProkurenci() {
//		return prokurenci;
//	}
//	public void setProkurenci(Set<Reprezentacja> prokurenci) {
//		this.prokurenci = prokurenci;
//	}
//	public Set<Osoba> getJedynyAkcjonariusz() {
//		return jedynyAkcjonariusz;
//	}
//	public void setJedynyAkcjonariusz(Set<Osoba> jedynyAkcjonariusz) {
//		this.jedynyAkcjonariusz = jedynyAkcjonariusz;
//	}

	public KRSPodmiot() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "KRSPodmiot [idKRSPodmiot=" + idKRSPodmiot + ", firma=" + firma + "\n, nazwa=" + nazwa + ", nazwaSkrocona="
				+ nazwaSkrocona + ", nazwaOrganuReprezentacji=" + nazwaOrganuReprezentacji + ", \nwykreslony="
				+ wykreslony + ", formaPrawnaStr=" + formaPrawnaStr + ", regon=" + regon + ", \nkrs=" + krs + ", nip="
				+ nip + ", oop=" + oop + ", siedziba=" + siedziba + ", adresUlica=" + adresUlica + ", \nadresNumer="
				+ adresNumer + ", adresLokal=" + adresLokal + ", adresKodPocztowy=" + adresKodPocztowy
				+ ", adresMiejscowosc=" + adresMiejscowosc + ", adresPoczta=" + adresPoczta + ", \nadresKraj=" + adresKraj
				+ ", adres=" + adres + ", rejestr=" + rejestr + ", \nformaPrawnaTypId=" + formaPrawnaTypId
				+ ", formaPrawnaId=" + formaPrawnaId + ", dataDokonaniaWpisu=" + dataDokonaniaWpisu
				+ ", dataOstatniWpis=" + dataOstatniWpis + ", dataRejestracji=" + dataRejestracji + ", \ndataSprawdzenia="
				+ dataSprawdzenia + ", email=" + email + ", website=" + website + ", \ncelDzialania=" + celDzialania
				+ ", wartoscNominalnaPodwyzszeniaKapitalu=" + wartoscNominalnaPodwyzszeniaKapitalu
				+ ", \nwartoscNominalnaAkcji=" + wartoscNominalnaAkcji + ", wartoscCzescKapitaluWplaconego="
				+ wartoscCzescKapitaluWplaconego + ", liczbaAkcjiWszystkichEmisji=" + liczbaAkcjiWszystkichEmisji
				+ ", wartoscKapitalDocelowy=" + wartoscKapitalDocelowy + ", \nwartoscKapitalZakladowy="
				+ wartoscKapitalZakladowy + ", powiatId=" + powiatId + ", gminaId=" + gminaId + ", miejscowoscId="
				+ miejscowoscId + ", kodPocztowyId=" + kodPocztowyId + ", \nwojewodztwoId=" + wojewodztwoId
				+ ", nieprzedsiebiorca=" + nieprzedsiebiorca + ", rejestrStowarzyszen=" + rejestrStowarzyszen
				+ ", numerWpisu=" + numerWpisu + ", ostatniWpisId=" + ostatniWpisId + ", \ndotacjeUEBeneficjentId="
				+ dotacjeUEBeneficjentId + ", oznaczenieSadu=" + oznaczenieSadu + ", sygnaturaAkt=" + sygnaturaAkt
				+ ", wczesniejszaRejestracjaStr=" + wczesniejszaRejestracjaStr + ", \nsposobReprezentacji="
				+ sposobReprezentacji + ", \nnazwaOrganuNadzoru=" + nazwaOrganuNadzoru + ", umowaSpolkiCywilnej="
				+ umowaSpolkiCywilnej + ", knfOstrzezenieId=" + knfOstrzezenieId + ", gpwSpolkaId=" + gpwSpolkaId
				+ ", gpw=" + gpw + ", dzialalnosci=" + dzialalnosci + "]";
	}
	
	
	
		
}
