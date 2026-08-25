package com.smart.dto;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {
	
	@JsonProperty("customError")
    private String customError="default error";
	
	@JsonProperty("message")
    private String message="Default message";
	
	@JsonProperty("stackTrace")
    private String stackTrace="Default stack";
	
	@JsonProperty("status")
    private HttpStatus status = HttpStatus.BAD_REQUEST;
   
	@JsonProperty("data")
    private Object data = null;


	

	
   




	public ErrorMessage(String message, HttpStatus status, Object data) {
		super();
		this.message = message;
		this.status = status;
		this.data = data;
	}




	public ErrorMessage(String customError, String message, String stackTrace, HttpStatus status) {
		super();
		this.customError = customError;
		this.message = message;
		this.stackTrace = stackTrace;
		this.status = status;
	}




	public ErrorMessage(String customError, Exception e) {
		super();
		this.customError = customError;
		this.message = e.getMessage();
		this.stackTrace = getStackTraceAsString(e);
	}

    
    

	public ErrorMessage(String customError, String stackTrace, HttpStatus status) {
		super();
		this.customError = customError;
		this.stackTrace = stackTrace;
		this.status = status;
	}




	public ErrorMessage(String customError, Exception e, HttpStatus status) {
		super();
		this.customError = customError;
		this.message = e.getMessage();
		this.stackTrace = getStackTraceAsString(e);
		this.status = status;
	}










	public ErrorMessage(String customError, HttpStatus notFound) {
		this.customError = customError;
		this.status = notFound;
	}




	public ErrorMessage(String customError) {
		this.customError = customError;
	}




	private String getStackTraceAsString(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }




	@Override
	public String toString() {
		return "ErrorMessage [customError=" + customError + ", message=" + message + ", stackTrace=" + stackTrace
				+ ", status=" + status + "]";
	}




	




	
    
    
    
    

}
