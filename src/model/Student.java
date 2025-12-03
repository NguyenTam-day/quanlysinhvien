package model;

import java.util.Objects;

public class Student {
    private String id;
    private String name;
    private int age;
    private double gpa;
    private String classId; 
    private String className; 

    public Student() {
    }

    public Student(String id, String name, int age, double gpa, String classInfo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gpa = gpa;
        this.classId = classInfo; 
        this.className = classInfo; 
    }

    public Student(String id, String name, int age, double gpa, String classId, String className) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gpa = gpa;
        this.classId = classId;
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
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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