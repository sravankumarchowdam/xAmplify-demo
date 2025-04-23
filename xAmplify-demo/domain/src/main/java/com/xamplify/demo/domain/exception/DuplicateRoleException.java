package com.xamplify.demo.domain.exception;

public class DuplicateRoleException extends RuntimeException {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateRoleException(String message) {
	        super(message);
	    }

}
