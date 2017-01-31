package proxy;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="proxy")
public class ProxyEntity {

	private Long id;
	private String ipPort;
	private String parse;
	private Date dataSprawdzenia;
	private Long ms;
	private int retry;
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIpPort() {
		return ipPort;
	}
	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}
	public String getParse() {
		return parse;
	}
	public void setParse(String parse) {
		this.parse = parse;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataSprawdzenia() {
		return dataSprawdzenia;
	}
	public void setDataSprawdzenia(Date dataSprawdzenia) {
		this.dataSprawdzenia = dataSprawdzenia;
	}
	public Long getMs() {
		return ms;
	}
	public void setMs(Long ms) {
		this.ms = ms;
	}
	public int getRetry() {
		return retry;
	}
	public void setRetry(int retry) {
		this.retry = retry;
	}
	public ProxyEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
