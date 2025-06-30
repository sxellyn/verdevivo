package com.verdevivo.controllers;

import com.verdevivo.models.User;
import com.verdevivo.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserModel userModel;

    @GetMapping
    public List<User> getAllUsers() {
        try {
            return userModel.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        try {
            return userModel.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/search")
    public List<User> getUsersByName(@RequestParam String name) {
        try {
            return userModel.getUsersByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        try {
            return userModel.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        try {
            User user = userModel.updateUser(id, updatedUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            userModel.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{email}")
    public User getUsersByEmail(@RequestParam String email) {
        try {
            return userModel.getUsersByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
