package com.ggl.email;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ggl.dto.Member;
import com.ggl.mongo.model.MiniTree;
import com.ggl.mongo.model.PrivateTree;
import com.ggl.mongo.model.Publictree;
import com.ggl.util.PushEmail;

public class InvestmentEmail {

	public static final Logger logger = LoggerFactory.getLogger(InvestmentEmail.class);
	public static String adminMailID="globalgains@gmail.com";

	//--------- Closed After 8 Tree Comes ----
	public static void EightComeOneClosedEmail(Member member,String emailID){
		logger.info("----------------- Inside Public Tree EightComeOneClosedEmail -------------------");
		logger.info("Unit ID ------->"+member.getInvoiceNumber()); 
		logger.info("Email ID ----------->"+member.getEmailID()); 
		String email_closed ="<html><head><style> .panel { border: 1px solid #00a65a;border-radius:0 !important;transition: box-shadow 0.5s; } </style> "
			+ "<style> table, th , td  { width: 40%;border-collapse: collapse;padding: 5px;margin-left:30%; margin-right:30%; } table thead tr{ background-color: #3c8dbc;}</style> "
	 		+ "<style> table tbody tr:nth-child(odd) { background-color: #dbdee0; } table tbody tr:nth-child(even) { background-color: #b3acac; }tbody {text-align: center;}</style></head>"
	 		+ "<body><div class=\"panel panel-default text-center\"> <div style=\"color: #fff !important;background-color: #3c8dbc !important;padding: 10px;font-size: 20px;border-bottom: 1px solid transparent;text-align: center;\"> "
			+ "<h2>Public Tree Unit Closed Info</h2> </div><div style=\"background-color: #d2d6de !important;padding-left: 35px;\"> <br/>  " 
	 		+ "<label>Congratulation you just finished the Unit from GGL SEED FUND</label> <br/><br/>"
	 		+ "<label style=\"font-size: 16px;font-weight: bold;padding-left: 20px;\"> Unit ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp; " + member.getInvoiceNumber() +"</label> <br/> "
	 		+ "<label style=\"font-size: 16px;font-weight: bold;padding-left: 20px;\"> Unit Status &nbsp;&nbsp;&nbsp; :  &nbsp;&nbsp;  CLOSED </label> <br/><br/>"
	 		+ "<label>The disbursement of fund from Public Tree will take 5 working days </label> <br/><br/>"
	 		
	 		+ "<div style=\"text-align: center;\"> Rewards Total </div><br/>"
	 		+ "<table align=\"center\" style=\"border: 1px solid grey\"><thead><tr><th style=\"background-color: #3c8dbc\">Payment Details</th><th style=\"background-color: #3c8dbc\">Fees</th></tr></thead>"
	 		+ "<tbody><tr><td>Unit Rewards</td><td>800 SGD</td></tr><tr><td>Deduction</td><td>100 SGD</td></tr><tr style=\"font-weight:bold;\"><td>Total Amount</td><td>700 SGD</td></tr></tbody></table><br/>"
			+ "<div style=\"text-align: center;margin-left: -80px;\"> *Note : Rate SGD to Rupiah = IDR 10,000/SGD </div><div style=\"text-align: center;\"> Deduction = Re-registration Public Tree Unit </div><br/><br/>"
	 		+ "<label>we got you covered.check out our FAQ or contact our customer service support info@gglway.com</label><br/>"
			+ "<label>Thank you for join with us,have nice a day. </label><br/><br/>"
	 		+ "<p><b>For and on behalf of</b><p>"
			+ "<p><b>Global Gains Limited</b></p>"
	 		+ "<br/>"
			+ "<img src=\"http://35.162.40.190/assets/images/gglLogo1.png\" >"
	 		+ " </div></div></body></html>";
		
		try {
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(emailID,"GGL MEMBER Unit Closed",email_closed);
			logger.info("Successfully  Email Called Service------------");	
			
		}catch(Exception e) {
			logger.info("Public EightComeOneClosedEmail Exception -->"+e.getMessage());
		}
	}
	
