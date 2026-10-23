package com.javalab.lab1.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javalab.lab1.Entities.Harvest;
import com.javalab.lab1.Service.GardenBusinessService;
import com.javalab.lab1.Service.HarvestService;

import java.util.List;

@RestController
@RequestMapping("/api/harvests")
public class HarvestController 
{
    private final HarvestService harvestService;
    private final GardenBusinessService gardenBusinessService;
    public HarvestController(HarvestService harvestService, GardenBusinessService gardenBusinessService)
    {
        this.harvestService = harvestService;
        this.gardenBusinessService = gardenBusinessService;
    }

    @GetMapping
    public List<Harvest> getAllHarvests()
    {
        return harvestService.getAllHarvests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Harvest> getHarvestbyId(@PathVariable Long id)
    {
        return harvestService.getHarvestById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Harvest registerHarvest(@RequestParam Long plantingId, @RequestParam Double mass)
    {
        return gardenBusinessService.registerHarvest(plantingId, mass);
    }

    @PatchMapping("/{id}")
    public Harvest updateHarvest(@PathVariable Long id, @RequestBody Harvest harvest)
    {
        return harvestService.updateHarvest(id, harvest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }
}


