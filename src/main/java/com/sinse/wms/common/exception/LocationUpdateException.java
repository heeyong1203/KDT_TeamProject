package com.sinse.wms.common.exception;

public class LocationUpdateException extends RuntimeException{

	public LocationUpdateException() {
		super();
	}

	public LocationUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocationUpdateException(String message) {
		super(message);
	}

	public LocationUpdateException(Throwable cause) {
		super(cause);
	}
}
