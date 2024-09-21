package com.example.rest_keycloak_admin.repository;

import com.example.rest_keycloak_admin.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    List<User> findByUsername(String username);
    Optional<User> findById(String username);
}
