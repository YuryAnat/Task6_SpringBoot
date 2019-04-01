package com.yuryanat.task6.repositories;

import com.yuryanat.task6.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository{
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    User getUserById(int id);
    User getUserByName(String name);
    List<User> getAllUsers();
}
