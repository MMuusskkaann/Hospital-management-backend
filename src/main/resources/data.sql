-- DROP table if exists (optional, safe for dev)
DROP TABLE IF EXISTS patient_table;

-- CREATE table
CREATE TABLE patient_table (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    gender VARCHAR(10),
    blood_group VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_patient_email UNIQUE (email),
    CONSTRAINT unique_patient_name_birth_date UNIQUE (name, birth_date)
);

-- CREATE index
CREATE INDEX idx_patient_birth_date ON patient_table (birth_date);

-- INSERT sample data
INSERT INTO patient_table (name, birth_date, email, gender, blood_group) VALUES
('John Doe', '1990-05-12', 'john.doe@example.com', 'Male', 'A_POSITIVE'),
('Jane Smith', '1985-11-20', 'jane.smith@example.com', 'Female', 'B_NEGATIVE'),
('Michael Johnson', '1992-08-03', 'michael.johnson@example.com', 'Male', 'O_POSITIVE'),
('Emily Davis', '1995-02-14', 'emily.davis@example.com', 'Female', 'AB_POSITIVE'),
('William Brown', '1988-07-30', 'william.brown@example.com', 'Male', 'A_NEGATIVE'),
('Olivia Wilson', '1991-12-25', 'olivia.wilson@example.com', 'Female', 'B_POSITIVE'),
('James Taylor', '1987-03-10', 'james.taylor@example.com', 'Male', 'O_NEGATIVE'),
('Sophia Anderson', '1993-09-05', 'sophia.anderson@example.com', 'Female', 'AB_NEGATIVE'),
('Benjamin Thomas', '1994-06-18', 'benjamin.thomas@example.com', 'Male', 'A_POSITIVE'),
('Isabella Martinez', '1989-10-22', 'isabella.martinez@example.com', 'Female', 'O_POSITIVE');
