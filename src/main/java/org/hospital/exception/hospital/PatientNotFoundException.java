package org.hospital.exception.hospital;


public class PatientNotFoundException extends HospitalException {
    public PatientNotFoundException(int id) {
        super("Patient with ID " + id + " not found");
    }
}
