package models;

public class CustomerData {
	private String prescription;
	private String firstName;
	private String name;
	private String regNumber;
	private String emdNumber;
	private String addressOrNumber;
	private String cipher;
	
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public String getAddressOrNumber() {
		return addressOrNumber;
	}
	public void setAddressOrNumber(String addressOrNumber) {
		this.addressOrNumber = addressOrNumber;
	}
	public String getCipher() {
		return cipher;
	}
	public void setCipher(String cipher) {
		this.cipher = cipher;
	}
	public String getEmdNumber() {
		return emdNumber;
	}
	public void setEmdNumber(String emdNumber) {
		this.emdNumber = emdNumber;
	}
	
}
