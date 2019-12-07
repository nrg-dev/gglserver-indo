package com.ggl.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ggl.dto.JobSeekerDto;
import com.ggl.util.PushEmail;

@FunctionalInterface
public interface JobSeekerEmailService {
	public static final Logger logger = LoggerFactory.getLogger(JobSeekerEmailService.class);

	public void test();
	
	public default boolean jobSeekerRegistration(JobSeekerDto jobseekerdto){
		logger.info("----------------- Inside payment Confirmation -------------------");
		String email1 ="<html> <head> <style> div {  background-color: white; }</style> </head>\r\n" + 
			"<body bgcolor=\"#E6E6FA\" lang=EN-US style='tab-interval:.5in'> \r\n" + 
			"<a href=\"http://trio-i.com/\">  </a>\r\n" + 
			"<br/><div><br/><p class=MsoNormal align=center style='text-align:center'><b><span\r\n" + 
			"style='font-size:20.0pt;line-height:115%;color:blue'>GGL Job Seeker User Details <p></p></span></b></p>\r\n" + 
			"<p> Dear Job Seeker,</p> \r\n" + 
			"\r\n" + 
			"<p> Here are the login details for your GGL Job Seeker Account.\r\n" + 
			"</p>	\r\n" + 
			"\r\n" + 
			"<p> \r\n" + 
			"User ID: "+jobseekerdto.getEmailID()+"\r\n" + 
			"</p>	\r\n" + 
			"\r\n" + 
			"<p> \r\n" + 
			"To change your password: <a href=\"http://ggl.neotural.com/jobseekerlogin\"> Click Here</a> \r\n" + 
			"</p>	\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>\r\n" + 
			"<p>Thanks and regards,<p></p></span></b></p><p>GGL Management </span></b></p><p>Please feel free to touch with us at 	contact@gglway.com</span></b></p><p>Please visit our website <a href=\"http://trio-i.com/\">http://ggl.neotural.com/</a>  \r\n" + 
			"</span></b></p><br/><br/></div> <br/><br/> \r\n" + 
			"<br/><p><font size=\"2\">\r\n" + 
			"You have received this mail because your e-mail ID is registered with ggl.neotural.com. This is a system-generated e-mail regarding your ggljobs account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of ggl.neotural.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n" + 
			"</font></p></body> </html>"; 
			try {		
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(jobseekerdto.getEmailID(),"your account information from www.gglway.com ",email1);
			logger.info("Successfully  Email Called Service------------");			
			return true;
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
			return false;	
			}		
			
	
	}

}
