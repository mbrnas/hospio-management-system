package org.hospital.service.department;

import org.hospital.dao.department.DepartmentDao;
import org.hospital.model.department.Department;

import java.util.List;

public class DepartmentService {
    private final DepartmentDao departmentDao;

    public DepartmentService(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public List<Department> getAllDepartments() {
        return departmentDao.getAllDepartments();
    }

    public Department getDepartmentById(int id) {
        return departmentDao.getDepartmentById(id);
    }

    public void saveDepartment(Department department) {
        departmentDao.saveDepartment(department);
    }

    public void updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
    }

    public void deleteDepartment(int id) {
        departmentDao.deleteDepartment(id);
    }
}