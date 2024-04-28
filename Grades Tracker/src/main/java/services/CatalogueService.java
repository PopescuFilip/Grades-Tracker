package services;

import entities.*;
import jakarta.persistence.EntityManager;
import repositories.*;

import java.util.List;

public class CatalogueService {

    private final AccountRepo accountRepo;
    private final TeacherRepo teacherRepo;
    private final StudentRepo studentRepo;
    private final GradeRepo gradeRepo;
    private final DisciplineRepo disciplineRepo;

    public CatalogueService(EntityManager entityManager) {
        this.teacherRepo = new TeacherRepo(entityManager);
        this.studentRepo = new StudentRepo(entityManager);
        this.gradeRepo = new GradeRepo(entityManager);
        this.disciplineRepo = new DisciplineRepo(entityManager);
        this.accountRepo = new AccountRepo(entityManager);
    }
    public void save(Grade grade) {
        gradeRepo.save(grade);
    }
    public void update(Grade grade) {
        gradeRepo.update(grade);
    }
    public void remove(Grade grade) {
        gradeRepo.remove(grade);
    }
    public boolean authenticate(String username, String password) {
        return accountRepo.check(username, password);
    }
    public boolean hasGrades(Discipline discipline, Student student) {
        return gradeRepo.count(discipline, student) != 0;
    }
    public Account findAccount(String username, String password) {
        return accountRepo.find(username, password);
    }

    public List<Discipline> findAllDisciplines(Teacher teacher) {
        return teacherRepo.findAll(teacher);
    }
    public List<Discipline> findAllDisciplines() {
        return disciplineRepo.findAll();
    }

    public List<Student> findAllStudents(Discipline discipline) {
        return disciplineRepo.findAll(discipline);
    }
    public List<Student> findAllStudents() {
        return studentRepo.findAll();
    }
    public List<Grade> findAllGrades() {
        return gradeRepo.findAll();
    }
    public List<Grade> findAllGrades(Discipline discipline) {
        return gradeRepo.findAll(discipline);
    }
    public List<Grade> findAllGrades(Student student) {
        return gradeRepo.findAll(student);
    }
    public List<Grade> findAllGrades(Discipline discipline, Student student) {
        return gradeRepo.findAll(discipline, student);
    }

}
