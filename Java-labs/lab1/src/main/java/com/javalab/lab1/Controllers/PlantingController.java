package com.javalab.lab1.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javalab.lab1.Entities.Planting;
import com.javalab.lab1.Service.PlantingService;
import java.util.List;

@RestController
@RequestMapping("/api/plantings")
public class PlantingController 
{
    private final PlantingService plantingService;
    public PlantingController(PlantingService plantingService)
    {
        this.plantingService = plantingService;
    }

    @GetMapping
    public ResponseEntity<List<Planting>> getAllPlantings()
    {
        return ResponseEntity.ok(plantingService.getAllPlantings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planting> getPlantingbyId(@PathVariable Long id)
    {
        Planting planting = plantingService.getPlantingsbyId(id);
        if (planting == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(planting);
    }
    
    @PostMapping
    public ResponseEntity<Planting> postPlanting(@RequestBody Planting planting) {
        Planting createdPlanting = plantingService.postPlanting(planting);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlanting);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Planting> patchPlanting(@PathVariable Long id, @RequestBody Planting planting)
    {
        Planting patchedPlanting = plantingService.patchPlanting(id, planting);
        if (patchedPlanting == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patchedPlanting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanting(@PathVariable Long id) {
        plantingService.deletePlanting(id);
        return ResponseEntity.noContent().build();
    }
}


