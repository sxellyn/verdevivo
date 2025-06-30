package com.verdevivo.controllers;

import com.verdevivo.models.Plant;
import com.verdevivo.models.PlantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    private PlantModel plantModel;

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
    public Plant createPlant(@RequestBody Plant plant) {
        try {
            return plantModel.createPlant(plant);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable int id, @RequestBody Plant updatedPlant) {
        try {
            Plant plant = plantModel.updatePlant(id, updatedPlant);
            return new ResponseEntity<>(plant, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable int id) {
        try {
            plantModel.deletePlantById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
