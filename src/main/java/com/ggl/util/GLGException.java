package com.ggl.util;

public class GLGException extends Exception {

	 
		private static final long serialVersionUID = 1L;
		public  String errorMsg;
		public  String errorCode;
		
		public  String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String message ,String errorCode) {
			this.errorCode = errorCode;
		}
		
		 public GLGException(String message) {
		        super(message);
		        setErrorMsg(message);
		    }
		 public GLGException(String message, Throwable throwable) {
		        super(message, throwable);
		    }
		
}
