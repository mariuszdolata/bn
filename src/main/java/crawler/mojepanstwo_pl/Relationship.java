package crawler.mojepanstwo_pl;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Relationship {
	@Id
	private long id;
	private String type;
	private enum startTyp {OSOBA, PODMIOT};
	private long startId;
	private enum endTyp {OSOBA, PODMIOT};
	private long endId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getStartId() {
		return startId;
	}
	public void setStartId(long startId) {
		this.startId = startId;
	}
	public long getEndId() {
		return endId;
	}
	public void setEndId(long endId) {
		this.endId = endId;
	}
	public Relationship(String type, long startId, long endId) {
		super();
		this.type = type;
		this.startId = startId;
		this.endId = endId;
	}
	public Relationship() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Relationship [id=" + id + ", type=" + type + ", startId=" + startId + ", endId=" + endId + "]";
	}
	
	

}
