package com.ggl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ggl.dto.Member;
import com.ggl.dto.User;

public class Email {
	
	public static final Logger logger = LoggerFactory.getLogger(Email.class);
	
//	public static String adminMailID="magin.8808@gmail.com";
	public static String adminMailID="globalgains@gmail.com";

	
	// Send certificate
	
	public static void sendCertificate(User user){
		logger.info("----------------- Inside payment Confirmation -------------------");
		//String certificate ="<center> <img  src=\"C:\\temp\\certificate\\"+user.getMemberID()+".jpg\"> "+
				String certificate ="<center> <img src=\"http://35.162.40.190/images/certificate/"+user.getMemberID()+".jpg\"> "+

				"<center>\r\n <br/><br/>"; 
			try {		
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(user.getEmail_ID(),"GLG Certificate",certificate);
			logger.info("Successfully  Email Called Service------------");			
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
			}
		
		
	
	}
//Payment confirmation email
public static void paymentConfirmation(Member member){
	 
	logger.info("----------------- Inside payment Confirmation -------------------"+member.getEmailID());
	String paymentConfirmation ="<center> <img src=\"http://35.162.40.190/images/headerEmail.png\"> "+
			"<center>\r\n <br/><br/>"+ 
			"<h1 align=\"center\" style=\"text-align:center\"><u>GGL PAYMENT CONFIRMATION</u><o:p></o:p></h1>"+
			"<br/><br/>"+
			"<h1><span style=\"font-size:12.0pt;line-height:115%\">Thanks for Payment You have successfully completed your payment Please check your email for approval <o:p></o:p></span></h1>"+
			"<p class=\"MsoNormal\"><o:p>&nbsp;</o:p></p> <table class=\"MsoTableGrid\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;"+
			"mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt\"></table><p class=\"MsoNormal\"><o:p>&nbsp;</o:p></p> <p class=\"MsoNormal\">Thanks and regards,<o:p></o:p></p><p class=\"MsoNormal\">GGL Management <o:p></o:p></p>"+
			"<p class=\"MsoNormal\">Please feel free to touch with us at <a href=\"http:info@gglway.com/\">info@gglway.com</a><o:p></o:p></p> " +
			"<p class=\"MsoNormal\">Please visit our website<span style=\"mso-spacerun:yes\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><a href=\"http://gglway.com/\">http://gglway.com/</a><o:p></o:p></p>"+
			"<h1 align=\"center\" style=\"text-align:center\"><u></u><o:p></o:p></h1>\r\n" + 
			"<h1><span style=\"font-size:12.0pt;line-height:115%\"><o:p></o:p></span></h1>\r\n" + 
			"<p class=\"MsoNormal\"><o:p>&nbsp;</o:p></p>";
	
	
	try {
	
		logger.info("Calling Email Service -------------");
		PushEmail.sendMail(member.getEmailID(),"GLG Payment Confirmation Letter",paymentConfirmation);
		logger.info("Successfully  Email Called Service------------");	
		
		logger.info("Your Payment Confirmation Letter  Saved Successfully.");
		}catch(Exception e) {
			logger.error("Exception -->"+e.getMessage());
		}
	
	

}



//Reservation acknowledgement Email

public static void bookingAcknowledgement(Member member){ //C:\\code\\ggl-server\\src\\main\\webapp\\images\\
	logger.info("----------------- Inside Booking Acknowledgement -------------------");
	logger.info("member.getBookingCode()-------->"+member.getBookingCode());
	/*if(member.getBookingtime() != null || !member.getBookingtime().equals("")){
		logger.info("----------------- Inside Booking Time Not Equal to Null -------------------"+member.getBookingtime());
		member.setBookingTime(member.getBookingtime());
	}
	if(member.getFinancialtime() != null || !member.getFinancialtime().equals("")){
		logger.info("----------------- Inside Financial Time Not Equal to Null -------------------"+member.getFinancialtime());
		member.setBookingTime(member.getFinancialtime());
	}
	if(member.getMedicaltime() != null || !member.getMedicaltime().equals("")){
		logger.info("----------------- Inside Medical Time Not Equal to Null -------------------"+member.getMedicaltime());
		member.setBookingTime(member.getMedicaltime());
	}*/
	
	String booking_ack ="<h1 align=\"center\" style=\"text-align:center\"><u>GGL BOOKING ACKNOWLAGEMENT EMAIL</u><o:p></o:p></h1>\r\n" + 
		"<h1><span style=\"font-size:12.0pt;line-height:115%\">Thanks for booking with GGL Web site and your Booking details are in below <o:p></o:p></span></h1>\r\n" + 
		"<p class=\"MsoNormal\"><o:p>&nbsp;</o:p></p> <table class=\"MsoTableGrid\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;\r\n" + 
		" mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt\"> <tbody><tr style=\"mso-yfti-irow:0;mso-yfti-firstrow:yes\">\r\n" + 
		"  <td width=\"638\" colspan=\"2\" valign=\"top\" style=\"width:6.65in;border:solid windowtext 1.0pt;  mso-border-alt:solid windowtext .5pt;background:#EEECE1;mso-background-themecolor:\r\n" + 
		"  background2;padding:0in 5.4pt 0in 5.4pt\">  <h2 align=\"center\" style=\"text-align:center;line-height:normal;mso-outline-level:\r\n" + 
		"  2\">Global Gains Limited Booking details<o:p></o:p></h2>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\"><o:p>&nbsp;</o:p></p>  </td>  </tr> <tr style=\"mso-yfti-irow:2\">  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border:solid windowtext 1.0pt;\r\n" +
		"  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\r\n" + 
		"  padding:0in 5.4pt 0in 5.4pt\">  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;text-align:\r\n" + 
		"  justify;line-height:normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;text-align:\r\n" + 
		"  justify;line-height:normal\">Booking ID<o:p></o:p></p>  </td>  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border-top:none;border-left:\r\n" +
		"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\r\n" +
		"  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt\">  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" +
		"  normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" +
		"  normal\"><o:p>&nbsp;</o:p></p>"+member.getInvoiceNumber()+" </td>  </tr> <tr style=\"mso-yfti-irow:2\">  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border:solid windowtext 1.0pt;\r\n" +
		"  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\r\n" + 
		"  padding:0in 5.4pt 0in 5.4pt\">  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;text-align:\r\n" + 
		"  justify;line-height:normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;text-align:\r\n" + 
		"  justify;line-height:normal\">Booking Code<o:p></o:p></p>  </td>  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border-top:none;border-left:\r\n" + 
		"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\r\n" + 
		"  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt\">  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\"><o:p>&nbsp;</o:p></p>"+member.getBookingCode()+" </td>  </tr> <tr style=\"mso-yfti-irow:2\">  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border:solid windowtext 1.0pt;\r\n" + 
		"  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;  padding:0in 5.4pt 0in 5.4pt\">\r\n" + 
		"  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:  normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\">Booking Date<o:p></o:p></p>  </td>  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border-top:none;border-left:\r\n" + 
		"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\r\n" + 
		"  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt\">  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\"><o:p>&nbsp;</o:p></p>  "+Custom.getCurrentDate()+" </td> </tr> <tr style=\"mso-yfti-irow:3;mso-yfti-lastrow:yes\">  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border:solid windowtext 1.0pt;\r\n" + 
		"  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;  padding:0in 5.4pt 0in 5.4pt\">\r\n" + 
		"  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:  normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\">Booking status<o:p></o:p></p>   </td>  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border-top:none;border-left:  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
		"  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt\">\r\n" + 
		"  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:  normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\"><o:p>&nbsp;</o:p></p> "+member.getBookingStatus()+" </td> </tr><tr style=\"mso-yfti-irow:2\">  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border:solid windowtext 1.0pt;\r\n" + 
		"  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;  padding:0in 5.4pt 0in 5.4pt\">\r\n" + 
		"  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:  normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\">Booking Time<o:p></o:p></p>  </td>  <td width=\"319\" valign=\"top\" style=\"width:239.4pt;border-top:none;border-left:\r\n" + 
		"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\r\n" + 
		"  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt\">  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\"><o:p>&nbsp;</o:p></p>  <p class=\"MsoNormal\" style=\"margin-bottom:0in;margin-bottom:.0001pt;line-height:\r\n" + 
		"  normal\"><o:p>&nbsp;</o:p></p>  "+member.getMedicaltime()+" </td>  </tr></tbody></table><p class=\"MsoNormal\"><o:p>&nbsp;</o:p></p> <p class=\"MsoNormal\">Thanks and regards,<o:p></o:p></p><p class=\"MsoNormal\">GGL Management <o:p></o:p></p>\r\n" + 
		"<p class=\"MsoNormal\">Please feel free to touch with us at <a href=\"http:info@gglway.com/\">info@gglway.com</a><o:p></o:p></p>\r\n" + 
		"<p class=\"MsoNormal\">Please visit our website<span style=\"mso-spacerun:yes\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><a href=\"http://gglway.com/\">http://gglway.com/</a><o:p></o:p></p>";


try {
	/*con=pushEmail();
	preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
	preparedStatement.setString(1, member.getEmailID());
	preparedStatement.setString(2, "GLG Booking Acknowledgement"); 
	preparedStatement.setString(3, booking_ack);// Message
	preparedStatement.setString(4, "GGL");
	preparedStatement.executeUpdate();	*/
	
	logger.info("Calling Email Service -------------");
	PushEmail.sendMail(member.getEmailID(),"GLG Booking Acknowledgement",booking_ack);
	logger.info("Successfully  Email Called Service------------");	
	
	
	logger.info("Booking Successfully Saved data.");
	}catch(Exception e) {
		logger.info("Exception -->"+e.getMessage());
	}


}

public static void optMailsend(User user) {	
	String otpEmailBody ="<html> <head> <style> </style> </head>"
	+"<body lang=EN-US style='tab-interval:.5in'> <div class=Section1> <p class=MsoNormal align=center style='text-align:center'><b><u><span"
	+"style='font-size:26.0pt;line-height:115%;color:black'>GLG OTP Notification<p></p></span></u></b></p>"
	+"<p> Your One-Time-Password (OTP) is "+user.getOtp()+" . Please enter this password online to complete the pass word rest with your GGL Account. Validity 10 min.</p><p> For more information on your accounts, please login to GGL Personal Internet Account by visiting gglway.com or the GGL Gains Mobile App from your Smartphone.</p> <p> ----------------------------------------------------------------------------------------------------------------------------------------------------"
	+"</p>"
	
	+"<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>"
	+"<p>For and on behalf of<p></p></span></b></p>" 
	+"<p>Global Gains Limited</span></b></p>"
	+"</div> </body> </html>";
	try {
		/*con=pushEmail();
		preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
		preparedStatement.setString(1, user.getEmail_ID());
		preparedStatement.setString(2, "GGL OPT Request"); 
		preparedStatement.setString(3, otpEmailBody);// Message
		preparedStatement.setString(4, "GGL");
		//preparedStatement.setDate(5, );
		preparedStatement.executeUpdate();	*/
		
		logger.info("Calling Email Service -------------");
		PushEmail.sendMail(user.getEmail_ID(),"GGL OPT Request",otpEmailBody);
		logger.info("Successfully  Email Called Service------------");	
		
	logger.info("Successfully Saved data.");
	}catch(Exception e) {
		logger.info("Exception -->"+e.getMessage());
	}
	
}

public static void tipsMail(Member member){
	/*blueprintnusantaraindo@gmail.com*/
	logger.info("----------------- Inside tipsMail-------------------");
	String email_tips ="<html> <head> <style> </style> </head>\r\n" + 
		"<body lang=EN-US style='tab-interval:.5in'> <div class=Section1> <p class=MsoNormal align=center style='text-align:center'><b><u><span\r\n" + 
		"style='font-size:26.0pt;line-height:115%;color:black'>GLG MEMBER TIPS<p></p></span></u></b></p>\r\n" + 
		"<div> <p> Dear Sir/Madam, </p>\r\n" + 
		"<p> A huge thank you and welcome aboard to our Global Save program </p>\r\n" + 
		"<p> Here are some tips to get you started</p> <div>\r\n" + 
		"<p> 1) You will receive an Official invoice to your registered email with your user name and password. </p>\r\n" + 
		"<p> 2) All payments should be made into our company DBS Singapore Bank account. </p>\r\n" + 
		"<p> 3) Upload banking slip into our system using your Login ID.</p>\r\n" + 
		"<p> 4) You will receive official receipt and certificate of share purchased.</p>\r\n" + 
		"<p> 5) Remember to use your ID when introducing new partner into the program. </p>\r\n" + 
		"<p> 6) You will receive an email for every new member joined under your ID. </p>\r\n" + 
		"<p> 7) All reservations and rewards withdrawal only can be done trough the system.</p>\r\n" + 
		"</div> <p> Got a question?</p>\r\n" + 
		"<p> We’ve got you covered. Check out our FAQ or contact our customer service support at info@gglway.com </p>\r\n" + 
		"<p> your globalgains team </p>\r\n" + 
		"<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>\r\n" + 
		"<p>For and on behalf of<p></p></span></b></p> \r\n" + 
		"<p>Global Gains Limited</span></b></p> </div> </body> </html>\r\n" + 
		"";
	
	
	try {
	/*con=pushEmail();
	preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
	preparedStatement.setString(1, member.getEmailID());
	preparedStatement.setString(2, "GLG MEMBER TIPS"); 
	preparedStatement.setString(3, email_tips);// Message
	preparedStatement.setString(4, "GGL");
	preparedStatement.executeUpdate();	*/
		logger.info("Calling Email Service -------------");
		PushEmail.sendMail(member.getEmailID(),"GLG MEMBER TIPS",email_tips);
		logger.info("Successfully  Email Called Service------------");	
		
	logger.info("tipsMail Successfully Saved data.");
	}catch(Exception e) {
		logger.info("Exception -->"+e.getMessage());
	}
	
	
}
	
// For Closed GGL Member
public static void EightComeOneClosedEmail(Member member){
		logger.info("----------------- Inside EightComeOneClosedEmail -------------------");
		logger.info("Unit ID ------->"+member.getInvoiceNumber()); 
		logger.info("Email ID ------->"+member.getEmailID()); 
		String email_closed ="<html><head><style> .panel { border: 1px solid #00a65a;border-radius:0 !important;transition: box-shadow 0.5s; } </style> "
			+ "<style> table, th , td  { width: 40%;border-collapse: collapse;padding: 5px;margin-left:30%; margin-right:30%; } table thead tr{ background-color: #3c8dbc;}</style> "
	 		+ "<style> table tbody tr:nth-child(odd) { background-color: #dbdee0; } table tbody tr:nth-child(even) { background-color: #b3acac; }tbody {text-align: center;}</style></head>"
	 		+ "<body><div class=\"panel panel-default text-center\"> <div style=\"color: #fff !important;background-color: #3c8dbc !important;padding: 10px;font-size: 20px;border-bottom: 1px solid transparent;text-align: center;\"> "
			+ "<label>Unit Closed Info</label> </div><div style=\"background-color: #d2d6de !important;padding-left: 35px;\"> <br/>  " 
	 		+ "<label>Congratulation you just finished the Unit from GGL SEED FUND</label> <br/><br/>"
	 		+ "<label style=\"font-size: 16px;font-weight: bold;padding-left: 20px;\"> Unit ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp; " + member.getInvoiceNumber() +"</label> <br/> "
	 		+ "<label style=\"font-size: 16px;font-weight: bold;padding-left: 20px;\"> Unit Status &nbsp;&nbsp;&nbsp; :  &nbsp;&nbsp;  CLOSED </label> <br/><br/>"
	 		+ "<label>The disbursement of fund will take 5 working days </label> <br/><br/>"
	 		
	 		+ "<div style=\"text-align: center;\"> Rewards Total </div><br/>"
	 		+ "<table align=\"center\" style=\"border: 1px solid grey\"><thead><tr><th style=\"background-color: #3c8dbc\">Payment Details</th><th style=\"background-color: #3c8dbc\">Fees</th></tr></thead>"
	 		+ "<tbody><tr><td>Unit Rewards</td><td>800SGD</td></tr><tr><td>Deduction</td><td>100SGD</td></tr><tr style=\"font-weight:bold;\"><td>Total Amount</td><td>700SGD</td></tr></tbody></table><br/>"
			+ "<div style=\"text-align: center;margin-left: -80px;\"> *Note : Rate SGD to Rupiah = IDR 10,000/SGD </div><div style=\"text-align: center;\"> Deduction = Re-registration Tree Unit </div><br/><br/>"
	 		+ "<label>we got you covered.check out our FAQ or contact our customer service support info@gglway.com</label><br/>"
			+ "<label>Thank you for join with us,have nice a day. </label><br/><br/>"
	 		+ "<p><b>For and on behalf of</b><p>"
			+ "<p><b>Global Gains Limited</b></p>"
	 		+ "<br/>"
			+ "<img src=\"http://35.162.40.190/assets/images/gglLogo1.png\" >"
	 		+ " </div></div></body></html>";
			/*
			 * String email_closed ="<html> <head> <style> </style> </head>\r\n" +
			 * "<body lang=EN-US style='tab-interval:.5in'> <div class=Section1> <p class=MsoNormal align=center style='text-align:center'><b><u>\r\n"
			 * +
			 * "<span style='font-size:26.0pt;line-height:115%;color:black'>Global Gains Unit Closed<p></p></span></u></b></p>\r\n"
			 * + "<div> <p> Dear Sir/Madam, </p>\r\n" +
			 * "<p> Your Unit is Closed Successfully Now.</p>\r\n" +
			 * "<p> Here are some process to get collect your Money</p> <div>\r\n" +
			 * "<p> 1) Please login to GGL System to verify the Units Closed details. </p>\r\n"
			 * + "<p> 2) Contact the GGL Team via Phone Number or Email ID. </p>\r\n" +
			 * "<p> Thank You for Choosing GGL Investment.</p>\r\n" +
			 * "</div> <p> Got a question?</p>\r\n" +
			 * "<p> We’ve got you covered. Check out our FAQ or contact our customer service support at info@gglway.com </p>\r\n"
			 * + "<p> your globalgains team </p>\r\n" +
			 * "<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>\r\n"
			 * + "<p>For and on behalf of<p></p></span></b></p> \r\n" +
			 * "<p>Global Gains Limited</span></b></p> </div> </body> </html>\r\n" + "";
			 */
			
			
			try {
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(member.getEmailID(),"GGL MEMBER Unit Closed",email_closed);
				logger.info("Successfully  Email Called Service------------");	
				
			}catch(Exception e) {
				logger.info("EightComeOneClosedEmail Exception -->"+e.getMessage());
			}
		}
	
