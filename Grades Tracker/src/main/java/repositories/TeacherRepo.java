package repositories;

import entities.Discipline;
import entities.Student;
import entities.Teacher;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TeacherRepo {
    final private EntityManager entityManager;

    public TeacherRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();
    }

    public void update(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.merge(teacher);
        entityManager.getTransaction().commit();
    }
    public void remove(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.remove(teacher);
        entityManager.getTransaction().commit();
    }
    public Teacher find(long id) {
        return entityManager.find(Teacher.class, id);
    }

    public List<Teacher> findAll() {
        String jpql = "SELECT t from Teacher t";
        return entityManager.createQuery(jpql, Teacher.class).getResultList();
    }

    public List<Discipline> findAll(Teacher teacher) {
        String jpql = "SELECT t.disciplines from Teacher t where t.id = " + teacher.getId();
        return entityManager.createQuery(jpql, Discipline.class).getResultList();
    }
    public Long count() {
        String jpql = "SELECT count(t) from Teacher t";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }

    public void printAll() {
        System.out.println(count() + " teachers");
        findAll().forEach(System.out::println);
    }
}
