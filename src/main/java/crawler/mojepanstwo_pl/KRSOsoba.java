package crawler.mojepanstwo_pl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="krsosoba")
public class KRSOsoba {
	private Long id;
	private Long mojePanstwoOsobaId;
	private Date dataUrodzenia;
	private int privacy;
	private String imiePierwsze;
	private String imieDrugie;
	private String nazwisko;
	public enum Plec {Mezczyzna, Kobieta};
	private Plec plec;
	private Set<Stanowisko> stanowiska = new HashSet<Stanowisko>();
	private Date data;
	private Set<KRSOsobaGmina> gminy = new HashSet<KRSOsobaGmina>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="osoba_id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="mojepanstwo_osoba_id")
	public Long getOsobaId() {
		return mojePanstwoOsobaId;
	}
	public void setOsobaId(Long osobaId) {
		this.mojePanstwoOsobaId = osobaId;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataUrodzenia() {
		return dataUrodzenia;
	}
	public void setDataUrodzenia(Date dataUrodzenia) {
		this.dataUrodzenia = dataUrodzenia;
	}
	public int getPrivacy() {
		return privacy;
	}
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	public String getImiePierwsze() {
		return imiePierwsze;
	}
	public void setImiePierwsze(String imiePierwsze) {
		this.imiePierwsze = imiePierwsze;
	}
	public String getImieDrugie() {
		return imieDrugie;
	}
	public void setImieDrugie(String imieDrugie) {
		this.imieDrugie = imieDrugie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	@Enumerated(EnumType.STRING)
	public Plec getPlec() {
		return plec;
	}
	public void setPlec(Plec plec) {
		this.plec = plec;
	}
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="krsosoba_stanowiska", joinColumns={@JoinColumn(name="osoba_id", nullable=false, updatable=false)}, inverseJoinColumns={
			@JoinColumn(name="stanowisko_id", nullable=false, updatable=false)
	})
	public Set<Stanowisko> getStanowiska() {
		return stanowiska;
	}
	public void setStanowiska(Set<Stanowisko> stanowiska) {
		this.stanowiska = stanowiska;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="krsosoba_krsosobagmina", joinColumns={@JoinColumn(name="osoba_id", nullable=false, updatable=false)}, inverseJoinColumns={
			@JoinColumn(name="krsosobagmina_id", nullable=false, updatable=false)
	})
	public Set<KRSOsobaGmina> getGminy() {
		return gminy;
	}
	public void setGminy(Set<KRSOsobaGmina> gminy) {
		this.gminy = gminy;
	}
	
	
}
