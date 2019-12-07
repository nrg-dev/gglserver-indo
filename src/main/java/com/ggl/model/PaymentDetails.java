package com.ggl.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

/**
 * The persistent class for the payment_details database table.
 * 
 */
@Entity
@Table(name="payment_details")
@NamedQuery(name="PaymentDetails.findAll", query="SELECT m FROM PaymentDetails m")
public class PaymentDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int payment_ID;

	@Column(name="adminBank_Name")
	private String adminBank_Name;

	@Column(name="adminBankAcct_Number")
	private String adminBankAcct_Number;

	@Column(name="adminBankAcct_Name")
	private String adminBankAcct_Name;
	
	@Column(name="transferBank_Name")
	private String transferBank_Name;
	
	@Column(name="transferBankAcct_Number")
	private String transferBankAcct_Number;
	
	@Column(name="transferBankAcct_Name")
	private String transferBankAcct_Name;
	
	@Column(name="payment_Path")
	private String payment_Path;
	
	@Column(name="member_Number")
	private String member_Number;
	
	@Column(name="acctCreated_date")
	private Date acctCreated_date;
	
	@Column(name="treeName")
	private String treeName;
	
	@Column(name="invoiceNumber")
	private String invoiceNumber;

	public PaymentDetails() {
	}
	
	public int getPayment_ID() {
		return payment_ID;
	}

	public void setPayment_ID(int payment_ID) {
		this.payment_ID = payment_ID;
	}

	public String getAdminBank_Name() {
		return adminBank_Name;
	}

	public void setAdminBank_Name(String adminBank_Name) {
		this.adminBank_Name = adminBank_Name;
	}

	public String getAdminBankAcct_Number() {
		return adminBankAcct_Number;
	}

	public void setAdminBankAcct_Number(String adminBankAcct_Number) {
		this.adminBankAcct_Number = adminBankAcct_Number;
	}

	public String getAdminBankAcct_Name() {
		return adminBankAcct_Name;
	}

	public void setAdminBankAcct_Name(String adminBankAcct_Name) {
		this.adminBankAcct_Name = adminBankAcct_Name;
	}

	public String getTransferBank_Name() {
		return transferBank_Name;
	}

	public void setTransferBank_Name(String transferBank_Name) {
		this.transferBank_Name = transferBank_Name;
	}

	public String getTransferBankAcct_Number() {
		return transferBankAcct_Number;
	}

	public void setTransferBankAcct_Number(String transferBankAcct_Number) {
		this.transferBankAcct_Number = transferBankAcct_Number;
	}

	public String getTransferBankAcct_Name() {
		return transferBankAcct_Name;
	}

	public void setTransferBankAcct_Name(String transferBankAcct_Name) {
		this.transferBankAcct_Name = transferBankAcct_Name;
	}

	public String getPayment_Path() {
		return payment_Path;
	}

	public void setPayment_Path(String payment_Path) {
		this.payment_Path = payment_Path;
	}

	public String getMember_Number() {
		return member_Number;
	}

	public void setMember_Number(String member_Number) {
		this.member_Number = member_Number;
	}

	public Date getAcctCreated_date() {
		return acctCreated_date;
	}

	public void setAcctCreated_date(Date acctCreated_date) {
		this.acctCreated_date = acctCreated_date;
	}
	
	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

}