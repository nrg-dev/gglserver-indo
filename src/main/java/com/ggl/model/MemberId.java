package com.ggl.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the member_id database table.
 * 
 */
@Entity
@Table(name="member_id")
@NamedQuery(name="MemberId.findAll", query="SELECT m FROM MemberId m")
public class MemberId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int member_ID;

	@Column(name="group_name")
	private int groupName;

	@Column(name="Member_Acct_Type")
	private String member_Acct_Type;

	@Column(name="Member_Number")
	private String member_Number;

	@Column(name="sequance_number")
	private int sequanceNumber;

	@Column(name="status")
	private String status;

	@Column(name="total_commission")
	private double totalCommission;

	@Column(name="total_overriding")
	private double totalOverriding;

	@Column(name="level_number")
	private int level_number;
	
	@Column(name="tree_name")
	private String tree_name;
	
	@Column(name="total_amount")
	private String totalAmount;

	@Column(name="Withdraw_Status")
	private String Withdraw_Status;
	
	//bi-directional many-to-one association to CommOverrDetail
	@OneToMany(mappedBy="memberId",cascade = CascadeType.ALL)
	private List<CommOverrDetail> commOverrDetails;

	public MemberId() {
	}

	public int getMember_ID() {
		return this.member_ID;
	}

	public void setMember_ID(int member_ID) {
		this.member_ID = member_ID;
	}

	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public int getGroupName() {
		return this.groupName;
	}

	public void setGroupName(int groupName) {
		this.groupName = groupName;
	}

	public String getMember_Acct_Type() {
		return this.member_Acct_Type;
	}

	public void setMember_Acct_Type(String member_Acct_Type) {
		this.member_Acct_Type = member_Acct_Type;
	}

	public String getMember_Number() {
		return this.member_Number;
	}

	public void setMember_Number(String member_Number) {
		this.member_Number = member_Number;
	}

	public int getSequanceNumber() {
		return this.sequanceNumber;
	}

	public void setSequanceNumber(int sequanceNumber) {
		this.sequanceNumber = sequanceNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public double getTotalOverriding() {
		return totalOverriding;
	}

	public void setTotalOverriding(double totalOverriding) {
		this.totalOverriding = totalOverriding;
	}
	
	public String getWithdraw_Status() {
		return Withdraw_Status;
	}

	public void setWithdraw_Status(String withdraw_Status) {
		Withdraw_Status = withdraw_Status;
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

	public List<CommOverrDetail> getCommOverrDetails() {
		return this.commOverrDetails;
	}

	public void setCommOverrDetails(List<CommOverrDetail> commOverrDetails) {
		this.commOverrDetails = commOverrDetails;
	}

	public CommOverrDetail addCommOverrDetail(CommOverrDetail commOverrDetail) {
		getCommOverrDetails().add(commOverrDetail);
		commOverrDetail.setMemberId(this);

		return commOverrDetail;
	}

	public CommOverrDetail removeCommOverrDetail(CommOverrDetail commOverrDetail) {
		getCommOverrDetails().remove(commOverrDetail);
		commOverrDetail.setMemberId(null);

		return commOverrDetail;
	}

}