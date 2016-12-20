package crawler.firmenwissen_com;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;

import org.hibernate.annotations.GenerationTime;

@Entity
public class FirmenwissenProfil {

	@Id
	@GeneratedValue
	private long id;
	private String companyName;
	@Column(columnDefinition="MEDIUMTEXT")
	private String description;
	private String street;
	private String city;
	private String zip;
	private String country;
	private String status;
	private String last_modified;
	private String phone;
	private String fax;
	private String email;
	private String website;
	private String tradeRegister;
	private String managment;
	private String creditreformNumber;
	private String registerNumber;
	private String owners;
	private String meta;
	@Column(name="data", insertable=false, updatable=false, columnDefinition="timestamp default current_timestamp")
	@org.hibernate.annotations.Generated(value=GenerationTime.INSERT)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateCreated;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
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
	public String getTradeRegister() {
		return tradeRegister;
	}
	public void setTradeRegister(String tradeRegister) {
		this.tradeRegister = tradeRegister;
	}
	public String getManagment() {
		return managment;
	}
	public void setManagment(String managment) {
		this.managment = managment;
	}
	
	public String getCreditreformNumber() {
		return creditreformNumber;
	}
	public void setCreditreformNumber(String creditreformNumber) {
		this.creditreformNumber = creditreformNumber;
	}
	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	public String getOwners() {
		return owners;
	}
	public void setOwners(String owners) {
		this.owners = owners;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public FirmenwissenProfil() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FirmenwissenProfil [id=" + id + ", companyName=" + companyName + ", description=" + description
				+ ", street=" + street + ", city=" + city + ", zip=" + zip + ", country=" + country + ", status="
				+ status + ", last_modified=" + last_modified + ", phone=" + phone + ", fax=" + fax + ", email=" + email
				+ ", website=" + website + ", tradeRegister=" + tradeRegister + ", managment=" + managment
				+ ", creditreformNumber=" + creditreformNumber + ", registerNumber=" + registerNumber + ", owners="
				+ owners + ", meta=" + meta + ", dateCreated=" + dateCreated + "]";
	}
	
	
}
