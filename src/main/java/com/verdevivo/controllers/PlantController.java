package com.verdevivo.controllers;

import com.verdevivo.models.Plant;
import com.verdevivo.repositories.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;

    // GET all plants
    @GetMapping
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    // GET plant by ID
    @GetMapping("/{id}")
    public Plant getPlantById(@PathVariable int id) {
        return plantRepository.findById(id).orElse(null);
    }

    // GET plants by name (partial match, case insensitive)
    @GetMapping("/search")
    public List<Plant> getPlantsByName(@RequestParam String name) {
        return plantRepository.findByNameContainingIgnoreCase(name);
    }

    // POST new plant
    @PostMapping
    public Plant createPlant(@RequestBody Plant plant) {
        return plantRepository.save(plant);
    }

    // PUT (update) plant by ID
    @PutMapping("/{id}")
    public Plant updatePlant(@PathVariable int id, @RequestBody Plant updatedPlant) {
        return plantRepository.findById(id).map(plant -> {
            plant.setName(updatedPlant.getName());
            plant.setSpecies(updatedPlant.getSpecies());
            plant.setDescription(updatedPlant.getDescription());
            plant.setIsWatered(updatedPlant.getIsWatered());
            plant.setUserId(updatedPlant.getUserId());
            return plantRepository.save(plant);
        }).orElse(null);
    }

    // DELETE plant by ID
    @DeleteMapping("/{id}")
    public void deletePlant(@PathVariable int id) {
        plantRepository.deleteById(id);
    }

}
