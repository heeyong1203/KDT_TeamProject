package com.sinse.wms.common.exception;

public class CompanySelectException extends RuntimeException{

	public CompanySelectException() {
		super();
	}

	public CompanySelectException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanySelectException(String message) {
		super(message);
	}

	public CompanySelectException(Throwable cause) {
		super(cause);
	}
}
