package com.yuryanat.task6.services;

import com.yuryanat.task6.models.User;
import com.yuryanat.task6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addNewUser(User user){
        userRepository.addUser(user);
    }

    public void updateUser(User user){
        userRepository.updateUser(user);
    }

    public void deleteUser(int id){
        userRepository.deleteUser(id);
    }

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public User getUserByID(int id){
        return userRepository.getUserById(id);
    }

    public User getUserByLogin(String login){
        return userRepository.getUserByName(login);
    }
}
