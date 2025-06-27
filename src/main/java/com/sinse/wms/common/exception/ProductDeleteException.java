package com.sinse.wms.common.exception;

public class ProductDeleteException extends RuntimeException{

	public ProductDeleteException() {
		super();
	}

	public ProductDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductDeleteException(String message) {
		super(message);
	}

	public ProductDeleteException(Throwable cause) {
		super(cause);
	}
}
