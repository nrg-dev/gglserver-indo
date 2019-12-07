package com.ggl.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TempPublicTree {

	@Id
	private String TempublicID;
	private int userID;
	private int numberofUnits;
	private int payAmount;
	private String paymentStatus;
	private String currency;
	private String invoiceCode;

	
	public String getTempublicID() {
		return TempublicID;
	}
	public void setTempublicID(String tempublicID) {
		TempublicID = tempublicID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	
}