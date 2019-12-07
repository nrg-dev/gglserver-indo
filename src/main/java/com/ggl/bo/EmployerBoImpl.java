package com.ggl.bo;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ggl.dao.EmployerDao;
import com.ggl.dto.EmployerDto;
import com.ggl.dto.JobSeekerDto;
import com.ggl.email.EmployerEmailService;
import com.ggl.util.GLGException;


@Service
public class EmployerBoImpl implements EmployerBo{
	
public static final Logger logger = LoggerFactory.getLogger(EmployerBoImpl.class);

	
	@Autowired
	EmployerDao employerdao;
	
	
	public String validateEmployerUserName(JobSeekerDto jobseekerdto) {		
		String result =employerdao.userExitCheck(jobseekerdto.getEmailID());
		return result;

	}
	
	//------------ Login JobSeeker Bo Calling -------------
	public EmployerDto loginEmployer(EmployerDto employer) {
		logger.info("[BO] loginEmployer ------------->");
		try {
			employer = employerdao.loginEmployer(employer);
		}catch(Exception e) {
			System.out.println("loginEmployer Exception ------->"+e.getMessage());
		}
		finally {
			
		}
		return employer;
	}

	public EmployerDto registerEmployer(EmployerDto employer) throws GLGException {
		String result=null;
		logger.info("Before Calling Job Employer DAO for registerEmployer Email Validation");
		result =employerdao.userExitCheck(employer.getUsername());
		logger.info("Successfully Called Job Employer DAO for registerEmployer Email Validation");
		if(result=="success") {
			
			EmployerEmailService employeremial = () -> {
				logger.info("Called Interface 1");

			};
			boolean emailstatus=employeremial.employerRegistration(employer); 	

			if(emailstatus==true) {
				logger.info("Valid Email ID");
				logger.info("Before Calling Job Employer DAO for registerEmployer");
				employer=employerdao.registerEmployer(employer);
				logger.info("Successfully Called Job Employer DAO for registerEmployer");
			} else {
				employer.setStatus("InvalidEmailID");
				logger.info("Invalid Email ID");
			}
			
			

		} 
		else if(result=="failure") {
			employer.setStatus("emailIDExists");
		}
		else {
			employer.setStatus("unknownError");
		}
		
		return employer; 
	}

	
}
