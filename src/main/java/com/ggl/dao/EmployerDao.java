package com.ggl.dao;


import com.ggl.dto.EmployerDto;
import com.ggl.util.GLGException;

public interface EmployerDao {
	public String userExitCheck(String emailID);
	public EmployerDto registerEmployer(EmployerDto employer) throws GLGException;
	public EmployerDto loginEmployer(EmployerDto employer);
}
 