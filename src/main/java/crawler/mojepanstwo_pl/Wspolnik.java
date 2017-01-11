package crawler.mojepanstwo_pl;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Wspolnik extends Reprezentacja{

	protected long krsId;
	protected long wspolnikId;
	protected int udzialyLiczba;
	protected BigDecimal udzialyWartoscJedn;
	protected BigDecimal udzialyWartosc;
	public long getKrsId() {
		return krsId;
	}
	public void setKrsId(long krsId) {
		this.krsId = krsId;
	}
	public long getWspolnikId() {
		return wspolnikId;
	}
	public void setWspolnikId(long wspolnikId) {
		this.wspolnikId = wspolnikId;
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
	@Override
	public String toString() {
		return "Wspolnik [krsId=" + krsId + ", wspolnikId=" + wspolnikId + ", udzialyLiczba=" + udzialyLiczba
				+ ", udzialyWartoscJedn=" + udzialyWartoscJedn + ", udzialyWartosc=" + udzialyWartosc + "]";
	}
	public Wspolnik(long id, String nazwa, Date dataUrodzenia, int privacyLevel, long osobaId) {
		super(id, nazwa, dataUrodzenia, privacyLevel, osobaId);
		// TODO Auto-generated constructor stub
	}
	public Wspolnik() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	
}
