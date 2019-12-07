package com.ggl.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the member_id database table.
 * 
 */ 

// jobseeker_details
@Entity
@Table(name="jobseeker_details")
//@NamedQuery(name="JobSeeker.findAll", query="SELECT j FROM JobSeeker j")
public class JobSeekerDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int jobseeker_ID;

	@Column(name="Jobseeker_name")
	private String Jobseeker_name;

	@Column(name="Jobseeker_phoneNumber")
	private String Jobseeker_phoneNumber;

	@Column(name="Jobseeker_emailID")
	private String Jobseeker_emailID;
	
	@Column(name="Jobseeker_password")
	private String Jobseeker_password;
	
	@Column(name="Jobseeker_country")
	private String Jobseeker_country;	

	@Column(name="Jobseeker_status")
	private String Jobseeker_status;

	

	public JobSeekerDetails() {
	}



	public int getJobseeker_ID() {
		return jobseeker_ID;
	}



	public void setJobseeker_ID(int jobseeker_ID) {
		this.jobseeker_ID = jobseeker_ID;
	}



	public String getJobseeker_name() {
		return Jobseeker_name;
	}



	public void setJobseeker_name(String jobseeker_name) {
		Jobseeker_name = jobseeker_name;
	}



	public String getJobseeker_phoneNumber() {
		return Jobseeker_phoneNumber;
	}



	public void setJobseeker_phoneNumber(String jobseeker_phoneNumber) {
		Jobseeker_phoneNumber = jobseeker_phoneNumber;
	}



	public String getJobseeker_emailID() {
		return Jobseeker_emailID;
	}



	public void setJobseeker_emailID(String jobseeker_emailID) {
		Jobseeker_emailID = jobseeker_emailID;
	}



	public String getJobseeker_password() {
		return Jobseeker_password;
	}



	public void setJobseeker_password(String jobseeker_password) {
		Jobseeker_password = jobseeker_password;
	}



	public String getJobseeker_country() {
		return Jobseeker_country;
	}



	public void setJobseeker_country(String jobseeker_country) {
		Jobseeker_country = jobseeker_country;
	}



	public String getJobseeker_status() {
		return Jobseeker_status;
	}



	public void setJobseeker_status(String jobseeker_status) {
		Jobseeker_status = jobseeker_status;
	}

	

}