package org.hospital.exception;

import java.rmi.AccessException;

public class UnauthorizedAccessException extends AccessException {
    public UnauthorizedAccessException() {
        super("Unauthorized access");
    }
}
