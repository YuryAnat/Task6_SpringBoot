package com.yuryanat.task6.repositories;

import com.yuryanat.task6.exceptions.DBException;
import com.yuryanat.task6.models.Role;
import com.yuryanat.task6.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final SessionFactory sessionFactory;


    @Autowired
    public UserRepositoryImpl(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    @Override
    public boolean addUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            Set<Role> roles = user.getRoles();
            roles.forEach(role -> role.addUser(user));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DBException("Don't save user: " + user.getLogin(), e);
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User editUser = session.load(User.class,user.getId());
            editUser.setLogin(user.getLogin());
            editUser.setPassword(user.getPassword());
            editUser.setName(user.getName());
            editUser.setEmail(user.getEmail());
            editUser.setRoles(user.getRoles());
            session.update(editUser);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DBException("Don't update user: " + user.getLogin(), e);
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean deleteUser(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int result;
        try {
            transaction = session.beginTransaction();
            result = session.createQuery("delete User where id = :id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DBException("Don't delete user by id: " + id, e);
        } finally {
            session.close();
        }
        return result > 0;
    }

    @Override
    public User getUserById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User user;
        try {
            transaction = session.beginTransaction();
            user = session.createQuery("from User where id = :id", User.class).setParameter("id", id).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DBException("User not found by id: " + id, e);
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public User getUserByName(String login) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User user;
        try {
            transaction = session.beginTransaction();
            user = session.createQuery("from User where login = :login", User.class).setParameter("login", login).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DBException("User not found by login: " + login, e);
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<User> list = null;
        try {
            transaction = session.beginTransaction();
            list = session.createQuery("from User Order by id", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DBException("No users found.. check connection", e);
        } finally {
            session.close();
        }
        return list;
    }
}
