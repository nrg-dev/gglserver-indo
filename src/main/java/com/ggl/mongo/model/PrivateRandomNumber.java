package com.ggl.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PrivateRandomNumber {

	@Id
	private String userID;
	private int currentqueueNumber;
	private int nextOutqueueNumber;
	private int invocieNumber;
	private String invoiceCode;
	private int randamID;
	
	
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

	
	
	
	public int getRandamID() {
		return randamID;
	}
	public void setRandamID(int randamID) {
		this.randamID = randamID;
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
