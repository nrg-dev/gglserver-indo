package com.ggl.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the booking_details database table.
 * 
 */
@Entity
@Table(name="booking_details")
@NamedQuery(name="BookingDetail.findAll", query="SELECT b FROM BookingDetail b")
public class BookingDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int booking_ID;
	
	@Column(name="country_name")
	private String countryName;
	
	@Column(name="state_name")
	private String stateName;

	@Column(name="city_name")
	private String cityName;
	
	@Column(name="industry_name")
	private String industryName;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="person_count")
	private String personCount;
	
	@Column(name="booking_date")
	private Date bookingDate;
	
	@Column(name="booking_time")
	private String bookingTime;

	@Column(name="booking_status")
	private String bookingStatus;

	@Column(name="invoice_number")
	private String invoiceNumber;

	@Column(name="member_ID")
	private String member_ID;

	@Column(name="noofrooms")
	private int noofrooms;
	
	@Column(name="noofchild")
	private int noofchild;
	
	@Column(name="noofadult")
	private int noofadult;
	
	@Column(name="ImagePath")
	private String ImagePath;
	
	@Column(name="price")
	private String price;

	@Column(name="address")
	private String address;
	
	@Column(name="contactNumber")
	private String contactNumber;
	
	@Column(name="emailID")
	private String emailID;

	
	@Column(name="acctCreated_date")
	private java.sql.Date acctCreated_date;
	
	//bi-directional many-to-one association to UserLogin
	@ManyToOne
	@JoinColumn(name="User_Login_ID")
	private UserLogin userbooking;
		

	public BookingDetail() {
	}

	public int getBooking_ID() {
		return this.booking_ID;
	}

	public void setBooking_ID(int booking_ID) {
		this.booking_ID = booking_ID;
	}

	
	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBookingStatus() {
		return this.bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getBookingTime() {
		return this.bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getIndustryName() {
		return this.industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getMember_ID() {
		return this.member_ID;
	}

	public void setMember_ID(String member_ID) {
		this.member_ID = member_ID;
	}

	public String getPersonCount() {
		return this.personCount;
	}

	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	
	
	
	public int getNoofrooms() {
		return noofrooms;
	}

	public void setNoofrooms(int noofrooms) {
		this.noofrooms = noofrooms;
	}

	public int getNoofchild() {
		return noofchild;
	}

	public void setNoofchild(int noofchild) {
		this.noofchild = noofchild;
	}

	public int getNoofadult() {
		return noofadult;
	}

	public void setNoofadult(int noofadult) {
		this.noofadult = noofadult;
	}
	
	

	

	/*public int getUser_Login_ID() {
		return this.user_Login_ID;
	}

	public void setUser_Login_ID(int user_Login_ID) {
		this.user_Login_ID = user_Login_ID;
	}*/
	public UserLogin getUserLogin() {
		return this.userbooking;
	}
	
	
	public void setUserLogin(UserLogin userbooking) {
		this.userbooking = userbooking;
	}
	
	
	
	//added newly
	
	@Column(name="No_of_tables")
	int noofTables;
	
	@Column(name="air_name")
	String airname;
	
	@Column(name="departure_date")
	Date departure;
	
	@Column(name="return_date")
	Date returndate;
	
	@Column(name="From_place")
	String fromplace;
	
	@Column(name="To_place")
	String toplace;
	
	@Column(name="Triptype")
	String triptype;
	
	@Column(name="noofpax")
	private int noofpax;

	@Column(name="arrival_date")
	Date arrivaldate;
	
	@Column(name="Visiting_country")
	private String visitcountry;
	
	@Column(name="category")
	private String category;
	
	@Column(name="departure_name")
	private String departurename;
	
	@Column(name="Appoinment_date")
	Date appointmentdate;
	

	@Column(name="University")
	String university;
	
	@Column(name="Study_name")
	String study;
	
	@Column(name="Year_of_study")
	String yearofstudy;
	
	@Column(name="Category_insurance")
	String categoryinsurance;
	
	@Column(name="Company_insurance")
	String companyinsurance;
	
	@Column(name="Hospital_name")
	String hospitalname;
	
	@Column(name="Kind_of_Treatment")
	String treatment;
	
	@Column(name="Category_Product")
	String categoryproduct;
	
	@Column(name="List_Product")
	String listproduct;
	
	@Column(name="Quantity")
	double quantity;

	@Column(name="bookingCode")
	String bookingCode;
	
	public String getAirname() {
		return airname;
	}

	public void setAirname(String airname) {
		this.airname = airname;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public Date getReturndate() {
		return returndate;
	}

	public void setReturndate(Date returndate) {
		this.returndate = returndate;
	}

	public String getFromplace() {
		return fromplace;
	}

	public void setFromplace(String fromplace) {
		this.fromplace = fromplace;
	}

	public String getToplace() {
		return toplace;
	}

	public void setToplace(String toplace) {
		this.toplace = toplace;
	}
	
	public String getTriptype() {
		return triptype;
	}

	public void setTriptype(String triptype) {
		this.triptype = triptype;
	}

	public int getNoofpax() {
		return noofpax;
	}

	public void setNoofpax(int noofpax) {
		this.noofpax = noofpax;
	}

	public Date getArrivaldate() {
		return arrivaldate;
	}

	public void setArrivaldate(Date arrivaldate) {
		this.arrivaldate = arrivaldate;
	}

	public String getVisitcountry() {
		return visitcountry;
	}

	public void setVisitcountry(String visitcountry) {
		this.visitcountry = visitcountry;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDeparturename() {
		return departurename;
	}

	public void setDeparturename(String departurename) {
		this.departurename = departurename;
	}

	public Date getAppointmentdate() {
		return appointmentdate;
	}

	public void setAppointmentdate(Date appointmentdate) {
		this.appointmentdate = appointmentdate;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public String getYearofstudy() {
		return yearofstudy;
	}

	public void setYearofstudy(String yearofstudy) {
		this.yearofstudy = yearofstudy;
	}

	public String getCategoryinsurance() {
		return categoryinsurance;
	}

	public void setCategoryinsurance(String categoryinsurance) {
		this.categoryinsurance = categoryinsurance;
	}

	public String getCompanyinsurance() {
		return companyinsurance;
	}

	public void setCompanyinsurance(String companyinsurance) {
		this.companyinsurance = companyinsurance;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getCategoryproduct() {
		return categoryproduct;
	}

	public void setCategoryproduct(String categoryproduct) {
		this.categoryproduct = categoryproduct;
	}

	public String getListproduct() {
		return listproduct;
	}

	public void setListproduct(String listproduct) {
		this.listproduct = listproduct;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public int getNoofTables() {
		return noofTables;
	}

	public void setNoofTables(int noofTables) {
		this.noofTables = noofTables;
	}

	public Date getAcctCreated_date() {
		return acctCreated_date;
	}

	public void setAcctCreated_date(java.sql.Date acctCreated_date) {
		this.acctCreated_date = acctCreated_date;
	}

	public String getBookingCode() {
		return bookingCode;
	}

	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}

	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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