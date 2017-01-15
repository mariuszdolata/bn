package crawler.mojepanstwo_pl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="reprezentacja")
public class Reprezentacja extends Osoba{

	protected String funkcja;

	public String getFunkcja() {
		return funkcja;
	}

	public void setFunkcja(String funkcja) {
		this.funkcja = funkcja;
	}

	@Override
	public String toString() {
		return "Reprezentacja [funkcja=" + funkcja + "]";
	}

	public Reprezentacja(String funkcja) {
		super();
		this.funkcja = funkcja;
	}

	public Reprezentacja() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reprezentacja(long id, String nazwa, Date dataUrodzenia, int privacyLevel, long osobaId) {
		super(id, nazwa, dataUrodzenia, privacyLevel, osobaId);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
