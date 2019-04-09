package com.yuryanat.task6.controllers;

import com.yuryanat.task6.models.Role;
import com.yuryanat.task6.models.User;
import com.yuryanat.task6.services.RoleService;
import com.yuryanat.task6.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RestUserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestUserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/rest/admin")
    public ResponseEntity<List<User>> restGetAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/rest/admin/{id}")
    public ResponseEntity<User> restGetSingleUserById(@PathVariable("id") int id){
        return new ResponseEntity<>(userService.getUserByID(id),HttpStatus.OK);
    }

    @PostMapping(value = "/rest/admin/add")
    public ResponseEntity<String> restAddNewUser(@RequestBody User user){
        Set<Role> roles = user.getRoles().stream().map(r -> roleService.findRoleByName(r.getRole())).collect(Collectors.toSet());
        roles.forEach(r -> r.addUser(user));
        user.setRoles(roles);
        userService.addNewUser(user);
        return new ResponseEntity<>("{\"Status\" : \"User added\"}", HttpStatus.OK);
    }

    @PostMapping(value = "/rest/admin/delete/")
    public ResponseEntity restDeleteUserById(@RequestBody Map<String,Integer> req) throws IOException {
        userService.deleteUser(req.entrySet().iterator().next().getValue());
        ResponseEntity re = new ResponseEntity<>("{\"Status\" : \"User deleted\"}", HttpStatus.OK);
        return re;
    }

    @PostMapping(value = "/rest/admin/edit")
    public ResponseEntity<String> restEditUser(@RequestBody User user){
        Set<Role> roles = user.getRoles().stream().map(r -> roleService.findRoleByName(r.getRole())).collect(Collectors.toSet());
//        roles.forEach(r -> r.addUser(user));
        user.setRoles(roles);
        userService.updateUser(user);
        return new ResponseEntity<>("{\"Status\" : \"User edited\"}", HttpStatus.OK);
    }
}