	// After 8 out and 1 gets Saved as new Tree
	public static void temp8OutandOnseSavedPublicTree(Member member,String emailID) {
		
		StringBuffer sb = new StringBuffer();
		int tempAmount=0;
		//	for(Member m: list) {
			sb.append("<h1>  Unit Chain Invoice Number #:"+member.getInvoiceNumber()+"</h1>");
			//m.getInvoiceNumber();
			tempAmount=tempAmount+100;
		//	}
		System.out.println("String --->"+sb);
		String email ="<!DOCTYPE html>" + 
				"<html lang=\"en\">" + 
				"<head>  \r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<div class=\"container\">\r\n" + 
				"<div style=\"background-color: #3c8dbc !important; width: 320px; color: white;padding-left: 200px; padding-right: 200px;\">\r\n" + 
				"<h2> Public Tree Invoice Info</h2>\r\n" + 
				"</div>\r\n" + 
				"</div>\r\n" + 
				"<div class=\"container-fluid bg-grey\">\r\n" + 
				"<div class=\"row\"> \r\n" + 
				"<div class=\"col-sm-8\">\r\n" + 
				"<h2>Thank you for buy the Public Unit from GGL Investement:</h2>\r\n" + 
				"<br>	  \r\n" + sb + 
				"Amount Details :  " +tempAmount+"SGD\r\n" + 
				"Note Please make the payment via Wire transfer or Online payment For and on behalf of Global Gains Limited\r\n" + 
				"</div>\r\n" + 
				"</div>\r\n" + 
				"</div>\r\n" + 
				"<footer class=\"container-fluid text-center\">\r\n" + 
				"<a href=\"#myPage\" title=\"To Top\">\r\n" + 
				"<span class=\"glyphicon glyphicon-chevron-up\"></span>\r\n" + 
				"</a>\r\n" + 
				"<p>GGL System Made By <a href=\"https://www.gglway.com\" title=\"Visit gglway.com\">www.gglway.com</a></p>\r\n" + 
				"</footer>\r\n" + 
				"</body>\r\n" + 
				"</html>\r\n" + 
				"";
		try {

			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(emailID,"GGL Public Tree | Purchase Order",email);
			logger.info("Successfully  Email Called Service------------");	
			
		logger.info("Successfully Saved data.");
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
		}
		
	}


	public static void tempPublicTree(ArrayList<Member> list,String emailID) {
		
		StringBuffer sb = new StringBuffer();
		StringBuffer payment = new StringBuffer();
		int tempAmount=0;
		for(Member m: list) {
			sb.append("<h1>  Unit Invoice Number #:"+m.getInvoiceNumber()+"</h1>");
			payment.append("<tr><td>Public Tree - "+m.getInvoiceNumber()+" </td><td> 100 SGD </td></tr>");
			tempAmount=tempAmount+100;
		}
		System.out.println("String --->"+sb);
		System.out.println("Table Payment Details --->"+payment);
		String email ="<!DOCTYPE html>"  
				+ "	<html><head><style> .panel { border-radius:0 !important;transition: box-shadow 0.5s; } </style> " 
				+ "	<style> table { border-collapse: collapse;width: 100%; } th, td {  border: 1px solid black;padding: 8px;text-align: center; }</style> " 
				+ "	<style> table tbody tr:nth-child(odd) { background-color: #dbdee0; } table tbody tr:nth-child(even) { background-color: #b3acac; }tbody {text-align: center;}</style></head>" 
				+ "	<body><div class=\"panel panel-default text-center\"> <div style=\"color: #fff !important;background-color: #3c8dbc !important;padding: 10px;font-size: 20px;border-bottom: 1px solid transparent;text-align: center;\"> " 
				+ "	<h2>Public Tree Unit Invoice Info</h2> </div><div style=\"background-color: #f3efed !important;padding-left: 35px;\"> <br/> "  
				+ "	<label>Thank you for buy the Public Unit from GGL SEED FUND : </label> <br/><br/>"  
				+ "	<label style=\"padding-left: 20px;\">" + sb + "</h1><label> <br/><br/> "  
				+ "	<label>Please make the payment via Wire transfer</label><br/> " 

				+ "	<table> <thead>  <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th>" 
				+ "	<th>Bank Account Number</th>  <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr>  </thead>" 
				+ "	<tbody>  <tr style=\"background-color: #f3efed;\"> <td>DBS Bank</td>     <td>Global Gains Limited</td>     <td>003-932398-0</td> 	    <td>DBSSGSG</td>     <td>0346756</td>     <td>RS - 24455</td> <td> 12 Marina Boulevard,Marina Bay Financial Centre,Tower 3, Singapore 018982" 
				+ "	</td> </tr>  </tbody>  </table>" 

				+ "	<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><span style='font-size:12.0pt'>" 
				+ "	or Bank Account in thru our partner </span></p>" 

				+ "	<table style=\"width:100%:border:2px;\"> <thead>  <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th>  "  
				+ "	<th>Bank Account Number</th> 	    <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr> </thead>" 
				+ "	<tbody><tr  style=\"background-color: #f3efed;\"><td style='text-align: center;'>CIMB NIAGA</td>  <td style='text-align: center;'>Blueprint Nusatara Indonesia</td>     <td style='text-align: center;'>800077326600</td> 	    <td style='text-align: center;'>BNIAIDJA</td>     <td style='text-align: center;'>84711</td>"  
				+ "	<td style='text-align: center;'>60875 - 60877 NAGA HO IA</td>  <td style='text-align: center;'> GrahaNiaga / Niaga Tower Jl. Jend. SudirmanKav. 58, Jakarta 12190</td> </tr></table>"  

				+ "	<br/> " 
				+ "	<div style=\"text-align: center;\"> Payment Total </div><br/>" 
				+ "	<table align=\"center\" style=\"width: 40%;padding: 5px;margin-left:30%; margin-right:30%;\"><thead><tr><th style=\"background-color: #3c8dbc;border: 1px solid grey\">Type of Payment</th><th style=\"background-color: #3c8dbc;border: 1px solid grey\">Fees</th></tr></thead>" 
				+ " <tbody>" + payment + " <tr style=\"font-weight:bold;\"><td style=\"font-weight:bold;\">Total Amount</td><td style=\"font-weight:bold;\">" + tempAmount + "SGD </td></tr></tbody></table><br/>"  
				+ "	<div style=\"text-align: center;margin-left: -80px;\"> *Note : Kurs SGD to Rupiah = IDR 10.500/SGD </div><br/><br/>" 

				+ "	<label>Please Confirm and upload if you have made payment to http://ggl.neotural.com/login</label><br/>" 
				+ "	<label>Thank you for join with us,have nice a day. </label><br/><br/> " 
				+ "	<p><b>For and on behalf of</b><p> "  
				+ "	<p><b>Global Gains Limited</b></p> " 
				+ "	<br/> "  
				+ "	<img src=\"http://35.162.40.190/assets/images/gglLogo1.png\" > "  
				+ "	</div></div></body></html> ";
		try {

			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(emailID,"GGL Public Tree | Purchase Order",email);
			logger.info("Successfully  Email Called Service------------");	
			
		logger.info("Successfully Saved data.");
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
		}
		
	}
	
