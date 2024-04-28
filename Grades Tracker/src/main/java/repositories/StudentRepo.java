package repositories;

import entities.Discipline;
import entities.Grade;
import entities.Student;
import jakarta.persistence.EntityManager;

import java.util.List;

public class StudentRepo {
    final private EntityManager entityManager;

    public StudentRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Student student) {
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }
    public void remove(Student student) {
        Student foundStudent = find(student.getId());
        entityManager.getTransaction().begin();
        entityManager.remove(foundStudent);
        entityManager.getTransaction().commit();
    }
    public Student find(long id) {
        return entityManager.find(Student.class, id);
    }

    public List<Student> findAll() {
        String jpql = "SELECT s from Student s";
        return entityManager.createQuery(jpql, Student.class).getResultList();
    }

    public void update(Student student) {
        entityManager.getTransaction().begin();
        entityManager.merge(student);
        entityManager.getTransaction().commit();
    }

    public void addGrade(int id, int value, Discipline discipline) {
        Student student = find(id);
        student.addGrade(value, discipline);
        update(student);
    }
    public Long count() {
        String jpql = "SELECT count(s) from Student s";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }
    public void printAll() {
        System.out.println(count() + " students");
        findAll().forEach(System.out::println);
    }

    public void removeAll() {
        List<Student> list = findAll();
        for (Student student: list) {
            remove(student);
        }
    }

}
