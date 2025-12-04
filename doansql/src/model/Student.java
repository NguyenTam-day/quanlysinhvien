/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;

/**
 *
 * @author Admin
 */
public class Student {
    private String mssv;
    private String hoten;
    private int tuoi;
    private String gioitinh;
    private double gpa;
    private int sdt;
    private String email;
    private String diachi;
    public Student() {
    }

    public Student(String mssv, String hoten, int tuoi, String gioitinh, int sdt,  String diachi, String email, double gpa) {
        this.mssv = mssv;
        this.hoten = hoten;
        this.tuoi = tuoi;
        this.gioitinh = gioitinh;
        this.gpa = gpa;
        this.sdt = sdt;
        this.email = email;
        this.diachi = diachi;
    }
    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getDiachi() {
        return email;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
    

    @Override
    public String toString() {
        return String.format("%s |%s |%s |%s |%s |%d |%s |%2f",mssv,hoten,tuoi,gioitinh,diachi,sdt,email, gpa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mssv);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        return Objects.equals(this.mssv, other.mssv);
    }
    
    
}