	// Own Tree
	public static void tempOwnTree(ArrayList<Member> list,String emailID) {
		
		StringBuffer sb = new StringBuffer();
		StringBuffer payment = new StringBuffer();
		int tempAmount=0;
		for(Member m: list) {
			sb.append("<h1> Own Tree Unit Invoice Number #:"+m.getInvoiceNumber()+"</h1>");
			payment.append("<tr><td>Own Tree - "+m.getInvoiceNumber()+" </td><td> 100 SGD </td></tr>");
			tempAmount=tempAmount+100;
		}
		System.out.println("Own String --->"+sb);
		System.out.println("Own Table Payment Details --->"+payment);
		String email ="<!DOCTYPE html> "  
				+ "	<html><head><style> .panel { border-radius:0 !important;transition: box-shadow 0.5s; } </style> \"\r\n" 
				+ "	<style> table { border-collapse: collapse;width: 100%; } th, td {  border: 1px solid black;padding: 8px;text-align: center; }</style> " 
				+ "	<style> table tbody tr:nth-child(odd) { background-color: #dbdee0; } table tbody tr:nth-child(even) { background-color: #b3acac; }tbody {text-align: center;}</style></head> " 
				+ "	<body><div class=\"panel panel-default text-center\"> <div style=\"color: #fff !important;background-color: #3c8dbc !important;padding: 10px;font-size: 20px;border-bottom: 1px solid transparent;text-align: center;\"> " 
				+ "	<h2>Own Tree Unit Closed Info</h2> </div><div style=\"background-color: #f3efed !important;padding-left: 35px;\"> <br/> "  
				+ "	<label>Thank you for buy the Own Unit from GGL SEED FUND : </label> <br/><br/> "  
				+ "	<label style=\"padding-left: 20px;\">" + sb + "</h1><label> <br/><br/> "  
				+ "	<label>Please make the payment via Wire transfer</label><br/> " 

				+ "	<table> <thead>  <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th> " 
				+ "	<th>Bank Account Number</th> 	    <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr>  </thead>" 
				+ "	<tbody>  <tr style=\"background-color: #f3efed;\"> <td>DBS Bank</td>     <td>Global Gains Limited</td>     <td>003-932398-0</td> 	    <td>DBSSGSG</td>     <td>0346756</td>     <td>RS - 24455</td> <td> 12 Marina Boulevard,Marina Bay Financial Centre,Tower 3, Singapore 018982 " 
				+ "	</td> </tr>  </tbody>  </table>" 

				+ "	<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><span style='font-size:12.0pt'> " 
				+ "	or Bank Account in thru our partner </span></p>" 

				+ "	<table style=\"width:100%:border:2px;\"> <thead>  <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th>"  
				+ "	<th>Bank Account Number</th> 	    <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr> </thead> " 
				+ "	<tbody><tr  style=\"background-color: #f3efed;\"><td style='text-align: center;'>CIMB NIAGA</td>  <td style='text-align: center;'>Blueprint Nusatara Indonesia</td>     <td style='text-align: center;'>800077326600</td> 	    <td style='text-align: center;'>BNIAIDJA</td>     <td style='text-align: center;'>84711</td> "  
				+ "	<td style='text-align: center;'>60875 - 60877 NAGA HO IA</td>  <td style='text-align: center;'> GrahaNiaga / Niaga Tower Jl. Jend. SudirmanKav. 58, Jakarta 12190</td> </tr></table> "  

				+ "	<br/> " 
				+ "	<div style=\"text-align: center;\"> Payment Total </div><br/> " 
				+ "	<table align=\"center\" style=\"width: 40%;padding: 5px;margin-left:30%; margin-right:30%;\"><thead><tr><th style=\"background-color: #3c8dbc;border: 1px solid grey\">Type of Payment</th><th style=\"background-color: #3c8dbc;border: 1px solid grey\">Fees</th></tr></thead> " 
				+ " <tbody>" + payment + " <tr style=\"font-weight:bold;\"><td style=\"font-weight:bold;\">Total Amount</td><td style=\"font-weight:bold;\">" + tempAmount + " SGD </td></tr></tbody></table><br/> "  
				+ "	<div style=\"text-align: center;margin-left: -80px;\"> *Note : Kurs SGD to Rupiah = IDR 10.500/SGD </div><br/><br/> " 

				+ "	<label>Please Confirm and upload if you have made payment to http://ggl.neotural.com/login</label><br/> " 
				+ "	<label>Thank you for join with us,have nice a day. </label><br/><br/> " 
				+ "	<p><b>For and on behalf of</b><p> "  
				+ "	<p><b>Global Gains Limited</b></p> " 
				+ "	<br/>\"\r\n"  
				+ "	<img src=\"http://35.162.40.190/assets/images/gglLogo1.png\" > "  
				+ "	</div></div></body></html> ";
		try {

			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(emailID,"GGL Own Tree | Purchase Order",email);
			logger.info("Successfully  Email Called Service------------");	
			
		logger.info("Successfully Saved data.");
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
		}
		
		}
	
	
	// Private Tree
	
