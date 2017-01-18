package crawler.mojepanstwo_pl.entites;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="dotacje_ue")
public class DotacjeUE {
	
	private Long id;
	private Long dzialanieId;
	private Long formaPrawnaId;
	private Long beneficjentId;
	private Long osId;
	private Long mojePanstwoId;
	private Long programId;
	private String beneficjentNazwa;
	private String symbol;
	private String formaPrawnaStr;
	private Date dataRozpoczecia;
	private Date dataPodpisania;
	private Date dataUtworzeniaKsi;
	private Date dataZakonczenia;
	private String tytul;
	private String umowaNumer;
	private String projektZakonczony;
	private BigDecimal wartoscOgolem;
	private BigDecimal wartoscDofinansowaniaEU;
	private BigDecimal wartoscWydatkiKwalfikowane;
	private BigDecimal wartoscDofinansowania;
	private Date data;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(columnDefinition="mediumtext")
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	@Column(columnDefinition="mediumtext")
	public String getFormaPrawnaStr() {
		return formaPrawnaStr;
	}
	public void setFormaPrawnaStr(String formaPrawnaStr) {
		this.formaPrawnaStr = formaPrawnaStr;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataRozpoczecia() {
		return dataRozpoczecia;
	}
	public void setDataRozpoczecia(Date dataRozpoczecia) {
		this.dataRozpoczecia = dataRozpoczecia;
	}
	@Column(columnDefinition="mediumtext")
	public String getBeneficjentNazwa() {
		return beneficjentNazwa;
	}
	public void setBeneficjentNazwa(String beneficjentNazwa) {
		this.beneficjentNazwa = beneficjentNazwa;
	}
	public BigDecimal getWartoscOgolem() {
		return wartoscOgolem;
	}
	public void setWartoscOgolem(BigDecimal wartoscOgolem) {
		this.wartoscOgolem = wartoscOgolem;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataZakonczenia() {
		return dataZakonczenia;
	}
	public void setDataZakonczenia(Date dataZakonczenia) {
		this.dataZakonczenia = dataZakonczenia;
	}
	public Long getMojePanstwoId() {
		return mojePanstwoId;
	}
	public void setMojePanstwoId(Long mojePanstwoId) {
		this.mojePanstwoId = mojePanstwoId;
	}
	@Column(columnDefinition="mediumtext")
	public String getTytul() {
		return tytul;
	}
	public void setTytul(String tytul) {
		this.tytul = tytul;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataUtworzeniaKsi() {
		return dataUtworzeniaKsi;
	}
	public void setDataUtworzeniaKsi(Date dataUtworzeniaKsi) {
		this.dataUtworzeniaKsi = dataUtworzeniaKsi;
	}
	@Column(columnDefinition="mediumtext")
	public String getUmowaNumer() {
		return umowaNumer;
	}
	public void setUmowaNumer(String umowaNumer) {
		this.umowaNumer = umowaNumer;
	}
	public String getProjektZakonczony() {
		return projektZakonczony;
	}
	public void setProjektZakonczony(String projektZakonczony) {
		this.projektZakonczony = projektZakonczony;
	}
	public Long getOsId() {
		return osId;
	}
	public void setOsId(Long osId) {
		this.osId = osId;
	}
	public BigDecimal getWartoscDofinansowaniaEU() {
		return wartoscDofinansowaniaEU;
	}
	public void setWartoscDofinansowaniaEU(BigDecimal wartoscDofinansowaniaEU) {
		this.wartoscDofinansowaniaEU = wartoscDofinansowaniaEU;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataPodpisania() {
		return dataPodpisania;
	}
	public void setDataPodpisania(Date dataPodpisania) {
		this.dataPodpisania = dataPodpisania;
	}
	public BigDecimal getWartoscWydatkiKwalfikowane() {
		return wartoscWydatkiKwalfikowane;
	}
	public void setWartoscWydatkiKwalfikowane(BigDecimal wartoscWydatkiKwalfikowane) {
		this.wartoscWydatkiKwalfikowane = wartoscWydatkiKwalfikowane;
	}
	public Long getDzialanieId() {
		return dzialanieId;
	}
	public void setDzialanieId(Long dzialanieId) {
		this.dzialanieId = dzialanieId;
	}
	public Long getFormaPrawnaId() {
		return formaPrawnaId;
	}
	public void setFormaPrawnaId(Long formaPrawnaId) {
		this.formaPrawnaId = formaPrawnaId;
	}
	public Long getBeneficjentId() {
		return beneficjentId;
	}
	public void setBeneficjentId(Long beneficjentId) {
		this.beneficjentId = beneficjentId;
	}
	public BigDecimal getWartoscDofinansowania() {
		return wartoscDofinansowania;
	}
	public void setWartoscDofinansowania(BigDecimal wartoscDofinansowania) {
		this.wartoscDofinansowania = wartoscDofinansowania;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public Long getProgramId() {
		return programId;
	}
	public void setProgramId(Long programId) {
		this.programId = programId;
	}
	public DotacjeUE() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DotacjeUE [id=" + id + ", dzialanieId=" + dzialanieId + ", formaPrawnaId=" + formaPrawnaId
				+ ", beneficjentId=" + beneficjentId + ", osId=" + osId + ", mojePanstwoId=" + mojePanstwoId
				+ ", beneficjentNazwa=" + beneficjentNazwa + ", symbol=" + symbol + ", formaPrawnaStr=" + formaPrawnaStr
				+ ", dataRozpoczecia=" + dataRozpoczecia + ", dataPodpisania=" + dataPodpisania + ", dataUtworzeniaKsi="
				+ dataUtworzeniaKsi + ", dataZalozenia=" + dataZakonczenia + ", tytul=" + tytul + ", umowaNumer="
				+ umowaNumer + ", projektZakonczony=" + projektZakonczony + ", wartoscOgolem=" + wartoscOgolem
				+ ", wartoscDofinansowaniaEU=" + wartoscDofinansowaniaEU + ", wartoscWydatkiKwalfikowane="
				+ wartoscWydatkiKwalfikowane + ", wartoscDofinansowania=" + wartoscDofinansowania + ", data=" + data
				+ "]";
	}
	
	
}
