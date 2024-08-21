package org.hospital.model.staff;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    private int staffId;
    private String firstName;
    private String lastName;
    private String role;
    private Integer departmentId;
    private String departmentName;
    private String contactNumber;
    private String address;
}

