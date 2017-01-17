package crawler.mojepanstwo_pl;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="zamowienia_publiczne")
public class ZamowieniaPubliczne {
	private Long id;
	private Long zamawiajacyId;
	private Date data;
	private String nazwa;
	private String przedmiot;
	private String trybyId;
	private String trybyNazwa;
	private int liczbaMiesiecy;
	
	
	private BigDecimal wartoscCenaMin;
	private BigDecimal wartoscCenaMax;
	private BigDecimal zaliczka;
	private BigDecimal wartoscCena;
	private BigDecimal wartoscSzacunkowa;
	private BigDecimal wartoscSzacowana;
	
	private Date dataPublikacji;
	private Date dataPublikacjiZamowienia;
	
	private String zamawiajacyNazwa;
	private String zamawiajacyUlica;
	private String zamawiajacyNumerDomu;
	private String zamawiajacyMiejscowosc;
	private String zamawiajacyKodPocztowy;
	private String zamawiajacyWojewodztwo;
	
	private String zamawiajacyRegon;
	private String zamawiajacyEmail;
	private String zamawiajacyWebsite;
	private String zamawiajacyDynWebsite;
	private String zamawiajacyDszWebsite;
	private String zamawiajacyFax;
	private String zamawiajacyTelefon;
	
	private String projektUE;
	private String zamowienieUE;
	
	private String zamawiajacyRodzajInny;
	
	private Long gminaId;
	private Long powiatId;
	private Long wojewodztwoId;
	private Long kodPocztowyId;
	
