package com.ggl.service;

import java.io.IOException;
import java.util.ArrayList;

/*import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;*/

//import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*import java.io.IOException;
*/import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;*/
//import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ggl.bo.EmployerBo;

/*import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
*/
//import org.springframework.web.bind.annotation.RestController;

import com.ggl.bo.JobSeekerBo;
import com.ggl.dto.EmployerDto;
import com.ggl.dto.JobSeekerDto;
import com.ggl.util.Industry;
import com.google.gson.Gson;

@RestController
@SpringBootApplication
public class EndPointService implements Filter{
	
public static final Logger logger = LoggerFactory.getLogger(EndPointService.class);

	
	@Autowired
	JobSeekerBo jobseekerbo;
	
	@Autowired
	EmployerBo employerbo;
	
	
	/*enum Industry 
    { 
		CivilEngineering, SoftwareDevelopment, Others; 
    } */

	enum Day 
	{ 
	    SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY; 
	} 
	
	/*enum Month 
	{ 
		January,February,March,April,May,June,July,August,September,October,November,December; 
	} */
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
	    chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}
	
	ArrayList<String> industryList;
	
	// http://35.162.40.190:80-
	// http://35.162.40.190:80
	
	
	// ---------------------------------------- Common Method ----------------------------------------
	
	//----------------- Job Seeker and Employer Check user -------------------------	
		@CrossOrigin(origins = "http://35.162.40.190:80")
		@RequestMapping(value="/validateUserName",method=RequestMethod.GET)
		public ResponseEntity<?>  validateUserName(@RequestParam String username,@RequestParam String type) {
			logger.info("[UI Application-Checkuser] User Name ---------------->"+username);
			JobSeekerDto jobseekerdto=null;
			String result="";
			try {
				jobseekerdto = new JobSeekerDto();
				jobseekerdto.setUsername(username);
				if(type.equalsIgnoreCase("jobseeker")) {
					logger.info("Before Calling Job seeker Email/UserName Validation");
					result = jobseekerbo.validateJobSeekerUserName(jobseekerdto);
					logger.info("Successfully Called Job seeker Email/UserName Validation");

				} else if(type.equalsIgnoreCase("employer")) {
					logger.info("Before Calling Employer Email/UserName Validation");
					result = employerbo.validateEmployerUserName(jobseekerdto);
					logger.info("Successfully Called Employer Email/UserName Validation");


				}
				jobseekerdto.setStatus(result);
			}catch(Exception e) {
				jobseekerdto.setStatus("Network Error Please try again");
				logger.info("Exception ------------->"+e.getMessage());
			}finally {
				
			}
			return new ResponseEntity<JobSeekerDto>(jobseekerdto, HttpStatus.OK);
		  }
	
	// Job Seeker and Employer Load Industry 	
	@CrossOrigin(origins = "http://35.162.40.190:80")
	@RequestMapping(value="/getIndustry",method=RequestMethod.GET)
	public ResponseEntity<?> getIndustry()
	{
	   try {
		   logger.info("Industry ---->"+Industry.CivilEngineering);
			logger.info("Server side getCountry Called");
			industryList = new ArrayList<String>();
			industryList.add(Industry.CivilEngineering.toString());
			industryList.add(Industry.SoftwareDevelopment.toString());
			industryList.add(Industry.Others.toString());

			}catch(Exception e){
			logger.info("Exception ------------->"+e.getMessage());
		}finally{
			
		}
		return new ResponseEntity<ArrayList<String>>(industryList, HttpStatus.CREATED);

	}
	
	
	
//------------------------------Job Seeker start ------------------------------------------------------	
	
	// Job Seeker Login	
	@GetMapping("/jobseekerslogin")
	@Produces(MediaType.APPLICATION_JSON)
	@CrossOrigin(origins = "http://35.162.40.190:80")
    public JobSeekerDto getJobSeekersLogin(String username,String password) throws JSONException{
		logger.info("User Name ..............."+username);
		logger.info("Password  ..............."+password);
		JobSeekerDto jobseeker = null; 
		try{
			jobseeker = new JobSeekerDto();
			jobseeker.setUsername(username); // Email ID
			jobseeker.setPassword(password);
			logger.info("Before Calling Job Seeker BO for LoginJobSeeker");
			jobseeker=jobseekerbo.loginJobSeeker(jobseeker);
			logger.info("Successfully Called Job Seeker BO for LoginJobSeeker");
		}catch(Exception e) {
			jobseeker.setStatus("Network Error Please try again");
			logger.info("Exception ------------->"+e.getMessage());
		}finally {
			
		}
		return jobseeker;

    }  
	
		// Job Seeker Register Method	
		@PostMapping("/resgisterJobSeeker")
		@CrossOrigin(origins = "http://35.162.40.190:80")
		public String resgisterJobSeeker(@RequestBody JobSeekerDto jobseeker) throws JSONException {
			  Gson gson = null;// new Gson();
			 logger.debug("Name ....."+jobseeker.getName());
			 logger.debug("Phone Number ....."+jobseeker.getPhoneNumber());
			 logger.debug("Email Address ....."+jobseeker.getEmailID());
			 logger.debug("User Name ....."+jobseeker.getUsername());
			 logger.debug("Password ....."+jobseeker.getPassword());
			 logger.debug("Country ....."+jobseeker.getCountry());
			try {		
				logger.info("Before Calling Job Seeker BO for registerJobSeeker");
				jobseeker=jobseekerbo.registerJobSeeker(jobseeker);
				logger.info("Successfully Called Job Seeker BO for registerJobSeeker");
				gson = new Gson();
			}
			catch(Exception e) {
			logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(jobseeker);
		}
		
	
//------------------------------Employer start ------------------------------------------------------
	
		// Employer Login method
		@GetMapping("/employerlogin")
		@Produces(MediaType.APPLICATION_JSON)
		@CrossOrigin(origins = "http://35.162.40.190:80")
	    public EmployerDto getEmployerLogin(String username,String password) throws JSONException{
			logger.info("User Name ..............."+username);
			logger.info("Password  ..............."+password);
			EmployerDto employee = null; 
			try{
				employee = new EmployerDto();
				employee.setUsername(username); // Email ID
				employee.setPassword(password);
				logger.info("Before Calling Employer BO for LoginJobSeeker");
				employee=employerbo.loginEmployer(employee);
				logger.info("Successfully Called Employer BO for LoginJobSeeker");
			}catch(Exception e) {
				employee.setStatus("Network Error Please try again");
				logger.info("Exception ------------->"+e.getMessage());
			}finally {
				
			}
			return employee;

	    }  
		
		// Employer Register Method	
		@PostMapping("/resgisterEmployer")
		@CrossOrigin(origins = "http://35.162.40.190:80")
		public String resgisterEmployer(@RequestBody EmployerDto empdto) throws JSONException {
			  Gson gson = null;// new Gson();
			 logger.debug("Name ....."+empdto.getName());
			 logger.debug("Phone Number ....."+empdto.getPhoneNumber());
			// logger.debug("Email Address ....."+empdto.getEmailID());
			 logger.debug("User Name / Email ID ....."+empdto.getUsername());
			 logger.debug("Password ....."+empdto.getPassword());
			 logger.debug("Country ....."+empdto.getCountry());
			try {		
				logger.info("Before Calling Job Seeker BO for registerJobSeeker");
				empdto=employerbo.registerEmployer(empdto);
				logger.info("Successfully Called Job Seeker BO for registerJobSeeker");
				gson = new Gson();
			}
			catch(Exception e) {
			logger.error("Exception -->"+e.getMessage());
			}
			return gson.toJson(empdto);
		}
}
