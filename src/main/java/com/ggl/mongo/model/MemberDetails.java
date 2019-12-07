package com.ggl.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MemberDetails {

  @Id
  private String      memberid;	
  private String      member_Number  ; 
  private String      member_acct_type;
  private int         group_name      ;
  private int         sequance_number ;
  private int         total_commission;
  private int         total_overriding;
  private int         level_number    ;
  private String      tree_name       ;
  private String      total_amount    ;
  private String      withdraw_status ;
  private String      status        ;  

public String getMemberid() {
	return memberid;
}
public void setMemberid(String memberid) {
	this.memberid = memberid;
}
public String getMember_Number() {
	return member_Number;
}
public void setMember_Number(String member_Number) {
	this.member_Number = member_Number;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

public String getMember_acct_type() {
	return member_acct_type;
}
public void setMember_acct_type(String member_acct_type) {
	this.member_acct_type = member_acct_type;
}
public int getGroup_name() {
	return group_name;
}
public void setGroup_name(int group_name) {
	this.group_name = group_name;
}
public int getSequance_number() {
	return sequance_number;
}
public void setSequance_number(int sequance_number) {
	this.sequance_number = sequance_number;
}
public int getTotal_commission() {
	return total_commission;
}
public void setTotal_commission(int total_commission) {
	this.total_commission = total_commission;
}
public int getTotal_overriding() {
	return total_overriding;
}
public void setTotal_overriding(int total_overriding) {
	this.total_overriding = total_overriding;
}
public int getLevel_number() {
	return level_number;
}
public void setLevel_number(int level_number) {
	this.level_number = level_number;
}
public String getTree_name() {
	return tree_name;
}
public void setTree_name(String tree_name) {
	this.tree_name = tree_name;
}
public String getTotal_amount() {
	return total_amount;
}
public void setTotal_amount(String total_amount) {
	this.total_amount = total_amount;
}
public String getWithdraw_status() {
	return withdraw_status;
}
public void setWithdraw_status(String withdraw_status) {
	this.withdraw_status = withdraw_status;
}

	
	
	
}