	private int liczbaWykonawcow;
	private String wykonawcaNazwa;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}	
	@Column(columnDefinition="mediumtext")
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	@Column(columnDefinition="mediumtext")
	public String getPrzedmiot() {
		return przedmiot;
	}
	public void setPrzedmiot(String przedmiot) {
		this.przedmiot = przedmiot;
	}
	public String getTrybyId() {
		return trybyId;
	}
	public void setTrybyId(String trybyId) {
		this.trybyId = trybyId;
	}
	@Column(columnDefinition="mediumtext")
	public String getTrybyNazwa() {
		return trybyNazwa;
	}
	public void setTrybyNazwa(String trybyNazwa) {
		this.trybyNazwa = trybyNazwa;
	}
	public int getLiczbaMiesiecy() {
		return liczbaMiesiecy;
	}
	public void setLiczbaMiesiecy(int liczbaMiesiecy) {
		this.liczbaMiesiecy = liczbaMiesiecy;
	}
	public BigDecimal getWartoscCenaMin() {
		return wartoscCenaMin;
	}
	public void setWartoscCenaMin(BigDecimal wartoscCenaMin) {
		this.wartoscCenaMin = wartoscCenaMin;
	}
	public BigDecimal getWartoscCenaMax() {
		return wartoscCenaMax;
	}
	public void setWartoscCenaMax(BigDecimal wartoscCenaMax) {
		this.wartoscCenaMax = wartoscCenaMax;
	}
	public BigDecimal getZaliczka() {
		return zaliczka;
	}
	public void setZaliczka(BigDecimal zaliczka) {
		this.zaliczka = zaliczka;
	}
	public BigDecimal getWartoscCena() {
		return wartoscCena;
	}
	public void setWartoscCena(BigDecimal wartoscCena) {
		this.wartoscCena = wartoscCena;
	}
	public BigDecimal getWartoscSzacunkowa() {
		return wartoscSzacunkowa;
	}
	public void setWartoscSzacunkowa(BigDecimal wartoscSzacunkowa) {
		this.wartoscSzacunkowa = wartoscSzacunkowa;
	}
	public BigDecimal getWartoscSzacowana() {
		return wartoscSzacowana;
	}
	public void setWartoscSzacowana(BigDecimal wartoscSzacowana) {
		this.wartoscSzacowana = wartoscSzacowana;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataPublikacji() {
		return dataPublikacji;
	}
	public void setDataPublikacji(Date dataPublikacji) {
		this.dataPublikacji = dataPublikacji;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataPublikacjiZamowienia() {
		return dataPublikacjiZamowienia;
	}
	public void setDataPublikacjiZamowienia(Date dataPublikacjiZamowienia) {
		this.dataPublikacjiZamowienia = dataPublikacjiZamowienia;
	}
	@Column(columnDefinition="mediumtext")
	public String getZamawiajacyNazwa() {
		return zamawiajacyNazwa;
	}
	public void setZamawiajacyNazwa(String zamawiajacyNazwa) {
		this.zamawiajacyNazwa = zamawiajacyNazwa;
	}
	public String getZamawiajacyUlica() {
		return zamawiajacyUlica;
	}
	public void setZamawiajacyUlica(String zamawiajacyUlica) {
		this.zamawiajacyUlica = zamawiajacyUlica;
	}
	public String getZamawiajacyNumerDomu() {
		return zamawiajacyNumerDomu;
	}
	public void setZamawiajacyNumerDomu(String zamawiajacyNumerDomu) {
		this.zamawiajacyNumerDomu = zamawiajacyNumerDomu;
	}
	public String getZamawiajacyMiejscowosc() {
		return zamawiajacyMiejscowosc;
	}
	public void setZamawiajacyMiejscowosc(String zamawiajacyMiejscowosc) {
		this.zamawiajacyMiejscowosc = zamawiajacyMiejscowosc;
	}
	public String getZamawiajacyKodPocztowy() {
		return zamawiajacyKodPocztowy;
	}
	public void setZamawiajacyKodPocztowy(String zamawiajacyKodPocztowy) {
		this.zamawiajacyKodPocztowy = zamawiajacyKodPocztowy;
	}
	public String getZamawiajacyWojewodztwo() {
		return zamawiajacyWojewodztwo;
	}
	public void setZamawiajacyWojewodztwo(String zamawiajacyWojewodztwo) {
		this.zamawiajacyWojewodztwo = zamawiajacyWojewodztwo;
	}
	public String getZamawiajacyRegon() {
		return zamawiajacyRegon;
	}
	public void setZamawiajacyRegon(String zamawiajacyRegon) {
		this.zamawiajacyRegon = zamawiajacyRegon;
	}
	public String getZamawiajacyEmail() {
		return zamawiajacyEmail;
	}
	public void setZamawiajacyEmail(String zamawiajacyEmail) {
		this.zamawiajacyEmail = zamawiajacyEmail;
	}
	public String getZamawiajacyWebsite() {
		return zamawiajacyWebsite;
	}
	public void setZamawiajacyWebsite(String zamawiajacyWebsite) {
		this.zamawiajacyWebsite = zamawiajacyWebsite;
	}
	public String getZamawiajacyDynWebsite() {
		return zamawiajacyDynWebsite;
	}
	public void setZamawiajacyDynWebsite(String zamawiajacyDynWebsite) {
		this.zamawiajacyDynWebsite = zamawiajacyDynWebsite;
	}
	public String getZamawiajacyDszWebsite() {
		return zamawiajacyDszWebsite;
	}
	public void setZamawiajacyDszWebsite(String zamawiajacyDszWebsite) {
		this.zamawiajacyDszWebsite = zamawiajacyDszWebsite;
	}
	public String getZamawiajacyFax() {
		return zamawiajacyFax;
	}
	public void setZamawiajacyFax(String zamawiajacyFax) {
		this.zamawiajacyFax = zamawiajacyFax;
	}
	public String getZamawiajacyTelefon() {
		return zamawiajacyTelefon;
	}
	public void setZamawiajacyTelefon(String zamawiajacyTelefon) {
		this.zamawiajacyTelefon = zamawiajacyTelefon;
	}
	@Column(columnDefinition="mediumtext")
	public String getProjektUE() {
		return projektUE;
	}
	public void setProjektUE(String projektUE) {
		this.projektUE = projektUE;
	}
	public String getZamowienieUE() {
		return zamowienieUE;
	}
	public void setZamowienieUE(String zamowienieUE) {
		this.zamowienieUE = zamowienieUE;
	}
	public String getZamawiajacyRodzajInny() {
		return zamawiajacyRodzajInny;
	}
	public void setZamawiajacyRodzajInny(String zamawiajacyRodzajInny) {
		this.zamawiajacyRodzajInny = zamawiajacyRodzajInny;
	}
	public Long getGminaId() {
		return gminaId;
	}
	public void setGminaId(Long gminaId) {
		this.gminaId = gminaId;
	}
	public Long getPowiatId() {
		return powiatId;
	}
	public void setPowiatId(Long powiatId) {
		this.powiatId = powiatId;
	}
	public Long getWojewodztwoId() {
		return wojewodztwoId;
	}
	public void setWojewodztwoId(Long wojewodztwoId) {
		this.wojewodztwoId = wojewodztwoId;
	}
	public Long getKodPocztowyId() {
		return kodPocztowyId;
	}
	public void setKodPocztowyId(Long kodPocztowyId) {
		this.kodPocztowyId = kodPocztowyId;
	}
	public int getLiczbaWykonawcow() {
		return liczbaWykonawcow;
	}
	public void setLiczbaWykonawcow(int liczbaWykonawcow) {
		this.liczbaWykonawcow = liczbaWykonawcow;
	}
	@Column(columnDefinition="mediumtext")
	public String getWykonawcaNazwa() {
		return wykonawcaNazwa;
	}
	public void setWykonawcaNazwa(String wykonawcaNazwa) {
		this.wykonawcaNazwa = wykonawcaNazwa;
	}
	public Long getZamawiajacyId() {
		return zamawiajacyId;
	}
	public void setZamawiajacyId(Long zamawiajacyId) {
		this.zamawiajacyId = zamawiajacyId;
	}
	@Override
	public String toString() {
		return "ZamowieniaPubliczne [id=" + id + ", zamawiajacyId=" + zamawiajacyId + ", nazwa=" + nazwa
				+ ", przedmiot=" + przedmiot + "\n, trybyId=" + trybyId + ", trybyNazwa=" + trybyNazwa
				+ ", liczbaMiesiecy=" + liczbaMiesiecy + "\n, wartoscCenaMin=" + wartoscCenaMin + ", wartoscCenaMax="
				+ wartoscCenaMax + ", zaliczka=" + zaliczka + ", wartoscCena=" + wartoscCena + ", wartoscSzacunkowa="
				+ wartoscSzacunkowa + ", wartoscSzacowana=" + wartoscSzacowana + ", dataPublikacji=" + dataPublikacji
				+ ", dataPublikacjiZamowienia=" + dataPublikacjiZamowienia + "\n, zamawiajacyNazwa=" + zamawiajacyNazwa
				+ ", zamawiajacyUlica=" + zamawiajacyUlica + ", zamawiajacyNumerDomu=" + zamawiajacyNumerDomu
				+ ", zamawiajacyMiejscowosc=" + zamawiajacyMiejscowosc + ", zamawiajacyKodPocztowy="
				+ zamawiajacyKodPocztowy + "\n, zamawiajacyWojewodztwo=" + zamawiajacyWojewodztwo + ", zamawiajacyRegon="
				+ zamawiajacyRegon + ", zamawiajacyEmail=" + zamawiajacyEmail + ", zamawiajacyWebsite="
				+ zamawiajacyWebsite + ", zamawiajacyDynWebsite=" + zamawiajacyDynWebsite + ", zamawiajacyDszWebsite="
				+ zamawiajacyDszWebsite + "\n, zamawiajacyFax=" + zamawiajacyFax + ", zamawiajacyTelefon="
				+ zamawiajacyTelefon + ", projektUE=" + projektUE + ", zamowienieUE=" + zamowienieUE
				+ ", zamawiajacyRodzajInny=" + zamawiajacyRodzajInny + "\n, gminaId=" + gminaId + ", powiatId=" + powiatId
				+ ", wojewodztwoId=" + wojewodztwoId + ", kodPocztowyId=" + kodPocztowyId + "\n, liczbaWykonawcow="
				+ liczbaWykonawcow + ", wykonawcaNazwa=" + wykonawcaNazwa + "]";
	}
	
	
}
