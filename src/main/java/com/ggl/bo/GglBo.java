package com.ggl.bo;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ggl.dto.GLGMem;
import com.ggl.dto.Member;
import com.ggl.dto.User;
import com.ggl.model.UserDetail;
import com.ggl.util.GLGException;

public interface GglBo {
	
	public default void test() {
		logger.info("userLogin Default Method Called.....");
	}

	public static final Logger logger = LoggerFactory.getLogger(GglBo.class);

	// ---------------- GGL Number Validate ------------------------------
	public String gglNumberCheck(String gglNumber);
	// ---------------- Update Payment ------------------------------
	public boolean UpdatePayment(String gglNumber);
	
	// ---------------- user login ------------------------------
	public User userLogin(User user);
	
	//----------------- Save reservation ------------------------
	public Member saveReservation(Member member,int temp) throws GLGException;
	//----------------- Create member ---------------------------
	public Member createMember(Member member) throws GLGException;
	//---------------- load the country list --------------------
	public HashMap<String,String> getCountry(HashMap<String,String> statemap);
	//---------------- Member ID validate------------------------
	public boolean getMemberIDValidate(String MemberID);
	//--------------- Get only My member list -------------------
	public ArrayList<GLGMem> getMyMemberList(String memberNumber, ArrayList<GLGMem> myMemList);
	//------------ load All GLG Member details ------------------
	public ArrayList<GLGMem> getAllMemberList(String requestType,ArrayList<GLGMem> myMemList);
	//------------ Admin approve --------------------------------
	public User getApproved(User user,int userLoginPrimaryKey,String requestType);
	//------------ list only my reservation details -------------
	public ArrayList<Member> getMyReservationDetails(ArrayList<Member> member,String primaryKeyStr);
	// -------- list all reservation details
	public ArrayList<Member> getAllReservationDetails(ArrayList<Member> allreslist,Member member);
	//------------ Admin reservation approve --------------------------------
	public User getApprovedForReservation(User user,int userLoginPrimaryKey,String requestType);
	// ---------------- get My Profile ------------------------------
	public Member getMyProfile(Member member);
	public ArrayList<GLGMem> getMyCommandOverInfo(String primaryKeyStr,ArrayList<GLGMem> glgmember);
	// ---------------- forget Password use check ------------------------------
	public User Checkuser(User user,int temp);
	// ---------------- Load Hotel Name ------------------------------
	public ArrayList<String> getName(ArrayList<String> namelist,Member member, int temp);
//---------------- load the country list --------------------
	public ArrayList<String> loadCountryList(ArrayList<String> loadcountrylist);
	//---------------- load the state list --------------------
	public ArrayList<String> loadState(ArrayList<String> loadstatelist,String country);
	//---------------- load the Category list --------------------
	public ArrayList<String> loadCategory(ArrayList<String> loadstatelist,String country,String state);
	// get Hotel list 
	public ArrayList<Member> getCountryInfo(Member member, ArrayList<Member> list, String imagePath);
	//--------update My Profile ------ 
	public Member updateMyProfile(Member member);
	//submit withdrawal amount
	public Member submitWith(Member member);
	public ArrayList<Member> getAllWithdrawList(String requestType,ArrayList<Member> withdrawList);
	public Member getApproveForWithdraw(Member member, int userLoginPrimaryKey,String requestType);
	public Member saveCategory(Member member);
	public ArrayList<Member> getAllCategoryList(ArrayList<Member> categoryList);
	public Member setCategoryUpdate(Member member);
	public String setCategoryRemove(int categoryPK);
	public String setCompanyUpdate(Member member);
	public String setCompanyRemove(int userLoginPrimaryKey);

	// ---------------- Update Payment ------------------------------
	public boolean UpdateImage(String gglNumber);
	public Member getMyReservationView(String userloginPrimaryKeyString,Member member);
	public Member getprimaryKey(Member member);
	// --------------------------------- get Member Email ID ----------------
	public UserDetail getMemberEmailID(int primaryKey);
	//----------------------------Get User Details ----
	public Member getMemberDetails(int userLoginPrimaryKey,Member member);
	public Member setMemberRemove(Member member);
	public ArrayList<Member> searchHotel(ArrayList<Member> searchHotelList, Member member);
	public Member setBookingRemove(Member member);
	public boolean UpdateBookingImage(String uploadPk);
	public ArrayList<String> getProductName(ArrayList<String> productNameList, Member member);
	public Member getProductPrice(Member member);  
	public Member websiteFeedBackRegister(Member member);
	public ArrayList<Member> getWithdrawDetails(ArrayList<Member> withdrawList,String memberNumber);
	//-------- Register Member Payment Details --------
	public Member memberPayment(Member member);
	public Member getrandomNumber(Member member);   
}
