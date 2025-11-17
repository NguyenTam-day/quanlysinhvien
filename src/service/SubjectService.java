package service;

import model.Student;
import model.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectService {

    private List<Subject> subjects;
    private FileSubjectService fileService;

    public SubjectService(FileSubjectService fileService) {
        this.fileService = fileService;
        this.subjects = fileService.load();
    }

    // Thêm môn học
    public boolean addSubject(Subject s) {
        if (s == null || subjects.contains(s))
            return false;
        subjects.add(s);
        save();
        return true;
    }

    // Xóa môn học theo mã
    public boolean removeSubject(String maMon) {
        Optional<Subject> opt = subjects.stream()
                .filter(s -> s.getMaMon().equalsIgnoreCase(maMon))
                .findFirst();
        if (opt.isPresent()) {
            subjects.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    // Sửa môn học
    public boolean updateSubject(String maMon, String tenMon, int soTinChi) {
        Optional<Subject> opt = subjects.stream()
                .filter(s -> s.getMaMon().equalsIgnoreCase(maMon))
                .findFirst();
        if (opt.isPresent()) {
            Subject s = opt.get();
            s.setTenMon(tenMon);
            s.setSoTinChi(soTinChi);
            save();
            return true;
        }
        return false;
    }

    // Tìm môn học theo mã
    public Subject findByCode(String maMon) {
        return subjects.stream()
                .filter(s -> s.getMaMon().equalsIgnoreCase(maMon))
                .findFirst()
                .orElse(null);
    }

    // Tìm môn học theo tên (có thể chứa chuỗi con)
    public List<Subject> findByName(String namePart) {
        List<Subject> result = new ArrayList<>();
        for (Subject s : subjects) {
            if (s.getTenMon().toLowerCase().contains(namePart.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    // Lấy tất cả môn học
    public List<Subject> getAllSubjects() {
        return new ArrayList<>(subjects);
    }

    // Lưu vào file
    public void save() {
        fileService.save(subjects);
    }

    public String getAllSubjectsAsString() {
        StringBuilder sb = new StringBuilder();
        for (Subject s : subjects) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
}
