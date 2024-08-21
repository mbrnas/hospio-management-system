package org.hospital.dao.staff;


import org.hospital.model.staff.Staff;

import java.util.List;

public interface StaffDao {
    List<Staff> getAllStaff();

    Staff getStaffById(int id);

    void saveStaff(Staff staff);

    void updateStaff(Staff staff);

    void deleteStaff(int id);
}
