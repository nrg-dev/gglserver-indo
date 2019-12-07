package com.ggl.bo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ggl.dao.JobSeekerDao;
import com.ggl.dto.JobSeekerDto;
import com.ggl.email.JobSeekerEmailService;
import com.ggl.util.GLGException;


@Service
public class JobSeekerBoImpl implements JobSeekerBo{
	
public static final Logger logger = LoggerFactory.getLogger(JobSeekerBoImpl.class);

	
	@Autowired
	JobSeekerDao jobseekerdao;
	
	/*@Autowired
	JobSeekerEmailService jobseekerEmaiservice;
	
	*/
	
	public String validateJobSeekerUserName(JobSeekerDto jobseekerdto) {		
		String result =jobseekerdao.userExitCheck(jobseekerdto.getEmailID());
		return result;

	}

	public JobSeekerDto registerJobSeeker(JobSeekerDto jobseeker) throws GLGException {
		String result=null;
		try {
			logger.info("Before Calling Job Seeker DAO for registerJobSeeker Email Validation");
			result =jobseekerdao.userExitCheck(jobseeker.getEmailID());
			logger.info("Successfully Called Job Seeker DAO for registerJobSeeker Email Validation");
			if(result=="success") {
				
				JobSeekerEmailService jobseeEmail = () -> {
					logger.info("Called Interface 1");

				};
				Runnable ref =  () -> {
					logger.info("Called Interface 2");
				};
				
				ref.run();
				
				/*TestLambda obj = new TestLambda();
				obj.run();
				*/
				
			/*	Runnable r = new PushEmail(jobseeker);
				new Thread(r).start();*/
				//jobseeEmail.
				boolean emailstatus=jobseeEmail.jobSeekerRegistration(jobseeker); 	
				
				
				if(emailstatus==true) {
					logger.info("Valid Email ID");
					logger.info("Before Calling Job Seeker DAO for registerJobSeeker");
					jobseeker=jobseekerdao.registerJobSeeker(jobseeker);
					logger.info("Successfully Called Job Seeker DAO for registerJobSeeker");
				} else {
					jobseeker.setStatus("InvalidEmailID");
					logger.info("Invalid Email ID");
				}
		
			} 
			else if(result=="failure") {
				jobseeker.setStatus("emailIDExists");
			}
			else {
				jobseeker.setStatus("unknownError");
			}
			
			return jobseeker;
		} catch(Exception e) {
			logger.info("LoginJobSeeker Exception ------->"+e.getMessage());

		} finally {
			
		}
		return jobseeker;
	}
	
	//------------ Login JobSeeker Bo Calling -------------
	public JobSeekerDto loginJobSeeker(JobSeekerDto jobseeker) {
		logger.info("[BO] LoginJobSeeker ------------->");
		//List<JobSeeker> result=null;
		try {
			//jobseeker.setId(1);
			jobseeker = jobseekerdao.loginJobSeeker(jobseeker);
		}catch(Exception e) {
			logger.info("LoginJobSeeker Exception ------->"+e.getMessage());
		}
		finally {
			
		}
		return jobseeker;
	}
	
}
