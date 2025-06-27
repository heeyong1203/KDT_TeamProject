package com.sinse.wms.common.exception;

public class ProductInsertException extends RuntimeException{

	public ProductInsertException() {
		super();
	}

	public ProductInsertException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductInsertException(String message) {
		super(message);
	}

	public ProductInsertException(Throwable cause) {
		super(cause);
	}
}
