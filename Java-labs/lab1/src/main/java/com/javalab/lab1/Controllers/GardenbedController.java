package com.javalab.lab1.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javalab.lab1.Entities.Gardenbed;
import com.javalab.lab1.Service.GardenbedService;
import java.util.List;

@RestController
@RequestMapping("/api/gardenbeds")
public class GardenbedController 
{
    private final GardenbedService gardenbedService;
    public GardenbedController(GardenbedService gardenbedService)
    {
        this.gardenbedService = gardenbedService;
    }

    @GetMapping
    public List<Gardenbed> getAllGardenbeds()
    {
        return gardenbedService.getAllGardenbeds();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gardenbed> getGardenbedbyId(@PathVariable Long id)
    {
        return gardenbedService.getGardenbedsbyId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Gardenbed createGardenbed(@RequestBody Gardenbed gardenbed, @RequestParam Long userId)
    {
        return gardenbedService.createGardenbed(gardenbed, userId);
    }

    @PatchMapping("/{id}")
    public Gardenbed updateGardenbed(@PathVariable Long id, @RequestBody Gardenbed gardenbed)
    {
        return gardenbedService.updateGardenbed(id, gardenbed);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGardenbed(@PathVariable Long id) {
        gardenbedService.deleteGardenbed(id);
        return ResponseEntity.noContent().build();
    }
}
