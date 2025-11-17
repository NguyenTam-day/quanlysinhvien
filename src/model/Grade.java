package model;

import java.util.Objects;

public class Grade {

    private String studentId;
    private String subjectId;
    private double diemChuyenCan;
    private double diemBaiTap;
    private double diemGiuaKy;
    private double diemThucHanh;
    private double diemCuoiKy;

    public Grade() {
    }

    public Grade(String studentId, String subjectId,
            double diemChuyenCan, double diemBaiTap,
            double diemGiuaKy, double diemThucHanh, double diemCuoiKy) {

        this.studentId = studentId;
        this.subjectId = subjectId;
        this.diemChuyenCan = diemChuyenCan;
        this.diemBaiTap = diemBaiTap;
        this.diemGiuaKy = diemGiuaKy;
        this.diemThucHanh = diemThucHanh;
        this.diemCuoiKy = diemCuoiKy;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public double getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public void setDiemChuyenCan(double diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
    }

    public double getDiemBaiTap() {
        return diemBaiTap;
    }

    public void setDiemBaiTap(double diemBaiTap) {
        this.diemBaiTap = diemBaiTap;
    }

    public double getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(double diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }

    public double getDiemThucHanh() {
        return diemThucHanh;
    }

    public void setDiemThucHanh(double diemThucHanh) {
        this.diemThucHanh = diemThucHanh;
    }

    public double getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(double diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %.2f | %.2f | %.2f | %.2f | %.2f",
                studentId, subjectId,
                diemChuyenCan, diemBaiTap, diemGiuaKy, diemThucHanh, diemCuoiKy);
    }

    // ============================
    // EXPORT -> 1 DÒNG CSV
    // ============================
    public String toTXT() {
        return escapeTxt(studentId) + "," +
                escapeTxt(subjectId) + "," +
                diemChuyenCan + "," +
                diemBaiTap + "," +
                diemGiuaKy + "," +
                diemThucHanh + "," +
                diemCuoiKy;
    }

    // ============================
    // IMPORT -> TỪ 1 DÒNG CSV
    // ============================
    public static Grade fromTXT(String line) {
        if (line == null || line.isEmpty())
            return null;

        String[] parts = line.split(",", -1);

        if (parts.length < 7)
            return null;

        String studentId = unescapeTxt(parts[0]);
        String subjectId = unescapeTxt(parts[1]);

        double cc = Double.parseDouble(parts[2]);
        double bt = Double.parseDouble(parts[3]);
        double gk = Double.parseDouble(parts[4]);
        double th = Double.parseDouble(parts[5]);
        double ck = Double.parseDouble(parts[6]);

        return new Grade(studentId, subjectId, cc, bt, gk, th, ck);
    }

    // ============================
    // ESCAPE giống Student
    // ============================
    private static String escapeTxt(String s) {
        if (s == null)
            return "";
        if (s.contains(" ") || s.contains("\"") || s.contains("\n") || s.contains(",")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private static String unescapeTxt(String s) {
        if (s == null)
            return "";
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1).replace("\"\"", "\"");
        }
        return s;
    }

    // ============================
    // EQUALS -> 1 sinh viên + 1 môn = 1 record
    // ============================
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Grade grade = (Grade) o;
        return Objects.equals(studentId, grade.studentId) &&
                Objects.equals(subjectId, grade.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, subjectId);
    }
}
