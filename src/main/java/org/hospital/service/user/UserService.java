package org.hospital.service.user;

import org.hospital.dao.patient.PatientDaoImpl;
import org.hospital.dao.user.UserDaoImpl;
import org.hospital.exception.hospital.DatabaseOperationException;
import org.hospital.model.patient.Patient;
import org.hospital.model.user.User;
import org.hospital.model.user.UserRole;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserService {

    private final UserDaoImpl userDao;
    private final PatientDaoImpl patientDao = new PatientDaoImpl();

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    public UserService(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    public void registerUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setRole(UserRole.PATIENT);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        userDao.saveUser(user);
    }

    public boolean authenticateUser(String username, String password) {
        User user = findUserByUsername(username);
        return user != null && BCrypt.checkpw(password, user.getPassword());
    }

    public List<User> findAllPatients() throws DatabaseOperationException {
        List<User> allUsers = userDao.findAllUsers();
        List<Patient> allPatients = patientDao.getAllPatients();
        List<Integer> patientUserIds = allPatients.stream().map(Patient::getUserId).collect(Collectors.toList());

        return allUsers.stream()
                .filter(user -> user.getRole() == UserRole.PATIENT)
                .filter(user -> !patientUserIds.contains(user.getUserId()))
                .collect(Collectors.toList());
    }

    public int getPatientIdForUser(int userId) {
        return userDao.getPatientIdForUser(userId);
    }
}
