package com.yuryanat.task6.repositories;

import com.yuryanat.task6.exceptions.DBException;
import com.yuryanat.task6.models.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final SessionFactory sessionFactory;
    private final EntityManager entityManager;

    @Autowired
    public RoleRepositoryImpl(EntityManagerFactory factory, EntityManager entityManager) {
        this.entityManager = entityManager;
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }


    @Override
    public Role findRoleByName(String roleName) {
        Session session = sessionFactory.openSession();
        Role role = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            role = session.createQuery("from Role where role = :role", Role.class).setParameter("role", roleName).getSingleResult();
            transaction.commit();
        } catch (NoResultException e) {
            if (transaction != null) {
                role = new Role(roleName);
                session.save(role);
                transaction.commit();
            } else {
                throw e;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DBException("Error found role " + roleName, e);
        } finally {
            session.close();
        }
        return role;
    }
}
