package crawler.znanefirmy_com;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(indexes = { @Index(columnList = "url", name = "index_url") })
public class ZnanefirmyIndex {
	@Id
	@GeneratedValue
	private long id;
	private String nazwa;
	private String url;
	private String adres;
	private String meta;
	private String status;
	private Date data;
	
	
	
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

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

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
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Column(columnDefinition="mediumtext")
	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public ZnanefirmyIndex(String nazwa, String url, String adres, String meta) {
		super();
		this.nazwa = nazwa;
		this.url = url;
		this.adres = adres;
		this.meta = meta;
	}
	

	public ZnanefirmyIndex() {
		
	}

	@Override
	public String toString() {
		return "ZnanefirmyIndex [id=" + id + ", nazwa=" + nazwa + ", url=" + url + ", adres=" + adres + "]";
	}

}
