package com.ggl.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the organization_list database table.
 * 
 */
@Entity
@Table(name="organization_list")
@NamedQuery(name="OrganizationList.findAll", query="SELECT o FROM OrganizationList o")
public class OrganizationList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int org_ID;

	@Column(name="category")
	private String category;

	@Column(name="country_name")
	private String countryName;

	
	@Column(name="discount")
	private int discount;

	@Column(name="name")
	private String name;

	@Column(name="state_name")
	private String stateName;
	
	@Column(name="imagePath")
	private String imagePath;

	@Column(name="description")
	private String description;

	@Column(name="phoneNumber")
	private String phoneNumber;
	
	@Column(name="emailID")
	private String emailID;
	
	@Column(name="price")
	private String price;
	
	public OrganizationList() {
	}

	public int getOrg_ID() {
		return this.org_ID;
	}

	public void setOrg_ID(int org_ID) {
		this.org_ID = org_ID;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getDiscount() {
		return this.discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	
}