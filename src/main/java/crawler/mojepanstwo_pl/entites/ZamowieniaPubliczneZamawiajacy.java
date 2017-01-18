package crawler.mojepanstwo_pl.entites;

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
@Table(name="zamowienia_publiczne_zamawiajacy")
public class ZamowieniaPubliczneZamawiajacy {

	private Long id;
	private Long gminaId;
	private Long instytucjaId;
	private Long objectId;
	private Long rodzajId;
	private Long mojepanstwoId;
	private Long kodPocztowyId;
	
	private String regon;
	private String nazwa;
	private String ulica;
	private String domNumer;
	private String kodPocztowy;
	private String miejscowosc;
	private String wojewodztwo;
	private String telefon;
	private String fax;
	private String website;
	private String email;
	private String dataSet;
	private String rodzaj;
	private String rodzajInny;
	private Date data;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGminaId() {
		return gminaId;
	}
	public void setGminaId(Long gminaId) {
		this.gminaId = gminaId;
	}
	public Long getInstytucjaId() {
		return instytucjaId;
	}
	public void setInstytucjaId(Long instytucjaId) {
		this.instytucjaId = instytucjaId;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public Long getRodzajId() {
		return rodzajId;
	}
	public void setRodzajId(Long rodzajId) {
		this.rodzajId = rodzajId;
	}
	public Long getMojepanstwoId() {
		return mojepanstwoId;
	}
	public void setMojepanstwoId(Long mojepanstwoId) {
		this.mojepanstwoId = mojepanstwoId;
	}
	public Long getKodPocztowyId() {
		return kodPocztowyId;
	}
	public void setKodPocztowyId(Long kodPocztowyId) {
		this.kodPocztowyId = kodPocztowyId;
	}
	public String getRegon() {
		return regon;
	}
	public void setRegon(String regon) {
		this.regon = regon;
	}
	@Column(columnDefinition="mediumtext")
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	@Column(columnDefinition="mediumtext")
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public String getDomNumer() {
		return domNumer;
	}
	public void setDomNumer(String domNumer) {
		this.domNumer = domNumer;
	}
	public String getKodPocztowy() {
		return kodPocztowy;
	}
	public void setKodPocztowy(String kodPocztowy) {
		this.kodPocztowy = kodPocztowy;
	}
	public String getMiejscowosc() {
		return miejscowosc;
	}
	public void setMiejscowosc(String miejscowosc) {
		this.miejscowosc = miejscowosc;
	}
	public String getWojewodztwo() {
		return wojewodztwo;
	}
	public void setWojewodztwo(String wojewodztwo) {
		this.wojewodztwo = wojewodztwo;
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
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(columnDefinition="mediumtext")
	public String getDataSet() {
		return dataSet;
	}
	public void setDataSet(String dataSet) {
		this.dataSet = dataSet;
	}
	@Column(columnDefinition="mediumtext")
	public String getRodzaj() {
		return rodzaj;
	}
	public void setRodzaj(String rodzaj) {
		this.rodzaj = rodzaj;
	}
	@Column(columnDefinition="mediumtext")
	public String getRodzajInny() {
		return rodzajInny;
	}
	public void setRodzajInny(String rodzajInny) {
		this.rodzajInny = rodzajInny;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public ZamowieniaPubliczneZamawiajacy() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ZamowieniaPubliczneZamawiajacy [id=" + id + ", gminaId=" + gminaId + ", instytucjaId=" + instytucjaId
				+ ", objectId=" + objectId + ", rodzajId=" + rodzajId + ", mojepanstwoId=" + mojepanstwoId
				+ ", kodPocztowyId=" + kodPocztowyId + ", regon=" + regon + ", nazwa=" + nazwa + ", ulica=" + ulica
				+ ", domNumer=" + domNumer + ", kodPocztowy=" + kodPocztowy + ", miejscowosc=" + miejscowosc
				+ ", wojewodztwo=" + wojewodztwo + ", telefon=" + telefon + ", fax=" + fax + ", website=" + website
				+ ", email=" + email + ", dataSet=" + dataSet + ", rodzaj=" + rodzaj + ", rodzajInny=" + rodzajInny
				+ ", data=" + data + "]";
	}
	
	
	
}
