package com.ggl.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the website_feedback_mail database table.
 * 
 */
@Entity
@Table(name="website_feedback_mail")
@NamedQuery(name="WebsiteFeedBackMail.findAll", query="SELECT w FROM WebsiteFeedBackMail w")
public class WebsiteFeedBackMail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int feedback_ID;
	
	@Column(name="name")
	private String name;
	
	@Column(name="emailID")
	private String emailID;
	
	@Column(name="subject")
	private String subject;

	@Column(name="message")
	private String message;

	
	@Column(name="feedBackCreated_date")
	private java.sql.Date feedBackCreated_date;
	
	

	public WebsiteFeedBackMail() {
	}


	public int getFeedback_ID() {
		return feedback_ID;
	}

	public void setFeedback_ID(int feedback_ID) {
		this.feedback_ID = feedback_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public java.sql.Date getFeedBackCreated_date() {
		return feedBackCreated_date;
	}


	public void setFeedBackCreated_date(java.sql.Date feedBackCreated_date) {
		this.feedBackCreated_date = feedBackCreated_date;
	}


	
}