		public static void tempPrivateTree(ArrayList<Member> list,String emailID) {
			
			StringBuffer sb = new StringBuffer();
			StringBuffer payment = new StringBuffer();
			int tempAmount=0;
			for(Member m: list) {
				sb.append("<h1>  Unit Invoice Number #:"+m.getInvoiceNumber()+"</h1>");
				tempAmount=tempAmount+100;
				payment.append("<tr><td>Private Tree - "+m.getInvoiceNumber()+" </td><td> 100 SGD </td></tr>");
			}
			System.out.println("Private String --->"+sb);
			System.out.println("Private Table Payment Details --->"+payment);
			String email ="<!DOCTYPE html> "  
					+ "	<html><head><style> .panel { border-radius:0 !important;transition: box-shadow 0.5s; } </style> " 
					+ "	<style> table { border-collapse: collapse;width: 100%; } th, td {  border: 1px solid black;padding: 8px;text-align: center; }</style> " 
					+ "	<style> table tbody tr:nth-child(odd) { background-color: #dbdee0; } table tbody tr:nth-child(even) { background-color: #b3acac; }tbody {text-align: center;}</style></head> " 
					+ "	<body><div class=\"panel panel-default text-center\"> <div style=\"color: #fff !important;background-color: #3c8dbc !important;padding: 10px;font-size: 20px;border-bottom: 1px solid transparent;text-align: center;\"> " 
					+ "	<h2>Private Tree Unit Closed Info</h2> </div><div style=\"background-color: #f3efed !important;padding-left: 35px;\"> <br/> "  
					+ "	<label>Thank you for buy the Private Unit from GGL SEED FUND : </label> <br/><br/> " 
					+ "	<label style=\"padding-left: 20px;\">" + sb + "</h1><label> <br/><br/>"  
					+ "	<label>Please make the payment via Wire transfer</label><br/> " 

					+ "	<table> <thead>  <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th> " 
					+ "	<th>Bank Account Number</th> 	    <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr>  </thead> " 
					+ "	<tbody>  <tr style=\"background-color: #f3efed;\"> <td>DBS Bank</td>     <td>Global Gains Limited</td>     <td>003-932398-0</td> 	    <td>DBSSGSG</td>     <td>0346756</td>     <td>RS - 24455</td> <td> 12 Marina Boulevard,Marina Bay Financial Centre,Tower 3, Singapore 018982 " 
					+ "	</td> </tr>  </tbody>  </table>" 

					+ "	<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><span style='font-size:12.0pt'> " 
					+ "	or Bank Account in thru our partner </span></p> " 

					+ "	<table style=\"width:100%:border:2px;\"> <thead>  <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th>"  
					+ "	<th>Bank Account Number</th> 	    <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr> </thead> " 
					+ "	<tbody><tr  style=\"background-color: #f3efed;\"><td style='text-align: center;'>CIMB NIAGA</td>  <td style='text-align: center;'>Blueprint Nusatara Indonesia</td>     <td style='text-align: center;'>800077326600</td> 	    <td style='text-align: center;'>BNIAIDJA</td>     <td style='text-align: center;'>84711</td> "  
					+ "	<td style='text-align: center;'>60875 - 60877 NAGA HO IA</td>  <td style='text-align: center;'> GrahaNiaga / Niaga Tower Jl. Jend. SudirmanKav. 58, Jakarta 12190</td> </tr></table> "  

					+ "	<br/>\"\r\n" 
					+ "	<div style=\"text-align: center;\"> Payment Total </div><br/> " 
					+ "	<table align=\"center\" style=\"width: 40%;padding: 5px;margin-left:30%; margin-right:30%;\"><thead><tr><th style=\"background-color: #3c8dbc;border: 1px solid grey\">Type of Payment</th><th style=\"background-color: #3c8dbc;border: 1px solid grey\">Fees</th></tr></thead> " 
					+ " <tbody>" + payment + " <tr style=\"font-weight:bold;\"><td style=\"font-weight:bold;\">Total Amount</td><td style=\"font-weight:bold;\">" + tempAmount + " SGD </td></tr></tbody></table><br/> "  
					+ "	<div style=\"text-align: center;margin-left: -80px;\"> *Note : Kurs SGD to Rupiah = IDR 10.500/SGD </div><br/><br/> " 

					+ "	<label>Please Confirm and upload if you have made payment to http://ggl.neotural.com/login</label><br/> " 
					+ "	<label>Thank you for join with us,have nice a day. </label><br/><br/> " 
					+ "	<p><b>For and on behalf of</b><p> "  
					+ "	<p><b>Global Gains Limited</b></p> " 
					+ "	<br/> "  
					+ "	<img src=\"http://35.162.40.190/assets/images/gglLogo1.png\" > "  
					+ "	</div></div></body></html> ";
			try {

				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(emailID,"GGL Private Tree | Purchase Order",email);
				logger.info("Successfully  Email Called Service------------");	
				
			logger.info("Successfully Saved data.");
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
			}
			
		}
		
		
		// Private
		public static void approvePrivateTree(PrivateTree l ,String emailID) {
			
			StringBuffer sb = new StringBuffer();
			int tempAmount=100;
			//for(Member m: list) {
				sb.append("<h1>  Private Unit Original Invoice Number #:"+l.getInvoiceNumber()+"</h1>");
				//m.getInvoiceNumber();
				//tempAmount=tempAmount+100;
			//}
			//System.out.println("String --->"+sb);
			String email ="<!DOCTYPE html>\r\n" + 
					"<html lang=\"en\">\r\n" + 
					"<head>  \r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"<div class=\"container\">\r\n" + 
					"<div style=\"background-color: #3c8dbc !important; width: 320px; color: white;padding-left: 200px; padding-right: 200px;\">\r\n" + 
					"<h2> Private Tree Invoice Info</h2>\r\n" + 
					"</div>\r\n" + 
					"</div>\r\n" + 
					"<div class=\"container-fluid bg-grey\">\r\n" + 
					"<div class=\"row\"> \r\n" + 
					"<div class=\"col-sm-8\">\r\n" + 
					"<h2>Thank you for buy the Private Unit from GGL Investement:</h2>\r\n" + 
					"<br>	  \r\n" + sb + 
					"Paid Amount Details :  " +tempAmount+"SGD\r\n" + 
					"Note Please trak and check in My status in Your Member Login in GGL System behalf of Global Gains Limited\r\n" + 
					"</div>\r\n" + 
					"</div>\r\n" + 
					"</div>\r\n" + 
					"<footer class=\"container-fluid text-center\">\r\n" + 
					"<a href=\"#myPage\" title=\"To Top\">\r\n" + 
					"<span class=\"glyphicon glyphicon-chevron-up\"></span>\r\n" + 
					"</a>\r\n" + 
					"<p>GGL System Made By <a href=\"https://www.gglway.com\" title=\"Visit gglway.com\">www.gglway.com</a></p>\r\n" + 
					"</footer>\r\n" + 
					"</body>\r\n" + 
					"</html>\r\n" + 
					"";
			try {

				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(emailID,"GGL Private Tree | Purchase Order Confirmed Invoice",email);
				logger.info("Successfully  Email Called Service------------");	
				
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
			}
			
		}
		
