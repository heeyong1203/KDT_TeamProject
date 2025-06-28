package com.sinse.wms.common.exception;

public class ProductUnitUpdateException extends RuntimeException{

	public ProductUnitUpdateException() {
		super();
	}

	public ProductUnitUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductUnitUpdateException(String message) {
		super(message);
	}

	public ProductUnitUpdateException(Throwable cause) {
		super(cause);
	}
}
