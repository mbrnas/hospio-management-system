package org.hospital.model.prescription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    private int prescriptionId;
    private Integer recordId;
    private Integer medicationId;
    private String dosage;
    private String frequency;
    private String notes;
}
