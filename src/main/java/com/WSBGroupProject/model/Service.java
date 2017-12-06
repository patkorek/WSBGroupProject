package com.WSBGroupProject.model;

import com.WSBGroupProject.constants.Constants;
import com.WSBGroupProject.dto.ServiceForm;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Service {

    @Id
    @GeneratedValue
    private Long id;
    private Integer type;
    private Integer status;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="provider_id")
    private Account provider;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="recipient_id")
    private Account recipient;

    private String category;
    private String name;    
    private String amount;
    private String description;
    private String city;
    private String postCode;
    private String street;
    private String houseNumber;
    private String dateTime;
    
    
    public Service(ServiceForm sf, Account poster) { // rest controller constructor
        this.id = sf.getId();
        this.type = sf.getType();
        this.category = sf.getCategory();
        this.name = sf.getName();
        this.amount = sf.getAmount().replace(",", ".").replaceAll("[^\\d\\.]", "");
        this.description = sf.getDescription();
        this.city = sf.getCity();
        this.postCode = sf.getPostCode();
        this.street = sf.getStreet();
        this.houseNumber = sf.getHouseNumber();
        this.dateTime = sf.getDateTime();
        if (poster != null) {
	        if (sf.getType() == Constants.SERVICE_PROVIDER) {
	        	this.provider = poster;
	        }
	        else if (sf.getType() == Constants.SERVICE_RECIPIENT) {
	        	this.recipient = poster;
	        }
        }
    }

    public Service() { // jpa constructor
    }

    public Long getId() {
        return id;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Account getProvider() {
		return provider;
	}

	public void setProvider(Account provider) {
		this.provider = provider;
	}

	public Account getRecipient() {
		return recipient;
	}

	public void setRecipient(Account recipient) {
		this.recipient = recipient;
	}
}
