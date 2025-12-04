package view;

import model.Student;
import service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentPanel extends JPanel {

    private DefaultTableModel model;
    private JTable table;
    private StudentService service = new StudentService();

    public StudentPanel() {
        setLayout(new BorderLayout());

        // ===== TOP BUTTONS =====
        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Tải lại");

        // Các nút phụ (nếu cần)
        JButton btnImport = new JButton("Nhập Excel");
        JButton btnExport = new JButton("Xuất Excel");

        topButtons.add(btnAdd);
        topButtons.add(btnEdit);
        topButtons.add(btnDelete);
        topButtons.add(btnRefresh);
        topButtons.add(new JSeparator(SwingConstants.VERTICAL));
        topButtons.add(btnImport);
        topButtons.add(btnExport);

        add(topButtons, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] columns = {"MSSV", "Họ và tên", "Tuổi", "Giới tính", "GPA", "SĐT", "Email", "Địa chỉ"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // chọn 1 dòng
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load dữ liệu ban đầu
        loadStudentTable();

        // ===== SỰ KIỆN =====
        btnRefresh.addActionListener(e -> loadStudentTable());

        btnAdd.addActionListener(e -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            new StudentDialog(parent, this, null).setVisible(true);
        });

        btnEdit.addActionListener(e -> editStudent());

        btnDelete.addActionListener(e -> deleteStudent());
    }

    public void loadStudentTable() {
        List<Student> list = service.findAll();
        model.setRowCount(0);
        for (Student s : list) {
            model.addRow(new Object[]{
                    s.getMssv(), s.getHoten(), s.getTuoi(),
                    s.getGioitinh(), s.getGpa(), s.getSdt(), s.getEmail(), s.getDiachi()
            });
        }
    }

    private void editStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần sửa!");
            return;
        }

        String mssv = table.getValueAt(row, 0).toString();
        String ten = table.getValueAt(row, 1).toString();
        int tuoi = Integer.parseInt(table.getValueAt(row, 2).toString());
        String gioitinh = table.getValueAt(row, 3).toString();
        double gpa = Double.parseDouble(table.getValueAt(row, 4).toString());
        int sdt = Integer.parseInt(table.getValueAt(row, 5).toString());
        String email = table.getValueAt(row, 6).toString();
        String diachi = table.getValueAt(row, 7).toString();

        // Chú ý: thứ tự constructor đúng: mssv, ten, tuoi, gioitinh, sdt, email, gpa, diachi
        Student s = new Student(mssv, ten, tuoi, gioitinh, sdt, diachi, email, gpa);

        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        new StudentDialog(parent, this, s).setVisible(true);
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!");
            return;
        }

        String mssv = table.getValueAt(row, 0).toString();
        
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa sinh viên MSSV: " + mssv + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            service.deleteStudent(mssv);
            loadStudentTable();
            JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
        }
    }
}
