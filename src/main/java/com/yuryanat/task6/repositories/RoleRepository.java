package com.yuryanat.task6.repositories;

import com.yuryanat.task6.models.Role;

public interface RoleRepository {
    Role findRoleByName(String role);
}
