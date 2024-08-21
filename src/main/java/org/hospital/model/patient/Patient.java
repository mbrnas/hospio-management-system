package org.hospital.model.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private int patientId;
    private String firstName;
    private String lastName;
    private int userId;
}
