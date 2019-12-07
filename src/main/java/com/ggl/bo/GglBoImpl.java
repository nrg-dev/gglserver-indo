package com.ggl.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ggl.dao.GglDao;
//import com.ggl.dao.GglDaoImpl;
import com.ggl.dto.Dropbox;
import com.ggl.dto.GLGMem;
import com.ggl.dto.Member;
import com.ggl.dto.User;
import com.ggl.model.BookingDetail;
import com.ggl.model.CommOverrDetail;
import com.ggl.model.CountryDetail;
import com.ggl.model.IndustryDetail;
//import com.ggl.model.MemberId;
import com.ggl.model.UserDetail;
import com.ggl.model.UserLogin;
import com.ggl.util.Custom;
import com.ggl.util.Email;
import com.ggl.util.GLGException;


@Service("bo")
public class GglBoImpl implements GglBo{
	
	public static final Logger logger = LoggerFactory.getLogger(GglBoImpl.class);

	
	 @Value("${memeber.silver}")
	 private String silver;
	 @Value("${memeber.gold}")
	 private String gold;
	 @Value("${memeber.platinum}")
	 private String platinum;
	 
	@Autowired
	GglDao dao;
	
	/*@FunctionalInterface
	interface Test {
		public int userLogin1(int i, int j);
		default void userLogin2() {
			
		}

	}
	
	Test t = (int i ,int j) -> i+j;	
	*/
	
	//logger.info("Hi");
	
	//user;
	//logger.info("Inside userLogin() BO");
	
	
	// ---------------------- Load Email ID -------------------
	public UserDetail getMemberEmailID(int primaryKey) {
		return dao.getMemberEmailID(primaryKey);

	}
	
	// ---------------- GGL Number Validate ------------------------------
		public String gglNumberCheck(String gglNumber)
		{
			return dao.gglNumberCheck(gglNumber);
			//return "";
		}
		
		// ---------------- Update Payment ------------------------------
		public boolean UpdatePayment(String gglNumber)
		{
			boolean paymentUpdateStatus = false;
			try {
				Member member = new Member();
				member.setMemberNumber(gglNumber);
				member =  dao.UpdatePayment(member);
				paymentUpdateStatus=true;
				Email.paymentConfirmation(member); 
				
				return paymentUpdateStatus;
			} catch(Exception e) {
				paymentUpdateStatus=false;
				return paymentUpdateStatus;
			}
			
			//return paymentUpdateStatus;

		}
	public boolean getMemberIDValidate(String MemberID){
		boolean boreponse = false;
		try {
			boreponse=dao.getMemberIDValidate(MemberID);//.get
			return boreponse;
		}catch(Exception e) {
			e.printStackTrace();
			boreponse=false;
		}finally {
			
		}
		return boreponse;
	}
	
