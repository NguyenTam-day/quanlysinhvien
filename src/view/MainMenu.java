package view;


import model.Student;
import service.StudentService;
import utils.InputUtils;


import java.util.List;


public class MainMenu {
    private StudentService studentService;
    private final String DATA_FILE = "students.csv"; // file created in working directory


    public MainMenu() {
        studentService = new StudentService(DATA_FILE);
    }


    public void run() {
        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT =====");
            System.out.println("1. Add student");
            System.out.println("2. Update student");
            System.out.println("3. Delete student");
            System.out.println("4. List all students");
            System.out.println("5. Search by name");
            System.out.println("6. Sort by name");
            System.out.println("0. Exit");
            int choice = InputUtils.readInt("Choose option: ");
            switch (choice) {
            case 1: addStudent(); break;
            case 2: updateStudent(); break;
            case 3: deleteStudent(); break;
            case 4: listAll(); break;
            case 5: searchByName(); break;
            case 6: sortByName(); break;
            case 0: System.out.println("Bye!"); return;
            default: System.out.println("Invalid choice");
            }
        }
    }


    private void addStudent() {
        System.out.println("--- Add student ---");
        String id = InputUtils.readNonEmptyString("ID: ");
        if (studentService.findById(id).isPresent()) {
            System.out.println("ID already exists.");
        return;
        }
        String name = InputUtils.readNonEmptyString("Name: ");
        int age = InputUtils.readInt("Age: ");
        double gpa = InputUtils.readDouble("GPA: ");
        String className = InputUtils.readNonEmptyString("Class: ");
        Student s = new Student(id, name, age, gpa, className);
        if (studentService.addStudent(s)) System.out.println("Added."); else System.out.println("Add failed.");
    }


    private void updateStudent() {
        System.out.println("--- Update student ---");
        String id = InputUtils.readNonEmptyString("ID to update: ");
        if (!studentService.findById(id).isPresent()) {
            System.out.println("Student not found.");
            return;
        }
        String name = InputUtils.readNonEmptyString("New name: ");
        int age = InputUtils.readInt("New age: ");
        double gpa = InputUtils.readDouble("New GPA: ");
        String className = InputUtils.readNonEmptyString("New class: ");
        Student s = new Student(id, name, age, gpa, className);
        if (studentService.updateStudent(id, s)) System.out.println("Updated."); else System.out.println("Update failed.");
    }


    private void deleteStudent() {
        System.out.println("--- Delete student ---");
        String id = InputUtils.readNonEmptyString("ID to delete: ");
        if (studentService.deleteStudent(id)) System.out.println("Deleted."); else System.out.println("Delete failed or not found.");
    }


    private void listAll() {
        System.out.println("--- All students ---");
        List<Student> list = studentService.findAll();
        if (list.isEmpty()) System.out.println("No students.");
        else list.forEach(s -> System.out.println(s));
    }


    private void searchByName() {
        System.out.println("--- Search by name ---");
        String kw = InputUtils.readNonEmptyString("Keyword: ");
        List<Student> list = studentService.searchByName(kw);
        if (list.isEmpty()) System.out.println("No match."); else list.forEach(s -> System.out.println(s));
    }


    private void sortByName() {
        System.out.println("--- Sorted by name ---");
        List<Student> list = studentService.sortByName();
        if (list.isEmpty()) System.out.println("No students."); else list.forEach(s -> System.out.println(s));
    }
}