package com.sinse.wms.common.exception;

public class ProductUnitDeleteException extends RuntimeException{

	public ProductUnitDeleteException() {
		super();
	}

	public ProductUnitDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductUnitDeleteException(String message) {
		super(message);
	}

	public ProductUnitDeleteException(Throwable cause) {
		super(cause);
	}
}