	//-------- Admin Unit Tree Closed Info ----------
	public static void adminunitclosedalertEmail(Member member){
		logger.info("Inside Admin Alert Public Unit Closed Email Method()----------------------------");
		logger.info("Unit ID --------->"+member.getInvoiceNumber());
		String emailRegalertMessage ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
			+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
			+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL MEMBER UNIT CLOSED ALERT</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
			+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
			+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear GGL Admin,</span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
			+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"You have alert for Member unit closed and closed details in below </span></p>"	
		
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Unit ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp; "+member.getInvoiceNumber()+" </span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> Unit Status  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :  &nbsp;&nbsp; <span style='mso-spacerun:yes'> CLOSED </span></span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> Unit Rewards  &nbsp;&nbsp;&nbsp; :  &nbsp;&nbsp; <span style='mso-spacerun:yes'> 800 SGD </span></span></p>"
	
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> Deduction  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :  &nbsp;&nbsp; <span style='mso-spacerun:yes'> 100 SGD </span></span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Total Amount &nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp; <span style='mso-spacerun:yes'> 700 SGD </span></span><b style='mso-bidi-font-weight:normal'></b></p>"
			
			+"<br/><br/>  "
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
			+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
			+"mso-fareast-language:ZH-CN'></span></b></p>"
			
			+"</div> </body> </html>";
	
		try {	
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(adminMailID,"GGL MEMBER Unit Closed ALERT",emailRegalertMessage);
			logger.info("Successfully  Email Called Service------------");
			
		
		}catch(Exception e){
			logger.error("Admin Unit Close Exception --------->"+e.getMessage());
		}
	
	}
	