	@Override
	public User userLogin(User user){
		logger.info("Inside userLogin() BO");
		List<UserLogin> result=null;
		try {
			
			//logger.info("Lampda Exression Value ------->"+t.userLogin1(10, 20));
			user.setId(1);
			result = dao.userLogin(user,result);
			if(result.size() > 0) {
				result=null;
				user.setId(2);
				result = dao.userLogin(user,result);
				if(result.size() > 0) {
					logger.info("userLogin BO ------------------->"+result.get(0).getUserRole());
					logger.info("Primary Key ------------------->"+result.get(0).getUser_Login_ID());
					//user.setStatus("success");
					user.setUserRole(result.get(0).getUserRole());
					user.setId(result.get(0).getUser_Login_ID());
					user.setUserloginPrimaryKeyString(String.valueOf(result.get(0).getUser_Login_ID()));
					//user.setMemberNumber(result.get(0).);
					
					if(result.get(0).getStatus().equalsIgnoreCase("Approved")){
						user.setStatus("success");
					}
					if(result.get(0).getStatus().equalsIgnoreCase("Waiting For Approval")){
						user.setStatus("Please wait for Admin approval and It is still under process");
					}
					if(result.get(0).getStatus().equalsIgnoreCase("Rejected")){
						user.setStatus("Your Account was rejected by Admin");
					}

					//user.setUserStatus(result.get(0).getStatus());
					
					//user.setMemberNumber(memberNumber);
				}
				else {
					user.setStatus("Invalid Pass word Please try again");
				}
			}else {
			user.setStatus("Invalid User Name Please try again");
			//logger.info("No data found.");
		}
		
		}catch(Exception e){
			logger.info("BO Exception -->"+e.getMessage());
			user.setStatus("Network Error Please try again");

		}
		finally{
			result=null;
		}
		return user;
	}
	
// ------------------------ Save reservation ------------------------------------
	public Member saveReservation(Member member,int temp) throws GLGException
	{
		if(temp==1) {
			logger.info("Booking Date --------------->"+member.getBookingdate());
			int Code = dao.getRandamCode(0, "bookingInvoiceNumber");//bookingInvoiceNumber
			member.setInvoiceNumber("GGLINV"+Code);
			member = dao.saveReservation(member);
			logger.info("Status ------->"+member.getStatus());
			if(member.getStatus().equalsIgnoreCase("success")){
				member.setBookingCode("Booking Code is available in attached File");
				logger.info("BO Booking COde "+member.getBookingCode());
				Email.bookingAcknowledgement(member); 
				// Call Admin alert mail
				Email.adminbookingalertEmail(member); 
			}else if(member.getStatus().equalsIgnoreCase("failure")){
				logger.info("Member has not Reserve Successfully");
			}
			//String resultDate = Custom.getFormatedDate(member.getBookingdate());
			//member.setBookingdate(bookingdate);(resultDate);
			
			
		}
		
		if(temp==2) {
			dao.saveOrganization(member);
			//File f = new File("E:/joseny/Ggl Technology/2019/March/28/ggl-ui/src/assets/Files/"+member.getUserloginPrimaryKeyString()+ ".jpg");
			File f = new File("/var/www/html/assets/Files/"+member.getUserloginPrimaryKeyString()+ ".jpg");
			if(f.exists() && !f.isDirectory()) { 
				member =  dao.UpdateImage(member);		
			}else{
				
			}
		}
			
		return member;
	}

	
// -------------------Create a Member -------------------------------------------
	 public Member createMember(Member member) throws GLGException{

			logger.info("Inside createMember() method Bo");
			int newTempCode=0; 
			String newCodeFinal=null;
			try {
				int silverInt = Integer.parseInt(silver); 
				int goldInt = Integer.parseInt(gold);
				int platinumInt = Integer.parseInt(platinum);
				
				member = dao.userExistingCheck("UserCheck",member);
				if(member.getStatus().equalsIgnoreCase("0")) {
					logger.info("------------[BO] userexists called -------------"); 
					// -------------- Username is already registred Please try again. -----------
					member.setStatus("userexits");				
				}if(member.getStatus().equalsIgnoreCase("1")){
					logger.info("------------[BO] Success called -------------"); 
					// -------------- Username is successfully created. -----------
					member.setStatus("success");
				}
				if(member.getStatus().equalsIgnoreCase("2")){
					logger.info("------------[BO] Othererror called -------------"); 
					// -------------- Network issue Please try again. -----------
					member.setStatus("otherError");
				}
				
				member = dao.userExistingCheck("MemberIDCheck",member);
				if(member.getStatus().equalsIgnoreCase("Exist")){
					logger.info("------------[BO] MemberID Valid called -------------"); 
					member.setStatus("success");
				}
				if(member.getStatus().equalsIgnoreCase("NotExist")){
					logger.info("------------[BO] MemberID NotValid called -------------"); 
					// -------------- Network issue Please try again. -----------
					member.setStatus("memberIDNotValid");
				}
					
				// ----------- Only for success user name creation ------------
				if(member.getStatus().equalsIgnoreCase("success")) 
				{

					logger.info("Before Calling the Randam Number choose ----------------------");
					newTempCode=dao.getRandamCode(newTempCode,"CurrentGLGCode");
					logger.info("Successfully Called the Randam Number choose ----------------------");	
					logger.info("New Generated Randam Number ---------------->"+newTempCode);
					
					if(member.getActType().equalsIgnoreCase("Silver"))  {
						newCodeFinal = "GLGS"+newTempCode;
						logger.info("Silver New Generated Randam Number ---------------->"+newTempCode);
						member.setTriptype("Member");
						member.setPayAmt(silverInt); 
						member.setAdminFees(5);
						member.setTotalFees(silverInt+5);
					}
					
					if(member.getActType().equalsIgnoreCase("Gold"))  {
						newCodeFinal = "GLGG"+newTempCode;
						logger.info("GOLD New Generated Randam Number ---------------->"+newTempCode);
						member.setTriptype("Franchise");
						member.setPayAmt(goldInt);
						member.setAdminFees(50);
						member.setTotalFees(goldInt+50);
					}
					if(member.getActType().equalsIgnoreCase("Platinum"))  {
						newCodeFinal = "GLGP"+newTempCode;
						logger.info("Platinum New Generated Randam Number ---------------->"+newTempCode);
						member.setTriptype("Master Franchise");
						member.setPayAmt(platinumInt);
						member.setAdminFees(500);
						member.setTotalFees(platinumInt+500);
					}
					
					member.setMemberNumber(newCodeFinal);// New Member ID 
					
					logger.info("Payment Amount ---------------------->"+member.getPayAmt());
					logger.info("Admin Fees ---------------------->"+member.getAdminFees());
					logger.info("Total Amount ---------------------->"+member.getTotalFees());
					
					logger.info("Generated New Code ---------------------->"+newCodeFinal);
					// Calling for Save data
					dao.createMember(member);
					logger.info("------------[BO] Successfully Called createMember -------------"); 
					// Email pushing calling 
					Email.tipsMail(member);					
					Email.saveEmail(member,newCodeFinal);
					Email.adminRegalertEmail(member,newCodeFinal);
					
					logger.info("Successfully called Dao"+member.getStatus());
			
				}
			
					
					
			}catch(Exception e) {
				// -------------- Network issue Please try again. -----------
				member.setStatus("otherError");
				logger.info("BO Exception -->"+e.getMessage());
			}finally {
				
			}
			return member;

	 }
	/*
	 * public Member createMember(Member member) throws GLGException{
	 * logger.info("Inside createMember() method Bo");
	 * logger.info("Sliver--------------->"+silver);
	 * logger.info("Frenschiese--------------->"+gold);
	 * logger.info("Master Frenschiese --------------->"+platinum);
	 * 
	 * int silverInt = Integer.parseInt(silver); int goldInt =
	 * Integer.parseInt(gold); int platinumInt = Integer.parseInt(platinum);
	 * 
	 * //String memberCode = null; String response=null; int newTempCode=0; String
	 * newCodeFinal=null; int NewGroup=0; ArrayList<MemberId> memberInfo=null;//new
	 * ArrayList<MemberId>(); ArrayList<MemberId> memberInnerInfo=null;//new
	 * ArrayList<MemberId>();
	 * 
	 * try { member = dao.userExistingCheck("UserCheck",member);
	 * if(member.getStatus().equalsIgnoreCase("0")) {
	 * logger.info("------------[BO] userexists called -------------"); //
	 * -------------- Username is already registred Please try again. -----------
	 * member.setStatus("userexits"); }if(member.getStatus().equalsIgnoreCase("1")){
	 * logger.info("------------[BO] Success called -------------"); //
	 * -------------- Username is successfully created. -----------
	 * member.setStatus("success"); } if(member.getStatus().equalsIgnoreCase("2")){
	 * logger.info("------------[BO] Othererror called -------------"); //
	 * -------------- Network issue Please try again. -----------
	 * member.setStatus("otherError"); }
	 * 
	 * member = dao.userExistingCheck("MemberIDCheck",member);
	 * if(member.getStatus().equalsIgnoreCase("Exist")){
	 * logger.info("------------[BO] MemberID Valid called -------------"); //
	 * -------------- Network issue Please try again. -----------
	 * member.setStatus("success"); }
	 * if(member.getStatus().equalsIgnoreCase("NotExist")){
	 * logger.info("------------[BO] MemberID NotValid called -------------"); //
	 * -------------- Network issue Please try again. -----------
	 * member.setStatus("memberIDNotValid"); }
	 * 
	 * // ----------- Only for success user name creation ------------
	 * if(member.getStatus().equalsIgnoreCase("success")) {
	 * logger.info("Referance Member ID -->"+member.getRefmemberID()); //1
	 * logger.info("Email ID -->"+member.getEmailID()); // 2
	 * logger.info("Country -->"+member.getCountry()); // 3
	 * logger.info("Phone Number -->"+member.getPhoneNumber()); //4
	 * logger.info("First Name -->"+member.getFirstName()); // 5
	 * logger.info("Last Name -->"+member.getLastName()); // 6
	 * logger.info("User Name -->"+member.getUsername()); // 7
	 * logger.info("Password Name -->"+member.getPassword()); // 8
	 * logger.info("Bank Name -->"+member.getBankName()); // 9
	 * logger.info("Account number -->"+member.getBankAcctNumber()); // 10
	 * logger.info("Member Type -->"+member.getActType()); // 11
	 * 
	 * logger.info("Before calling Dao to get Ref Member Acct Type"); // Calling for
	 * Existing reference Member ID and details for calculation response =
	 * dao.getMemberCode(member.getRefmemberID());
	 * logger.info("Ref Member Acct type -------------->"+response); String[] res =
	 * response.split("-"); // res[0] String acctName=res[0]; // Reference acctName
	 * int gropName=Integer.valueOf(res[1]); // Group Name int
	 * maxNumber=Integer.valueOf(res[2]); // Sequence Number int
	 * PrimaryKey=Integer.valueOf(res[3]); // Primary key String treeName = res[4];
	 * int leveNumber = Integer.valueOf(res[5]); int
	 * sequanceNumber=Integer.valueOf(res[6]); // Sequence Number
	 * 
	 * logger.info("Ref Acct Name ---------------->"+acctName);
	 * logger.info("Ref Group Name --------------->"+gropName);
	 * logger.info("Ref Max Sequance Number ---------->"+maxNumber); // Max Size
	 * logger.info("Ref Primary Key -------------->"+PrimaryKey);
	 * logger.info("Ref Tree nmae -------------->"+treeName);
	 * logger.info("Ref Level Number -------------->"+leveNumber);
	 * logger.info("Ref Sequance Number -------------->"+sequanceNumber);
	 * 
	 * // Alex Start // Senario 1 if(member.getActType().equalsIgnoreCase("silver")
	 * && acctName.equalsIgnoreCase("silver")) { int i=0;
	 * member.setTreeName(treeName); member.setLeveNumber(leveNumber+1); // for new
	 * account pay member.setPayAmt(silverInt); // Integer.parseInt(number);
	 * //member.setAdminFees(0.5); //member.setTotalFees(silverInt+0.5);
	 * member.setAdminFees(5); member.setTotalFees(silverInt+5); // reference
	 * member.setRef_commition1(1); member.setRef_ovrriding1(0);
	 * member.setMember1_primaryKey(PrimaryKey);
	 * 
	 * member = dao.getMember1_EmailID(member);
	 * 
	 * // try to reach platinum a/c memberInfo=new ArrayList<MemberId>();
	 * memberInnerInfo=new ArrayList<MemberId>(); memberInfo =
	 * dao.getGroupData(memberInfo,gropName); int tmpSize = memberInfo.size();
	 * logger.info("Group Size --------->"+tmpSize); for(MemberId m : memberInfo){
	 * if(sequanceNumber==m.getSequanceNumber()){ // 3 memberInnerInfo =
	 * dao.getFiltredData(memberInfo,gropName,sequanceNumber,tmpSize,treeName);
	 * 
	 * for(MemberId me : memberInnerInfo) {
	 * //if(me.getMember_Acct_Type().equalsIgnoreCase("gold")){
	 * if(me.getMember_Acct_Type().equalsIgnoreCase("gold") && i==0){ i=1;
	 * logger.info("Test 1"); member.setRef_ovrriding2(0.5);
	 * System.out.println("Member Overridding2 2nd silver to gold ------------>"
	 * +member.getRef_ovrriding2()); member.setRef_commition2(0);//(5);
	 * member.setMember2_primaryKey(me.getMember_ID());
	 * 
	 * member.setMemberID2(me.getMember_Number()); member =
	 * dao.getMember2_EmailID(member);
	 * 
	 * } if(me.getMember_Acct_Type().equalsIgnoreCase("platinum")){
	 * logger.info("Test 2"); member.setRef_ovrriding3(1); // this overriding
	 * member.setRef_commition3(0);//(5);
	 * member.setMember3_primaryKey(me.getMember_ID());
	 * 
	 * member.setMemberID3(me.getMember_Number()); member =
	 * dao.getMember3_EmailID(member);
	 * 
	 * } }
	 * 
	 * break;} }
	 * 
	 * // Set group name and sequence number member.setSequanceNumber(maxNumber+1);
	 * member.setGroupName(gropName);
	 * 
	 * }
	 * 
	 * if(member.getActType().equalsIgnoreCase("silver") &&
	 * acctName.equalsIgnoreCase("gold")) { member.setTreeName(treeName);
	 * member.setLeveNumber(leveNumber+1); member.setPayAmt(silverInt);
	 * //member.setAdminFees(0.5); //member.setTotalFees(silverInt+0.5);
	 * member.setAdminFees(5); member.setTotalFees(silverInt+5);
	 * member.setRef_commition1(1); // Refereed one in UI this is for gold
	 * member.setRef_ovrriding1(0.5); // Refereed one in UI this is for gold
	 * System.out.println("Member Overridding1 1st for silver to gold ------------>"
	 * +member.getRef_ovrriding1());
	 * 
	 * // reference member calculation logger.
	 * info("--------------- Referance Account type is GOLD -------------------");
	 * memberInfo=new ArrayList<MemberId>();
	 * member.setMember_refer_Number1(member.getRefmemberID()); // Set group name
	 * and sequence number member.setSequanceNumber(maxNumber+1);
	 * member.setGroupName(gropName); member.setMember1_primaryKey(PrimaryKey);
	 * 
	 * member = dao.getMember1_EmailID(member);
	 * 
	 * memberInfo = dao.getGroupData(memberInfo,gropName); //member.setPayAmt(1000);
	 * for(MemberId m : memberInfo){ logger.info("Test 1");
	 * if(m.getMember_Acct_Type().equalsIgnoreCase("platinum")){
	 * logger.info("platinum find"); member.setRef_commition2(0);
	 * member.setRef_ovrriding2(1);
	 * //member.setMember_refer_Number2(m.getMember_Number());
	 * member.setMember2_primaryKey(m.getMember_ID());
	 * 
	 * member.setMemberID2(m.getMember_Number()); member =
	 * dao.getMember2_EmailID(member);
	 * 
	 * break; } }
	 * 
	 * }
	 * 
	 * // Check 1 if(member.getActType().equalsIgnoreCase("silver") &&
	 * acctName.equalsIgnoreCase("platinum")) { logger.
	 * info("--------------- Silver & Platinum Condition Called.-------------------"
	 * ); member.setPayAmt(silverInt); //member.setAdminFees(0.5);
	 * //member.setTotalFees(silverInt+0.5); member.setAdminFees(5);
	 * member.setTotalFees(silverInt+5); member.setLeveNumber(leveNumber+1);
	 * member.setGroupName(gropName);
	 * newTempCode=dao.getRandamCode(newTempCode,"treeNumber");
	 * member.setTreeName("A"+newTempCode);
	 * member.setMember1_primaryKey(PrimaryKey); member.setRef_commition1(1); //
	 * platinum member.setRef_ovrriding1(1); // platinum
	 * member.setSequanceNumber(maxNumber+1);
	 * 
	 * member = dao.getMember1_EmailID(member);
	 * 
	 * }
	 * 
	 * // Senario 2 if(member.getActType().equalsIgnoreCase("gold") &&
	 * acctName.equalsIgnoreCase("silver")) { int i=0; logger.
	 * info("--------------- Gold & Silver Condition Called.-------------------");
	 * member.setTreeName(treeName); member.setLeveNumber(leveNumber+1);
	 * member.setPayAmt(goldInt); member.setAdminFees(50);
	 * member.setTotalFees(goldInt+50); // get only 10% commission and 0% overriding
	 * member.setRef_commition1(100); member.setRef_ovrriding1(0);
	 * member.setMember1_primaryKey(PrimaryKey);
	 * 
	 * member = dao.getMember1_EmailID(member);
	 * 
	 * memberInfo=new ArrayList<MemberId>(); memberInnerInfo=new
	 * ArrayList<MemberId>(); memberInfo = dao.getGroupData(memberInfo,gropName);
	 * int tmpSize = memberInfo.size();
	 * logger.info("Group Size --------->"+tmpSize); for(MemberId m : memberInfo){
	 * if(sequanceNumber==m.getSequanceNumber()){ // 3 memberInnerInfo =
	 * dao.getFiltredData(memberInfo,gropName,sequanceNumber,tmpSize,treeName);
	 * 
	 * for(MemberId me : memberInnerInfo) {
	 * //if(me.getMember_Acct_Type().equalsIgnoreCase("gold")){
	 * if(me.getMember_Acct_Type().equalsIgnoreCase("gold") && i==0){ i=1;
	 * logger.info("Test 1"); member.setRef_ovrriding2(50);
	 * member.setRef_commition2(0);//(5);
	 * member.setMember2_primaryKey(me.getMember_ID());
	 * 
	 * member.setMemberID2(me.getMember_Number()); member =
	 * dao.getMember2_EmailID(member);
	 * 
	 * } if(me.getMember_Acct_Type().equalsIgnoreCase("platinum")){
	 * logger.info("Test 2"); member.setRef_ovrriding3(100); // this overriding
	 * member.setRef_commition3(0);//(5);
	 * member.setMember3_primaryKey(me.getMember_ID());
	 * 
	 * member.setMemberID3(me.getMember_Number()); member =
	 * dao.getMember3_EmailID(member);
	 * 
	 * } } break; } }
	 * 
	 * // Set group name and sequence number member.setSequanceNumber(maxNumber+1);
	 * member.setGroupName(gropName);
	 * 
	 * }
	 * 
	 * if(member.getActType().equalsIgnoreCase("gold") &&
	 * acctName.equalsIgnoreCase("gold")) { logger.
	 * info("--------------- Gold & Gold Condition Called.-------------------");
	 * member.setTreeName(treeName); member.setLeveNumber(leveNumber+1);
	 * member.setPayAmt(goldInt); member.setAdminFees(50);
	 * member.setTotalFees(goldInt+50); member.setRef_commition1(100); // Refereed
	 * one in UI this is for gold member.setRef_ovrriding1(50); // Refereed one in
	 * UI this is for gold // reference member calculation logger.
	 * info("--------------- Referance Account type is GOLD -------------------");
	 * memberInfo=new ArrayList<MemberId>();
	 * member.setMember_refer_Number1(member.getRefmemberID()); // Set group name
	 * and sequence number member.setSequanceNumber(maxNumber+1);
	 * member.setGroupName(gropName); member.setMember1_primaryKey(PrimaryKey);
	 * 
	 * member = dao.getMember1_EmailID(member);
	 * 
	 * memberInfo = dao.getGroupData(memberInfo,gropName); //member.setPayAmt(1000);
	 * for(MemberId m : memberInfo){
	 * if(m.getMember_Acct_Type().equalsIgnoreCase("platinum")){
	 * member.setMember_refer_Number2(m.getMember_Number());
	 * 
	 * member.setRef_commition2(0); member.setRef_ovrriding2(100);
	 * member.setMember2_primaryKey(m.getMember_ID());
	 * 
	 * member.setMemberID2(m.getMember_Number()); member =
	 * dao.getMember2_EmailID(member);
	 * 
	 * } }
	 * 
	 * } // check 2 // New member // Refer member
	 * if(member.getActType().equalsIgnoreCase("gold") &&
	 * acctName.equalsIgnoreCase("platinum")) { logger.
	 * info("--------------- Gold & Platinum Condition Called.-------------------");
	 * member.setPayAmt(goldInt); member.setAdminFees(50);
	 * member.setTotalFees(goldInt+50); member.setLeveNumber(leveNumber+1);
	 * newTempCode=dao.getRandamCode(newTempCode,"treeNumber");
	 * member.setTreeName("A"+newTempCode); member.setGroupName(gropName);
	 * member.setMember1_primaryKey(PrimaryKey); member.setRef_commition1(100);
	 * member.setRef_ovrriding1(100); member.setSequanceNumber(maxNumber+1);
	 * 
	 * member = dao.getMember1_EmailID(member);
	 * 
	 * }
	 * 
	 * // Senario 3 if(member.getActType().equalsIgnoreCase("platinum") &&
	 * acctName.equalsIgnoreCase("silver")) { logger.
	 * info("--------------- Platinum & Silver Condition Called.-------------------"
	 * ); int i=0; member.setRef_commition1(1000); member.setRef_ovrriding1(0);
	 * member.setMember1_primaryKey(PrimaryKey);
	 * 
	 * member = dao.getMember1_EmailID(member);
	 * 
	 * memberInfo=new ArrayList<MemberId>(); memberInnerInfo=new
	 * ArrayList<MemberId>(); memberInfo = dao.getGroupData(memberInfo,gropName);
	 * int tmpSize = memberInfo.size();
	 * logger.info("Group Size --------->"+tmpSize); for(MemberId m : memberInfo){
	 * 
	 * if(sequanceNumber==m.getSequanceNumber()){ // 3 memberInnerInfo =
	 * dao.getFiltredData(memberInfo,gropName,sequanceNumber,tmpSize,treeName);
	 * logger.info("Size ------------->"+memberInnerInfo.size()); for(MemberId me :
	 * memberInnerInfo) { logger.info("Member ID ---------->"+me.getMember_ID());
	 * if(me.getMember_Acct_Type().equalsIgnoreCase("gold") && i==0){ i=1;
	 * logger.info("Test 1"); member.setRef_ovrriding2(500);
	 * member.setRef_commition2(0);//(5);
	 * member.setMember2_primaryKey(me.getMember_ID());
	 * 
	 * member.setMemberID2(me.getMember_Number()); member =
	 * dao.getMember2_EmailID(member);
	 * 
	 * } if(me.getMember_Acct_Type().equalsIgnoreCase("platinum")){
	 * logger.info("Test 2"); member.setRef_ovrriding3(1000); // this overriding
	 * member.setRef_commition3(0);//(5);
	 * member.setMember3_primaryKey(me.getMember_ID());
	 * 
	 * member.setMemberID3(me.getMember_Number()); member =
	 * dao.getMember3_EmailID(member);
	 * 
	 * //break; } } break; } }
	 * 
	 * member.setSequanceNumber(1); member.setGroupName(gropName); NewGroup =
	 * dao.getRandamCode(NewGroup,"CurrentGroupCode");
	 * member.setPayAmt(platinumInt); member.setAdminFees(500);
	 * member.setTotalFees(platinumInt+500); member.setGroupName(NewGroup);
	 * member.setLeveNumber(0); member.setTreeName("A0"); }
	 * 
	 * if(member.getActType().equalsIgnoreCase("platinum") &&
	 * acctName.equalsIgnoreCase("gold")) { logger.
	 * info("--------------- Platinum & Gold Condition Called.-------------------");
	 * member.setTreeName(treeName); member.setLeveNumber(leveNumber+1);
	 * member.setLeveNumber(0); member.setTreeName("A0"); //CurrentGroupCode
	 * NewGroup = dao.getRandamCode(NewGroup,"CurrentGroupCode");
	 * member.setPayAmt(platinumInt); member.setAdminFees(500);
	 * member.setTotalFees(platinumInt+500); member.setRef_commition1(1000); //
	 * Refereed one in UI this is for gold member.setRef_ovrriding1(500); //
	 * Refereed one in UI this is for gold
	 * 
	 * // reference member calculation logger.
	 * info("--------------- Referance Account type is GOLD -------------------");
	 * memberInfo=new ArrayList<MemberId>();
	 * member.setMember_refer_Number1(member.getRefmemberID()); // Set group name
	 * and sequence number member.setSequanceNumber(1);
	 * member.setGroupName(gropName); member.setMember1_primaryKey(PrimaryKey);
	 * 
	 * member = dao.getMember1_EmailID(member);
	 * 
	 * memberInfo = dao.getGroupData(memberInfo,gropName); //member.setPayAmt(1000);
	 * for(MemberId m : memberInfo){
	 * if(m.getMember_Acct_Type().equalsIgnoreCase("platinum")){ //platinum
	 * member.setMember_refer_Number2(m.getMember_Number());
	 * member.setRef_commition2(0); member.setRef_ovrriding2(1000);
	 * member.setMember2_primaryKey(m.getMember_ID());
	 * 
	 * member.setMemberID2(m.getMember_Number()); member =
	 * dao.getMember2_EmailID(member);
	 * 
	 * break; } }
	 * 
	 * member.setGroupName(NewGroup); }
	 * 
	 * // check 3 if(member.getActType().equalsIgnoreCase("platinum") &&
	 * acctName.equalsIgnoreCase("platinum")) { logger.
	 * info("--------------- Platinum & Platinum Condition Called.-------------------"
	 * ); member.setPayAmt(platinumInt); member.setAdminFees(500);
	 * member.setTotalFees(platinumInt+500); member.setLeveNumber(0); NewGroup =
	 * dao.getRandamCode(NewGroup,"CurrentGroupCode");
	 * logger.info("GROUPName-------"+NewGroup); member.setGroupName(NewGroup);
	 * member.setTreeName("A0"); member.setMember1_primaryKey(PrimaryKey);
	 * member.setRef_commition1(1000); member.setRef_ovrriding1(1000);
	 * //CurrentGroupCode member.setSequanceNumber(1);
	 * 
	 * member = dao.getMember1_EmailID(member);
	 * 
	 * logger.
	 * info("--------------- Referance Account type is Platinum -------------------"
	 * ); }
	 * 
	 * 
	 * // Alex end
	 * 
	 * 
	 * logger.info("Before Calling the Randam Number choose ----------------------"
	 * ); newTempCode=dao.getRandamCode(newTempCode,"CurrentGLGCode"); logger.
	 * info("Successfully Called the Randam Number choose ----------------------");
	 * logger.info("New Generated Randam Number ---------------->"+newTempCode);
	 * 
	 * if(member.getActType().equalsIgnoreCase("Silver")) { newCodeFinal =
	 * "GLGS"+newTempCode;
	 * logger.info("Silver New Generated Randam Number ---------------->"
	 * +newTempCode); }
	 * 
	 * if(member.getActType().equalsIgnoreCase("Gold")) { newCodeFinal =
	 * "GLGG"+newTempCode;
	 * logger.info("GOLD New Generated Randam Number ---------------->"+newTempCode)
	 * ; } if(member.getActType().equalsIgnoreCase("Platinum")) { newCodeFinal =
	 * "GLGP"+newTempCode; //member.setPayAmt(10000);
	 * 
	 * logger.info("Platinum New Generated Randam Number ---------------->"
	 * +newTempCode); }
	 * 
	 * member.setMemberNumber(newCodeFinal);// New Member ID
	 * 
	 * logger.info("Generated New Code ---------------------->"+newCodeFinal); //
	 * Calling for Save data dao.createMember(member); dao.refer1Update(member);
	 * logger.
	 * info("------------[BO] Successfully Called createMember and UpdateMember -------------"
	 * ); // Email pushing calling Email.tipsMail(member);
	 * 
	 * if(member.getActType().equalsIgnoreCase("silver")){
	 * member.setTriptype("Member"); }else
	 * if(member.getActType().equalsIgnoreCase("gold")){
	 * member.setTriptype("Franchise"); }else
	 * if(member.getActType().equalsIgnoreCase("platinum")){
	 * member.setTriptype("Master Franchise"); }
	 * 
	 * Email.saveEmail(member,newCodeFinal);
	 * Email.adminRegalertEmail(member,newCodeFinal); if(member.getEmailID1() !=
	 * null){ Email.saveEmailReferMember1(member,newCodeFinal); }
	 * if(member.getEmailID2() != null){
	 * Email.saveEmailReferMember2(member,newCodeFinal); } if(member.getEmailID3()
	 * != null){ Email.saveEmailReferMember3(member,newCodeFinal); }
	 * logger.info("Successfully called Dao"+member.getStatus());
	 * 
	 * }
	 * 
	 * 
	 * 
	 * }catch(Exception e) { // -------------- Network issue Please try again.
	 * ----------- member.setStatus("otherError");
	 * logger.info("BO Exception -->"+e.getMessage()); }finally {
	 * 
	 * } return member; }
	 */

