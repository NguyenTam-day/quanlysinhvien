package model;

import java.util.Objects;

public class Subject {

    private String maMon;
    private String tenMon;
    private int soTinChi;

    public Subject() {
    }

    public Subject(String maMon, String tenMon, int soTinChi) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soTinChi = soTinChi;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %d", maMon, tenMon, soTinChi);
    }

    // ============================
    // EXPORT -> 1 DÒNG CSV
    // ============================
    public String toTXT() {
        return escapeTxt(maMon) + "," +
                escapeTxt(tenMon) + "," +
                soTinChi;
    }

    // ============================
    // IMPORT -> TỪ 1 DÒNG CSV
    // ============================
    public static Subject fromTXT(String line) {
        if (line == null || line.isEmpty())
            return null;

        // CSV ngăn cách bằng dấu phẩy nên split ","
        String[] parts = line.split(",", -1);

        if (parts.length < 3)
            return null;

        String maMon = unescapeTxt(parts[0]);
        String tenMon = unescapeTxt(parts[1]);
        int soTinChi = Integer.parseInt(parts[2]);

        return new Subject(maMon, tenMon, soTinChi);
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
    // EQUALS -> so sánh theo mã môn
    // ============================
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Subject subject = (Subject) o;
        return Objects.equals(maMon, subject.maMon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maMon);
    }
}
