TRUNCATE TABLE appointment RESTART IDENTITY CASCADE;
TRUNCATE TABLE doctors RESTART IDENTITY CASCADE;
TRUNCATE TABLE patient_table RESTART IDENTITY CASCADE;

-- Patients
INSERT INTO patient_table(name, birth_date, email, gender, blood_group) VALUES
('Alice', '1993-02-14', 'alice@example.com', 'F', 'A_POSITIVE'),
('Bob', '1998-06-20', 'bob@example.com', 'M', 'B_NEGATIVE'),
('Charlie', '1983-11-05', 'charlie@example.com', 'M', 'O_POSITIVE'),
('David', '1988-03-12', 'david@example.com', 'M', 'AB_POSITIVE'),
('Eve', '1995-09-30', 'eve@example.com', 'F', 'A_NEGATIVE');

-- Doctors
INSERT INTO doctors(name, specialization, email) VALUES
('Dr. Rakesh Mehta', 'Cardiology', 'rakesh.mehta@gmail.com'),
('Dr. Sneha Kapoor', 'Dermatology', 'sneha.kapoor12@gmail.com'),
('Dr. Arjun Nair', 'Orthopedics', 'arjun.nair234@gmail.com');

-- Appointments
INSERT INTO appointment(appointment_time, reason, doctor_id, patient_id) VALUES
('2025-07-01 10:30:00', 'General Checkup', 1, 2),
('2025-07-02 11:00:00', 'Skin Rash', 2, 2),
('2025-07-03 13:30:00', 'Knee Pain', 3, 3),
('2025-07-04 08:20:00', 'Follow up visit', 1, 1),
('2025-07-05 16:15:00', 'Consultation', 1, 4),
('2025-07-06 14:30:00', 'Allergy Treatment', 2, 5);