		// Own Tree
		public static void approveOwnTree(ArrayList<Member> list,String emailID,String owntreeName) {
			
			StringBuffer sb = new StringBuffer();
			int tempAmount=0;
			for(Member m: list) {
				sb.append("<h1> Own Tree Unit Original Invoice Number #:"+m.getInvoiceNumber()+"</h1>");
				//m.getInvoiceNumber();
				tempAmount=tempAmount+100;
			}
			System.out.println("String --->"+sb);
			String email ="<!DOCTYPE html>\r\n" + 
					"<html lang=\"en\">\r\n" + 
					"<head>  \r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"<div class=\"container\">\r\n" + 
					"<div style=\"background-color: #3c8dbc !important; width: 320px; color: white;padding-left: 200px; padding-right: 200px;\">\r\n" + 
					"<h2> Own Tree Invoice Info</h2>\r\n" + 
					"</div>\r\n" + 
					"</div>\r\n" + 
					"<div class=\"container-fluid bg-grey\">\r\n" + 
					"<div class=\"row\"> \r\n" + 
					"<div class=\"col-sm-8\">\r\n" + 
					"<h2>Your OWN Tree Name : "+owntreeName+"</h2>\r\n" + 
					"<h2>Thank you for buy the Own Tree Unit from GGL Investement:</h2>\r\n" + 
					"<br>	  \r\n" + sb + 
					"Paid Amount Details :  " +tempAmount+"SGD\r\n" + 
					"Note Please make note Please check the status of your Unit details on behalf of Global Gains Limited\r\n" + 
					"</div>\r\n" + 
					"</div>\r\n" + 
					"</div>\r\n" + 
					"<footer class=\"container-fluid text-center\">\r\n" + 
					"<a href=\"#myPage\" title=\"To Top\">\r\n" + 
					"<span class=\"glyphicon glyphicon-chevron-up\"></span>\r\n" + 
					"</a>\r\n" + 
					"<p>GGL System Made By <a href=\"https://www.gglway.com\" title=\"Visit gglway.com\">www.gglway.com</a></p>\r\n" + 
					"</footer>\r\n" + 
					"</body>\r\n" + 
					"</html>\r\n" + 
					"";
			try {

				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(emailID,"GGL Own Tree | Purchase Order Confirmed Invoice",email);
				logger.info("Successfully  Email Called Service------------");	
				
				logger.info("Successfully Saved data.");
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
			}
			
		}
		

