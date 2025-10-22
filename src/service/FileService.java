package service;

import model.Student;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileService {
    private String filePath;


    public FileService(String filePath) {
        this.filePath = filePath;
}


    public List<Student> load() {
        List<Student> list = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromCSV(line);
                if (s != null) list.add(s);
            }
        } catch (IOException e) {
        System.err.println("Error loading students: " + e.getMessage());
        }
        return list;
    }


    public void save(List<Student> list) {
        File f = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (Student s : list) {
                bw.write(s.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
        System.err.println("Error saving students: " + e.getMessage());
        }
    }
}
