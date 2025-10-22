# Hệ thống Quản lý Sinh viên

## Tổng quan
Dự án **Hệ thống Quản lý Sinh viên** là một ứng dụng Java giúp quản lý thông tin sinh viên một cách dễ dàng và hiệu quả. Người dùng có thể thêm, xem, sửa, xóa và tìm kiếm thông tin sinh viên. Dữ liệu có thể được lưu trữ và đọc từ file để tiện quản lý lâu dài.

---

## Tính năng

1. **Thêm sinh viên**
   - Nhập thông tin sinh viên: Mã số sinh viên (MSSV), Họ tên, Lớp, Ngày sinh, Điểm trung bình.
   - Kiểm tra trùng MSSV trước khi thêm.

2. **Hiển thị danh sách sinh viên**
   - Hiển thị tất cả sinh viên hiện có trong hệ thống.
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

6. **Lưu và đọc dữ liệu từ file**
   - Dữ liệu sinh viên được lưu vào file `.txt` hoặc `.csv`.
   - Khi mở ứng dụng, hệ thống có thể đọc dữ liệu từ file để khôi phục thông tin.

---

## Cấu trúc dự án

QuanLySinhVien/
│
├─ src/
│ ├─ model/
│ │ └─ SinhVien.java # Class sinh viên
│ ├─ service/
│ │ └─ SinhVienService.java # Các phương thức quản lý sinh viên
│ ├─ ui/
│ │ └─ MainUI.java # Giao diện người dùng (console hoặc GUI)
│ └─ Main.java # Điểm khởi chạy ứng dụng
│
├─ data/
│ └─ sinhvien.txt # File lưu dữ liệu sinh viên
│
└─ README.md

yaml
Sao chép mã

---

## Cách chạy

1. Clone repository:

```bash
git clone https://github.com/username/QuanLySinhVien.git
Mở dự án trong IDE (IntelliJ IDEA, Eclipse, VSCode…).

Chạy file Main.java.

Sử dụng giao diện console hoặc GUI để thao tác quản lý sinh viên.

Công nghệ sử dụng
Ngôn ngữ: Java 8+

IDE gợi ý: IntelliJ IDEA, Eclipse, VSCode

Lưu trữ dữ liệu: File text (.txt) hoặc CSV

GUI: Swing (nếu có giao diện đồ họa)

Hướng phát triển
Thêm chức năng sắp xếp sinh viên theo điểm hoặc tên.

Kết nối cơ sở dữ liệu (MySQL, SQLite) thay vì file.

Thêm chức năng thống kê (tỷ lệ điểm giỏi, trung bình, yếu).

Thêm xác thực dữ liệu người dùng (validation).
