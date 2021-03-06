package com.yuryanat.task6.controllers;

import com.yuryanat.task6.models.Role;
import com.yuryanat.task6.models.User;
import com.yuryanat.task6.services.RoleService;
import com.yuryanat.task6.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
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
    public ResponseEntity<List<User>> restGetAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/rest/admin/{id}")
    public ResponseEntity<User> restGetSingleUserById(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.getUserByID(id), HttpStatus.OK);
    }

    @PostMapping(value = "/rest/admin/add")
    public ResponseEntity<String> restAddNewUser(@RequestBody User user) {
        Set<Role> roles = user.getRoles().stream().map(r -> roleService.findRoleByName(r.getRole())).collect(Collectors.toSet());
        user.setRoles(roles);
        userService.addNewUser(user);
        return new ResponseEntity<>("{\"Status\" : \"User added\"}", HttpStatus.OK);
    }

    @PostMapping(value = "/rest/admin/delete/")
    public ResponseEntity restDeleteUserById(@RequestBody Map<String, Integer> req) throws IOException {
        userService.deleteUser(req.entrySet().iterator().next().getValue());
        return new ResponseEntity<>("{\"Status\" : \"User deleted\"}", HttpStatus.OK);
    }

    @PostMapping(value = "/rest/admin/edit")
    public ResponseEntity<String> restEditUser(@RequestBody User user) {
        Set<Role> roles = user.getRoles().stream().map(r -> roleService.findRoleByName(r.getRole())).collect(Collectors.toSet());
        user.setRoles(roles);
        userService.updateUser(user);
        return new ResponseEntity<>("{\"Status\" : \"User edited\"}", HttpStatus.OK);
    }

    @GetMapping(value = {"/rest/user/"})
    public ResponseEntity<Map<String, Object>> restUserInfo(Authentication authentication, HttpSession session) {
        Map<String, Object> userInfo = new LinkedHashMap<>();
        if (authentication != null && authentication.isAuthenticated()) {
            List<String> roles = authentication.getAuthorities().stream().map(a -> ((GrantedAuthority) a).getAuthority()).collect(Collectors.toList());
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String userName = principal.getUsername();
            userInfo.put("userName", userName);
            userInfo.put("roles", roles);
        }
        String sessionId = session.getId();
        userInfo.put("sessionId", sessionId);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
