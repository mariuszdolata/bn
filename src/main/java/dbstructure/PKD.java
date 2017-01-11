package dbstructure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PKD {

	@Id
	private long idPKD;
	private String dzial;
	private String grupa;
	private String kod;
	private String nazwa;
	private String pkdUrl;
	private String sekcja;
	public long getId() {
		return idPKD;
	}
	public void setId(long id) {
		this.idPKD = id;
	}
	public String getDzial() {
		return dzial;
	}
	public void setDzial(String dzial) {
		this.dzial = dzial;
	}
	public String getGrupa() {
		return grupa;
	}
	public void setGrupa(String grupa) {
		this.grupa = grupa;
	}
	public String getKod() {
		return kod;
	}
	public void setKod(String kod) {
		this.kod = kod;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getPkdUrl() {
		return pkdUrl;
	}
	public void setPkdUrl(String pkdUrl) {
		this.pkdUrl = pkdUrl;
	}
	public String getSekcja() {
		return sekcja;
	}
	public void setSekcja(String sekcja) {
		this.sekcja = sekcja;
	}
	@Override
	public String toString() {
		return "PKD [idPKD=" + idPKD + ", dzial=" + dzial + ", grupa=" + grupa + ", kod=" + kod + ", nazwa=" + nazwa
				+ ", pkdUrl=" + pkdUrl + ", sekcja=" + sekcja + "]";
	}
	public PKD() {
	}
	
	
}
