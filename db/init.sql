use quanlysinhvien;

-- XÓA BẢNG THEO THỨ TỰ KHÓA NGOẠI (Foreign Key)
DROP TABLE IF EXISTS grades;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS classrooms;
DROP TABLE IF EXISTS majors;

---

-- ===================================================
-- 1. Bảng Ngành học (Major)
-- ===================================================
CREATE TABLE majors (
    major_id VARCHAR(10) PRIMARY KEY,
    major_name VARCHAR(100) NOT NULL
);

-- ===================================================
-- 2. Bảng Lớp học (Classroom)
-- ===================================================
CREATE TABLE classrooms (
    class_id VARCHAR(20) PRIMARY KEY,
    class_name VARCHAR(100) NOT NULL,
    major_id VARCHAR(10),
    FOREIGN KEY(major_id) REFERENCES majors(major_id) ON DELETE SET NULL
);
-- ===================================================
-- 3. Bảng Sinh viên (Student)
-- ===================================================
CREATE TABLE students (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    gpa FLOAT,
    -- Khóa ngoại trỏ đến classrooms(class_id)
    class_id VARCHAR(20), 
    FOREIGN KEY(class_id) REFERENCES classrooms(class_id) ON DELETE SET NULL
);

-- ===================================================
-- 4. Bảng Môn học (Subject)
-- ===================================================
CREATE TABLE subjects (
    maMon VARCHAR(20) PRIMARY KEY,
    tenMon VARCHAR(100) NOT NULL,
    soTinChi INT
);

-- ===================================================
-- 5. Bảng Điểm (Grades)
-- ===================================================
CREATE TABLE grades (
    student_id VARCHAR(20),
    subject_id VARCHAR(20),
    diemCC FLOAT,
    diemBT FLOAT,
    diemGK FLOAT,
    diemTH FLOAT,
    diemCK FLOAT,
    PRIMARY KEY(student_id, subject_id),
    FOREIGN KEY(student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY(subject_id) REFERENCES subjects(maMon) ON DELETE CASCADE
);

---

## 🚀 Dữ Liệu Mẫu Đã Cập Nhật

-- Dữ liệu mẫu Ngành học
INSERT INTO majors(major_id, major_name) VALUES
('IT', 'Công nghệ Thông tin'),
('BA', 'Quản trị Kinh doanh'),
('EE', 'Kỹ thuật Điện tử');

-- Dữ liệu mẫu Lớp học (Liên kết với Major)
INSERT INTO classrooms(class_id, class_name, major_id) VALUES
('IT101', 'Lập trình viên K25', 'IT'),
('IT102', 'Kỹ sư phần mềm K26', 'IT'),
('BA201', 'Quản trị Marketing K25', 'BA'),
('EE301', 'Điện tử viễn thông K26', 'EE');

-- Dữ liệu mẫu Sinh viên (Liên kết với Classroom)
INSERT INTO students(id, name, age, gpa, class_id) VALUES
('S001','Nguyen Van A',20,3.2,'IT101'),
('S002','Tran Thi B',21,3.5,'IT102'),
('S003','Le Van C',22,3.1,'IT101'),
('S004','Pham Thi D',20,3.6,'BA201'),
('S005','Hoang Van E',23,2.9,'IT102'),
('S006','Nguyen Thi F',21,3.8,'EE301'),
('S007','Tran Van G',22,3.0,'IT101'),
('S008','Le Thi H',20,3.7,'BA201'),
('S009','Pham Van I',21,3.4,'IT102'),
('S010','Hoang Thi J',22,3.2,'EE301'),
('S011','Nguyen Van K',23,3.5,'IT101'),
('S012','Tran Thi L',20,3.1,'BA201'),
('S013','Le Van M',21,3.6,'IT102'),
('S014','Pham Thi N',22,3.0,'EE301'),
('S015','Hoang Van O',20,3.3,'IT101');

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
('M012','Mang May Tinh',2),
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