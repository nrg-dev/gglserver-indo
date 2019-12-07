package com.ggl.service;

/*
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.ParseException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.Produces;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggl.bo.EmployerBo;
import com.ggl.bo.GglBo;
import com.ggl.dto.EmployerDto;
import com.ggl.dto.GLGMem;
import com.ggl.dto.JobSeekerDto;
import com.ggl.dto.Member;
import com.ggl.dto.User;
import com.ggl.email.InvestmentEmail;
import com.ggl.model.UserDetail;
import com.ggl.mongo.dal.PublicTreeDAL;
import com.ggl.mongo.dal.RandomNumberDAL;
import com.ggl.mongo.model.OwnTree;
import com.ggl.mongo.model.PrivateTree;
import com.ggl.mongo.model.Publictree;
import com.ggl.mongo.model.RandomNumber;
import com.ggl.mongo.model.TempOwnTree;
import com.ggl.mongo.model.TempPrivateTree;
import com.ggl.mongo.model.TempPublicTree;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class EndPointMobileApp implements Filter {

	public static final Logger logger = LoggerFactory.getLogger(EndPointMobileApp.class);

	@Autowired
	// @Qualifier("mobile")
	GglBo bo1;

	@Autowired
	EmployerBo employerBo;

	@Autowired
	GglBo investmentBo1;

	private final RandomNumberDAL randamNumberDAL;
	private final PublicTreeDAL publicTreeDAL;

	List<String> files = new ArrayList<String>();
	List<String> publicfiles = new ArrayList<String>();
	List<String> privatefiles = new ArrayList<String>();
	List<String> ownfiles = new ArrayList<String>();

	public EndPointMobileApp(RandomNumberDAL randamNumberDAL, PublicTreeDAL publicTreeDAL) {
		this.randamNumberDAL = randamNumberDAL;
		this.publicTreeDAL = publicTreeDAL;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

	User user = new User();
	Member member = new Member();
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

	HashMap<String, String> hm = new HashMap<String, String>();
	ArrayList<Member> hotelList = null;
	ArrayList<String> listCountry = null;

	// --------- Login User ----------
	@GetMapping("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public User getLogin(String username, String pwd) throws JSONException {
		logger.info("User Name ..............." + username);
		logger.info("Password  ..............." + pwd);
		try {
			user.setUsername(username);
			user.setPassword(pwd);
			user = bo1.userLogin(user);
			logger.info(" Member Number ---------------->" + user.getMemberNumber());
		} catch (Exception e) {
			user.setStatus("Network Error Please try again");
			logger.info("Exception ------------->" + e.getMessage());
		} finally {

		}
		return user;

	}

	// --------- Validate Existing MemberID ----------
	@GetMapping("/getMemberIDValidation")
	@Produces(MediaType.APPLICATION_JSON)
	public User getMemberIDValidation(String memberID) throws JSONException {
		logger.info("User Name ..............." + memberID);
		boolean UIResponse = false;
		try {
			logger.info("Member Number  -->" + memberID);
			UIResponse = bo1.getMemberIDValidate(memberID);
			logger.info("Response ------------------>" + UIResponse);
			if (UIResponse == true) {
				user.setStatus("Valid");
			}
			if (UIResponse == false) {
				user.setStatus("InValid");
			}
		} catch (Exception e) {
			user.setStatus("Network Error Please try again");
			logger.info("Exception ------------->" + e.getMessage());
		} finally {

		}
		return user;
	}

	// --------- Register New Member ----------
	@PostMapping("/memberRegister")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String memberRegister(@RequestBody String myData) throws JSONException {
		logger.info("----- Inside memberRegister Method Calling ----");
		Gson gson = new Gson();
		logger.info("json Value----" + myData);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(myData);
		String refmemberID = element.getAsJsonObject().get("refmemberID").getAsString();
		String emailId = element.getAsJsonObject().get("email").getAsString();
		String country = element.getAsJsonObject().get("selectedCountry").getAsString();
		String phonenumber = element.getAsJsonObject().get("phoneNumber").getAsString();
		String firstName = element.getAsJsonObject().get("firstName").getAsString();
		String lastName = element.getAsJsonObject().get("lastName").getAsString();
		String userName = element.getAsJsonObject().get("username").getAsString();
		String password = element.getAsJsonObject().get("password").getAsString();
		String bankName = element.getAsJsonObject().get("bankName").getAsString();
		String accountNumber = element.getAsJsonObject().get("bankAcctNumber").getAsString();
		String accountType = element.getAsJsonObject().get("actType").getAsString();
		try {
			logger.info("------------ Inside Try Condition -------------");
			member.setRefmemberID(refmemberID);
			member.setEmailID(emailId);
			member.setCountry(country);
			member.setPhoneNumber(phonenumber);
			member.setFirstName(firstName);
			member.setLastName(lastName);
			member.setUsername(userName);
			member.setPassword(password);
			member.setBankName(bankName);
			member.setBankAcctNumber(accountNumber);
			member.setActType(accountType);
			logger.info("User Name ----------->" + userName);
			logger.info("Password ----------->" + password);
			logger.info("Before calling Bo ------------->");
			member = bo1.createMember(member);
			logger.info("Status --------------->" + member.getStatus());
			logger.info("Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(member);
	}

	// ------------ Employer Register -------------
	@PostMapping("/registerEmployer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String registerEmployer(@RequestBody EmployerDto employer) throws JSONException {
		logger.info("Name ....." + employer.getName());
		logger.info("Phone Number ....." + employer.getPhoneNumber());
		logger.info("Email Address ....." + employer.getEmailID());
		logger.info("Password ....." + employer.getPassword());
		logger.info("Country ....." + employer.getCountry());
		Gson gson = null;
		try {
			gson = new Gson();
			logger.info("------------ Before Register Employee BO Calling -------------");
			employer = employerBo.registerEmployer(employer);
			logger.info("------------ After Register Employee BO Calling -------------");
		} catch (Exception e) {
			System.out.println("[MobileApp] RegisterEmp Exception ---------->" + e.getMessage());
		} finally {

		}

		return gson.toJson(employer);
	}

	// ------------ Employee Login ---------------
	@GetMapping("/emplyerlogin")
	@Produces(MediaType.APPLICATION_JSON)
	public JobSeekerDto getemplyerlogin(String username, String password) throws JSONException {
		logger.info("User Name ..............." + username);
		logger.info("Password  ..............." + password);
		EmployerDto employer = null;
		try {
			employer = new EmployerDto();
			employer.setUsername(username); // Email ID
			employer.setPassword(password);
			logger.info("Before Calling Employer BO for LoginEmplyee");
			employer = employerBo.loginEmployer(employer);
			System.out.println("Status ------------>" + employer.getStatus());
			logger.info("Successfully Called Employer BO for LoginEmployer");
		} catch (Exception e) {
			employer.setStatus("Network Error Please try Again");
			logger.info("Exception ------------->" + e.getMessage());
		} finally {

		}
		return employer;
	}

	// ---------- getCountry List -------
	@GetMapping("/getCountryList")
	@Produces(MediaType.APPLICATION_JSON)
	public String countryChange() {
		List<String> listCountry = null;
		Gson gson = new Gson();
		try {
			logger.info("--------- Server side Mobile getCountryList Called -----------");
			listCountry = new ArrayList<String>();
			hm = bo1.getCountry(hm);
			Set<String> keys = hm.keySet();
			for (String key : keys) {
				listCountry.add(key);
			}
			Collections.sort(listCountry);
			listCountry.add("Other International");
		} catch (Exception e) {
			logger.info("Exception ------------->" + e.getMessage());
		} finally {

		}
		return gson.toJson(listCountry);
	}

	// ---------- get Dashboard List -----
	@GetMapping("/getCompanyList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCompanyList(String country) {
		logger.info("-------------- getCompanyInfo Called--------------");
		hotelList = null;
		int temp = 2;
		String ImagePath = "MobileCompanyList";
		Gson gson = new Gson();
		try {
			logger.info("Chooseen Country --->" + country);
			Member member = new Member();
			this.hotelList = new ArrayList<Member>();
			member.setSelectedCountry(country);
			member.setSequanceNumber(temp);
			this.hotelList = bo1.getCountryInfo(member, hotelList, ImagePath);
		} catch (Exception e) {
			logger.info("MobileApp getCompany Exception -->" + e.getMessage());
		} finally {

		}
		return gson.toJson(hotelList);
	}

	// --------- Get Profile View ----------
	@GetMapping("/profileView")
	@Produces(MediaType.APPLICATION_JSON)
	public String profileView(String primaryKey) throws JSONException {
		System.out.println("------ Inside profileView Method Calling -----------");
		Gson gson = new Gson();
		try {
			System.out.println("primaryKey ID  ----------->" + primaryKey);
			member.setUserloginPrimaryKeyString(primaryKey);
			System.out.println("Before calling Bo ------------->");
			member = bo1.getMyProfile(member);
			System.out.println("Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(member);
	}

	// --------- Update Profile Info ----------
	@PostMapping("/profileUpdate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String profileUpdate(@RequestBody String jsonValue) throws JSONException {
		System.out.println("------ Inside profileUpdate Method Calling -----------");
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(jsonValue);
		System.out.println("json Value----" + jsonValue);
		String primaryKey = element.getAsJsonObject().get("primaryKey").getAsString();
		String userName = element.getAsJsonObject().get("userName").getAsString();
		String firstName = element.getAsJsonObject().get("firstName").getAsString();
		String actType = element.getAsJsonObject().get("memberType").getAsString();
		String phonenumber = element.getAsJsonObject().get("phoneNumber").getAsString();
		String emailId = element.getAsJsonObject().get("email").getAsString();
		String lastName = element.getAsJsonObject().get("lastName").getAsString();
		String country = element.getAsJsonObject().get("country").getAsString();
		String bankName = element.getAsJsonObject().get("bankName").getAsString();
		String accountNumber = element.getAsJsonObject().get("accountNumber").getAsString();
		try {
			member.setUsername(userName);
			member.setUserloginPrimaryKeyString(primaryKey);
			member.setPhoneNumber(phonenumber);
			member.setEmailID(emailId);
			member.setFirstName(firstName);
			member.setLastName(lastName);
			member.setCountry(country);
			member.setBankName(bankName);
			member.setBankAcctNumber(accountNumber);
			member.setActType(actType);
			System.out.println("Before calling Bo ------------->");
			member = bo1.updateMyProfile(member);
			System.out.println("Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(member);
	}

	// --------- Get Hotel List name ----------
	@GetMapping("/getHotelNameList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getHotelNameList() throws JSONException {
		System.out.println("------ Inside getHotelNameList Method Calling -----------");
		Gson gson = new Gson();
		int temp;
		ArrayList<String> hotelList = null;
		try {
			hotelList = new ArrayList<String>();
			// if(country == null || country.isEmpty()){
			temp = 1;
			/*
			 * }else{ member.setSelectedCountry(country); member.setSelectedState(state);
			 * member.setCategoryname(categoryname); temp = 2; }
			 */
			System.out.println("Before getHotel BO Calling ------------->");
			hotelList = bo1.getName(hotelList, member, temp);
			for (String hotlename : hotelList) {
				//logger.info("Hotle Names --------->" + hotlename);
			}
			System.out.println("Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(hotelList);
	}

	// --------- Booking Hotel Details ----------
	@PostMapping("/saveHotelBooking")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveHotelBooking(@RequestBody String myData) throws JSONException {
		logger.info("----- Inside saveHotelBooking Method Calling ----");
		Gson gson = new Gson();
		logger.info("json Value----" + myData);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(myData);
		String selectedCountry = element.getAsJsonObject().get("selectedCountry").getAsString();
		String selectedState = element.getAsJsonObject().get("selectedState").getAsString();
		String categoryname = element.getAsJsonObject().get("categoryname").getAsString();
		String companyname = element.getAsJsonObject().get("companyname").getAsString();
		String noofadult = element.getAsJsonObject().get("noofadult").getAsString();
		String noofchild = element.getAsJsonObject().get("noofchild").getAsString();
		String noofrooms = element.getAsJsonObject().get("noofrooms").getAsString();
		String bookingdate = element.getAsJsonObject().get("bookingdate").getAsString();
		String medicaltime = element.getAsJsonObject().get("medicaltime").getAsString();
		int noofTables = element.getAsJsonObject().get("noofTables").getAsInt();
		String primaryKey = element.getAsJsonObject().get("primaryKey").getAsString();
		try {
			logger.info("------------ Inside Try Condition -------------");
			member.setUserloginPrimaryKeyString(primaryKey);
			member.setCountry(selectedCountry);
			member.setSelectedState(selectedState);
			member.setCategoryname(categoryname);
			member.setCompanyname(companyname);
			member.setNoofadult(noofadult);
			member.setNoofchild(noofchild);
			member.setNoofrooms(noofrooms);
			member.setBookingdate(ft.parse(bookingdate));
			member.setMedicaltime(medicaltime);
			member.setNoofTables(noofTables);
			member.setNoofpax("");

			logger.info("Before calling Bo ------------->");
			member = bo1.saveReservation(member, 1);
			logger.info("Status --------------->" + member.getStatus());
			logger.info("Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(member);
	}

	// --------- get My Member List View ----------
	@GetMapping("/getMyMemberViewList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMyMemberViewList(String memberNumber) throws JSONException {
		System.out.println("------ Inside getMyMemberList Method Calling -----------");
		Gson gson = new Gson();
		ArrayList<GLGMem> myMemList = null;
		try {
			myMemList = new ArrayList<GLGMem>();
			logger.info("Member Number ----------" + memberNumber);
			myMemList = bo1.getMyMemberList(memberNumber, myMemList);
			for (GLGMem g : myMemList) {
				logger.info("Value ------->" + g.getMemberName());
			}
			System.out.println("Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(myMemList);
	}

	// --------- Get My Booking List Details ----------
	@GetMapping("/getMyBookingList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMyBookingList(String primaryKey) throws JSONException {
		System.out.println("------ Inside getMyBookingList Method Calling -----------");
		Gson gson = new Gson();
		ArrayList<Member> list = null;
		try {
			list = new ArrayList<Member>();
			logger.info("Primary Key ----------" + primaryKey);
			list = bo1.getMyReservationDetails(list, primaryKey);
			System.out.println("Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(list);
	}

	// --------- Get My Booking View Based on ID ----------
	@GetMapping("/getMyBookingView")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMyBookingView(String userloginPrimaryKeyString) throws JSONException {
		System.out.println("------ Inside getMyBookingView Method Calling -----------");
		Gson gson = new Gson();
		logger.info("Booking ID ---------->" + userloginPrimaryKeyString);
		try {
			logger.info("Primary Key ----------" + userloginPrimaryKeyString);
			member = bo1.getMyReservationView(userloginPrimaryKeyString, member);
			System.out.println("Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(member);
	}

	// --------- Submit Withdraw ----------
	@PostMapping("/submitWithdraw")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String submitWithdraw(@RequestBody String jsonValue) throws JSONException {
		System.out.println("------ Inside submitWithdraw Method Calling -----------");
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(jsonValue);
		System.out.println("json Value----" + jsonValue);
		String memberID = element.getAsJsonObject().get("memberID").getAsString();
		int totalAmount = element.getAsJsonObject().get("totalAmount").getAsInt();
		int commission = element.getAsJsonObject().get("commission").getAsInt();
		int overriding = element.getAsJsonObject().get("overriding").getAsInt();
		try {
			member.setMemberID(memberID);
			member.setTotalAmount(totalAmount);
			member.setMemberCommition(commission);
			member.setMemberOvrriding(overriding);
			System.out.println("Before calling Bo ------------->");
			if (member.getTotalAmount() == 0) {
				logger.info(" Withdraw Amount is Equal to zero ");
				member.setStatus("exsist");
			} else {
				logger.info("--- Both Withdraw and Total Amount are Equal ---");
				member = bo1.submitWith(member);
			}
			System.out.println("Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(member);
	}

	// --------- Forget Password Check userName ----------
	@GetMapping("/checkUserName")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkUserName(String username) throws JSONException {
		System.out.println("------ Inside checkUserName Method Calling -----------");
		Gson gson = new Gson();
		logger.info("User Name ---------->" + username);
		try {
			user = new User();
			user.setUsername(username);
			user = bo1.Checkuser(user, 1);
			logger.info("Status for userCheck ----" + user.getStatus());
		} catch (Exception e) {
			user.setStatus("Network Error Please try Again");
			logger.info("Exception ------------->" + e.getMessage());
		} finally {

		}
		return gson.toJson(user.getStatus());
	}

	// --------- Forget Password Check OTP ----------
	@GetMapping("/otpCheck")
	@Produces(MediaType.APPLICATION_JSON)
	public String otpCheck(String otp) throws JSONException {
		System.out.println("------ Inside otpCheck Method Calling -----------");
		Gson gson = new Gson();
		logger.info("OTP ---------->" + otp);
		try {
			user = new User();
			user.setMemberID(otp);
			user = bo1.Checkuser(user, 2);
			logger.info("Status for otpCheck ----" + user.getStatus());
		} catch (Exception e) {
			user.setStatus("Network Error Please try Again");
			logger.info("Exception ------------->" + e.getMessage());
		} finally {

		}
		return gson.toJson(user.getStatus());
	}

	// --------- Forget Password Change New Password ----------
	@GetMapping("/changePassword")
	@Produces(MediaType.APPLICATION_JSON)
	public String changePassword(String newPassword1, String forgetUser) throws JSONException {
		System.out.println("------ Inside changePassword Method Calling -----------");
		Gson gson = new Gson();
		logger.info("[MobileApp-resetPassword] New Password ---------------->" + newPassword1);
		logger.info("[MobileApp-resetPassword] User Name ---------------->" + forgetUser);
		try {
			user = new User();
			user.setPassword(newPassword1);
			user.setUsername(forgetUser);
			user = bo1.Checkuser(user, 3);
			logger.info("Status for changePassword ----" + user.getStatus());
		} catch (Exception e) {
			user.setStatus("Network Error Please try Again");
			logger.info("Exception ------------->" + e.getMessage());
		} finally {

		}
		return gson.toJson(user.getStatus());
	}

	// --------- Temp Public Tree Unit Save ----------
	@PostMapping("/mobileTempPublicTreeUnitSave")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String mobileTempPublicTreeUnitSave(@RequestBody String publicData) throws JSONException {
		logger.info("----- Inside mobileTempPublicTreeUnitSave Method Calling ----");
		Gson gson = new Gson();
		logger.info("json Value----" + publicData);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(publicData);
		int numberofUnit = element.getAsJsonObject().get("numberofUnit").getAsInt();
		int userID = element.getAsJsonObject().get("primaryKey").getAsInt();
		ArrayList<Member> list = new ArrayList<Member>();
		TempPublicTree temppublictree;
		try {
			logger.info("------------ Inside Try Condition -------------");
			RandomNumber randamNumber = randamNumberDAL.getTempPublicRandomNumber();
			member.setNumberofUnit(numberofUnit);
			member.setUserLoginPrimaryKey(userID);
			logger.info("Number of Unit ----------->" + numberofUnit);
			logger.info("Primary Key ----------->" + userID);

			for (int i = 0; i < numberofUnit; i++) {
				temppublictree = new TempPublicTree();
				temppublictree.setPaymentStatus("NOT PAID");
				temppublictree.setPayAmount(100);
				temppublictree.setNumberofUnits(1);
				temppublictree.setUserID(userID);
				temppublictree.setCurrency("SGD");

				int curInNumber = randamNumber.getPublicCount();
				int nextInvoiceNumber = curInNumber + 1;
				temppublictree.setInvoiceCode(randamNumber.getPublicInvCode() + nextInvoiceNumber);

				publicTreeDAL.insertTempPublicUser(temppublictree);

				RandomNumber document = new RandomNumber();
				document.setPublicCount(nextInvoiceNumber);
				randamNumberDAL.updateTempPublicRandamNumber(document);

				member = new Member();
				member.setInvoiceNumber(temppublictree.getInvoiceCode());
				member.setTotalAmount(numberofUnit * 100);
				list.add(member);
				randamNumber = randamNumberDAL.getTempPublicRandomNumber();

			}

			logger.info("Before calling Email ------------->");
			UserDetail userdetails = investmentBo1.getMemberEmailID(userID);
			logger.info("EndPoint Public Email ID ------->" + userdetails.getEmail1());
			if (userdetails.getEmail1().isEmpty()) {
				logger.info("------------- No Email ID for public Tree -----------------");
			} else {
				logger.info("----------- Found Email ID for Public Tree ----------------");
				InvestmentEmail.tempPublicTree(list, userdetails.getEmail1());
			}
			logger.info("Successfuly called Email --------------->");
		} catch (Exception e) {
			logger.info("TempPublicTree Exception ------------->" + e.getMessage());
			e.printStackTrace();
		}

		return gson.toJson(list);
	}

	// --------- Temp Own Tree Unit Save ----------
	@PostMapping("/mobileTempOwnTreeUnitSave")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String mobileTempOwnTreeUnitSave(@RequestBody String ownData) throws JSONException {
		logger.info("----- Inside mobileTempOwnTreeUnitSave Method Calling ----");
		Gson gson = new Gson();
		logger.info("json Value----" + ownData);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(ownData);
		int numberofUnit = element.getAsJsonObject().get("numberofUnit").getAsInt();
		int userID = element.getAsJsonObject().get("primaryKey").getAsInt();
		ArrayList<Member> list = new ArrayList<Member>();
		TempOwnTree tempowntree;
		try {
			logger.info("------------ Inside Try Condition -------------");
			RandomNumber randamNumber = randamNumberDAL.getTempPublicRandomNumber();
			member.setNumberofUnit(numberofUnit);
			member.setUserLoginPrimaryKey(userID);
			logger.info("Number of Unit ----------->" + numberofUnit);
			logger.info("Primary Key ----------->" + userID);

			tempowntree = new TempOwnTree();
			tempowntree.setPaymentStatus("NOT PAID");
			tempowntree.setPayAmount(numberofUnit * 100);
			tempowntree.setNumberofUnits(numberofUnit);
			tempowntree.setUserID(userID);
			tempowntree.setCurrency("SGD");

			int curInNumber = randamNumber.getOwnCount();
			int nextInvoiceNumber = curInNumber + 1;
			tempowntree.setInvoiceCode(randamNumber.getOwnInvCode() + nextInvoiceNumber);

			publicTreeDAL.insertTempOwnTreeUser(tempowntree);

			RandomNumber document = new RandomNumber();
			document.setOwnCount(nextInvoiceNumber);
			randamNumberDAL.updateTempOwnTreeandamNumber(document);
			// Response
			member = new Member();
			member.setInvoiceNumber(tempowntree.getInvoiceCode());
			member.setTreeName("Not Yet Created.");
			member.setTotalAmount(numberofUnit * 100);
			list.add(member);

			logger.info("Mobile App Temp Own Primary Key --->" + userID);

			UserDetail userdetails = investmentBo1.getMemberEmailID(userID);
			logger.info("Before calling Email ------------->");
			if (userdetails.getEmail1().isEmpty()) {
				logger.info("----------- No Email ID----------------");
			} else {
				logger.info("----------- Found Email ID----------------");
				InvestmentEmail.tempOwnTree(list, userdetails.getEmail1());
			}
			logger.info("Successfuly called Email --------------->");
		} catch (Exception e) {
			logger.info("TempOwnTree Exception ------------->" + e.getMessage());
			e.printStackTrace();
		}

		return gson.toJson(list);
	}

	// --------- Temp Own Tree Name Validation ----------
	@GetMapping("/getMobileOwnTreeNameValidate")
	@Produces(MediaType.APPLICATION_JSON)
	public User getMobileOwnTreeNameValidate(String memberID) throws JSONException {
		logger.info("-------- getMobileOwnTreeNameValidate -----------");
		User user = null;
		try {
			logger.info("Ref. Own Tree Number ------>" + memberID);
			user = new User();
			String response = publicTreeDAL.validateOwnTreeName(memberID);
			user.setStatus(response);
		} catch (Exception e) {
			logger.info("getMobileOwnTreeNameValidate Error -->" + e.getMessage());
			user.setStatus("Network Error Please try again");
		} finally {

		}
		return user;
	}

	// --------- Temp Private Tree Unit Save ----------
	@PostMapping("/mobileTempPrivateTreeUnitSave")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String mobileTempPrivateTreeUnitSave(@RequestBody String ownData) throws JSONException {
		logger.info("----- Inside mobileTempPrivateTreeUnitSave Method Calling ----");
		Gson gson = new Gson();
		logger.info("json Value----" + ownData);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(ownData);
		int noUnit = element.getAsJsonObject().get("numberOfUnits").getAsInt();
		int userID = element.getAsJsonObject().get("primaryKey").getAsInt();
		String treeName = element.getAsJsonObject().get("refmemberID").getAsString();
		ArrayList<Member> list = new ArrayList<Member>();
		TempPrivateTree tempprivatetree;
		try {
			RandomNumber randamNumber = randamNumberDAL.getTempPublicRandomNumber();

			for (int i = 0; i < noUnit; i++) {
				tempprivatetree = new TempPrivateTree();
				tempprivatetree.setPaymentStatus("NOT PAID");
				tempprivatetree.setPayAmount(100);
				tempprivatetree.setNumberofUnits(1);
				tempprivatetree.setUserID(userID);
				tempprivatetree.setCurrency("SGD");
				tempprivatetree.setTreeName(treeName);

				int curInNumber = randamNumber.getPrivateCount();
				int nextInvoiceNumber = curInNumber + 1;
				tempprivatetree.setInvoiceCode(randamNumber.getPrivateInvCode() + nextInvoiceNumber);

				publicTreeDAL.insertTempPrivateTreeUser(tempprivatetree);

				RandomNumber document = new RandomNumber();
				document.setPrivateCount(nextInvoiceNumber);
				randamNumberDAL.updateTempPrivateRandamNumber(document);

				member = new Member();
				member.setInvoiceNumber(tempprivatetree.getInvoiceCode());
				member.setTotalAmount(noUnit * 100);
				list.add(member);
				randamNumber = randamNumberDAL.getTempPublicRandomNumber();
			}

			logger.info("Mobile App Temp Private Primary Key --->" + userID);
			// Get Email ID
			UserDetail userdetails = investmentBo1.getMemberEmailID(userID);
			if (userdetails.getEmail1().isEmpty()) {
				logger.info("----------- No Email ID----------------");
			} else {
				logger.info("----------- Found Email ID----------------");
				InvestmentEmail.tempPrivateTree(list, userdetails.getEmail1());
			}
		} catch (Exception e) {
			logger.info("TempOwnTree Exception ------------->" + e.getMessage());
			e.printStackTrace();
		}
		return gson.toJson(list);
	}

	// --------- Get Public Tree Status Details after Approval ----------
	@GetMapping("/getMobileSingleUnitInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMobileSingleUnitInfo(String primaryKey) throws JSONException {
		System.out.println("------ Inside getMobileSingleUnitInfo Method Calling -----------");
		Gson gson = new Gson();
		List<Publictree> responseList = null;
		try {
			responseList = new ArrayList<Publictree>();
			System.out.println("primaryKey ID  ----------->" + primaryKey);
			member.setUserloginPrimaryKeyString(primaryKey);
			System.out.println("getMobileSingleUnitInfo Before calling Bo ------------->");
			responseList = publicTreeDAL.getSinglePurchaseUnitByUserId(primaryKey);
			System.out.println("getMobileSingleUnitInfo Successfuly called Bo --------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(responseList);
	}

	// ----------- load private tree list -----------
	@GetMapping("/mobileloadTreeName")
	@Produces(MediaType.APPLICATION_JSON)
	public String mobileloadTreeName(String memberID) throws JSONException {
		logger.info("-------- mobileloadTreeName -----------");
		Gson gson = new Gson();
		ArrayList<String> response = null;
		List<OwnTree> responseList = null;
		try {
			response = new ArrayList<String>();
			responseList = new ArrayList<OwnTree>();
			responseList = publicTreeDAL.loadTreeName();
			for (OwnTree ot : responseList) {
				response.add(ot.getInvoiceCode());
			}
		} catch (Exception e) {
			logger.info("mobileloadTreeName Error -->" + e.getMessage());
			user.setStatus("Network Error Please try again");
		} finally {

		}
		return gson.toJson(response);
	}

	// --------- Get Private Unit Status Details ------
	@GetMapping("/getmobileSinglePrivateUnitInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getmobileSinglePrivateUnitInfo(String primaryKey, String treeName) throws JSONException {
		logger.info("-------- getmobileSinglePrivateUnitInfo -----------");
		Gson gson = new Gson();
		logger.info("Private User Primary Key--->" + primaryKey);
		logger.info("Private User Tree Name --->" + treeName);
		List<PrivateTree> responseList = null;
		try {
			responseList = new ArrayList<PrivateTree>();
			responseList = publicTreeDAL.getSinglePrivatePurchaseUnitByUserId(primaryKey, treeName);
		} catch (Exception e) {
			logger.info("mobileloadTreeName Error -->" + e.getMessage());
			user.setStatus("Network Error Please try again");
		} finally {

		}
		return gson.toJson(responseList);
	}

	// ---------- Load State Based on Country --------
	@GetMapping("/getStateList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStateList(String selectCountry) throws JSONException {
		logger.info("-------- getStateList -----------");
		Gson gson = new Gson();
		ArrayList<String> listState = null;
		try {
			listState = new ArrayList<String>();
			String stateString = hm.get(selectCountry);
			String[] stateStringArray = stateString.split("-");
			for (String r : stateStringArray) {
				listState.add(r);
			}
		} catch (Exception e) {
			logger.info("getStateList Exception -->" + e.getMessage());
		}
		return gson.toJson(listState);
	}

	// ----------- Get Dashboard List Based on Search Country,State,company and
	@PostMapping("/getSearchCompanyList")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSearchCompanyList(@RequestBody String searchData) throws JSONException {
		logger.info("-------- Inside getSearchCompanyList Calling -----------");
		Gson gson = new Gson();
		logger.info("json Value----" + searchData);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(searchData);
		String selectedCountry = element.getAsJsonObject().get("country").getAsString();
		String selectedState = element.getAsJsonObject().get("state").getAsString();
		String categoryname = element.getAsJsonObject().get("category").getAsString();
		String cname = element.getAsJsonObject().get("cname").getAsString();
		try {
			logger.info("Selected Country ----------->" + selectedCountry);
			logger.info("Selected State ----------->" + selectedState);
			logger.info("Hotel Name ----------->" + cname);
			logger.info("Category Name ----------->" + categoryname);
			member = new Member();
			this.hotelList = new ArrayList<Member>();
			member.setSelectedCountry(selectedCountry);
			member.setSelectedState(selectedState);
			member.setCname(cname);
			member.setCategoryname(categoryname);
			this.hotelList = bo1.searchHotel(hotelList, member);
		} catch (Exception e) {
			logger.info("getSearchCompanyList Exception -->" + e.getMessage());
		}
		return gson.toJson(hotelList);
	}

	// --------- Validate Temp Trees ----------
	@GetMapping("/getValidateTempmobileTree")
	@Produces(MediaType.APPLICATION_JSON)
	public User getValidateTempmobileTree(String invoiceNumber, String treeName) throws JSONException {
		System.out.println("------ Inside getValidateTempmobileTree Method Calling -----------");
		logger.info(" ------- Invoice Number ------- " + invoiceNumber);
		logger.info(" ------- Tree Name ------- " + treeName);
		try {
			user = new User();
			if (treeName.equalsIgnoreCase("publicTree")) {
				TempPublicTree res = publicTreeDAL.validatePublic(invoiceNumber);
				if (res != null) {
					logger.info("----------- Public Tree match--------");
					user.setStatus("Valid");
				} else {
					logger.info("----------- Public Tree not match--------");
					user.setStatus("InValid");
				}
			}

			// Private
			if (treeName.equalsIgnoreCase("privateTree")) {
				TempPrivateTree res = publicTreeDAL.validatePrivate(invoiceNumber);
				if (res != null) {
					logger.info("----------- Private Tree match--------");
					user.setStatus("Valid");
				} else {
					logger.info("----------- Private Tree not match--------");
					user.setStatus("InValid");
				}
			}

			// Own
			if (treeName.equalsIgnoreCase("ownTree")) {
				TempOwnTree res = publicTreeDAL.validateOwn(invoiceNumber);
				if (res != null) {
					logger.info("----------- Own Tree match--------");
					user.setStatus("Valid");
				} else {
					logger.info("----------- Own Tree not match--------");
					user.setStatus("InValid");
				}
			}

		} catch (Exception e) {
			logger.info("Error -->" + e.getMessage());
			user.setStatus("Network Error Please try again");
		} finally {

		}
		return user;
	}
	
	//---------- Upload Payment on Investment ------------
	 @PostMapping("/uploadimage")
	 public ResponseEntity<?> uploadFileMulti(@RequestParam("file") MultipartFile file,@RequestParam String invoiceNumber,@RequestParam String treeName) {
		String message = "";
		try {
			logger.info(" --------- Inside paymentImageUplaod Method --------- ");
			if(treeName.equalsIgnoreCase("publicTree")) {
					logger.info("------------- publicTree Match ------");
					publicTreeDAL.storeImage(file,invoiceNumber,treeName);
					publicfiles.add(file.getOriginalFilename());
					message = "You successfully uploaded " + file.getOriginalFilename() + "!";
				    return ResponseEntity.status(HttpStatus.OK).body(message);
			}
		
			if(treeName.equalsIgnoreCase("privateTree")) {
					logger.info("------------- privateTree Match ------");
					publicTreeDAL.storeImage(file,invoiceNumber,treeName);
					publicfiles.add(file.getOriginalFilename());
					message = "You successfully uploaded " + file.getOriginalFilename() + "!";
					return ResponseEntity.status(HttpStatus.OK).body(message);
			}
		
			if(treeName.equalsIgnoreCase("ownTree")) {
					logger.info("------------- Own Tree Match ------");
					publicTreeDAL.storeImage(file,invoiceNumber,treeName);
					publicfiles.add(file.getOriginalFilename());
					message = "You successfully uploaded " + file.getOriginalFilename() + "!";
					return ResponseEntity.status(HttpStatus.OK).body(message);
		    }
			
			return ResponseEntity.status(HttpStatus.OK).body(message); 

		} catch (Exception e) {
			 message = "FAIL to upload " + file.getOriginalFilename() + "!";
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}	 
	 
	@PostMapping("/websiteFeedBackRegister")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String websiteFeedBackRegister(@RequestBody String feedbackData) throws JSONException {
		logger.info("------ Inside Website Feed Back Mail Calling ------");
		Gson gson = new Gson();
		logger.info("json Value----" + feedbackData);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(feedbackData);
		String email = element.getAsJsonObject().get("email").getAsString(); 
		String name = element.getAsJsonObject().get("name").getAsString();
		String subject = element.getAsJsonObject().get("subject").getAsString();
		String message = element.getAsJsonObject().get("message").getAsString();
		Member member = null;
		try {
			logger.info("------ Inside try Condition Calling ------");
			member = new Member();
			member.setFirstName(name);
			member.setEmailID(email);
			member.setAddress(subject); 
			member.setDescription(message);
			member = bo1.websiteFeedBackRegister(member);
			
		}catch(Exception e) {
			logger.info("[Website]Exception ---->"+e.getMessage());
		}
		finally {
			
		}
		return gson.toJson(member);
	}

}
