package com.sinse.wms.common.exception;

public class CategoryUpdateException extends RuntimeException{

	public CategoryUpdateException() {
		super();
	}

	public CategoryUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategoryUpdateException(String message) {
		super(message);
	}

	public CategoryUpdateException(Throwable cause) {
		super(cause);
	}
}
