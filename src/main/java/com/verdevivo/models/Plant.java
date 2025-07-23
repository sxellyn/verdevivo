package com.verdevivo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "plants") // name of the table
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;
    private String species;
    private String description;
    private Boolean isWatered = false;

    @Column(name = "user_id")
    private int userId;

    // constructor
    public Plant() {
    }

    // constructor with arguments
    public Plant(int id, String name, String species, String description, Boolean isWatered, int userId) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.description = description;
        this.isWatered = isWatered;
        this.userId = userId;
    }

    // Getters and Setters:

    // id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // species
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    // description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // isWatered
    public Boolean getIsWatered() {
        return isWatered;
    }

    public void setIsWatered(Boolean isWatered) {
        this.isWatered = isWatered;
    }

    // userid
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // to print:
    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", description='" + description + '\'' +
                ", isWatered=" + isWatered +
                ", userId=" + userId +
                '}';
    }
}
