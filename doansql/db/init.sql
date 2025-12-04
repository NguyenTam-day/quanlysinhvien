USE quanlysinhvien;

DROP TABLE IF EXISTS grades;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS classrooms;
DROP TABLE IF EXISTS majors;

CREATE TABLE majors (
    major_id VARCHAR(10) PRIMARY KEY,
    major_name VARCHAR(100) NOT NULL
);

CREATE TABLE classrooms (
    class_id VARCHAR(20) PRIMARY KEY,
    class_name VARCHAR(100) NOT NULL,
    major_id VARCHAR(10),
    FOREIGN KEY (major_id) REFERENCES majors(major_id) ON DELETE SET NULL
);

CREATE TABLE students (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,           -- ← Java đang tìm cột này
    age INT CHECK (age >= 17 AND age <= 40),
    gpa FLOAT CHECK (gpa >= 0 AND gpa <= 4.0),
    class_id VARCHAR(20),
    className VARCHAR(100),                -- ← tên lớp đẹp để hiển thị
    FOREIGN KEY (class_id) REFERENCES classrooms(class_id) ON DELETE SET NULL
);

CREATE TABLE subjects (
    maMon VARCHAR(20) PRIMARY KEY,
    tenMon VARCHAR(100) NOT NULL,
    soTinChi INT CHECK (soTinChi > 0)
);

CREATE TABLE grades (
    student_id VARCHAR(20),
    subject_id VARCHAR(20),
    diemCC DOUBLE CHECK (diemCC BETWEEN 0 AND 10),
    diemBT DOUBLE CHECK (diemBT BETWEEN 0 AND 10),
    diemGK DOUBLE CHECK (diemGK BETWEEN 0 AND 10),
    diemTH DOUBLE CHECK (diemTH BETWEEN 0 AND 10),
    diemCK DOUBLE CHECK (diemCK BETWEEN 0 AND 10),
    PRIMARY KEY (student_id, subject_id),
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(maMon) ON DELETE CASCADE
);

INSERT IGNORE INTO majors (major_id, major_name) VALUES 
('IT', 'Công nghệ Thông tin'),('BA', 'Quản trị Kinh doanh'),('EE', 'Kỹ thuật Điện – Điện tử');

INSERT IGNORE INTO classrooms (class_id, class_name, major_id) VALUES 
('IT101', 'Lập trình viên K25', 'IT'),
('IT102', 'Kỹ sư phần mềm K26', 'IT'),
('BA201', 'Quản trị Marketing K25', 'BA'),
('EE301', 'Điện tử viễn thông K26', 'EE');

INSERT IGNORE INTO students (id, name, age, gpa, class_id, className) VALUES
('S001','Nguyen Van A',20,3.2,'IT101','Lập trình viên K25'),
('S002','Tran Thi B',21,3.5,'IT102','Kỹ sư phần mềm K26'),
('S003','Le Van C',22,3.1,'IT101','Lập trình viên K25'),
('S004','Pham Thi D',20,3.6,'BA201','Quản trị Marketing K25'),
('S005','Hoang Van E',23,2.9,'IT102','Kỹ sư phần mềm K26'),
('S006','Nguyen Thi F',21,3.8,'EE301','Điện tử viễn thông K26'),
('S007','Tran Van G',22,3.0,'IT101','Lập trình viên K25'),
('S008','Le Thi H',20,3.7,'BA201','Quản trị Marketing K25'),
('S009','Pham Van I',21,3.4,'IT102','Kỹ sư phần mềm K26'),
('S010','Hoang Thi J',22,3.2,'EE301','Điện tử viễn thông K26'),
('S011','Nguyen Van K',23,3.5,'IT101','Lập trình viên K25'),
('S012','Tran Thi L',20,3.1,'BA201','Quản trị Marketing K25'),
('S013','Le Van M',21,3.6,'IT102','Kỹ sư phần mềm K26'),
('S014','Pham Thi N',22,3.0,'EE301','Điện tử viễn thông K26'),
('S015','Hoang Van O',20,3.3,'IT101','Lập trình viên K25'),
('S016','Hoang Honag P',19,3.3,'IT101','Lập trình viên K25');

INSERT IGNORE INTO subjects (maMon, tenMon, soTinChi) VALUES
('M001','Toán Cao Cấp',3),('M002','Lý Đại Cương',3),('M003','Hóa Học',3),
('M004','CNTT Cơ Bản',2),('M005','Tiếng Anh 1',2),('M006','Giải Tích 1',3),
('M007','Vật Lý 1',3),('M008','Kinh Tế',2),('M009','Cơ Khí',3),
('M010','Lập Trình Java',3),('M011','Cơ Sở Dữ Liệu',3),('M012','Mạng Máy Tính',2),
('M013','Trí Tuệ Nhân Tạo',3),('M014','Quản Trị Dự Án',2),('M015','Tiếng Anh 2',2);

INSERT IGNORE INTO grades (student_id, subject_id, diemCC, diemBT, diemGK, diemTH, diemCK) VALUES
('S001','M001',8,7,6,9,8),('S002','M001',7,8,7,8,9),('S003','M001',6,7,6,7,6),
('S004','M002',8,9,7,8,8),('S005','M002',7,7,8,6,7),('S006','M003',9,8,7,8,9),
('S007','M003',6,6,6,7,6),('S008','M004',8,7,8,7,8),('S009','M004',7,6,7,8,7),
('S010','M005',8,8,9,8,9),('S011','M005',6,7,6,7,6),('S012','M001',7,7,8,7,8),
('S013','M002',8,9,8,8,9),('S014','M003',6,7,6,7,6),('S015','M004',9,8,9,8,9);

SET SESSION sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_DATE,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';