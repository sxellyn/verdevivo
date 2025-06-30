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
        return plantRepository.findAll();
    }

    public Plant getPlantById(int id) throws Exception {
        Optional<Plant> plant = plantRepository.findById(id);
        if (plant.isEmpty()) {
            throw new Exception("PLANTA NÃO EXISTE.");
        }
        return plant.get();
    }

    public List<Plant> getPlantsByName(String name) throws Exception {
        List<Plant> plants = plantRepository.findByNameContainingIgnoreCase(name);
        if (plants.isEmpty()) {
            throw new Exception("NENHUMA PLANTA ENCONTRADA.");
        }
        return plants;
    }

    public Plant createPlant(Plant plant) throws Exception {
        return plantRepository.save(plant);
    }

    public Plant updatePlant(int id, Plant updatedPlant) throws Exception {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if (plantOptional.isEmpty()) {
            throw new Exception("PLANTA NÃO EXISTE.");
        }

        Plant plant = plantOptional.get();
        plant.setName(updatedPlant.getName());
        plant.setSpecies(updatedPlant.getSpecies());
        plant.setIsWatered(updatedPlant.getIsWatered());
        plant.setUserId(updatedPlant.getUserId());

        return plantRepository.save(plant);
    }

    public void deletePlantById(int id) throws Exception {
        Optional<Plant> plant = plantRepository.findById(id);
        if (plant.isEmpty()) {
            throw new Exception("PLANTA NÃO EXISTE.");
        }
        plantRepository.deleteById(id);
    }
}
