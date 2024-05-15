package com.example.springbootkeycloack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/test")
public class Controller {

    @GetMapping("/anonymous")
    public ResponseEntity<String> getAnonymous(){
        return ResponseEntity.ok("Hello Anonymous");
    }

    @GetMapping("/admin")
//    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> getAdmin(){
        return ResponseEntity.ok("Hello admin");
    }

    @GetMapping("/user")
//    @PreAuthorize("hasRole('user')")
    public ResponseEntity<String> getUser(Principal principal){
        return ResponseEntity.ok("Hello User");
    }
//    @PreAuthorize("hasAnyAuthority('GROUP_client','GROUP_admin')")
//    @GetMapping("/user")
//    public ResponseEntity<String> getUser(Principal principal){
//        return ResponseEntity.ok("Hello User");
//    }
}


