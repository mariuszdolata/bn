package crawler.mojepanstwo_pl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="relationship")
public class Relationship {
	
	public enum RelationshipTyp  {OSOBA, PODMIOT};
	private Long id;
	private String type;
	private Long startId;
	private Long endId;
	private RelationshipTyp startTyp;
	private RelationshipTyp endTyp;
	private Set<KRSPodmiot> krsPodmioty = new HashSet<KRSPodmiot>(0);
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="relationship_id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Enumerated(EnumType.STRING)
	public RelationshipTyp getStartTyp() {
		return startTyp;
	}
	public void setStartTyp(RelationshipTyp startTyp) {
		this.startTyp = startTyp;
	}
	@Enumerated(EnumType.STRING)
	public RelationshipTyp getEndTyp() {
		return endTyp;
	}
	public void setEndTyp(RelationshipTyp endTyp) {
		this.endTyp = endTyp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getStartId() {
		return startId;
	}
	public void setStartId(Long startId) {
		this.startId = startId;
	}
	public Long getEndId() {
		return endId;
	}
	public void setEndId(Long endId) {
		this.endId = endId;
	}
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="relationships")
	public Set<KRSPodmiot> getKrsPodmioty() {
		return krsPodmioty;
	}
	public void setKrsPodmioty(Set<KRSPodmiot> krsPodmioty) {
		this.krsPodmioty = krsPodmioty;
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
