package com.javalab.lab1.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('ROLE_USER')")
    public List<Planting> getAllPlantings()
    {
        return plantingService.getAllPlantings();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('ROLE_USER')")
    public ResponseEntity<Planting> getPlantingbyId(@PathVariable Long id)
    {
        return plantingService.getPlantingById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Planting createPlanting(@RequestParam Long gardenbedId, @RequestParam Long cropId, @RequestParam int Plantnum)
    {
        return gardenBusinessService.createPlantingWithAreaCheck(gardenbedId, cropId, Plantnum);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Planting updatePlanting(@PathVariable Long id, @RequestBody Planting planting)
    {
        return plantingService.updatePlanting(id, planting);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePlanting(@PathVariable Long id) {
        plantingService.deletePlanting(id);
        return ResponseEntity.noContent().build();
    }
}


