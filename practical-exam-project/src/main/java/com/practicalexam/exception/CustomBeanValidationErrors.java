package com.practicalexam.exception;

import java.util.HashMap;
import java.util.Map;

public class CustomBeanValidationErrors {
	private Boolean error;
	private String message;
	private Map<String, String> errorDetails = new HashMap<>();

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(Map<String, String> errorDetails) {
		this.errorDetails = errorDetails;
	}

}
