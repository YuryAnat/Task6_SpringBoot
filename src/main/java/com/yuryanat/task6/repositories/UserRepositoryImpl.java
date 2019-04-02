package com.yuryanat.task6.repositories;

import com.yuryanat.task6.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManagerFactory) {
        this.entityManager = entityManagerFactory;
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.createQuery("delete User where id = :id").setParameter("id", id).executeUpdate();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.createQuery("from User where id = :id", User.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public User getUserByName(String login) {
        return entityManager.createQuery("from User where login = :login", User.class).setParameter("login", login).getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User Order by id", User.class).getResultList();
    }
}
