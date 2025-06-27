package com.sinse.wms.common.exception;

public class ProductSelectException extends RuntimeException{

	public ProductSelectException() {
		super();
	}

	public ProductSelectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductSelectException(String message) {
		super(message);
	}

	public ProductSelectException(Throwable cause) {
		super(cause);
	}
}
