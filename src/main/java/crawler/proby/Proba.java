package crawler.proby;

import java.util.Date;

public class Proba {
	public String str1;
	public String str2;
	public int integerr;
	public Date data;
	public String getStr1() {
		return str1;
	}
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	public String getStr2() {
		return str2;
	}
	public void setStr2(String str2) {
		this.str2 = str2;
	}
	public int getIntegerr() {
		return integerr;
	}
	public void setIntegerr(int integerr) {
		this.integerr = integerr;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Proba() {
		super();
	}
	@Override
	public String toString() {
		return "Proba [str1=" + str1 + ", str2=" + str2 + ", integerr=" + integerr + ", data=" + data + "]";
	}
	
	

}
