package com.sinse.wms.common.exception;

public class LocationSelectException extends RuntimeException{

	public LocationSelectException() {
		super();
	}

	public LocationSelectException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocationSelectException(String message) {
		super(message);
	}

	public LocationSelectException(Throwable cause) {
		super(cause);
	}
}
