package model;

public class Timetable {
    private int id;
    private String subjectId;
    private String subjectName; // Biến này để hiển thị tên môn cho đẹp
    private String className;
    private String dayOfWeek;
    private String shift;
    private String room;

    public Timetable() {}

    public Timetable(int id, String subjectId, String subjectName, String className, String dayOfWeek, String shift, String room) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName; // Lưu tên môn lấy từ bảng Subject
        this.className = className;
        this.dayOfWeek = dayOfWeek;
        this.shift = shift;
        this.room = room;
    }

    // Getters Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
}