	// ----------------- get Country list -----------------
	public HashMap<String,String> getCountry(HashMap<String,String> statemap){
		ArrayList<CountryDetail> Clist=null;
		try  {
		Clist = dao.getCountry();
		for(CountryDetail country:Clist){
			//list.add(country.getCountryName());
			statemap.put(country.getCountryName(), country.getState_name());
		}
	}catch(Exception e) {
		logger.info("BO - Exception -->"+e.getMessage());
	}
		return statemap;
	}
	
	
	// ----------------- get All state and category ----------------
	
	public Map<String,Dropbox> getState(String country,HashMap<String,Dropbox> map){
		
		ArrayList<IndustryDetail> industrydetail=null;
		Dropbox box =null;
		try {
			box = new Dropbox();
			industrydetail= dao.getState(country,industrydetail);
			for(IndustryDetail industry:industrydetail){
				box.setCategory(industry.getIndustryName());
				box.setHname(industry.getCompanyName());			
			}
			//map.put(industry.getStateName(), box);
		}catch(Exception e) {
			logger.info("BO - Exception -->"+e.getMessage());
		}
		return map;
	}

	// -------------------------- Load Only MY GLG member list ---------------------
	public ArrayList<GLGMem> getMyMemberList(String memberNumber, ArrayList<GLGMem> myMemList){
		return dao.getMyMemberList(memberNumber,myMemList);
	}
	// -------------------------- Load ALL GLG member list ---------------------
		public ArrayList<GLGMem> getAllMemberList(String requestType,ArrayList<GLGMem> myMemList){
		return dao.getAllMemberList(requestType,myMemList);
	}
		
