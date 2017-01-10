package crawler.firmenwissen_com;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;

import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class FirmenwissenIndex {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String url;
	private String address;
	private String country;
	private String branche;
	private String update_info;
	@DateTimeFormat(pattern = "YYYY-mm-DD")
	private Date last_update;
	private String status;
	@Column(name="data", insertable=false, updatable=false, columnDefinition="timestamp default current_timestamp")
	@org.hibernate.annotations.Generated(value=GenerationTime.INSERT)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateCreated;
	private String meta;
	
	
	
	
	
	
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getBranche() {
		return branche;
	}
	public void setBranche(String branche) {
		this.branche = branche;
	}
	

	public String getUpdate_info() {
		return update_info;
	}
	public void setUpdate_info(String update_info) {
		this.update_info = update_info;
	}
	public FirmenwissenIndex() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FirmenwissenIndex(String name, String url, String address, String country, String branche, String update, String meta) {
		super();
		this.name = name;
		this.url = "http://www.firmenwissen.com/en"+url;
		this.address = address;
		this.country = country;
		this.branche = branche;
		if(update.contains("Update")){
			update=update.replaceAll("Update", "").replaceAll("days ago", "").replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "");;
			int days = Integer.parseInt(update)*(-1);
			this.update_info = update;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
//			System.out.println("Obecny czas="+sdf.format(calendar.getTime()));
			calendar.add(Calendar.DATE, days);
//			System.out.println("Zmieniony czas="+sdf.format(calendar.getTime()));
			this.last_update = calendar.getTime();
			this.setMeta(meta);
		
		}
		else this.update_info="";
		
	}
	@Override
	public String toString() {
		return "FirmenwissenIndex [id=" + id + ", name=" + name + ", url=" + url + ", address=" + address + ", country="
				+ country + ", branche=" + branche + ", update_info=" + update_info + ", last_update=" + last_update
				+ ", status=" + status + ", dateCreated=" + dateCreated + "]";
	}
	
	
	
	
	
}
