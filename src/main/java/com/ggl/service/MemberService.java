package com.ggl.service;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.beans.factory.annotation.Autowire;

//import javax.enterprise.inject.Produces;

 import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
/*import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
*/import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
/*import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import java.util.stream.Collectors;
*/
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
//import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ggl.bo.GglBo;
import com.ggl.dto.Member;
import com.ggl.dto.User;
import com.ggl.mongo.dal.MemberDAL;
import com.ggl.mongo.model.MemberDetails;
//import com.ggl.mongo.dal.RandomNumberRepository;
import com.ggl.service.MemberService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@RestController
public class MemberService implements Filter{
	
	public static final Logger logger = LoggerFactory.getLogger(MemberService.class);
		
	
	@Autowired
	GglBo investmentBo;


	//private final RandamNumberRepository randamNumberRepository;

	//private final RandomNumberDAL randamNumberDAL;
	private final MemberDAL memberDAL;

	List<String> publicfiles = new ArrayList<String>();
	List<String> privatefiles = new ArrayList<String>();
	List<String> ownfiles = new ArrayList<String>();
	
	public MemberService(MemberDAL memberDAL) {
	//	this.randamNumberRepository = randamNumberRepository;
		this.memberDAL = memberDAL;
	}


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
	
	

			/*// -------------------Create a Member -------------------------------------------
			@CrossOrigin(origins = "http://35.162.40.190:80")
			@RequestMapping(value="/addMember",method=RequestMethod.POST)
			public ResponseEntity<Member>  addMember(@RequestBody Member member) {
			   logger.info("------------------ Inside Member Create -----------------");
			   logger.info("[UiApplication] Member ID ----------->"+member.getRefmemberID()); // referance member ID
		       logger.info("[UiApplication] Email ID ------------->"+member.getEmailID()); // Email
		       logger.info("[UiApplication] Country ----------->"+member.getSelectedCountry()); // Country
		       logger.info("[UiApplication] Phone number ----------->"+member.getPhoneNumber()); // Phone number	      
		       logger.info("[UiApplication] First name ----------->"+member.getFirstName()); // First name
		       logger.info("[UiApplication] Last name ----------->"+member.getLastName()); // Last name 
		       logger.info("[UiApplication] User name ----------->"+member.getUsername()); // User name 
		       logger.info("[UiApplication] Password ----------->"+member.getPassword()); // Password	       
		       logger.info("[UiApplication] Bank name ----------->"+member.getBankName()); // bank name
		       logger.info("[UiApplication] Acct Number ----------->"+member.getBankAcctNumber()); // bank account number 
		       logger.info("[UiApplication] Acct type  ----------->"+member.getActType()); // bank account type
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
			*/
			
	User user;
	// ----------------------- Member ID validate ----------------
			//@CrossOrigin(origins = "http://35.162.40.190:80")
			@RequestMapping(value="/validateMember",method=RequestMethod.GET)
			public ResponseEntity<?>  validateMember(@RequestParam String memberID) {
				boolean UIResponse = false;
				try {
					user = new User();
					logger.info("Member Number  -->"+memberID);
					logger.info("Before Calling BO");
					UIResponse = memberDAL.validateMember(memberID);
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
			
			
			// Temporary Save Own Tree 		
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/savemember",method=RequestMethod.POST)
			public ResponseEntity<?>  savemember(@RequestBody Member member) {
			//ArrayList<Member> list = new ArrayList<Member>();
				int newTempCode=0;
		  @SuppressWarnings("unused")
		String newCodeFinal=null;
		   logger.info("------------------ Inside savemember  -----------------");
		   logger.info("------------------ Inside Member Create -----------------");
		   logger.info("[UiApplication] Member ID ----------->"+member.getRefmemberID()); // referance member ID
	       logger.info("[UiApplication] Email ID ------------->"+member.getEmailID()); // Email
	       logger.info("[UiApplication] Country ----------->"+member.getSelectedCountry()); // Country
	       logger.info("[UiApplication] Phone number ----------->"+member.getPhoneNumber()); // Phone number	      
	       logger.info("[UiApplication] First name ----------->"+member.getFirstName()); // First name
	       logger.info("[UiApplication] Last name ----------->"+member.getLastName()); // Last name 
	       logger.info("[UiApplication] User name ----------->"+member.getUsername()); // User name 
	       logger.info("[UiApplication] Password ----------->"+member.getPassword()); // Password	       
	       logger.info("[UiApplication] Bank name ----------->"+member.getBankName()); // bank name
	       logger.info("[UiApplication] Acct Number ----------->"+member.getBankAcctNumber()); // bank account number 
	       logger.info("[UiApplication] Acct type  ----------->"+member.getActType()); // bank account type
		   try 
		   {	   
			   // Member ID Check start
			   logger.info("Before Calling the Randam Number choose ----------------------");
				newTempCode=memberDAL.getRandomNumber(newTempCode,"CurrentGLGCode");
				logger.info("Successfully Called the Randam Number choose ----------------------");	
				logger.info("New Generated Randam Number ---------------->"+newTempCode);
				MemberDetails memberDetails=new MemberDetails();  

				// Member ID Check end
				if(member.getActType().equalsIgnoreCase("Silver"))  {
					newCodeFinal = "GGLM"+newTempCode;
					memberDetails.setStatus("Pending");
					memberDetails.setWithdraw_status("Not Available");
					logger.info("Silver New Generated Randam Number ---------------->"+newTempCode);
				}
				
				if(member.getActType().equalsIgnoreCase("Gold"))  {
					newCodeFinal = "GGLF"+newTempCode;
					memberDetails.setStatus("Pending");
					memberDetails.setWithdraw_status("Not Available");
					logger.info("GOLD New Generated Randam Number ---------------->"+newTempCode);
				}
				if(member.getActType().equalsIgnoreCase("Platinum"))  {
					newCodeFinal = "GGLMF"+newTempCode;
					memberDetails.setStatus("Pending");
					memberDetails.setWithdraw_status("Not Available");
					
					logger.info("Platinum New Generated Randam Number ---------------->"+newTempCode);
				}
			logger.info("Before Saving member");
			
			memberDAL.saveMember(memberDetails);//.updateRandamNumber(document);	
				   
			logger.info("After Saving member");

				   
		   }catch(Exception e) {
			   logger.info("Exception ------------->"+e.getMessage());
			   e.printStackTrace();
		   	}finally{
			   
		   	}
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			}

	
			
	
	
}
		
