package service;

import model.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentService {
    private List<Student> students;
    private FileService fileService;

    public StudentService(String filePath) {
        fileService = new FileService(filePath);
        students = fileService.load();
        if (students == null)
            students = new ArrayList<>();
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public Optional<Student> findById(String id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public boolean addStudent(Student s) {
        if (s == null)
            return false;
        if (findById(s.getId()).isPresent())
            return false;
        students.add(s);
        save();
        return true;
    }

    public boolean updateStudent(String id, Student newData) {
        Optional<Student> opt = findById(id);
        if (!opt.isPresent())
            return false;
        Student s = opt.get();
        s.setName(newData.getName());
        s.setAge(newData.getAge());
        s.setGpa(newData.getGpa());
        s.setClassName(newData.getClassName());
        save();
        return true;
    }

    public boolean deleteStudent(String id) {
        Optional<Student> opt = findById(id);
        if (!opt.isPresent())
            return false;
        students.remove(opt.get());
        save();
        return true;
    }

    public List<Student> searchByName(String keyword) {
        String k = keyword == null ? "" : keyword.toLowerCase();
        return students.stream().filter(s -> s.getName().toLowerCase().contains(k)).collect(Collectors.toList());
    }

    public List<Student> sortByName() {
        return students.stream().sorted(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public void save() {
        fileService.save(students);
    }

    public String getAllStudentsAsString() {
        StringBuilder sb = new StringBuilder();
        for (Student s : students) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
}