	public static void saveEmail(Member member,String newCodeFinal){
		logger.info("-------- Inside Save Mail Calling --------"); 
		logger.info("Sender Email -->"+member.getEmailID());
		logger.info("Pay amount -->"+member.getPayAmt());
		logger.info("Member type -->"+member.getActType());
		logger.info("Member user name -->"+member.getUsername());
		logger.info("Member password -->"+member.getPassword());
		
		String emailMessage ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
		+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
		+"style='mso-bidi-font-weight:normal'><u><span style='font-size:26.0pt'>Deposit Advice Notice</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
		+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
		+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Please be"
		+"informed that the following bank account in Singapore is for the purpose of collection/remitting all monies on our behalf. Please Telegraphic transfer your"
		+"deposits into the following Bank Account (charge is applicable):<o:p></o:p></span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
		+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		+"<table style=\"width:100%:border:2px;\">   <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th> "
		+"<th>Bank Account Number</th> 	    <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr>   <tr>"
		+"<td>DBS Bank</td>     <td>Global Gains Limited</td>     <td>003-932398-0</td> 	    <td>DBSSGSG</td>     <td>0346756</td>     <td>RS - 24455</td> <td> 12 Marina Boulevard,Marina Bay Financial Centre,Tower 3, Singapore 018982"
		+"</td> </tr></table>"
		
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><span style='font-size:12.0pt'>"
		+"or Bank Account in thru our partner </span></p>"
	
		+"<table style=\"width:100%:border:2px;\">   <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th> "
		+"<th>Bank Account Number</th> 	    <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr>   <tr>"
		+"<td style='text-align: center;'>CIMB NIAGA</td>  <td style='text-align: center;'>Blueprint Nusatara Indonesia</td>     <td style='text-align: center;'>800077326600</td> 	    <td style='text-align: center;'>BNIAIDJA</td>     <td style='text-align: center;'>84711</td>"
		+"<td style='text-align: center;'>60875 - 60877 NAGA HO IA</td>  <td style='text-align: center;'> GrahaNiaga / Niaga Tower Jl. Jend. SudirmanKav. 58, Jakarta 12190</td> </tr></table>"
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+newCodeFinal+"</span></b></p>"
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Member "
		+"fees Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+member.getPayAmt()+" SGD </span></b></p>"
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Admin "
		+"fees &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>   "+member.getAdminFees()+" SGD </span>"
		+"</span></b></p>"
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Total"
		+" Amount in SGD &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getTotalFees()+" SGD</span></b><b"
		+"style='mso-bidi-font-weight:normal'></b></p>"
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Member Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getTriptype()+"</span></b>"
		+"</span></b></p>"
		
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Login user name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getUsername()+"</span></b>"
		+"</span></b></p>"
		
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Login password &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getPassword()+"</span></b>"
		+"</span></b></p>"
		
		
		
		
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
		+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>After the "
		+"deposits were made, kindly e-mail the Bank deposit slip to us at ( </span></b><a"
		+"href=\"mailto:finance@gglway.com\"><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt'>finance@gglway.com</span></b></a><b"
		+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>). The "
		+"original deposit slips are to be kept by customer. Should you have any queries,"
		+"please feel free to contact us through e-mail( </span></b><a"
		+"href=\"mailto:admin@gglway.com\"><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt'>admin@gglway.com</span></b></a><b"
		+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'> ).<o:p></o:p></span></b></p>"
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>The management shall hold full "
		+"responsibilities when client deposited to the above said account.<o:p></o:p></span></b></p>"
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>For and on behalf of<o:p></o:p></span></b></p>"
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span></b><a"
		+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
		+"mso-fareast-language:ZH-CN'><o:p></o:p></span></b></p> </div> </body> </html>";
		
