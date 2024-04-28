package repositories;

import entities.Discipline;
import entities.Student;
import entities.Teacher;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DisciplineRepo {
    final private EntityManager entityManager;

    public DisciplineRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public void save(Discipline discipline) {
        entityManager.getTransaction().begin();
        entityManager.persist(discipline);
        entityManager.getTransaction().commit();
    }

    public void update(Discipline discipline) {
        entityManager.getTransaction().begin();
        entityManager.merge(discipline);
        entityManager.getTransaction().commit();
    }
    public void remove(Discipline discipline) {
        entityManager.getTransaction().begin();
        entityManager.remove(discipline);
        entityManager.getTransaction().commit();
    }

    public Discipline find(long id) {
        return entityManager.find(Discipline.class, id);
    }

    public List<Discipline> findAll() {
        String jpql = "SELECT d from Discipline d";
        return entityManager.createQuery(jpql, Discipline.class).getResultList();
    }

    public List<Student> findAll(Discipline discipline) {
        String jpql = "SELECT d.students from Discipline d where d.id = " + discipline.getId();
        return entityManager.createQuery(jpql, Student.class).getResultList();
    }
    public Long count() {
        String jpql = "SELECT count(d) from Discipline d";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }
    public void printAll() {
        System.out.println(count() + " disciplines");
        findAll().forEach(System.out::println);
    }
}
