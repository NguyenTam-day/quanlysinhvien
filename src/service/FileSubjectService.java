package service;

import model.Subject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSubjectService {
    private String filePath;

    public FileSubjectService(String filePath) {
        this.filePath = filePath;
    }

    public List<Subject> load() {
        List<Subject> list = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists())
            return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Subject s = Subject.fromTXT(line);
                if (s != null)
                    list.add(s);
            }
        } catch (IOException e) {
            System.err.println("Error loading subjects: " + e.getMessage());
        }
        return list;
    }

    public void save(List<Subject> list) {
        File f = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (Subject s : list) {
                bw.write(s.toTXT());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving subjects: " + e.getMessage());
        }
    }
}
