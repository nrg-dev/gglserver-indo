package com.ggl.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the comm_overr_details database table.
 * 
 */
@Entity
@Table(name="comm_overr_details")
@NamedQuery(name="CommOverrDetail.findAll", query="SELECT c FROM CommOverrDetail c")
public class CommOverrDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int com_Overr_ID;

	@Column(name="commission_amt")
	private double commissionAmt;

	
	@Column(name="overriding_amt")
	private double overridingAmt;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date created_date;
	
	@Column(name="value_type")
	private String value_type;
	
	
	@Column(name="status")
	private String status;
	
	
	@Column(name="Member_Number")
	private String Member_Number;
	

	//bi-directional many-to-one association to MemberId
	//@ManyToOne(cascade = CascadeType.ALL)
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="Member_ID")
	private MemberId memberId;
	
	public CommOverrDetail() {
	}

	public int getCom_Overr_ID() {
		return this.com_Overr_ID;
	}

	public void setCom_Overr_ID(int com_Overr_ID) {
		this.com_Overr_ID = com_Overr_ID;
	}
    
	public double getCommissionAmt() {
		return commissionAmt;
	}

	public void setCommissionAmt(double commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	public double getOverridingAmt() {
		return overridingAmt;
	}

	public void setOverridingAmt(double overridingAmt) {
		this.overridingAmt = overridingAmt;
	}

	

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	
	
	public String getValue_type() {
		return value_type;
	}

	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}

	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getMember_Number() {
		return Member_Number;
	}

	public void setMember_Number(String member_Number) {
		Member_Number = member_Number;
	}

	public MemberId getMemberId() {
		return memberId;
		//return this.memberId;
	}

	public void setMemberId(MemberId memberId) {
		this.memberId = memberId;
	}
	
}