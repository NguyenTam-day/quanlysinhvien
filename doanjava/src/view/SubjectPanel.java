package view;

import model.Subject;
import service.SubjectService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class SubjectPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private SubjectService subjectService = new SubjectService();

    public SubjectPanel() {
        setLayout(new BorderLayout());

        // ==== TOP BUTTONS ====
        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm môn");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Tải lại");

        topButtons.add(btnAdd);
        topButtons.add(btnEdit);
        topButtons.add(btnDelete);
        topButtons.add(btnRefresh);

        // ==== TABLE ====
        // 1. Thêm cột "STT" vào đầu mảng
        String[] columns = {"STT", "Mã môn", "Tên môn", "Số tín chỉ", "Giảng viên", "Khoa"};
        
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp
            }
        };
        table = new JTable(model);
        
        // 2. Chỉnh độ rộng cột STT cho nhỏ lại (Vì nó chỉ chứa số 1, 2, 3...)
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // STT nhỏ
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100); // Mã môn
        columnModel.getColumn(2).setPreferredWidth(200); // Tên môn

        JScrollPane scroll = new JScrollPane(table);

        add(topButtons, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadData();

        // ====== BUTTON ACTION ======
        btnRefresh.addActionListener(e -> loadData());

        // Gọi Dialog THÊM
        btnAdd.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            new SubjectDialog(parentFrame, this, null).setVisible(true);
        });

        // Gọi Dialog SỬA
        btnEdit.addActionListener(e -> editSubject());

        // Xóa
        btnDelete.addActionListener(e -> deleteSubject());
    }

    public void loadData() {
        model.setRowCount(0);
        List<Subject> list = subjectService.findAll();

        int stt = 1; // Biến đếm bắt đầu từ 1
        for (Subject s : list) {
            model.addRow(new Object[]{
                    stt++,               // Cột 0: Số thứ tự (tăng dần)
                    s.getSubjectId(),    // Cột 1: Mã môn
                    s.getSubjectName(),  // Cột 2
                    s.getCredit(),       // Cột 3
                    s.getTeacherName(),  // Cột 4
                    s.getDepartment()    // Cột 5
            });
        }
    }

    private void editSubject() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn môn học cần sửa!");
            return;
        }

        // LƯU Ý: Vì có thêm cột STT ở đầu (index 0), nên các cột khác bị lùi lại 1 số
        // Mã môn bây giờ là cột 1 (index 1), Tên môn là cột 2...
        
        String id = table.getValueAt(row, 1).toString();      // Cột 1: Mã môn
        String name = table.getValueAt(row, 2).toString();    // Cột 2: Tên
        int credit = Integer.parseInt(table.getValueAt(row, 3).toString());
        String teacher = table.getValueAt(row, 4) != null ? table.getValueAt(row, 4).toString() : "";
        String dept = table.getValueAt(row, 5) != null ? table.getValueAt(row, 5).toString() : "";

        Subject s = new Subject(id, name, credit, teacher, dept);

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        new SubjectDialog(parentFrame, this, s).setVisible(true);
    }

    private void deleteSubject() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng để xóa!");
            return;
        }

        // Lấy ID ở cột số 1 (vì cột 0 là STT)
        String id = table.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Xóa môn: " + id + "?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            subjectService.deleteSubject(id);
            loadData();
        }
    }
}