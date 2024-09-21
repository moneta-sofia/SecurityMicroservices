package com.example.rest_keycloak_admin.repository;

import com.example.rest_keycloak_admin.model.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class KeycloakRepository implements IUserRepository {

    @Autowired
    private Keycloak keycloak;

    @Value("${dh.keycloak.realm}")
    private String realm;

    @Override
    public Optional<User> findById(String id){
        UserRepresentation userRepresentation = keycloak
                .realm(realm)
                .users()
                .get(id)
                .toRepresentation();
        if (userRepresentation == null) {
            return Optional.empty(); // O manejar la excepción de forma adecuada
        }
        return Optional.of(fromRepresentation(userRepresentation));
    }

    @Override
    public List<User> findByUsername(String username){
        List<UserRepresentation> userRepresentations = keycloak
                .realm(realm)
                .users()
                .search(username);
        return userRepresentations.stream().map(user -> fromRepresentation(user)).collect(Collectors.toList());
    }

    private User fromRepresentation(UserRepresentation userRepresentation){
        return new User(userRepresentation.getId(), userRepresentation.getFirstName(), userRepresentation.getLastName(), userRepresentation.getEmail());
    }

}
