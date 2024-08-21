package org.hospital.dao.user;

import org.hospital.model.user.User;

import java.util.List;

public interface UserDao {
    User findUserByUsername(String username);
    boolean saveUser(User user);
    int getPatientIdForUser(int userId);


    List<User> findAllUsers();
}
