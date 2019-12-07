package com.ggl.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the category_details database table.
 * 
 */ 

// category_details
@Entity
@Table(name="category_details")
@NamedQuery(name="CategoryDetails.findAll", query="SELECT c FROM CategoryDetails c")
public class CategoryDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int category_ID;

	@Column(name="category_name")
	private String categoryName;

	@Column(name="description")
	private String description;

	@Column(name="country_name")
	private String countryName;
	
	@Column(name="state_name")
	private String stateName;
	
	@Column(name="category_code")
	private String categoryCode;

	public CategoryDetails() {
	}

	public int getCategory_ID() {
		return category_ID;
	}

	public void setCategory_ID(int category_ID) {
		this.category_ID = category_ID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

}