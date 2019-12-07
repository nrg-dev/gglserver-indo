package com.ggl.mongo.dal;


import com.ggl.mongo.model.MemberDetails;

public interface MemberDAL {

	public boolean validateMember(String memberID);
	public int getRandomNumber(int newCode,String requestType);
	public String saveMember(MemberDetails memberDetails);


	

}