package entities;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;
    @OneToOne(cascade = CascadeType.ALL)
    private Teacher teacher;

    public Account() {
    }

    public Account(String username, String password, Teacher teacher) {
        this.username = username;
        this.password = password;
        this.teacher = teacher;
    }
    public Account(String username, String password, Student student) {
        this.username = username;
        this.password = password;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
