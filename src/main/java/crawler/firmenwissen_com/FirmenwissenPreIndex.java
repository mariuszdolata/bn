package crawler.firmenwissen_com;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FirmenwissenPreIndex {
	@Id
	@GeneratedValue
	private long id;
	private String url;
	private String status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public FirmenwissenPreIndex(int number) {
		this.setUrl("http://www.firmenwissen.com/en/ergebnis.html?seite="+Integer.toString(number));
	}
	
	

}
