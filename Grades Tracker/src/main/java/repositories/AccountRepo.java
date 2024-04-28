package repositories;

import entities.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class AccountRepo {
    final private EntityManager entityManager;

    public AccountRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean save(Account account) {
        if(check(account.getUsername()))
            return false;
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        return true;
    }
    public void remove(Account account) {
        Account foundAccount = find(account.getId());
        entityManager.getTransaction().begin();
        entityManager.remove(foundAccount);
        entityManager.getTransaction().commit();
    }
    public Account find(long id) {
        return entityManager.find(Account.class, id);
    }

    public Account find(String username, String password) {
        Query query = entityManager.createQuery("select a from Account a where a.username = ?1 and a.password = ?2");
        query.setParameter(1, username).setParameter(2, password);
        return  (Account) query.getSingleResult();
    }
    public boolean check(String username) {
        Query query = entityManager.createQuery("select count (a) from Account a where a.username = ?1");
        query.setParameter(1, username);
        Long num = (Long) query.getSingleResult();
        return num != 0;
    }
    public boolean check(String username, String password) {
        Query query = entityManager.createQuery("select count (a) from Account a where a.username = ?1 and a.password = ?2");
        query.setParameter(1, username).setParameter(2, password);
        Long num = (Long) query.getSingleResult();
        return num != 0;
    }
    public List<Account> findAll() {
        String jpql = "SELECT a from Account a";
        return entityManager.createQuery(jpql, Account.class).getResultList();
    }

    public Account update(Account account) {
        entityManager.getTransaction().begin();
        Account updatedAccount = entityManager.merge(account);
        entityManager.getTransaction().commit();
        return updatedAccount;
    }

    public Long count() {
        String jpql = "SELECT count(a) from Account a";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }

    public void printAll() {
        System.out.println(count() + " account");
        findAll().forEach(System.out::println);
    }

    public void removeAll() {
        List<Account> list = findAll();
        for (Account account: list) {
            remove(account);
        }
    }
}
