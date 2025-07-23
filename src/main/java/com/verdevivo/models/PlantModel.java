package com.verdevivo.models;

import com.verdevivo.models.repositories.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class PlantModel {

    @Autowired
    private PlantRepository plantRepository;

    public List<Plant> getAllPlants() throws Exception {
        List<Plant> plants = plantRepository.findAll();
        if (plants == null || plants.isEmpty()) {
            throw new Exception("PLANT DOESN'T EXIST.");
        }
        return plants;
    }

    public Plant getPlantById(int id) throws Exception {
        Optional<Plant> plant = plantRepository.findById(id);
        if (plant.isEmpty()) {
            throw new Exception("PLANT DOESN'T EXIST.");
        }
        return plant.get();
    }

    public List<Plant> getPlantsByName(String name) throws Exception {
        List<Plant> plants = plantRepository.findByNameContainingIgnoreCase(name);
        if (plants.isEmpty()) {
            throw new Exception("PLANT NOT FOUND");
        }
        return plants;
    }

    public Plant createPlant(Plant plant) throws Exception {
        if (plant == null) {
            throw new Exception("INVALID PLANT DATA.");
        }
        return plantRepository.save(plant);
    }

    public void deletePlantById(int id) throws Exception {
        Optional<Plant> plant = plantRepository.findById(id);
        if (plant.isEmpty()) {
            throw new Exception("PLANT DOESN'T EXIST.");
        }
        plantRepository.deleteById(id);
    }

}
