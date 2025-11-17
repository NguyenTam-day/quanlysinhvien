package service;

import model.Grade;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileGradeService {
    private String filePath;

    public FileGradeService(String filePath) {
        this.filePath = filePath;
    }

    public List<Grade> load() {
        List<Grade> list = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists())
            return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Grade g = Grade.fromTXT(line);
                if (g != null)
                    list.add(g);
            }
        } catch (IOException e) {
            System.err.println("Error loading grades: " + e.getMessage());
        }
        return list;
    }

    public void save(List<Grade> list) {
        File f = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (Grade g : list) {
                bw.write(g.toTXT());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving grades: " + e.getMessage());
        }
    }
}
