package org.hospital.exception.hospital;


public class DatabaseOperationException extends HospitalException {
    public DatabaseOperationException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}

