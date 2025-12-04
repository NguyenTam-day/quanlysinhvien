package model;

public class Grade {
    private String studentId;
    private String studentName;
    private String subjectId;
    
    // 5 đầu điểm
    private double attendance; // Chuyên cần
    private double homework;   // Bài tập
    private double practical;  // Thực hành
    private double midterm;    // Giữa kì
    private double finalExam;  // Cuối kì

    public Grade() {}

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public double getAttendance() {
        return attendance;
    }

    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    public double getHomework() {
        return homework;
    }

    public void setHomework(double homework) {
        this.homework = homework;
    }

    public double getPractical() {
        return practical;
    }

    public void setPractical(double practical) {
        this.practical = practical;
    }

    public double getMidterm() {
        return midterm;
    }

    public void setMidterm(double midterm) {
        this.midterm = midterm;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    public double getTotal() {
        return (attendance * 0.1) + (homework * 0.1) + (practical * 0.1) + (midterm * 0.2) + (finalExam * 0.5);
    }
}