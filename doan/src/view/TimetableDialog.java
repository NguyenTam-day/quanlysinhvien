package view;

import model.Subject;
import model.Timetable;
import service.SubjectService;
import service.TimetableService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TimetableDialog extends JDialog {
    private JComboBox<String> cboSubject; // Dropdown chọn môn
    private JComboBox<String> cboDay;     // Dropdown chọn thứ
    private JTextField txtClass, txtShift, txtRoom;
    private JButton btnSave, btnCancel;
    
    private TimetableService timetableService = new TimetableService();
    private SubjectService subjectService = new SubjectService();
    private TimetablePanel parentPanel;

    public TimetableDialog(Frame owner, TimetablePanel parentPanel) {
        super(owner, "Thêm lịch học", true);
        this.parentPanel = parentPanel;
        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Chọn môn học (Load từ DB lên ComboBox)
        cboSubject = new JComboBox<>();
        loadSubjectsToComboBox();
        
        // 2. Chọn thứ
        String[] days = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ Nhật"};
        cboDay = new JComboBox<>(days);

        txtClass = new JTextField();
        txtShift = new JTextField();
        txtRoom = new JTextField();

        form.add(new JLabel("Môn học:")); form.add(cboSubject);
        form.add(new JLabel("Lớp học:")); form.add(txtClass);
        form.add(new JLabel("Thứ:"));     form.add(cboDay);
        form.add(new JLabel("Ca học:"));  form.add(txtShift);
        form.add(new JLabel("Phòng:"));   form.add(txtRoom);

        add(form, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel();
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        
        btnSave.setBackground(new Color(30, 144, 255));
        btnSave.setForeground(Color.WHITE);

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);

        // Events
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadSubjectsToComboBox() {
        List<Subject> list = subjectService.findAll();
        for (Subject s : list) {
            // Lưu format: "JAVA1 - Lập trình Java"
            cboSubject.addItem(s.getSubjectId() + " - " + s.getSubjectName());
        }
    }

    private void save() {
        String selectedSub = (String) cboSubject.getSelectedItem();
        if (selectedSub == null) return;
        
        // Cắt chuỗi để lấy mã môn (Vd: "JAVA1 - ..." -> lấy "JAVA1")
        String subId = selectedSub.split(" - ")[0]; 
        
        Timetable t = new Timetable();
        t.setSubjectId(subId);
        t.setClassName(txtClass.getText());
        t.setDayOfWeek((String) cboDay.getSelectedItem());
        t.setShift(txtShift.getText());
        t.setRoom(txtRoom.getText());

        timetableService.addTimetable(t);
        JOptionPane.showMessageDialog(this, "Thêm lịch thành công!");
        parentPanel.loadData();
        dispose();
    }
}