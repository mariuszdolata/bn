package crawler.znanefirmy_com;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class ZnanefirmyProfil {
	@Id
	@GeneratedValue
	private long id;
	private String nazwa;
	private String wlasciciel;
	private String nip;
	private String regon;
	private String dataZalozenia;
	private String wiek;
	private String wojewodztwo;
	private String miasto;
	private String kodPocztowy;
	private String ulica;
	private String pkd_main;
	private String pkd2;
	private String meta;
	private String gps;
	private Date dateCreated;
	
	
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getPkd_main() {
		return pkd_main;
	}
	public void setPkd_main(String pkd_main) {
		this.pkd_main = pkd_main;
	}
	public String getPkd2() {
		return pkd2;
	}
	public void setPkd2(String pkd2) {
		this.pkd2 = pkd2;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getWlasciciel() {
		return wlasciciel;
	}
	public void setWlasciciel(String wlasciciel) {
		this.wlasciciel = wlasciciel;
	}
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public String getRegon() {
		return regon;
	}
	public void setRegon(String regon) {
		this.regon = regon;
	}
	public String getDataZalozenia() {
		return dataZalozenia;
	}
	public void setDataZalozenia(String dataZalozenia) {
		this.dataZalozenia = dataZalozenia;
	}
	public String getWiek() {
		return wiek;
	}
	public void setWiek(String wiek) {
		this.wiek = wiek;
	}
	public String getWojewodztwo() {
		return wojewodztwo;
	}
	public void setWojewodztwo(String wojewodztwo) {
		this.wojewodztwo = wojewodztwo;
	}
	public String getMiasto() {
		return miasto;
	}
	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}
	public String getKodPocztowy() {
		return kodPocztowy;
	}
	public void setKodPocztowy(String kodPocztowy) {
		this.kodPocztowy = kodPocztowy;
	}
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public ZnanefirmyProfil() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ZnanefirmyProfil [id=" + id + ", nazwa=" + nazwa + ", wlasciciel=" + wlasciciel + ", nip=" + nip
				+ ", regon=" + regon + ", dataZalozenia=" + dataZalozenia + ", wiek=" + wiek + ", wojewodztwo="
				+ wojewodztwo + ", miasto=" + miasto + ", kodPocztowy=" + kodPocztowy + ", ulica=" + ulica
				+ ", pkd_main=" + pkd_main + ", pkd2=" + pkd2 + ", meta=" + meta + ", gps=" + gps +  ", dateCreated=" + dateCreated + "]";
	}
	
	
	

}
