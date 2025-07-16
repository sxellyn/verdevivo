package com.verdevivo.controllers;

import java.util.Optional;
import com.verdevivo.models.Plant;
import com.verdevivo.models.PlantModel;
import com.verdevivo.models.repositories.PlantRepository;
import com.verdevivo.models.repositories.UserRepository;
import com.verdevivo.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    private PlantModel plantModel;
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Plant> getAllPlants() {
        try {
            return plantModel.getAllPlants();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public Plant getPlantById(@PathVariable int id) {
        try {
            return plantModel.getPlantById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/search")
    public List<Plant> getPlantsByName(@RequestParam String name) {
        try {
            return plantModel.getPlantsByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

@PostMapping
public ResponseEntity<?> addPlant(@RequestBody Plant plant, Principal principal) {
    try {
        String email = principal.getName(); // username from JWT
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("USER NOT FOUND", HttpStatus.UNAUTHORIZED);
        }

        plant.setUserId(user.getId()); // Atribui o ID do usuário logado
        Plant saved = plantModel.createPlant(plant);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);

    } catch (Exception e) {
        return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


@PutMapping("/{id}")
public ResponseEntity<?> updatePlant(@PathVariable int id, @RequestBody Map<String, Object> updates) {
    try {
        Plant plant = plantModel.getPlantById(id);

        if (updates.containsKey("isWatered")) {
            plant.setIsWatered((Boolean) updates.get("isWatered"));
        }

        // outros campos opcionais se quiser
        Plant updated = plantModel.createPlant(plant);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}


   @DeleteMapping("/{id}")
public ResponseEntity<?> deletePlant(@PathVariable int id, Principal principal) {
    try {   
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>("USER NOT FOUND", HttpStatus.UNAUTHORIZED);
        }

        Optional<Plant> optionalPlant = plantRepository.findById(id);
        if (optionalPlant.isEmpty()) {
            return new ResponseEntity<>("PLANT NOT FOUND", HttpStatus.NOT_FOUND);
        }

        Plant plant = optionalPlant.get();

        // ✅ Compare directly with userId
        if (plant.getUserId() != user.getId()) {
            return new ResponseEntity<>("UNAUTHORIZED DELETE", HttpStatus.FORBIDDEN);
        }

        plantRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch (Exception e) {
        return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
