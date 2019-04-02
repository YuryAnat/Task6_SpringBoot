package com.yuryanat.task6.repositories;

import com.yuryanat.task6.models.User;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface UserRepository{
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    User getUserById(int id);
    User getUserByName(String name);
    List<User> getAllUsers();
}