	// ----------------- Approval for member register ---------------
	public User getApproved(User user,int userLoginPrimaryKey,String requestType){/*
		user=  dao.getApproved(user,userLoginPrimaryKey,requestType);
		return user;
	*/
		
	

		user=  dao.getApproved(user,userLoginPrimaryKey,requestType);
		/*if(requestType.equalsIgnoreCase("Approve")) {
			// send the certificate
			// Create Certifiacte
			boolean result = generateCertifiacte(user);
			if(result== true) {
				// Calling the Email method for sending email
				Email.sendCertificate(user);
			}
			if(result==false) {
				// do not send the certificate to customer mail ID
				logger.info("Technical issue while creating the certificate -");
			}
			
		}*/
		
		if(requestType.equalsIgnoreCase("Approve")) {
			// send the certificate
			if(user.getStatus().equalsIgnoreCase("success")){
				Email.PaymentApproveMail(user);
			}
			// Create Certifiacte
			boolean result = generateCertifiacte(user);
			if(result== true) {
				// Calling the Email method for sending email
				Email.sendCertificate(user);
			}
			if(result==false) {
				// do not send the certificate to customer mail ID
				logger.info("Technical issue while creating the certificate -");
			}
			
			
		}else if(requestType.equalsIgnoreCase("Reject")) {
			if(user.getStatus().equalsIgnoreCase("rejectSuccess")){
				Email.PaymentRejectMail(user);
			}
		}
		
		//if()

		return user;
	
	
	}
	
	
	// create Certificate
	
