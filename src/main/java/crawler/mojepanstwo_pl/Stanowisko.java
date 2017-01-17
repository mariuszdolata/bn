package crawler.mojepanstwo_pl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="stanowisko")
public class Stanowisko {

	private Long id;
	private String stanowisko;
	private String nazwaFirmy;
	private String krs;
	private Set<KRSOsoba> osoba = new HashSet<KRSOsoba>(0);
	private Date data;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStanowisko() {
		return stanowisko;
	}
	public void setStanowisko(String stanowisko) {
		this.stanowisko = stanowisko;
	}
	public String getNazwaFirmy() {
		return nazwaFirmy;
	}
	public void setNazwaFirmy(String nazwaFirmy) {
		this.nazwaFirmy = nazwaFirmy;
	}
	public String getKrs() {
		return krs;
	}
	public void setKrs(String krs) {
		this.krs = krs;
	}
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="stanowiska")
	public Set<KRSOsoba> getOsoba() {
		return osoba;
	}
	public void setOsoba(Set<KRSOsoba> osoba) {
		this.osoba = osoba;
	}
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
