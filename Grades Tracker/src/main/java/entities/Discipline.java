package entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Discipline {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL)
    private List<Grade> grades = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "discipline_student",
            joinColumns = {
            @JoinColumn(name = "discipline_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "student_id", referencedColumnName = "id")
            }

    )
    private List<Student> students = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "discipline_teacher",
            joinColumns = {
            @JoinColumn(name = "discipline_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "teacher_id", referencedColumnName = "id")
            }
    )
    private List<Teacher> teachers = new ArrayList<>();

    public Discipline() {
    }

    public Discipline(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }
    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }
    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
    }
    public List<Grade> getGrades() {
        return grades;
    }
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }
    public void removeGrade(Grade grade) {
        this.grades.remove(grade);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
