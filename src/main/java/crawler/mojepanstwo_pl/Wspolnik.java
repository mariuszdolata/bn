package crawler.mojepanstwo_pl;

import java.math.BigDecimal;
import java.util.Date;
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
@Table(name = "wspolnicy")
public class Wspolnik extends Osoba {

	protected Long krsId;
	protected Long wspolnikId;
	protected int udzialyLiczba;
	protected String funkcja;
	protected BigDecimal udzialyWartoscJedn;
	protected BigDecimal udzialyWartosc;
//	private Set<KRSPodmiot> krspodmioty = new HashSet<KRSPodmiot>(0);

	public Long getKrsId() {
		return krsId;
	}

	public void setKrsId(Long krsId) {
		this.krsId = krsId;
	}

	
	public Long getWspolnikId() {
		return wspolnikId;
	}

	public void setWspolnikId(Long wspolnikId) {
		this.wspolnikId = wspolnikId;
	}

	@Column(columnDefinition="text")
	public String getFunkcja() {
		return funkcja;
	}

	public void setFunkcja(String funkcja) {
		this.funkcja = funkcja;
	}

	public int getUdzialyLiczba() {
		return udzialyLiczba;
	}

	public void setUdzialyLiczba(int udzialyLiczba) {
		this.udzialyLiczba = udzialyLiczba;
	}

	public BigDecimal getUdzialyWartoscJedn() {
		return udzialyWartoscJedn;
	}

	public void setUdzialyWartoscJedn(BigDecimal udzialyWartoscJedn) {
		this.udzialyWartoscJedn = udzialyWartoscJedn;
	}

	public BigDecimal getUdzialyWartosc() {
		return udzialyWartosc;
	}

	public void setUdzialyWartosc(BigDecimal udzialyWartosc) {
		this.udzialyWartosc = udzialyWartosc;
	}


	public Wspolnik(long id, String nazwa, Date dataUrodzenia, int privacyLevel, long osobaId) {
		super(id, nazwa, dataUrodzenia, privacyLevel, osobaId);
		// TODO Auto-generated constructor stub
	}

	
	

	@Override
	public String toString() {
		return "Wspolnik [krsId=" + krsId + ", udzialyLiczba=" + udzialyLiczba + ", funkcja=" + funkcja
				+ ", udzialyWartoscJedn=" + udzialyWartoscJedn + ", udzialyWartosc=" + udzialyWartosc + ", id=" + id
				+ ", nazwa=" + nazwa + ", dataUrodzenia=" + dataUrodzenia + ", privacyLevel=" + privacyLevel
				+ ", osobaId=" + osobaId + "]";
	}

	public Wspolnik() {
		super();
		// TODO Auto-generated constructor stub
	}

}
