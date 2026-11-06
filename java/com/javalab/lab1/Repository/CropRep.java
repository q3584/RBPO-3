package com.javalab.lab1.Repository;
import com.javalab.lab1.Entities.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRep extends JpaRepository<Crop, Long> {}
