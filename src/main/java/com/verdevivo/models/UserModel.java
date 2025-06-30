package com.verdevivo.models;

import com.verdevivo.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserModel {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() throws Exception {
        return userRepository.findAll();
    }

    public User getUserById(int id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new Exception("USER DOESN'T EXIST.");
        }
        return user.get();
    }

    public List<User> getUsersByName(String name) throws Exception {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        if (users.isEmpty()) {
            throw new Exception("USER DOESN'T EXIST.");
        }
        return users;
    }

    // save fuction already puts a throw auto.
    public User createUser(User user) throws Exception {
        return userRepository.save(user);
    }

    public User updateUser(int id, User updatedUser) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new Exception("USER DOESN'T EXIST.");
        }

        User user = userOptional.get();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setPlants(updatedUser.getPlants());

        return userRepository.save(user);
    }

    public void deleteUserById(int id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new Exception("USER DOESN'T EXIST.");
        }
        userRepository.deleteById(id);
    }

        public User getUsersByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("USER DOESN'T EXIST.");
        }
        return user;
    }
}
