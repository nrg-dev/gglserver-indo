package com.ggl.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OwnTree {

	@Id
	private String ownTreeID;
	private int userID;
	private int currentqueueNumber;
	private int nextOutqueueNumber;
	private int invocieNumber;
	private String invoiceCode;
	
	
	
	
	public String getOwnTreeID() {
		return ownTreeID;
	}
	public void setOwnTreeID(String ownTreeID) {
		this.ownTreeID = ownTreeID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getCurrentqueueNumber() {
		return currentqueueNumber;
	}
	public void setCurrentqueueNumber(int currentqueueNumber) {
		this.currentqueueNumber = currentqueueNumber;
	}
	public int getNextOutqueueNumber() {
		return nextOutqueueNumber;
	}
	public void setNextOutqueueNumber(int nextOutqueueNumber) {
		this.nextOutqueueNumber = nextOutqueueNumber;
	}
	public int getInvocieNumber() {
		return invocieNumber;
	}
	public void setInvocieNumber(int invocieNumber) {
		this.invocieNumber = invocieNumber;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	
	
	

	
	
	
}
