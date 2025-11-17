package service;

import model.Grade;
import model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GradeService {

    private List<Grade> grades;
    private FileGradeService fileService;

    public GradeService(FileGradeService fileService) {
        this.fileService = fileService;
        this.grades = fileService.load();
    }

    // Thêm grade
    public boolean addGrade(Grade g) {
        if (g == null || grades.contains(g))
            return false;
        grades.add(g);
        save();
        return true;
    }

    // Xóa grade theo studentId + subjectId
    public boolean removeGrade(String studentId, String subjectId) {
        Optional<Grade> opt = grades.stream()
                .filter(g -> g.getStudentId().equalsIgnoreCase(studentId)
                        && g.getSubjectId().equalsIgnoreCase(subjectId))
                .findFirst();
        if (opt.isPresent()) {
            grades.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    // Sửa grade
    public boolean updateGrade(String studentId, String subjectId,
            double diemCC, double diemBT,
            double diemGK, double diemTH, double diemCK) {
        Optional<Grade> opt = grades.stream()
                .filter(g -> g.getStudentId().equalsIgnoreCase(studentId)
                        && g.getSubjectId().equalsIgnoreCase(subjectId))
                .findFirst();
        if (opt.isPresent()) {
            Grade g = opt.get();
            g.setDiemChuyenCan(diemCC);
            g.setDiemBaiTap(diemBT);
            g.setDiemGiuaKy(diemGK);
            g.setDiemThucHanh(diemTH);
            g.setDiemCuoiKy(diemCK);
            save();
            return true;
        }
        return false;
    }

    // Tìm grade theo studentId + subjectId
    public Grade findGrade(String studentId, String subjectId) {
        return grades.stream()
                .filter(g -> g.getStudentId().equalsIgnoreCase(studentId)
                        && g.getSubjectId().equalsIgnoreCase(subjectId))
                .findFirst()
                .orElse(null);
    }

    // Lấy tất cả grades
    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }

    // Lưu vào file
    public void save() {
        fileService.save(grades);
    }

    public String getAllGradesAsString() {
        StringBuilder sb = new StringBuilder();
        for (Grade s : grades) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
}
