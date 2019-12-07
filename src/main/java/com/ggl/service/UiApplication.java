package com.ggl.service;

//import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//import javax.enterprise.inject.Produces;

 import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggl.dto.Dropbox;
import com.ggl.dto.GLGMem;
import com.ggl.dto.Member;
import com.ggl.dto.User;
import com.ggl.util.GLGException;
import com.ggl.bo.*;
import com.ggl.service.UiApplication;

import java.util.HashMap;
//import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@RestController
public class UiApplication implements Filter{
	
	public static final Logger logger = LoggerFactory.getLogger(UiApplication.class);
		User user;
		Member member;
		Dropbox dropbox;
		
		
		@Value("${memeber.bankName}")
		private String bankName;
		@Value("${memeber.bankTransfer}")
		private String bankTransfer;
		 
		@Autowired
		GglBo bo;
	
		@Autowired
		StorageService storageService;
	
		List<String> files = new ArrayList<String>();
		ArrayList<Member> categoryList = null;
		ArrayList<Member> hotelList = null;
		// http://localhost:8080
		// http://35.162.40.190:80
	
		

		@Override
		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		   // HttpServletRequest request = (HttpServletRequest) req;
		    HttpServletResponse response = (HttpServletResponse) res;
		  //  response.setHeader("Access-Control-Allow-Origin", "https://www.gglway.com");
		    response.setHeader("Access-Control-Allow-Origin", "*");
		    response.setHeader("Access-Control-Allow-Credentials", "true");
		    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		    response.setHeader("Access-Control-Max-Age", "3600");
		   // response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
		    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

		  //  Access-Control-Allow-Origin: https://www.gglway.com