		try {
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
		
			preparedStatement.setString(1, member.getEmailID());
			preparedStatement.setString(2, "Registration Successfully on GLG Membership"); 
			preparedStatement.setString(3, emailMessage);// Message
			preparedStatement.setString(4, "GGL");
			//preparedStatement.setDate(5, );
			preparedStatement.executeUpdate();	*/
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(member.getEmailID(),"Registration Successfully on GLG Membership",emailMessage);
			logger.info("Successfully  Email Called Service------------");			
			logger.info("Successfully Saved data.");
		
		}catch(Exception e){
		//	e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
	
		}
	}
//----------------- Admin Register New Member Notification --------------
public static void adminRegalertEmail(Member member,String newCodeFinal){
	logger.info("Inside Admin Alert Registration Email Method()----------------------------");
	
	logger.info("Sender Email -->"+member.getEmailID());
	logger.info("Pay amount -->"+member.getPayAmt());
	logger.info("Member type -->"+member.getActType());
	logger.info("Member user name -->"+member.getUsername());
	logger.info("Member password -->"+member.getPassword());
	
	String emailRegalertMessage ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
		+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
		+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL NEW MEMBER REGISTRATION ALERT</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
		+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
		+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear GGL Admin,</span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
		+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"You have alert for New Member registration and Member details in below </span></p>"	
	
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>        </span>"+newCodeFinal+"</span></p>"						
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Member "
		+"Fees Amount <span style='mso-spacerun:yes'>      </span>"
		+" "+member.getPayAmt()+" SGD </span></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Admin "
		+"Fees &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>   "+member.getAdminFees()+" SGD </span></span></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Total"
		+" Amount in SGD &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>  </span>"+member.getTotalFees()+" SGD</span><b"
		+"style='mso-bidi-font-weight:normal'></b></p>"  
		
		+"<br/><br/>  "
	
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Please approve &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>  </span>  <a href=\"http://ggl.neotural.com/login\" style='color:blue;'>GGL LOGIN</a> </span></b></p>"			
		
		+"<br/><br/> <br/><br/> "
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
		+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
		+"mso-fareast-language:ZH-CN'></span></b></p>"
		
		+"</div> </body> </html>";
	
		try {
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
			preparedStatement.setString(1, adminMailID);
			preparedStatement.setString(2, "GGL NEW MEMBER REGISTRATION ALERT"); 
			preparedStatement.setString(3, emailRegalertMessage);// Message
			preparedStatement.setString(4, "GGL");
			preparedStatement.executeUpdate();	*/
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(adminMailID,"GGL NEW MEMBER REGISTRATION ALERT",emailRegalertMessage);
			logger.info("Successfully  Email Called Service------------");
			
		
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception --------->"+e.getMessage());
		}
	
	}

