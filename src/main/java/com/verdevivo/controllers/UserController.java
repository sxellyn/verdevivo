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

    @GetMapping("/plants")
    public ResponseEntity<?> getUserPlants(Principal principal) {
        try {
            // Get the user's email or username from the Principal
            String email = principal.getName();

            // Look up the user by email
            User user = userModel.getUsersByEmail(email);
            if (user == null) {
                return new ResponseEntity<>("USER NOT FOUND", HttpStatus.UNAUTHORIZED);
            }

            // Get the plants owned by this user
            List<Plant> userPlants = plantRepository.findByUserId(user.getId());

            return new ResponseEntity<>(userPlants, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/me")
public ResponseEntity<User> getLoggedUser(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
    User user = userRepository.findByEmail(userDetails.getUsername());
    return new ResponseEntity<>(user, HttpStatus.OK);
}

}
