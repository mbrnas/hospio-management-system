package org.hospital.config;

import org.hospital.controller.IController;
import org.hospital.controller.appointment.AppointmentController;
import org.hospital.controller.department.DepartmentController;
import org.hospital.controller.doctor.DoctorController;
import org.hospital.controller.invoice.InvoiceController;
import org.hospital.controller.medical_record.MedicalRecordController;
import org.hospital.controller.medication.MedicationController;
import org.hospital.controller.patient.PatientController;
import org.hospital.controller.prescription.PrescriptionController;
import org.hospital.controller.specialization.SpecializationController;
import org.hospital.controller.staff.StaffController;
import org.hospital.controller.user.UserController;
import org.hospital.dao.appointment.AppointmentDaoImpl;
import org.hospital.dao.department.DepartmentDaoImpl;
import org.hospital.dao.doctor.DoctorDaoImpl;
import org.hospital.dao.invoice.InvoiceDaoImpl;
import org.hospital.dao.medical_records.MedicalRecordDaoImpl;
import org.hospital.dao.medication.MedicationDaoImpl;
import org.hospital.dao.patient.PatientDaoImpl;
import org.hospital.dao.prescription.PrescriptionDaoImpl;
import org.hospital.dao.specialization.SpecializationDaoImpl;
import org.hospital.dao.staff.StaffDaoImpl;
import org.hospital.dao.user.UserDaoImpl;
import org.hospital.database.DatabaseConfig;
import org.hospital.model.staff.Staff;
import org.hospital.service.appointment.AppointmentService;
import org.hospital.service.department.DepartmentService;
import org.hospital.service.doctor.DoctorService;
import org.hospital.service.invoice.InvoiceService;
import org.hospital.service.medical_record.MedicalRecordService;
import org.hospital.service.medication.MedicationService;
import org.hospital.service.patient.PatientService;
import org.hospital.service.prescription.PrescriptionService;
import org.hospital.service.specialization.SpecializationService;
import org.hospital.service.staff.StaffService;
import org.hospital.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DependencyInjector {
    private final DatabaseConfig dbConfig;

    private final Logger logger = LoggerFactory.getLogger(DependencyInjector.class);


    public DependencyInjector(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public List<IController> getControllers() {
        List<IController> controllers = new ArrayList<>();
        controllers.add(new PatientController(new PatientService(new PatientDaoImpl())));
        controllers.add(new AppointmentController(new AppointmentService(new AppointmentDaoImpl())));
        controllers.add(new DepartmentController(new DepartmentService(new DepartmentDaoImpl())));
        controllers.add(new DoctorController(new DoctorService(new DoctorDaoImpl())));
        controllers.add(new InvoiceController(new InvoiceService(new InvoiceDaoImpl())));
        controllers.add(new MedicalRecordController(new MedicalRecordService(new MedicalRecordDaoImpl())));
        controllers.add(new MedicationController(new MedicationService(new MedicationDaoImpl())));
        controllers.add(new PrescriptionController(new PrescriptionService(new PrescriptionDaoImpl())));
        controllers.add(new StaffController(new StaffService(new StaffDaoImpl())));
        controllers.add(new UserController(new UserService(new UserDaoImpl())));
        controllers.add(new SpecializationController(new SpecializationService(new SpecializationDaoImpl())));
        logger.info("Controllers injected");
        return controllers;
    }
}
