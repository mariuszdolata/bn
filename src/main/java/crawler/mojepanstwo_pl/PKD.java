package crawler.mojepanstwo_pl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

@Entity
public class PKD {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pkd_id")
	private long id;
	private String kod;
	private String nazwa;
	private Boolean przewazajaca;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "krspodmiot_pkd", joinColumns = @JoinColumn(name = "pkd_id"), inverseJoinColumns = @JoinColumn(name = "id_KRSPodmiot"))
	private Set<KRSPodmiot> kRSPodmioty = new HashSet<KRSPodmiot>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
		return "PKD [id="+", kod=" + kod + ", nazwa=" + nazwa + ", przewazajaca=" + przewazajaca
				+ ", kRSPodmioty=" + kRSPodmioty + "]";
	}

}
