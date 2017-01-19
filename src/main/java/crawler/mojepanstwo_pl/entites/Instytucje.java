package crawler.mojepanstwo_pl.entites;

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
@Table(name="instytucje")
public class Instytucje {

	private Long id;
	private Long mojePanstwoId;
	private Long gminaId;
	private Long parentId;
	private Long kodPocztowyId;
	private String nazwa;
	private String email;
	private String website;
	private String telefon;
	private String fax;
	private String twitter;
	private String adres;
	private String kodPocztowy;
	private String opisHtml;
	private String avatar;
	private Long liczbaPozycjiBudzetowych;
	private BigDecimal budzetDotacjeSubwencje;
	private BigDecimal budzetWydatkiObslugaDlugu;
	private BigDecimal budzetWspolfinansowanieUE;
	private BigDecimal budzetWydatkiBiezaceJednostekBudzetowych;
	private BigDecimal budzetPlan;
	private BigDecimal budzetSwiadczeniaNaRzeczOsobFizycznych;
	private BigDecimal budzetWydatkiMajatkowe;
	private BigDecimal budzetSrodkiWlasneUE;
	private Date data;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMojePanstwoId() {
		return mojePanstwoId;
	}
	public void setMojePanstwoId(Long mojePanstwoId) {
		this.mojePanstwoId = mojePanstwoId;
	}
	public Long getGminaId() {
		return gminaId;
	}
	public void setGminaId(Long gminaId) {
		this.gminaId = gminaId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getKodPocztowyId() {
		return kodPocztowyId;
	}
	public void setKodPocztowyId(Long kodPocztowyId) {
		this.kodPocztowyId = kodPocztowyId;
	}
	@Column(columnDefinition="mediumtext")
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
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
	@Column(columnDefinition="mediumtext")
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getKodPocztowy() {
		return kodPocztowy;
	}
	public void setKodPocztowy(String kodPocztowy) {
		this.kodPocztowy = kodPocztowy;
	}
	@Column(columnDefinition="mediumtext")
	public String getOpisHtml() {
		return opisHtml;
	}
	public void setOpisHtml(String opisHtml) {
		this.opisHtml = opisHtml;
	}
	@Column(columnDefinition="mediumtext")
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Long getLiczbaPozycjiBudzetowych() {
		return liczbaPozycjiBudzetowych;
	}
	public void setLiczbaPozycjiBudzetowych(Long liczbaPozycjiBudzetowych) {
		this.liczbaPozycjiBudzetowych = liczbaPozycjiBudzetowych;
	}
	public BigDecimal getBudzetDotacjeSubwencje() {
		return budzetDotacjeSubwencje;
	}
	public void setBudzetDotacjeSubwencje(BigDecimal budzetDotacjeSubwencje) {
		this.budzetDotacjeSubwencje = budzetDotacjeSubwencje;
	}
	public BigDecimal getBudzetWydatkiObslugaDlugu() {
		return budzetWydatkiObslugaDlugu;
	}
	public void setBudzetWydatkiObslugaDlugu(BigDecimal budzetWydatkiObslugaDlugu) {
		this.budzetWydatkiObslugaDlugu = budzetWydatkiObslugaDlugu;
	}
	public BigDecimal getBudzetWspolfinansowanieUE() {
		return budzetWspolfinansowanieUE;
	}
	public void setBudzetWspolfinansowanieUE(BigDecimal budzetWspolfinansowanieUE) {
		this.budzetWspolfinansowanieUE = budzetWspolfinansowanieUE;
	}
	public BigDecimal getBudzetWydatkiBiezaceJednostekBudzetowych() {
		return budzetWydatkiBiezaceJednostekBudzetowych;
	}
	public void setBudzetWydatkiBiezaceJednostekBudzetowych(BigDecimal budzetWydatkiBiezaceJednostekBudzetowych) {
		this.budzetWydatkiBiezaceJednostekBudzetowych = budzetWydatkiBiezaceJednostekBudzetowych;
	}
	public BigDecimal getBudzetPlan() {
		return budzetPlan;
	}
	public void setBudzetPlan(BigDecimal budzetPlan) {
		this.budzetPlan = budzetPlan;
	}
	public BigDecimal getBudzetSwiadczeniaNaRzeczOsobFizycznych() {
		return budzetSwiadczeniaNaRzeczOsobFizycznych;
	}
	public void setBudzetSwiadczeniaNaRzeczOsobFizycznych(BigDecimal budzetSwiadczeniaNaRzeczOsobFizycznych) {
		this.budzetSwiadczeniaNaRzeczOsobFizycznych = budzetSwiadczeniaNaRzeczOsobFizycznych;
	}
	public BigDecimal getBudzetWydatkiMajatkowe() {
		return budzetWydatkiMajatkowe;
	}
	public void setBudzetWydatkiMajatkowe(BigDecimal budzetWydatkiMajatkowe) {
		this.budzetWydatkiMajatkowe = budzetWydatkiMajatkowe;
	}
	public BigDecimal getBudzetSrodkiWlasneUE() {
		return budzetSrodkiWlasneUE;
	}
	public void setBudzetSrodkiWlasneUE(BigDecimal budzetSrodkiWlasneUE) {
		this.budzetSrodkiWlasneUE = budzetSrodkiWlasneUE;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Instytucje() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Instytucje [id=" + id + ", mojePanstwoId=" + mojePanstwoId + ", gminaId=" + gminaId + ", parentId="
				+ parentId + ", kodPocztowyId=" + kodPocztowyId + ", nazwa=" + nazwa + ", email=" + email + ", website="
				+ website + ", telefon=" + telefon + ", fax=" + fax + ", twitter=" + twitter + ", adres=" + adres
				+ ", kodPocztowy=" + kodPocztowy + ", opisHtml=" + opisHtml + ", avatar=" + avatar
				+ ", liczbaPozycjiBudzetowych=" + liczbaPozycjiBudzetowych + ", budzetDotacjeSubwencje="
				+ budzetDotacjeSubwencje + ", budzetWydatkiObslugaDlugu=" + budzetWydatkiObslugaDlugu
				+ ", budzetWspolfinansowanieUE=" + budzetWspolfinansowanieUE
				+ ", budzetWydatkiBiezaceJednostekBudzetowych=" + budzetWydatkiBiezaceJednostekBudzetowych
				+ ", budzetPlan=" + budzetPlan + ", budzetSwiadczeniaNaRzeczOsobFizycznych="
				+ budzetSwiadczeniaNaRzeczOsobFizycznych + ", budzetWydatkiMajatkowe=" + budzetWydatkiMajatkowe
				+ ", budzetSrodkiWlasneUE=" + budzetSrodkiWlasneUE + ", data=" + data + "]";
	}
	
	
	
}