//----------------- Payment Approval Notification --------------
public static void PaymentApproveMail(User user){
	String PaymentApproveMail ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
			+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
			+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL PAYMENT UPLOAD APPROVAL EMAIL</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
			+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
			+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear Member,</span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
			+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Admin have successfully approved the Payment and Approval details in below</span></p>"	
					
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Member ID  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
			+user.getMemberID()+ " </span></p>"

			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Approved Date  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+" </span></span></p>" 		
			
			+"<br/><br/> <br/><br/> "
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
			+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
			+"mso-fareast-language:ZH-CN'></span></b></p>"
			
			+"</div> </body> </html>";	

		try {
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
	
			preparedStatement.setString(1, user.getEmail_ID());
			preparedStatement.setString(2, "GGL ADMIN IS APPROVED YOUR PAYMENT"); 
			preparedStatement.setString(3, PaymentApproveMail);// Message
			preparedStatement.setString(4, "GGL");
			preparedStatement.executeUpdate();	
			logger.info("Successfully Saved data.");*/
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(user.getEmail_ID(),"GGL ADMIN IS APPROVED YOUR PAYMENT",PaymentApproveMail);
			logger.info("Successfully  Email Called Service------------");
		
		}catch(Exception e){
		     logger.info("Exception -->"+e.getMessage());
		}
}

//----------------- Payment Rejected Notification --------------  
public static void PaymentRejectMail(User user){
	String PaymentRejectMail ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
			+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
			+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL PAYMENT UPLOAD REJECT EMAIL</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
			+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
			+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear Member,</span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
			+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"

			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Admin have rejected the Payment and Rejection details in below </span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Member ID  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
			+user.getMemberID()+ " </span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Rejected Date  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+" </span></p>" 		
			
			+"<br/><br/> <br/><br/> "
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
			+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
			+"mso-fareast-language:ZH-CN'></span></b></p>"
			
			+"</div> </body> </html>";	

		try {
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
	
			preparedStatement.setString(1, user.getEmail_ID());
			preparedStatement.setString(2, "GGL ADMIN IS REJECTED YOUR PAYMENT"); 
			preparedStatement.setString(3, PaymentRejectMail);// Message
			preparedStatement.setString(4, "GGL");
			preparedStatement.executeUpdate();	*/			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(user.getEmail_ID(),"GGL ADMIN IS REJECTED YOUR PAYMENT",PaymentRejectMail);
			logger.info("Successfully  Email Called Service------------");
		}catch(Exception e){
		     logger.error("Exception -->"+e.getMessage());
		}
}


