package repositories;

import entities.Discipline;
import entities.Grade;
import entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class GradeRepo {
    final private EntityManager entityManager;
    public GradeRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Grade grade) {
        entityManager.getTransaction().begin();
        entityManager.persist(grade);
        entityManager.getTransaction().commit();
    }
    public Grade find(long id) {
        return entityManager.find(Grade.class, id);
    }
    public void updateGrade(Long id, Integer value) {
        Grade foundGrade = find(id);
        entityManager.getTransaction().begin();
        foundGrade.setValue(value);
        entityManager.getTransaction().commit();
    }

    public void remove(long id) {
        Grade grade = find(id);
        entityManager.getTransaction().begin();
        entityManager.remove(grade);
        entityManager.getTransaction().commit();
    }

    public void remove(Grade grade) {
        entityManager.getTransaction().begin();
        entityManager.remove(grade);
        entityManager.getTransaction().commit();
    }

    public void removeAll(long id) {
        for(Grade grade: findAll(id))
            remove(grade);
    }
    public void removeAll() {
        List<Grade> list = findAll();
        for (Grade grade: list) {
            remove(grade);
        }
    }

    public void update(Grade grade) {
        entityManager.getTransaction().begin();
        entityManager.merge(grade);
        entityManager.getTransaction().commit();
    }
    public Long count() {
        Query query = entityManager.createQuery("select count(g) from Grade g");
        return (Long) query.getSingleResult();
    }

    public Long count(Discipline discipline, Student student) {
        Query query = entityManager.createQuery("select count (g) from Grade g where g.discipline.id = ?1 and g.student.id = ?2");
        query.setParameter(1, discipline.getId()).setParameter(2, student.getId());
        return (Long) query.getSingleResult();
    }
    public List<Grade> findAllOrderByValue() {
        String jpql = "SELECT g from Grade g order by g.value desc ";
        return entityManager.createQuery(jpql, Grade.class).getResultList();
    }

    public List<Grade> findAllById(int id) {
        String jpql = "SELECT g from Grade g where g.student.id = " + id;
        return entityManager.createQuery(jpql, Grade.class).getResultList();
    }

    public List<Grade> findAll() {
        String jpql = "SELECT g from Grade g";
        return entityManager.createQuery(jpql, Grade.class).getResultList();
    }

    public List<Grade> findAll(long id) {
        String jpql = "SELECT g from Grade g where g.student.id = " + id;
        return entityManager.createQuery(jpql, Grade.class).getResultList();
    }
    public List<Grade> findAll(Discipline discipline) {
        String jpql = "select g from Grade g where g.discipline.id = " + discipline.getId();
        return entityManager.createQuery(jpql, Grade.class).getResultList();
    }

    public List<Grade> findAll(Student student) {
        String jpql = "select g from Grade g where g.student.id = " + student.getId();
        return entityManager.createQuery(jpql, Grade.class).getResultList();
    }
    public List<Grade> findAll(Discipline discipline, Student student) {
        Query query = entityManager.createQuery("select g from Grade g where g.discipline.id = ?1 and g.student.id = ?2");
        query.setParameter(1, discipline.getId()).setParameter(2, student.getId());
        return (List<Grade>) query.getResultList();
    }
    public void printAll() {
        System.out.println(count() + " grades");
        findAll().forEach(System.out::println);
    }
}
