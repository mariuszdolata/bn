package crawler.mojepanstwo_pl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="pkd")
public class PKD {
	
	private Long id;
	private String kod;
	private String nazwa;
	private Boolean przewazajaca;
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "krspodmiot_pkd", joinColumns = @JoinColumn(name = "pkd_id"), inverseJoinColumns = @JoinColumn(name = "idKRSPodmiot"))
	private Set<KRSPodmiot> kRSPodmioty = new HashSet<KRSPodmiot>(0);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pkd_id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKod() {
		return kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public Boolean getPrzewazajaca() {
		return przewazajaca;
	}

	public void setPrzewazajaca(Boolean przewazajaca) {
		this.przewazajaca = przewazajaca;
	}

	@ManyToMany(fetch=FetchType.LAZY, mappedBy="dzialalnosci")
	public Set<KRSPodmiot> getkRSPodmioty() {
		return kRSPodmioty;
	}

	public void setkRSPodmioty(Set<KRSPodmiot> kRSPodmioty) {
		this.kRSPodmioty = kRSPodmioty;
	}

	public PKD() {

	}

	@Override
	public String toString() {
		return "PKD [id=" + id + ", kod=" + kod + ", nazwa=" + nazwa + ", przewazajaca=" + przewazajaca
				+ ", kRSPodmioty=" + kRSPodmioty + "]";
	}

}
