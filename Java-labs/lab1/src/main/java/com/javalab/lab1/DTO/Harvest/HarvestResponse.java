package com.javalab.lab1.DTO.Harvest;

import java.time.LocalDate;

public record HarvestResponse(Long id, Long plantingID, LocalDate date, Double mass) {
    
}
