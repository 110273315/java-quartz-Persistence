package com.ai.quartz;

public class InvokeResult {
	
	private Object data;
	
	private String errorMessage;
	
	private boolean hasErrors;
	
	public static InvokeResult success(Object data) {
		InvokeResult result = new InvokeResult();
		result.data = data;
		result.hasErrors = false;
		return result;
	}
	
	public static InvokeResult success() {
		InvokeResult result = new InvokeResult();
		result.hasErrors = false;
		return result;
	}
	
	public static InvokeResult failure(String message) {
		InvokeResult result = new InvokeResult();
		result.errorMessage = message;
		result.hasErrors = true;
		return result;
	}
	
	public Object getData() {
		return data;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public boolean isHasErrors() {
		return hasErrors;
	}
	
	public boolean isSuccess() {
		return !hasErrors;
	}
}
