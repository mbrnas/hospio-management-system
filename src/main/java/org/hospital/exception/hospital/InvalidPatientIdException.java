package org.hospital.exception.hospital;

public class InvalidPatientIdException extends HospitalException {
    public InvalidPatientIdException(int id) {
        super("Invalid patient ID: " + id);
    }
}