package com.verdevivo.models;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.verdevivo.models.repositories.PlantRepository;

@Component
public class PlantModel {

    @Autowired
    private PlantRepository plantRepository;

    // Save or update a plant with try/catch error handling
    public Plant CadastrarPlant(Plant plant) {
        try {
            return this.plantRepository.save(plant);
        } catch (Exception e) {
            System.err.println("Error saving plant: " + e.getMessage());
            return null;
        }
    }

    // Delete plant by ID with validation and exception handling
    public boolean deleteById(int id) {
        try {
            Optional<Plant> plant = this.plantRepository.findById(id);
            if (plant.isEmpty()) {
                System.err.println("PLANT DOES NOT EXIST.");
                return false;
            }
            this.plantRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting plant: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all plants
    public List<Plant> findAll() {
        try {
            return this.plantRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error retrieving plants: " + e.getMessage());
            return List.of(); // return empty list on error
        }
    }

    // Find plant by ID, returns Optional to handle not found
    public Optional<Plant> findById(int id) {
        try {
            return this.plantRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error finding plant by id: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Find plants by name ignoring case
    public List<Plant> findByNameContainingIgnoreCase(String name) {
        try {
            return this.plantRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            System.err.println("Error searching plants by name: " + e.getMessage());
            return List.of(); // empty list on error
        }
    }

    // Save plant (alternative method to CadastrarPlant, also with try/catch)
    public Plant save(Plant plant) {
        try {
            return this.plantRepository.save(plant);
        } catch (Exception e) {
            System.err.println("Error saving plant: " + e.getMessage());
            return null;
        }
    }
}
