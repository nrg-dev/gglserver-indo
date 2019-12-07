package com.ggl.dao;



import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ggl.dto.JobSeekerDto;
import com.ggl.model.JobSeekerDetails;
import com.ggl.util.GLGException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



@Repository
@Singleton
public class JobSeekerDaoImpl implements JobSeekerDao {
	public static final Logger logger = LoggerFactory.getLogger(JobSeekerDaoImpl.class);

	@PersistenceContext(unitName="ggl-pu")
	private EntityManager entityManager1;
	
	// Email ID Validation / Exist Checking
	@Transactional(value="transactionManager")
	public String userExitCheck(String emailID) {
		System.out.println("------------------ Inside userExitCheck DAO ---------------");
		Query query;
		String returnValue="failure";
		try {
			query=entityManager1.createQuery("from JobSeekerDetails where Jobseeker_emailID=?");
			query.setParameter(1, emailID);
			JobSeekerDetails jobseeker=(JobSeekerDetails)query.getSingleResult();	
			if(jobseeker.getJobseeker_emailID().isEmpty()) {
				returnValue="success";
			}
			else{
				returnValue = "failure";
			}
			
			//return returnValue;
		}
		catch (NoResultException nre){
			returnValue="success";
			logger.error("Exception -->"+nre.getMessage());
			//Ignore this because as per your logic this is ok!
			}
		catch(Exception e) {
			returnValue = "unknownError";
			logger.error("Exception -->"+e.getMessage());

			//return returnValue;
		}
		finally {
			
		}
		return returnValue;
	}
	
	@Transactional(value="transactionManager")
	public JobSeekerDto registerJobSeeker(JobSeekerDto jobseeker) throws GLGException{
		System.out.println("Name -->"+jobseeker.getName());
		System.out.println("Phone Number  -->"+jobseeker.getPhoneNumber());
		try {
			JobSeekerDetails jobsekr = new JobSeekerDetails();
			//jobsekr.set
			jobsekr.setJobseeker_name(jobseeker.getName());
			jobsekr.setJobseeker_phoneNumber(jobseeker.getPhoneNumber());
			jobsekr.setJobseeker_emailID(jobseeker.getEmailID());
			jobsekr.setJobseeker_password(jobseeker.getPassword());
			jobsekr.setJobseeker_country(jobseeker.getCountry());
			jobsekr.setJobseeker_status("Registered");
			entityManager1.persist(jobsekr);	
			jobseeker.setStatus("success");
		} 
		
		catch(Exception e) {
			jobseeker.setStatus("unknownError");
			logger.error("Exception -->"+e.getMessage());

		}
		
		finally {
			
		}
		return jobseeker;
	}
	
	@Transactional(value="transactionManager")
	public JobSeekerDto loginJobSeeker(JobSeekerDto jobseeker){
		System.out.println("-------------- Login JobSeeker DAO Calling -----------------"); 
		Query query = null;
		//String tmppwd=jobseeker.getPassword();
		try {
				query=entityManager1.createQuery("from JobSeekerDetails where Jobseeker_emailID=?");
				query.setParameter(1, jobseeker.getUsername());
				JobSeekerDetails rs=(JobSeekerDetails)query.getSingleResult();					
				if(rs.getJobseeker_emailID().equalsIgnoreCase(jobseeker.getUsername())) {
					System.out.println("UI Password -->"+jobseeker.getPassword());
					System.out.println("DB Password -->"+rs.getJobseeker_password());
					
				 if(rs.getJobseeker_password().equalsIgnoreCase(jobseeker.getPassword())) {
						jobseeker.setStatus("success");
					}
					else {
						jobseeker.setStatus("Password is Not Match Please try again");
					}
					
				}
				else {
					jobseeker.setStatus("UserID is Not Match Please try again");
				}
				//result=(List<JobSeeker>)query.getResultList();
			
		}
		catch (NoResultException nre){
			jobseeker.setStatus("UserID Not Exist Please enter valid Email ID");
			//returnValue="success";
			logger.error("Exception -->"+nre.getMessage());
			//Ignore this because as per your logic this is ok!
			}
		catch(Exception e) {
			jobseeker.setStatus("unknowerror");
			System.out.println("DAO LoginJobseeker Exception ------------->"+e.getMessage());
		}
		finally {
			
		}
		return jobseeker;
	}
	
}
