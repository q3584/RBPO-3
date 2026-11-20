package com.javalab.lab1.DTO.Planting;

public record PlantingRequest(Long gardenbedID, Long cropID, Integer plantnum, String status) {
    
}
