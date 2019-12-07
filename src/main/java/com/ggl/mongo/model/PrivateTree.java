package com.ggl.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PrivateTree {

	@Id
	private String privateID;
	private int userID;
	private int queueNumber;
	private String userQueueStatus;
	private int numberofUnits;
	private int payAmount;
	private String paymentStatus;
	private String userstatus;
	private String invoiceNumber;
	private String currency;
	private String adminpaystatus;
	private String referanceGSPNumber;
	
	
	
	
	
	public String getPrivateID() {
		return privateID;
	}
	public void setPrivateID(String privateID) {
		this.privateID = privateID;
	}
	/*public int getPrivateID() {
		return privateID;
	}
	public void setPrivateID(int privateID) {
		this.privateID = privateID;
	}*/
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getQueueNumber() {
		return queueNumber;
	}
	public void setQueueNumber(int queueNumber) {
		this.queueNumber = queueNumber;
	}
	public String getUserQueueStatus() {
		return userQueueStatus;
	}
	public void setUserQueueStatus(String userQueueStatus) {
		this.userQueueStatus = userQueueStatus;
	}
	public int getNumberofUnits() {
		return numberofUnits;
	}
	public void setNumberofUnits(int numberofUnits) {
		this.numberofUnits = numberofUnits;
	}
	public int getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAdminpaystatus() {
		return adminpaystatus;
	}
	public void setAdminpaystatus(String adminpaystatus) {
		this.adminpaystatus = adminpaystatus;
	}
	public String getReferanceGSPNumber() {
		return referanceGSPNumber;
	}
	public void setReferanceGSPNumber(String referanceGSPNumber) {
		this.referanceGSPNumber = referanceGSPNumber;
	}

	
	
	
}
