package com.sinse.wms.common.exception;

public class LocationDeleteException extends RuntimeException{

	public LocationDeleteException() {
		super();
	}

	public LocationDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocationDeleteException(String message) {
		super(message);
	}

	public LocationDeleteException(Throwable cause) {
		super(cause);
	}
}
