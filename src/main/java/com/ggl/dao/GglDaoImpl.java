package com.ggl.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggl.dto.GLGMem;
import com.ggl.dto.Member;
import com.ggl.dto.User;
import com.ggl.model.BookingDetail;
import com.ggl.model.CategoryDetails;
import com.ggl.model.CommOverrDetail;
import com.ggl.model.CountryDetail;
import com.ggl.model.IndustryDetail;
import com.ggl.model.MemberId;
import com.ggl.model.OrganizationList;
import com.ggl.model.PaymentDetails;
import com.ggl.model.RandamNumber;
import com.ggl.model.UserDetail;
import com.ggl.model.UserLogin;
import com.ggl.model.WebsiteFeedBackMail;
import com.ggl.util.Custom;
import com.ggl.util.Email;



//import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



@Repository
@Singleton
public class GglDaoImpl implements GglDao {

	public static final Logger logger = LoggerFactory.getLogger(GglDaoImpl.class);

	@PersistenceContext(unitName="ggl-pu")
	private EntityManager entityManager;
	
	 @Value("${memeber.silver}")
	 private String silver;
	 @Value("${memeber.gold}")
	 private String gold;
	 @Value("${memeber.platinum}")
	 private String platinum;
	 

	// File Upload Validate GGL Number --------------------
	@Transactional(value="transactionManager")
	public String gglNumberCheck(String gglNumber) {
		logger.info("------------------ Inside gglNumberCheck DAO ---------------");
		Query query;
		String returnValue="failure";
		try {
			query=entityManager.createQuery("from UserDetail where memberID=?");
			query.setParameter(1, gglNumber);
			UserDetail userDetail=(UserDetail)query.getSingleResult();	
			if(userDetail.getMemberID().isEmpty()) {
				returnValue="failure";
			}
			else{
				returnValue = "success";
			}
			return returnValue;
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());

			return returnValue;
		}
		finally {
			
		}
		//return returnValue;
	}

	// ---------------------- Load Email ID -------------------
	public UserDetail getMemberEmailID(int primaryKey) {
		logger.info("------------------ Inside getMemberEmailID DAO ---------------");
		 UserDetail userdetails=null;
		try {
				userdetails = entityManager.find(UserDetail.class, primaryKey);
				return userdetails;
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());

			return userdetails;
		}
		finally {
			
		}
		//return returnValue;
	}
	
	// ---------------- forget Password use check ------------------------------
	@Transactional(value="transactionManager")
	public User Checkuser(User user){
		Query query;
		String tempEmail=null;
		try {	
		query=entityManager.createQuery("from UserLogin where username=?");
		query.setParameter(1, user.getUsername());
		UserLogin userloing=(UserLogin)query.getSingleResult();	
		logger.info("DB User Name -->"+userloing.getUsername());
		logger.info("DB User Email ID -->"+userloing.getUserDetails().get(0).getEmail1());
		tempEmail=userloing.getUserDetails().get(0).getEmail1();
		if(userloing.getUsername().equalsIgnoreCase(user.getUsername())){
			// generate otp and send to mail.
			String tempDate=Custom.getCurrentDateandTime().replaceAll(":", "");
			logger.info("Current date ---->"+tempDate);
			String otp = user.getUsername().substring(0, 3) + tempDate;// String.valueOf(Custom.getCurrentDate());
			userloing.setUserOtp(otp);
			entityManager.merge(userloing);			
			user.setStatus("success");
			user.setEmail_ID(tempEmail);
			user.setOtp(otp);
			user.setUsername(userloing.getUsername());
		}
		else {
			user.setStatus("failure");
		}
			return user;
		}catch(Exception e) {
			logger.error("Exception -->"+e.getMessage());
			user.setStatus("failure");
			return user;
		}finally {
			
		}
	}
			
			
// ---------------- forget Password use check ------------------------------
	@Transactional(value="transactionManager")
	public User OtpCheck(User user){
		Query query;
		try {
		logger.info("OTP Number -->"+user.getMemberID());	
		//user = dao.Checkuser(user);
		query=entityManager.createQuery("from UserLogin where userOtp=?");
		query.setParameter(1, user.getMemberID());
		UserLogin userloing=(UserLogin)query.getSingleResult();	
		//logger.info("DB User Name -->"+userloing.getUsername());
		if(userloing.getUserOtp().isEmpty()) {
			user.setStatus("failure");
		}
		else{
			user.setStatus("success");
		}
		
			return user;
		}catch(Exception e) {
			logger.error("Exception -->"+e.getMessage());
			user.setStatus("failure");
			return user;
		}finally {
			
		}
	}
				
	// ---------------- reSet Password ------------------------------
	@Transactional(value="transactionManager")
	public User resetPassword(User user){
		Query query;
		try {
		logger.info("New Password -->"+user.getPassword());	
		logger.info("User Name -->"+user.getUsername());	
		query=entityManager.createQuery("from UserLogin where username=?");
		query.setParameter(1, user.getUsername());
		UserLogin userlogin=(UserLogin)query.getSingleResult();	
		//UserLogin userlogin = entityManager.find(UserLogin.class, userloing.getUser_Login_ID());
		userlogin.setPassword(user.getPassword());
		entityManager.merge(userlogin);
		user.setStatus("success");
		return user;
		}catch(Exception e) {
			logger.error("Exception -->"+e.getMessage());
			user.setStatus("failure");
			return user;
		}finally {
			
		}
	}
	
	// ------------------- Member ID validate -----------------
	
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public boolean getMemberIDValidate(String MemberID){
		logger.info("--------------- Inside getMemberIDValidate (Dao) -----------------");
		boolean Response=false;
		Query query=null;
		List<MemberId> result;
		try {
			query=entityManager.createQuery("from MemberId where member_Number=?");
			query.setParameter(1, MemberID);
			result=(List<MemberId>)query.getResultList();
			logger.info("Result size ------------>"+result.size());
			if(result.size()>0){				
			for(MemberId memberID : result) {
				logger.info("Database referance Member ID ----------------->"+memberID.getMember_Number());
				if(memberID.getMember_Number().equalsIgnoreCase(MemberID)) {
						Response=true;
					}
					else {
						Response=false;
					}
				}
			}
			return Response;
		}catch(Exception e) {
			logger.error("Exception -->"+e.getMessage());
			return Response;
		}finally {
			
		}
		//return Response;
	}
	
	// -------------------Login check -------------------------------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public List<UserLogin> userLogin(User user,List<UserLogin> result){
		Query query=null;
		//List<UserLogin> resultDB;
		logger.info("Inside userLogin() DAO");
		try {
			if(user.getId()==1) {
				query=entityManager.createQuery("from UserLogin where username=?");
				query.setParameter(1, user.getUsername());
				result=(List<UserLogin>)query.getResultList();
			}
			if(user.getId()==2){
				query=entityManager.createQuery("from UserLogin where password=? and username=?");
				query.setParameter(1, user.getPassword());
				query.setParameter(2, user.getUsername());
				result=(List<UserLogin>)query.getResultList();				
				//query=entityManager.createQuery("from UserDetail where password=? and username=?");
				     UserDetail userdetails = entityManager.find(UserDetail.class, result.get(0).getUser_Login_ID());
				     logger.info("[DAO] Member ID ------------------>"+userdetails.getMemberID());
				     user.setMemberNumber(userdetails.getMemberID());
				     user.setMemberID(userdetails.getFirstname()); 
				     user.setCountry(userdetails.getCountry());
				    // result.add(e)
		     
			}
		} catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
			user.setStatus("Network Error Please try again");
		}
		finally {
			query=null;
		}

		return result;
	}
	
	// -------------------------------- User Name Check For Member Registration 
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public Member userExistingCheck(String requestType,Member member){
	    Query query=null;
	    List<UserLogin> result;
		try {
			if(requestType.equalsIgnoreCase("UserCheck")){
				query=entityManager.createQuery("from UserLogin where username=?");
				query.setParameter(1, member.getUsername());
				result=(List<UserLogin>)query.getResultList();
				if(result.size()>0) {
					member.setStatus("0"); // UI purpose			
					}
				else {
					member.setStatus("1"); // UI purpose			

				}
				//return member;
			}
			
			// ------------------------- Member ID check ---------------			
			if(requestType.equalsIgnoreCase("memberIDNotValid")){
				query=entityManager.createQuery("from MemberId where member_Number=?");
				query.setParameter(1,member.getMemberID());
				result=(List<UserLogin>)query.getResultList();
				if(result.size()>0) {
					member.setStatus("Exist"); // UI purpose			
					}
				else {
					member.setStatus("NotExist"); // UI purpose			

				}
			}

			
			
			return member;
 
		}catch(Exception e) {
			member.setStatus("2"); // UI purpose
			logger.error("Exception -->"+e.getMessage());
			return member;
		}
	   
	}
	// -------------------------- Save reservation ---------------------
	@Transactional(value="transactionManager")
