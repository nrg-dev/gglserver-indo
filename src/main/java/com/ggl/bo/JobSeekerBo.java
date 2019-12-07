package com.ggl.bo;

import com.ggl.dto.JobSeekerDto;
import com.ggl.util.GLGException;

/*import java.util.ArrayList;
import java.util.HashMap;
import com.ggl.dto.GLGMem;
import com.ggl.dto.Member;
import com.ggl.dto.User;
import com.ggl.util.GLGException;*/

public interface JobSeekerBo {
	public JobSeekerDto registerJobSeeker(JobSeekerDto jobseeker) throws GLGException;	
	//--------- Login Job Seekers -------------
	public JobSeekerDto loginJobSeeker(JobSeekerDto jobseeker);
	public String validateJobSeekerUserName(JobSeekerDto jobseekerdto);


}