		private boolean generateCertifiacte(User user) {
			boolean result=false;
			try 
			{

	        	logger.info("Main 0");
			    String url = "http://localhost:8091/user-portal/images/silver.jpg";
			    String goldurl = "http://localhost:8091/user-portal/images/gold.jpg";
			    String platinumurl = "http://localhost:8091/user-portal/images/platinum.jpg";
			    logger.info("Main 1");
		        String text = user.getUsername();
		        logger.info("Main 2");        
		      
				if(user.getAccoutType().equalsIgnoreCase("gold")) {		        	
					url=goldurl;
				}
				if(user.getAccoutType().equalsIgnoreCase("platinum")) {
					url=platinumurl;
				}
	
		        byte[] b = mergeImageAndText(url, text, new Point(250,200),user.getMemberID());
		        
		        
		        logger.info("Main 3");
		        FileOutputStream fos = new FileOutputStream("output.png");
		        logger.info("Main 4");
		        fos.write(b);
		        logger.info("Main 5");
		        fos.close();
		        logger.info("Main 6");
	        
				result=true;
				return result;
			}catch(Exception e) {
				e.printStackTrace();
				result=false;
				return result;
				
			}
		}
		
		
		 public static byte[] mergeImageAndText(String imageFilePath,String text, Point textPosition,String memberID) throws IOException {
		    	ByteArrayOutputStream baos =null;
		    	try {
		    	logger.info("File Path--->"+imageFilePath);
		    	BufferedImage im = ImageIO.read(new URL(imageFilePath));
		    	logger.info("2");
		        Graphics2D g2 = im.createGraphics();
		        g2.setColor(Color.BLUE);
		        g2.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 

		        logger.info("3");
		        g2.drawString(text, textPosition.x, textPosition.y);
		        logger.info("4");
		        baos = new ByteArrayOutputStream();
		        logger.info("5");
		       // ImageIO.write(im, "png", baos);D:\code\ggl-server
		        ImageIO.write(im, "jpg", new File("D:\\code\\ggl-server\\src\\main\\webapp\\images\\certificate\\"+memberID+ ".jpg"));
		        logger.info("6");
		        return baos.toByteArray();
		        }catch(Exception e) {
		        	logger.info("Error");
		        	//return baos.toByteArray();
		        	e.printStackTrace();
		        	//return baos.toByteArray();
		        }
		        return baos.toByteArray();
		    }
		 // ----------------- Approval for reservation ---------------
		public User getApprovedForReservation(User user,int userLoginPrimaryKey,String requestType){
			logger.info("------------[BO] Inside getApprovedForReservation Method --------------");
			user=  dao.getApprovedForReservation(user,userLoginPrimaryKey,requestType);
			logger.info("-------- Status --------------"+user.getStatus());
			if(user.getStatus().equalsIgnoreCase("success")){ 
				logger.info("------------[DAO] Inside getApprovedForReservation Method --------------");
				Email.ReservationApproveMail(user);
			}else if(user.getStatus().equalsIgnoreCase("rejectSuccess")){
				Email.ReservationRejectMail(user);
			}
			    
			return user;
		}
				
