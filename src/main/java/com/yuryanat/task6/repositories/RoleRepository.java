package com.yuryanat.task6.repositories;

import com.yuryanat.task6.models.Role;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoleRepository {
    Role findRoleByName(String role);
}
