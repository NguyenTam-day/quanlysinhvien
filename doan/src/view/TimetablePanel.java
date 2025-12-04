package view;

import model.Timetable;
import service.TimetableService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TimetablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TimetableService service = new TimetableService();

    public TimetablePanel() {
        setLayout(new BorderLayout());

        // Buttons
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Sắp xếp lịch học");
        JButton btnDelete = new JButton("Xóa lịch");
        JButton btnRefresh = new JButton("Xem lại");
        
        top.add(btnAdd); top.add(btnDelete); top.add(btnRefresh);
        add(top, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Mã môn", "Tên môn", "Lớp", "Thứ", "Ca", "Phòng"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        // Ẩn cột ID đi cho đẹp (nhưng vẫn cần để xóa)
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        // Events
        btnRefresh.addActionListener(e -> loadData());
        
        btnAdd.addActionListener(e -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            new TimetableDialog(parent, this).setVisible(true);
        });

        btnDelete.addActionListener(e -> delete());
    }

    public void loadData() {
        model.setRowCount(0);
        List<Timetable> list = service.findAll();
        for (Timetable t : list) {
            model.addRow(new Object[]{
                t.getId(),
                t.getSubjectId(),
                t.getSubjectName(),
                t.getClassName(),
                t.getDayOfWeek(),
                t.getShift(),
                t.getRoom()
            });
        }
    }

    private void delete() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn lịch để xóa!");
            return;
        }
        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        int cf = JOptionPane.showConfirmDialog(this, "Xóa lịch này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf == JOptionPane.YES_OPTION) {
            service.deleteTimetable(id);
            loadData();
        }
    }
}