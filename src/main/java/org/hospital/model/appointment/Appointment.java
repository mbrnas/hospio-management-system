package org.hospital.model.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private int appointmentId;
    private Integer patientId;
    private String patientName;
    private Integer doctorId;
    private String doctorName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentDate;
    private String notes;
}
