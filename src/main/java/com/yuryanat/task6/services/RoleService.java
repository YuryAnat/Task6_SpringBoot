package com.yuryanat.task6.services;

import com.yuryanat.task6.models.Role;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoleService {
    Role findRoleByName(String role);
}
