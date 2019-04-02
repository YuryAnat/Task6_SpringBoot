package com.yuryanat.task6.repositories;

import com.yuryanat.task6.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final EntityManager entityManager;

    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleByName(String roleName) {
        return entityManager.createQuery("from Role where role = :role", Role.class).setParameter("role", roleName).getSingleResult();
    }
}