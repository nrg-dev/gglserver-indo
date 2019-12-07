package com.ggl.email;

public class TestLambda implements JobSeekerEmailService,Runnable{

	@Override
	public void test() {
		System.out.println("Functional Interface");
	}
	
	@Override
	public void run() {		
		System.out.println("Run Method is Called...");
	}

}