		// ------------------------ list only my reservation view -------------------------
		public ArrayList<Member> getMyReservationDetails(ArrayList<Member> member,String primaryKeyStr){
			//ArrayList<BookingDetail> result=null;
			Member m=null;
			UserLogin userlogin=null;
			try {
				//result = new ArrayList<BookingDetail>();
				userlogin=dao.getMyReservationDetails(primaryKeyStr);
				int i=1;
				for(BookingDetail book : userlogin.getBookingdetail()){
					logger.info("Booking Hotel Name ------------->"+book.getCompanyName());					
					m = new Member();
					m.setsNo(i);
					m.setCname(book.getCompanyName());
					m.setCountry(book.getCountryName());
					m.setSelectedState(book.getStateName());
					m.setCategoryname(book.getIndustryName());
					m.setInvoiceNumber(book.getInvoiceNumber());
					m.setNoofadult(String.valueOf(book.getNoofadult()));
					m.setNoofchild(String.valueOf(book.getNoofchild()));
					m.setNoofrooms(String.valueOf(book.getNoofrooms()));
					m.setStatus(book.getBookingStatus());
					m.setUserloginPrimaryKeyString(String.valueOf(book.getBooking_ID()));
					if(book.getIndustryName().equalsIgnoreCase("Food and hotels"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getBookingDate()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Ticketing"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getDeparture()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Travel and Tour"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getDeparture()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Financial Solution"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getArrivaldate()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Education"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getAppointmentdate()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Insurance"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getAppointmentdate()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Medical Treatment"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getAppointmentdate()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Health Accessories"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getBookingDate()));				
					}
					if(book.getIndustryName().equalsIgnoreCase("Herbal Product"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getBookingDate()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Umrah"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getBookingDate()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Software and Hardware"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getBookingDate()));
					}
					if(book.getIndustryName().equalsIgnoreCase("Energy Saving"))
					{
						m.setBookingtime(Custom.getFormatedDate(book.getBookingDate()));
					}					
					//book.getBookingDate(); pending
					//book.getBookingTime(); pending
					i++;
					member.add(m);
					
					//booking = new BookingDetail();
					//result.add(booking);
				}
				
				
			
			}catch(Exception e) {
				logger.info("BO - Exception -->"+e.getMessage());
			}finally {
				
			}
		return member;	
		}

