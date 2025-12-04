package model;

import java.util.Objects;

public class Classroom {
    private String id;          
    private String name;        
    private String majorId;     

    public Classroom() {}

    public Classroom(String id, String name, String majorId) {
        this.id = id;
        this.name = name;
        this.majorId = majorId;
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

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }



    @Override
    public String toString() {
        return "Classroom{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", majorId='" + majorId + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return Objects.equals(id, classroom.id); 
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}