package crawler.mojepanstwo_pl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Strings;

@Entity
@Table(name="id")
public class ID {
	private int id;
	private String krs;
	private String status_krspodmiot;
	private String status_osoba;
	private String status_zamowieniepubliczne;
	private String status_patenty;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getStatus_krspodmiot() {
		return status_krspodmiot;
	}
	public void setStatus_krspodmiot(String status_krspodmiot) {
		this.status_krspodmiot = status_krspodmiot;
	}
	public String getStatus_osoba() {
		return status_osoba;
	}
	public void setStatus_osoba(String status_osoba) {
		this.status_osoba = status_osoba;
	}
	public String getKrs() {
		return krs;
	}
	public void setKrs(String krs) {
		this.krs = krs;
	}	
	public String getStatus_zamowieniepubliczne() {
		return status_zamowieniepubliczne;
	}
	public void setStatus_zamowieniepubliczne(String status_zamowieniepubliczne) {
		this.status_zamowieniepubliczne = status_zamowieniepubliczne;
	}
	public String getStatus_patenty() {
		return status_patenty;
	}
	public void setStatus_patenty(String status_patenty) {
		this.status_patenty = status_patenty;
	}
	@Override
	public String toString() {
		return "ID [id=" + id + ", krs=" + krs + ", status_krspodmiot=" + status_krspodmiot + ", status_osoba="
				+ status_osoba + "]";
	}
	public ID() {
	}
	public ID(int id) {
//		this.setKrs(Strings.padStart(Integer.toString(id), 10, '0'));
		this.setKrs(Integer.toString(id));
	}
	
	
}
