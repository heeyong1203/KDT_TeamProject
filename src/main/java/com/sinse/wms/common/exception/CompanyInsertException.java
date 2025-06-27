package com.sinse.wms.common.exception;

public class CompanyInsertException extends RuntimeException{

	public CompanyInsertException() {
		super();
	}

	public CompanyInsertException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanyInsertException(String message) {
		super(message);
	}

	public CompanyInsertException(Throwable cause) {
		super(cause);
	}
}
