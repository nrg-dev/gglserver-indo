package com.ggl.dao;

import java.util.ArrayList;
import java.util.List;

import com.ggl.dto.GLGMem;
import com.ggl.dto.Member;
import com.ggl.dto.User;
import com.ggl.model.BookingDetail;
import com.ggl.model.CommOverrDetail;
import com.ggl.model.CountryDetail;
import com.ggl.model.IndustryDetail;
import com.ggl.model.MemberId;
import com.ggl.model.UserDetail;
//import com.ggl.model.OrganizationList;
import com.ggl.model.UserLogin;

public interface GglDao {
	// File upload GGL Number validation check 
	public String gglNumberCheck(String gglNumber);
	public Member UpdatePayment(Member member);

	public boolean getMemberIDValidate(String MemberID);
	public List<UserLogin> userLogin(User user,List<UserLogin> result);
	public Member userExistingCheck(String requestType,Member member);
	public Member createMember(Member member);	
	public Member saveReservation(Member member);
	public Member saveOrganization(Member member);
	public ArrayList<CountryDetail> getCountry();
	// get country and state
	public ArrayList<IndustryDetail> getState (String country,ArrayList<IndustryDetail> industrylist);
	public String getMemberCode(String memberCode);
	public int getRandamCode(int newCode,String requestType);
	public ArrayList<MemberId> getGroupData(ArrayList<MemberId> memberID,int groupName);
	public ArrayList<MemberId> getFiltredData(ArrayList<MemberId> memberID,int groupName,int fromNumber,int ToNumber,String treeName);
	// ------------ load only My GLG Member details --------------------
	public ArrayList<GLGMem> getMyMemberList(String memberNumber, ArrayList<GLGMem> myMemList);
	// ------------ load All GLG Member details ------------------------
	public ArrayList<GLGMem> getAllMemberList(String requestType,ArrayList<GLGMem> myMemList);
	public User getApproved(User user,int userLoginPrimaryKey,String requestType);
	public User getApprovedForReservation(User user,int userLoginPrimaryKey,String requestType);
	public UserLogin getMyReservationDetails(String primaryKeyStr);
	//----------------- view all reservation details for admin login ------------
	public ArrayList<BookingDetail> getAllReservationDetails(Member member);
	//------------------ view my profile ----------------------------------------
	public Member getMyProfile(Member member);
	public boolean refer1Update(Member member);
	public ArrayList<GLGMem> getMyCommandOverInfo(String primaryKeyStr,ArrayList<CommOverrDetail> commdetails);
	// ---------------- forget Password use check ------------------------------
	public User Checkuser(User user);
	// ---------------- OTP Validate ------------------------------
	public User OtpCheck(User user);
	// ---------------- OTP Validate ------------------------------
	public User resetPassword(User user);
	// ---------------- Load Hotel Name ------------------------------
	public ArrayList<String> getName(ArrayList<String> namelist,Member member, int temp);
	public ArrayList<String> loadCountry();
	public ArrayList<String> loadStateList(String country);
	public ArrayList<String> loadCategory(String country,String state);
	public ArrayList<Member> getCountryInfo(Member member, ArrayList<Member> list, String imagePath);		
	//---- update my profile -------
	public Member updateMyProfile(Member member);
	//------- submit withdrawal amount ----------------
	public Member submitWith(Member member);
	//------------ Get All withdraw List ----------
	public ArrayList<Member> getAllWithdrawList(String requestType,ArrayList<Member> withdrawList);
	public Member getApproveForWithdraw(Member member, int userLoginPrimaryKey,String requestType);
	public Member saveCategory(Member member);
	public ArrayList<Member> getAllCategoryList(ArrayList<Member> categoryList);
	public Member setCategoryUpdate(Member member);
	public String setCategoryRemove(int categoryPK);
	public String setCompanyUpdate(Member member);
	public String setCompanyRemove(int userLoginPrimaryKey);
	public Member UpdateImage(Member member);
	public Member getMyReservationView(String userloginPrimaryKeyString,Member member);
	public Member getprimaryKey(Member member);
	public UserDetail getMemberEmailID(int primaryKey);
	public Member getMember1_EmailID(Member member);
	public Member getMember2_EmailID(Member member);
	public Member getMember3_EmailID(Member member);
	//----------------------------Get User Details ----
	public Member getMemberDetails(int userLoginPrimaryKey,Member member);
	public Member setMemberRemove(Member member);
	public ArrayList<Member> searchHotel(ArrayList<Member> searchHotelList, Member member,String customQuery);
	public Member setBookingRemove(Member member);
	public Member UpdateBookingImage(Member member);
	public ArrayList<String> getProductName(ArrayList<String> productNameList, Member member);
	public Member getProductPrice(Member member);  
	public Member websiteFeedBackRegister(Member member); 
	public ArrayList<Member> getWithdrawDetails(ArrayList<Member> withdrawList,String memberNumber);   
	//-------- Register Member Payment Details --------
	public Member memberPayment(Member member);
	public Member getrandomNumber(Member member);  
}
