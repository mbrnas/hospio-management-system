package org.hospital.service.staff;

import org.hospital.dao.staff.StaffDao;
import org.hospital.model.staff.Staff;

import java.util.List;


public class StaffService {

    private final StaffDao staffDao;

    public StaffService(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public List<Staff> getAllStaff() {
        return staffDao.getAllStaff();
    }

    public Staff getStaffById(int id) {
        return staffDao.getStaffById(id);
    }

    public void saveStaff(Staff staff) {
        staffDao.saveStaff(staff);
    }

    public void updateStaff(Staff staff) {
        staffDao.updateStaff(staff);
    }

    public void deleteStaff(int id) {
        staffDao.deleteStaff(id);
    }
}
