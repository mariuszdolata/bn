package crawler.mojepanstwo_pl;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Firma {

	@Id
	private long id;
	private long idFirma;
	private String nazwa;
	private String udzielyStr;
	private int udzialyStatus;
	private int udzialyLiczba;
	private BigDecimal udzialyWartoscJedn;
	private BigDecimal udzialyWartosc;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdFirma() {
		return idFirma;
	}
	public void setIdFirma(long idFirma) {
		this.idFirma = idFirma;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getUdzielyStr() {
		return udzielyStr;
	}
	public void setUdzielyStr(String udzielyStr) {
		this.udzielyStr = udzielyStr;
	}
	public int getUdzialyStatus() {
		return udzialyStatus;
	}
	public void setUdzialyStatus(int udzialyStatus) {
		this.udzialyStatus = udzialyStatus;
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
		return "Firma [id=" + id + ", idFirma=" + idFirma + ", nazwa=" + nazwa + ", udzielyStr=" + udzielyStr
				+ ", udzialyStatus=" + udzialyStatus + ", udzialyLiczba=" + udzialyLiczba + ", udzialyWartoscJedn="
				+ udzialyWartoscJedn + ", udzialyWartosc=" + udzialyWartosc + "]";
	}
	public Firma(long idFirma, String nazwa, String udzielyStr, int udzialyStatus, int udzialyLiczba,
			BigDecimal udzialyWartoscJedn, BigDecimal udzialyWartosc) {
		super();
		this.idFirma = idFirma;
		this.nazwa = nazwa;
		this.udzielyStr = udzielyStr;
		this.udzialyStatus = udzialyStatus;
		this.udzialyLiczba = udzialyLiczba;
		this.udzialyWartoscJedn = udzialyWartoscJedn;
		this.udzialyWartosc = udzialyWartosc;
	}
	public Firma() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
