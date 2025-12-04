package view;

import service.ClassroomService; 
import service.MajorService;    
import model.Classroom;
import model.Major;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassroomFrame extends JFrame {

    private ClassroomService classroomService;
    private MajorService majorService;
    
    private DefaultTableModel tableModel;
    private JTable classroomTable;

    private JTextField txtClassroomId;
    private JTextField txtClassroomName;
    private JComboBox<String> cbMajorId; // ComboBox hiển thị Major
    private Map<String, String> majorDisplayMap; // Lưu trữ Major ID -> Chuỗi hiển thị (ID - Name)
    
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;

    public ClassroomFrame(ClassroomService classroomService, MajorService majorService) {
        this.classroomService = classroomService;
        this.majorService = majorService;
        this.majorDisplayMap = new HashMap<>(); 
        
        setTitle("Quản Lý Lớp Học");
        setSize(750, 450); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        loadMajorComboBox(); 
        loadClassroomData();
    }

    private void initComponents() {
        // --- 1. Khu vực Bảng (CENTER) ---
        tableModel = new DefaultTableModel(new Object[]{"Mã Lớp", "Tên Lớp", "Mã Ngành"}, 0);
        classroomTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(classroomTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Lớp Học"));
        add(scrollPane, BorderLayout.CENTER);

        // --- 2. Khu vực Nhập liệu và Thao tác (SOUTH) ---
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10)); 
        
        txtClassroomId = new JTextField();
        txtClassroomName = new JTextField();
        cbMajorId = new JComboBox<>();
        
        inputPanel.add(new JLabel("Mã Lớp:"));
        inputPanel.add(txtClassroomId);
        inputPanel.add(new JLabel("Tên Lớp:"));
        inputPanel.add(txtClassroomName);
        inputPanel.add(new JLabel("Ngành Học:"));
        inputPanel.add(cbMajorId);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        JPanel mainSouthPanel = new JPanel(new BorderLayout());
        mainSouthPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainSouthPanel.add(inputPanel, BorderLayout.NORTH);
        mainSouthPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainSouthPanel, BorderLayout.SOUTH);

        // --- 3. Thêm Listeners ---
        btnAdd.addActionListener(this::addClassroomAction);
        btnUpdate.addActionListener(this::updateClassroomAction);
        btnDelete.addActionListener(this::deleteClassroomAction);
        btnClear.addActionListener(e -> clearInputFields());
        classroomTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                displaySelectedClassroom();
            }
        });
    }
    
    private void loadMajorComboBox() {
        cbMajorId.removeAllItems();
        majorDisplayMap.clear();
        
        List<Major> majors = majorService.getAllMajors();
        for (Major m : majors) {
            String displayItem = m.getId() + " - " + m.getName();
            cbMajorId.addItem(displayItem);
            majorDisplayMap.put(m.getId(), displayItem); 
        }
    }

    private void loadClassroomData() {
        tableModel.setRowCount(0);
        List<Classroom> classrooms = classroomService.getAllClassrooms();
        for (Classroom c : classrooms) {
            tableModel.addRow(new Object[]{c.getId(), c.getName(), c.getMajorId()});
        }
    }
    
    // --- Các phương thức Xử lý sự kiện (Action Handlers) ---
    
    private void addClassroomAction(ActionEvent e) {
        String id = txtClassroomId.getText();
        String name = txtClassroomName.getText();
        String selectedItem = (String) cbMajorId.getSelectedItem();
        String majorId = selectedItem != null ? selectedItem.split(" - ")[0].trim() : null;

        if (id.isEmpty() || name.isEmpty() || majorId == null) {
             JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Classroom newClassroom = new Classroom(id, name, majorId);
        classroomService.addClassroom(newClassroom);
        loadClassroomData();
        clearInputFields();
    }
    
    private void updateClassroomAction(ActionEvent e) {
        String id = txtClassroomId.getText();
        String name = txtClassroomName.getText();
        String selectedItem = (String) cbMajorId.getSelectedItem();
        String majorId = selectedItem != null ? selectedItem.split(" - ")[0].trim() : null;

        if (id.isEmpty() || name.isEmpty() || majorId == null || txtClassroomId.isEditable()) {
             JOptionPane.showMessageDialog(this, "Vui lòng chọn Lớp cần sửa trong bảng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Classroom updatedClassroom = new Classroom(id, name, majorId);
        classroomService.updateClassroom(updatedClassroom);
        loadClassroomData();
        clearInputFields();
    }
    
    private void deleteClassroomAction(ActionEvent e) {
        String id = txtClassroomId.getText();
        if (id.isEmpty() || txtClassroomId.isEditable()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Lớp cần xóa trong bảng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa lớp " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            classroomService.removeClassroom(id);
            loadClassroomData();
            clearInputFields();
        }
    }

    private void clearInputFields() {
        txtClassroomId.setText("");
        txtClassroomName.setText("");
        if (cbMajorId.getItemCount() > 0) {
            cbMajorId.setSelectedIndex(0);
        }
        txtClassroomId.setEditable(true);
    }
    
    private void displaySelectedClassroom() {
        int selectedRow = classroomTable.getSelectedRow();
        if (selectedRow != -1) {
            txtClassroomId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtClassroomName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            String majorId = tableModel.getValueAt(selectedRow, 2).toString();
            
            // Chọn Major tương ứng trong ComboBox
            if (majorDisplayMap.containsKey(majorId)) {
                cbMajorId.setSelectedItem(majorDisplayMap.get(majorId));
            }

            txtClassroomId.setEditable(false);
        }
    }
}