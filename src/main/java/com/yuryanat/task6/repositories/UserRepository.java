package com.yuryanat.task6.repositories;

import com.yuryanat.task6.models.User;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface UserRepository{
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    User getUserById(int id);
    User getUserByName(String name);
    List<User> getAllUsers();
}
