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
@Table(name="osoba_gmina")
public class KRSOsobaGmina {

	private Long osobaGminaId;
	private Long osobaGminaMojePanstwo;
	private Set<KRSOsoba> osoba = new HashSet<KRSOsoba>(0);
	private Date data;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getOsobaGminaId() {
		return osobaGminaId;
	}
	public void setOsobaGminaId(Long osobaGminaId) {
		this.osobaGminaId = osobaGminaId;
	}
	public Long getOsobaGminaMojePanstwo() {
		return osobaGminaMojePanstwo;
	}
	public void setOsobaGminaMojePanstwo(Long osobaGminaMojePanstwo) {
		this.osobaGminaMojePanstwo = osobaGminaMojePanstwo;
	}
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="gminy")
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
