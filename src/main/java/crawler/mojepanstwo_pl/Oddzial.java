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
@Table(name="oddzial")
public class Oddzial {


	private Long oddzialId;
	private String nazwa;
	private String adres;
	private Set<KRSPodmiot> krsPodmioty = new HashSet<KRSPodmiot>(0);
	public void setKrsPodmioty(Set<KRSPodmiot> krsPodmioty) {
		this.krsPodmioty = krsPodmioty;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="oddzial_id")
	public Long getOddzialId() {
		return oddzialId;
	}
	public void setOddzialId(Long oddzialId) {
		this.oddzialId = oddzialId;
	}
	@Column(columnDefinition="text")
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	@Column(columnDefinition="text")
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="oddzial")
	public Set<KRSPodmiot> getKrsPodmioty() {
		return krsPodmioty;
	}
	public void setKrspodmioty(Set<KRSPodmiot> krsPodmioty) {
		this.krsPodmioty = krsPodmioty;
	}
	
	
	@Override
	public String toString() {
		return "Oddzial [oddzialId=" + oddzialId + ", nazwa=" + nazwa + ", adres=" + adres + ", krspodmioty="
				+ krsPodmioty + "]";
	}
	public Oddzial() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
