package com.javalab.lab1.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.javalab.lab1.DTO.Planting.PlantingRequest;
import com.javalab.lab1.DTO.Planting.PlantingResponse;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<Planting> getAllPlantings()
    {
        return plantingService.getAllPlantings();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Planting> getPlantingbyId(@PathVariable Long id)
    {
        return plantingService.getPlantingById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Planting createPlanting(@RequestBody Long gardenbedId, @RequestBody Long cropId, @RequestBody int Plantnum, @RequestBody String status)
    {
        return gardenBusinessService.createPlantingWithAreaCheck(gardenbedId, cropId, Plantnum, status);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PlantingResponse> updatePlanting(@PathVariable Long id, @RequestBody PlantingRequest request)
    {
        return ResponseEntity.ok(plantingService.updatePlanting(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePlanting(@PathVariable Long id) {
        plantingService.deletePlanting(id);
        return ResponseEntity.noContent().build();
    }
}


