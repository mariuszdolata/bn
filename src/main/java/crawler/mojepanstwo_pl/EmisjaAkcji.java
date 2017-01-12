package crawler.mojepanstwo_pl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="emisjaakcji")
public class EmisjaAkcji {

	
	private Long id;
	private String seria;
	private Long liczba;
	private String rodzajUprzywilejowania;
	private Set<KRSPodmiot> kRSPodmioty = new HashSet<KRSPodmiot>(0);
	
	
	
	public EmisjaAkcji(String seria, long liczba, String rodzajUprzywilejowania) {
		super();
		this.seria = seria;
		this.liczba = liczba;
		this.rodzajUprzywilejowania = rodzajUprzywilejowania;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="emisjeakcji_id", unique=true, updatable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSeria() {
		return seria;
	}
	public void setSeria(String seria) {
		this.seria = seria;
	}
	public Long getLiczba() {
		return liczba;
	}
	public void setLiczba(Long liczba) {
		this.liczba = liczba;
	}
	public String getRodzajUprzywilejowania() {
		return rodzajUprzywilejowania;
	}
	public void setRodzajUprzywilejowania(String rodzajUprzywilejowania) {
		this.rodzajUprzywilejowania = rodzajUprzywilejowania;
	}
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="emisjeAkcji")
	public Set<KRSPodmiot> getkRSPodmioty() {
		return kRSPodmioty;
	}
	public void setkRSPodmioty(Set<KRSPodmiot> kRSPodmioty) {
		this.kRSPodmioty = kRSPodmioty;
	}
	public EmisjaAkcji() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
