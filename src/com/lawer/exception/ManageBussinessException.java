package com.lawer.exception;

public class ManageBussinessException extends RuntimeException {
	private static final long serialVersionUID = 538922474277376456L;

    private String backUrl;
    
    
	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public ManageBussinessException() {
		super();
	}

	public ManageBussinessException(String message) {
		super(message);
	}
	
	public ManageBussinessException(String message, String backUrl) {
		super(message);
		this.backUrl = backUrl;
	}
}