		    chain.doFilter(req, res);
		}

		@Override
		public void init(FilterConfig filterConfig) {
		}

		@Override
		public void destroy() {
		}
		
		// File upload 
		@PostMapping("/paymentUplaod")
		//@CrossOrigin(origins = "http://localhost:80")
		@CrossOrigin(origins = "http://localhost:80")
		public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file , @RequestParam("memberID") String memberID ) {
			logger.info("--------File upload-------");
			logger.info("--------Member Number -------"+memberID);
			String uploadStatus="paymentUpload";
			String message = "";
			try {
				String status = storageService.store(file,memberID,uploadStatus);	
				if(status.equalsIgnoreCase("Success")){
					bo.UpdatePayment(memberID);
				}else if(status.equalsIgnoreCase("failure")){
					logger.info("---- Failed to Upload File ------");
				}
				files.add(file.getOriginalFilename());
				message = "You successfully uploaded " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				message = "FAIL to upload " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}
		
		@GetMapping("/getallfiles")
		@CrossOrigin(origins = "http://localhost:80")
		public ResponseEntity<List<String>> getListFiles(Model model,String memberID) {
			logger.info("------- Inside getListFiles Method ---------");
			logger.info("-- MemberID --->"+memberID); 
			String fileName1 = memberID + ".jpg";
			
			List<String> fileNames = storageService.loadAll().map(fileName -> MvcUriComponentsBuilder
	                .fromMethodName(UiApplication.class, "getFile", fileName1).build().encode().toString())
	                .collect(Collectors.toList());
			return ResponseEntity.ok().body(fileNames); 
		}

		@GetMapping("/files/{filename:.+}")
		@ResponseBody
		public ResponseEntity<Resource> getFile(@PathVariable String filename) {
			logger.info("------- Inside getFile Method ---------");
			Resource file = storageService.loadFile(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		}
		
		// -------------------Login check -------------------------------------------
		
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/user",method=RequestMethod.GET)
		public ResponseEntity<?>  user(@RequestParam String username,@RequestParam String password) {
			logger.info("----------Inside User-------------------");
			logger.info("[UI Application] User Name ---------------->"+username);
			logger.info("[UI Application] Password  ---------------->"+password);
			String userName = username;
			String investPassword=password;
			try {
				if(userName.equalsIgnoreCase("admin") && investPassword.equalsIgnoreCase("admin123")) {
					user = new User();
					user.setStatus("success1");
					user.setUserRole("admin");
				}
				else {
					user = new User();
					user.setUsername(username);
					user.setPassword(password);
				//	bo.test();
					user = bo.userLogin(user);
					logger.info("Status --->"+user.getStatus());
					logger.info("User Type -->"+user.getUserRole());
					logger.info("[UI Application] Member Number ---------------->"+user.getMemberNumber());
				}
				
				//user.setStatus(bo.userLogin(user).getStatus());
			}catch(Exception e) {
				user.setStatus("Network Error Please try again");
				logger.info("Exception ------------->"+e.getMessage());
			}finally {
				
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		  }
		
		//----------------- Check user -------------------------	
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/Checkuser",method=RequestMethod.GET)
		public ResponseEntity<?>  Checkuser(@RequestParam String username) {
			logger.info("[UI Application-Checkuser] User Name ---------------->"+username);
	
			try {
				user = new User();
				user.setUsername(username);
				user = bo.Checkuser(user,1);
			}catch(Exception e) {
				user.setStatus("Network Error Please try again");
				logger.info("Exception ------------->"+e.getMessage());
			}finally {
				
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		  }
			
		//----------------- reset Password submit -------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/resetPassword",method=RequestMethod.GET)
		public ResponseEntity<User>  resetPassword(@RequestParam String newPassword,@RequestParam String userName) {
			logger.info("[UI Application-resetPassword] New Password ---------------->"+newPassword);
			try {
				user = new User();
				user.setPassword(newPassword);
				user.setUsername(userName);
				user = bo.Checkuser(user,3);					
			}catch(Exception e) {
				user.setStatus("Network Error Please try again");
				logger.info("Exception ------------->"+e.getMessage());
			}finally {
				
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		  }
	
		//----------------- OTP Validate check -------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/OTPCheck",method=RequestMethod.GET)
		public ResponseEntity<?>  OTPCheck(@RequestParam String otp) {
			logger.info("[UI Application-OTPCheck] User Name ---------------->"+otp);
			//String tmpOTP;
			try {
				user = new User();
				user.setMemberID(otp);
				user = bo.Checkuser(user,2);
			}catch(Exception e) {
				user.setStatus("Network Error Please try again");
				logger.info("Exception ------------->"+e.getMessage());
			}finally {
				
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		  }
				
		// ----------------------- Member ID validate ----------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getMemberIDValidate",method=RequestMethod.GET)
		public ResponseEntity<?>  getMemberIDValidate(@RequestParam String memberID) {
			boolean UIResponse = false;
			try {
				user = new User();
				logger.info("Member Number  -->"+memberID);
				logger.info("Before Calling BO");
				UIResponse = bo.getMemberIDValidate(memberID);
				logger.info("Response ------------------>"+UIResponse);
				logger.info("Successfully Called BO -->");
				if(UIResponse==true) {
					user.setStatus("Valid");
				} 
				if(UIResponse==false) {
					user.setStatus("InValid");
				}
				//user.setStatus("Valid");
	
			}catch(Exception e) {
				logger.info("Error -->"+e.getMessage());
				user.setStatus("Network Error Please try again");
			}finally {
				
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		  }
		
		// ------------------------------ Save reservation -----------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/saveReservation",method=RequestMethod.POST)
		public ResponseEntity<Member>  saveReservation(@RequestBody Member member) {
		   logger.info("------------------ Inside saveReservation Create -----------------");
	       logger.info("[UiApplication] User Primary Key ----------->"+member.getUserloginPrimaryKeyString()); // Country
	       logger.info("[UiApplication] Country ----------->"+member.getSelectedCountry()); // Country
	       logger.info("[UiApplication] State ----------->"+member.getSelectedState()); // State
	       logger.info("[UiApplication] Category ----------->"+member.getCategoryname()); // Category
	       logger.info("[UiApplication] Hotel Name  ----------->"+member.getCname()); // Hotel name
	       logger.info("[UiApplication] Number adults ----------->"+member.getNoofadult()); // No of adult
	       logger.info("[UiApplication] Number of child ----------->"+member.getNoofchild()); // No of child
	       logger.info("[UiApplication] Number of rooms ----------->"+member.getNoofrooms()); // No of rooms
	       logger.info("[UiApplication] Booking Code ----------->"+member.getBookingCode());
	       
	       logger.info("[UiApplication] AirName ----------->"+member.getAirname());
	       logger.info("[UiApplication] Departure Date ----------->"+member.getDeparture());
	       logger.info("[UiApplication] Return Date ----------->"+member.getReturndate());
	       logger.info("[UiApplication] From Place ----------->"+member.getFromplace());
	       logger.info("[UiApplication] To Place ----------->"+member.getToplace());
	       logger.info("[UiApplication] No.of Pax ----------->"+member.getNoofpax());
	       logger.info("[UiApplication] Trip Type ----------->"+member.getTriptype());
		   try 
		   {
			   member= bo.saveReservation(member,1); 
			   logger.info("Response -------------->"+member.getStatus());
		   }catch(Exception e) {
			   logger.info("Exception ------------->"+e.getMessage());
		   }finally{
			   
		   }
		  	return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			//return new ResponseEntity<Member>(member, HttpStatus.CREATED);
	
		}
		
		// -------------------Create a Member -------------------------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/register",method=RequestMethod.POST)
		public ResponseEntity<Member>  createMember(@RequestBody Member member) {
		   logger.info("------------------ Inside Member Create -----------------");
		   logger.info("[UiApplication] Member ID --------->"+member.getRefmemberID()); // referance member ID
	       logger.info("[UiApplication] Email ID ----------->"+member.getEmailID()); // Email
	       logger.info("[UiApplication] Country --------->"+member.getSelectedCountry()); // Country
	       logger.info("[UiApplication] Phone number --------->"+member.getPhoneNumber()); // Phone number	      
	       logger.info("[UiApplication] First name --------->"+member.getFirstName()); // First name
	       logger.info("[UiApplication] Last name --------->"+member.getLastName()); // Last name 
	       logger.info("[UiApplication] User name --------->"+member.getUsername()); // User name 
	       logger.info("[UiApplication] Password --------->"+member.getPassword()); // Password	       
	       logger.info("[UiApplication] Bank name --------->"+member.getBankName()); // bank name
	       logger.info("[UiApplication] Acct Number --------->"+member.getBankAcctNumber()); // bank account number 
	       logger.info("[UiApplication] Acct type  --------->"+member.getActType()); // bank account type
	       
	       logger.info("[UiApplication-memberPayment] Admin Bank Name --------->"+member.getAdminbankName()); 
	       logger.info("[UiApplication-memberPayment] Admin Account Number -------->"+member.getAdminacctNumber()); 
	       logger.info("[UiApplication-memberPayment] Admin Account Name ------->"+member.getAdminacctName()); 
	       logger.info("[UiApplication-memberPayment] Transfer Bank Name ------->"+member.getBanktransfer());
	       logger.info("[UiApplication-memberPayment] Transfer Account Number --------->"+member.getTransferBankAcctNumber());
	       logger.info("[UiApplication-memberPayment] Transfer Account Name ---------->"+member.getTransferAcctName());
	       logger.info("[UiApplication-memberPayment] MemberID --------->"+member.getMemberID());
	       
	       try 
	       {
			   logger.info("Before calling Bo");
		       bo.createMember(member);
		       logger.info("Successfully called Bo"+member.getStatus());
			} 
			 catch(GLGException ge) {
				logger.info("GLGException --------------->"+ge.getMessage()); 
			 }
			 catch(Exception e) {
					logger.info("Exception --------------->"+e.getMessage()); 
			 }
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		// get all State
		HashMap<String,String> hm =  new HashMap<String,String>();//new HashMap<String,String>();
			
		ArrayList<String> listCountry=null;
		ArrayList<String> listState=null;
	
		// -------------------- drop down value --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getAllCountry",method=RequestMethod.GET)
		public ResponseEntity<?> getAllCountry()
		{
		   try {
				logger.info("Server side getCountry Called");
				listCountry = new ArrayList<String>();
				hm=bo.getCountry(hm);		
				Set<String> keys = hm.keySet();
			    for(String key: keys){
			        listCountry.add(key);
			    }
		        Collections.sort(listCountry);
		        listCountry.add("Other International");
	
				}catch(Exception e){
				logger.info("Exception ------------->"+e.getMessage());
			}finally{
				
			}
			return new ResponseEntity<ArrayList<String>>(listCountry, HttpStatus.CREATED);
	
		}
		
		// -------------------- drop down value --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getState",method=RequestMethod.GET)
		public ResponseEntity<?> getState(@RequestParam String country)
		{
			logger.info("Choosen Country ------->"+country); 
			try {
			listState = new ArrayList<String>();
			String stateString=hm.get(country);
		    String[] stateStringArray = stateString.split("-");
		    for (String r : stateStringArray) {
		    	listState.add(r);
		       //  logger.info(r);
	      }
	   	return new ResponseEntity<ArrayList<String>>(listState, HttpStatus.CREATED);
			}catch(Exception e) {
				
			}
		    return new ResponseEntity<ArrayList<String>>(listState, HttpStatus.CREATED);
	
		}
	
		// -------------------- drop down value for Category --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getCategory",method=RequestMethod.GET)
		public ResponseEntity<?> getCategory(@RequestParam String category)
		{
			logger.info("Choosen State name -------------------->"+category);
			ArrayList<String> categoryList = null;
			try {
				categoryList = new ArrayList<String>();
				categoryList.add("---- Select ----");
				//categoryList.add("Restaurants and Hotels");
				categoryList.add("Food and hotels");
				categoryList.add("Ticketing");
				categoryList.add("Travel and Tour");
				categoryList.add("Financial Solution");
				categoryList.add("Education");
				categoryList.add("Insurance");
				//categoryList.add("Medical Treatment");
				categoryList.add("Health Accessories");
				categoryList.add("Herbal Product");
				categoryList.add("Umrah");
				categoryList.add("Software And Hardware");
				//categoryList.add("Energy Saving");
				categoryList.add("----Others----");
				
			}catch(Exception e) {
				logger.info("Exception ------------->"+e.getMessage());
			}
			return new ResponseEntity<ArrayList<String>>(categoryList, HttpStatus.CREATED);
	
		}
		
		// ------------------------------ Save Org registration -----------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/saveOrg",method=RequestMethod.POST)
		public ResponseEntity<Member>  saveOrg(@RequestBody Member member) {
		   logger.info("------------------ Inside saveOrg -----------------");
	       logger.info("[UiApplication-saveOrg] Country ------------>"+member.getSelectedCountry()); // Country
	       logger.info("[UiApplication-saveOrg] State ----------->"+member.getSelectedState()); // State
	       logger.info("[UiApplication-saveOrg] Category ----------->"+member.getCategoryname()); // Category
	       logger.info("[UiApplication-saveOrg] Hotel Name  ----------->"+member.getCname()); // Hotel name
	       logger.info("[UiApplication-saveOrg] Price  ----------->"+member.getPrice());
		   try 
		   {
			  member= bo.saveReservation(member,2); 
			   logger.info("Response -------------->"+member.getStatus());
		   }catch(Exception e) {
			   member.setStatus("failure");
			   logger.info("Exception ------------->"+e.getMessage());
		   }finally{
			   
		   }
		  	return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			//return new ResponseEntity<Member>(member, HttpStatus.CREATED);
	
		}
		
				
				
		// -------------------- drop down value for Hotel Name  --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getHotelName",method=RequestMethod.POST)
		public ResponseEntity<?> getHotelName(@RequestBody Member member)
		{
			ArrayList<String> hotelList = null;
			int temp;
			logger.info("Country name -------------------->"+member.getSelectedCountry());
			logger.info("State name -------------------->"+member.getSelectedState());
			logger.info("Category name -------------------->"+member.getCategoryname());			
			try {
				hotelList = new ArrayList<String>();
				if(member.getSelectedCountry() == null || member.getSelectedCountry().isEmpty()){
					temp = 1;
				}else{
					temp = 2;
				}
				hotelList = bo.getName(hotelList,member,temp);
				for(String hotlename : hotelList) { 
					//logger.info("Hotle Names --------->"+hotlename);
				}
			return new ResponseEntity<ArrayList<String>>(hotelList, HttpStatus.CREATED);
		
			}catch(Exception e) {
				logger.info("Exception ------------->"+e.getMessage());
			}
			return new ResponseEntity<ArrayList<String>>(hotelList, HttpStatus.CREATED);
	
			
			   
			   /*
			   
			   
				logger.info("Country name -------------------->"+member.getSelectedCountry());
				logger.info("State name -------------------->"+member.getSelectedState());
				logger.info("Category name -------------------->"+member.getCategoryname());
	
				ArrayList<String> hotelList = null;
				try {
	
					if(member.getCategoryname().equalsIgnoreCase("----Select----")){
						hotelList = new ArrayList<String>();
						hotelList.add("Category is not selected.");
					}
					else {
						hotelList = bo.getName(member);
						if(hotelList.size()==0) {
							logger.info("No data found");
							hotelList.add("No record found.");
						}
					}
			
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				return new ResponseEntity<ArrayList<String>>(hotelList, HttpStatus.CREATED);
	
			*/}	
		
		// 
				
		
		// -------------- get All reservation details -----------------
		  Member m=null;
		  ArrayList<Member> list = null;
		 
		
		 @CrossOrigin(origins = "http://localhost:80")
		 @RequestMapping(value="/getAllReservationDetails",method=RequestMethod.GET)
		 public ResponseEntity<?> getAllReservationDetails(){
			 logger.info("---------- getAllReservationDetails Called ------------");
			 int temp = 1;
			 Member member = new Member();
			 try{
				 list = new ArrayList<Member>();
				 member.setSequanceNumber(temp);
				 list = bo.getAllReservationDetails(list,member);
			 }catch(Exception e){
				 logger.info("getAllReservationDetails Exception ---->"+e.getMessage());
			 }
			 finally{

			 }
			 return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);
		}
			
		// ---------------- get All GLG member for New status ------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getMyReservationDetails",method=RequestMethod.GET)
		public ResponseEntity<?> getMyReservationDetails(String primaryKeyStr)
		{
			logger.info("--------- getMyReservationDetails Called ---------");
			logger.info("Primary Key ------------->"+primaryKeyStr);
			list = new ArrayList<Member>();
			list = bo.getMyReservationDetails(list, primaryKeyStr);//(list,primaryKeyStr);
		    return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);

		}
	
		 
		// ---------------- get My Profile view ------------
			 @CrossOrigin(origins = "http://localhost:80")
			 @RequestMapping(value="/getMyProfile",method=RequestMethod.GET)
			 public ResponseEntity<?> getMyProfile(String primaryKeyStr)
				{
				// Member member = new Member();
				 	member = new Member();
				 	member.setUserloginPrimaryKeyString(primaryKeyStr); // set the user login primary key			 	
				 	member = bo.getMyProfile(member);
					logger.info("-------------- getMyProfile Called--------------");
				    return new ResponseEntity<Member>(member, HttpStatus.CREATED);
	
				}
	
		//GLGMem glgmem=null;
		//ArrayList<GLGMem> glglist = null;	 
	// ------------------- Load my GLG Member list method --------------------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getMyMemberList",method=RequestMethod.GET)
		public ResponseEntity<?> getMyMemberList(@RequestParam String memberNumber) {
			logger.info("[UI Application] Member Number ---------------->"+memberNumber);
			ArrayList<GLGMem> myMemList = null;
			//GLGMem glgmem=null;
			try{
				myMemList = new ArrayList<GLGMem>();
				myMemList = bo.getMyMemberList(memberNumber,myMemList);				
			}catch(Exception e) {
				
			}finally {
				
			}
		    return new ResponseEntity<ArrayList<GLGMem>>(myMemList, HttpStatus.CREATED);
		
		}
		// ------------------- Load ALL GLG Member list method --------------------------------------
		@CrossOrigin(origins = "http://localhost:80")
			 @RequestMapping(value="/getAllMemberList",method=RequestMethod.GET)
			 public ResponseEntity<?> getAllMemberList(@RequestParam String requestType)
				
			{
				logger.info("Request type ------------>"+requestType);
				 ArrayList<GLGMem> glglist = null;	
					logger.info("-------------- getAllMemberList Called --------------");
					glglist = new ArrayList<GLGMem>();
					glglist = bo.getAllMemberList(requestType,glglist);
					return new ResponseEntity<ArrayList<GLGMem>>(glglist, HttpStatus.CREATED);
			
				}
	
			
			
	   // ---------------------- Approval -------------------------
	
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getApproved",method=RequestMethod.GET)
		public ResponseEntity<?>  getApproved(@RequestParam int userLoginPrimaryKey,@RequestParam String requestType) {
			logger.info("Primary Key -------------->"+userLoginPrimaryKey);
			try {
				user = new User();
				logger.info("Before Calling BO -->"+requestType);
				user = bo.getApproved(user,userLoginPrimaryKey,requestType);
				logger.info("Successfully Called BO -->");
				//user.setStatus("success");
			}catch(Exception e) {
				user.setStatus("Network Error Please try again");
			}finally {
				
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		  }		
		
	// ------------------------- reservation approval ---------------------
			@CrossOrigin(origins = "http://localhost:80")
			@RequestMapping(value="/getApprovedForReservation",method=RequestMethod.GET)
			public ResponseEntity<?>  getApprovedForReservation(@RequestParam int userLoginPrimaryKey,@RequestParam String requestType) {
				logger.info("getApprovedForReservation - Primary Key -------------->"+userLoginPrimaryKey);
	
				try {
					user = new User();
					logger.info("Before Calling BO -->"+requestType);
					user = bo.getApprovedForReservation(user,userLoginPrimaryKey,requestType);
					logger.info("Successfully Called BO -->");
					//user.setStatus("success");
				}catch(Exception e) {
					user.setStatus("Network Error Please try again");
				}finally {
					
				}
				return new ResponseEntity<User>(user, HttpStatus.OK);
			  }	
			
			 // ---------------- get My Comm and Overriding ------------
			 @CrossOrigin(origins = "http://localhost:80")
			 @RequestMapping(value="/getMyCommandOverInfo",method=RequestMethod.GET)
			 public ResponseEntity<?> getMyCommandOverInfo(String primaryKeyStr)
			 {
					ArrayList<GLGMem> myMemList = null;
	
				    GLGMem glgmem = new GLGMem();
					myMemList = new ArrayList<GLGMem>();
					glgmem.setUserloginPrimaryKeyString(primaryKeyStr); // set the user login primary key			 	
					myMemList = bo.getMyCommandOverInfo(primaryKeyStr,myMemList);
					logger.info("-------------- getMyCommandOverInfo Called--------------");
				    return new ResponseEntity<ArrayList<GLGMem>>(myMemList, HttpStatus.CREATED);
	
			 }
			 	// ---------------- get My Comm and Overriding ------------
				@CrossOrigin(origins = "http://localhost:80")
				@RequestMapping(value="/getCountryInfo",method=RequestMethod.GET)
				public ResponseEntity<?> getCountryInfo(String selectedCountry,String selectedState,String categoryname)
				{
					logger.info("-------------- getCountryInfo Called--------------");
					hotelList = null;
					int temp = 1;
					String ImagePath = "WebCompanyList";
					try{
						logger.info("Chooseen Country --->"+selectedCountry);
						logger.info("Chooseen State --->"+selectedState);
						logger.info("Chooseen Category --->"+categoryname);
					    Member member = new Member();
					    this.hotelList = new ArrayList<Member>();
					    member.setSelectedCountry(selectedCountry);
					    member.setSelectedState(selectedState);
					    member.setCategoryname(categoryname); 
					    member.setSequanceNumber(temp);
						this.hotelList = bo.getCountryInfo(member,hotelList,ImagePath);
					}catch(Exception e){
						logger.info("UI Application Exception -->"+e.getMessage());
					}
					finally{
						
					}
				    return new ResponseEntity<ArrayList<Member>>(hotelList, HttpStatus.CREATED);
		
				}
			 //
			 
			ArrayList<String> loadCountry=null;
			ArrayList<String> loadState=null;
	
	
			// -------------------- drop down value --------------
				@CrossOrigin(origins = "http://localhost:80")
				@RequestMapping(value="/loadCountryList",method=RequestMethod.GET)
				public ResponseEntity<?> loadAllCountry()
				{
				   try {
						logger.info("----------- Inside loadAllCountry -----------");
						loadCountry = new ArrayList<String>();
						loadCountry=bo.loadCountryList(loadCountry);	
						//Set<String> hs = new HashSet<>();
						//hs.addAll(loadCountry);
						//loadCountry.clear();
						//loadCountry.addAll(hs);
						for(String s : loadCountry) {
							logger.info("Country name -->"+s);
						}
						}catch(Exception e){
							logger.info("UI Application Exception -->"+e.getMessage());
						//e.printStackTrace();
					}finally{
						
					}
					return new ResponseEntity<ArrayList<String>>(loadCountry, HttpStatus.CREATED);
	
				}	
	
			
			ArrayList<String> loadlistState=null;
		// -------------------- drop down value --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/loadState",method=RequestMethod.GET)
		public ResponseEntity<?> loadState(@RequestParam String country)
		{
			try {
				loadlistState = new ArrayList<String>();
				loadlistState = bo.loadState(loadlistState,country);
				for(String s : loadlistState) {
					logger.info("State Name ---->"+s);
				}
			   	return new ResponseEntity<ArrayList<String>>(loadlistState, HttpStatus.CREATED);
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());		
			}
		    return new ResponseEntity<ArrayList<String>>(loadlistState, HttpStatus.CREATED);
	
		}		
		
		ArrayList<String> loadlistCategory=null;
	
		// -------------------- Load Category down value --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/loadCategory",method=RequestMethod.GET)
		public ResponseEntity<?> loadCategory(@RequestParam String selectedCountry,@RequestParam String selectedState)
		{
			logger.info("[loadCategory Country] ---------------->"+selectedCountry);
			logger.info("[loadCategory State] ---------------->"+selectedState);
	
			try {
				loadlistCategory = new ArrayList<String>();
				loadlistCategory = bo.loadCategory(loadlistCategory,selectedCountry,selectedState);
				for(String s : loadlistCategory) {
					logger.info("Category name ---->"+s);
				}
			   	return new ResponseEntity<ArrayList<String>>(loadlistCategory, HttpStatus.CREATED);
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());	
			}
		    return new ResponseEntity<ArrayList<String>>(loadlistCategory, HttpStatus.CREATED);
	
		}	
		// My Profile update method 
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/updateMyProfile",method=RequestMethod.POST)
		public ResponseEntity<?> updateMyProfile(@RequestBody Member member)
		{
			logger.info("Primary Key -->"+member.getUserloginPrimaryKeyString());
			logger.info("Bank Name -->"+member.getBankName());
			logger.info("First name -->"+member.getFirstName());
			logger.info("Last Name -->"+member.getLastName());
			logger.info("Bank A/C number -->"+member.getBankAcctNumber());
			logger.info("Country -->"+member.getCountry());
			logger.info("Phone Number -->"+member.getPhoneNumber());
			logger.info("Email -->"+member.getEmailID());
		   try {
			  logger.info("Before Calling BO");
			  member= bo.updateMyProfile(member);
			  logger.info("Successfully Called BO");
			   return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			   
		   }catch(Exception e){
					logger.info("UI Application Exception -->"+e.getMessage());
					member.setStatus("failure");
				    return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			}finally{
				
			}
		}	
		
		//------- withdrawal form ------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/submitWith",method=RequestMethod.POST)
		public ResponseEntity<Member> submitWith(@RequestBody Member member) {
			logger.info("Inside withdraw method");
			logger.info("Total Amount----->"+member.getTotalAmount());
			logger.info("MemberID----> "+member.getMemberID());
			
			if(member.getTotalAmount() == 0){ 
				logger.info(" Withdraw Amount is Equal to zero ");
				member.setStatus("exsist");
			}else {
			 	logger.info("--- Both Withdraw and Total Amount are Equal ---");
			 	member = bo.submitWith(member);
			}
			
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		//----------- Get All Withdraw List ----------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getAllWithdrawList",method=RequestMethod.GET)
		public ResponseEntity<?> getAllWithdrawList(@RequestParam String requestType){
			logger.info("-------------- getAllWithdrawList Called--------------");
			ArrayList<Member> withdrawList = null;	
			try{
				logger.info("Request type ------------>"+requestType);
				withdrawList = new ArrayList<Member>();
				withdrawList = bo.getAllWithdrawList(requestType,withdrawList);
				return new ResponseEntity<ArrayList<Member>>(withdrawList, HttpStatus.CREATED);
	
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				
			}		 
			return new ResponseEntity<ArrayList<Member>>(withdrawList, HttpStatus.CREATED);		
		}
				
		//---------------- Get Approved Withdraw ----------------		
		@CrossOrigin(origins="http://localhost:80")
		@RequestMapping(value="/getApproveForWithdraw",method=RequestMethod.GET)
		public ResponseEntity<?> getApproveForWithdraw(@RequestParam double memberCommition,@RequestParam double memberOvrriding,@RequestParam String memberID,@RequestParam int primaryKey,
				@RequestParam String requestType){
			logger.info("----------------[UI] Insdide getApproveForWithdraw Method ---------------");
			Member member = null;
			try{
				logger.info("Primary Key -------------->"+primaryKey);
				logger.info("Request Type -------------->"+requestType);
				logger.info("Member Number -------------->"+memberID);
				member = new Member();
				member.setMemberNumber(memberID);
				member.setCommition(memberCommition);
				member.setOverriding(memberOvrriding); 
				member.setTotalAmount(memberCommition + memberOvrriding); 
				member = bo.getApproveForWithdraw(member,primaryKey,requestType);
				logger.info("Successfully Called BO -->");			
			}catch(Exception e){
				member.setStatus("failure"); 
				e.printStackTrace();  
			}
			finally{
				
			}
			return new ResponseEntity<Member>(member,HttpStatus.OK);
		}
	
		// ------------------------------ Save reservation -----------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/saveCategory",method=RequestMethod.POST)
		public ResponseEntity<Member> saveCategory(@RequestBody Member member) {
		   logger.info("------------------ Inside saveCategory Create -----------------");
	       logger.info("[UiApplication] Country ------->"+member.getSelectedCountry()); // Country
	       logger.info("[UiApplication] Category ----------->"+member.getCategoryname()); // Category
	       logger.info("[UiApplication] Description  ----------->"+member.getDescription());
		   try 
		   {
			   member= bo.saveCategory(member); 
			   logger.info("Response -------------->"+member.getStatus());
		   }catch(Exception e) {
			   logger.info("Exception ------------->"+e.getMessage());
		   }finally{
			   
		   }
		  	return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
				
		// ------------------------------ Save reservation -----------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getAllCategoryDetails",method=RequestMethod.GET)
		public ResponseEntity<?> getAllCategoryDetails(){
			logger.info("-------------- getAllCategoryDetails Called--------------");
			categoryList = null;
			try{
				this.categoryList =  new ArrayList<Member>();
				this.categoryList = bo.getAllCategoryList(categoryList);
				logger.info("Category List ---------->"+categoryList.size());
				return new ResponseEntity<ArrayList<Member>>(categoryList, HttpStatus.CREATED);
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				
			}		 
			return new ResponseEntity<ArrayList<Member>>(categoryList, HttpStatus.CREATED);		
		}
		
		//------------ Get Category Details Based on category Name ------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getCategoryView",method=RequestMethod.GET)
		public ResponseEntity<?> getCategoryView(String categoryname,int userLoginPrimaryKey){
			logger.info("-------------- getCategoryView Called--------------");
			Member member = null;
			try{
				logger.info("Category Name --------->"+categoryname);
				logger.info("userLoginPrimaryKey --->"+userLoginPrimaryKey);
				member = new Member();
				for(Member m : categoryList) {
					if(m.getUserLoginPrimaryKey()==userLoginPrimaryKey) {
						logger.info("CategoryPK -------------->"+userLoginPrimaryKey);
						logger.info("List CategoryPK -------------->"+m.getUserLoginPrimaryKey());
						member.setCategoryname(m.getCategoryname());
						member.setSelectedCountry(m.getSelectedCountry());
						member.setSelectedState(m.getSelectedState());
						member.setDescription(m.getDescription());
						member.setCategoryCode(m.getCategoryCode());
						member.setUserLoginPrimaryKey(m.getUserLoginPrimaryKey()); 
					}
				}
				logger.info("Category Code --->"+member.getCategoryCode()); 
				return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				
			}		 
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);	
		}
				
		//---------- Update Category --------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/setCategoryUpdate",method=RequestMethod.POST)
		public ResponseEntity<Member> setCategoryUpdate(@RequestBody Member member) {
		   logger.info("------------------ Inside setCategoryUpdate -----------------");		      
	       logger.info("[UiApplication] Category ----------->"+member.getCategoryname());
	       logger.info("[UiApplication] Description  ----------->"+member.getDescription());
	       logger.info("[UiApplication] Selected Country --------->"+member.getSelectedCountry()); 
	       logger.info("[UiApplication] State  ----------->"+member.getSelectedState());
	       logger.info("[UiApplication] Category Primary Key  ----------->"+member.getUserLoginPrimaryKey());
		   try 
		   {
			   for(Member m : categoryList) {
					if(m.getUserLoginPrimaryKey()==member.getUserLoginPrimaryKey()) {
						logger.info("category Pk both equal  ----------------->"+m.getUserLoginPrimaryKey() + "  ==  " +member.getUserLoginPrimaryKey()); 
						member.setCategoryname(member.getCategoryname());
						member.setSelectedCountry(member.getSelectedCountry());
						member.setSelectedState(member.getSelectedState());
						member.setDescription(member.getDescription());
						member.setCategoryCode(member.getCategoryCode()); 
						member= bo.setCategoryUpdate(member); 
					}
				}	
			   logger.info("Response -------------->"+member.getStatus());
		   }catch(Exception e) {
			   logger.info("Exception ------------->"+e.getMessage());
		   }finally{
			   
		   }
		  	return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		// Remove Category Method - Use DELETE 		
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/setCategoryRemove",method=RequestMethod.DELETE)
		public ResponseEntity<Member> setCategoryRemove(String categoryname,int userLoginPrimaryKey)
		{
			logger.info("Category Remove Called....");
			Member member = null;
			String response = null;
			try {
				member = new Member();
				for(Member m : categoryList) {
					if(m.getUserLoginPrimaryKey()==userLoginPrimaryKey) {
						response = bo.setCategoryRemove(m.getUserLoginPrimaryKey());
					}
				}
				member.setStatus(response);
			}catch(Exception e) {
				member.setStatus("failure");
				logger.info("Exception -->"+e.getMessage());
			}finally {
				
			}
		return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		//----------- get Company View --------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getCompanyView",method=RequestMethod.GET)
		public ResponseEntity<?> getCompanyView(String cname,int userLoginPrimaryKey){
			logger.info("-------------- getCompanyView Called--------------");
			Member member = null;
			try{
				logger.info("Company Name --------->"+cname);
				logger.info("userLoginPrimaryKey --->"+userLoginPrimaryKey);
				member = new Member();
				for(Member m : hotelList) {
					if(m.getUserLoginPrimaryKey()==userLoginPrimaryKey) {
						logger.info("CompanyPK -------------->"+userLoginPrimaryKey);
						logger.info("List CompanyPK -------------->"+m.getUserLoginPrimaryKey());
						member.setCategoryname(m.getCategoryname());
						member.setSelectedCountry(m.getSelectedCountry());
						member.setSelectedState(m.getSelectedState());
						member.setCname(m.getCname()); 
						member.setDescription(m.getDescription());
						member.setPhoneNumber(m.getPhoneNumber());
						member.setEmailID(m.getEmailID()); 
						member.setUserLoginPrimaryKey(m.getUserLoginPrimaryKey()); 
						member.setUserloginPrimaryKeyString(m.getUserloginPrimaryKeyString()); 
						logger.info("User Login PrimaryString --------->"+member.getUserloginPrimaryKeyString()); 
					}
				}
				return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				
			}		 
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);	
		}
		
		//--------- Set Company Update ---------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/setCompanyUpdate",method=RequestMethod.POST)
		public ResponseEntity<Member> setCompanyUpdate(@RequestBody Member member)
		{
			logger.info("------------ Inside setCompanyUpdate method -------------");
			String reponse ="failure";
			logger.info("Company Name -------------->"+member.getCname());
			logger.info("Category Name  ----------->"+member.getCategoryname());  
			logger.info("Country Name -------------->"+member.getSelectedCountry());
			logger.info("State Name --------->"+member.getSelectedState());
			logger.info("Description --------->"+member.getDescription());
			logger.info("Primary Key --------->"+member.getUserLoginPrimaryKey());
			try {
				for(Member mem : hotelList) {
					if(mem.getUserLoginPrimaryKey()==member.getUserLoginPrimaryKey()) {
						logger.info("Company Pk both equal  ----------------->"+mem.getUserLoginPrimaryKey() + "  ==  " +member.getUserLoginPrimaryKey());																	
						member.setCategoryname(member.getCategoryname());
						member.setCname(member.getCname());
						member.setSelectedCountry(member.getSelectedCountry());
						member.setSelectedState(member.getSelectedState()); 
						member.setDescription(member.getDescription());
						member.setPhoneNumber(member.getPhoneNumber());
						member.setEmailID(member.getEmailID());
						reponse = bo.setCompanyUpdate(member);
						member.setStatus(reponse);
					}
				}			   
		   }catch(Exception e){
				logger.info("UI Application Exception -->"+e.getMessage());
				member.setStatus("failure");
			}finally{
				
			}
		   return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		// Remove Category Method - Use DELETE 		
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/setCompanyRemove",method=RequestMethod.DELETE)
		public ResponseEntity<Member> setCompanyRemove(String cname,int userLoginPrimaryKey)
		{
			logger.info("Company Remove Called....");
			Member member = null;
			String response = null;
			try {
				member = new Member();
				for(Member m : hotelList) {
					if(m.getUserLoginPrimaryKey()==userLoginPrimaryKey) {
						response = bo.setCompanyRemove(m.getUserLoginPrimaryKey());
					}
				}
				member.setStatus(response);
			}catch(Exception e) {
				member.setStatus("failure");
				logger.info("Exception -->"+e.getMessage());
			}finally {
				
			}
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		@PostMapping("/imageUpload")
		@CrossOrigin(origins = "http://localhost:80")
		public ResponseEntity<String> handleImageUpload(@RequestParam("file") MultipartFile file , @RequestParam("uploadPk") String uploadPk ) {
			logger.info("-------- GGL Primary Key -------"+uploadPk);
			String uploadStatus = "updateImage";
			String message = "";
			try {
				String status = storageService.store(file,uploadPk,uploadStatus);	
				if(status.equalsIgnoreCase("Success")){
					bo.UpdateImage(uploadPk);
				}else if(status.equalsIgnoreCase("failure")){
					logger.info("---- Failed to Upload File ------");
				}
				files.add(file.getOriginalFilename());
				message = "You successfully uploaded " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				message = "FAIL to upload " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}
		
		// ---------------- get My Comm and Overriding ------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getCompanyInfo",method=RequestMethod.GET)
		public ResponseEntity<?> getCompanyInfo(@RequestParam String selectedCountry)
		{
			logger.info("-------------- getCompanyInfo Called--------------");
			hotelList = null;
			int temp = 2;
			String ImagePath = "WebCompanyList";
			try{
				logger.info("Chooseen Country --->"+selectedCountry);
			    Member member = new Member();
			    this.hotelList = new ArrayList<Member>();
			    member.setSelectedCountry(selectedCountry);
			    member.setSequanceNumber(temp); 
				this.hotelList = bo.getCountryInfo(member,hotelList,ImagePath);
			}catch(Exception e){
				logger.info("UI Application Exception -->"+e.getMessage());
			}
			finally{
				
			}
		    return new ResponseEntity<ArrayList<Member>>(hotelList, HttpStatus.CREATED);

		}
		
		//------- getMyReservation View ------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getMyReservationView",method=RequestMethod.GET)
		public ResponseEntity<?> getMyReservationView(@RequestParam String userloginPrimaryKeyString,@RequestParam String invoiceNumber){
			logger.info("Booking ID ------------>"+userloginPrimaryKeyString);
			logger.info("invoiceNumber ------------>"+invoiceNumber);
			try{
				logger.info("-------------- getMyReservationView Called--------------");
				member = bo.getMyReservationView(userloginPrimaryKeyString,member);
			}catch(Exception e){
				logger.info("[UI] ReservationView Exception -------->"+e.getMessage());
			}
			finally{
				
			}
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			//return new ResponseEntity<ArrayList<Member>>(reservelist, HttpStatus.CREATED);
		}

		// -------------------- drop down value for Category --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getAllCategoryList",method=RequestMethod.GET)
		public ResponseEntity<?> getAllCategoryList()
		{
			logger.info("--------- GetAll CategoryList ----------");
			ArrayList<String> categoryList = null;
			try {
				categoryList = new ArrayList<String>();
				categoryList.add("Food and hotels");
				categoryList.add("Ticketing");
				categoryList.add("Travel and Tour");
				categoryList.add("Financial Solution");
				categoryList.add("Education");
				categoryList.add("Insurance");
				//categoryList.add("Medical Treatment");
				categoryList.add("Health Accessories");
				categoryList.add("Herbal Product");
				categoryList.add("Umrah");
				categoryList.add("Software And Hardware");
				//categoryList.add("Energy Saving");				
			}catch(Exception e) {
				logger.info("Exception ------------->"+e.getMessage());
			}
			return new ResponseEntity<ArrayList<String>>(categoryList, HttpStatus.CREATED);
		}
		
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getprimaryKey",method=RequestMethod.GET)
		public ResponseEntity<?> getprimaryKey(){
			logger.info("-------------- getprimaryKey Called--------------");
			try{
				member = new Member();
			 	member = bo.getprimaryKey(member);				
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				
			}
			    return new ResponseEntity<Member>(member, HttpStatus.CREATED);
	
		}
		
		@PostMapping("/saveImage")
		@CrossOrigin(origins = "http://localhost:80")
		public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile file , @RequestParam("uploadPk") String uploadPk ) {
			logger.info("-------- GGL Primary Key -------"+uploadPk);
			String uploadStatus = "updateImage";
			String message = "";
			try {
				int imagePk = Integer.valueOf(uploadPk)+1;
				logger.info("imagePk --------->"+imagePk);
				String primaryKey = String.valueOf(imagePk);
				logger.info("Primary Key --------->"+primaryKey);
				String status = storageService.store(file,primaryKey,uploadStatus);	
				if(status.equalsIgnoreCase("Success")){
					bo.UpdateImage(primaryKey);
				}else if(status.equalsIgnoreCase("failure")){
					logger.info("---- Failed to Upload File ------");
				}
				files.add(file.getOriginalFilename());
				message = "You successfully uploaded " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				message = "FAIL to upload " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}
		
		/*	// ------------------------------ Save reservation -----------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/publicUnitSave",method=RequestMethod.POST)
		public ResponseEntity<?>  publicUnitSave(@RequestBody Member member) {
			ArrayList<Member> list = new ArrayList<Member>();
		   logger.info("------------------ Inside publicUnitSave  -----------------");
		   logger.info("------------------ Number of Units  -----------------"+member.getNumberofUnit());
		   int noUnit = member.getNumberofUnit();
		   try 
		   {
			 //  for(int i=0;i<2;i++) {
				   for(int i=0;i<noUnit;i++) {

				   member = new Member();
				   member.setInvoiceNumber("0001");
				   list.add(member);

			   }

				  
				   
			 //  member= bo.saveReservation(member,1); 
		   }catch(Exception e) {
			   logger.info("Exception ------------->"+e.getMessage());
		   }finally{
			   
		   }
		  	return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);
			//return new ResponseEntity<Member>(member, HttpStatus.CREATED);
	
		}
		
		*/
		
		//------- getMyReservation View ------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getMemberDetails",method=RequestMethod.GET)
		public ResponseEntity<?> getMemberDetails(@RequestParam String memberID,@RequestParam int userLoginPrimaryKey){
			logger.info("User Login ID ------------>"+userLoginPrimaryKey);
			logger.info("Member ID ------------>"+memberID);
			try{
				logger.info("-------------- getMemberDetails Called--------------");
				member = bo.getMemberDetails(userLoginPrimaryKey,member);
			}catch(Exception e){
				logger.info("[UI] getMemberDetails Exception -------->"+e.getMessage());
			}
			finally{
				
			}
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		// Remove Member - Use DELETE 		
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/setMemberRemove",method=RequestMethod.DELETE)
		public ResponseEntity<Member> setMemberRemove(String memberID,String refmemberID,int userLoginPrimaryKey,String userloginPrimaryKeyString)
		{
			logger.info("Member Remove Called....");
			Member member = null;
			try {
				member = new Member();
				member.setMemberID(memberID);
				member.setRefmemberID(refmemberID);
				member.setUserLoginPrimaryKey(userLoginPrimaryKey);
				member.setUserloginPrimaryKeyString(userloginPrimaryKeyString);
				logger.info("Member ID ------>"+member.getMemberID());
				logger.info("Ref Member ID ------>"+member.getRefmemberID());
				logger.info("User Details ID ------>"+member.getUserLoginPrimaryKey());
				member = bo.setMemberRemove(member);	
			}catch(Exception e) {
				member.setStatus("failure");
				logger.info("[UI]MemberRemove Exception -->"+e.getMessage());
			}finally {
				
			}
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}	
		
		@CrossOrigin(origins="http://localhost:80")
		@RequestMapping(value="/searchHotel",method=RequestMethod.GET)
		public ResponseEntity<?> searchHotel(String cname,String categoryname,String selectedCountry,String selectedState){
			Member member = null;
			hotelList = null;
			try{
				logger.info("Selected Country ----------->"+selectedCountry);
				logger.info("Selected State ----------->"+selectedState);
				logger.info("Hotel Name ----------->"+cname);
				logger.info("Category Name ----------->"+categoryname);
				member = new Member();
				this.hotelList = new ArrayList<Member>();
				member.setSelectedCountry(selectedCountry);
				member.setSelectedState(selectedState); 
				member.setCname(cname);
				member.setCategoryname(categoryname);
				this.hotelList = bo.searchHotel(hotelList,member);
				
			}catch(Exception e){
				logger.info("[UI]SearchHotel Exception -------->"+e.getMessage()); 
			}
			finally{
				
			}			
			return new ResponseEntity<ArrayList<Member>>(hotelList, HttpStatus.CREATED);
		}
		
		// Remove Member - Use DELETE 		
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/setBookingRemove",method=RequestMethod.DELETE)
		public ResponseEntity<Member> setBookingRemove(String invoiceNumber,int userLoginPrimaryKey)
		{
			logger.info("Booking Remove Called....");
			Member member = null;
			try {
				member = new Member();
				member.setInvoiceNumber(invoiceNumber);
				member.setUserLoginPrimaryKey(userLoginPrimaryKey);
				logger.info("Invoice Number ------>"+member.getInvoiceNumber());
				logger.info("Booking ID ------>"+member.getUserLoginPrimaryKey());
				member = bo.setBookingRemove(member);	
			}catch(Exception e) {
				member.setStatus("failure");
				logger.info("[UI]setBookingRemove Exception -->"+e.getMessage());
			}finally {
				
			}
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		@PostMapping("/saveBookingImage")
		@CrossOrigin(origins = "http://localhost:80")
		public ResponseEntity<String> saveBookingImage(@RequestParam("file") MultipartFile file , @RequestParam("uploadPk") String uploadPk ) {
			logger.info("-------- Booking Primary Key -------"+uploadPk);
			String uploadStatus = "bookingImage";
			String message = "";
			try {		
				String status = storageService.store(file,uploadPk,uploadStatus);	
				if(status.equalsIgnoreCase("Success")){
					bo.UpdateBookingImage(uploadPk);
				}else if(status.equalsIgnoreCase("failure")){
					logger.info("---- Failed to Upload File ------");
				}
				files.add(file.getOriginalFilename());
				message = "You successfully uploaded " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				message = "FAIL to upload " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}
		
		// -------------------- drop down value for Product Name  --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getProductName",method=RequestMethod.POST)
		public ResponseEntity<?> getProductName(@RequestBody Member member)
		{
			ArrayList<String> productNameList = null;
			logger.info("Country name ------------>"+member.getSelectedCountry());
			logger.info("State name -------------->"+member.getSelectedState());
			logger.info("Category name ----------->"+member.getCategoryname());			
			try {
				productNameList = new ArrayList<String>();
				productNameList = bo.getProductName(productNameList,member);
				for(String productName : productNameList) { 
					logger.info("Product Names --------->"+productName);
				}
				return new ResponseEntity<ArrayList<String>>(productNameList, HttpStatus.CREATED);
		
			}catch(Exception e) {
				logger.info("Product Exception ------------->"+e.getMessage());
			}
			return new ResponseEntity<ArrayList<String>>(productNameList, HttpStatus.CREATED);
		}
		
		//-------- Get Price Based on Product -------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getProductPrice",method=RequestMethod.GET)
		public ResponseEntity<?> getProductPrice(String companyname){
			logger.info("Company Name ----"+companyname); 
		 	member = new Member();
		 	member.setCname(companyname);		 	
		 	member = bo.getProductPrice(member);
		    return new ResponseEntity<Member>(member, HttpStatus.CREATED);

		}
		
		//------- Get Withdraw Detailed List -------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getMyWithdrawList",method=RequestMethod.GET)
		public ResponseEntity<?> getMyWithdrawList(@RequestParam String memberNumber) {
			logger.info("--------- Inside getMyWithdrawList Calling ---------");
			logger.info("[UI Application] Member Number -------->"+memberNumber);
			ArrayList<Member> withdrawList = null;
			try{
				withdrawList = new ArrayList<Member>();
				withdrawList = bo.getWithdrawDetails(withdrawList,memberNumber);				
			}catch(Exception e) {
				logger.info("Withdraw List Exception ------------->"+e.getMessage());
			}finally {
				
			}
		    return new ResponseEntity<ArrayList<Member>>(withdrawList, HttpStatus.CREATED);	
		}
		
		// -------------------- drop down value for Bank Name --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getBankName",method=RequestMethod.GET)
		public ResponseEntity<?> getBankName()
		{
			ArrayList<String> bankList = null;
			try {
				logger.info("Bank Name -------->"+bankName);
				bankList = new ArrayList<String>();
				bankList.add("DBS BANK");
				bankList.add(bankName);				
			}catch(Exception e) {
				logger.info("bankList Exception ------------->"+e.getMessage());
			}
			return new ResponseEntity<ArrayList<String>>(bankList, HttpStatus.CREATED);
		}
		
		// -------------------- drop down value for Bank Transfer Form --------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getBankTransfer",method=RequestMethod.GET)
		public ResponseEntity<?> getBankTransfer()
		{
			ArrayList<String> bankTransferList = null;
			try {
				logger.info("Bank Transfer -------->"+bankTransfer);
				bankTransferList = new ArrayList<String>();
				bankTransferList.add("DBS BANK");
				bankTransferList.add(bankTransfer);	
				bankTransferList.add("CIMB NIAGA");
				bankTransferList.add("Bank Rakyat Indonesia");
				bankTransferList.add("Bank Negara Indonesia");
				bankTransferList.add("Bank Mandiri");
				bankTransferList.add("Bank Danamon");
				bankTransferList.add("Bank Bukopin");
				bankTransferList.add("Bank BTN");
			}catch(Exception e) {
				logger.info("bankList Exception ------------->"+e.getMessage());
			}
			return new ResponseEntity<ArrayList<String>>(bankTransferList, HttpStatus.CREATED);
		}
		
		// ------------------------------ member Payment Registration -----------------------------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/memberPayment",method=RequestMethod.POST)
		public ResponseEntity<Member>  memberPayment(@RequestBody Member member) {
		   logger.info("------------------ Inside memberPayment Calling -----------------");
	       logger.info("[UiApplication-memberPayment] Admin Bank Name ------------>"+member.getBankName()); 
	       logger.info("[UiApplication-memberPayment] Admin Account Number ----------->"+member.getAdminacctNumber()); 
	       logger.info("[UiApplication-memberPayment] Admin Account Name ----------->"+member.getAdminacctName()); 
	       logger.info("[UiApplication-memberPayment] Transfer Bank Name ----------->"+member.getBanktransfer());
	       logger.info("[UiApplication-memberPayment] Transfer Account Number ----------->"+member.getBankAcctNumber());
	       logger.info("[UiApplication-memberPayment] Transfer Account Name ------------>"+member.getUsername());
	       logger.info("[UiApplication-memberPayment] MemberID ----------->"+member.getMemberID());
	       logger.info("[UiApplication-memberPayment] Tree Name ------------>"+member.getTreeName());
	       logger.info("[UiApplication-memberPayment] Invoice Number ----------->"+member.getInvoiceNumber());
		   try 
		   {
			  member= bo.memberPayment(member); 
			  logger.info("Payment Response -------------->"+member.getStatus());
		   }catch(Exception e) {
			   member.setStatus("failure");
			   logger.info("Member Payment Exception ------------->"+e.getMessage());
		   }finally{
			   
		   }
		  	return new ResponseEntity<Member>(member, HttpStatus.CREATED);	
		}
		
		//-------- Get Categoty Search Details --------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getCategorySearch",method=RequestMethod.GET)
		public ResponseEntity<?> getCategorySearch(@RequestParam String categoryname)
		{
			logger.info("-------------- getCategorySearch Called--------------");
			list = null;
			int temp = 2;
			Member member = new Member();
			try{
				logger.info("Category Name --->"+categoryname);
			   this.list = new ArrayList<Member>();
			   member.setCategoryname(categoryname);
			   member.setSequanceNumber(temp);
			   this.list = bo.getAllReservationDetails(list,member);
			}catch(Exception e){
				logger.info("[UI Application]getCategorySearch Exception -->"+e.getMessage());
			}
			finally{
	
			}
			return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);
		}
		
		//--------- Get Member Random Number -------
		@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getrandomNumber",method=RequestMethod.GET)
		public ResponseEntity<?> getrandomNumber(){
			logger.info("-------------- getrandomNumber Called--------------");
			try{
				member = new Member();
			 	member = bo.getrandomNumber(member);				
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				
			}
			    return new ResponseEntity<Member>(member, HttpStatus.CREATED);
	
		}
}
		
