package com.ggl.dao;



import java.util.ArrayList;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ggl.dto.EmployerDto;
import com.ggl.model.EmployerDetails;
import com.ggl.model.EmployerLogin;
import com.ggl.util.GLGException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



@Repository
@Singleton
public class EmployerDaoImpl implements EmployerDao {
	public static final Logger logger = LoggerFactory.getLogger(EmployerDaoImpl.class);

	@PersistenceContext(unitName="ggl-pu")
	private EntityManager entityManager2;
	
	// Email ID Validation / Exist Checking
	@Transactional(value="transactionManager")
	public String userExitCheck(String emailID) {
		logger.info("------------------ Inside userExitCheck DAO ---------------");
		Query query;
		String returnValue="failure";
		try {
			query=entityManager2.createQuery("from EmployerLogin where username=?");
			query.setParameter(1, emailID);
			EmployerLogin employerlogin=(EmployerLogin)query.getSingleResult();	
			if(employerlogin.getUsername().isEmpty()) {
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
	public EmployerDto registerEmployer(EmployerDto employer) throws GLGException{
		logger.info("Employer Name -->"+employer.getName());
		logger.info("Employer Phone Number  -->"+employer.getPhoneNumber());
		ArrayList<EmployerDetails> emplist;
		try {
			EmployerLogin emplogin = new EmployerLogin();
			emplogin.setUsername(employer.getUsername()); // Same as Email ID
			emplogin.setPassword(employer.getPassword());
			emplogin.setStatus("Registered"); 
			
			EmployerDetails empyr = new EmployerDetails();
			empyr.setEmployer_name(employer.getName());
			empyr.setEmployer_phoneNumber(employer.getPhoneNumber());
			empyr.setEmployer_emailID(employer.getUsername()); // Same as Email ID
			empyr.setEmployer_password(employer.getPassword());
			empyr.setEmployer_country(employer.getCountry());
			empyr.setEmployer_status("Registered");
			empyr.setEmployerLogin(emplogin); 
			
			emplist = new ArrayList<EmployerDetails>();
			emplist.add(empyr);
			emplogin.setEmployerDetails(emplist);
			logger.info("Add EmployeeDetails Values into EmployeeLogin -->");		
			entityManager2.persist(emplogin);	
			logger.info("EmployeeLogin values added to the database -->");
			employer.setStatus("success");
			logger.info("Status -->"+employer.getStatus());
		} 
		
		catch(Exception e) {
			employer.setStatus("unknownError");
			logger.error("Employer DAO Exception -->"+e.getMessage());

		}
		
		finally {
			
		}
		return employer;
	}
	
	@Transactional(value="transactionManager")
	public EmployerDto loginEmployer(EmployerDto employer){
		logger.info("-------------- Login Employer DAO Calling -----------------"); 
		Query query = null;
		try {
				query=entityManager2.createQuery("from EmployerLogin where username=?");
				query.setParameter(1, employer.getUsername());
				EmployerLogin rs=(EmployerLogin)query.getSingleResult();					
				if(rs.getUsername().equalsIgnoreCase(employer.getUsername())) {
					System.out.println("UI Password -->"+employer.getPassword());
					System.out.println("DB Password -->"+rs.getPassword());
					System.out.println("UserName Status Success -->");
					if(rs.getPassword().equalsIgnoreCase(employer.getPassword())) {
						employer.setStatus("success");
						System.out.println("Success Status -->"+employer.getStatus());
					}
					else {
						System.out.println(" Password Status -->"+employer.getStatus());
						employer.setStatus("Password is Not Match Please try again");
					}
				}
				else {
					System.out.println("EmailId Exists Status -->"+employer.getStatus());
					employer.setStatus("UserID is Not Match Please try again");
				}			
		}
		catch (NoResultException nre){
			employer.setStatus("UserID Not Exist Please enter valid Email ID");
			logger.error("Exception -->"+nre.getMessage());
		}
		catch(Exception e) {
			employer.setStatus("unknowerror");
			System.out.println("DAO LoginEmpoyer Exception ------------->"+e.getMessage());
		}
		finally {
			
		}
		return employer;
	}
	
}
