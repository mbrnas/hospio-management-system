package org.hospital.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String username;
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String email;
}
