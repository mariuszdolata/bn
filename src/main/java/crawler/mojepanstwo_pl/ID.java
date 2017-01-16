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
	private String status;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getKrs() {
		return krs;
	}
	public void setKrs(String krs) {
		this.krs = krs;
	}
	@Override
	public String toString() {
		return "ID [id=" + id + ", status=" + status + "]";
	}
	public ID() {
	}
	public ID(int id) {
//		this.setKrs(Strings.padStart(Integer.toString(id), 10, '0'));
		this.setKrs(Integer.toString(id));
	}
	
	
}
