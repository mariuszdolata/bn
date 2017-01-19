package crawler.mojepanstwo_pl.entites;

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
@Table(name="patenty")
public class Patenty {

	private Long id;
	private Long dokumentId;
	private Long uprawnionyId;
	private Long mojePanstwoId;
	private String uprawniony;
	private String nazwa;
	private String miasto;
	private String kraj;
	private String espacenetUrl;
	private String img;
	private String pierwszenstwo;
	private String pelnomocnik;
	private String tytul;
	private String ogloszenie;
	private String opis;
	private String registerUrl;
	private String kod;
	private String tworcy;
	private Long nrStr;
	private Date dataMojePanstwo;
	private Date dataZgloszeniaEU;
	private Date dataOgloszenia;
	private Date data;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDokumentId() {
		return dokumentId;
	}
	public void setDokumentId(Long dokumentId) {
		this.dokumentId = dokumentId;
	}
	public Long getUprawnionyId() {
		return uprawnionyId;
	}
	public void setUprawnionyId(Long uprawnionyId) {
		this.uprawnionyId = uprawnionyId;
	}
	public Long getMojePanstwoId() {
		return mojePanstwoId;
	}
	public void setMojePanstwoId(Long mojePanstwoId) {
		this.mojePanstwoId = mojePanstwoId;
	}
	@Column(columnDefinition="mediumtext")
	public String getUprawniony() {
		return uprawniony;
	}
	public void setUprawniony(String uprawniony) {
		this.uprawniony = uprawniony;
	}
	@Column(columnDefinition="mediumtext")
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	@Column(columnDefinition="mediumtext")
	public String getMiasto() {
		return miasto;
	}
	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}
	public String getKraj() {
		return kraj;
	}
	public void setKraj(String kraj) {
		this.kraj = kraj;
	}
	@Column(columnDefinition="mediumtext")
	public String getEspacenetUrl() {
		return espacenetUrl;
	}
	public void setEspacenetUrl(String espacenetUrl) {
		this.espacenetUrl = espacenetUrl;
	}
	@Column(columnDefinition="mediumtext")
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Column(columnDefinition="mediumtext")
	public String getPierwszenstwo() {
		return pierwszenstwo;
	}
	public void setPierwszenstwo(String pierwszenstwo) {
		this.pierwszenstwo = pierwszenstwo;
	}
	@Column(columnDefinition="mediumtext")
	public String getPelnomocnik() {
		return pelnomocnik;
	}
	public void setPelnomocnik(String pelnomocnik) {
		this.pelnomocnik = pelnomocnik;
	}
	@Column(columnDefinition="mediumtext")
	public String getTytul() {
		return tytul;
	}
	public void setTytul(String tytul) {
		this.tytul = tytul;
	}
	@Column(columnDefinition="mediumtext")
	public String getOgloszenie() {
		return ogloszenie;
	}
	public void setOgloszenie(String ogloszenie) {
		this.ogloszenie = ogloszenie;
	}
	@Column(columnDefinition="mediumtext")
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	@Column(columnDefinition="mediumtext")
	public String getRegisterUrl() {
		return registerUrl;
	}
	public void setRegisterUrl(String registerUrl) {
		this.registerUrl = registerUrl;
	}
	public String getKod() {
		return kod;
	}
	public void setKod(String kod) {
		this.kod = kod;
	}
	@Column(columnDefinition="mediumtext")
	public String getTworcy() {
		return tworcy;
	}
	public void setTworcy(String tworcy) {
		this.tworcy = tworcy;
	}
	public Long getNrStr() {
		return nrStr;
	}
	public void setNrStr(Long nrStr) {
		this.nrStr = nrStr;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataZgloszeniaEU() {
		return dataZgloszeniaEU;
	}
	public void setDataZgloszeniaEU(Date dataZgloszeniaEU) {
		this.dataZgloszeniaEU = dataZgloszeniaEU;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataOgloszenia() {
		return dataOgloszenia;
	}
	public void setDataOgloszenia(Date dataOgloszenia) {
		this.dataOgloszenia = dataOgloszenia;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataMojePanstwo() {
		return dataMojePanstwo;
	}
	public void setDataMojePanstwo(Date dataMojePanstwo) {
		this.dataMojePanstwo = dataMojePanstwo;
	}
	public Patenty() {
		super();
	}
	@Override
	public String toString() {
		return "Patenty [id=" + id + ", dokumentId=" + dokumentId + ", uprawnionyId=" + uprawnionyId
				+ ", mojePanstwoId=" + mojePanstwoId + ", uprawniony=" + uprawniony + ", nazwa=" + nazwa + ", miasto="
				+ miasto + ", kraj=" + kraj + ", espacenetUrl=" + espacenetUrl + ", img=" + img + ", pierwszenstwo="
				+ pierwszenstwo + ", pelnomocnik=" + pelnomocnik + ", tytul=" + tytul + ", ogloszenie=" + ogloszenie
				+ ", opis=" + opis + ", registerUrl=" + registerUrl + ", kod=" + kod + ", tworcy=" + tworcy + ", nrStr="
				+ nrStr + ", dataZgloszeniaEU=" + dataZgloszeniaEU + ", dataOgloszenia=" + dataOgloszenia + ", data="
				+ data + "]";
	}
	
	
}
