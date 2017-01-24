package crawler.biznesfinder_pl;

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
@Table(name="biznesfinderindex")
public class BiznesFinderIndex {
	private Long id;
	private String nazwa;
	private String url;
	private String miasto;
	private String ulica;
	private String numer;
	private String telefon;
	private String website;
	private String email;
	private String meta;
	private String branza;
	private Date data;
	private String status;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(columnDefinition="mediumtext")
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(columnDefinition="mediumtext")
	public String getMiasto() {
		return miasto;
	}
	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}
	@Column(columnDefinition="mediumtext")
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	
	public String getNumer() {
		return numer;
	}
	public void setNumer(String numer) {
		this.numer = numer;
	}
	@Column(columnDefinition="mediumtext")
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
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
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	@Column(columnDefinition="mediumtext")
	public String getBranza() {
		return branza;
	}
	public void setBranza(String branza) {
		this.branza = branza;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BiznesFinderIndex() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BiznesFinderIndex [id=" + id + ", nazwa=" + nazwa + ", url=" + url + ", miasto=" + miasto + ", ulica="
				+ ulica + ", numer=" + numer + ", telefon=" + telefon + ", website=" + website + ", email=" + email
				+ ", meta=" + meta + ", branza=" + branza + ", data=" + data + ", status=" + status + "]";
	}
	

	
}
