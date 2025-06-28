package com.sinse.wms.common.exception;

public class CategorySelectException extends RuntimeException{

	public CategorySelectException() {
		super();
	}

	public CategorySelectException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategorySelectException(String message) {
		super(message);
	}

	public CategorySelectException(Throwable cause) {
		super(cause);
	}
}
