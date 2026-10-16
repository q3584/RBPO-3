package com.javalab.lab1.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javalab.lab1.Entities.Harvest;
import com.javalab.lab1.Service.HarvestService;
import java.util.List;

@RestController
@RequestMapping("/api/harvests")
public class HarvestController 
{
    private final HarvestService harvestService;
    public HarvestController(HarvestService harvestService)
    {
        this.harvestService = harvestService;
    }

    @GetMapping
    public ResponseEntity<List<Harvest>> getAllHarvests()
    {
        return ResponseEntity.ok(harvestService.getAllHarvests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Harvest> getHarvestbyId(@PathVariable Long id)
    {
        Harvest harvest = harvestService.getHarvestsbyId(id);
        if (harvest == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(harvest);
    }
    
    @PostMapping
    public ResponseEntity<Harvest> postHarvest(@RequestBody Harvest harvest) {
        Harvest createdHarvest = harvestService.postHarvest(harvest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHarvest);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Harvest> patchHarvest(@PathVariable Long id, @RequestBody Harvest harvest)
    {
        Harvest patchedHarvest = harvestService.patchHarvest(id, harvest);
        if (patchedHarvest == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patchedHarvest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }
}


