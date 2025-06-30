package com.verdevivo.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users") // table name on db
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // the relation: 1:N → one user can have many plants
    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
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

    // Getters and Setters:

    // id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // plants
    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    // name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // to print: 
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", plants=" + plants +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
