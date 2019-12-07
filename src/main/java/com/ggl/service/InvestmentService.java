package com.ggl.service;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.beans.factory.annotation.Autowire;

//import javax.enterprise.inject.Produces;

 import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.http.HttpHeaders;

import java.util.stream.Collectors;
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
import com.ggl.email.InvestmentEmail;
import com.ggl.model.UserDetail;
import com.ggl.mongo.dal.PublicTreeDAL;
import com.ggl.mongo.dal.RandomNumberDAL;
import com.ggl.mongo.model.MiniTree;
//import com.ggl.mongo.dal.RandomNumberRepository;
import com.ggl.mongo.model.OwnTree;
import com.ggl.mongo.model.PrivateTree;
import com.ggl.mongo.model.Publictree;
import com.ggl.mongo.model.RandomNumber;
import com.ggl.mongo.model.TempMiniTree;
import com.ggl.mongo.model.TempOwnTree;
import com.ggl.mongo.model.TempPrivateTree;
import com.ggl.mongo.model.TempPublicTree;
import com.ggl.service.InvestmentService;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@RestController
public class InvestmentService implements Filter{
	
	public static final Logger logger = LoggerFactory.getLogger(InvestmentService.class);
		
	
	@Autowired
	GglBo investmentBo;


	//private final RandamNumberRepository randamNumberRepository;

	private final RandomNumberDAL randamNumberDAL;
	private final PublicTreeDAL publicTreeDAL;

	List<String> publicfiles = new ArrayList<String>();
	List<String> privatefiles = new ArrayList<String>();
	List<String> ownfiles = new ArrayList<String>();
	List<String> minifiles = new ArrayList<String>();
	
	public InvestmentService(RandomNumberDAL randamNumberDAL,PublicTreeDAL publicTreeDAL) {
	//	this.randamNumberRepository = randamNumberRepository;
		this.randamNumberDAL = randamNumberDAL;
		this.publicTreeDAL = publicTreeDAL;
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
	
	
	// Temp Public Tree Load Data
	//http://localhost:8080
	
	// -------------------- Load Tree Name value --------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/getTempPublicTree",method=RequestMethod.GET)
		public ResponseEntity<?> getTempPublicTree()
		{
			logger.info("------------- Inside getTempPublicTree-----------------");
			List<TempPublicTree> responseList=null;
			//responseList = new ArrayList<TempPublicTree>();
		   try {
				logger.info("-----------Inside getTempPublicTree Called----------");
				//responseList = new ArrayList<TempPublicTree>();
				responseList=publicTreeDAL.getTempPublicTree();
				
				}catch(Exception e){
				logger.info("Exception ------------->"+e.getMessage());
				e.printStackTrace();
			}finally{
				
			}
			return new ResponseEntity<List<TempPublicTree>>(responseList, HttpStatus.CREATED);

		}
			
		

		// Temporary Public Tree Save
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/TempPublicTreeUnitSave",method=RequestMethod.POST)
		public ResponseEntity<?>  TempPublicTreeUnitSave(@RequestBody Member member) {
			ArrayList<Member> list = new ArrayList<Member>();
			logger.info("------------------ Inside TemppublicUnitSave  -----------------");
			logger.info("------------------ Number of TemppublicUnitSave  -----------------"+member.getNumberofUnit());
			int noUnit = member.getNumberofUnit();
			int userID = member.getUserLoginPrimaryKey();
			try {	   
			   	 TempPublicTree temppublictree;
				 RandomNumber randamNumber=  randamNumberDAL.getTempPublicRandomNumber();
			     for(int i=0;i<noUnit;i++) {
				   temppublictree = new TempPublicTree();
				   temppublictree.setPaymentStatus("NOT PAID");
				   temppublictree.setPayAmount(100);
				   temppublictree.setNumberofUnits(1);
				   temppublictree.setUserID(userID);
				   temppublictree.setCurrency("SGD");
				   temppublictree.setUserID(userID);
		
				   int curInNumber = randamNumber.getPublicCount();
				   int nextInvoiceNumber = curInNumber+1;	
				   temppublictree.setInvoiceCode(randamNumber.getPublicInvCode() + nextInvoiceNumber);
				   
				   publicTreeDAL.insertTempPublicUser(temppublictree);//.updateRandamNumber(document);	
				   
				   RandomNumber document = new RandomNumber();
				   document.setPublicCount(nextInvoiceNumber);
				   randamNumberDAL.updateTempPublicRandamNumber(document);
				   
				   // Generate Temporary Invoice
				   // Response 
				   member = new Member();
				   member.setInvoiceNumber(temppublictree.getInvoiceCode());
				   member.setTotalAmount(noUnit*100);
				   member.setPayAmt(temppublictree.getPayAmount()); 
				   list.add(member);
				   randamNumber=  randamNumberDAL.getTempPublicRandomNumber();
		
			  }
			  logger.info("Temp Public Primary Key --->"+userID);
		      // Get Email ID 
		      UserDetail userdetails = investmentBo.getMemberEmailID(userID);
		    		 
			  if(userdetails.getEmail1().isEmpty()) {
				 logger.info("----------- No Email ID----------------");
			  }
			  else{
				 logger.info("----------- Found Email ID----------------");
				 // Push Email start 
			     InvestmentEmail.tempPublicTree(list,userdetails.getEmail1());
			     // Push Email End
			 }
		   }catch(Exception e) {
			   logger.info("Exception ------------->"+e.getMessage());
			   e.printStackTrace();
		   }finally{
			   
		   }
		   return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);
		}

	
	
