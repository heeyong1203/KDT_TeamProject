package com.sinse.wms.common.exception;

public class MemberUpdateException extends RuntimeException{

	public MemberUpdateException() {
		super();
	}

	public MemberUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemberUpdateException(String message) {
		super(message);
	}

	public MemberUpdateException(Throwable cause) {
		super(cause);
	}
}
