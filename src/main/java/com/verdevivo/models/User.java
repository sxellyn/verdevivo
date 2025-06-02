package com.verdevivo.models;

import java.util.List;

public class User {

    private int id;
    private List<Plant> plants;
    private String name;
    private String password;
    private String email;

    // constructor
    public User() {
    }

    // constructor with arguments
    public User(int id, List<Plant> plants, String name, String password, String email) {
        this.id = id;
        this.plants = plants;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
