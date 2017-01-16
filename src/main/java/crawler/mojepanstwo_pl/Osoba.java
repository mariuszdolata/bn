package crawler.mojepanstwo_pl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="osoba")
//@SecondaryTables({@SecondaryTable(name="komitet_zalozycielski"), @SecondaryTable(name="jedyny_akcjonariusz")})
@Inheritance(strategy=InheritanceType.JOINED)
public class Osoba {

	
	protected long id;
	protected String nazwa;
	protected Date dataUrodzenia;
	protected int privacyLevel;
	protected long osobaId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="osoba_id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(columnDefinition="text")
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataUrodzenia() {
		return dataUrodzenia;
	}
	public void setDataUrodzenia(Date dataUrodzenia) {
		if(dataUrodzenia!=null)this.dataUrodzenia = dataUrodzenia;
		else this.dataUrodzenia=null;
	}
	@Column(nullable=true)
	public int getPrivacyLevel() {
		return privacyLevel;
	}
	public void setPrivacyLevel(int privacyLevel) {
		this.privacyLevel = privacyLevel;
	}
	@Column(name="osobaId", nullable=true)
	public long getOsobaId() {
		return osobaId;
	}
	public void setOsobaId(long osobaId) {
		this.osobaId = osobaId;
	}
	@Override
	public String toString() {
		return "Osoba [id=" + id + ", nazwa=" + nazwa + ", dataUrodzenia=" + dataUrodzenia + ", privacyLevel="
				+ privacyLevel + ", osobaId=" + osobaId + "]";
	}
	public Osoba() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Osoba(long id, String nazwa, Date dataUrodzenia, int privacyLevel, long osobaId) {
		super();
		this.id = id;
		this.nazwa = nazwa;
		this.dataUrodzenia = dataUrodzenia;
		this.privacyLevel = privacyLevel;
		this.osobaId = osobaId;
	}
	
	
	
}
