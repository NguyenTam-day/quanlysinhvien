package view;

import service.MajorService; 
import model.Major;         
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MajorFrame extends JFrame {

    private MajorService majorService;
    private DefaultTableModel tableModel;
    private JTable majorTable;

    private JTextField txtMajorId;
    private JTextField txtMajorName;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    public MajorFrame() {
        majorService = new MajorService();
        setTitle("Quản Lý Ngành Học");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        initComponents();
        loadMajorData();

    }

    private void initComponents() {  
        tableModel = new DefaultTableModel(new Object[]{"Mã Ngành", "Tên Ngành"}, 0);
        majorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(majorTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Ngành Học"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10)); 
        txtMajorId = new JTextField();
        txtMajorName = new JTextField();
        
        inputPanel.add(new JLabel("Mã Ngành:"));
        inputPanel.add(txtMajorId);
        inputPanel.add(new JLabel("Tên Ngành:"));
        inputPanel.add(txtMajorName);

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

        btnAdd.addActionListener(this::addMajorAction);
        btnUpdate.addActionListener(this::updateMajorAction);
        btnDelete.addActionListener(this::deleteMajorAction);
        btnClear.addActionListener(e -> clearInputFields());
        majorTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                displaySelectedMajor();
            }
        });
    }

    private void loadMajorData() {
        tableModel.setRowCount(0); 
        List<Major> majors = majorService.getAllMajors();
        for (Major m : majors) {
            tableModel.addRow(new Object[]{m.getId(), m.getName()});
        }
    }

    private void addMajorAction(ActionEvent e) {
        String id = txtMajorId.getText();
        String name = txtMajorName.getText();
        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã và Tên Ngành.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Major newMajor = new Major(id, name);
        majorService.addMajor(newMajor);
        loadMajorData();
        clearInputFields();
    }
    
    private void updateMajorAction(ActionEvent e) {
        String id = txtMajorId.getText();
        String name = txtMajorName.getText();
        if (id.isEmpty() || name.isEmpty() || txtMajorId.isEditable()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Ngành cần sửa trong bảng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Major updatedMajor = new Major(id, name);
        majorService.updateMajor(updatedMajor);
        loadMajorData();
        clearInputFields();
    }
    
    private void deleteMajorAction(ActionEvent e) {
        String id = txtMajorId.getText();
        if (id.isEmpty() || txtMajorId.isEditable()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Ngành cần xóa trong bảng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa ngành " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            majorService.removeMajor(id);
            loadMajorData();
            clearInputFields();
        }
    }

    private void clearInputFields() {
        txtMajorId.setText("");
        txtMajorName.setText("");
        txtMajorId.setEditable(true);
    }
    
    private void displaySelectedMajor() {
        int selectedRow = majorTable.getSelectedRow();
        if (selectedRow != -1) {
            txtMajorId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtMajorName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtMajorId.setEditable(false); 
        }
    }
}