			// Temporary Save Own Tree 		
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/TempOwnTreeUnitSave",method=RequestMethod.POST)
			public ResponseEntity<?>  TempOwnTreeUnitSave(@RequestBody Member member) {
			ArrayList<Member> list = new ArrayList<Member>();
		   logger.info("------------------ Inside Temp Own Tree Save  -----------------");
		   logger.info("------------------ Number of Temp Own Tree Count  -----------------"+member.getNumberofUnit());
		   int noUnit = member.getNumberofUnit();
		   int userID = member.getUserLoginPrimaryKey();
		   try 
		   {	   
				 RandomNumber randamNumber=  randamNumberDAL.getTempPublicRandomNumber();

			   TempOwnTree tempowntree;
				   tempowntree = new TempOwnTree();
				   tempowntree.setPaymentStatus("NOT PAID");
				   tempowntree.setPayAmount(noUnit * 100);
				   tempowntree.setNumberofUnits(noUnit);
				   tempowntree.setUserID(userID);
				   tempowntree.setCurrency("SGD");
				   
				   
				   int curInNumber = randamNumber.getOwnCount();
				   int nextInvoiceNumber = curInNumber+1;	
				   tempowntree.setInvoiceCode(randamNumber.getOwnInvCode() + nextInvoiceNumber);
				   
				   
				   publicTreeDAL.insertTempOwnTreeUser(tempowntree);//.updateRandamNumber(document);	
				   
				   
				   RandomNumber document = new RandomNumber();
				   document.setOwnCount(nextInvoiceNumber);
				   randamNumberDAL.updateTempOwnTreeandamNumber(document);
				   // Response 
				   member = new Member();
				   member.setInvoiceNumber(tempowntree.getInvoiceCode());
				   member.setTreeName("Not Yet Created.");
				   member.setTotalAmount(noUnit*100);
				   list.add(member);
				   
				   logger.info("Temp Own Primary Key --->"+userID);
				     // Get Email ID 
				     UserDetail userdetails = investmentBo.getMemberEmailID(userID);
				    		 
					 if(userdetails.getEmail1().isEmpty()) {
						 logger.info("----------- No Email ID----------------");
					}
					else{
						 logger.info("----------- Found Email ID----------------");

						 // Push Email start 
					     InvestmentEmail.tempOwnTree(list,userdetails.getEmail1());
					     // Push Email End
					}
				   
		   }catch(Exception e) {
			   logger.info("Exception ------------->"+e.getMessage());
			   e.printStackTrace();
		   	}finally{
			   
		   	}
		  	return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);
			}

	
		// Temporary Save Private Tree
	
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/TempPrivateTreeUnitSave",method=RequestMethod.POST)
		public ResponseEntity<?>  TempPrivateTreeUnitSave(@RequestBody Member member) {
		ArrayList<Member> list = new ArrayList<Member>();
	   logger.info("------------------ Inside TempPrivateTreeUnitSave  -----------------");
	   logger.info("------------------ Number of TempPrivateTreeUnitSave  -----------------"+member.getNumberofUnit());
	   logger.info("------------------ Tree Name -----------------"+member.getRefmemberID());

	   int noUnit = member.getNumberofUnit();
	   int userID = member.getUserLoginPrimaryKey();
	   String treeName = member.getRefmemberID();
	   try 
	   {	   
		 TempPrivateTree tempprivatetree;
		 RandomNumber randamNumber=  randamNumberDAL.getTempPublicRandomNumber();

		   for(int i=0;i<noUnit;i++) {
			   tempprivatetree = new TempPrivateTree();
			   tempprivatetree.setPaymentStatus("NOT PAID");
			   tempprivatetree.setPayAmount(100);
			   tempprivatetree.setNumberofUnits(1);
			   tempprivatetree.setUserID(userID);
			   tempprivatetree.setCurrency("SGD");
			   tempprivatetree.setTreeName(treeName);
			   
			   int curInNumber = randamNumber.getPrivateCount();
			   int nextInvoiceNumber = curInNumber+1;	
			   tempprivatetree.setInvoiceCode(randamNumber.getPrivateInvCode() + nextInvoiceNumber);
			   
			   
			   publicTreeDAL.insertTempPrivateTreeUser(tempprivatetree);//.updateRandamNumber(document);	
			   
			   
			   RandomNumber document = new RandomNumber();
			   document.setPrivateCount(nextInvoiceNumber);
			   randamNumberDAL.updateTempPrivateRandamNumber(document);
			   
			   // Generate Temporary Invoice
			   // Response 
			   member = new Member();
			   member.setInvoiceNumber(tempprivatetree.getInvoiceCode());
			   member.setTotalAmount(noUnit*100);
			   list.add(member);
			   randamNumber=  randamNumberDAL.getTempPublicRandomNumber();
			  }
		   
		   logger.info("Temp Private Primary Key --->"+userID);
		     // Get Email ID 
		     UserDetail userdetails = investmentBo.getMemberEmailID(userID);
		    		 
			 if(userdetails.getEmail1().isEmpty()) {
				 logger.info("----------- No Email ID----------------");
			}
			else{
				 logger.info("----------- Found Email ID----------------");

				 // Push Email start 
			     InvestmentEmail.tempPrivateTree(list,userdetails.getEmail1());
			     // Push Email End
			}
		   
	   }catch(Exception e) {
		   logger.info("Exception ------------->"+e.getMessage());
		   e.printStackTrace();
	   	}finally{
		   
	   	}
	  	return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);
		}

		
		// Temp Own Tree Load Data

		// -------------------- Load Tree Name value --------------
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/getTempOwnTree",method=RequestMethod.GET)
			public ResponseEntity<?> getTempOwnTree()
			{
				logger.info("------------- Inside getTempOwnTree-----------------");
				List<TempOwnTree> responseList=null;
				//responseList = new ArrayList<TempPublicTree>();
			   try {
					logger.info("-----------Inside getTempOwnTree Called----------");
					//responseList = new ArrayList<TempPublicTree>();
					responseList=publicTreeDAL.getTempOwnTree();
					
					}catch(Exception e){
					logger.info("Exception ------------->"+e.getMessage());
					e.printStackTrace();
				}finally{
					
				}
				return new ResponseEntity<List<TempOwnTree>>(responseList, HttpStatus.CREATED);

			}
		
					
		
		// Temp Private Tree Load Data
		
		// -------------------- Load Tree Name value --------------
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/getTempPrivateTree",method=RequestMethod.GET)
			public ResponseEntity<?> getTempPrivateTree()
			{
				logger.info("------------- Inside getTempPrivateTree-----------------");
				List<TempPrivateTree> responseList=null;
				//responseList = new ArrayList<TempPublicTree>();
			   try {
					logger.info("-----------Inside getTempPrivateTree Called----------");
					//responseList = new ArrayList<TempPublicTree>();
					responseList=publicTreeDAL.getTempPrivateTree();
					
					}catch(Exception e){
					logger.info("Exception ------------->"+e.getMessage());
					e.printStackTrace();
				}finally{
					
				}
				return new ResponseEntity<List<TempPrivateTree>>(responseList, HttpStatus.CREATED);

			}
		
			// -------------------- Load Tree Name value --------------
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/approvePublicUnit",method=RequestMethod.POST)
			public ResponseEntity<?> approvePublicUnit(@RequestBody Member member)
			{
				logger.info("approvePublicUnit Invoice Code --->"+member.getRefmemberID());
				TempPublicTree response=null;
			   try {
					logger.info("-----------Inside approvePublicUnit Called----------");
					response=publicTreeDAL.getTempPublicSingleUnitTree(member.getRefmemberID());
					// Before Calling...
					member = new Member();
					//member.setRefmemberID(response.getTreeName());
					member.setNumberofUnit(1);
					member.setUserLoginPrimaryKey(response.getUserID());					
					boolean status = savePublicTree(member);
					if(status==true) {
						logger.info("Good!!!!");
						member.setStatus("good");
						// Remove temp data start
						boolean getStatus = publicTreeDAL.RemoveTempPublicSingleUnitTree(response);
						logger.info("Output-->"+getStatus);						
					}else {
						member.setStatus("bad");
						logger.info("Error!!!!");
					}
				}catch(Exception e){
					logger.info("Exception ------------->"+e.getMessage());
					e.printStackTrace();
				}finally{
					
				}
				return new ResponseEntity<Member>(member, HttpStatus.CREATED);

			}
			
			// Reject Private Tree
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/rejectPrivate",method=RequestMethod.POST)
			public ResponseEntity<?> rejectPrivate(@RequestBody Member member)
			{
				//logger.info("----------Alex--------------------");
				//Member member=null;
				logger.info("Reject Private Invoice Code --->"+member.getRefmemberID());
				logger.info("------------- Inside rejectPrivate-----------------");
			   try {
					logger.info("-----------Before Calling  rejectPublicUnit ----------");
					publicTreeDAL.rejectPrivatecUnit(member.getRefmemberID());
					logger.info("-----------Successfully Called  rejectPublicUnit ----------");
					// Before Calling...
					member = new Member();
					member.setStatus("good");
					// After Called...
					}catch(Exception e){
						member.setStatus("bad");

					logger.info("Exception ------------->"+e.getMessage());
					e.printStackTrace();
				}finally{
					
				}
				return new ResponseEntity<Member>(member, HttpStatus.CREATED);

			}
				
			// Reject Own Tree
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/rejectOwnTree",method=RequestMethod.POST)
			public ResponseEntity<?> rejectOwnTree(@RequestBody Member member)
			{
				//logger.info("----------Alex--------------------");
				//Member member=null;
				logger.info("Reject rejectOwnTree Invoice Code --->"+member.getRefmemberID());
				logger.info("------------- Inside rejectOwnTree-----------------");
			   try {
					logger.info("-----------Before Calling  rejectOwnTree ----------");
					publicTreeDAL.rejectOwnTree(member.getRefmemberID());
					logger.info("-----------Successfully Called  rejectOwnTree ----------");
					// Before Calling...
					member = new Member();
					member.setStatus("good");
					// After Called...
					}catch(Exception e){
						member.setStatus("bad");

					logger.info("Exception ------------->"+e.getMessage());
					e.printStackTrace();
				}finally{
					
				}
				return new ResponseEntity<Member>(member, HttpStatus.CREATED);

			}
			
			// Reject Public
			// -------------------- Load Tree Name value --------------
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/rejectPublic",method=RequestMethod.POST)
			public ResponseEntity<?> rejectPublic(@RequestBody Member member)
			{
				//logger.info("----------Alex--------------------");
				//Member member=null;
				logger.info("rejectPublic Invoice Code --->"+member.getRefmemberID());
				logger.info("------------- Inside approvePublicUnit-----------------");
				//responseList = new ArrayList<TempPublicTree>();
			   try {
					logger.info("-----------Before Calling  rejectPublicUnit ----------");
					publicTreeDAL.rejectPublicUnit(member.getRefmemberID());
					logger.info("-----------Successfully Called  rejectPublicUnit ----------");
					// Before Calling...
					member = new Member();
					member.setStatus("good");
					// After Called...
					}catch(Exception e){
						member.setStatus("bad");

					logger.info("Exception ------------->"+e.getMessage());
					e.printStackTrace();
				}finally{
					
				}
				return new ResponseEntity<Member>(member, HttpStatus.CREATED);

			}
						
		
			public boolean  savePublicTree(Member member) {
				boolean status=false;
				logger.info("------------------ Inside publicUnitSave  -----------------");
				logger.info("------------------ Number of Units  -----------------"+member.getNumberofUnit());
				int userID = member.getUserLoginPrimaryKey();
				try {
					RandomNumber randamNumber=  randamNumberDAL.getAllRandamNumber();
					logger.info("User ID / Primary Key e-->"+member.getUserLoginPrimaryKey());
					logger.info("Invoice Code-->"+randamNumber.getInvoiceCode());
					logger.info("Current Invoice-->"+randamNumber.getInvocieNumber());
					logger.info("Current Queue Number-->"+randamNumber.getCurrentqueueNumber());
					logger.info("Out Queue Number-->"+randamNumber.getNextOutqueueNumber());
					Publictree publictree;	
					int curInNumber = randamNumber.getInvocieNumber();
					int nextInvoiceNumber = curInNumber+1;			   
					int curQueNumber = randamNumber.getCurrentqueueNumber();
					int nextcurQueNumber = curQueNumber+1;			   
					publictree = new Publictree();
					publictree.setQueueNumber(nextcurQueNumber);
					publictree.setUserQueueStatus("IN");
					publictree.setInvoiceNumber(randamNumber.getInvoiceCode() + nextInvoiceNumber);
					publictree.setPaymentStatus("PAID");
					publictree.setPayAmount(100);
					publictree.setNumberofUnits(1);
					publictree.setUserID(userID);
					publictree.setCurrency("SGD");
					publictree.setUserstatus("Pending");					   
					// curInNumber ++;
					logger.info("New Invoice Number------------>"+publictree.getInvoiceNumber());
					publicTreeDAL.insertUser(publictree);//.updateRandamNumber(document);			
					RandomNumber document = new RandomNumber();
					document.setCurrentqueueNumber(nextcurQueNumber);
					document.setInvocieNumber(nextInvoiceNumber);		   
					if(randamNumber.getCurrentqueueNumber()==randamNumber.getNextOutqueueNumber()) {
						document.setNextOutqueueNumber(randamNumber.getNextOutqueueNumber()+8);
						Publictree publictreeupdate = new Publictree();
						publictreeupdate=publicTreeDAL.get8ComeOneOut();
						logger.info("8 Come one Out Value ---------->"+publictreeupdate.getInvoiceNumber());
						publicTreeDAL.updateOutNumber(publictreeupdate,userID);
				   }
					randamNumberDAL.updateRandamNumber(document);
			   		logger.info("Save Public Primary Key --->"+userID);
			   		
		     		// Get Email ID 
		     		UserDetail userdetails = investmentBo.getMemberEmailID(userID);	    		 
			 		if(userdetails.getEmail1().isEmpty()) {
				 		logger.info("----------- No Email ID----------------");
					}
					else{
						 logger.info("----------- Found Email ID----------------");
						 // Push Email start 
					     InvestmentEmail.approvePublicTree(publictree,userdetails.getEmail1());
					     // Push Email End
					}
			   		// Send Email end 
			   		status=true;
				}catch(Exception e) {
					status = false;
	
				}finally {
					
				}
				return status;
			}
			
			// -------------------- Load Tree Name value --------------
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/approvePrivateUnit",method=RequestMethod.POST)
			public ResponseEntity<?> approvePrivateUnit(@RequestBody Member member)
			{
				//logger.info("----------Alex--------------------");
				//Member member=null;
				logger.info("Invoice Code --->"+member.getRefmemberID());
				logger.info("------------- Inside getTempPrivateSingleUnitTree-----------------");
				TempPrivateTree response=null;
				//responseList = new ArrayList<TempPublicTree>();
			   try {
					logger.info("-----------Inside getTempPrivateSingleUnitTree Called----------");
					response=publicTreeDAL.getTempPrivateSingleUnitTree(member.getRefmemberID());
					// Before Calling...
					member = new Member();
					member.setRefmemberID(response.getTreeName());
					member.setNumberofUnit(1);
					member.setUserLoginPrimaryKey(response.getUserID());					
					boolean status = savePrivate(member);
					if(status==true) {
						logger.info("Good!!!!");
						member.setStatus("good");
						// Remove temp data start
						boolean getStatus = publicTreeDAL.RemoveTempPrivateSingleUnitTree(response);
						logger.info("Output-->"+getStatus);
						
						// Remove temp data end

					}else {
						member.setStatus("bad");

						logger.info("Error!!!!");
					}
					// After Called...
					}catch(Exception e){
					logger.info("Exception ------------->"+e.getMessage());
					e.printStackTrace();
				}finally{
					
				}
				return new ResponseEntity<Member>(member, HttpStatus.CREATED);

			}
						
			public boolean  saveOwn(Member member) {
				boolean status = false;
				ArrayList<Member> list = new ArrayList<Member>();
			   logger.info("------------------ Inside saveOwn   -----------------");
			   logger.info("------------------ Referance Number   -----------------"+member.getRefmemberID());
			   logger.info("------------------ Number saveOwn of Units  -----------------"+member.getNumberofUnit());
			   int noUnit = member.getNumberofUnit();
			   int userID = member.getUserLoginPrimaryKey();
			  // String tempRefNo = member.getRefmemberID();
			   try {
				   RandomNumber randamNumber=  randamNumberDAL.getAllOwnTreeRandomNumber();
					// RandomNumber randamNumber = publicTreeDAL.getAllOwnTreeRandomNumber();
					 if(randamNumber!=null) {
						 logger.info("Some value....");
					 }
					 else {
						 logger.info("No Value.......");
					 }
					 logger.info("createOwnTree User ID / Primary Key e-->"+member.getUserLoginPrimaryKey());
					 logger.info("createOwnTree Invoice Code-->"+randamNumber.getInvoiceCode());
					 logger.info("createOwnTree Current Invoice-->"+randamNumber.getInvocieNumber());
					 logger.info("createOwnTree Current Queue Number-->"+randamNumber.getCurrentqueueNumber());
					 logger.info("createOwnTree Out Queue Number-->"+randamNumber.getNextOutqueueNumber());
					 PrivateTree privatetree;
					 OwnTree owntree=null;
					 // int tmp = noUnit * 100;
					 int curInNumber = randamNumber.getInvocieNumber();
					 int nextInvoiceNumber = curInNumber+1;			   
					 int curQueNumber = randamNumber.getCurrentqueueNumber();
					 int nextcurQueNumber = curQueNumber+1;			   
					 owntree = new OwnTree();
					   owntree.setCurrentqueueNumber(0);
					   owntree.setNextOutqueueNumber(7);
					   owntree.setInvocieNumber(0);
					   owntree.setInvoiceCode(randamNumber.getInvoiceCode() + nextInvoiceNumber);
					   owntree.setUserID(userID);
					//   privatetree.setInvoiceNumber(randamNumber.getInvoiceCode() + nextInvoiceNumber);
					   logger.info("Tree Name ---------->"+owntree.getInvoiceCode());
					   logger.info("Before Save Own Tree");
					   publicTreeDAL.createOwnTree(owntree);//.updateRandamNumber(document);	
					   logger.info("Successfully Saved Own Tree");
					   
					// Update the parent Random Number 	
					   logger.info("Before update Random Table with  randomID 2....");
					   RandomNumber rddocument = new RandomNumber();
					   rddocument.setCurrentqueueNumber(nextcurQueNumber);
					   rddocument.setInvocieNumber(nextInvoiceNumber);		   
					   randamNumberDAL.updateRandamNumberForOwnTree(rddocument);
					   logger.info("Successfully updated Random Table with randomID 2....");
					   
					   OwnTree owntreeRandamNumber = publicTreeDAL.getAllPrivateTreeRandomNumber(owntree.getInvoiceCode());

					   for(int i=0;i<noUnit;i++) {
		   				   
						   int owntree1 = owntreeRandamNumber.getInvocieNumber();//.getInvocieNumber();
						   int nextInvoiceNumberOwntree = owntree1+1;			   
						   int curQueForOwnNumber = owntreeRandamNumber.getCurrentqueueNumber();
						   int nextcurQueForOwnNumber = curQueForOwnNumber+1;		
						   
						   privatetree = new PrivateTree();
						   privatetree.setQueueNumber(nextcurQueForOwnNumber);
						   privatetree.setUserQueueStatus("IN");
						   privatetree.setInvoiceNumber(owntreeRandamNumber.getInvoiceCode() + nextInvoiceNumberOwntree);
						   privatetree.setPaymentStatus("PAID");
						   privatetree.setPayAmount(100);
						   privatetree.setNumberofUnits(1);
						   privatetree.setUserID(userID);
						   privatetree.setCurrency("SGD");
						   privatetree.setUserstatus("Pending");
						   privatetree.setReferanceGSPNumber(owntree.getInvoiceCode());
						   // curInNumber ++;
						   logger.info("New Invoice Number------------>"+privatetree.getInvoiceNumber());
						   logger.info("Before Save Own Tree");
						   publicTreeDAL.insertPrivateUser(privatetree);	
						   logger.info("After Save Own Tree");

						   OwnTree document = new OwnTree();
						   document.setCurrentqueueNumber(nextcurQueForOwnNumber);
						   document.setInvocieNumber(nextInvoiceNumberOwntree);	
						   document.setInvoiceCode(owntree.getInvoiceCode());
						   if(owntree.getCurrentqueueNumber()==owntree.getNextOutqueueNumber()) {
							   document.setNextOutqueueNumber(owntree.getNextOutqueueNumber()+8);
							   PrivateTree privatetreeupdate = new PrivateTree();
							   privatetreeupdate=publicTreeDAL.getOwn8ComeOneOut();
							   logger.info("Own Private Tree 8 Come one Out Value Save Own ---------->"+privatetreeupdate.getInvoiceNumber());
							   publicTreeDAL.updatePrivateOutNumber(privatetreeupdate,member);
						   }
						   
						   publicTreeDAL.updatePrivateTreeRandomNumber(document);
						   // Response 
						   member = new Member();
						   member.setInvoiceNumber(privatetree.getInvoiceNumber());
						   member.setTreeName(owntree.getInvoiceCode());
						   member.setTotalAmount(noUnit*100);
						   list.add(member);
						   owntreeRandamNumber = publicTreeDAL.getAllPrivateTreeRandomNumber(owntree.getInvoiceCode());

					   }
					   
					   String owntreeName = owntree.getInvoiceCode();
					   logger.info("Save Own Primary Key --->"+userID);
					     // Get Email ID 
					     UserDetail userdetails = investmentBo.getMemberEmailID(userID);
					    		 
						 if(userdetails.getEmail1().isEmpty()) {
							 logger.info("----------- No Email ID----------------");
						}
						else{
							 logger.info("----------- Found Email ID----------------");

							 // Push Email start 
						     InvestmentEmail.approveOwnTree(list,userdetails.getEmail1(),owntreeName);
						     // Push Email End
						}   
					   
					   
					   status=true;

			 }catch(Exception e) {
				   logger.info("Exception ------------->"+e.getMessage());
				   status=false;
				   e.printStackTrace();
			   }finally {
				   
			   }
			   
				return status;
			}

			public boolean  savePrivate(Member member) {
				boolean status = false;
			ArrayList<Member> list = new ArrayList<Member>();
		   logger.info("------------------ Inside privateUnitSave   -----------------");
		   logger.info("------------------ Referance Number   -----------------"+member.getRefmemberID());

		   logger.info("------------------ Number privateUnitSave of Units  -----------------"+member.getNumberofUnit());
		   int noUnit = member.getNumberofUnit();
		   int userID = member.getUserLoginPrimaryKey();
		   String tempRefNo = member.getRefmemberID();
		   try 
		   {	   
			 OwnTree randamNumber = publicTreeDAL.getAllPrivateTreeRandomNumber(member.getRefmemberID());
			 logger.info("privateUnitSave User ID / Primary Key e-->"+member.getUserLoginPrimaryKey());
			 logger.info("privateUnitSave Invoice Code-->"+randamNumber.getInvoiceCode());
			 logger.info("privateUnitSave Current Invoice-->"+randamNumber.getInvocieNumber());
			 logger.info("privateUnitSave Current Queue Number-->"+randamNumber.getCurrentqueueNumber());
			 logger.info("privateUnitSave Out Queue Number-->"+randamNumber.getNextOutqueueNumber());
			 PrivateTree privatetree;
				   int curInNumber = randamNumber.getInvocieNumber();
				   int nextInvoiceNumber = curInNumber+1;			   
				   int curQueNumber = randamNumber.getCurrentqueueNumber();
				   int nextcurQueNumber = curQueNumber+1;	
				   privatetree = new PrivateTree();
				   privatetree.setQueueNumber(nextcurQueNumber);
				   privatetree.setUserQueueStatus("IN");
				   privatetree.setInvoiceNumber(randamNumber.getInvoiceCode() + nextInvoiceNumber);
				   privatetree.setPaymentStatus("PAID");
				   privatetree.setPayAmount(100);
				   privatetree.setNumberofUnits(1);
				   privatetree.setUserID(userID);
				   privatetree.setCurrency("SGD");
				   privatetree.setUserstatus("Pending");
				  // privatetree.setReferanceGSPNumber(member.getRefmemberID());
				   privatetree.setReferanceGSPNumber(tempRefNo);

				  // curInNumber ++;
				   logger.info("New Invoice Number------------>"+privatetree.getInvoiceNumber());
				   logger.info("Before Save One unit to Private Tree....");
				   publicTreeDAL.insertPrivateUser(privatetree);//.updateRandamNumber(document);	
				   logger.info("After Save One unit to Private Tree....");
				   OwnTree document = new OwnTree();
				   document.setCurrentqueueNumber(nextcurQueNumber);
				   document.setInvocieNumber(nextInvoiceNumber);	
				   document.setInvoiceCode(tempRefNo);

				   if(randamNumber.getCurrentqueueNumber()==randamNumber.getNextOutqueueNumber()) {
					   document.setNextOutqueueNumber(randamNumber.getNextOutqueueNumber()+8);
					   PrivateTree privatetreeupdate = new PrivateTree();
					   privatetreeupdate=publicTreeDAL.getPrivate8ComeOneOut();//.get8ComeOneOut();
					   logger.info("Private 8 Come one Out Value Save Private ---------->"+privatetreeupdate.getInvoiceNumber());
					   publicTreeDAL.updatePrivateOutNumber(privatetreeupdate,member);
					   }
				   logger.info("Before update Own Tree....");
				   publicTreeDAL.updatePrivateTreeRandomNumber(document);
				   logger.info("After update Own Tree....");

				   // Response 
				   member = new Member();
				   member.setInvoiceNumber(privatetree.getInvoiceNumber());
				   member.setTotalAmount(noUnit*100);
				   list.add(member);
				   
				   
				   
				   logger.info("Save Private Primary Key --->"+userID);
				     // Get Email ID 
				     UserDetail userdetails = investmentBo.getMemberEmailID(userID);
				    		 
					 if(userdetails.getEmail1().isEmpty()) {
						 logger.info("----------- No Email ID----------------");
					}
					else{
						 logger.info("----------- Found Email ID----------------");

						 // Push Email start 
					     InvestmentEmail.approvePrivateTree(privatetree,userdetails.getEmail1());
					     // Push Email End
					}
				   
				   status=true;
			  
		   }catch(Exception e) {
			   logger.info("Exception ------------->"+e.getMessage());
			   e.printStackTrace();
		   	}finally{
			   
		   	}
		  	return status;
			}
			
		
		
		// -------------------- Load Tree Name value --------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/approveOwnTree",method=RequestMethod.POST)
		public ResponseEntity<?> approveOwnTree(@RequestBody Member member)
		{
			//logger.info("----------Alex--------------------");
			//Member member=null;
			logger.info("Own Invoice Code --->"+member.getRefmemberID());
			logger.info("------------- Inside getTempPrivateSingleUnitTree-----------------");
			TempOwnTree response=null;
			//responseList = new ArrayList<TempPublicTree>();
		   try {
				logger.info("-----------Inside getTempPrivateSingleUnitTree Called----------");
				response=publicTreeDAL.getTempOwnSingleUnitTree(member.getRefmemberID());
				// Before Calling...
				member = new Member();
				//member.setRefmemberID(response.getTreeName());
				member.setNumberofUnit(response.getNumberofUnits());
				member.setUserLoginPrimaryKey(response.getUserID());					
				boolean status = saveOwn(member);
				if(status==true) {
					logger.info("Good!!!!");
					member.setStatus("good");
					// Remove temp data start
					boolean getStatus = publicTreeDAL.RemoveTempOwnSingleUnitTree(response);
					logger.info("Output-->"+getStatus);
					
					// Remove temp data end

				}else {
					member.setStatus("bad");

					logger.info("Error!!!!");
				}
				// After Called...
				}catch(Exception e){
				logger.info("Exception ------------->"+e.getMessage());
				e.printStackTrace();
			}finally{
				
			}
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);

		}
									

		// ------------------------------ Save Public Unit -----------------------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/publicUnitSave",method=RequestMethod.POST)
		public ResponseEntity<?>  publicUnitSave(@RequestBody Member member) {
		ArrayList<Member> list = new ArrayList<Member>();
	   logger.info("------------------ Inside publicUnitSave  -----------------");
	   logger.info("------------------ Number of Units  -----------------"+member.getNumberofUnit());
	   int noUnit = member.getNumberofUnit();
	   int userID = member.getUserLoginPrimaryKey();
	   try 
	   {	   
		 RandomNumber randamNumber=  randamNumberDAL.getAllRandamNumber();
		 logger.info("User ID / Primary Key e-->"+member.getUserLoginPrimaryKey());
		 logger.info("Invoice Code-->"+randamNumber.getInvoiceCode());
		 logger.info("Current Invoice-->"+randamNumber.getInvocieNumber());
		 logger.info("Current Queue Number-->"+randamNumber.getCurrentqueueNumber());
		 logger.info("Out Queue Number-->"+randamNumber.getNextOutqueueNumber());
		 Publictree publictree;
		   for(int i=0;i<noUnit;i++) {
			   int curInNumber = randamNumber.getInvocieNumber();
			   int nextInvoiceNumber = curInNumber+1;			   
			   int curQueNumber = randamNumber.getCurrentqueueNumber();
			   int nextcurQueNumber = curQueNumber+1;			   
			   publictree = new Publictree();
			   publictree.setQueueNumber(nextcurQueNumber);
			   publictree.setUserQueueStatus("IN");
			   publictree.setInvoiceNumber(randamNumber.getInvoiceCode() + nextInvoiceNumber);
			   publictree.setPaymentStatus("NOT PAID");
			   publictree.setPayAmount(100);
			   publictree.setNumberofUnits(1);
			   publictree.setUserID(userID);
			   publictree.setCurrency("SGD");
			   publictree.setUserstatus("Pending");					   
			  // curInNumber ++;
			   logger.info("New Invoice Number------------>"+publictree.getInvoiceNumber());
			   publicTreeDAL.insertUser(publictree);//.updateRandamNumber(document);			
			   RandomNumber document = new RandomNumber();
			   document.setCurrentqueueNumber(nextcurQueNumber);
			   document.setInvocieNumber(nextInvoiceNumber);		   
			   if(randamNumber.getCurrentqueueNumber()==randamNumber.getNextOutqueueNumber()) {
				   document.setNextOutqueueNumber(randamNumber.getNextOutqueueNumber()+7);
				   Publictree publictreeupdate = new Publictree();
				   publictreeupdate=publicTreeDAL.get8ComeOneOut();
				   logger.info("8 Come one Out Value ---------->"+publictreeupdate.getInvoiceNumber());
				   // update by Alex 23-July-2019
				   publicTreeDAL.updateOutNumber(publictreeupdate,userID);
				   }
			   randamNumberDAL.updateRandamNumber(document);
			   // Response 
			   member = new Member();
			   member.setInvoiceNumber(publictree.getInvoiceNumber());
			   member.setTotalAmount(noUnit*100);
			   list.add(member);
			   randamNumber=  randamNumberDAL.getAllRandamNumber();
		   }
	    }catch(Exception e) {
		   logger.info("Exception ------------->"+e.getMessage());
		   e.printStackTrace();
	   	}finally{
		   
	   	}
	  	return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);
		}

	ArrayList<String> response=null;

	// -------------------- Load Tree Name value --------------
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value="/loadTreeName",method=RequestMethod.GET)
	public ResponseEntity<?> loadTreeName()
	{
		logger.info("------------- Inside loadTreeName-----------------");
		List<OwnTree> responseList=null;
		response = new ArrayList<String>();
	   try {
			logger.info("-----------Inside loadTreeName Called----------");
			responseList = new ArrayList<OwnTree>();
			responseList=publicTreeDAL.loadTreeName();
			for(OwnTree ot:responseList) {
				response.add(ot.getInvoiceCode());
			}
			
			}catch(Exception e){
			logger.info("Exception ------------->"+e.getMessage());
			e.printStackTrace();
		}finally{
			
		}
		return new ResponseEntity<ArrayList<String>>(response, HttpStatus.CREATED);

	}
		
	
	// -------------------- drop down value --------------
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value="/getSingleUnitInfo",method=RequestMethod.GET)
	public ResponseEntity<?> getSingleUnitInfo(String primaryKey)
	{
		logger.info("User Primary Key--->"+primaryKey);
		List<Publictree> responseList=null;
	   try {
			logger.info("Server side getMyUnitInfo Called");
			responseList = new ArrayList<Publictree>();
			responseList=publicTreeDAL.getSinglePurchaseUnitByUserId(primaryKey);
			}catch(Exception e){
			logger.info("Exception ------------->"+e.getMessage());
			e.printStackTrace();
		}finally{
			
		}
		return new ResponseEntity<List<Publictree>>(responseList, HttpStatus.CREATED);

	}
			
	
	// -------------------- drop down value --------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/getSinglePrivateUnitInfo",method=RequestMethod.GET)
		public ResponseEntity<?> getSinglePrivateUnitInfo(String primaryKey,String treeName)
		{
			logger.info("User Primary Key--->"+primaryKey);
			logger.info("User Tree Name --->"+treeName);

			List<PrivateTree> responseList=null;
		   try {
				logger.info("Server side getSinglePrivateUnitInfo Called");
				responseList = new ArrayList<PrivateTree>();
				responseList=publicTreeDAL.getSinglePrivatePurchaseUnitByUserId(primaryKey,treeName);
				}catch(Exception e){
				logger.info("Exception ------------->"+e.getMessage());
				e.printStackTrace();
			}finally{
				
			}
			return new ResponseEntity<List<PrivateTree>>(responseList, HttpStatus.CREATED);

		}
		
				

			// ------------------------------ Own Tree -----------------------------
			@CrossOrigin(origins = "http://localhost:8080")
			@RequestMapping(value="/createOwnTree",method=RequestMethod.POST)
			public ResponseEntity<?>  createOwnTree(@RequestBody Member member) {
			ArrayList<Member> list = new ArrayList<Member>();
		   logger.info("------------------ Inside createOwnTree   -----------------");
		   logger.info("------------------ Number privateUnitSave of Units  -----------------"+member.getNumberofUnit());
		   int noUnit = member.getNumberofUnit();
		   int userID = member.getUserLoginPrimaryKey();
		   try 
		   {	   
			 RandomNumber randamNumber=  randamNumberDAL.getAllOwnTreeRandomNumber();
			// RandomNumber randamNumber = publicTreeDAL.getAllOwnTreeRandomNumber();
			 if(randamNumber!=null) {
				 logger.info("Some value....");
			 }
			 else {
				 logger.info("No Value.......");
			 }
			 logger.info("createOwnTree User ID / Primary Key e-->"+member.getUserLoginPrimaryKey());
			 logger.info("createOwnTree Invoice Code-->"+randamNumber.getInvoiceCode());
			 logger.info("createOwnTree Current Invoice-->"+randamNumber.getInvocieNumber());
			 logger.info("createOwnTree Current Queue Number-->"+randamNumber.getCurrentqueueNumber());
			 logger.info("createOwnTree Out Queue Number-->"+randamNumber.getNextOutqueueNumber());
			 PrivateTree privatetree;
			 OwnTree owntree=null;
			// int tmp = noUnit * 100;
				   int curInNumber = randamNumber.getInvocieNumber();
				   int nextInvoiceNumber = curInNumber+1;			   
				   int curQueNumber = randamNumber.getCurrentqueueNumber();
				   int nextcurQueNumber = curQueNumber+1;			   
				  
				   
				   owntree = new OwnTree();
				   owntree.setCurrentqueueNumber(0);
				   owntree.setNextOutqueueNumber(7);
				   owntree.setInvocieNumber(0);
				   owntree.setInvoiceCode(randamNumber.getInvoiceCode() + nextInvoiceNumber);
				   owntree.setUserID(userID);
				//   privatetree.setInvoiceNumber(randamNumber.getInvoiceCode() + nextInvoiceNumber);
				   logger.info("Tree Name ---------->"+owntree.getInvoiceCode());
				   logger.info("Before Save Own Tree");
				   publicTreeDAL.createOwnTree(owntree);//.updateRandamNumber(document);	
				   logger.info("Successfully Saved Own Tree");

				   // Update the parent Random Number 	
				   logger.info("Before update Random Table with  randomID 2....");
				   RandomNumber rddocument = new RandomNumber();
				   rddocument.setCurrentqueueNumber(nextcurQueNumber);
				   rddocument.setInvocieNumber(nextInvoiceNumber);		   
				   randamNumberDAL.updateRandamNumberForOwnTree(rddocument);
				   logger.info("Successfully updated Random Table with randomID 2....");

				   OwnTree owntreeRandamNumber = publicTreeDAL.getAllPrivateTreeRandomNumber(owntree.getInvoiceCode());
				   for(int i=0;i<noUnit;i++) {
					   				   
					   int owntree1 = owntreeRandamNumber.getInvocieNumber();//.getInvocieNumber();
					   int nextInvoiceNumberOwntree = owntree1+1;			   
					   int curQueForOwnNumber = owntreeRandamNumber.getCurrentqueueNumber();
					   int nextcurQueForOwnNumber = curQueForOwnNumber+1;		
					   
					   privatetree = new PrivateTree();
					   privatetree.setQueueNumber(nextcurQueForOwnNumber);
					   privatetree.setUserQueueStatus("IN");
					   privatetree.setInvoiceNumber(owntreeRandamNumber.getInvoiceCode() + nextInvoiceNumberOwntree);
					   privatetree.setPaymentStatus("NOT PAID");
					   privatetree.setPayAmount(100);
					   privatetree.setNumberofUnits(1);
					   privatetree.setUserID(userID);
					   privatetree.setCurrency("SGD");
					   privatetree.setUserstatus("Pending");
					   privatetree.setReferanceGSPNumber(owntree.getInvoiceCode());
					   // curInNumber ++;
					   logger.info("New Invoice Number------------>"+privatetree.getInvoiceNumber());
					   logger.info("Before Save Own Tree");
					   publicTreeDAL.insertPrivateUser(privatetree);	
					   logger.info("After Save Own Tree");

					   OwnTree document = new OwnTree();
					   document.setCurrentqueueNumber(nextcurQueForOwnNumber);
					   document.setInvocieNumber(nextInvoiceNumberOwntree);	
					   document.setInvoiceCode(owntree.getInvoiceCode());
					   if(owntree.getCurrentqueueNumber()==owntree.getNextOutqueueNumber()) {
						   document.setNextOutqueueNumber(owntree.getNextOutqueueNumber()+7);
						   PrivateTree privatetreeupdate = new PrivateTree();
						   privatetreeupdate=publicTreeDAL.getOwn8ComeOneOut();
						   logger.info("Own Private Tree 8 Come one Out Value on Own Tree ---------->"+privatetreeupdate.getInvoiceNumber());
						   publicTreeDAL.updatePrivateOutNumber(privatetreeupdate,member);

						   
						   }
					   
					   publicTreeDAL.updatePrivateTreeRandomNumber(document);
					   // Response 
					   member = new Member();
					   member.setInvoiceNumber(privatetree.getInvoiceNumber());
					   member.setTreeName(owntree.getInvoiceCode());
					   member.setTotalAmount(noUnit*100);
					   list.add(member);
					   owntreeRandamNumber = publicTreeDAL.getAllPrivateTreeRandomNumber(owntree.getInvoiceCode());

				   }
				 

		   }catch(Exception e) {
			   logger.info("Exception ------------->"+e.getMessage());
			   e.printStackTrace();
		   	}finally{
			   
		   	}
		  	return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);
			}
	
	// Validate Own Tree Name for Register the Private Tree Member			
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value="/getOwnTreeNameValidate",method=RequestMethod.GET)
	public ResponseEntity<?>  getOwnTreeNameValidate(@RequestParam String memberID) {
		User user=null;
		try {
			user = new User();			
			 String response = publicTreeDAL.validateOwnTreeName(memberID);
			 user.setStatus(response);
		}catch(Exception e) {
			logger.info("Error -->"+e.getMessage());
			user.setStatus("Network Error Please try again");
		}finally {
			
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	  }
			
		// ------------------------------ Private Tree -----------------------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/privateUnitSave",method=RequestMethod.POST)
		public ResponseEntity<?>  privateUnitSave(@RequestBody Member member) {
		ArrayList<Member> list = new ArrayList<Member>();
	   logger.info("------------------ Inside privateUnitSave   -----------------");
	   logger.info("------------------ Referance Number   -----------------"+member.getRefmemberID());

	   logger.info("------------------ Number privateUnitSave of Units  -----------------"+member.getNumberofUnit());
	   int noUnit = member.getNumberofUnit();
	   int userID = member.getUserLoginPrimaryKey();
	   String tempRefNo = member.getRefmemberID();
	   try 
	   {	   
		// RandomNumber randamNumber=  randamNumberDAL.getAllRandamNumber();
		 OwnTree randamNumber = publicTreeDAL.getAllPrivateTreeRandomNumber(member.getRefmemberID());
		 logger.info("privateUnitSave User ID / Primary Key e-->"+member.getUserLoginPrimaryKey());
		 logger.info("privateUnitSave Invoice Code-->"+randamNumber.getInvoiceCode());
		 logger.info("privateUnitSave Current Invoice-->"+randamNumber.getInvocieNumber());
		 logger.info("privateUnitSave Current Queue Number-->"+randamNumber.getCurrentqueueNumber());
		 logger.info("privateUnitSave Out Queue Number-->"+randamNumber.getNextOutqueueNumber());
		 PrivateTree privatetree;
		   for(int i=0;i<noUnit;i++) {
			   int curInNumber = randamNumber.getInvocieNumber();
			   int nextInvoiceNumber = curInNumber+1;			   
			   int curQueNumber = randamNumber.getCurrentqueueNumber();
			   int nextcurQueNumber = curQueNumber+1;			   
			  
			   
			   privatetree = new PrivateTree();
			   privatetree.setQueueNumber(nextcurQueNumber);
			   privatetree.setUserQueueStatus("IN");
			   privatetree.setInvoiceNumber(randamNumber.getInvoiceCode() + nextInvoiceNumber);
			   privatetree.setPaymentStatus("NOT PAID");
			   privatetree.setPayAmount(100);
			   privatetree.setNumberofUnits(1);
			   privatetree.setUserID(userID);
			   privatetree.setCurrency("SGD");
			   privatetree.setUserstatus("Pending");
			  // privatetree.setReferanceGSPNumber(member.getRefmemberID());
			   privatetree.setReferanceGSPNumber(tempRefNo);

			  // curInNumber ++;
			   logger.info("New Invoice Number------------>"+privatetree.getInvoiceNumber());
			   logger.info("Before Save One unit to Private Tree....");
			   publicTreeDAL.insertPrivateUser(privatetree);//.updateRandamNumber(document);	
			   logger.info("After Save One unit to Private Tree....");
			   OwnTree document = new OwnTree();
			   document.setCurrentqueueNumber(nextcurQueNumber);
			   document.setInvocieNumber(nextInvoiceNumber);	
			   document.setInvoiceCode(tempRefNo);

			   if(randamNumber.getCurrentqueueNumber()==randamNumber.getNextOutqueueNumber()) {
				   document.setNextOutqueueNumber(randamNumber.getNextOutqueueNumber()+7);
				   PrivateTree privatetreeupdate = new PrivateTree();
				   privatetreeupdate=publicTreeDAL.getPrivate8ComeOneOut();//.get8ComeOneOut();
				   logger.info("Private 8 Come one Out Value on Private Tree ---------->"+privatetreeupdate.getInvoiceNumber());
				   publicTreeDAL.updatePrivateOutNumber(privatetreeupdate,member);
				   }
			   logger.info("Before update Own Tree....");
			   publicTreeDAL.updatePrivateTreeRandomNumber(document);
			   logger.info("After update Own Tree....");

			   // Response 
			   member = new Member();
			   member.setInvoiceNumber(privatetree.getInvoiceNumber());
			   member.setTotalAmount(noUnit*100);
			   list.add(member);
			   // Important line
			   logger.info("Before get Own Tree Info based on Tree Name....");
			   randamNumber=  publicTreeDAL.getAllPrivateTreeRandomNumber(tempRefNo);//getAllRandamNumber();
			   logger.info("After get Own Tree Info based on Tree Name....");


		   }
	   }catch(Exception e) {
		   logger.info("Exception ------------->"+e.getMessage());
		   e.printStackTrace();
	   	}finally{
		   
	   	}
	  	return new ResponseEntity<ArrayList<Member>>(list, HttpStatus.CREATED);
		}
		
		@GetMapping("/privatefiles/{filename:.+}")
		@ResponseBody
		public ResponseEntity<Resource> getPrivateTreeFile(@PathVariable String filename) {
			logger.info("------- Inside get PrivateTreeFile Method ---------");
			Resource file = publicTreeDAL.loadPrivateFile(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		}
		
		
		@GetMapping("/publicfiles/{filename:.+}")
		@ResponseBody
		public ResponseEntity<Resource> getPublicTreeFile(@PathVariable String filename) {
			logger.info("------- Inside get PublicTreeFile Method ---------");
			Resource file = publicTreeDAL.loadPublicFile(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		}
		
		@GetMapping("/ownfiles/{filename:.+}")
		@ResponseBody
		public ResponseEntity<Resource> getOwnTreeFile(@PathVariable String filename) {
			logger.info("------- Inside get OwnTreeFile Method ---------");
			Resource file = publicTreeDAL.loadOwnFile(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		}
		
		
		// ----------------------- Member ID validate ----------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/getValidateTempTree",method=RequestMethod.GET)
		public ResponseEntity<?>  getValidateTempTree(@RequestParam String invoiceNumber,@RequestParam String treeName) {
			User user = null;
			try {
				user = new User();

				// Public 
				if(treeName.equalsIgnoreCase("publicTree")) {
					TempPublicTree res =  publicTreeDAL.validatePublic(invoiceNumber);
					if(res != null) {
						logger.info("----------- Public Tree match--------");
						user.setStatus("Valid");

					}else {
						logger.info("----------- Public Tree not match--------");

						user.setStatus("InValid");

					}
				}

				// Private 
				if(treeName.equalsIgnoreCase("privateTree")) {
					TempPrivateTree res =  publicTreeDAL.validatePrivate(invoiceNumber);
					if(res != null) {
						logger.info("----------- Private Tree match--------");

						user.setStatus("Valid");

					}else {
						logger.info("----------- Private Tree not match--------");

						user.setStatus("InValid");

					}
				}

				
				// Own
				if(treeName.equalsIgnoreCase("ownTree")) {
					TempOwnTree res =  publicTreeDAL.validateOwn(invoiceNumber);
					if(res != null) {
						logger.info("----------- Own Tree match--------");

						user.setStatus("Valid");

					}else {
						logger.info("----------- Own Tree not match--------");

						user.setStatus("InValid");

					}
				}
				
				if(treeName.equalsIgnoreCase("miniTree")) {
					TempMiniTree res =  publicTreeDAL.validateMini(invoiceNumber);
					if(res != null) {
						logger.info("----------- Mini Tree match--------");
						user.setStatus("Valid");
					}else {
						logger.info("----------- Mini Tree not match--------");
						user.setStatus("InValid");
					}
				}
				
			}catch(Exception e) {
				logger.info("Error -->"+e.getMessage());
				user.setStatus("Network Error Please try again");
			}finally {
				
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		  }
				
		//=========== Upload the image using invoiceNumber and treeName ==========
		@PostMapping("/paymentImageUplaod")
		@CrossOrigin(origins = "http://localhost:8080")
		public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file ,@RequestParam String invoiceNumber,@RequestParam String treeName) {
			String message = "";
			try {
				logger.info(" --------- Inside paymentImageUplaod Method --------- ");
				
				if(treeName.equalsIgnoreCase("publicTree")) {
					logger.info("-----Selected Pubic Tree--------");
					logger.info("Before Calling ");				
				//TempPublicTree res =  publicTreeDAL.validatePublic(invoiceNumber);
					//if(res != null) {
						logger.info("-------------Match ------");
						publicTreeDAL.storeImage(file,invoiceNumber,treeName);
						publicfiles.add(file.getOriginalFilename());
						 message = "You successfully uploaded " + file.getOriginalFilename() + "!";
						 return ResponseEntity.status(HttpStatus.OK).body(message); 

				}
			
				if(treeName.equalsIgnoreCase("privateTree")) {
					logger.info("-----Selected Private Tree--------");
					
					logger.info("privateTree Before Calling ");				
					//TempPrivateTree res =  publicTreeDAL.validatePrivate(invoiceNumber);
					//if(res != null) {
						logger.info("-------------privateTree Match ------");
						publicTreeDAL.storeImage(file,invoiceNumber,treeName);
						publicfiles.add(file.getOriginalFilename());
						 message = "You successfully uploaded " + file.getOriginalFilename() + "!";
						 return ResponseEntity.status(HttpStatus.OK).body(message);

				}
			
				if(treeName.equalsIgnoreCase("ownTree")) {
					logger.info("-----Selected Own Tree--------");
					logger.info("-----Selected ownTree Tree--------");
					logger.info("ownTree Before Calling ");				
				//	TempOwnTree res =  publicTreeDAL.validateOwn(invoiceNumber);
					//if(res != null) {
						logger.info("-------------Match ------");
						publicTreeDAL.storeImage(file,invoiceNumber,treeName);
						publicfiles.add(file.getOriginalFilename());
						 message = "You successfully uploaded " + file.getOriginalFilename() + "!";
						 return ResponseEntity.status(HttpStatus.OK).body(message); 

			    }
				
				if(treeName.equalsIgnoreCase("miniTree")) {
					logger.info("----- Selected Mini Tree --------");
					logger.info("------- Match ------");
					publicTreeDAL.storeImage(file,invoiceNumber,treeName);
					minifiles.add(file.getOriginalFilename());
					message = "You successfully uploaded " + file.getOriginalFilename() + "!";
					return ResponseEntity.status(HttpStatus.OK).body(message); 
				}
				 return ResponseEntity.status(HttpStatus.OK).body(message); 

			
			} catch (Exception e) {
				 message = "FAIL to upload " + file.getOriginalFilename() + "!";
				 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}
		
		//=========== get the image using invoiceNumber and treeName ==========
		@GetMapping("/getPaymentView")
		@CrossOrigin(origins = "http://localhost:8080")
		public ResponseEntity<List<String>> getPaymentView(@RequestParam String invoiceCode,@RequestParam String treeName) {
			logger.info("------- Inside getPaymentView Method ---------");
			String fileName1 = invoiceCode + ".jpg";
			logger.info("--- fileNmame ----"+fileName1);
			List<String> fileNames = null; 
			logger.info("--- TreeName ----"+treeName);
			if(treeName.equalsIgnoreCase("publicTree")){
				logger.info("--- Inside Public Tree ----");
				fileNames = publicTreeDAL.loadPublicImage().map(fileName -> MvcUriComponentsBuilder
		                .fromMethodName(InvestmentService.class, "getPublicTreeFile", fileName1).build().encode().toString())
		                .collect(Collectors.toList());
				return ResponseEntity.ok().body(fileNames); 
			}else if(treeName.equalsIgnoreCase("privateTree")){
				logger.info("--- Inside Private Tree ----");
				fileNames = publicTreeDAL.loadPrivateImage().map(fileName -> MvcUriComponentsBuilder
		                .fromMethodName(InvestmentService.class, "getPrivateTreeFile", fileName1).build().encode().toString())
		                .collect(Collectors.toList());
				return ResponseEntity.ok().body(fileNames); 
			}else if(treeName.equalsIgnoreCase("ownTree")){
				logger.info("--- Inside own Tree ----");
				fileNames = publicTreeDAL.loadOwnImage().map(fileName -> MvcUriComponentsBuilder
		                .fromMethodName(InvestmentService.class, "getOwnTreeFile", fileName1).build().encode().toString())
		                .collect(Collectors.toList());
				return ResponseEntity.ok().body(fileNames); 
			}else if(treeName.equalsIgnoreCase("miniTree")){
				logger.info("--- Inside Mini Tree ----");
				fileNames = publicTreeDAL.loadMiniImage().map(fileName -> MvcUriComponentsBuilder
		                .fromMethodName(InvestmentService.class, "getMiniTreeFile", fileName1).build().encode().toString())
		                .collect(Collectors.toList());
				return ResponseEntity.ok().body(fileNames); 
			}
			return ResponseEntity.ok().body(fileNames); 
		} 
		
		//------------- Temporary Mini Tree Save ---------------
		@CrossOrigin(origins="localhost:80")
		@RequestMapping(value="/TempMiniTreeUnitSave",method=RequestMethod.POST)
		public ResponseEntity<?> TempMiniTreeUnitSave(@RequestBody Member member){
			ArrayList<Member> miniList = new ArrayList<Member>();
			logger.info("------------- Inside TempMiniTreeUnitSave Calling --------------");
			logger.info("------------- Number of TempMiniTreeUnitSave  -----------"+member.getNumberofUnit());
			int noofUnits = member.getNumberofUnit();
			int userID =  member.getUserLoginPrimaryKey();
			try{
				TempMiniTree tempminitree;
				RandomNumber randamNumber = randamNumberDAL.getTempMiniRandomNumber();
				for(int i=0;i<noofUnits;i++) {
					tempminitree = new TempMiniTree();
					tempminitree.setPaymentStatus("NOT PAID");
					tempminitree.setPayAmount(100);
					tempminitree.setNumberofUnits(1);
					tempminitree.setUserID(userID);
					tempminitree.setCurrency("SGD");
					
					int curInNumber = randamNumber.getMiniCount();
				    int nextInvoiceNumber = curInNumber+1;	
				    tempminitree.setInvoiceCode(randamNumber.getMiniInvCode() + nextInvoiceNumber);
				    //---- insert into tempminiTree ------
				    publicTreeDAL.insertTempMiniUser(tempminitree);
				    //--------- Update MiniRandomNumber ---------
				    RandomNumber document = new RandomNumber();
				    document.setMiniCount(nextInvoiceNumber);
				    randamNumberDAL.updateTempMiniRandamNumber(document);
				    
				    member = new Member();
				    member.setInvoiceNumber(tempminitree.getInvoiceCode());
				    member.setTotalAmount(noofUnits*100);
				    member.setPayAmt(tempminitree.getPayAmount()); 
				    miniList.add(member);
				    randamNumber=  randamNumberDAL.getTempMiniRandomNumber();
				 }  
				 logger.info("Temp Mini Primary Key --->"+userID);
			     // Get Email ID 
			     UserDetail userdetails = investmentBo.getMemberEmailID(userID);
			     if(userdetails.getEmail1().isEmpty()) {
					 logger.info("----------- No Email ID----------------");
				 }
				 else{
					 logger.info("----------- Found Temp Mini Email ID----------------"+userdetails.getEmail1());
					 // Push Email start 
				     InvestmentEmail.tempMiniTree(miniList,userdetails.getEmail1());
				}
			}catch(Exception e){
				logger.info("TempMiniTree Exception --------->"+e.getMessage());
			}
			finally{
				
			}
			return new ResponseEntity<ArrayList<Member>>(miniList,HttpStatus.CREATED);
		}
		
		// -------------------- Load MiniTree value --------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/getTempMiniTree",method=RequestMethod.GET)
		public ResponseEntity<?> getTempMiniTree()
		{
			logger.info("------------- Inside getTempMiniTree -----------------");
			List<TempMiniTree> responseList=null;
		   try {
				logger.info("-----------Inside getTempMiniTree Called----------");
				responseList = publicTreeDAL.getTempMiniTree();
			}catch(Exception e){
				logger.info("getTempMiniTree Exception ------------->"+e.getMessage());
				e.printStackTrace();
			}finally{
				
			}
			return new ResponseEntity<List<TempMiniTree>>(responseList, HttpStatus.CREATED);
		}
		
		// -------------------- Load Tree Name value --------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/approveMiniUnit",method=RequestMethod.POST)
		public ResponseEntity<?> approveMiniUnit(@RequestBody Member member)
		{
			logger.info("approveMiniUnit Invoice Code --->"+member.getRefmemberID());
			TempMiniTree response=null;
		   try {
				logger.info("-----------Inside approveMiniUnit Called----------");
				response = publicTreeDAL.getTempMiniSingleUnitTree(member.getRefmemberID());
				// Before Calling...
				member = new Member();
				member.setNumberofUnit(1);
				member.setUserLoginPrimaryKey(response.getUserID());					
				member = saveMiniTree(member);
				if(member.getBookingStatus().equalsIgnoreCase("true")) {
					logger.info("Good!!!!");
					member.setStatus("good");
					// Remove temp data start
					boolean getStatus = publicTreeDAL.RemoveTempMiniSingleUnitTree(response);
					logger.info("Output-->"+getStatus);
					// Remove temp data end	
				}else {
					member.setStatus("bad");
					logger.info("Error!!!!");
				}
				// After Called...
				}catch(Exception e){
				logger.info("approveMiniUnit Exception ------------->"+e.getMessage());
				e.printStackTrace();
			}finally{
				
			}
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		//----- Save Temp MiniTree to MiniTree -------
		public Member saveMiniTree(Member member) {
			logger.info("------------------ Inside MiniUnitSave  -----------------");
			logger.info("------------------ Number of Units  -----------------"+member.getNumberofUnit());
			int userID = member.getUserLoginPrimaryKey();
			member.setBookingStatus("false");
			try {
				RandomNumber randamNumber = randamNumberDAL.getAllMiniRandamNumber();
				logger.info("Mini User ID / Primary Key -->"+member.getUserLoginPrimaryKey());
				logger.info("Mini Invoice Code-->"+randamNumber.getInvoiceCode());
				logger.info("Mini Current Invoice-->"+randamNumber.getInvocieNumber());
				logger.info("Mini Current Queue Number-->"+randamNumber.getCurrentqueueNumber());
				logger.info("Mini Out Queue Number-->"+randamNumber.getMininextOutqueueNumber());
				MiniTree minitree;	
				int curInNumber = randamNumber.getInvocieNumber();
				int nextInvoiceNumber = curInNumber+1;			   
				int curQueNumber = randamNumber.getCurrentqueueNumber();
				int nextcurQueNumber = curQueNumber+1;	
				minitree = new MiniTree();
				if(randamNumber.getCurrentqueueNumber() == 0) {
					minitree.setQueueNumber(nextcurQueNumber);
				}else {
					MiniTree lastQueueNumber = randamNumberDAL.getLastMiniQueueNumber();
					logger.info("Last QueueNumber ------>"+lastQueueNumber.getQueueNumber()); 
					minitree.setQueueNumber(lastQueueNumber.getQueueNumber()+1);
				}
				
				logger.info("Mini Queue Number ----->"+minitree.getQueueNumber());
				minitree.setUserQueueStatus("IN");
				minitree.setInvoiceNumber(randamNumber.getInvoiceCode() + nextInvoiceNumber);
				minitree.setPaymentStatus("PAID");
				minitree.setPayAmount(100);
				minitree.setNumberofUnits(1);
				minitree.setUserID(userID);
				minitree.setCurrency("SGD");
				minitree.setUserstatus("Pending");					   
				// curInNumber ++;
				logger.info("New Invoice Number ------------>"+minitree.getInvoiceNumber());
				publicTreeDAL.insertMiniUser(minitree);		
				RandomNumber document = new RandomNumber();
				document.setCurrentqueueNumber(nextcurQueNumber);
				document.setInvocieNumber(nextInvoiceNumber);		   
				if(randamNumber.getCurrentqueueNumber()==randamNumber.getMininextOutqueueNumber()) {
					document.setMininextOutqueueNumber(randamNumber.getMininextOutqueueNumber()+3);
					MiniTree minitreeupdate = new MiniTree();
					minitreeupdate = publicTreeDAL.get3ComeOneOut();
					logger.info("3 Come one Out Value ---------->"+minitreeupdate.getInvoiceNumber());
					String newInvoiceCode = publicTreeDAL.updateMiniOutNumber(minitreeupdate,userID);
					logger.info("After 3 out new Invoice Number -->"+newInvoiceCode); 
					member.setMemberNumber(newInvoiceCode); 
			   }
			   randamNumberDAL.updateMiniRandamNumber(document);		
			   logger.info("Save Mini Primary Key --->"+userID);
			   // Get Email ID 
			   UserDetail userdetails = investmentBo.getMemberEmailID(userID);		 
			   if(userdetails.getEmail1().isEmpty()) {
				 logger.info("----------- No Email ID----------------");
			   }
			   else{
				 logger.info("----------- Found Approve Email ID----------------"+userdetails.getEmail1());
				 // Push Email start 
			     InvestmentEmail.approveMiniTree(minitree,userdetails.getEmail1());
			     // Push Email End
			   }   
			   member.setBookingStatus("true");
			}catch(Exception e) {
				member.setBookingStatus("false");
			}finally {
				
			}
			return member;
		}
		
		// -------------------- Reject MiniTree value --------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/rejectMini",method=RequestMethod.POST)
		public ResponseEntity<?> rejectMini(@RequestBody Member member)
		{
			logger.info("rejectMini Invoice Code --->"+member.getRefmemberID());
			logger.info("------------- Inside RejectMiniUnit -----------------");
		   try {
				logger.info("----------- Before Calling  rejectMiniUnit ----------");
				publicTreeDAL.rejectMiniUnit(member.getRefmemberID());
				logger.info("----------- Successfully Called  rejectMiniUnit ----------");
				// Before Calling...
				member = new Member();
				member.setStatus("good");
				// After Called...
			}catch(Exception e){
				member.setStatus("bad");
				logger.info("Exception ------------->"+e.getMessage());
				e.printStackTrace();
			}finally{
				
			}
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
		//-------- FileName Getting on MiniTree ------
		@GetMapping("/minifiles/{filename:.+}")
		@ResponseBody
		public ResponseEntity<Resource> getMiniTreeFile(@PathVariable String filename) {
			logger.info("------- Inside get MiniTreeFile Method ---------");
			Resource file = publicTreeDAL.loadMiniFile(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		}
		
		// -------------------- MiniTree UnitStatus value --------------
		@CrossOrigin(origins = "http://localhost:8080")
		@RequestMapping(value="/getSingleMiniUnitInfo",method=RequestMethod.GET)
		public ResponseEntity<?> getSingleMiniUnitInfo(String primaryKey)
		{
			logger.info("Mini User Primary Key--->"+primaryKey);
			List<MiniTree> responseList=null;
		   try {
				logger.info("Server side getMyMiniUnitInfo Called");
				responseList = new ArrayList<MiniTree>();
				responseList=publicTreeDAL.getSinglePurchaseMiniUnitByUserId(primaryKey);
			}catch(Exception e){
				logger.info("getSingleMiniUnitInfo Exception ------------->"+e.getMessage());
				e.printStackTrace();
			}finally{
				
			}
			return new ResponseEntity<List<MiniTree>>(responseList, HttpStatus.CREATED);
		}
		
		// -------------------- Public Tree Based InvoiceNumber --------------
		/*@CrossOrigin(origins = "http://localhost:80")
		@RequestMapping(value="/getPublicInvoiceNumber",method=RequestMethod.GET)
		public ResponseEntity<?> getPublicInvoiceNumber() {
			logger.info("--------- Inside getPublicInvoiceNumber Calling ---------");
			List<TempPublicTree> alltempList = null;
			ArrayList<TempPublicTree> invoiceList = null;
			try{
				alltempList = new ArrayList<TempPublicTree>();
				invoiceList = new ArrayList<TempPublicTree>();
				alltempList = publicTreeDAL.getPublicInvoiceNumber();
				logger.info("List Size --"+alltempList.size());
				for(int i=0; i<alltempList.size(); i++) {
					TempPublicTree temppublictree = new TempPublicTree();
					temppublictree.setInvoiceCode(alltempList.get(i).getInvoiceCode());
					logger.info("Invoice Code --->"+temppublictree.getInvoiceCode()); 
					invoiceList.add(temppublictree);
				}
				logger.info("Invoice List Size --"+invoiceList.size() + "Invoice Code -->"+invoiceList.get(0).getInvoiceCode());				
			}catch(Exception e) {
				logger.info("getPublicInvoiceNumber Exception ------------->"+e.getMessage());
			}finally {
				
			}
		    return new ResponseEntity<ArrayList<TempPublicTree>>(invoiceList, HttpStatus.CREATED);	
		} */
		

}
		
