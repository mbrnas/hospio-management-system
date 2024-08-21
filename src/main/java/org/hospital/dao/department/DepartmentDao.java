package org.hospital.dao.department;


import org.hospital.model.department.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> getAllDepartments();

    Department getDepartmentById(int id);

    void saveDepartment(Department department);

    void updateDepartment(Department department);

    void deleteDepartment(int id);
}
