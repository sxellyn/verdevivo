package com.verdevivo.controllers;

import com.verdevivo.models.Plant;
import com.verdevivo.models.User;
import com.verdevivo.models.UserModel;
import com.verdevivo.models.repositories.PlantRepository;
import com.verdevivo.models.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserModel userModel;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userModel.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            User user = userModel.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getUsersByName(@RequestParam String name) {
        try {
            List<User> users = userModel.getUsersByName(name);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User created = userModel.createUser(user);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        try {
            User user = userModel.updateUser(id, updatedUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            userModel.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by-email")
    public ResponseEntity<?> getUsersByEmail(@RequestParam String email) {
        try {
            User user = userModel.getUsersByEmail(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/plants")
    public ResponseEntity<?> getUserPlants(Principal principal) {
        try {
            String email = principal.getName();
            User user = userModel.getUsersByEmail(email);

            if (user == null) {
                return new ResponseEntity<>("USER NOT FOUND", HttpStatus.UNAUTHORIZED);
            }

            List<Plant> userPlants = plantRepository.findByUserId(user.getId());
            return new ResponseEntity<>(userPlants, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedUser(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userRepository.findByEmail(userDetails.getUsername());

            if (user == null) {
                return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
