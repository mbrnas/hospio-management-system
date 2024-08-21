CREATE TABLE `appointments`
(
    `appointment_id`   INT NOT NULL AUTO_INCREMENT,
    `patient_id`       INT      DEFAULT NULL,
    `doctor_id`        INT      DEFAULT NULL,
    `appointment_date` DATETIME DEFAULT NULL,
    `notes`            TEXT,
    PRIMARY KEY (`appointment_id`),
    FOREIGN KEY (`patient_id`) REFERENCES `patients` (`patient_id`),
    FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`)
);

CREATE TABLE `departments`
(
    `department_id`   INT          NOT NULL AUTO_INCREMENT,
    `department_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`department_id`)
);

CREATE TABLE `doctors`
(
    `doctor_id`      INT          NOT NULL AUTO_INCREMENT,
    `first_name`     VARCHAR(255) NOT NULL,
    `last_name`      VARCHAR(255) NOT NULL,
    `specialization` VARCHAR(255) DEFAULT NULL,
    `contact_number` VARCHAR(20)  DEFAULT NULL,
    `email`          VARCHAR(255) DEFAULT NULL,
    `address`        VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`doctor_id`)
);

CREATE TABLE `invoices`
(
    `invoice_id`   INT NOT NULL AUTO_INCREMENT,
    `patient_id`   INT            DEFAULT NULL,
    `invoice_date` DATETIME       DEFAULT NULL,
    `amount`       DECIMAL(10, 2) DEFAULT NULL,
    `paid`         TINYINT(1)     DEFAULT NULL,
    PRIMARY KEY (`invoice_id`),
    FOREIGN KEY (`patient_id`) REFERENCES `patients` (`patient_id`)
);

CREATE TABLE `medical_records`
(
    `record_id`      INT NOT NULL AUTO_INCREMENT,
    `patient_id`     INT      DEFAULT NULL,
    `admission_date` DATETIME DEFAULT NULL,
    `discharge_date` DATETIME DEFAULT NULL,
    `diagnosis`      TEXT,
    `treatment`      TEXT,
    PRIMARY KEY (`record_id`),
    FOREIGN KEY (`patient_id`) REFERENCES `patients` (`patient_id`)
);

CREATE TABLE `medications`
(
    `medication_id`   INT          NOT NULL AUTO_INCREMENT,
    `medication_name` VARCHAR(255) NOT NULL,
    `description`     TEXT,
    PRIMARY KEY (`medication_id`)
);

CREATE TABLE `patients`
(
    `patient_id`     INT          NOT NULL AUTO_INCREMENT,
    `first_name`     VARCHAR(255) NOT NULL,
    `last_name`      VARCHAR(255) NOT NULL,
    `gender`         VARCHAR(10)  DEFAULT NULL,
    `date_of_birth`  DATE         DEFAULT NULL,
    `contact_number` VARCHAR(20)  DEFAULT NULL,
    `address`        VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`patient_id`)
);

CREATE TABLE `prescription`
(
    `prescription_id` INT NOT NULL AUTO_INCREMENT,
    `record_id`       INT         DEFAULT NULL,
    `medication_id`   INT         DEFAULT NULL,
    `dosage`          VARCHAR(50) DEFAULT NULL,
    `frequency`       VARCHAR(50) DEFAULT NULL,
    `notes`           TEXT,
    PRIMARY KEY (`prescription_id`),
    FOREIGN KEY (`record_id`) REFERENCES `medical_records` (`record_id`),
    FOREIGN KEY (`medication_id`) REFERENCES `medications` (`medication_id`)
);

CREATE TABLE `staff`
(
    `staff_id`       INT          NOT NULL AUTO_INCREMENT,
    `first_name`     VARCHAR(255) NOT NULL,
    `last_name`      VARCHAR(255) NOT NULL,
    `role`           VARCHAR(50)  DEFAULT NULL,
    `department_id`  INT          DEFAULT NULL,
    `contact_number` VARCHAR(20)  DEFAULT NULL,
    `address`        VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`staff_id`),
    FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`)
);

CREATE TABLE `users`
(
    `user_id`  INT                                 NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255)                        NOT NULL UNIQUE,
    `password` VARCHAR(255)                        NOT NULL,
    `role`     ENUM ('admin', 'doctor', 'patient') NOT NULL,
    PRIMARY KEY (`user_id`)
);

