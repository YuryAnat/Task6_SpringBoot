package com.yuryanat.task6.services;

import com.yuryanat.task6.exceptions.DBException;
import com.yuryanat.task6.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService service;

    @Autowired
    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User findUser = service.getUserByLogin(s);
        if (findUser != null){
            return new org.springframework.security.core.userdetails.User(s,findUser.getPassword(),findUser.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet()));
        }else {
            throw new DBException("User not found");
        }
    }
}
