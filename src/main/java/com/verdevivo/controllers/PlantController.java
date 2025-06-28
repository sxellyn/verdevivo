package com.verdevivo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verdevivo.models.Plant;
import com.verdevivo.models.PlantModel;

@RestController
@RequestMapping("/plants")
public class PlantController {

@Autowired
PlantModel plantModel;

    // GET all plants
    @GetMapping
    public List<Plant> getAllPlants() {
        return plantModel.findAll();
    }

    // GET plant by ID
    @GetMapping("/{id}")
    public Plant getPlantById(@PathVariable int id) {
        return plantModel.findById(id).orElse(null);
    }

    // GET plants by name (partial match, case insensitive)
    @GetMapping("/search")
    public List<Plant> getPlantsByName(@RequestParam String name) {
        return plantModel.findByNameContainingIgnoreCase(name);
    }

    // POST new plant
    @PostMapping
    public Plant createPlant(@RequestBody Plant plant) {
        return plantModel.save(plant);
    }

    // PUT (update) plant by ID
    @PutMapping("/{id}")
    public Plant updatePlant(@PathVariable int id, @RequestBody Plant updatedPlant) {
        return plantModel.findById(id).map(plant -> {
            plant.setName(updatedPlant.getName());
            plant.setSpecies(updatedPlant.getSpecies());
            plant.setDescription(updatedPlant.getDescription());
            plant.setIsWatered(updatedPlant.getIsWatered());
            plant.setUserId(updatedPlant.getUserId());
            return plantModel.save(plant);
        }).orElse(null);
    }

    // DELETE plant by ID
    @DeleteMapping("/{id}")
    public ResponseEntity deletePlant(@PathVariable int id) {
        try {
            plantModel.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
         return new ResponseEntity<>(HttpStatus.OK);
    }

}
