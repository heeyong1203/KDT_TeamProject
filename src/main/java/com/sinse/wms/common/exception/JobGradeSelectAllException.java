package com.sinse.wms.common.exception;

public class JobGradeSelectAllException extends RuntimeException {
    public JobGradeSelectAllException(String message) {
        super(message);
    }

    public JobGradeSelectAllException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobGradeSelectAllException(Throwable cause) {
        super(cause);
    }
}
