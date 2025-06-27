package com.sinse.wms.common.exception;

public class CompanyUpdateException extends RuntimeException{

	public CompanyUpdateException() {
		super();
	}

	public CompanyUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanyUpdateException(String message) {
		super(message);
	}

	public CompanyUpdateException(Throwable cause) {
		super(cause);
	}
}
