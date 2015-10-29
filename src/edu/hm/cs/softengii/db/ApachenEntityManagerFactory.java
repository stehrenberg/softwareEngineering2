package edu.hm.cs.softengii.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by cmon on 18.05.2014.
 */
public class ApachenEntityManagerFactory {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ApachenPersistenceUnit");

    private static EntityManager entityManager;

    public ApachenEntityManagerFactory() {
        entityManager = emf.createEntityManager();
    }

    // helper methods to get entity manager
    public static EntityManager getCasherEtityManager(){
        return entityManager = emf.createEntityManager();
    }


}
