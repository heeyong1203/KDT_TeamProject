package com.sinse.wms.common.exception;

public class LocationInsertException extends RuntimeException{

	public LocationInsertException() {
		super();
	}

	public LocationInsertException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocationInsertException(String message) {
		super(message);
	}

	public LocationInsertException(Throwable cause) {
		super(cause);
	}
}
