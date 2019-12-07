package com.ggl.dao;

import com.ggl.dto.JobSeekerDto;
import com.ggl.util.GLGException;

public interface JobSeekerDao {
	public String userExitCheck(String emailID);
	public JobSeekerDto registerJobSeeker(JobSeekerDto jobseeker) throws GLGException;
	public JobSeekerDto loginJobSeeker(JobSeekerDto jobseeker);
}
 