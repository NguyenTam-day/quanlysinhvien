package model;

import java.util.Objects;

public class Student {
    private String id;
    private String name;
    private int age;
    private double gpa;
    private String className;

    public Student() {
    }

    public Student(String id, String name, int age, double gpa, String className) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gpa = gpa;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %d | %.2f | %s", id, name, age, gpa, className);
    }

    // ✅ Hàm xuất 1 sinh viên thành 1 dòng CSV
    public String toCSV() {
        return escapeCsv(id) + "," + escapeCsv(name) + "," +
                age + "," + gpa + "," + escapeCsv(className);
    }

    public static Student fromCSV(String line) {
        if (line == null || line.isEmpty())
            return null;

        String[] parts = line.split(",", -1);
        if (parts.length < 5)
            return null;

        String id = unescapeCsv(parts[0]);
        String name = unescapeCsv(parts[1]);
        int age = Integer.parseInt(parts[2]);
        double gpa = Double.parseDouble(parts[3]);
        String className = unescapeCsv(parts[4]);

        return new Student(id, name, age, gpa, className);
    }

    // ✅ Hàm hỗ trợ escape ký tự đặc biệt trong CSV
    private static String escapeCsv(String s) {
        if (s == null)
            return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    // ✅ Hàm hỗ trợ đọc ngược lại (nếu cần import CSV)
    public static String unescapeCsv(String s) {
        if (s == null)
            return "";
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1).replace("\"\"", "\"");
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