//----------------- Admin New Resevation  Notification --------------
public static void adminbookingalertEmail(Member member){
	logger.info("Inside Admin Alert Registration Email Method()----------------------------");

	logger.info("Sender Email -->"+member.getEmailID());
	logger.info("Pay amount -->"+member.getPayAmt());
	logger.info("Member type -->"+member.getActType());
	logger.info("Member user name -->"+member.getUsername());
	logger.info("Member password -->"+member.getPassword());

	String emailReservationMessage ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
		+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
		+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL NEW BOOKING ACKNOWLAGEMENT ALERT</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
		+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
		+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear GGL Admin,</span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
		+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"You have alert for New Member reservation details in below </span></p>"	

		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'> </span>"+member.getMemberID()+"</span></p>"						
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
		+"Booking ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>  </span> "+member.getInvoiceNumber()+" </span></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> Booking Date "
		+" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>   "+Custom.getCurrentDate()+" </span></span></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Booking status"
		+" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>  </span>"+member.getBookingStatus()+"</span><b"
		+"style='mso-bidi-font-weight:normal'></b></p>"  
		
		+"<br/><br/>  "

		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Please approve &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>  </span>  <a href=\"http://ggl.neotural.com/login\" style='color:blue;'>GGL LOGIN</a> </span></b></p>"			
		
		+"<br/><br/> <br/><br/> "
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
		+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
		+"mso-fareast-language:ZH-CN'></span></b></p>"
		
		+"</div> </body> </html>";

		try {
		/*	Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
	
			preparedStatement.setString(1, adminMailID);
			preparedStatement.setString(2, "GGL NEW RESERVATION ALERT"); 
			preparedStatement.setString(3, emailReservationMessage);// Message
			preparedStatement.setString(4, "GGL");
			preparedStatement.executeUpdate();	*/
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(adminMailID,"GGL NEW RESERVATION ALERT",emailReservationMessage);
			logger.info("Successfully  Email Called Service------------");			
		
		}catch(Exception e){
		//	e.printStackTrace();
			logger.error("Exception -->"+e.getMessage());
		}

	}

//----------------- RESERVATION Approval  Notification --------------
public static void ReservationApproveMail(User user){
	logger.info("Inside Admin Alert Registration Email Method()----------------------------");

	logger.info("Sender Email -->"+user.getEmail_ID());
	logger.info("Payment Path --------"+user.getPaymentPath()); 
	logger.info("Payment Path --------"+user.getUserLoginPrimaryKey()); 

/*		String ReservationApproveMail ="<html> <head> <img src=\"http://35.162.40.190/booking/"+user.getUserLoginPrimaryKey()+".jpg\" style=\"align:center\"> "
*/
				String ReservationApproveMail ="<html> <head> "

				
				/*		+"<p class=MsoNormal style='mso-bidi-font-weight:normal;text-align:center;color:red;font-size:16.0pt;'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow;'> "
		+"Reservation Code  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " +user.getAccoutType()+ " </span></p>"*/
		+ " </head> "

		+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
		+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL RESERVATION APPROVAL EMAIL</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
		+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
		+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear Member,</span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
		+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
	
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Admin have successfully approved the Reservation and Approval details in below</span></p>"	
				
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
		+"Member ID  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
		+user.getMemberID()+ " </span></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
		+"Invoice Number  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " +user.getPassword()+ " </span></p>"

		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
		+"Approved Date  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+" </span></span></p>" 		
		
		+"<br/><br/> <br/><br/> "
		+"<img src=\"http://35.162.40.190/booking/"+user.getUserLoginPrimaryKey()+".jpg\" style=\"align:center\">"
		+"<br/><br/> <br/><br/> "
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
		+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
		+"mso-fareast-language:ZH-CN'></span></b></p>"
		
		+"</div> </body> </html>";	

		try {
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
	
			preparedStatement.setString(1, user.getEmail_ID());
			preparedStatement.setString(2, "GGL ADMIN IS APPROVED YOUR RESERVATION"); 
			preparedStatement.setString(3, ReservationApproveMail);// Message
			preparedStatement.setString(4, "GGL");
			preparedStatement.executeUpdate();	
			*/
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(user.getEmail_ID(),"GGL ADMIN IS APPROVED YOUR RESERVATION",ReservationApproveMail);
			logger.info("Successfully  Email Called Service------------");			
		
		}catch(Exception e){
		     logger.error("Exception -->"+e.getMessage());
		}
}
		
//----------------- Resevation Rejection Notification --------------
public static void ReservationRejectMail(User user){
	String ReservationRejectMail ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
		+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
		+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL RESERVATION REJECT EMAIL</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
		+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
		+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear Member,</span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
		+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"

		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Admin have rejected the Reservation and Rejection details in below </span></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
		+"Member ID  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
		+user.getMemberID()+ " </span></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
		+"Invoice Number  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " +user.getPassword()+ " </span></p>" 
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
		+"Rejected Date  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+" </span></p>" 		
		
		+"<br/><br/> <br/><br/> "
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
		+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
		+"mso-fareast-language:ZH-CN'></span></b></p>"
		
		+"</div> </body> </html>";	

		try {
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");	
			preparedStatement.setString(1, user.getEmail_ID());
			preparedStatement.setString(2, "GGL ADMIN IS REJECTED YOUR RESERVATION"); 
			preparedStatement.setString(3,ReservationRejectMail);// Message
			preparedStatement.setString(4, "GGL");
			preparedStatement.executeUpdate();	*/
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(user.getEmail_ID(),"GGL ADMIN IS REJECTED YOUR RESERVATION",ReservationRejectMail);
			logger.info("Successfully  Email Called Service------------");	
		
		}catch(Exception e){
		     logger.error("Exception -->"+e.getMessage());
		}
}

