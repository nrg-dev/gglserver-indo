package com.ggl.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RandomNumber {

	@Id
	private String userID;
	private int currentqueueNumber;
	private int nextOutqueueNumber;
	private int mininextOutqueueNumber;
	private int invocieNumber;
	private String invoiceCode;
	private int randomID;
	
	private int publicCount;
	private int privateCount;
	private int ownCount;
	private int miniCount;
	private String publicInvCode;
	private String privateInvCode;
	private String ownInvCode;
	private String miniInvCode;

	
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

	
	
	
	
	public int getRandomID() {
		return randomID;
	}
	public void setRandomID(int randomID) {
		this.randomID = randomID;
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
	public int getMininextOutqueueNumber() {
		return mininextOutqueueNumber;
	}
	public void setMininextOutqueueNumber(int mininextOutqueueNumber) {
		this.mininextOutqueueNumber = mininextOutqueueNumber;
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
	public int getPublicCount() {
		return publicCount;
	}
	public void setPublicCount(int publicCount) {
		this.publicCount = publicCount;
	}
	public int getPrivateCount() {
		return privateCount;
	}
	public void setPrivateCount(int privateCount) {
		this.privateCount = privateCount;
	}
	
	public int getOwnCount() {
		return ownCount;
	}
	public void setOwnCount(int ownCount) {
		this.ownCount = ownCount;
	}
	public String getPublicInvCode() {
		return publicInvCode;
	}
	public void setPublicInvCode(String publicInvCode) {
		this.publicInvCode = publicInvCode;
	}
	public String getPrivateInvCode() {
		return privateInvCode;
	}
	public void setPrivateInvCode(String privateInvCode) {
		this.privateInvCode = privateInvCode;
	}
	public String getOwnInvCode() {
		return ownInvCode;
	}
	public void setOwnInvCode(String ownInvCode) {
		this.ownInvCode = ownInvCode;
	}
	public int getMiniCount() {
		return miniCount;
	}
	public void setMiniCount(int miniCount) {
		this.miniCount = miniCount;
	}
	public String getMiniInvCode() {
		return miniInvCode;
	}
	public void setMiniInvCode(String miniInvCode) {
		this.miniInvCode = miniInvCode;
	}

	
	
	
}
