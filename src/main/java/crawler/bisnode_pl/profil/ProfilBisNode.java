package crawler.bisnode_pl.profil;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class ProfilBisNode {
	private String nazwa;
	private String lokalizacja;
	private String phone;
	private String email;
	private String website;
	private String duns;
	private String nip;
	private String regon;
	private String krs;
	private String dataRozpoczeciaDzialalnosci;
	private String dzialalnosc;
	private String formaPrawna;
	private String opis;
	private int threadId;
	private int hostId;
	private String meta;
	
	private long id;
    private Date data =new Date();
	
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	@Column(columnDefinition="mediumtext")
	public String getLokalizacja() {
		return lokalizacja;
	}
	public void setLokalizacja(String lokalizacja) {
		this.lokalizacja = lokalizacja;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getDuns() {
		return duns;
	}
	public void setDuns(String duns) {
		this.duns = duns;
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
	public String getKrs() {
		return krs;
	}
	public void setKrs(String krs) {
		this.krs = krs;
	}
	public String getDataRozpoczeciaDzialalnosci() {
		return dataRozpoczeciaDzialalnosci;
	}
	public void setDataRozpoczeciaDzialalnosci(String dataRozpoczeciaDzialalnosci) {
		this.dataRozpoczeciaDzialalnosci = dataRozpoczeciaDzialalnosci;
	}
	@Column(columnDefinition="mediumtext")
	public String getDzialalnosc() {
		return dzialalnosc;
	}
	public void setDzialalnosc(String dzialalnosc) {
		this.dzialalnosc = dzialalnosc;
	}
	public String getFormaPrawna() {
		return formaPrawna;
	}
	public void setFormaPrawna(String formaPrawna) {
		this.formaPrawna = formaPrawna;
	}
	public int getThreadId() {
		return threadId;
	}
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	public int getHostId() {
		return hostId;
	}
	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(columnDefinition="mediumtext")
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public ProfilBisNode() {
		super();
	}
	@Override
	public String toString() {
		return "ProfilBisNode [nazwa=" + nazwa + ", lokalizacja=" + lokalizacja + "\n, phone=" + phone + ", email=" + email
				+ ", website=" + website + "\n, duns=" + duns + ", nip=" + nip + ", regon=" + regon + ", krs=" + krs
				+ ", dataRozpoczeciaDzialalnosci=" + dataRozpoczeciaDzialalnosci + "\n, dzialalnosc=" + dzialalnosc
				+ ", formaPrawna=" + formaPrawna + "\n, opis=" + opis + "\n, threadId=" + threadId + ", hostId=" + hostId
				+ ", meta=" + meta + ", id=" + id + "]";
	}
	
	

}