		public ArrayList<Member> getAllReservationDetails(ArrayList<Member> allreslist,Member member){
			ArrayList<BookingDetail> bookinglist=null;
			try {
				bookinglist= dao.getAllReservationDetails(member);
				int i=1;
				for(BookingDetail b: bookinglist){
					member = new Member();
					member.setStatus(b.getBookingStatus());
					member.setInvoiceNumber(b.getInvoiceNumber());
					member.setBookingCode(b.getBookingCode()); 
					if(b.getIndustryName().equalsIgnoreCase("Food and hotels"))
					{
						member.setNoofadult(String.valueOf(b.getNoofadult()));
						member.setNoofchild(String.valueOf(b.getNoofchild()));
						member.setNoofpax("No Pax");
						member.setCname(b.getCompanyName()); 
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Ticketing"))
					{
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax(String.valueOf(b.getNoofpax()));
						member.setCname(b.getAirname());
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Travel and Tour"))
					{
						member.setNoofadult(String.valueOf(b.getNoofadult()));
						member.setNoofchild(String.valueOf(b.getNoofchild()));
						member.setNoofpax("No Pax");
						member.setCname(b.getDeparturename()); 
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Financial Solution"))
					{ 
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax(String.valueOf(b.getNoofpax()));
						member.setCname("BPNI");
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Education"))
					{
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax("No Pax");
						member.setCname(b.getUniversity());
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Insurance"))
					{
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax(String.valueOf(b.getNoofpax()));
						member.setCname(b.getCategoryinsurance());
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Medical Treatment"))
					{
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax(String.valueOf(b.getNoofpax()));
						member.setCname(b.getHospitalname());
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Health Accessories"))
					{
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax("No Pax");
						member.setCname(b.getCategoryproduct()); 
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));	
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Herbal Product"))
					{
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax("No Pax");
						member.setCname(b.getCategoryproduct()); 
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Umrah"))
					{
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax(String.valueOf(b.getNoofpax()));
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Software and Hardware"))
					{
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax("No Pax");
						member.setCname(b.getCompanyName());
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}
					if(b.getIndustryName().equalsIgnoreCase("Energy Saving"))
					{
						member.setNoofadult("No Adult");
						member.setNoofchild("No Child");
						member.setNoofpax("No Pax");
						member.setCname(b.getListproduct()); 
						member.setBookingtime(Custom.getFormatedDate(b.getBookingDate()));
						member.setBookingTime(b.getBookingTime()); 
					}	
					member.setsNo(i);
					member.setUserLoginPrimaryKey(b.getBooking_ID());// Primary KEy
					member.setCategoryname(b.getIndustryName());
					i++;
					allreslist.add(member);
				}
			}catch(Exception e){
				logger.info("BO - Exception -->"+e.getMessage());
			}finally{
				
			}
			return allreslist;
		}
	
		public Member getMyProfile(Member member){
			return dao.getMyProfile(member);
		}
		
		
		public ArrayList<GLGMem> getMyCommandOverInfo(String primaryKeyStr,ArrayList<GLGMem> glgmember){
			ArrayList<CommOverrDetail> result=null;
			glgmember = dao.getMyCommandOverInfo(primaryKeyStr,result);
			return glgmember;
		}
		
		public ArrayList<Member> getCountryInfo(Member member, ArrayList<Member> list,String ImagePath){
			list = dao.getCountryInfo(member,list,ImagePath);
			return list;
		}  
		
		// ---------------- forget Password use check ------------------------------
		public User Checkuser(User user,int temp){
			if(temp ==1 ){
				user = dao.Checkuser(user);
				logger.info("[Checkuser 1] User status ------>"+user.getStatus());
				if(user.getStatus().equalsIgnoreCase("success")){
					Email.optMailsend(user);
				}
				else 
				{
					
				}
			}
			
			if(temp == 2) {
				user = dao.OtpCheck(user);			
				logger.info("[Checkuser 2] User status ------>"+user.getStatus());
				//userName = user.getUsername();
			}
			
			if(temp == 3) {
				user = dao.resetPassword(user);	 		
				logger.info("[Checkuser 3] User status ------>"+user.getStatus());
			}
			
			return user;
		}

		// ----------------- Load Country list -----------------
		public ArrayList<String> loadCountryList(ArrayList<String> loadcountrylist){
			//ArrayList<String> Clist=null;
			try  {
				loadcountrylist = dao.loadCountry();
				Set<String> hs = new HashSet<>(); 
				hs.addAll(loadcountrylist);
				loadcountrylist.clear();
				loadcountrylist.addAll(hs);			
			return loadcountrylist;
		}catch(Exception e) {
			logger.info("BO - Exception -->"+e.getMessage());
		}
			return loadcountrylist;
		}
		
		// ----------------- Load state list -----------------
		public ArrayList<String> loadState(ArrayList<String> loadstatelist,String country){
			//ArrayList<String> loadStateList=null;
			try  {
				loadstatelist = dao.loadStateList(country); 
				Set<String> hs = new HashSet<>();
				hs.addAll(loadstatelist);
				loadstatelist.clear();
				loadstatelist.addAll(hs);
				return loadstatelist;
			}catch(Exception e) {
				logger.info("BO - Exception -->"+e.getMessage());
			}
			return loadstatelist;
		}
		
		// Load Category list
		public ArrayList<String> loadCategory(ArrayList<String> loadCategorylist,String country,String state){
			//ArrayList<String> loadStateList=null;
			try  {
				loadCategorylist = dao.loadCategory(country,state); 
				Set<String> hs = new HashSet<>();
				hs.addAll(loadCategorylist);
				loadCategorylist.clear();
				loadCategorylist.addAll(hs);
				return loadCategorylist;
			}catch(Exception e) {
				logger.info("BO - Exception -->"+e.getMessage());
			}
			return loadCategorylist;
		}
		// ---------------- Load Hotel Name ------------------------------
		public ArrayList<String> getName(ArrayList<String> namelist,Member member, int temp){
			//ArrayList<String> Clist=null;
			try  {
				namelist = dao.getName(namelist,member,temp);
				//Set<String> hs = new HashSet<>();
				//hs.addAll(loadcountrylist);
				//loadcountrylist.clear();
				//loadcountrylist.addAll(hs);			
			return namelist;
		}catch(Exception e) {
			logger.info("BO - Exception -->"+e.getMessage());
		}
			return namelist;
		}
		//--------- My profile update BO ---------
		public Member updateMyProfile(Member member){
			logger.info("Before Calling DAO");
			return dao.updateMyProfile(member);
		}	
				

		// ---------- submit withdraw amount ---------------
		public Member submitWith(Member member){
			logger.info("---[BO] Inside submitWith Method ---");
			/*if(member.getWithdrawamt() % 2 == 0) {
				member.setOverriding(member.getWithdrawamt()/2);
				member.setCommition(member.getWithdrawamt()/2);
			}
			else {
				int tmp = member.getWithdrawamt()-1;
				//member.setOverriding(temp /2);
				member.setCommition(member.getWithdrawamt()/2);  
			}*/

			member = dao.submitWith(member);
			if(member.getStatus().equalsIgnoreCase("success")){
				Email.adminwithdrawalertEmail(member); 
			}else{
				logger.info(" -------- Requested Withdraw is failure ------- ");
			}
			
			return member;
		}
		
		//============== BO get All withdraw list method ===========
		public ArrayList<Member> getAllWithdrawList(String requestType,ArrayList<Member> withdrawList){
			withdrawList = dao.getAllWithdrawList(requestType,withdrawList);
			return withdrawList;
		}
		
		//------------------ BO Approve Withdraw Request ---------------
		public Member getApproveForWithdraw(Member member, int userLoginPrimaryKey,String requestType){
			member = dao.getApproveForWithdraw(member, userLoginPrimaryKey, requestType);
			if(member.getStatus().equalsIgnoreCase("success")){
				Email.WithdrawApproveMail(member);
			}else if(member.getStatus().equalsIgnoreCase("rejectSuccess")){
				Email.WithdrawRejectMail(member);
			}
			return member;
		}
		
		//------------- Save Category --------------
		public Member saveCategory(Member member){
			int newTempCode=0;
			String newCodeFinal=null;
			try{
				newTempCode=dao.getRandamCode(newTempCode,"CurrentCategoryCode");
				newCodeFinal = "GLGC"+newTempCode;
				member.setCategoryCode(newCodeFinal); 
				logger.info("Successfully Called DAO");	
				member = dao.saveCategory(member);
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				
			}
			return member;
		}
		
		//------- Category Details --------
		public ArrayList<Member> getAllCategoryList(ArrayList<Member> categoryList){
			categoryList = dao.getAllCategoryList(categoryList);
			return categoryList;
		}
	
		//--------- Update Category -------
		public Member setCategoryUpdate(Member member){
			member = dao.setCategoryUpdate(member);
			return member;
		}
		
		//--------- Remove Category -------
		public String setCategoryRemove(int categoryPK){
			String Response = dao.setCategoryRemove(categoryPK);
			return Response;
		}
		
		//-- set Company Update ----
		public String setCompanyUpdate(Member member){
			String response = dao.setCompanyUpdate(member);
			return response;
		}
		
		//--------- Set Remove Company ----
		public String setCompanyRemove(int userLoginPrimaryKey){
			String response = dao.setCompanyRemove(userLoginPrimaryKey);
			return response;
		}
		
		//--------- Update Image ----------
		public boolean UpdateImage(String uploadPk)
		{
			boolean imageUpdateStatus = false;
			try {
				Member member = new Member();
				member.setUserloginPrimaryKeyString(uploadPk);
				member =  dao.UpdateImage(member);
				imageUpdateStatus=true;					
				return imageUpdateStatus;
			} catch(Exception e) {
				imageUpdateStatus=false;
				return imageUpdateStatus;
			}
		}
		
		//------- getMyReservation View ------
		public Member getMyReservationView(String userloginPrimaryKeyString,Member member) {
			member = dao.getMyReservationView(userloginPrimaryKeyString, member);
			return member;
		}
		
		public Member getprimaryKey(Member member){
			member = dao.getprimaryKey(member);
			return member;
		}
			
		//------ Get User Details -------
		public Member getMemberDetails(int userLoginPrimaryKey,Member member) {
			member = dao.getMemberDetails(userLoginPrimaryKey, member);
			return member;
		}
		
		//-------- Remove Member ------
		public Member setMemberRemove(Member member){
			member = dao.setMemberRemove(member);
			return member;
		}
		
		//--------- Search Hotel Details-----------
		@Override
		public ArrayList<Member> searchHotel(ArrayList<Member> searchHotelList, Member member){
			
			if(member.getSelectedCountry().equalsIgnoreCase("All")) {
				member.setSelectedCountry(null);
			}
			if(member.getCname().equalsIgnoreCase("All")) {
				member.setCname(null);
			}
			if(member.getCategoryname().equalsIgnoreCase("All")) {
				member.setCategoryname(null);
			}
			if(member.getSelectedState().equalsIgnoreCase("All")) {
				member.setSelectedState(null);
			}
			
			String query = getHotelQuery(member);
			logger.info("BO Custom Query ----------->"+query);
			return dao.searchHotel(searchHotelList,member,query);
		}
		
		public static String getHotelQuery(Member member) {
			logger.info("Hotel Name --->"+member.getCname());
			logger.info("member Country --->"+member.getSelectedCountry());
			logger.info("member State --->"+member.getSelectedState());
			logger.info("Category Name --->"+member.getCategoryname());
			String query="from OrganizationList where ";		
			
			// 1 
			if(member.getCname() == null && member.getSelectedCountry() == null && 
				member.getCategoryname() == null && member.getSelectedState() == null){
					query="from OrganizationList";
			}
			
			// 2
			if(member.getCname() != null && !member.getCname().isEmpty()){
				query = query + "name='"+member.getCname()+"'";
			}		
			
			// 3 
			if(member.getSelectedCountry() != null && !member.getSelectedCountry().isEmpty()){
				// 3.1
				if(member.getCname() != null && !member.getCname().isEmpty()){
					query = query + " and countryName='"+member.getSelectedCountry()+"'";
				}			
				else {
					query = query + " countryName='"+member.getSelectedCountry()+"'";

				}
			}
			
			// 4
			if(member.getCategoryname() != null && !member.getCategoryname().isEmpty()){		
				// 4.1
				if(member.getSelectedCountry() != null && !member.getSelectedCountry().isEmpty()){
					query = query + " and category='"+member.getCategoryname()+"'";
				}
				else {
					// 4.2
					if(member.getCname() != null && !member.getCname().isEmpty()){
						query = query + " and category='"+member.getCategoryname()+"'";
					}
					else {
						query = query + " category='"+member.getCategoryname()+"'";
					}
				}
			}
			
			// 5
			if(member.getSelectedState() != null && !member.getSelectedState().isEmpty()){
				// 5.1
				if(member.getCategoryname() != null && !member.getCategoryname().isEmpty()){
					query = query + " and stateName='"+member.getSelectedState()+"'";
				}
				else {
					// 5.2
					if(member.getCname() != null && !member.getCname().isEmpty()){
						query = query + " and stateName='"+member.getSelectedState()+"'";
					}
					else {
						// 5.3
						if(member.getSelectedCountry() != null && !member.getSelectedCountry().isEmpty()){
							query = query + " and stateName='"+member.getSelectedState()+"'";
						}
						else {
							query = query + " stateName='"+member.getSelectedState()+"'";
						}
					}
				}
			}
			
			return query;
		}
		
		public Member setBookingRemove(Member member){
			member = dao.setBookingRemove(member);
			return member;
		}
		
		//--------- Update Image ----------
		public boolean UpdateBookingImage(String uploadPk)
		{
			boolean imageUpdateStatus = false;
			try {
				Member member = new Member();
				member.setUserloginPrimaryKeyString(uploadPk);
				member =  dao.UpdateBookingImage(member);
				imageUpdateStatus=true;					
				return imageUpdateStatus;
			} catch(Exception e) {
				imageUpdateStatus=false;
				return imageUpdateStatus;
			}
		}
		
		//-------- Get Product Name List -----
		public ArrayList<String> getProductName(ArrayList<String> productNameList, Member member){
			return dao.getProductName(productNameList,member);
		}

		//------ Get Price Based on Product ------
		public Member getProductPrice(Member member) {
			member = dao.getProductPrice(member);
			return member;
		}
		
		//------- Get Feedback Website Register ---------
		public Member websiteFeedBackRegister(Member member) {
			try {
				member = dao.websiteFeedBackRegister(member);
				if(member.getStatus().equalsIgnoreCase("success")) {
					Email.websiteFeedBackMail(member); 
				}else if(member.getStatus().equalsIgnoreCase("failure")) {
					logger.info("------ Failure status in Website Feedback --------"); 
				}
			}catch(Exception e) {
				logger.info("Exception ---->"+e.getMessage());
			}
			
			return member;
		}
		
		//-------- Get Withdraw Detailed List -----
		public ArrayList<Member> getWithdrawDetails(ArrayList<Member> withdrawList,String memberNumber){
			return dao.getWithdrawDetails(withdrawList,memberNumber);
		}
		
		//-------- Register Member Payment Details --------
		public Member memberPayment(Member member){
			return dao.memberPayment(member);
		}
		
		//----- Get Member Random Number ------
		public Member getrandomNumber(Member member){
			member = dao.getrandomNumber(member);
			return member;
		}
}
