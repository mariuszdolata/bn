package crawler.bisnode_pl.index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class IndexBisNode {
	
	private long id;
	private String nazwa;
	private String url;
	private String adres;
	private String krs;
	private String nip;
	private String meta;
	private String threadId;
	private String hostId;
    private Date data =new Date();
	private String status;
	
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
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
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
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
	public IndexBisNode(String nazwa) {
		super();
		this.nazwa = nazwa;
	}
	public IndexBisNode() {
		super();
	}
	@Override
	public String toString() {
		return "IndexBisNode [id=" + id + ", nazwa=" + nazwa + ", url=" + url + ", adres=" + adres + ", krs=" + krs
				+ ", nip=" + nip + ", meta=" + meta + ", threadId=" + threadId + ", hostId=" + hostId + ", data=" + data
				+ ", status=" + status + "]";
	}
	
	

}
