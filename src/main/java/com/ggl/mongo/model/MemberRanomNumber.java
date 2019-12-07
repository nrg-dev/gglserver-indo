package com.ggl.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MemberRanomNumber {

	@Id
	private String memRanNumID;
	
	private String member_code;
	private String franchise_code;
	private String master_franchise_code;
	private int  current_member_number;
	private int current_group_number;
	private int treerandamnumber;
	private String consnumber;	
	public String getMemRanNumID() {
		return memRanNumID;
	}
	public void setMemRanNumID(String memRanNumID) {
		this.memRanNumID = memRanNumID;
	}
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	public int getCurrent_member_number() {
		return current_member_number;
	}
	public void setCurrent_member_number(int current_member_number) {
		this.current_member_number = current_member_number;
	}
	public int getCurrent_group_number() {
		return current_group_number;
	}
	public void setCurrent_group_number(int current_group_number) {
		this.current_group_number = current_group_number;
	}
	public int getTreerandamnumber() {
		return treerandamnumber;
	}
	public void setTreerandamnumber(int treerandamnumber) {
		this.treerandamnumber = treerandamnumber;
	}
	public String getFranchise_code() {
		return franchise_code;
	}
	public void setFranchise_code(String franchise_code) {
		this.franchise_code = franchise_code;
	}
	public String getMaster_franchise_code() {
		return master_franchise_code;
	}
	public void setMaster_franchise_code(String master_franchise_code) {
		this.master_franchise_code = master_franchise_code;
	}
	public String getConsnumber() {
		return consnumber;
	}
	public void setConsnumber(String consnumber) {
		this.consnumber = consnumber;
	}
	
	

	
	
	
}
