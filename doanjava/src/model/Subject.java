package model;

import java.util.Objects;

public class Subject {
    private String subjectId;      // Mã môn học
    private String subjectName;    // Tên môn học
    private int credit;            // Số tín chỉ
    private String teacherName;    // Tên giảng viên
    private String department;     // Bộ môn / khoa phụ trách

    // Constructor không tham số
    public Subject() {
    }

    // Constructor đầy đủ
    public Subject(String subjectId, String subjectName, int credit, String teacherName, String department) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credit = credit;
        this.teacherName = teacherName;
        this.department = department;
    }

    // Getter & Setter
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // --- Key Improvements Below ---

    // 1. Corrected toString(): Only ONE is allowed. 
    // I kept this version because your comment suggests you use it for a UI (like JComboBox).
    @Override
    public String toString() {
        // Output format: "JAVA1 - Lập trình Java"
        return subjectId + " - " + subjectName;
    }

    // 2. Added equals() and hashCode(): 
    // Since you imported java.util.Objects, you should use these to compare Subject objects correctly.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(subjectId, subject.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId);
    }
}