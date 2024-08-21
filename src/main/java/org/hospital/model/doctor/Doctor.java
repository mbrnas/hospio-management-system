package org.hospital.model.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private int doctorId;
    private String firstName;
    private String lastName;
    private int specializationId;
    private String contactNumber;
    private String email;
    private String address;
}
