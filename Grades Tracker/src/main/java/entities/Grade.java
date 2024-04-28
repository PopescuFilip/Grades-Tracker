package entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Grade {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Integer value;
    private Date date;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Discipline discipline;

    public Grade() {
    }
    public Grade(Integer value, Student student, Discipline discipline) {
        this.student = student;
        this.discipline = discipline;
        this.value = value;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", value=" + value +
                ", student=" + student.getFirstName() + " " + student.getLastName() +
                '}';
    }
}