//	@SuppressWarnings (value="unchecked")
	public Member saveReservation(Member member){
		logger.info("DAO Booking COde --->"+member.getBookingCode());
		BookingDetail bookdetails;
		UserLogin userlogin;
		try {
			member.setBookingStatus("Waiting for Approval");
			bookdetails = new BookingDetail();
			bookdetails.setInvoiceNumber(member.getInvoiceNumber());
			bookdetails.setCountryName(member.getCountry());
			bookdetails.setStateName(member.getSelectedState());
			bookdetails.setIndustryName(member.getCategoryname());
			bookdetails.setCompanyName(member.getCname());
			bookdetails.setBookingStatus("Waiting for Approval");
			bookdetails.setNoofadult(Custom.getAudult(member.getNoofadult()));
			bookdetails.setNoofchild(Custom.getChild(member.getNoofchild()));
			bookdetails.setNoofrooms(Custom.getRooms(member.getNoofrooms()));
			bookdetails.setBookingCode(member.getBookingCode()); 
			if(member.getBookingdate() == null || member.getBookingdate().equals("")){
				bookdetails.setBookingDate(Custom.getCurrentDate());//booking date added newly
			}else{
				bookdetails.setBookingDate(member.getBookingdate());//booking date added newly
			}		
			bookdetails.setAcctCreated_date(Custom.getCurrentDate());
			logger.info("Created Date -------->"+bookdetails.getAcctCreated_date()); 
			bookdetails.setNoofTables(member.getNoofTables()); //added newly
			logger.info("Booking Time -------->"+member.getBookingtime()); 
			if(member.getBookingtime() == null || member.getBookingtime().equals("")){
				
			}else{
				bookdetails.setBookingTime(member.getBookingtime()); 
			}
			bookdetails.setAirname(member.getAirname());//added newly
			bookdetails.setDeparture(member.getDeparture());
			bookdetails.setReturndate(member.getReturndate());			
			bookdetails.setFromplace(member.getFromplace());
			bookdetails.setToplace(member.getToplace());
			bookdetails.setNoofpax(Custom.getPax(member.getNoofpax()));
			bookdetails.setTriptype(member.getTriptype());
			bookdetails.setDeparturename(member.getDeparturename()); 
			bookdetails.setArrivaldate(member.getArrivaldate());
			bookdetails.setVisitcountry(member.getVisitcountry());
			bookdetails.setCategory(member.getCategory()); 
			bookdetails.setAppointmentdate(member.getAppointmentdate());
			logger.info("Financial Time----------->"+member.getFinancialtime());
			if(member.getFinancialtime() == null || member.getFinancialtime().equals("")){
				
			}else{
				bookdetails.setBookingTime(member.getFinancialtime());  
			}
			bookdetails.setUniversity(member.getUniversity());
			bookdetails.setStudy(member.getStudy());
			bookdetails.setYearofstudy(member.getYearofstudy()); 
			bookdetails.setCategoryinsurance(member.getCategoryinsurance());
			bookdetails.setCompanyinsurance(member.getCompanyinsurance()); 
			bookdetails.setHospitalname(member.getHospitalname());
			if(member.getMedicaltime() == null || member.getMedicaltime().equals("")){
				
			}else{
				bookdetails.setBookingTime(member.getMedicaltime()); 
			}
			bookdetails.setTreatment(member.getTreatment()); 
			bookdetails.setCategoryproduct(member.getCategoryproduct());
			bookdetails.setListproduct(member.getListproduct());
			bookdetails.setQuantity(member.getQuantity()); 
			bookdetails.setCompanyName(member.getCompanyname());
			
			//Newly Registered
			bookdetails.setPrice(member.getPrice());
			bookdetails.setAddress(member.getAddress());
			bookdetails.setContactNumber(member.getPhoneNumber());
			bookdetails.setEmailID(member.getEmailID()); 
			bookdetails.setCityName(member.getCityName()); 
			
			int tempPrimary = Integer.valueOf(member.getUserloginPrimaryKeyString());
			userlogin = entityManager.find(UserLogin.class, tempPrimary);
			bookdetails.setUserLogin(userlogin);
			entityManager.persist(bookdetails);
		    logger.info("Customer Email sending Email ID -->"+userlogin.getUserDetails().get(0).getEmail1());
		    member.setEmailID(userlogin.getUserDetails().get(0).getEmail1());
		    logger.info("Customer Email sending Member ID -->"+userlogin.getUserDetails().get(0).getMemberID());
		    member.setMemberID(userlogin.getUserDetails().get(0).getMemberID());
		    member.setStatus("success");  
		//    member.setBookingCode(bookdetails.getBookingCode());
		    
			
		}catch(Exception e) {
			 member.setStatus("failure");
			 logger.error("Exception -->"+e.getMessage());
		}finally {
			
		}
		return member;
	}
	
	// ------------ View only my reservation list ---------------------
	@Transactional(value="transactionManager")
	public UserLogin getMyReservationDetails(String primaryKeyStr){
		logger.info("---------- getMyReservationDetails -----------");
		UserLogin userlogin=null;
		try {
			//userlogin = new UserLogin();
			int tempPrimaryKey = Integer.valueOf(primaryKeyStr);
			userlogin = entityManager.find(UserLogin.class, tempPrimaryKey);
			logger.info("Size ------------->"+userlogin.getBookingdetail().size());
			return userlogin;
			
		}catch(Exception e) {
						logger.error("Exception -->"+e.getMessage());
		}
		return userlogin;
	}

	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<BookingDetail> getAllReservationDetails(Member member){
		Query query=null;
		ArrayList<BookingDetail> bookinglist=null;
		try {
			if(member.getSequanceNumber() == 1){
				query=entityManager.createQuery("from BookingDetail where bookingStatus=? ");
				query.setParameter(1, "Waiting for Approval");
				bookinglist=(ArrayList<BookingDetail>)query.getResultList();
			}else if(member.getSequanceNumber() == 2){
				if(member.getCategoryname().equalsIgnoreCase("All")){
					logger.info("---- calling all category -------");
					query=entityManager.createQuery("from BookingDetail where bookingStatus=? ");
					query.setParameter(1, "Waiting for Approval");
					bookinglist=(ArrayList<BookingDetail>)query.getResultList();
				}else{
					logger.info("---- calling sorted categorylist ---- ");
					query=entityManager.createQuery("from BookingDetail where bookingStatus=? and industryName=?");
					query.setParameter(1, "Waiting for Approval");
					query.setParameter(2, member.getCategoryname());
					bookinglist=(ArrayList<BookingDetail>)query.getResultList();
				}
			}
			return bookinglist;
		}catch(Exception e){
			
		}finally
		{
			
		}
		return bookinglist;
	}
	
	
	

	@Transactional(value="transactionManager") 
	//@SuppressWarnings (value="unchecked")
	public boolean refer1Update(Member member){
		boolean flag=true;
		MemberId memberID=null;
		CommOverrDetail comm=null;
		logger.info("reference 1");
		logger.info("First commission -->"+member.getRef_commition1());
		logger.info("First overriding --> "+member.getRef_ovrriding1());
		logger.info("Ref ID 1 ---------->"+member.getMember1_primaryKey());
		try {
			 if(member.getMember1_primaryKey()!=0){
    			 memberID = new MemberId();  
    			 comm = new CommOverrDetail();
    			 memberID = entityManager.find(MemberId.class, member.getMember1_primaryKey());
    			
    			 logger.info("Total Commition Refer 1 -->"+memberID.getTotalCommission());
    			 memberID.setTotalCommission(memberID.getTotalCommission() + member.getRef_commition1());
    			 logger.info("Total Commition -->"+memberID.getTotalCommission());
    			 //double temp1 = memberID.getTotalCommission() + member.getRef_commition1();
    			 double temp1 =  memberID.getTotalCommission();

    			 logger.info("Total Overriding Refer 1 -->"+memberID.getTotalOverriding());
    			 double temp2 = memberID.getTotalOverriding() + member.getRef_ovrriding1();
    			 memberID.setTotalOverriding(memberID.getTotalOverriding() + member.getRef_ovrriding1());
    			 logger.info("Total OverRiding ----------->"+memberID.getTotalOverriding());
    			 double temp3 = temp1 + temp2;
    			 logger.info("Temp3 for Refer1 ----------->"+temp3);
    			 String temp4 = String.valueOf(temp3);
    			 logger.info("Temp4 for Refer1 ----------->"+temp4);
    			 memberID.setTotalAmount(temp4);
   			 
    			 comm.setCommissionAmt(member.getRef_commition1());
    			 logger.info("Commission Amount for Refer1 ----------->"+comm.getCommissionAmt());
    			 comm.setOverridingAmt(member.getRef_ovrriding1());
    			 logger.info("Overriding Amount for Refer1 ----------->"+comm.getOverridingAmt());
    			 comm.setCountry(member.getCountry()); 
    			 comm.setCreated_date(Custom.getCurrentDate());
    			 comm.setValue_type("added");
    			 comm.setStatus("waiting");
    			 //comm.setWithdraw_Status("Not Available"); 
    			 comm.setMember_Number(member.getMemberNumber());
    			 comm.setMemberId(memberID);
    			 entityManager.persist(comm);
    			 entityManager.flush();
    			 entityManager.clear();
    			 
    			 // Member ID table need to merge
    			 
    			 // list.add(comm);
    			 //  memberID.setCommOverrDetails(list);//(comm);
    			 //  entityManager.merge(memberID);
			 }
		    if(member.getMember2_primaryKey()!=0) {
		    	memberID = new MemberId();
		    //	list = new ArrayList<CommOverrDetail>();
		    	logger.info("reference 2");
				logger.info("Second commission -->"+member.getRef_commition2());
				logger.info("Second overriding --> "+member.getRef_ovrriding2());
				logger.info("Ref ID 2---------->"+member.getMember2_primaryKey());
		    	comm = new CommOverrDetail();
				memberID = entityManager.find(MemberId.class, member.getMember2_primaryKey());
				
				 logger.info("Total Commition Refer 2 -->"+memberID.getTotalCommission());
    			 memberID.setTotalCommission(memberID.getTotalCommission() + member.getRef_commition2());
    			 logger.info("Total Commition -->"+memberID.getTotalCommission());
    			 //double temp1 = memberID.getTotalCommission() + member.getRef_commition1();
    			 double temp1 =  memberID.getTotalCommission();

    			 logger.info("Total Overriding Refer 2-->"+memberID.getTotalOverriding());
    			 double temp2 = memberID.getTotalOverriding() + member.getRef_ovrriding2();
    			 memberID.setTotalOverriding(memberID.getTotalOverriding() + member.getRef_ovrriding2());
    			 logger.info("Temp2 ----------->"+memberID.getTotalOverriding());
    			 double temp3 = temp1 + temp2;
    			 logger.info("Temp3 ----------->"+temp3);
    			 String temp4 = String.valueOf(temp3);
    			 logger.info("Temp4 ----------->"+temp4);
    			 memberID.setTotalAmount(temp4);
    			 
    			 
				logger.info("2");
				comm.setCommissionAmt(member.getRef_commition2());
				logger.info("Commission Amount for Refer2 ----------->"+comm.getCommissionAmt());
				comm.setOverridingAmt(member.getRef_ovrriding2());
				logger.info("Overriding Amount for Refer2 ----------->"+comm.getOverridingAmt());
				comm.setCountry(member.getCountry()); 
				comm.setCreated_date(Custom.getCurrentDate());
				comm.setValue_type("added");
				comm.setStatus("waiting");	
				comm.setMember_Number(member.getMemberNumber());
				comm.setMemberId(memberID);
				entityManager.persist(comm);
				entityManager.flush();
				entityManager.clear();
		    
		    }
		    if(member.getMember3_primaryKey()!=0){
		    	memberID = new MemberId();
		    	//list = new ArrayList<CommOverrDetail>();
		    	logger.info("reference 3");
				logger.info("Third commission -->"+member.getRef_commition3());
				logger.info("Third overriding --> "+member.getRef_ovrriding3());
				logger.info("Ref ID 3---------->"+member.getMember3_primaryKey());
		    	comm = new CommOverrDetail();
				memberID = entityManager.find(MemberId.class, member.getMember3_primaryKey());
				
				 logger.info("Total Commition Refer 3 -->"+memberID.getTotalCommission());
    			 memberID.setTotalCommission(memberID.getTotalCommission() + member.getRef_commition3());
    			 logger.info("Total Commition -->"+memberID.getTotalCommission());
    			 //double temp1 = memberID.getTotalCommission() + member.getRef_commition1();
    			 double temp1 =  memberID.getTotalCommission();

    			 logger.info("Total Overriding Refer 3-->"+memberID.getTotalOverriding());
    			 double temp2 = memberID.getTotalOverriding() + member.getRef_ovrriding3();
    			 logger.info("Temp2 ----------->"+temp2);
    			 memberID.setTotalOverriding(memberID.getTotalOverriding() + member.getRef_ovrriding3());
    			 double temp3 = temp1 + temp2;
    			 logger.info("Temp3 ----------->"+temp3);
    			 String temp4 = String.valueOf(temp3);
    			 logger.info("Temp4 ----------->"+temp4);
    			 memberID.setTotalAmount(temp4);
    			 
			    logger.info("3");
			    comm.setCommissionAmt(member.getRef_commition3());
			    logger.info("Commission Amount for Refer3 ----------->"+comm.getCommissionAmt());
			    comm.setOverridingAmt(member.getRef_ovrriding3());
			    logger.info("Overriding Amount for Refer3 ----------->"+comm.getOverridingAmt());
			    comm.setCountry(member.getCountry()); 
			    comm.setCreated_date(Custom.getCurrentDate());
			    comm.setValue_type("added");
			    comm.setStatus("waiting");	
			    comm.setMember_Number(member.getMemberNumber());
			    comm.setMemberId(memberID);
			    entityManager.persist(comm);
			    entityManager.flush();
			    entityManager.clear();
			    logger.info("Saved successfully 3");
		    }
	    
		//entityManager.flush();
		//comm.getClass().getClassLoader().clearAssertionStatus();

	   // entityManager.merge(memberID);
		} catch(Exception e) {
						logger.error("Exception -->"+e.getMessage());
			logger.info("Exception -->"+e.getMessage());

		} finally {
			//entityManager.clear();
		}
	return flag;
		
	}
	// -------------------Create a Member -------------------------------------------
	
	@Transactional(value="transactionManager")
	//@SuppressWarnings (value="unchecked")
	public Member createMember(Member member){
		logger.info("Inside createMember() DAO");
		UserLogin parent = null;
	    List<UserDetail> childlist = null;
		try{
			   // Save(Insert) into Member table 
			
			  MemberId saveMember = new MemberId();
			  saveMember.setGroupName(member.getGroupName());
			  saveMember.setMember_Acct_Type(member.getActType());
			  saveMember.setSequanceNumber(member.getSequanceNumber());
			  saveMember.setMember_Number(member.getMemberNumber());
			  saveMember.setTotalCommission(member.getCommition());
			  saveMember.setTotalOverriding(member.getOverriding());
			  saveMember.setLevel_number(member.getLeveNumber());
			  saveMember.setTree_name(member.getTreeName());
			  saveMember.setWithdraw_Status("Not Available");
			  
			  entityManager.persist(saveMember);
			 	
				// Save in login table 
				parent = new UserLogin();
				parent.setUsername(member.getUsername());
				parent.setPassword(member.getPassword());
				parent.setStatus("Waiting For Approval");
				parent.setUserRole("member");
				
				// Save in User details table 
				UserDetail child=new UserDetail();
				child.setFirstname(member.getFirstName());
				child.setLastname(member.getLastName());
				child.setEmail1(member.getEmailID());
				child.setPhonenumber1(member.getPhoneNumber());
				child.setCountry(member.getCountry());
				child.setBankName(member.getBankName());
				child.setBankAcctNumber(member.getBankAcctNumber());
				child.setMember_Ref_ID(member.getRefmemberID());
				child.setAdminFees(member.getAdminFees());
				child.setTotalFees(member.getTotalFees());
				child.setUserLogin(parent);
				
				// --------------- Important -----------------				
				child.setMemberID(member.getMemberNumber()); // get From Random table.
				child.setAcctType(member.getActType());
				child.setPayAmt(member.getPayAmt());
				child.setPayStatus("Waiting");
				child.setAcctCreated_date(Custom.getCurrentDate());
				childlist = new ArrayList<UserDetail>();
				childlist.add(child);
				parent.setUserDetails(childlist);
			    entityManager.persist(parent);
			    
			    //------- Save To Payment Details -----
			    PaymentDetails payment = new PaymentDetails();
				payment.setAdminBank_Name(member.getAdminbankName());
				payment.setAdminBankAcct_Name(member.getAdminacctName());
				payment.setAdminBankAcct_Number(member.getAdminacctNumber());
				payment.setTransferBank_Name(member.getBanktransfer());
				payment.setTransferBankAcct_Name(member.getTransferAcctName());
				payment.setTransferBankAcct_Number(member.getTransferBankAcctNumber());
				payment.setPayment_Path("/home/ec2-user/GGL/PaymentFiles/"+member.getMemberNumber()+".jpg");
				logger.info("Payment Path ------>"+payment.getPayment_Path());
				payment.setMember_Number(member.getMemberNumber()); 
				payment.setAcctCreated_date(Custom.getCurrentDate());
				entityManager.persist(payment);
			    
				logger.info("Successfully saved data");
		}catch(Exception e){
			logger.info("Exception -->"+e.getMessage());
		}finally{
			 parent = null;
		     childlist = null;
		}
		return member;
	}

	// ---------------- get country and state list---------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<IndustryDetail> getState (String country,ArrayList<IndustryDetail> industrylist){
		Query query=null;
		try {
			query=entityManager.createQuery("from IndustryDetail where countryName=? ");
			query.setParameter(1, country);
			industrylist=(ArrayList<IndustryDetail>)query.getResultList();
			
		}catch(Exception e){
			
		}finally
		{
			
		}
		return industrylist;
	}
	
	
	// -------------------- get Country list ---------------------	
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<CountryDetail> getCountry(){
		ArrayList<CountryDetail> result=null;
		Query query=null;
		try {
			query=entityManager.createQuery("from CountryDetail");
			result=(ArrayList<CountryDetail>)query.getResultList();
		}catch(Exception e) {
			logger.info("Exception --------------->"+e.getMessage());
		}finally{
			
		}		
		return result;
	}
	
	// ---------------------------- get Random member Code --------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public int getRandamCode(int newCode,String requestType) {
		Query query=null;
		ArrayList<RandamNumber> result=null;
		try {
			logger.info("-------------- getRandamCode inside try ---------->");
			query=entityManager.createQuery("from RandamNumber");
			result=(ArrayList<RandamNumber>)query.getResultList();
			logger.info("-------------- getRandamCode  after select query ---------->");

			if(requestType.equalsIgnoreCase("CurrentGLGCode"))
			{
				logger.info("-------------- getRandamCode  inside CurrentGGLCode If ---------->");
				int temp = result.get(0).getCurrent_Member_Number();
				logger.info("Existing code ---------->"+temp);
				newCode = result.get(0).getCurrent_Member_Number() + 1;
				logger.info("Generated New code ------------>"+newCode);			
				query=entityManager.createQuery("update RandamNumber set current_Member_Number="+newCode);
			    query.executeUpdate();
				logger.info("New Member Code Updated Successfully");
			}
			
			if(requestType.equalsIgnoreCase("CurrentGroupCode")){
				logger.info("-------------- getRandamCode  inside If CurrentGroupCode ---------->");

				int currentGroupCode = result.get(0).getCurrent_Group_Number();
				newCode = currentGroupCode + 1;
				logger.info("Generated New Group Code  ------------>"+newCode);		
				query=entityManager.createQuery("update RandamNumber set Current_Group_Number="+newCode);
			    query.executeUpdate();
				logger.info("New Group Code Updated Successfully for CurrentGroupCode If");
			}
			
			if(requestType.equalsIgnoreCase("treeNumber")){
				logger.info("-------------- getRandamCode  inside If treeNumber ---------->");
				int currentTreeNumber = result.get(0).getTreeRandamNumber();
				newCode = currentTreeNumber + 1;
				logger.info("Generated New Group Code  ------------>"+newCode);		
				query=entityManager.createQuery("update RandamNumber set treeRandamNumber="+newCode);
			    query.executeUpdate();
				logger.info("New Group Code Updated Successfully for TreeNumber if");
			}
			
			if(requestType.equalsIgnoreCase("bookingInvoiceNumber")){
				int currentTreeNumber = result.get(0).getBooking_Randam_Number();
				newCode = currentTreeNumber + 1;
				logger.info("Generated New Booking Invoice Number  ------------>"+newCode);		
				query=entityManager.createQuery("update RandamNumber set booking_Randam_Number="+newCode);
			    query.executeUpdate();
				logger.info("New Booking Code Updated Successfully for bookingInvoiceNumber If");
			}
			
			if(requestType.equalsIgnoreCase("CurrentCategoryCode"))
			{
				int temp = result.get(0).getCategory_Randam_Number();
				logger.info("Category Existing code ---------->"+temp);
				newCode = result.get(0).getCategory_Randam_Number() + 1;
				logger.info("Category Generated New code ------------>"+newCode);			
				query=entityManager.createQuery("update RandamNumber set category_Randam_Number="+newCode);
			    query.executeUpdate();
				logger.info("New InvestmentAgent Code Updated Successfully for CurrentCategoryCode If");
			}
			return newCode;
		}catch(Exception e) {
			logger.info("Exception ------------>"+e.getMessage());
		}finally
		{
			
		}
		return newCode;	
	}
		
	// ---------------------------- get Old Member ID with Acct Type --------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public String getMemberCode(String Ref_memberCode) {
		String response=null;
		ArrayList<MemberId> result=null;
		ArrayList<MemberId> resultSeq=null;
		Query query=null;
		try {
			logger.info("----------Inside Try getMemberCode-------------");
			query=entityManager.createQuery("from MemberId where member_Number=?");
			query.setParameter(1, Ref_memberCode);
			result=(ArrayList<MemberId>)query.getResultList();
			logger.info("Current Referance GLG Acct Type  ------------->"+result.get(0).getMember_Acct_Type());
			logger.info("Current Group name  ------------->"+result.get(0).getGroupName());
			logger.info("Current Sequance number ------------->"+result.get(0).getSequanceNumber());			
			query=entityManager.createQuery("from MemberId where groupName=?");
			query.setParameter(1, result.get(0).getGroupName());
			resultSeq=(ArrayList<MemberId>) query.getResultList();
			ArrayList<Integer> maxList = new ArrayList<Integer>();
			for(MemberId id:resultSeq){
				maxList.add(id.getSequanceNumber());
			}
			Integer maxNumber = Collections.max(maxList);
			logger.info("Current Max Sequance Number ---------------->"+maxNumber);
			logger.info("Current Sequance number ------------->"+result.get(0).getMember_ID());		
			response = result.get(0).getMember_Acct_Type() +"-" + result.get(0).getGroupName() + "-" +maxNumber + "-" 
			+result.get(0).getMember_ID() +"-"+result.get(0).getTree_name() +"-"+ result.get(0).getLevel_number() +"-"+result.get(0).getSequanceNumber() ;
		}catch(Exception e) {
			logger.info("Exception ----------->"+e.getMessage());
		}finally{
			
		}		
		return response;	
	}

	// --------------------------- get Member group data ---------------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<MemberId> getGroupData(ArrayList<MemberId> memberID,int groupName){
		Query query=null;
		try {
			logger.info("--------------- Inside Try getGroupData-------------");
			query=entityManager.createQuery("from MemberId where groupName=? order by sequance_number desc");
			query.setParameter(1, groupName);
			memberID=(ArrayList<MemberId>)query.getResultList();
			logger.info("--------------- Inside getGroupData successfully got member ID-------------");

		}catch(Exception e) {
			logger.info("Exception ----------->"+e.getMessage());
		}finally{
			
		}		
		return memberID;
	}

	// ------------------------------  get Member group data ------------------------------------	
		@Transactional(value="transactionManager")
		@SuppressWarnings (value="unchecked")
		public ArrayList<MemberId> getFiltredData(ArrayList<MemberId> memberID,int groupName,int fromNumber,int ToNumber,String treeName){
			Query query=null;
			logger.info("getFiltredData Tree name ------------------->"+treeName);
			logger.info("getFiltredData Sequance Number ---------------->"+fromNumber);
		try {
			query=entityManager.createQuery("from MemberId where groupName=? and sequanceNumber < "+fromNumber+" and (tree_name=? or tree_name=?) order by sequanceNumber desc");
			query.setParameter(1, groupName);
			query.setParameter(2, treeName);
			query.setParameter(3, "A0");
			memberID=(ArrayList<MemberId>)query.getResultList();
			}catch(Exception e) {
				logger.info("Exception ----------->"+e.getMessage());
			}finally{
				
			}		
			return memberID;
		}	
		
		// ---------------------- load Only MY GLG Member Data -------------------------
		@Transactional(value="transactionManager")
		@SuppressWarnings (value="unchecked")
		public ArrayList<GLGMem> getMyMemberList(String memberNumber, ArrayList<GLGMem> myMemList){
			Query query=null;
			GLGMem glgmember;
			ArrayList<UserDetail> resultList;
			ArrayList<MemberId> memberResult;
			logger.info("Member Number ---------------->"+memberNumber);
			try {
				logger.info("--------------- Inside try getMyMemberList-------------------");
				query=entityManager.createQuery("from UserDetail where member_Ref_ID=? order by acctCreated_date desc");
				query.setParameter(1, memberNumber);
				resultList=(ArrayList<UserDetail>)query.getResultList();
				for(UserDetail userdetails : resultList) {
					glgmember = new GLGMem();
					glgmember.setMemberName(userdetails.getFirstname());
					glgmember.setMemberID(userdetails.getMemberID());
					glgmember.setMemberPhone(userdetails.getPhonenumber1());
					glgmember.setMemberEmail(userdetails.getEmail1());
					glgmember.setMemberType(userdetails.getAcctType());
					glgmember.setUsername(userdetails.getFirstname() + " " + userdetails.getLastname()); 
					glgmember.setCountry(userdetails.getCountry()); 
					glgmember.setCreated_date(userdetails.getAcctCreated_date()); 
					UserLogin userlogin = entityManager.find(UserLogin.class, userdetails.getUserLogin().getUser_Login_ID());
					glgmember.setMemberStatus(userlogin.getStatus());
					query=entityManager.createQuery("from MemberId where member_Number=?");
					query.setParameter(1, userdetails.getMemberID());
					memberResult=(ArrayList<MemberId>)query.getResultList();
					logger.info("List Size ------>"+memberResult.size() +"  Member ID----------->"+memberResult.get(0).getMember_Number()); 
					glgmember.setMemberCommition(memberResult.get(0).getTotalCommission());  
					logger.info("Member Commission ---------->"+glgmember.getMemberCommition()); 
					glgmember.setMemberOvrriding(memberResult.get(0).getTotalOverriding());
					logger.info("Member OverRiding ---------->"+glgmember.getMemberOvrriding());
					myMemList.add(glgmember);
				}
			}catch(Exception e) {
				logger.info("Exception ----------->"+e.getMessage());
			}finally{
				
			}		
			return myMemList;
		
		}
	// ---------------------- load ALL GLG Member Data -------------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<GLGMem> getAllMemberList(String requestType,ArrayList<GLGMem> myMemList){
		Query query=null;
		GLGMem glgmember;
		ArrayList<UserLogin> resultList;
		try {
			if(requestType.equalsIgnoreCase("WaitingForApproval")) {
				query=entityManager.createQuery("from UserLogin where status=?  order by user_Login_ID desc");
				query.setParameter(1, "Waiting For Approval");
					
			}
			if(requestType.equalsIgnoreCase("All")) {
				query=entityManager.createQuery("from UserLogin order by user_Login_ID desc");				
			}
			resultList=(ArrayList<UserLogin>)query.getResultList();	
			logger.info("Total Member Size --------------->"+resultList.size());
			for(int i=0;i<resultList.size();i++){
				glgmember = new GLGMem();
				logger.info("getAllMemberList Test 1");
				glgmember.setMemberStatus(resultList.get(i).getStatus());
				glgmember.setUserLoginPrimaryKey(resultList.get(i).getUser_Login_ID());
				UserDetail userdetails = entityManager.find(UserDetail.class, resultList.get(i).getUser_Login_ID());
				glgmember.setMemberID(userdetails.getMemberID());
				glgmember.setMemberName(userdetails.getFirstname());
				glgmember.setMemberEmail(userdetails.getEmail1());
				glgmember.setMemberPhone(userdetails.getPhonenumber1());
				logger.info("Test 2");
				glgmember.setMemberType(userdetails.getAcctType());
				glgmember.setCountry(userdetails.getCountry()); 
				logger.info("Test 3");
				glgmember.setMemberID1(userdetails.getMember_Ref_ID()); 
				query=entityManager.createQuery("from MemberId where member_Number=?");
				query.setParameter(1, glgmember.getMemberID());
				logger.info("Test 4");
				ArrayList<MemberId> memberResult=(ArrayList<MemberId>)query.getResultList();
				glgmember.setMemberCommition(memberResult.get(0).getTotalCommission());
				glgmember.setMemberOvrriding(memberResult.get(0).getTotalOverriding());
				myMemList.add(glgmember);
				logger.info("Test 5");
			}
	}
		catch(Exception e) {
			logger.info("Exception ----------->"+e.getMessage());
		}finally{
			
		}		
		return myMemList;
	
	}		



