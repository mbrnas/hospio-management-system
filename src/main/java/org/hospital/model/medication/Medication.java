package org.hospital.model.medication;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medication {
    private int medicationId;
    private String medicationName;
    private String description;
}
