package com.javalab.lab1.Repository;
import com.javalab.lab1.Entities.Planting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantingRep extends JpaRepository<Planting, Long> {}