/*
//	------------------ Approval -------------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public User getApproved(User user,int userLoginPrimaryKey,String requestType){
		Query query;
		MemberId memberid=null;
	
		try{
			UserLogin userlogin = entityManager.find(UserLogin.class, userLoginPrimaryKey);
			if(requestType.equalsIgnoreCase("Approve")) {				
				UserDetail userdetails = entityManager.find(UserDetail.class, userLoginPrimaryKey);
				logger.info("Member ID "+userdetails.getMemberID());
			 	query=entityManager.createQuery("from CommOverrDetail where Member_Number=?");
				query.setParameter(1, userdetails.getMemberID());
				ArrayList<CommOverrDetail> result=(ArrayList<CommOverrDetail>)query.getResultList();
				logger.info("List size --------------->"+result.size());
				if(result.size()>0) {
					for(CommOverrDetail comm:result){
						//double tempComm=0;
						//double tempOver=0;
						memberid=null;
						memberid = new MemberId();
						logger.info("Member table primary key ---->"+comm.getMemberId().getMember_ID());
						logger.info("Child Overriding amount ---->"+comm.getOverridingAmt());
						logger.info("Child Commission amount---->"+comm.getCommissionAmt());
						
						memberid = entityManager.find(MemberId.class, comm.getMemberId().getMember_ID());
						logger.info("Parent Total Overriding amount ---->"+memberid.getTotalCommission());
						logger.info("Parent Total Commission amount---->"+memberid.getTotalOverriding());
						
						tempComm = memberid.getTotalCommission() + comm.getCommissionAmt();
						tempOver = memberid.getTotalOverriding() + comm.getOverridingAmt();
						logger.info("Total Comm ---->"+tempComm);
						logger.info("Total Over---->"+tempOver);						
						memberid.setTotalCommission(tempComm);
						memberid.setTotalOverriding(tempOver);
						entityManager.merge(memberid);
						entityManager.flush();
						entityManager.clear();
						
						query=entityManager.createQuery("from MemberId WHERE member_Number='" +comm.getMember_Number()+"' ");
						MemberId mem = (MemberId) query.getSingleResult();
						logger.info("Member Number -->"+mem.getMember_Number());
						mem.setWithdraw_Status("Available");
						entityManager.merge(mem);
						entityManager.flush();
						entityManager.clear();
						
						logger.info("Member table is updated successfully");
						
						comm.setStatus("approved");
						entityManager.merge(comm);
						entityManager.flush();
						entityManager.clear();
					}
					
					userlogin.setStatus("Approved");
					entityManager.merge(userlogin);
					entityManager.flush();
					entityManager.clear();
				}
				else {
					logger.info("No child record found");
				}
				user.setStatus("success");
				user.setEmail_ID(userdetails.getEmail1());
				user.setUsername(userdetails.getFirstname() + " " + userdetails.getLastname());
				user.setMemberID(userdetails.getMemberID());
				user.setAccoutType(userdetails.getAcctType());
			}
			if(requestType.equalsIgnoreCase("Reject")) {
				UserDetail userdetails = entityManager.find(UserDetail.class, userLoginPrimaryKey);
				userlogin.setStatus("Rejected");
				entityManager.merge(userlogin);
				user.setEmail_ID(userdetails.getEmail1());
				user.setMemberID(userdetails.getMemberID());
				user.setStatus("rejectSuccess");
			}
			//user.setStatus("rejectSuccess");			
			logger.info("Member data is successfully saved.");
		}catch(Exception e) {
			user.setStatus("failure");
						logger.error("Exception -->"+e.getMessage());
		}finally{
			
		}
		return user;
	}*/
	
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public User getApproved(User user,int userLoginPrimaryKey,String requestType){
		Query query;
		MemberId memberid=null;
		
		logger.info("Sliver--------------->"+silver);
		logger.info("Frenschiese--------------->"+gold);
		logger.info("Master Frenschiese --------------->"+platinum);
		String response=null;		
		int newTempCode=0;
		String newCodeFinal=null;
		int NewGroup=0;
		@SuppressWarnings("unused")
		String memberType=null;
		Member member = null;
		ArrayList<MemberId> memberInfo=null;//new ArrayList<MemberId>();
		ArrayList<MemberId> memberInnerInfo=null;//new ArrayList<MemberId>();
		
		try{
			member = new Member();
			UserLogin userlogin = entityManager.find(UserLogin.class, userLoginPrimaryKey);
			if(requestType.equalsIgnoreCase("Approve")) {				
				UserDetail userdetails = entityManager.find(UserDetail.class, userLoginPrimaryKey);
				logger.info("Member ID "+userdetails.getMemberID());
				
				
				// Newly Added for Update commission and overriding 
				
				int silverInt = Integer.parseInt(silver); 
				int goldInt = Integer.parseInt(gold);
				int platinumInt = Integer.parseInt(platinum);
				
				/*
				 * if(userdetails.getAcctType() == "silver"){ int silverInt =
				 * Integer.parseInt(silver); }else if(userdetails.getAcctType() == "gold"){ int
				 * goldInt = Integer.parseInt(gold); }else if(userdetails.getAcctType() ==
				 * "platinum"){ int platinumInt = Integer.parseInt(platinum); }
				 */
				
			
				
				logger.info("Referance Member ID -->"+userdetails.getMember_Ref_ID()); //1
				logger.info("Email ID -->"+userdetails.getEmail1()); // 2
				logger.info("Country -->"+userdetails.getCountry()); // 3
				logger.info("Phone Number -->"+userdetails.getPhonenumber1()); //4
				logger.info("First Name -->"+userdetails.getFirstname()); // 5
				logger.info("Last Name -->"+userdetails.getLastname()); // 6
				logger.info("User Name -->"+userdetails.getUserLogin().getUsername()); // 7
				logger.info("Password Name -->"+userdetails.getUserLogin().getPassword()); // 8
				logger.info("Bank Name -->"+userdetails.getBankName()); // 9
				logger.info("Account number -->"+userdetails.getBankAcctNumber()); // 10
				logger.info("Member Type -->"+userdetails.getAcctType()); // 11
				
				logger.info("Before calling Dao to get Ref Member Acct Type");			
				// Calling for Existing reference Member ID and details for calculation
				response = getMemberCode(userdetails.getMember_Ref_ID());
				logger.info("Ref Member Acct type -------------->"+response);
				String[] res = response.split("-");
				 // res[0]
			    String acctName=res[0]; // Reference acctName
			    int gropName=Integer.valueOf(res[1]); // Group Name
			    int maxNumber=Integer.valueOf(res[2]);	// Sequence Number
			    int PrimaryKey=Integer.valueOf(res[3]);	    // Primary key 
			    String treeName = res[4];	
			    int leveNumber = Integer.valueOf(res[5]);
			    int sequanceNumber=Integer.valueOf(res[6]);	// Sequence Number
		
			    logger.info("Ref Acct Name ---------------->"+acctName);
			    logger.info("Ref Group Name --------------->"+gropName);
			    logger.info("Ref Max Sequance Number ---------->"+maxNumber); // Max Size 
			    logger.info("Ref Primary Key -------------->"+PrimaryKey);
			    logger.info("Ref Tree nmae -------------->"+treeName);
			    logger.info("Ref Level Number -------------->"+leveNumber);
			    logger.info("Ref Sequance Number -------------->"+sequanceNumber);
		    
			    // Alex Start
			    // Senario 1
			    if(userdetails.getAcctType().equalsIgnoreCase("silver") && acctName.equalsIgnoreCase("silver")) {
			    	int i=0;
					member.setTreeName(treeName);
					member.setLeveNumber(leveNumber+1);
					// for new account pay
					member.setPayAmt(silverInt); 
					//member.setAdminFees(0.5);
					//member.setTotalFees(silverInt+0.5);
					member.setAdminFees(5);
					member.setTotalFees(silverInt+5);
					// reference 
					member.setRef_commition1(5);
					member.setRef_ovrriding1(0);
					member.setMember1_primaryKey(PrimaryKey);
					member.setRefmemberID(userdetails.getMember_Ref_ID());
					member = getMember1_EmailID(member); 
					
					// try to reach platinum a/c
					memberInfo=new ArrayList<MemberId>();
			    	memberInnerInfo=new ArrayList<MemberId>();
			    	memberInfo = getGroupData(memberInfo,gropName);
			    	int tmpSize = memberInfo.size();
			    	logger.info("Group Size --------->"+tmpSize);
			    	for(MemberId m : memberInfo){
			    		if(sequanceNumber==m.getSequanceNumber()){	
			    			// 3
			    			memberInnerInfo = getFiltredData(memberInfo,gropName,sequanceNumber,tmpSize,treeName);
			    			
			    			for(MemberId me : memberInnerInfo) {			    				
			    				//if(me.getMember_Acct_Type().equalsIgnoreCase("gold")){
			    				if(me.getMember_Acct_Type().equalsIgnoreCase("gold") && i==0){
			        				i=1;
			    					logger.info("Test 1"); 
			    					memberType = "gold";
			    					member.setRef_ovrriding2(0.5);
			    					logger.info("Member Overridding2 2nd silver to gold ------------>"+member.getRef_ovrriding2()); 
			    					member.setRef_commition2(0);//(5);
			    					member.setMember2_primaryKey(me.getMember_ID());
			    					logger.info("getApproved Test 2");

			    					member.setMemberID2(me.getMember_Number()); 
			    					logger.info("getApproved Test 3");

			    					member = getMember2_EmailID(member);
			    					logger.info("getApproved Test 4");


			    				}
		    					logger.info("getApproved Test 5");

			    				if(me.getMember_Acct_Type().equalsIgnoreCase("platinum")){
			    					logger.info("Test 2");
									/*
									 * member.setRef_ovrriding3(1); // this overriding
									 * member.setRef_commition3(0);//(5);
									 * member.setMember3_primaryKey(me.getMember_ID());
									 */
			    					
			    					if(i==1) {		
			    						logger.info("Silver to Silver with Gold ====");
			    						member.setRef_ovrriding3(0.5); // this overriding
				    					member.setRef_commition3(0);//(5);
				    					member.setMember3_primaryKey(me.getMember_ID());
			    					}else {
			    						logger.info("Silver to Silver without Gold ====");
			    						member.setRef_ovrriding3(1); // this overriding
				    					member.setRef_commition3(0);//(5);
				    					member.setMember3_primaryKey(me.getMember_ID());
			    					}
			    					
			    					
			    					member.setMemberID3(me.getMember_Number()); 
			    					member = getMember3_EmailID(member); 
			    					
			    				}
			    			}
			    			
			    			break;
			    		}
			    	}
			
			    	// Set group name and sequence number
					member.setSequanceNumber(maxNumber+1);
					member.setGroupName(gropName);
					
			    }
			    
			    if(userdetails.getAcctType().equalsIgnoreCase("silver") && acctName.equalsIgnoreCase("gold")) {
					member.setTreeName(treeName);
					member.setLeveNumber(leveNumber+1);
					member.setPayAmt(silverInt);
					//member.setAdminFees(0.5);
					//member.setTotalFees(silverInt+0.5);
					member.setAdminFees(5);
					member.setTotalFees(silverInt+5);
			    	member.setRef_commition1(1); // Refereed one in UI this is for gold
					member.setRef_ovrriding1(0.5);  // Refereed one in UI this is for gold
					System.out.println("Member Overridding1 1st for silver to gold ------------>"+member.getRef_ovrriding1()); 	
					
					// reference member calculation
			    	logger.info("--------------- Referance Account type is GOLD -------------------");
			    	memberInfo=new ArrayList<MemberId>();
					member.setMember_refer_Number1(userdetails.getMember_Ref_ID());
			    	// Set group name and sequence number
					member.setSequanceNumber(maxNumber+1);
					member.setGroupName(gropName);
					member.setMember1_primaryKey(PrimaryKey);
					member.setRefmemberID(userdetails.getMember_Ref_ID());
					member = getMember1_EmailID(member); 
					
			    	memberInfo = getGroupData(memberInfo,gropName);
			    	//member.setPayAmt(1000);
			    	for(MemberId m : memberInfo){	
			    		logger.info("silver and gold getApproved Test 1");
			    		if(m.getMember_Acct_Type().equalsIgnoreCase("platinum")){	
			        		logger.info("platinum find");
			        		member.setRef_commition2(0);
							member.setRef_ovrriding2(0.5);
							//member.setMember_refer_Number2(m.getMember_Number());
							member.setMember2_primaryKey(m.getMember_ID());
							
							member.setMemberID2(m.getMember_Number()); 
							member = getMember2_EmailID(member); 
							
							break;
			    		}
			    	}
				
			    }
			    
			    // Check 1
			    if(userdetails.getAcctType().equalsIgnoreCase("silver") && acctName.equalsIgnoreCase("platinum")) {
					logger.info("--------------- Silver & Platinum Condition Called.-------------------");
					member.setPayAmt(silverInt);
					//member.setAdminFees(0.5);
					//member.setTotalFees(silverInt+0.5);
					member.setAdminFees(5);
					member.setTotalFees(silverInt+5);
					member.setLeveNumber(leveNumber+1);
					member.setGroupName(gropName);	
					newTempCode = getRandamCode(newTempCode,"treeNumber");
					member.setTreeName("A"+newTempCode);
					member.setMember1_primaryKey(PrimaryKey);
					member.setRef_commition1(1); // platinum
					member.setRef_ovrriding1(1); // platinum
					member.setSequanceNumber(maxNumber+1);
					member.setRefmemberID(userdetails.getMember_Ref_ID());
					member = getMember1_EmailID(member); 
					
				}
			
			    // Senario 2
			    if(userdetails.getAcctType().equalsIgnoreCase("gold") && acctName.equalsIgnoreCase("silver")) {
			    	int i=0;
					logger.info("--------------- Gold & Silver Condition Called.-------------------");
					member.setTreeName(treeName);
					member.setLeveNumber(leveNumber+1);
					member.setPayAmt(goldInt);
					member.setAdminFees(50);
					member.setTotalFees(goldInt+50);		
				    	// get only 10% commission and 0% overriding
						member.setRef_commition1(100);
						member.setRef_ovrriding1(0);			
						member.setMember1_primaryKey(PrimaryKey);
						member.setRefmemberID(userdetails.getMember_Ref_ID());
						member = getMember1_EmailID(member); 
				    	
						memberInfo=new ArrayList<MemberId>();
				    	memberInnerInfo=new ArrayList<MemberId>();
				    	memberInfo = getGroupData(memberInfo,gropName);
				    	int tmpSize = memberInfo.size();
				    	logger.info("Group Size --------->"+tmpSize);
				    	for(MemberId m : memberInfo){
				    		if(sequanceNumber==m.getSequanceNumber()){	
				    			// 3
				    			memberInnerInfo = getFiltredData(memberInfo,gropName,sequanceNumber,tmpSize,treeName);
				    			
				    			for(MemberId me : memberInnerInfo) {			    				
				    				//if(me.getMember_Acct_Type().equalsIgnoreCase("gold")){
				        			if(me.getMember_Acct_Type().equalsIgnoreCase("gold") && i==0){
				        				i=1;
				    					logger.info("Test 1");
				    					memberType = "gold";
				    					member.setRef_ovrriding2(50);
				    					member.setRef_commition2(0);//(5);
				    					member.setMember2_primaryKey(me.getMember_ID());
				    					
				    					member.setMemberID2(me.getMember_Number()); 
				    					member = getMember2_EmailID(member); 
				    					
				    				}
				    				if(me.getMember_Acct_Type().equalsIgnoreCase("platinum")){
				    					logger.info("Test 2");
									/*
									 * member.setRef_ovrriding3(100); // this overriding
									 * member.setRef_commition3(0);//(5);
									 * member.setMember3_primaryKey(me.getMember_ID());
									 */
				    					if(i==1) {		
				    						logger.info("Silver to Gold with Gold ====");
				    						member.setRef_ovrriding3(50); 
					    					member.setRef_commition3(0);
					    					member.setMember3_primaryKey(me.getMember_ID());
				    					}else {
				    						logger.info("Silver to Gold without Gold ====");
				    						member.setRef_ovrriding3(100);
					    					member.setRef_commition3(0);
					    					member.setMember3_primaryKey(me.getMember_ID());
				    					}
				    					
				    					member.setMemberID3(me.getMember_Number()); 
				    					member = getMember3_EmailID(member); 
				    					
				    				}
				    			}
				    			break;
				    		}
				    	}
			
				    	// Set group name and sequence number
						member.setSequanceNumber(maxNumber+1);
						member.setGroupName(gropName);
						
			    }
			    
			    if(userdetails.getAcctType().equalsIgnoreCase("gold") && acctName.equalsIgnoreCase("gold")) {
					logger.info("--------------- Gold & Gold Condition Called.-------------------");
					member.setTreeName(treeName);
					member.setLeveNumber(leveNumber+1);
					member.setPayAmt(goldInt);
					member.setAdminFees(50);
					member.setTotalFees(goldInt+50);
			    	member.setRef_commition1(100); // Refereed one in UI this is for gold
					member.setRef_ovrriding1(50); // Refereed one in UI this is for gold		
					// reference member calculation
			    	logger.info("--------------- Referance Account type is GOLD -------------------");
			    	memberInfo=new ArrayList<MemberId>();
					//member.setMember_refer_Number1(member.getRefmemberID());
			    	member.setMember_refer_Number1(userdetails.getMember_Ref_ID());
			    	// Set group name and sequence number
					member.setSequanceNumber(maxNumber+1);
					member.setGroupName(gropName);
					member.setMember1_primaryKey(PrimaryKey); 
					member.setRefmemberID(userdetails.getMember_Ref_ID());
					member = getMember1_EmailID(member); 
					
			    	memberInfo = getGroupData(memberInfo,gropName);
			    	//member.setPayAmt(1000);
			    	for(MemberId m : memberInfo){		    		
			    		if(m.getMember_Acct_Type().equalsIgnoreCase("platinum")){					
							member.setMember_refer_Number2(m.getMember_Number());
						  	
							member.setRef_commition2(0);
							member.setRef_ovrriding2(50);
							member.setMember2_primaryKey(m.getMember_ID());
							
							member.setMemberID2(m.getMember_Number()); 
							member = getMember2_EmailID(member); 
								
			    		}
			    	}
				
			    }
			    // check 2
			    // New member                                     // Refer member
			    if(userdetails.getAcctType().equalsIgnoreCase("gold") && acctName.equalsIgnoreCase("platinum")) {
					logger.info("--------------- Gold & Platinum Condition Called.-------------------");
					member.setPayAmt(goldInt);
					member.setAdminFees(50);
					member.setTotalFees(goldInt+50);
					member.setLeveNumber(leveNumber+1);
					newTempCode = getRandamCode(newTempCode,"treeNumber");
					member.setTreeName("A"+newTempCode);
					member.setGroupName(gropName);
					member.setMember1_primaryKey(PrimaryKey);
					member.setRef_commition1(100);
					member.setRef_ovrriding1(100);
					member.setSequanceNumber(maxNumber+1);
					member.setRefmemberID(userdetails.getMember_Ref_ID());
					member = getMember1_EmailID(member); 
					
			    }
			    
			    // Senario 3
			    if(userdetails.getAcctType().equalsIgnoreCase("platinum") && acctName.equalsIgnoreCase("silver")) {
					logger.info("--------------- Platinum & Silver Condition Called.-------------------");
					int i=0;
					member.setRef_commition1(1000);
					member.setRef_ovrriding1(0);			
					member.setMember1_primaryKey(PrimaryKey);  
					member.setRefmemberID(userdetails.getMember_Ref_ID());
					member = getMember1_EmailID(member); 
					
					memberInfo=new ArrayList<MemberId>();
			    	 memberInnerInfo=new ArrayList<MemberId>();
			    	memberInfo = getGroupData(memberInfo,gropName);
			    	int tmpSize = memberInfo.size();
			    	logger.info("Group Size --------->"+tmpSize);
			    	for(MemberId m : memberInfo){
			    		
			    		if(sequanceNumber==m.getSequanceNumber()){    			// 3
			    			memberInnerInfo = getFiltredData(memberInfo,gropName,sequanceNumber,tmpSize,treeName);
			    			logger.info("Size ------------->"+memberInnerInfo.size());
			    			for(MemberId me : memberInnerInfo) {	
			    				logger.info("Member ID ---------->"+me.getMember_ID());
			    				if(me.getMember_Acct_Type().equalsIgnoreCase("gold") && i==0){
			    					i=1;
			    					//logger.info("Test 1");
			    					logger.info("Scenario 3 Inside for loop | If account type gold Test 1");

			    					member.setRef_ovrriding2(500);
			    					member.setRef_commition2(0);//(5);
			    					member.setMember2_primaryKey(me.getMember_ID());
			    					
			    					member.setMemberID2(me.getMember_Number()); 
			    					member = getMember2_EmailID(member); 
			    					
			    				}
			    				if(me.getMember_Acct_Type().equalsIgnoreCase("platinum")){
			    					logger.info("Test 2");
									/*
									 * member.setRef_ovrriding3(1000); // this overriding
									 * member.setRef_commition3(0);//(5);
									 * member.setMember3_primaryKey(me.getMember_ID());
									 */
			    					
			    					if(i==1) {		
			    						logger.info("Silver to Platinum with Gold ====");
			    						member.setRef_ovrriding3(500); 
				    					member.setRef_commition3(0);
				    					member.setMember3_primaryKey(me.getMember_ID());
			    					}else {
			    						logger.info("Silver to Platinum without Gold ====");
			    						member.setRef_ovrriding3(1000);
				    					member.setRef_commition3(0);
				    					member.setMember3_primaryKey(me.getMember_ID());
			    					}
			    					
			    					member.setMemberID3(me.getMember_Number()); 
			    					member = getMember3_EmailID(member); 
			    					
			    					//break;
			    				}
			    			}
			    			break;
			    		}
			    	}
			    	
					member.setSequanceNumber(1);
					member.setGroupName(gropName);
					NewGroup = getRandamCode(NewGroup,"CurrentGroupCode");
					member.setPayAmt(platinumInt);
					member.setAdminFees(500);
					member.setTotalFees(platinumInt+500);	
					member.setGroupName(NewGroup);
					member.setLeveNumber(0);
					member.setTreeName("A0");
			    }
			    
			    if(userdetails.getAcctType().equalsIgnoreCase("platinum") && acctName.equalsIgnoreCase("gold")) {
					logger.info("--------------- Platinum & Gold Condition Called.-------------------");
					member.setTreeName(treeName);
					member.setLeveNumber(leveNumber+1);
					member.setLeveNumber(0);
					member.setTreeName("A0");
					//CurrentGroupCode
					NewGroup = getRandamCode(NewGroup,"CurrentGroupCode");
					member.setPayAmt(platinumInt);
					member.setAdminFees(500);
					member.setTotalFees(platinumInt+500);
			    	member.setRef_commition1(1000); // Refereed one in UI this is for gold
					member.setRef_ovrriding1(500); // Refereed one in UI this is for gold   		
					
					// reference member calculation
			    	logger.info("--------------- Referance Account type is GOLD -------------------");
			    	memberInfo=new ArrayList<MemberId>();
					member.setMember_refer_Number1(userdetails.getMember_Ref_ID());
			    	// Set group name and sequence number
					member.setSequanceNumber(1);
					member.setGroupName(gropName);
					member.setMember1_primaryKey(PrimaryKey);  	
					member.setRefmemberID(userdetails.getMember_Ref_ID());
					member = getMember1_EmailID(member); 
					
			    	memberInfo = getGroupData(memberInfo,gropName);
			    	//member.setPayAmt(1000);
			    	for(MemberId m : memberInfo){		    		
			    		if(m.getMember_Acct_Type().equalsIgnoreCase("platinum")){	//platinum				
							member.setMember_refer_Number2(m.getMember_Number());    			  	
							member.setRef_commition2(0);
							member.setRef_ovrriding2(500);   				
							member.setMember2_primaryKey(m.getMember_ID());
							
							member.setMemberID2(m.getMember_Number()); 
							member = getMember2_EmailID(member); 
							
							break;
			    		}
			    	}
				
					member.setGroupName(NewGroup);
			    }
			    
			    // check 3
			    if(userdetails.getAcctType().equalsIgnoreCase("platinum") && acctName.equalsIgnoreCase("platinum")) {
					logger.info("--------------- Platinum & Platinum Condition Called.-------------------");
					member.setPayAmt(platinumInt);
					member.setAdminFees(500);
					member.setTotalFees(platinumInt+500);
					member.setLeveNumber(0);
					NewGroup = getRandamCode(NewGroup,"CurrentGroupCode");
					logger.info("GROUPName-------"+NewGroup); 
					member.setGroupName(NewGroup);
					member.setTreeName("A0");
					member.setMember1_primaryKey(PrimaryKey);
					member.setRef_commition1(1000);
					member.setRef_ovrriding1(1000);
			    	//CurrentGroupCode
					member.setSequanceNumber(1);
					member.setRefmemberID(userdetails.getMember_Ref_ID());
					member = getMember1_EmailID(member); 
					
					logger.info("--------------- Referance Account type is Platinum -------------------");
			    }
				
			    query=entityManager.createQuery("from MemberId where member_Number=?");
				query.setParameter(1, userdetails.getMemberID());
				MemberId saveMember=(MemberId)query.getSingleResult();	
				
				
				saveMember.setGroupName(member.getGroupName());
				saveMember.setMember_Acct_Type(userdetails.getAcctType());
				saveMember.setSequanceNumber(member.getSequanceNumber());
				saveMember.setMember_Number(userdetails.getMemberID());
				saveMember.setTotalCommission(member.getCommition());
				saveMember.setTotalOverriding(member.getOverriding());
				saveMember.setLevel_number(member.getLeveNumber());
				saveMember.setTree_name(member.getTreeName());
				saveMember.setWithdraw_Status("Not Available");
				entityManager.merge(saveMember);
				
				member.setMemberNumber(userdetails.getMemberID()); 
				member.setCountry(userdetails.getCountry()); 
				logger.info("----Country Name ----->"+member.getCountry()); 
				refer1Update(member); 
				logger.info("------------[DAO] Successfully Called UpdateMember -------------"); 
	
				logger.info("------------[DAO] After Update Refernce Member Commission and Overridding -------------"); 
			 	query=entityManager.createQuery("from CommOverrDetail where Member_Number=?");
				query.setParameter(1, userdetails.getMemberID());
				ArrayList<CommOverrDetail> result=(ArrayList<CommOverrDetail>)query.getResultList();
				logger.info("List size --------------->"+result.size());
				if(result.size()>0) {
					for(CommOverrDetail comm:result){
						//double tempComm=0;
						//double tempOver=0;
						memberid=null;
						memberid = new MemberId();
						logger.info("Member table primary key ---->"+comm.getMemberId().getMember_ID());
						logger.info("Child Overriding amount ---->"+comm.getOverridingAmt());
						logger.info("Child Commission amount---->"+comm.getCommissionAmt());
						
						memberid = entityManager.find(MemberId.class, comm.getMemberId().getMember_ID());
						logger.info("Parent Total Overriding amount ---->"+memberid.getTotalCommission());
						logger.info("Parent Total Commission amount---->"+memberid.getTotalOverriding());
						
						/*tempComm = memberid.getTotalCommission() + comm.getCommissionAmt();
						tempOver = memberid.getTotalOverriding() + comm.getOverridingAmt();
						logger.info("Total Comm ---->"+tempComm);
						logger.info("Total Over---->"+tempOver);						
						memberid.setTotalCommission(tempComm);
						memberid.setTotalOverriding(tempOver);*/
						entityManager.merge(memberid);
						entityManager.flush();
						entityManager.clear();
						
						logger.info("------------[DAO] After Update MemberID Table -------------"); 
						logger.info("------------[DAO] Commission Member Number -------------"+comm.getMember_Number()); 
						query=entityManager.createQuery("from MemberId WHERE member_Number='" +comm.getMember_Number()+"' ");
						MemberId mem = (MemberId) query.getSingleResult();
						System.out.println("Member Number -->"+mem.getMember_Number());
						mem.setWithdraw_Status("Available");
						entityManager.merge(mem);
						entityManager.flush();
						entityManager.clear();
						
						logger.info("Member table is updated successfully");
						
						comm.setStatus("approved");
						entityManager.merge(comm);
						entityManager.flush();
						entityManager.clear();
						logger.info("------------[DAO] After Update Status in CommissionOverriding Table -------------"); 
					}
					
					userlogin.setStatus("Approved");
					entityManager.merge(userlogin);
					entityManager.flush();
					entityManager.clear();
					logger.info("------------[DAO] After Update Status in UserLogin Table -------------"); 
				}
				else {
					logger.info("No child record found");
				}
				user.setStatus("success");
				user.setEmail_ID(userdetails.getEmail1());
				user.setUsername(userdetails.getFirstname() + " " + userdetails.getLastname());
				user.setMemberID(userdetails.getMemberID());
				user.setAccoutType(userdetails.getAcctType());
				
				newCodeFinal = userdetails.getMemberID();
				if(userdetails.getAcctType().equalsIgnoreCase("silver")){
					member.setTriptype("Member");
				}else if(userdetails.getAcctType().equalsIgnoreCase("gold")){
					member.setTriptype("Franchise");
				}else if(userdetails.getAcctType().equalsIgnoreCase("platinum")){
					member.setTriptype("Master Franchise");
				}
				logger.info("Member Country ------------>"+userdetails.getCountry());
				if(userdetails.getCountry().equalsIgnoreCase("Indonesia")){
					logger.info("----------- No Commission and Overridding for Indonesia Customer ----------");
				}else{ 
					Email.saveEmailReferMember1(member,newCodeFinal);
					if(member.getEmailID2() != null){
						Email.saveEmailReferMember2(member,newCodeFinal);
					}
					if(member.getEmailID3() != null){
						Email.saveEmailReferMember3(member,newCodeFinal);
					}
				}				
			}
			if(requestType.equalsIgnoreCase("Reject")) {
				UserDetail userdetails = entityManager.find(UserDetail.class, userLoginPrimaryKey);
				userlogin.setStatus("Rejected");
				entityManager.merge(userlogin);
				user.setEmail_ID(userdetails.getEmail1());
				user.setMemberID(userdetails.getMemberID());
				user.setStatus("rejectSuccess");
			}
			logger.info("Member data is successfully saved.");
		}catch(Exception e) {
			user.setStatus("failure");
						logger.error("Exception -->"+e.getMessage());
		}finally{
			
		}
		return user;
	}
	
