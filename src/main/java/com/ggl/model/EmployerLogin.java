package com.ggl.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user_login database table.
 * 
 */
@Entity
@Table(name="employer_login")
@NamedQuery(name="EmployerLogin.findAll", query="SELECT e FROM EmployerLogin e")
public class EmployerLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int employer_Login_ID;

	@Column(name="username")
	private String username;

	@Column(name="password")
	private String password;

	@Column(name="status")
	private String status;

	@Column(name="user_role")
	private String userRole;

	@Column(name="user_otp")
	private String userOtp;

	@Temporal(TemporalType.DATE)
	@Column(name="last_login")
	private Date lastLogin;
	
	
	//bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy="employerLogin",cascade = CascadeType.ALL)
	private List<EmployerDetails> employerDetails;

	/*//bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy="userbooking",cascade = CascadeType.ALL)
	private List<BookingDetail> bookingdetail;*/

		
	
	public EmployerLogin() {
	}

	/*public int getUser_Login_ID() {
		return this.user_Login_ID;
	}

	public void setUser_Login_ID(int user_Login_ID) {
		this.user_Login_ID = user_Login_ID;
	}*/

	
	
	public Date getLastLogin() {
		return this.lastLogin;
	}

	public int getEmployer_Login_ID() {
		return employer_Login_ID;
	}

	public void setEmployer_Login_ID(int employer_Login_ID) {
		this.employer_Login_ID = employer_Login_ID;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserOtp() {
		return this.userOtp;
	}

	public void setUserOtp(String userOtp) {
		this.userOtp = userOtp;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<EmployerDetails> getEmployerDetails() {
		return this.employerDetails;
	}

	public void setEmployerDetails(List<EmployerDetails> employerDetails) {
		this.employerDetails = employerDetails;
	}
	
	public EmployerDetails addEmployerDetails(EmployerDetails employerDetail) {
		getEmployerDetails().add(employerDetail);
		employerDetail.setEmployerLogin(this);

		return employerDetail;
	}

	public EmployerDetails removeEmployerDetails(EmployerDetails employerDetail) {
		getEmployerDetails().remove(employerDetail);
		employerDetail.setEmployerLogin(null);

		return employerDetail;
	}

	/*public List<BookingDetail> getBookingdetail() {
		return bookingdetail;
	}

	public void setBookingdetail(List<BookingDetail> bookingdetail) {
		this.bookingdetail = bookingdetail;
	}*/
	
	// Booking details table 
	
	

}