		// Approved Public Tree 
		public static void approvePublicTree( Publictree l ,String emailID) {
			
			StringBuffer sb = new StringBuffer();
			int tempAmount=100;
			//for(Member m: list) {
				sb.append("<h1>  Public Unit Original Invoice Number #:"+l.getInvoiceNumber()+"</h1>");
				//m.getInvoiceNumber();
				//tempAmount=tempAmount+100;
			//}
			//System.out.println("String --->"+sb);
			String email ="<!DOCTYPE html>\r\n" + 
					"<html lang=\"en\">\r\n" + 
					"<head>  \r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"<div class=\"container\">\r\n" + 
					"<div style=\"background-color: #3c8dbc !important; width: 320px; color: white;padding-left: 200px; padding-right: 200px;\">\r\n" + 
					"<h2> Public Tree Invoice Info</h2>\r\n" + 
					"</div>\r\n" + 
					"</div>\r\n" + 
					"<div class=\"container-fluid bg-grey\">\r\n" + 
					"<div class=\"row\"> \r\n" + 
					"<div class=\"col-sm-8\">\r\n" + 
					"<h2>Thank you for buy the Private Unit from GGL Investement:</h2>\r\n" + 
					"<br>	  \r\n" + sb + 
					"Paid Amount Details :  " +tempAmount+"SGD\r\n" + 
					"Note Please check the status in Your Member Login in GGL System behalf of Global Gains Limited\r\n" + 
					"</div>\r\n" + 
					"</div>\r\n" + 
					"</div>\r\n" + 
					"<footer class=\"container-fluid text-center\">\r\n" + 
					"<a href=\"#myPage\" title=\"To Top\">\r\n" + 
					"<span class=\"glyphicon glyphicon-chevron-up\"></span>\r\n" + 
					"</a>\r\n" + 
					"<p>GGL System Made By <a href=\"https://www.gglway.com\" title=\"Visit gglway.com\">www.gglway.com</a></p>\r\n" + 
					"</footer>\r\n" + 
					"</body>\r\n" + 
					"</html>\r\n" + 
					"";
			try {

				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(emailID,"GGL Public Tree | Purchase Order Confirmed Invoice",email);
				logger.info("Successfully  Email Called Service------------");	
				
			logger.info("Successfully Saved data.");
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
			}
			
		}
		