// ---------------- reservation approve -------------------------	
	@Transactional(value="transactionManager")
	public User getApprovedForReservation(User user,int userLoginPrimaryKey,String requestType){
		logger.info("------------[DAO] Inside getApprovedForReservation Method --------------");
		UserLogin userlogin;
		try{
			BookingDetail booking = entityManager.find(BookingDetail.class, userLoginPrimaryKey);
			
			userlogin = entityManager.find(UserLogin.class, booking.getUserLogin().getUser_Login_ID());
			logger.info("UserLogin Primary Key ------>"+booking.getUserLogin().getUser_Login_ID());
			user.setPassword(booking.getInvoiceNumber());
			logger.info("Invoice Number --------->"+user.getPassword());
		    logger.info("Member Email sending Email ID -->"+userlogin.getUserDetails().get(0).getEmail1());
		    user.setEmail_ID(userlogin.getUserDetails().get(0).getEmail1());
		    logger.info("Member Email sending Member ID -->"+userlogin.getUserDetails().get(0).getMemberID());
		    user.setMemberID(userlogin.getUserDetails().get(0).getMemberID());
		    user.setPaymentPath(booking.getImagePath()); 
		    user.setUserLoginPrimaryKey(booking.getBooking_ID()); 
		    user.setAccoutType(booking.getBookingCode()); 
		    logger.info("Payment Path -->"+user.getPaymentPath());
			if(requestType.equalsIgnoreCase("Approve")) {
				booking.setBookingStatus("Approved");
				entityManager.merge(booking);
				user.setStatus("success");
			}
			if(requestType.equalsIgnoreCase("Reject")) {
				booking.setBookingStatus("Rejected");
				entityManager.merge(booking);
				user.setStatus("rejectSuccess");
			}
			//user.setStatus("rejectSuccess");			
			logger.info("Member data is successfully saved.");
		}catch(Exception e) {
			user.setStatus("failure");
						logger.error("Exception -->"+e.getMessage());
		}finally{
			
		}
		return user;
	}
	
	// ------------------ view my profile ---------------------
	@Transactional(value="transactionManager")
	public Member getMyProfile(Member member){
		Query query;
		logger.info("Primary Key-->"+member.getUserloginPrimaryKeyString());
		try {
			logger.info("getMyProfile 1");
			UserLogin userlogin = entityManager.find(UserLogin.class, Integer.valueOf(member.getUserloginPrimaryKeyString()));
			// Single record ...
			member.setUsername(userlogin.getUsername());
			member.setActType(userlogin.getUserDetails().get(0).getAcctType());
			if(member.getActType().equalsIgnoreCase("silver")) {
				member.setTriptype("Member");
			}else if(member.getActType().equalsIgnoreCase("gold")) {
				member.setTriptype("Franchise");
			}else if(member.getActType().equalsIgnoreCase("platinum")) {
				member.setTriptype("Master Franchise");
			}
			member.setBankName(userlogin.getUserDetails().get(0).getBankName());
			member.setBankAcctNumber(userlogin.getUserDetails().get(0).getBankAcctNumber());
			member.setCountry(userlogin.getUserDetails().get(0).getCountry());
			member.setEmailID(userlogin.getUserDetails().get(0).getEmail1());
			member.setPhoneNumber(userlogin.getUserDetails().get(0).getPhonenumber1());
			member.setFirstName(userlogin.getUserDetails().get(0).getFirstname());
			member.setLastName(userlogin.getUserDetails().get(0).getLastname());
		 	query=entityManager.createQuery("from MemberId where member_Number=?");
			query.setParameter(1, userlogin.getUserDetails().get(0).getMemberID());
			MemberId memberResult = (MemberId)query.getSingleResult();
			//ArrayList<MemberId> memberResult=(ArrayList<MemberId>)query.getResultList();
		
		 	member.setGroupName(memberResult.getGroupName());
		 	member.setMemberID(memberResult.getMember_Number());
		 	member.setMemberCommition(memberResult.getTotalCommission());
		 	member.setMemberOvrriding(memberResult.getTotalOverriding());
		 	member.setMemberID1(String.valueOf(memberResult.getMember_ID()));
			logger.info("getMyProfile 2");

		 	// with draw display 
		 	member.setTotalAmount(memberResult.getTotalCommission() + memberResult.getTotalOverriding());
			logger.info("getMyProfile 3");

		}catch(Exception e){
			logger.info("My Profile Exception -->"+e.getMessage());
		} finally {
			
		}
		return member;
	}
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<Member> getCountryInfo(Member member, ArrayList<Member> list,String ImagePath){
		Query query;
		List<OrganizationList> result=null;
		try {
			if(member.getSequanceNumber() == 1){
				
				query=entityManager.createQuery("from OrganizationList where countryName=? and stateName=? and category=?");
				query.setParameter(1, member.getSelectedCountry());
				query.setParameter(2, member.getSelectedState());
				query.setParameter(3, member.getCategoryname());
				result =(ArrayList<OrganizationList>)query.getResultList();
				logger.info("List Size ----------------->"+result.size()); 
				if(result.size() > 0) {
					int i=1;
					for(OrganizationList org : result) {
						member = new Member();
						member.setsNo(i);
						member.setCategoryname(org.getCategory());
						member.setSelectedCountry(org.getCountryName()); 
						member.setSelectedState(org.getStateName());
						member.setCname(org.getName());
						member.setDescription(org.getDescription()); 
						member.setPhoneNumber(org.getPhoneNumber());
						member.setEmailID(org.getEmailID());
						member.setPrice(org.getPrice());
						logger.info("Description ----------->"+org.getDescription()); 
						member.setUserLoginPrimaryKey(org.getOrg_ID()); 
						member.setUserloginPrimaryKeyString(String.valueOf(org.getOrg_ID())); 
						if(ImagePath == "WebCompanyList"){
							if(org.getImagePath()==null || org.getImagePath()=="") {
								logger.info("No Image found");
								member.setHotelImagePath("/assets/Files/no_image.jpg");
							}
							else {
								logger.info("Image Path -->"+org.getImagePath());
								member.setHotelImagePath(org.getImagePath());  	
							}
						}else if(ImagePath == "MobileCompanyList"){
							if(org.getImagePath()==null || org.getImagePath()=="") {
								logger.info("No Image found");
								//member.setHotelImagePath("E:/joseny/Ggl Technology/2019/March/28/ggl-ui/src/assets/Files/no_image.jpg");
								//member.setHotelImagePath("/var/www/html/assets/Files/no_image.jpg");
								member.setHotelImagePath("http://35.162.40.190/assets/Files/no_image.jpg");

							}
							else {
								//logger.info("Image Path -->"+org.getImagePath());
								//member.setHotelImagePath("E:/joseny/Ggl Technology/2019/March/28/ggl-ui/src"+org.getImagePath());  
								//member.setHotelImagePath("/var/www/html"+org.getImagePath()); 
								member.setHotelImagePath("http://35.162.40.190"+org.getImagePath()); 

							//	member.setHotelImagePath("http://35.162.40.190/assets/Files/no_image.jpg");

								logger.info("Mobile App Image Path -->"+member.getHotelImagePath());	
							}
						}
						list.add(member);
						i++;
					}
				}
				
			}else if(member.getSequanceNumber() == 2){
				
				query=entityManager.createQuery("from OrganizationList where countryName=?");
				query.setParameter(1, member.getSelectedCountry());
				result =(ArrayList<OrganizationList>)query.getResultList();
				logger.info("List Size --------------------->"+result.size()); 
				if(result.size() > 0) {
					int i=1;
					for(OrganizationList org : result) {
						member = new Member();
						member.setsNo(i);
						member.setCategoryname(org.getCategory());
						member.setSelectedCountry(org.getCountryName()); 
						member.setSelectedState(org.getStateName());
						member.setCname(org.getName());
						member.setDescription(org.getDescription()); 
						member.setPhoneNumber(org.getPhoneNumber());
						member.setEmailID(org.getEmailID());
						member.setPrice(org.getPrice()); 
						member.setUserLoginPrimaryKey(org.getOrg_ID()); 
						member.setUserloginPrimaryKeyString(String.valueOf(org.getOrg_ID())); 
						if(ImagePath == "WebCompanyList"){
							if(org.getImagePath()==null || org.getImagePath()=="") {
								logger.info("No Image found");
								member.setHotelImagePath("/assets/Files/no_image.jpg");
							}
							else {
								logger.info("Image Path -->"+org.getImagePath());
								member.setHotelImagePath(org.getImagePath());  
									
							}
						}else if(ImagePath == "MobileCompanyList"){
							if(org.getImagePath()==null || org.getImagePath()=="") {
								logger.info("No Image found");
								//member.setHotelImagePath("E:/joseny/Ggl Technology/2019/March/28/ggl-ui/src/assets/Files/no_image.jpg");
								//member.setHotelImagePath("/var/www/html/assets/Files/no_image.jpg");
								member.setHotelImagePath("http://35.162.40.190/assets/Files/no_image.jpg");

							}
							else {
								//logger.info("Image Path -->"+org.getImagePath());
								//member.setHotelImagePath("E:/joseny/Ggl Technology/2019/March/28/ggl-ui/src"+org.getImagePath());  
								//member.setHotelImagePath("/var/www/html"+org.getImagePath()); 
								member.setHotelImagePath("http://35.162.40.190"+org.getImagePath()); 

								logger.info("Mobile App Image Path -->"+member.getHotelImagePath());	
							}
						}
						
						list.add(member);
						i++;
					}
				}
				
			}
		} catch(Exception e) {
			logger.info("DAO getCountryInfo Exception -->"+e.getMessage());
		}finally {
			
		}
		return list;
	}
	
	
	@Transactional(value="transactionManager")
	public ArrayList<GLGMem> getMyCommandOverInfo(String primaryKeyStr,ArrayList<CommOverrDetail> commdetails){
		Query query;
		ArrayList<GLGMem> glgmember=null;
		GLGMem ggl = null;// new GLGMem(); 
		int temp=1;
		double totalCommission = 0.0;
		double totalOverriding = 0.0;
		logger.info("Primary Key ---------"+Integer.valueOf(primaryKeyStr));
		UserLogin userlogin = entityManager.find(UserLogin.class, Integer.valueOf(primaryKeyStr));
		logger.info("Member Number ---------"+userlogin.getUserDetails().get(0).getMemberID());
		query=entityManager.createQuery("from MemberId where member_Number=?");
		query.setParameter(1, userlogin.getUserDetails().get(0).getMemberID());			
		MemberId memberResult = (MemberId)query.getSingleResult();	
		logger.info("List Size ---------"+memberResult.getCommOverrDetails().size());
		if(memberResult.getCommOverrDetails().size()>0){
			glgmember=new ArrayList<GLGMem>();
			for(int i=0;memberResult.getCommOverrDetails().size()>i;i++) {
				//temp=1;
				logger.info("Date --->"+memberResult.getCommOverrDetails().get(i).getCreated_date());
				logger.info("Commision --->"+memberResult.getCommOverrDetails().get(i).getCommissionAmt());
				logger.info("Overriding --->"+memberResult.getCommOverrDetails().get(i).getOverridingAmt());
			
				if(memberResult.getCommOverrDetails().get(i).getStatus().equalsIgnoreCase("approved")) {
					ggl = new GLGMem();
					ggl.setsNo(temp);
					
					totalCommission +=  memberResult.getCommOverrDetails().get(i).getCommissionAmt();
					logger.info("Total Commission ------>"+totalCommission); 
					ggl.setTotalCommission(totalCommission); 
					
					totalOverriding +=  memberResult.getCommOverrDetails().get(i).getOverridingAmt();
					logger.info("Total Overriding ------>"+totalOverriding); 
					ggl.setTotalOverriding(totalOverriding);
					ggl.setCountry(memberResult.getCommOverrDetails().get(i).getCountry()); 
					ggl.setMemberOvrriding(memberResult.getCommOverrDetails().get(i).getOverridingAmt());
					ggl.setMemberCommition(memberResult.getCommOverrDetails().get(i).getCommissionAmt());
					ggl.setCreated_date(memberResult.getCommOverrDetails().get(i).getCreated_date());
					ggl.setTotalAmount(memberResult.getCommOverrDetails().get(i).getOverridingAmt()+memberResult.getCommOverrDetails().get(i).getCommissionAmt());
					temp++;
					glgmember.add(ggl);
				}
				
				
			}
		}
		return glgmember;
	}
		
	// -------------------------- Save Organization ---------------------
	@Transactional(value="transactionManager")
	public Member saveOrganization(Member member){
		OrganizationList orglist;
		Query query;
		//List<OrganizationList> result;
		try {
			query=entityManager.createQuery("from OrganizationList where name=?");
			query.setParameter(1,member.getCname());
			//orglist=(List<OrganizationList>)query.getSingleResult();
			orglist = (OrganizationList)query.getSingleResult();

			if(orglist == null){
				logger.info("No data found !!!"); 
				//Do your logic..
			}
			
			if(orglist.getName().equalsIgnoreCase(member.getCname()) && orglist.getCountryName().equalsIgnoreCase(member.getSelectedCountry())) {
				member.setStatus("Exist"); // UI purpose			
			}
			else {
				orglist = new OrganizationList();
				orglist.setCountryName(member.getCountry());
				orglist.setStateName(member.getSelectedState());
				orglist.setCategory(member.getCategoryname());
				orglist.setName(member.getCname());
				logger.info("Member Price  ----------->"+member.getPrice());
				orglist.setPrice(member.getPrice()); 
				logger.info("Org Price  ----------->"+orglist.getPrice());
				orglist.setDiscount(50);
				orglist.setDescription(member.getDescription());
				orglist.setPhoneNumber(member.getPhoneNumber());
				orglist.setEmailID(member.getEmailID());
				entityManager.persist(orglist);
			    member.setStatus("success");
			    member.setUserloginPrimaryKeyString(String.valueOf(orglist.getOrg_ID()));
			}
			
		}
		catch (NoResultException nre){
			//Ignore this because as per your logic this is ok!
			orglist = new OrganizationList();
			orglist.setCountryName(member.getCountry());
			orglist.setStateName(member.getSelectedState());
			orglist.setCategory(member.getCategoryname());
			orglist.setName(member.getCname());
			logger.info("NoResult Member Price  ----------->"+member.getPrice());
			orglist.setPrice(member.getPrice()); 
			logger.info("NoResult Org Price  ----------->"+orglist.getPrice());
			orglist.setDiscount(50);
			orglist.setDescription(member.getDescription());
			orglist.setPhoneNumber(member.getPhoneNumber());
			orglist.setEmailID(member.getEmailID()); 
			entityManager.persist(orglist);
		    member.setStatus("success");
		    member.setUserloginPrimaryKeyString(String.valueOf(orglist.getOrg_ID()));
		}
		catch(Exception e) {
			 member.setStatus("failure");
			 logger.info("DAO Exception -->"+e.getMessage());		
		}
		
		finally {
			
		}
		return member;
	}

	// ------------------- load Organization list --------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<String> getName(ArrayList<String> namelist, Member member, int temp) {
		Query query=null;
		try{
			if(temp == 1){
				logger.info("--------- If Member Hotel List Name ----------");
				query=entityManager.createNativeQuery("select org.name from organization_list as org");
				namelist=(ArrayList<String>)query.getResultList();
				logger.info("Name list size ---------------->"+namelist.size());
				if(namelist.size() > 0) {
					logger.info("Name list ------------->"+namelist.get(0));
				}
			}else if(temp == 2){
				logger.info("--------- If Admin Hotel List Name ----------");
				query=entityManager.createNativeQuery("select org.name from organization_list as org where org.country_name=? and org.state_name=? and org.category=?");
				query.setParameter(1, member.getSelectedCountry());
				query.setParameter(2, member.getSelectedState());
				query.setParameter(3, member.getCategoryname());		
				namelist=(ArrayList<String>)query.getResultList();
				logger.info("Name list size ---------------->"+namelist.size());
				if(namelist.size() > 0) {
					logger.info("Name list ------------->"+namelist.get(0));
				}
			}
		 	//query=entityManager.createQuery("select org.name from OrganizationList as org where org.countryName=? and org.stateName=? and org.category=?");
			
			return namelist;	
		}catch(Exception e) {
				logger.info("Exception --->"+e.getMessage());
		}finally {
			
		}
		return namelist;	
	}

				
				
	// -------------------- get Country list ---------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<String> loadCountry(){
		ArrayList<String> result=null;
		Query query=null;
		logger.info("1");
		try {
			query=entityManager.createNativeQuery("select o.country_name from organization_list as o");
			logger.info("2");
			//query=entityManager.createQuery("select org.countryName from OrganizationList as org");
			result = (ArrayList<String>) query.getResultList();
			logger.info("3");
			//result=(ArrayList<OrganizationList>)query.getResultList();
		}catch(Exception e) {
			logger.info("DAO - Exception --------------->"+e.getMessage());
		}finally{
			
		}		
		return result;
	}
				
	// -------------------- get State list ---------------------	
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<String> loadStateList(String country){
		ArrayList<String> result=null;
		Query query=null;
		try {
			// query=entityManager.createQuery("select org.stateName from OrganizationList as org where org.countryName=?");
			query=entityManager.createNativeQuery("select org.state_name from organization_list as org where org.country_name=?");

			query.setParameter(1, country);
			result = (ArrayList<String>) query.getResultList();
			//result=(ArrayList<OrganizationList>)query.getResultList();
		}catch(Exception e) {
			logger.info("Exception --------------->"+e.getMessage());
		}finally{
			
		}		
		return result;
	}
			// Load Category list
	
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<String> loadCategory(String country,String state){
		ArrayList<String> result=null;
		Query query=null;
		try {
			query=entityManager.createNativeQuery("select org.category from organization_list as org where org.country_name=? and org.state_name=?");
			query.setParameter(1, country);
			query.setParameter(2, state);
			result = (ArrayList<String>) query.getResultList();
		}catch(Exception e) {
			logger.info("Exception --------------->"+e.getMessage());
		}finally{
			
		}		
		return result;
	}
	

	// ---------------- Update Payment ------------------------------
	@Transactional(value="transactionManager")
	public Member UpdatePayment(Member member)
	{
		logger.info("------------------ Inside UpdatePayment DAO ---------------");	
		Query query=null;
		UserDetail result;
		try {
			query=entityManager.createQuery("UPDATE UserDetail  set payStatus = 'paid' WHERE memberID='" +member.getMemberNumber()+"'");
		    query.executeUpdate();
			logger.info("Payment Status is updated Successfully");	
			query=null;
			query=entityManager.createQuery("from UserDetail WHERE memberID='" +member.getMemberNumber()+"'");
			result = (UserDetail) query.getSingleResult();
			logger.info("Email ID -->"+result.getEmail1());
			member.setEmailID(result.getEmail1());
			return member;
		}catch(Exception e ) {
			//status=false;
		}
		finally {
			query=null;
			
		}
		return member;//dao.UpdatePayment();
	
	}

	//---------- Update My Profile ---------------
	@Transactional(value="transactionManager")
	public Member updateMyProfile(Member member){
		logger.info("------------------ Inside updateMyProfile DAO ---------------");
		UserDetail userdetails = null;
		try{
			 userdetails=new UserDetail();
			 int tempPrimary = Integer.valueOf(member.getUserloginPrimaryKeyString());
			 userdetails = entityManager.find(UserDetail.class, tempPrimary);
			 logger.info("user login primary id---------->"+tempPrimary); 
			 userdetails.setFirstname(member.getFirstName());
			 userdetails.setLastname(member.getLastName());
			 userdetails.setEmail1(member.getEmailID());
			 userdetails.setPhonenumber1(member.getPhoneNumber());
			 userdetails.setCountry(member.getCountry());
			 userdetails.setBankName(member.getBankName());
			 userdetails.setBankAcctNumber(member.getBankAcctNumber());
			 //memberId = entityManager.find(MemberId.class, member.getUserloginPrimaryKeyString());
			 entityManager.persist(userdetails);		
			 member.setStatus("success");
		}catch(Exception e){
			 member.setStatus("failure");
			logger.info("Exception -->"+e.getMessage());
		}
		finally{
			 entityManager.flush();
			 entityManager.clear();
		}
	
		return member;
	}

	//---------- submit withdraw amount ---------------
	@Transactional(value="transactionManager")
	public Member submitWith(Member member){
		logger.info("---[DAO] Inside submitWith Method ---");
		Query query = null;
		try{
			logger.info("DAO Commisstion Amount -->"+member.getMemberCommition());
			logger.info("DAO Over riding Amount -->"+member.getMemberOvrriding());
			logger.info("Total Amount -->"+member.getTotalAmount());  
		 	
		 	query=entityManager.createQuery("from MemberId WHERE member_Number='" +member.getMemberID()+"' ");
			MemberId mem = (MemberId) query.getSingleResult();
			logger.info("Member Number -->"+mem.getMember_Number());
			mem.setWithdraw_Status("Requested For Withdraw");
			entityManager.merge(mem);
			entityManager.flush();
			entityManager.clear();
			
			member.setStatus("success");
					
		}catch(Exception e){
			member.setStatus("failure");
			logger.info("Exception -->"+e.getMessage());
		}
		finally{
			
		}
	
		return member;
	}
	
	// ---------------------- load ALL GLG Member Data -------------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<Member> getAllWithdrawList(String requestType,ArrayList<Member> withdrawList){
		Query query=null;
		Member member;
		ArrayList<MemberId> resultList;
		try {
			if(requestType.equalsIgnoreCase("RequestingForWithdraw")) {
				query=entityManager.createQuery("from MemberId where Withdraw_Status=?");
				query.setParameter(1, "Requested For Withdraw");
					
				resultList=(ArrayList<MemberId>)query.getResultList();	
				logger.info("Total Member Size --------------->"+resultList.size());
				for(int i=0;i<resultList.size();i++){
					member = new Member();
					member.setMemberID(resultList.get(i).getMember_Number());
					member.setStatus(resultList.get(i).getWithdraw_Status());
					member.setMemberCommition(resultList.get(i).getTotalCommission());
					member.setMemberOvrriding(resultList.get(i).getTotalOverriding());
					member.setMember1_primaryKey(resultList.get(i).getMember_ID());
					member.setTotalAmount(resultList.get(i).getTotalCommission() + resultList.get(i).getTotalOverriding()); 
					withdrawList.add(member);

				}
			}
		}catch(Exception e) {
			logger.info("[DAO] WithdrawList Exception ----------->"+e.getMessage());
		}finally{
			
		}		
		return withdrawList;
	
	}	
	
	//------------------ DAO Approve Withdraw Request -------------
	@Transactional(value="transactionManager")
	public Member getApproveForWithdraw(Member member,int userLoginPrimaryKey,String requestType){
		logger.info("------------[DAO] Inside getApproveForWithdraw Method --------------");		
		MemberId memberId=null;
		CommOverrDetail comm=null; 
		Query query = null;
		try{
			logger.info("Primary Key ------------>"+userLoginPrimaryKey); 
			logger.info("Member Number ------------>"+member.getMemberNumber());  
			member.setUserLoginPrimaryKey(userLoginPrimaryKey); 
			logger.info("Primary Key Int value ------------>"+member.getUserLoginPrimaryKey());
			memberId = entityManager.find(MemberId.class, userLoginPrimaryKey);
		    
			if(requestType.equalsIgnoreCase("Approve")) {														
				comm = new CommOverrDetail();
				comm.setCommissionAmt(member.getCommition());
				comm.setOverridingAmt(member.getOverriding());
				comm.setMember_Number(member.getMemberNumber()); 
				comm.setCreated_date(Custom.getCurrentDate());
				comm.setValue_type("subtract");
				comm.setStatus("approved");
				comm.setMemberId(memberId);
				entityManager.persist(comm);
				entityManager.flush();
				entityManager.clear();
				
				memberId.setWithdraw_Status("Approved Withdraw");
				memberId.setTotalCommission(0);
				memberId.setTotalOverriding(0);
				memberId.setTotalAmount("0");
				entityManager.merge(memberId);
				entityManager.flush();
				entityManager.clear();
				
				query=entityManager.createQuery("from UserDetail where memberID=?");
				query.setParameter(1, member.getMemberNumber());
				UserDetail result=(UserDetail)query.getSingleResult();				
				member.setEmailID(result.getEmail1());	
				member.setUsername(result.getFirstname() +" " + result.getLastname()); 
				member.setStatus("success");	
				
			}
			if(requestType.equalsIgnoreCase("Reject")) {
				memberId.setWithdraw_Status("Rejected Withdraw");
				entityManager.merge(memberId);
				entityManager.flush();
				entityManager.clear();
				
				query=entityManager.createQuery("from UserDetail where memberID=?");
				query.setParameter(1, member.getMemberNumber());
				UserDetail result=(UserDetail)query.getSingleResult();					
				member.setEmailID(result.getEmail1());	
				member.setUsername(result.getFirstname() +" " + result.getLastname()); 
				member.setStatus("rejectSuccess");
			}
			logger.info("Withdraw data is successfully Approved.");
		}catch(Exception e) {
			member.setStatus("failure");
			logger.info("[DAO]WithdrawApprove Exception ------>"+e.getMessage());
		}finally{
			
		}
		return member;
	}
	
	//------------- Save Category ----------------
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public Member saveCategory(Member member){
		ArrayList<CategoryDetails> resultList;
		CategoryDetails categorydetails;
		Query query=null;
		try {
			query=entityManager.createQuery("from CategoryDetails where categoryName=?");
			query.setParameter(1, member.getCategoryname());
			resultList=(ArrayList<CategoryDetails>)query.getResultList();	
			logger.info("List size ----->"+resultList.size()); 
			if(resultList.size()>0) {
				member.setStatus("NotExist"); // UI purpose				
			}
			else {
				categorydetails = new CategoryDetails();
				categorydetails.setCategoryName(member.getCategoryname());
				categorydetails.setDescription(member.getDescription());
				categorydetails.setCountryName(member.getSelectedCountry());
				categorydetails.setStateName(member.getSelectedState());
				categorydetails.setCategoryCode(member.getCategoryCode()); 
				entityManager.persist(categorydetails);
			    member.setStatus("success"); 								
			}
			   
		}catch(Exception e) {
			member.setStatus("failure");
			logger.info("[DAO] CategoryName Exception ----------->"+e.getMessage());
		}finally {
			
		}
		return member;
	}
	
	// ---------------------- load ALL Category Data -------------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<Member> getAllCategoryList(ArrayList<Member> categoryList){
		Query query=null;
		Member member;
		ArrayList<CategoryDetails> resultList;
		try {
			query=entityManager.createQuery("from CategoryDetails");						
			resultList=(ArrayList<CategoryDetails>)query.getResultList();	
			logger.info("Total Category Size --------------->"+resultList.size());
			int i=1;
			for(CategoryDetails category : resultList){
				member = new Member();
				member.setsNo(i);
				member.setCategoryname(category.getCategoryName());
				member.setDescription(category.getDescription());
				member.setSelectedCountry(category.getCountryName()); 
				member.setSelectedState(category.getStateName());
				member.setUserLoginPrimaryKey(category.getCategory_ID()); 
				member.setCategoryCode(category.getCategoryCode());
				categoryList.add(member); 
				i++;
			} 
		}catch(Exception e) {
			logger.info("[DAO] CategoryList Exception ----------->"+e.getMessage());
		}finally{
			
		}		
		return categoryList;		
	}	
		
	//------------- Save Category ----------------
	@Transactional(value="transactionManager")
	//@SuppressWarnings("unchecked")
	public Member setCategoryUpdate(Member member){
		//ArrayList<CategoryDetails> resultList;
		//Query query=null;
		CategoryDetails categorydetails;
		try {
			/* query=entityManager.createQuery("from CategoryDetails where categoryName=?");
			query.setParameter(1, member.getCategoryname());
			resultList=(ArrayList<CategoryDetails>)query.getResultList();	
			logger.info("List size ----->"+resultList.size()); 
			if(resultList.size()>0) {
				member.setStatus("NotExist"); 			
			}
			else { */
				categorydetails = entityManager.find(CategoryDetails.class,member.getUserLoginPrimaryKey());
				categorydetails.setCategoryName(member.getCategoryname());
				categorydetails.setDescription(member.getDescription());
				categorydetails.setCountryName(member.getSelectedCountry());
				categorydetails.setStateName(member.getSelectedState());
				categorydetails.setCategoryCode(member.getCategoryCode()); 
				entityManager.merge(categorydetails);
			    member.setStatus("success"); 								
			//}								
		}catch(Exception e) {
			member.setStatus("failure");
			logger.info("[DAO] Update Category Exception ----------->"+e.getMessage());
		}finally {
			
		}
		return member;
	}
	
	// Remove Agent data
	@Transactional(value="transactionManager")
	public String setCategoryRemove(int categoryPK) {
		CategoryDetails categorydetails;
		String response = "failure";
		try {
			categorydetails = entityManager.find(CategoryDetails.class,categoryPK);
		    entityManager.remove(categorydetails);
		    logger.info("Category is removed Successfully....");
		    response = "success"; 
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
			response = "failure";
		}finally {
			
		}
		return response;
	}
	
	//--------- Set Company Update -------
	@Transactional(value="transactionManager")
	public String setCompanyUpdate(Member member) {
		String response = null;
		OrganizationList companydetails;
		try {
				companydetails = entityManager.find(OrganizationList.class,member.getUserLoginPrimaryKey());
				companydetails.setName(member.getCname());
				companydetails.setCategory(member.getCategoryname());
				companydetails.setCountryName(member.getSelectedCountry());
				companydetails.setStateName(member.getSelectedState());
				companydetails.setDescription(member.getDescription()); 
				companydetails.setPhoneNumber(member.getPhoneNumber());
				companydetails.setEmailID(member.getEmailID());
				entityManager.merge(companydetails);
				response="success"; 								
		}catch(Exception e) {
			response="failure";
			logger.info("[DAO] Update Company Exception ----------->"+e.getMessage());
		}finally {
			
		}
		return response;
		
	}
	
	// Remove Agent data
	@Transactional(value="transactionManager")
	public String setCompanyRemove(int companyPK) {
		OrganizationList org;
		String response = "failure";
		try {
			org = entityManager.find(OrganizationList.class,companyPK);
		    entityManager.remove(org);
		    logger.info("Company is removed Successfully....");
		    response = "success"; 
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
			response = "failure";
		}finally {
			
		}
		return response;
	}
	
	// ---------------- Update Image ------------------------------
	@Transactional(value="transactionManager")
	public Member UpdateImage(Member member)
	{
		logger.info("------------------ Inside UpdateImage DAO ---------------");	
		Query query=null;
		try {
			//logger.info("Image Path ---->"+files);
			//String value = " \"+files+\" ";
			//String ROM = "\"" + files + "\"";
			//query=entityManager.createQuery("update OrganizationList set imagePath= \"" +files+ "\"  "+member.getUserloginPrimaryKeyString()+ ".jpg' WHERE org_ID='" +member.getUserloginPrimaryKeyString()+"' ");
			//query=entityManager.createQuery("UPDATE OrganizationList  set imagePath ='/home/ec2-user/GGL/Files/'"+member.getUserloginPrimaryKeyString()+"'.jpg' WHERE org_ID='" +member.getUserloginPrimaryKeyString()+"'");
			query=entityManager.createQuery("update OrganizationList set imagePath='/assets/Files/"+member.getUserloginPrimaryKeyString()+ ".jpg' WHERE org_ID='" +member.getUserloginPrimaryKeyString()+"' ");
			//query=entityManager.createQuery("UPDATE OrganizationList  set imagePath = '/assets/photos/'"member.getUserloginPrimaryKeyString()"'.jpg' WHERE org_ID='" +member.getUserloginPrimaryKeyString()+"'");
		    query.executeUpdate();
			logger.info("Payment Status is updated Successfully");	
			return member;
		}catch(Exception e ) {
		}
		finally {
			query=null;
			
		}
		return member;
	}
	
	//------------------- getMyReservation View -------------------------
	@Transactional(value="transactionManager")
	public Member getMyReservationView(String primaryKey,Member member){
		logger.info("---------- getMyReservationView -----------");
		BookingDetail bookingdetail=null;
		try {
			member = new Member();
			int tempPrimaryKey = Integer.valueOf(primaryKey);
			bookingdetail = entityManager.find(BookingDetail.class, tempPrimaryKey);
			logger.info("Primary Key ------>"+primaryKey);
			member.setCountry(bookingdetail.getCountryName());
			member.setSelectedState(bookingdetail.getStateName());
			member.setCategoryname(bookingdetail.getIndustryName());
			member.setCname(bookingdetail.getCompanyName());			
			member.setBookingStatus(bookingdetail.getBookingStatus());
			member.setInvoiceNumber(bookingdetail.getInvoiceNumber());
			member.setNoofrooms(String.valueOf(bookingdetail.getNoofrooms()));
			member.setNoofchild(String.valueOf(bookingdetail.getNoofchild())); 
			member.setNoofadult(String.valueOf(bookingdetail.getNoofadult()));
			member.setNoofpax(String.valueOf(bookingdetail.getNoofpax()));
			member.setNoofTables(bookingdetail.getNoofTables());
			member.setAirname(bookingdetail.getAirname());			
			member.setCategory(bookingdetail.getCategory());
			member.setCategoryinsurance(bookingdetail.getCategoryinsurance());
			member.setCategoryproduct(bookingdetail.getCategoryproduct());
			member.setCompanyinsurance(bookingdetail.getCompanyinsurance());		
			member.setDeparturename(bookingdetail.getDeparturename());
			member.setFromplace(bookingdetail.getFromplace());
			member.setToplace(bookingdetail.getToplace());
			member.setHospitalname(bookingdetail.getHospitalname());
			member.setListproduct(bookingdetail.getListproduct());
			member.setQuantity(bookingdetail.getQuantity());
			member.setStudy(bookingdetail.getStudy());
			member.setTreatment(bookingdetail.getTreatment());
			member.setTriptype(bookingdetail.getTriptype());
			member.setUniversity(bookingdetail.getUniversity());
			member.setVisitcountry(bookingdetail.getVisitcountry()); 
			member.setYearofstudy(bookingdetail.getYearofstudy());
			member.setEmailID(bookingdetail.getEmailID());
			member.setPhoneNumber(bookingdetail.getContactNumber());
			member.setCityName(bookingdetail.getCityName()); 
			member.setPrice(bookingdetail.getPrice()); 
			member.setAddress(bookingdetail.getAddress()); 
			member.setUserLoginPrimaryKey(bookingdetail.getBooking_ID()); 
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Food and hotels")){
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getBookingDate()));
				member.setBookingtime(bookingdetail.getBookingTime());
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Ticketing"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getDeparture()));
				member.setBookingtime(Custom.getFormatedDate(bookingdetail.getReturndate()));
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Travel and Tour"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getDeparture()));
				member.setBookingtime(Custom.getFormatedDate(bookingdetail.getReturndate()));
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Financial Solution"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getArrivaldate()));
				member.setBookingtime(bookingdetail.getBookingTime());
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Education"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getAppointmentdate()));
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Insurance"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getAppointmentdate()));
				//member.setBookingtime(bookingdetail.getBookingTime());
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Medical Treatment"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getAppointmentdate()));
				member.setBookingtime(bookingdetail.getBookingTime());
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Health Accessories"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getBookingDate()));
				member.setBookingdate(null);
				
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Herbal Product"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getBookingDate()));
				member.setBookingdate(null);
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Umrah"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getBookingDate()));
				member.setBookingdate(null);
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Software and Hardware"))
			{
				member.setFinancialtime(Custom.getFormatedDate(bookingdetail.getBookingDate()));
				member.setBookingdate(null);
			}
			if(bookingdetail.getIndustryName().equalsIgnoreCase("Energy Saving"))
			{
				member.setBookingdate(null);
			}	
			return member;
			
		}catch(Exception e) {
						logger.error("Exception -->"+e.getMessage());
		}
		return member;
	}
	
	//---------- Get PrimaryKey ---------------
	@Transactional(value="transactionManager")	
	@SuppressWarnings("unchecked")
	public Member getprimaryKey(Member member){
		logger.info("------------------ Inside getprimaryKey DAO ---------------");
		Query query = null;
		try{
			query=entityManager.createQuery("from OrganizationList order by org_ID desc limit 1");	
			List<OrganizationList> orglist=(ArrayList<OrganizationList>)query.getResultList();
			logger.info("Org ID ------->"+orglist.get(0).getOrg_ID());
			member.setUserloginPrimaryKeyString(String.valueOf(orglist.get(0).getOrg_ID())); 
		}catch(Exception e){
			logger.info("Exception -->"+e.getMessage());
		}
		finally{
			
		}		
		return member;
	}
	
	public Member getMember1_EmailID(Member member) {
		logger.info("------------------ Inside getMember1_EmailID DAO ---------------");
		UserDetail userdetail = null;
		Query query = null;
		try {		
			logger.debug("Reference 1 MemberId ---->"+member.getRefmemberID());
			/*userdetail = entityManager.find(UserDetail.class, member.getMember1_primaryKey());*/
			query=entityManager.createQuery("from UserDetail where memberID='" +member.getRefmemberID()+"' ");						
			userdetail = (UserDetail)query.getSingleResult();	
			member.setEmailID1(userdetail.getEmail1());
			logger.debug("Reference 1 Email -->"+member.getEmailID1());
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
		}
		finally {
			
		}
		return member;
	}
	
	public Member getMember2_EmailID(Member member) {

		logger.info("------------------ Inside getMember2_EmailID DAO ---------------");
		UserDetail userdetail = null;
		Query query = null;
		try {
			logger.debug("Reference 2 MemberId ---->"+member.getMemberID2());
			/*userdetail = entityManager.find(UserDetail.class, member.getMember2_primaryKey());*/
			query=entityManager.createQuery("from UserDetail where memberID='" +member.getMemberID2()+"' ");						
			userdetail = (UserDetail)query.getSingleResult();	
			member.setEmailID2(userdetail.getEmail1());
			logger.debug("Reference 2 Email -->"+member.getEmailID2());
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
		}
		finally {
			
		}
		return member;
	}
	
	public Member getMember3_EmailID(Member member) {

		logger.info("------------------ Inside getMember3_EmailID DAO ---------------");
		UserDetail userdetail = null;
		Query query = null;
		try {
			logger.debug("Reference 3 MemberId ---->"+member.getMemberID3());
			/*userdetail = entityManager.find(UserDetail.class, member.getMember3_primaryKey());*/
			query=entityManager.createQuery("from UserDetail where memberID='" +member.getMemberID3()+"' ");						
			userdetail = (UserDetail)query.getSingleResult();
			member.setEmailID3(userdetail.getEmail1());
			logger.debug("Reference 3 Email -->"+member.getEmailID3());
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
		}
		finally {
			
		}
		return member;
	}
	
	//------------------- getMemberDetails View -------------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public Member getMemberDetails(int userLoginPrimaryKey,Member member){
		logger.info("---------- getMemberDetails -----------");
		UserLogin userlogin=null;
		try {
			member = new Member();
			userlogin = entityManager.find(UserLogin.class, userLoginPrimaryKey);
			logger.debug("Primary Key ------>"+userLoginPrimaryKey);
			member.setMemberID(userlogin.getUserDetails().get(0).getMemberID());
			member.setRefmemberID(userlogin.getUserDetails().get(0).getMember_Ref_ID());
			member.setUserLoginPrimaryKey(userlogin.getUserDetails().get(0).getUser_Details_ID());
			member.setFirstName(userlogin.getUserDetails().get(0).getFirstname()+" "+userlogin.getUserDetails().get(0).getLastname());
			Query query=entityManager.createQuery("from MemberId where member_Number=?");
			query.setParameter(1, member.getMemberID());
			ArrayList<MemberId> memberResult=(ArrayList<MemberId>)query.getResultList();
			member.setUserloginPrimaryKeyString(String.valueOf(memberResult.get(0).getMember_ID()));
			return member;
			
		}catch(Exception e) {
						logger.error("Exception -->"+e.getMessage());
		}
		return member;
	}
	
	// Remove Agent data
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public Member setMemberRemove(Member member) {
		logger.info("------------- Inside setMemberRemove ----------------------------------");
		logger.debug("Member ID -------------->"+member.getMemberID());
		logger.debug("Ref Member ID ------------->"+member.getRefmemberID());
		logger.debug("User Details ID ------------>"+member.getUserLoginPrimaryKey());
		logger.debug("Member Table ID ------------>"+member.getUserloginPrimaryKeyString());
		Query query = null;
		List<UserDetail> userdetail = null;
		try {
			MemberId memberId = entityManager.find(MemberId.class,Integer.valueOf(member.getUserloginPrimaryKeyString()));
			List<CommOverrDetail> comm = memberId.getCommOverrDetails();
			memberId.setCommOverrDetails(comm);
		    entityManager.remove(memberId);		    
		    UserLogin userlogin = entityManager.find(UserLogin.class,member.getUserLoginPrimaryKey());
			userdetail = userlogin.getUserDetails();
			userlogin.setUserDetails(userdetail);
		    entityManager.remove(userlogin);		    
		    query=entityManager.createQuery("from UserDetail where member_Ref_ID=?");
			query.setParameter(1, member.getMemberID());
			userdetail=(ArrayList<UserDetail>)query.getResultList();	
			logger.debug("[DAO] UserDetail List Size ------------->"+userdetail.size());
			for(int i = 0; i<userdetail.size(); i++) {
				 query=entityManager.createQuery("UPDATE UserDetail set member_Ref_ID='" +member.getRefmemberID()+"' WHERE memberID='" +userdetail.get(i).getMemberID()+"'");
				 query.executeUpdate();				
			}
		    logger.info("Member is removed Successfully....");
		    member.setStatus("success");		
		}catch(Exception e) {
			logger.error("setMemberRemove Exception -->"+e.getMessage());
			member.setStatus("failure");
		}finally {
			
		}
		return member;
	}
	
	//-------- Search Employee List for Country and Code and Name and Type Values --------------
		@Transactional(value="transactionManager")
		@SuppressWarnings("unchecked")
		public ArrayList<Member> searchHotel(ArrayList<Member> searchHotelList,Member member,String customQuery) {
			logger.info("------------Inside searchHotel --------------");
			Query query=null;
			List<OrganizationList> resultList;
			logger.debug("searchHotel-Query ---------->"+customQuery);
			try {
				query=entityManager.createQuery(customQuery);
				resultList=(ArrayList<OrganizationList>)query.getResultList();
				logger.info("Result List ------->"+resultList.size());
				if(resultList.size() > 0) {
					int i=1;
					for(OrganizationList org : resultList) {
						member = new Member();
						member.setsNo(i);
						member.setCategoryname(org.getCategory());
						member.setSelectedCountry(org.getCountryName()); 
						member.setSelectedState(org.getStateName());
						member.setCname(org.getName());
						member.setDescription(org.getDescription()); 
						member.setPhoneNumber(org.getPhoneNumber());
						member.setEmailID(org.getEmailID());
						member.setPrice(org.getPrice()); 
						logger.debug("Description ----------->"+org.getDescription()); 
						member.setUserLoginPrimaryKey(org.getOrg_ID()); 
						member.setUserloginPrimaryKeyString(String.valueOf(org.getOrg_ID())); 
						if(org.getImagePath()==null || org.getImagePath()=="") {
							logger.debug("No Image found");
							member.setHotelImagePath("/assets/Files/no_image.jpg");
						}
						else {
							logger.debug("Image Path -->"+org.getImagePath());
							member.setHotelImagePath(org.getImagePath());  	
						}
						searchHotelList.add(member);
						i++;
					}
				}
				logger.debug("Search HotelList --------->"+searchHotelList.size()); 
			}catch(Exception e){
				logger.error("Exception -->"+e.getMessage());
			}finally{
				
			}
			return searchHotelList;
		}
		
		// Remove booking data
		@Transactional(value="transactionManager")
		public Member setBookingRemove(Member member) {
			logger.info("------------Inside setBookingRemove --------------");
			logger.debug("Invoice Number -------------->"+member.getInvoiceNumber());
			logger.debug("Booking ID ------------>"+member.getUserLoginPrimaryKey());
			BookingDetail bookdetails = null;
			try {
				bookdetails = entityManager.find(BookingDetail.class,member.getUserLoginPrimaryKey());
			    entityManager.remove(bookdetails);			    
			    logger.info("Booking is removed Successfully....");
			    member.setStatus("success");		
			}catch(Exception e) {
				logger.info("setBookingRemove Exception -->"+e.getMessage());
				member.setStatus("failure");
			}finally {
				
			}
			return member;
		}
		
		
		// ---------------- Update Image ------------------------------
		@Transactional(value="transactionManager")
		public Member UpdateBookingImage(Member member)
		{
			logger.info("------------------ Inside UpdateImage DAO ---------------");	
			Query query=null;
			try {	
				query=entityManager.createQuery("update BookingDetail set imagePath='/var/www/html/booking/"+member.getUserloginPrimaryKeyString()+ ".jpg' WHERE booking_ID='" +member.getUserloginPrimaryKeyString()+"' ");
			    query.executeUpdate();
				logger.info("Payment Status is updated Successfully");	
				return member;
			}catch(Exception e ) {
			}
			finally {
				query=null;
				
			}
			return member;
		}
		
		//------ Get Product Name ------
		@SuppressWarnings("unchecked")
		public ArrayList<String> getProductName(ArrayList<String> productNameList, Member member){
			logger.info("------------ Inside getProductName --------------");
			Query query = null;
			try {
				query=entityManager.createNativeQuery("select org.name from organization_list as org where org.country_name=? and org.state_name=? and org.category=?");
				query.setParameter(1, member.getSelectedCountry());
				query.setParameter(2, member.getSelectedState());
				query.setParameter(3, member.getCategoryname());	
				productNameList=(ArrayList<String>)query.getResultList();
				logger.debug("Search ProductList --------->"+productNameList.size()); 
			}catch(Exception e){
				logger.error("ProductName Exception -->"+e.getMessage());
			}finally{
				 
			}
			return productNameList;
		}
			
		
		//---------- Price Based on Product ---------------
		@SuppressWarnings("unchecked")
		@Transactional(value="transactionManager")	
		public Member getProductPrice(Member member){
			logger.info("------------------ Inside getProductPrice DAO ---------------");
			Query query = null;
			List<OrganizationList> orglist;
			try{
				query = entityManager.createQuery("from OrganizationList where name=?");
				query.setParameter(1,member.getCname());
				orglist=(ArrayList<OrganizationList>)query.getResultList();
				logger.info("Org ID ------->"+orglist.get(0).getOrg_ID() +" ------ Price ------"+orglist.get(0).getPrice());
				member.setPrice(orglist.get(0).getPrice());  
			}catch(Exception e){
				logger.info("Product Price Exception -->"+e.getMessage());
			}
			finally{
				
			}		
			return member;
		}
		
		//------- FeedBack Website Mail Register ---------
		@Transactional(value="transactionManager")
		public Member websiteFeedBackRegister(Member member) {
			logger.info("-------- Inside FeedBack Register Mail -------");
			WebsiteFeedBackMail feedback = null;
			try {
				feedback = new WebsiteFeedBackMail();
				feedback.setName(member.getFirstName());
				feedback.setEmailID(member.getEmailID());
				feedback.setSubject(member.getAddress());
				feedback.setMessage(member.getDescription());
				feedback.setFeedBackCreated_date(Custom.getCurrentDate());
				entityManager.persist(feedback);
				member.setStatus("success");
			}catch(Exception e) {
				member.setStatus("failure");
				logger.info("[DAO] WebsiteException ---------->"+e.getMessage());
			}
			return member;
		}
		
		//------ Get Withdraw Detailed List ------
		@SuppressWarnings("unchecked")
		public ArrayList<Member> getWithdrawDetails(ArrayList<Member> withdrawList,String memberNumber){ 
			logger.info("------------ Inside getWithdrawDetails DAO Calling --------------");
			Query query = null;
			Member member = null;
			try {
				query=entityManager.createQuery("from CommOverrDetail where Member_ID=?");
				query.setParameter(1, memberNumber);
				ArrayList<CommOverrDetail> result=(ArrayList<CommOverrDetail>)query.getResultList();
				logger.info("List size --------------->"+result.size());
				for(int i=0;i<result.size();i++){
					member = new Member();
					
					logger.info("Value Type ----->"+result.get(i).getValue_type()); 
					logger.info("commission ----->"+result.get(i).getCommissionAmt()); 
					logger.info("Overridding ----->"+result.get(i).getOverridingAmt()); 
					
					member.setBookingdate(result.get(i).getCreated_date());
					if(result.get(i).getValue_type().equalsIgnoreCase("added") && result.get(i).getOverridingAmt() == 0 && result.get(i).getCommissionAmt()!= 0) {	
						member.setCommition(result.get(i).getCommissionAmt());
					    String commissionString = "You got commission ";
						member.setMember_refer_Number1(commissionString);
					}else if(result.get(i).getValue_type().equalsIgnoreCase("added") && result.get(i).getCommissionAmt() == 0 && result.get(i).getOverridingAmt()!= 0) {
						member.setCommition(result.get(i).getOverridingAmt());
					    String overridingString = "You got funds for over riding " ;
						member.setMember_refer_Number1(overridingString);
					} else if(result.get(i).getValue_type().equalsIgnoreCase("added") && result.get(i).getOverridingAmt()!= 0 && result.get(i).getCommissionAmt()!= 0) {	
						member.setCommition(result.get(i).getCommissionAmt() + result.get(i).getOverridingAmt());
					    String commissionString = "You got funds for over riding and commission ";
						member.setMember_refer_Number1(commissionString);
					}
					if(result.get(i).getValue_type().equalsIgnoreCase("subtract")) {
						member.setCommition(result.get(i).getCommissionAmt() + result.get(i).getOverridingAmt()); 
					    String withdrawString = "You withdraw funds  ";
						member.setMember_refer_Number1(withdrawString);
					}
					logger.info("Total List Strings --------->"+member.getMember_refer_Number1()); 
					withdrawList.add(member);
				}
				
			}catch(Exception e){
				logger.error("withdrawList Exception -->"+e.getMessage());
			}finally{
				 
			}
			return withdrawList;
		}
		
		//---------- Register Member Payment Details ---------------
		@Transactional(value="transactionManager")
		public Member memberPayment(Member member){
			logger.info("------------------ Inside memberPayment DAO ---------------");
			PaymentDetails payment = null;
			try {
				payment = new PaymentDetails();
				payment.setAdminBank_Name(member.getBankName());
				payment.setAdminBankAcct_Name(member.getAdminacctName());
				payment.setAdminBankAcct_Number(member.getAdminacctNumber());
				payment.setTransferBank_Name(member.getBanktransfer());
				payment.setTransferBankAcct_Name(member.getUsername());
				payment.setTransferBankAcct_Number(member.getBankAcctNumber());
				payment.setMember_Number(member.getMemberID()); 
				payment.setAcctCreated_date(Custom.getCurrentDate());
				payment.setTreeName(member.getTreeName());
				payment.setInvoiceNumber(member.getInvoiceNumber()); 
				logger.info("Tree Name ---"+member.getTreeName()); 
				if(member.getTreeName() == null || member.getTreeName().equals("")){
					logger.info("Tree Name Equal to null ---"); 
					payment.setPayment_Path("/home/ec2-user/GGL/PaymentFiles/"+member.getMemberID()+".jpg");
				}else if(member.getTreeName().equalsIgnoreCase("publicTree")){
					logger.info("Tree Name Equal to Public Tree ---"); 
					payment.setPayment_Path("/home/ec2-user/GGL/PublicPayment/"+member.getInvoiceNumber()+".jpg");
				}else if(member.getTreeName().equalsIgnoreCase("privateTree")){
					logger.info("Tree Name Equal to Private Tree ---");
					payment.setPayment_Path("/home/ec2-user/GGL/PrivatePayment/"+member.getInvoiceNumber()+".jpg");
				}else if(member.getTreeName().equalsIgnoreCase("ownTree")){
					logger.info("Tree Name Equal to Own Tree ---");
					payment.setPayment_Path("/home/ec2-user/GGL/OwnPayment/"+member.getInvoiceNumber()+".jpg");
				}else if(member.getTreeName().equalsIgnoreCase("miniTree")){
					logger.info("Tree Name Equal to Mini Tree ---");
					payment.setPayment_Path("/home/ec2-user/GGL/MiniPayment/"+member.getInvoiceNumber()+".jpg");
				}
				logger.info("Payment Path ------>"+payment.getPayment_Path());

				entityManager.persist(payment);		
				member.setStatus("success");
				logger.info("------ Successfully Saved ---");
			}catch(Exception e){
				 member.setStatus("failure");
				logger.info("Exception -->"+e.getMessage());
			}
			finally{
				 
			}
		
			return member;
		}
		
		//---------- Get RandomNumber ---------------
		@Transactional(value="transactionManager")	
		public Member getrandomNumber(Member member){
			logger.info("------------------ Inside getrandomNumber DAO ---------------");
			Query query = null;
			try{
				query=entityManager.createQuery("from RandamNumber");	
				RandamNumber randomNumber=(RandamNumber)query.getSingleResult();	
				logger.info("RandomNumber ------->"+randomNumber.getCurrent_Member_Number());
				member.setMemberID2(String.valueOf(randomNumber.getCurrent_Member_Number())); 
			}catch(Exception e){
				logger.info("getrandomNumber Exception -->"+e.getMessage());
			}
			finally{
				
			}		
			return member;
		}
			
}
