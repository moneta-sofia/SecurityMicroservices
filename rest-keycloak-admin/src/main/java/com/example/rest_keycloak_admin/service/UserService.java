package com.example.rest_keycloak_admin.service;

import com.example.rest_keycloak_admin.model.User;
import com.example.rest_keycloak_admin.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findByName(String name){
        return userRepository.findByUsername(name);
    }
}
