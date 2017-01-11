package crawler.mojepanstwo_pl;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmisjaAkcji {

	@Id
	private long id;
	private String seria;
	private long liczba;
	private String rodzajUprzywilejowania;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSeria() {
		return seria;
	}
	public void setSeria(String seria) {
		this.seria = seria;
	}
	public long getLiczba() {
		return liczba;
	}
	public void setLiczba(long liczba) {
		this.liczba = liczba;
	}
	public String getRodzajUprzywilejowania() {
		return rodzajUprzywilejowania;
	}
	public void setRodzajUprzywilejowania(String rodzajUprzywilejowania) {
		this.rodzajUprzywilejowania = rodzajUprzywilejowania;
	}
	public EmisjaAkcji(String seria, long liczba, String rodzajUprzywilejowania) {
		super();
		this.seria = seria;
		this.liczba = liczba;
		this.rodzajUprzywilejowania = rodzajUprzywilejowania;
	}
	public EmisjaAkcji() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
