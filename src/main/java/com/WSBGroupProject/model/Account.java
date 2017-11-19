package com.WSBGroupProject.model;

import com.WSBGroupProject.dto.UserForm;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private Integer type;

    private String username;
//    @JsonIgnore
    private String password;
    
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
    
//    @JsonIgnore
    private Boolean isActivated = false;
//    @JsonIgnore
    private String hashLink;
    
    public Account(UserForm uf) { // rest controller constructor
        this.id = uf.getId();
        this.type = uf.getType();
        this.username = uf.getUsername()!=null ? uf.getUsername() : "";
        this.password = uf.getPassword();
        this.firstName = uf.getFirstName();
        this.lastName = uf.getLastName();
        this.phone = uf.getPhone()!=null ? uf.getPhone().replaceAll("\\D", "") : null;
        this.dateOfBirth = uf.getDateOfBirth();
        this.city = uf.getCity();
        this.postCode = uf.getPostCode();
        this.street = uf.getStreet();
        this.houseNumber = uf.getHouseNumber();
        this.companyName = uf.getCompanyName();
        this.nip = uf.getNip()!=null ? uf.getNip().replaceAll("\\D", "") : null;
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

	public void setUsername(String username) {
		this.username = username;
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

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHashLink() {
		return hashLink;
	}

	public void setHashLink(String hashLink) {
		this.hashLink = hashLink;
	}
}
