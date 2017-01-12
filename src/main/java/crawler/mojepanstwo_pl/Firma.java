package crawler.mojepanstwo_pl;

import java.math.BigDecimal;
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
@Table(name="firma")
public class Firma {

	private Long idFirma;
	private String nazwa;
	private String udzialyStr;
	private int udzialyStatus;
	private int udzialyLiczba;
	private BigDecimal udzialyWartoscJedn;
	private BigDecimal udzialyWartosc;
	private Set<KRSPodmiot> krsPodmiot = new HashSet<KRSPodmiot>(0);
	private String krs;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="firma_id", unique=true, nullable=false)
	public Long getIdFirma() {
		return idFirma;
	}
	public void setIdFirma(Long idFirma) {
		this.idFirma = idFirma;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getUdzialyStr() {
		return udzialyStr;
	}
	public void setUdzialyStr(String udzialyStr) {
		this.udzialyStr = udzialyStr;
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
	
	public String getKrs() {
		return krs;
	}
	public void setKrs(String krs) {
		this.krs = krs;
	}
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="firmy")
	public Set<KRSPodmiot> getKrsPodmiot() {
		return krsPodmiot;
	}
	public void setKrsPodmiot(Set<KRSPodmiot> krsPodmiot) {
		this.krsPodmiot = krsPodmiot;
	}
	
	@Override
	public String toString() {
		return "Firma [idFirma=" + idFirma + ", nazwa=" + nazwa + ", udzielyStr=" + udzialyStr + ", udzialyStatus="
				+ udzialyStatus + ", udzialyLiczba=" + udzialyLiczba + ", udzialyWartoscJedn=" + udzialyWartoscJedn
				+ ", udzialyWartosc=" + udzialyWartosc + ", krsPodmiot=" + krsPodmiot + "]";
	}
	public Firma(long idFirma, String nazwa, String udzielyStr, int udzialyStatus, int udzialyLiczba,
			BigDecimal udzialyWartoscJedn, BigDecimal udzialyWartosc) {
		super();
		this.idFirma = idFirma;
		this.nazwa = nazwa;
		this.udzialyStr = udzielyStr;
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
