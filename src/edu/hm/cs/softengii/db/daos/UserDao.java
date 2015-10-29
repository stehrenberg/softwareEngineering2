package edu.hm.cs.softengii.db.daos;

import edu.hm.cs.softengii.db.ApachenEntityManagerFactory;
import edu.hm.cs.softengii.db.entities.UserEntity;
import edu.hm.cs.softengii.utils.PasswordGen;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by cmon on 26.10.2015.
 */
public class UserDao {

    private EntityManager entityManager = null;

    private EntityManager getEntityManager() {
        return ApachenEntityManagerFactory.getCasherEtityManager();
    }


    public boolean isEmpty(){
        return getAllUserNames().isEmpty();
    }

    public List getAllUserNames() {

        this.entityManager = getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = cb.createQuery(UserEntity.class);

        Root root = criteriaQuery.from(UserEntity.class);
        criteriaQuery.select(root.get("loginName"));

        Query query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    public UserEntity getUserFromLoginName(String loginName) {

        this.entityManager = getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = cb.createQuery(UserEntity.class);

        Root userRoot = criteriaQuery.from(UserEntity.class);

        Predicate predicate = cb.like(userRoot.get("loginName"), loginName);
        criteriaQuery.where(predicate);

        Query query = entityManager.createQuery(criteriaQuery);

        return (UserEntity) query.getSingleResult();
    }


    public UserEntity createUser(String loginName, String password, String forename, String surname, boolean isAdmin) {

        String generatedPass = null;
        try {

            generatedPass = PasswordGen.generatePassword(password);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        UserEntity user = new UserEntity(isAdmin, forename, surname, loginName, generatedPass);

        this.entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();

        return user;
    }


    public boolean isLoginCorrect(String loginName, String password) {

        this.entityManager = getEntityManager();

        String generatedPass = null;
        try {

            generatedPass = PasswordGen.generatePassword(password);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = cb.createQuery(UserEntity.class);

        Root userRoot = criteriaQuery.from(UserEntity.class);

        Predicate predicate1 = cb.like(userRoot.get("loginName"), loginName);
        Predicate predicate2 = cb.like(userRoot.get("password"), generatedPass);

        criteriaQuery.where(cb.and(predicate1, predicate2));
        Query query = entityManager.createQuery(criteriaQuery);


        if(query.getResultList() != null && query.getResultList().size() == 1) {
            return true;
        }

        return false;
    }

}
