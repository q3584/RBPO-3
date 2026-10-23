package com.javalab.lab1.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javalab.lab1.Entities.Planting;
import com.javalab.lab1.Service.PlantingService;
import com.javalab.lab1.Service.GardenBusinessService;
import java.util.List;

@RestController
@RequestMapping("/api/plantings")
public class PlantingController 
{
    private final PlantingService plantingService;
    private final GardenBusinessService gardenBusinessService;
    public PlantingController(PlantingService plantingService, GardenBusinessService gardenBusinessService)
    {
        this.plantingService = plantingService;
        this.gardenBusinessService = gardenBusinessService;
    }

    @GetMapping
    public List<Planting> getAllPlantings()
    {
        return plantingService.getAllPlantings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planting> getPlantingbyId(@PathVariable Long id)
    {
        return plantingService.getPlantingById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Planting createPlanting(@RequestParam Long gardenbedId, @RequestParam Long cropId, @RequestParam int Plantnum)
    {
        return gardenBusinessService.createPlantingWithAreaCheck(gardenbedId, cropId, Plantnum);
    }

    @PatchMapping("/{id}")
    public Planting updatePlanting(@PathVariable Long id, @RequestBody Planting planting)
    {
        return plantingService.updatePlanting(id, planting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanting(@PathVariable Long id) {
        plantingService.deletePlanting(id);
        return ResponseEntity.noContent().build();
    }
}


