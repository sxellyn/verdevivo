package com.verdevivo.models;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.verdevivo.models.repositories.UserRepository;

@Component
public class UserModel {

    @Autowired
    private UserRepository userRepository;

    // Save or update a user
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            return null;
        }
    }

    // Delete user by ID
    public boolean deleteById(int id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                System.err.println("USER DOES NOT EXIST.");
                return false;
            }
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // Find all users
    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            return List.of();
        }
    }

    // Find user by ID
    public Optional<User> findById(int id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error finding user by id: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Find users by name ignoring case
    public List<User> findByNameContainingIgnoreCase(String name) {
        try {
            return userRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            System.err.println("Error searching users by name: " + e.getMessage());
            return List.of();
        }
    }
}