		//---------- Insertion Email For MiniTree --------
		public static void tempMiniTree(ArrayList<Member> miniList,String emailID) {
			logger.info("------------ Inside tempMiniTree Method Calling -----------------");
			logger.info("Temp Mini EmailID ------>"+emailID);
			StringBuffer sb = new StringBuffer();
			StringBuffer payment = new StringBuffer();
			int tempAmount=0;
			for(Member m: miniList) {
				sb.append("<h1>  Unit Invoice Number #:"+m.getInvoiceNumber()+"</h1>");
				payment.append("<tr><td>Mini Tree - "+m.getInvoiceNumber()+" </td><td> 100 SGD </td></tr>");
				tempAmount=tempAmount+100;
			}
			System.out.println("String ----->"+sb);
			System.out.println("Table Payment Details --->"+payment);
			String email ="<!DOCTYPE html>\r\n"  
				+ "	<html><head><style> .panel { border-radius:0 !important;transition: box-shadow 0.5s; } </style>" 
				+ "	<style> table { border-collapse: collapse;width: 100%; } th, td {  border: 1px solid black;padding: 8px;text-align: center; }</style> " 
				+ "	<style> table tbody tr:nth-child(odd) { background-color: #dbdee0; } table tbody tr:nth-child(even) { background-color: #b3acac; }tbody {text-align: center;}</style></head>" 
				+ "	<body><div class=\"panel panel-default text-center\"> <div style=\"color: #fff !important;background-color: #3c8dbc !important;padding: 10px;font-size: 20px;border-bottom: 1px solid transparent;text-align: center;\">" 
				+ "	<h2>Mini Tree Unit Invoice Info</h2> </div><div style=\"background-color: #f3efed !important;padding-left: 35px;\"> <br/>"  
				+ "	<label>Thank you for buy the Public Unit from GGL SEED FUND : </label> <br/><br/>"  
				+ "	<label style=\"padding-left: 20px;\">" + sb + "</h1><label> <br/><br/>"  
				+ "	<label>Please make the payment Via Wire transfer</label><br/>" 

				+ "	<table> <thead>  <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th>" 
				+ "	<th>Bank Account Number</th> 	    <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr>  </thead> " 
				+ "	<tbody>  <tr style=\"background-color: #f3efed;\"> <td>DBS Bank</td>     <td>Global Gains Limited</td>     <td>003-932398-0</td> 	    <td>DBSSGSG</td>     <td>0346756</td>     <td>RS - 24455</td> <td> 12 Marina Boulevard,Marina Bay Financial Centre,Tower 3, Singapore 018982 " 
				+ "	</td> </tr>  </tbody>  </table>" 

				+ "	<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><span style='font-size:12.0pt'>" 
				+ "	or Bank Account in thru our partner </span></p>" 

				+ "	<table style=\"width:100%:border:2px;\"> <thead>  <tr>    <th>Name of Bank</th>     <th>Bank Account Holder</th>"  
				+ "	<th>Bank Account Number</th> 	    <th>Swift Code</th>     <th>CHIPS UID Number</th>     <th>Telex Number</th>     <th>Bank Address</th>   </tr> </thead>" 
				+ "	<tbody><tr  style=\"background-color: #f3efed;\"><td style='text-align: center;'>CIMB NIAGA</td>  <td style='text-align: center;'>Blueprint Nusatara Indonesia</td>     <td style='text-align: center;'>800077326600</td> 	    <td style='text-align: center;'>BNIAIDJA</td>     <td style='text-align: center;'>84711</td> "  
				+ "	<td style='text-align: center;'>60875 - 60877 NAGA HO IA</td>  <td style='text-align: center;'> GrahaNiaga / Niaga Tower Jl. Jend. SudirmanKav. 58, Jakarta 12190</td> </tr></table> "  

				+ "	<br/> \r\n" 
				+ "	<div style=\"text-align: center;\"> Payment Total </div><br/> " 
				+ "	<table align=\"center\" style=\"width: 40%;padding: 5px;margin-left:30%; margin-right:30%;\"><thead><tr><th style=\"background-color: #3c8dbc;border: 1px solid grey\">Type of Payment</th><th style=\"background-color: #3c8dbc;border: 1px solid grey\">Fees</th></tr></thead> " 
				+ " <tbody> " + payment + " <tr style=\"font-weight:bold;\"><td style=\"font-weight:bold;\">Total Amount</td><td style=\"font-weight:bold;\">" + tempAmount + " SGD </td></tr></tbody></table><br/> "  
				+ "	<div style=\"text-align: center;margin-left: -80px;\"> *Note : Kurs SGD to Rupiah = IDR 10.500/SGD </div><br/><br/> " 

				+ "	<label>Please Confirm and upload if you have made payment to http://ggl.neotural.com/login</label><br/> " 
				+ "	<label>Thank you for join with us,have nice a day. </label><br/><br/> " 
				+ "	<p><b>For and on behalf of</b><p> "  
				+ "	<p><b>Global Gains Limited</b></p> " 
				+ "	<br/> "  
				+ "	<img src=\"http://35.162.40.190/assets/images/gglLogo1.png\" > "  
				+ "	</div></div></body></html> ";
			try {

				
				logger.info("Calling Email Service ------------");
				PushEmail.sendMail(emailID,"GGL Mini Tree | Purchase Order",email);
				logger.info("Successfully  Email Called Service------------");	
				
			}catch(Exception e) {
				logger.info("Exception ---->"+e.getMessage());
			}
			
		}
		
		//--------- Closed After 3 Tree Comes ----
		public static void ThreeComeOneClosedEmail(Member member,String emailID){
			logger.info("----------------- Inside Mini Tree ThreeComeOneClosedEmail -------------------");
			logger.info("Unit ID ------->"+member.getInvoiceNumber()); 
			logger.info("Closed Mini Email ID ----------->"+emailID); 
			String email_closed ="<html><head><style> .panel { border: 1px solid #00a65a;border-radius:0 !important;transition: box-shadow 0.5s; } </style> "
				+ "<style> table, th , td  { width: 40%;border-collapse: collapse;padding: 5px;margin-left:30%; margin-right:30%; } table thead tr{ background-color: #3c8dbc;}</style> "
		 		+ "<style> table tbody tr:nth-child(odd) { background-color: #dbdee0; } table tbody tr:nth-child(even) { background-color: #b3acac; }tbody {text-align: center;}</style></head>"
		 		+ "<body><div class=\"panel panel-default text-center\"> <div style=\"color: #fff !important;background-color: #3c8dbc !important;padding: 10px;font-size: 20px;border-bottom: 1px solid transparent;text-align: center;\"> "
				+ "<h2>Mini Tree Unit Closed Info</h2> </div><div style=\"background-color: #d2d6de !important;padding-left: 35px;\"> <br/>  " 
		 		+ "<label>Congratulation you just finished the Unit from GGL SEED FUND</label> <br/><br/>"
		 		+ "<label style=\"font-size: 16px;font-weight: bold;padding-left: 20px;\"> Unit ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp; " + member.getInvoiceNumber() +"</label> <br/> "
		 		+ "<label style=\"font-size: 16px;font-weight: bold;padding-left: 20px;\"> Unit Status &nbsp;&nbsp;&nbsp; :  &nbsp;&nbsp;  CLOSED </label> <br/><br/>"
		 		+ "<label>The disbursement of fund from Mini Tree will take 5 working days </label> <br/><br/>"
		 		
		 		+ "<div style=\"text-align: center;\"> Rewards Total </div><br/>"
		 		+ "<table align=\"center\" style=\"border: 1px solid grey\"><thead><tr><th style=\"background-color: #3c8dbc\">Payment Details</th><th style=\"background-color: #3c8dbc\">Fees</th></tr></thead>"
		 		+ "<tbody><tr><td>Unit Rewards</td><td>300 SGD</td></tr><tr><td>Deduction</td><td>100 SGD</td></tr><tr style=\"font-weight:bold;\"><td>Total Amount</td><td>200 SGD</td></tr></tbody></table><br/>"
				+ "<div style=\"text-align: center;margin-left: -80px;\"> *Note : Rate SGD to Rupiah = IDR 10,000/SGD </div><div style=\"text-align: center;\"> Deduction = Re-registration Mini Tree Unit </div><br/><br/>"
		 		+ "<label>we got you covered.check out our FAQ or contact our customer service support info@gglway.com</label><br/>"
				+ "<label>Thank you for join with us,have nice a day. </label><br/><br/>"
		 		+ "<p><b>For and on behalf of</b><p>"
				+ "<p><b>Global Gains Limited</b></p>"
		 		+ "<br/>"
				+ "<img src=\"http://35.162.40.190/assets/images/gglLogo1.png\" >"
		 		+ " </div></div></body></html>";
			
			try {
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(emailID,"GGL MINI MEMBER Unit Closed",email_closed);
				logger.info("Successfully  Email Called Service ------------");	
				
			}catch(Exception e) {
				logger.info("Mini ThreeComeOneClosedEmail Exception -->"+e.getMessage());
			}
		}
		
