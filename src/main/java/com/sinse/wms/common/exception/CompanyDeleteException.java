package com.sinse.wms.common.exception;

public class CompanyDeleteException extends RuntimeException{

	public CompanyDeleteException() {
		super();
	}

	public CompanyDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanyDeleteException(String message) {
		super(message);
	}

	public CompanyDeleteException(Throwable cause) {
		super(cause);
	}
}
