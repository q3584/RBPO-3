package com.javalab.lab1.Repository;
import com.javalab.lab1.Entities.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarvestRep extends JpaRepository<Harvest, Long> {}
