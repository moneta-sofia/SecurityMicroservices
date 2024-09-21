package com.example.rest_keycloak_admin.controller;

import com.example.rest_keycloak_admin.model.User;
import com.example.rest_keycloak_admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class UsersRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public User getById(@PathVariable String id){
        return userService.findById(id);
    }

    @GetMapping("/users/{name}")
    public List<User> getByName(@PathVariable String name){
        return userService.findByName(name);
    }
}
