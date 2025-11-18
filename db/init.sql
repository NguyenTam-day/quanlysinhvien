-- Tạo database
CREATE DATABASE IF NOT EXISTS quanlysinhvien;
USE quanlysinhvien;

-- Bảng sinh viên
CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    gpa DOUBLE,
    className VARCHAR(50)
);

-- Bảng môn học
CREATE TABLE IF NOT EXISTS subjects (
    maMon VARCHAR(20) PRIMARY KEY,
    tenMon VARCHAR(100) NOT NULL,
    soTinChi INT
);

-- Bảng điểm
CREATE TABLE IF NOT EXISTS grades (
    student_id VARCHAR(20),
    subject_id VARCHAR(20),
    diemCC DOUBLE,
    diemBT DOUBLE,
    diemGK DOUBLE,
    diemTH DOUBLE,
    diemCK DOUBLE,
    PRIMARY KEY(student_id, subject_id),
    FOREIGN KEY(student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY(subject_id) REFERENCES subjects(maMon) ON DELETE CASCADE
);


-- Dữ liệu mẫu sinh viên
INSERT INTO students(id, name, age, gpa, className) VALUES
('S001','Nguyen Van A',20,3.2,'CSE1'),
('S002','Tran Thi B',21,3.5,'CSE2'),
('S003','Le Van C',22,3.1,'CSE1'),
('S004','Pham Thi D',20,3.6,'CSE3'),
('S005','Hoang Van E',23,2.9,'CSE2'),
('S006','Nguyen Thi F',21,3.8,'CSE1'),
('S007','Tran Van G',22,3.0,'CSE3'),
('S008','Le Thi H',20,3.7,'CSE2'),
('S009','Pham Van I',21,3.4,'CSE1'),
('S010','Hoang Thi J',22,3.2,'CSE3'),
('S011','Nguyen Van K',23,3.5,'CSE2'),
('S012','Tran Thi L',20,3.1,'CSE1'),
('S013','Le Van M',21,3.6,'CSE3'),
('S014','Pham Thi N',22,3.0,'CSE2'),
('S015','Hoang Van O',20,3.3,'CSE1');


-- Dữ liệu mẫu môn học
INSERT INTO subjects(maMon, tenMon, soTinChi) VALUES
('M001','Toan Cao Cap',3),
('M002','Ly Dai Cuong',3),
('M003','Hoa Hoc',3),
('M004','CNTT Co Ban',2),
('M005','Tieng Anh 1',2),
('M006','Giai Tich 1',3),
('M007','Vat Ly 1',3),
('M008','Kinh Te',2),
('M009','Co Khi',3),
('M010','Lap Trinh Java',3),
('M011','CSDL',3),
('M012','Mạng Máy Tính',2),
('M013','Tri Tue Nhan Tao',3),
('M014','Quan Tri Du An',2),
('M015','Tieng Anh 2',2);

-- Dữ liệu mẫu bảng điểm
INSERT INTO grades(student_id, subject_id, diemCC, diemBT, diemGK, diemTH, diemCK) VALUES
('S001','M001',8,7,6,9,8),
('S002','M001',7,8,7,8,9),
('S003','M001',6,7,6,7,6),
('S004','M002',8,9,7,8,8),
('S005','M002',7,7,8,6,7),
('S006','M003',9,8,7,8,9),
('S007','M003',6,6,6,7,6),
('S008','M004',8,7,8,7,8),
('S009','M004',7,6,7,8,7),
('S010','M005',8,8,9,8,9),
('S011','M005',6,7,6,7,6),
('S012','M001',7,7,8,7,8),
('S013','M002',8,9,8,8,9),
('S014','M003',6,7,6,7,6),
('S015','M004',9,8,9,8,9);
