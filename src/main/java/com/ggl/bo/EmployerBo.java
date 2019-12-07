package com.ggl.bo;


import com.ggl.dto.EmployerDto;
import com.ggl.dto.JobSeekerDto;
import com.ggl.util.GLGException;


public interface EmployerBo {
	public String validateEmployerUserName(JobSeekerDto jobseekerdto);
	public EmployerDto loginEmployer(EmployerDto employer);

	public EmployerDto registerEmployer(EmployerDto employer) throws GLGException;	
	//--------- Login Job Seekers -------------

}
