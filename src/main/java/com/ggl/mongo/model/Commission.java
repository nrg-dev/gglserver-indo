package com.ggl.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Commission {

	@Id
	private String commID;
	private int commission_amt;
	private int created_date;
	private int member_ID;
	private String member_Number;
	private String new_member_number;
	private String new_member_type;
	private String status;
	
	public String getCommID() {
		return commID;
	}
	public void setCommID(String commID) {
		this.commID = commID;
	}
	public int getCommission_amt() {
		return commission_amt;
	}
	public void setCommission_amt(int commission_amt) {
		this.commission_amt = commission_amt;
	}
	public int getCreated_date() {
		return created_date;
	}
	public void setCreated_date(int created_date) {
		this.created_date = created_date;
	}
	public int getMember_ID() {
		return member_ID;
	}
	public void setMember_ID(int member_ID) {
		this.member_ID = member_ID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMember_Number() {
		return member_Number;
	}
	public void setMember_Number(String member_Number) {
		this.member_Number = member_Number;
	}
	public String getNew_member_number() {
		return new_member_number;
	}
	public void setNew_member_number(String new_member_number) {
		this.new_member_number = new_member_number;
	}
	public String getNew_member_type() {
		return new_member_type;
	}
	public void setNew_member_type(String new_member_type) {
		this.new_member_type = new_member_type;
	}
	
	
	
	

	
	
	
}
