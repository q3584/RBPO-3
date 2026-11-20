package com.javalab.lab1.DTO.Harvest;

import java.time.LocalDate;

public record HarvestRequest(Long plantingID, LocalDate date, Double mass) {
    
}
