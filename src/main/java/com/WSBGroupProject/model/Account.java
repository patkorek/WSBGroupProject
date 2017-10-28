package com.WSBGroupProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Account {

//    @OneToMany(mappedBy = "account")
//    private Set<Bookmark> bookmarks = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

//    @JsonIgnore
    private String password;
    private String username;
    
    private String firstName;
    private String lastName;
    private String phone;
    private String dateOfBirth;
    private String city;
    private String postCode;
    private String street;
    private String houseNumber;
    
    private String companyName;
    private String nip;
    private String companyAddress;
    
    private Boolean isActivated = false;
    
    public Account(Account a) { // rest controller constructor
        this.id = a.getId();
        this.username = a.getUsername();
        this.password = a.getPassword();
        this.firstName = a.getFirstName();
        this.lastName = a.getLastName();
        this.phone = a.getPhone();
        this.dateOfBirth = a.getDateOfBirth();
        this.city = a.getCity();
        this.postCode = a.getPostCode();
        this.street = a.getPostCode();
        this.houseNumber = a.getHouseNumber();
        this.companyName = a.getCompanyName();
        this.nip = a.getNip();
        this.companyAddress = a.getCompanyAddress();
    }

    public Account() { // jpa constructor
    }

    public Account(String username, String password) { // initialization constructor
    	this.username = username;
    	this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getCity() {
		return city;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getStreet() {
		return street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Boolean getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getNip() {
		return nip;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
}
