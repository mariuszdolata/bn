package dbstructure;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Powiat {
	@Id
	private long idPowiat;
	private long globalId;
	private int typId;
	private int wojewodztwoId;
	private String nts;
	private String nazwa;
	private int senatOkregId;
	private int sejmOkregId;
	public long getIdPowiat() {
		return idPowiat;
	}
	public void setIdPowiat(long idPowiat) {
		this.idPowiat = idPowiat;
	}
	public long getGlobalId() {
		return globalId;
	}
	public void setGlobalId(long globalId) {
		this.globalId = globalId;
	}
	public int getTypId() {
		return typId;
	}
	public void setTypId(int typId) {
		this.typId = typId;
	}
	public int getWojewodztwoId() {
		return wojewodztwoId;
	}
	public void setWojewodztwoId(int wojewodztwoId) {
		this.wojewodztwoId = wojewodztwoId;
	}
	public String getNts() {
		return nts;
	}
	public void setNts(String nts) {
		this.nts = nts;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public int getSenatOkregId() {
		return senatOkregId;
	}
	public void setSenatOkregId(int senatOkregId) {
		this.senatOkregId = senatOkregId;
	}
	public int getSejmOkregId() {
		return sejmOkregId;
	}
	public void setSejmOkregId(int sejmOkregId) {
		this.sejmOkregId = sejmOkregId;
	}
	public Powiat() {
	}
	@Override
	public String toString() {
		return "Powiat [idPowiat=" + idPowiat + ", globalId=" + globalId + ", typId=" + typId + ", wojewodztwoId="
				+ wojewodztwoId + ", nts=" + nts + ", nazwa=" + nazwa + ", senatOkregId=" + senatOkregId
				+ ", sejmOkregId=" + sejmOkregId + "]";
	}
	
	

}
