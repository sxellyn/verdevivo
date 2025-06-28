package com.verdevivo.models.repositories;

import com.verdevivo.models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Integer> {
    List<Plant> findByNameContainingIgnoreCase(String name);
}
