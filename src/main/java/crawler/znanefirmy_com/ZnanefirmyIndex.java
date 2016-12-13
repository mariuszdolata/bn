package crawler.znanefirmy_com;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = { @Index(columnList = "url", name = "index_url") })
public class ZnanefirmyIndex {
	@Id
	@GeneratedValue
	private long id;
	private String nazwa;
	private String url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public ZnanefirmyIndex(String nazwa, String url, String adres) {
		super();
		this.nazwa = nazwa;
		this.url = url;
		this.adres = adres;
	}
	

	public ZnanefirmyIndex() {
		
	}

	@Override
	public String toString() {
		return "ZnanefirmyIndex [id=" + id + ", nazwa=" + nazwa + ", url=" + url + ", adres=" + adres + "]";
	}

}
