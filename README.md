# Hệ thống Quản lý Sinh viên

## Tổng quan
Dự án **Hệ thống Quản lý Sinh viên** là một ứng dụng Java giúp quản lý thông tin sinh viên. Người dùng có thể thêm, xem, sửa, xóa và tìm kiếm thông tin sinh viên. Dữ liệu được lưu trữ trong file CSV để tiện quản lý lâu dài.

---

## Tính năng

1. **Thêm sinh viên**
   - Nhập thông tin sinh viên: Mã số sinh viên (MSSV), Họ tên, Lớp, Ngày sinh, Điểm trung bình.
   - Kiểm tra trùng MSSV trước khi thêm.

2. **Hiển thị danh sách sinh viên**
   - Hiển thị tất cả sinh viên hiện có.
   - Có thể hiển thị dưới dạng bảng hoặc danh sách.

3. **Tìm kiếm sinh viên**
   - Tìm theo MSSV hoặc họ tên.
   - Kết quả hiển thị đầy đủ thông tin sinh viên.

4. **Sửa thông tin sinh viên**
   - Chọn sinh viên cần sửa theo MSSV.
   - Cập nhật các thông tin cần thay đổi.

5. **Xóa sinh viên**
   - Xóa sinh viên theo MSSV.
   - Hệ thống xác nhận trước khi xóa.

6. **Lưu và đọc dữ liệu**
   - Dữ liệu được lưu trong file `student.csv`.
   - Hệ thống có thể đọc dữ liệu từ file khi khởi động.

---

## Cấu trúc dự án

QuanLySinhVien/
│
├─ .classpath
├─ .project
├─ README.md
├─ student.csv
├─ bin/ # Thư mục chứa file .class sau khi biên dịch
├─ src/
│ ├─ main/
│ │ └─ Main.java # Điểm khởi chạy ứng dụng
│ ├─ model/
│ │ └─ Student.java # Class sinh viên
│ ├─ service/
│ │ ├─ StudentService.java # Các phương thức quản lý sinh viên
│ │ └─ FileService.java # Các phương thức đọc/ghi file
│ ├─ utils/
│ │ └─ InputUtils.java # Các phương thức nhập liệu tiện ích
│ └─ view/
│ ├─ MainMenu.java # Giao diện menu chính
│ └─ StudentFrame.java # Giao diện chi tiết/sửa thông tin sinh viên

yaml
Sao chép mã

---

## Cách chạy

1. Mở dự án trong IDE (IntelliJ IDEA, Eclipse, VSCode…).

2. Biên dịch và chạy file `Main.java` trong thư mục `src/main`.

3. Sử dụng menu để thao tác quản lý sinh viên.

---

## Công nghệ sử dụng

- Ngôn ngữ: Java 8+
- IDE gợi ý: IntelliJ IDEA, Eclipse, VSCode
- Lưu trữ dữ liệu: CSV (`student.csv`)
- GUI: Console & Swing (`StudentFrame`)

---

## Hướng phát triển

- Thêm chức năng thống kê sinh viên theo điểm.
- Kết nối cơ sở dữ liệu MySQL/SQLite.
- Nâng cấp giao diện GUI trực quan hơn.
- Thêm xác thực dữ liệu người dùng (validation).