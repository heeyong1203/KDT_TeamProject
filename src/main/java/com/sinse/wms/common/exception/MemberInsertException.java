package com.sinse.wms.common.exception;

public class MemberInsertException extends RuntimeException{

	public MemberInsertException() {
		super();
	}

	public MemberInsertException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MemberInsertException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemberInsertException(String message) {
		super(message);
	}

	public MemberInsertException(Throwable cause) {
		super(cause);
	}
}