//----------------- Admin New Withdraw  Notification --------------
public static void adminwithdrawalertEmail(Member member){
	logger.info("Inside Admin Alert Withdraw Email Method()----------------------------");

	logger.info("Pay amount -->"+member.getPayAmt());
	logger.info("Member type -->"+member.getActType());
	logger.info("Member user name -->"+member.getUsername());
	logger.info("Member password -->"+member.getPassword());

	String emailReservationMessage ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
		+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
		+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL NEW WITHDRAW ALERT</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
		+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
		+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear GGL Admin,</span></b></p>"
		+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
		+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"You have alert for New Member reservation details in below </span></p>"	

		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'> </span>"+member.getMemberID()+"</span></p>"						
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
		+"Commission Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>  </span> "+member.getMemberCommition()+" </span></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> Overriding Amount "
		+" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>   "+member.getMemberOvrriding()+" </span></span></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Withdraw Amount"
		+" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>  </span>"+ member.getTotalAmount() +"</span><b"
		+"style='mso-bidi-font-weight:normal'></b></p>"  
		
		+"<br/><br/>  "

		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
		+"Please approve &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>  </span>  <a href=\"http://ggl.neotural.com/login\" style='color:blue;'>GGL LOGIN</a> </span></b></p>"			
		
		+"<br/><br/> <br/><br/> "
		
		+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
		
		+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
		+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
		+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
		+"mso-fareast-language:ZH-CN'></span></b></p>"
		
		+"</div> </body> </html>";

		try {
		/*	Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
	
			preparedStatement.setString(1, adminMailID);
			preparedStatement.setString(2, "GGL NEW WITHDRAW ALERT"); 
			preparedStatement.setString(3, emailReservationMessage);// Message
			preparedStatement.setString(4, "GGL");
			preparedStatement.executeUpdate();	*/
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(adminMailID,"GGL NEW WITHDRAW ALERT",emailReservationMessage);
			logger.info("Successfully  Email Called Service------------");
			
		
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
		}

	}		

	//----------------- Withdraw Approval  Notification --------------
	public static void WithdrawApproveMail(Member member){
		logger.info("Inside Admin Alert Withdraw Email Method()----------------------------");
	
		logger.info("Sender Email -->"+member.getEmailID());
	
		String WithdrawApproveMail ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
			+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
			+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL WITHDRAW APPROVAL EMAIL</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
			+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
			+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear Member,</span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
			+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
		
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Admin have successfully approved the Withdraw and Approval details in below</span></p>"	
					
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Member ID  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
			+member.getMemberNumber()+ " </span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"User Name  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
			+member.getUsername()+ " </span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Withdraw Amount  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " +member.getTotalAmount()+ " </span></p>"

			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Approved Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+" </span></span></p>" 		
			
			+"<br/><br/> <br/><br/> "
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
			+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
			+"mso-fareast-language:ZH-CN'></span></b></p>"
			
			+"</div> </body> </html>";	
	
			try {
			/*	Class.forName(JDBC_DRIVER);
				con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
				stmt=con.createStatement();
				preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
		
				preparedStatement.setString(1, member.getEmailID());
				preparedStatement.setString(2, "GGL ADMIN IS APPROVED YOUR WITHDRAW"); 
				preparedStatement.setString(3, WithdrawApproveMail);// Message
				preparedStatement.setString(4, "GGL");
				preparedStatement.executeUpdate();	*/
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(member.getEmailID(),"GGL ADMIN IS APPROVED YOUR WITHDRAW",WithdrawApproveMail);
				logger.info("Successfully  Email Called Service------------");
			
			}catch(Exception e){
			     logger.error("Exception -->"+e.getMessage());
			}
	}
			
	//----------------- Withdraw Rejection Notification --------------
	public static void WithdrawRejectMail(Member member){
		String WithdrawRejectMail ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
			+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
			+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL WITHDRAW REJECT EMAIL</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
			+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
			+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear Member,</span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
			+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"

			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Admin have rejected the Withdraw and Rejection details in below </span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Member ID  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
			+member.getMemberNumber()+ " </span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"User Name  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
			+member.getUsername()+ " </span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Withdraw Amount  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " +member.getTotalAmount()+ " </span></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> "
			+"Rejected Date  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+" </span></p>" 		
			
			+"<br/><br/> <br/><br/> "
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
			+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
			+"mso-fareast-language:ZH-CN'></span></b></p>"
			
			+"</div> </body> </html>";	
	
			try {
				/*Class.forName(JDBC_DRIVER);
				con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
				stmt=con.createStatement();
				preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
				preparedStatement.setString(1, member.getEmailID());
				preparedStatement.setString(2, "GGL ADMIN IS REJECTED YOUR WITHDRAW"); 
				preparedStatement.setString(3, WithdrawRejectMail);// Message
				preparedStatement.setString(4, "GGL");
				preparedStatement.executeUpdate();	*/
				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(member.getEmailID(),"GGL ADMIN IS REJECTED YOUR WITHDRAW",WithdrawRejectMail);
				logger.info("Successfully  Email Called Service------------");
			
			}catch(Exception e){
			     logger.error("Exception -->"+e.getMessage());
			}
	}	
		
		
	public static void saveEmailReferMember1(Member member,String newCodeFinal){
		logger.info("---- Update Member 1 Reference ----"); 
		logger.info("Sender Email -->"+member.getEmailID1());
		logger.info("Sender Member ID -->"+newCodeFinal);
		logger.info("Member type -->"+member.getTriptype());
		logger.info("Pay amount -->"+member.getRef_commition1());
		logger.info("Member user name -->"+member.getRef_ovrriding1());
		
		String emailMessage ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
			+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
			+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL NEW MEMBER REGISTRATION ALERT</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
			+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
			+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear GGL Admin,</span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
			+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+newCodeFinal+"</span></b></p>"
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Member Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getTriptype()+"</span></b>"
			+"</span></b></p>"

			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Commission Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+member.getRef_commition1()+" SGD </span></b></p>"
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Overriding Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>   "+member.getRef_ovrriding1()+" SGD </span>"
			+"</span></b></p>"

			
			+"<br/><br/> <br/><br/> "

			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
			+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
			+"mso-fareast-language:ZH-CN'></span></b></p>"
			
			+"</div> </body> </html>";

		
			try {					
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(member.getEmailID1(),"Updation Successfully on Reference Member 1",emailMessage);
				logger.info("Successfully  Email Called Service------------");			
				logger.info("Successfully Saved data.");
			
			}catch(Exception e){
			//	e.printStackTrace();
				logger.error("Exception -->"+e.getMessage());

			}
	}
	
	public static void saveEmailReferMember2(Member member,String newCodeFinal){
		
		logger.info("---- Update Member 2 Reference ----"); 
		logger.info("Sender Email -->"+member.getEmailID2());
		logger.info("Sender Member ID -->"+newCodeFinal);
		logger.info("Member type -->"+member.getTriptype());
		logger.info("Pay amount -->"+member.getRef_commition2());
		logger.info("Member user name -->"+member.getRef_ovrriding2());
		
		String emailMessage ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
			+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
			+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL NEW MEMBER REGISTRATION ALERT</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
			+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
			+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear GGL Admin,</span></b></p>"
			+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
			+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+newCodeFinal+"</span></b></p>"
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Member Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getTriptype()+"</span></b>"
			+"</span></b></p>"

			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Commission Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+member.getRef_commition2()+" SGD </span></b></p>"
			
			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
			+"Overriding Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>   "+member.getRef_ovrriding2()+" SGD </span>"
			+"</span></b></p>"

			
			+"<br/><br/> <br/><br/> "

			+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
			
			+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
			+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
			+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
			+"mso-fareast-language:ZH-CN'></span></b></p>"
			
			+"</div> </body> </html>";

		
			try {					
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(member.getEmailID2(),"Updation Successfully on Reference Member 2",emailMessage);
				logger.info("Successfully  Email Called Service------------");			
				logger.info("Successfully Saved data.");
			
			}catch(Exception e){
			//	e.printStackTrace();
				logger.error("Exception -->"+e.getMessage());

			}
		}

		public static void saveEmailReferMember3(Member member,String newCodeFinal){
			
			logger.info("---- Update Member 3 Reference ----"); 
			logger.info("Sender Email -->"+member.getEmailID3());
			logger.info("Sender Member ID -->"+newCodeFinal);
			logger.info("Member type -->"+member.getTriptype());
			logger.info("Pay amount -->"+member.getRef_commition3());
			logger.info("Member user name -->"+member.getRef_ovrriding3());
			
			String emailMessage ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
				+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
				+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL NEW MEMBER REGISTRATION ALERT</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
				+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
				+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
				+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
				+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear GGL Admin,</span></b></p>"
				+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
				+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
				
				+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
				+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+newCodeFinal+"</span></b></p>"
				
				+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
				+"Member Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getTriptype()+"</span></b>"
				+"</span></b></p>"
		
				+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
				+"Commission Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+member.getRef_commition3()+" SGD </span></b></p>"
				
				+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
				+"Overriding Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'>   "+member.getRef_ovrriding3()+" SGD </span>"
				+"</span></b></p>"
		
				
				+"<br/><br/> <br/><br/> "
		
				+"<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%'>For and on behalf of,</span></b></p>"
				
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%'>Global Gains Limited</span><a"
				+"name=\"GoBack\"></a><b style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;font-family:\"Arial\",\"sans-serif\";"
				+"mso-fareast-language:ZH-CN'></span></b></p>"
				
				+"</div> </body> </html>";
		
			
				try {					
					logger.info("Calling Email Service -------------");
					PushEmail.sendMail(member.getEmailID3(),"Updation Successfully on Reference Member 3",emailMessage);
					logger.info("Successfully  Email Called Service------------");			
					logger.info("Successfully Saved data.");
				
				}catch(Exception e){
				//	e.printStackTrace();
					logger.error("Exception -->"+e.getMessage());
				}
			}

			public static void websiteFeedBackMail(Member member) {
					logger.info("Name ----->"+member.getFirstName());
					logger.info("Email ----->"+member.getEmailID());
					logger.info("Subject ---->"+member.getAddress());
					logger.info("FeedBack Message ---->"+member.getDescription()); 
					String websiteMail = "<html> <body lang=EN-US link=\"#0563C1\" vlink=\"#954F72\" style='tab-interval:.5in'><div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b" 
							+ "style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL WEBSITE FEEDBACK MAIL</span></u></b><b style='mso-bidi-font-weight:normal'><u><span "
							+ "style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+ "minor-fareast;mso-fareast-language:ZH-CN'></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							+ "<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear GGL Admin,</span></b></p>"
							+ "<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'></span></b></p>"
							
							+ "<p class=MsoNormal style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;line-height:115%;mso-highlight:yellow'> "+member.getDescription()+" </span></p>"
							
							+ "<br/><br/> "
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;line-height:115%'>Thanks,</span></p>"
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;line-height:115%'> "+ member.getFirstName() +" </span>"
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;line-height:115%'> "+ member.getEmailID() +" </span>"
									
							+ "</div> </body> </html>";
					try {					
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail("globalgains49@gmail.com",member.getAddress(),websiteMail);
						//PushEmail.sendMail("shahayajoseny@gmail.com",member.getAddress(),websiteMail);
						logger.info("Successfully  Email Called Service------------");								
					}catch(Exception e){
						logger.error("Exception -->"+e.getMessage());
					}
				}
						
}