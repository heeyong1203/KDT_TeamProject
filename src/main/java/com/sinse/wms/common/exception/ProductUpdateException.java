package com.sinse.wms.common.exception;

public class ProductUpdateException extends RuntimeException{

	public ProductUpdateException() {
		super();
	}

	public ProductUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductUpdateException(String message) {
		super(message);
	}

	public ProductUpdateException(Throwable cause) {
		super(cause);
	}
}
