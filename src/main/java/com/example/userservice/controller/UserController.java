package com.example.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    // In user-service's controller
    @GetMapping("/users/{id}")
    public Map<String, Object> getUserById(@PathVariable int id) {
        // In a real app, you'd look this up in a database
        return Map.of("id", id, "name", "Alex");
    }
}