		//-------- Admin Mini Unit Tree Closed Info ----------
		public static void adminminiunitclosedalertEmail(Member member){
			logger.info("Inside Admin Alert MiniUnit Closed Email Method() ----------------------------");
			logger.info("Unit ID --------->"+member.getInvoiceNumber());
		
			String emailRegalertMessage ="<html> <head> <style> table, th, td {     border: 1px solid black;} </style> </head> "
				+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in'> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
				+"style='mso-bidi-font-weight:normal;color:blue;'><u><span style='font-size:26.0pt'>GGL MINI MEMBER UNIT CLOSED ALERT</span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
				+"style='font-size:26.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
				+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
				+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
				+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'>Dear GGL Admin,</span></b></p>"
				+"<p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><b"
				+"style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>"
				
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>"
				+"You have alert for Mini Member unit closed and closed details in below </span></p>"	
			
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Unit ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp; "+member.getInvoiceNumber()+" </span></p>"
				
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> Unit Status  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :  &nbsp;&nbsp; <span style='mso-spacerun:yes'> CLOSED </span></span></p>"
				
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> Unit Rewards  &nbsp;&nbsp;&nbsp; :  &nbsp;&nbsp; <span style='mso-spacerun:yes'> 300 SGD </span></span></p>"
		
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'> Deduction  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :  &nbsp;&nbsp; <span style='mso-spacerun:yes'> 100 SGD </span></span></p>"
				
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:12.0pt;line-height:115%;background:yellow;mso-highlight:yellow'>Total Amount &nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp; <span style='mso-spacerun:yes'> 200 SGD </span></span><b style='mso-bidi-font-weight:normal'></b></p>"
				
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

		//------ Approve Mini Unit Invoice -------
		public static void approveMiniTree(MiniTree minitree, String emailID) {
			logger.info("Approve Mini EmailID ------>"+emailID);
			StringBuffer sb = new StringBuffer();
			int tempAmount=100;
			sb.append("<h1> Mini Unit Original Invoice Number #:"+minitree.getInvoiceNumber()+"</h1>");
			String email ="<!DOCTYPE html>\r\n" + 
					"<html lang=\"en\">" + 
					"<head> " + 
					"</head>" + 
					"<body>" + 
					"<div class=\"container\">" + 
					"<div style=\"background-color: #3c8dbc !important; width: 320px; color: white;padding-left: 200px; padding-right: 200px;\">" + 
					"<h2> Mini Tree Invoice Info</h2>" + 
					"</div>" + 
					"</div>" + 
					"<div class=\"container-fluid bg-grey\">" + 
					"<div class=\"row\"> " + 
					"<div class=\"col-sm-8\">" + 
					"<h2>Thank you for buy the Mini Unit from GGL Investement:</h2>" + 
					"<br>	" + sb + 
					"Paid Amount Details :  " +tempAmount+"SGD" + 
					"Note Please check the status in Your Member Login in GGL System behalf of Global Gains Limited " + 
					"</div>" + 
					"</div>" + 
					"</div>" + 
					"<footer class=\"container-fluid text-center\">" + 
					"<a href=\"#myPage\" title=\"To Top\">" + 
					"<span class=\"glyphicon glyphicon-chevron-up\"></span>" + 
					"</a>" + 
					"<p>GGL System Made By <a href=\"https://www.gglway.com\" title=\"Visit gglway.com\">www.gglway.com</a></p>" + 
					"</footer>" + 
					"</body>" + 
					"</html>" + 
					"";
			try {

				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(emailID,"GGL Mini Tree | Purchase Order Confirmed Invoice",email);
				logger.info("Successfully  Email Called Service------------");	
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
			}
		}
		
}
