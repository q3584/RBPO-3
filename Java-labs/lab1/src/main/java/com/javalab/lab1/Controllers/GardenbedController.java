package com.javalab.lab1.Controllers;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Gardenbed>> getAllGardenbeds()
    {
        return ResponseEntity.ok(gardenbedService.getAllGardenbeds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gardenbed> getGardenbedbyId(@PathVariable Long id)
    {
        Gardenbed gardenbed = gardenbedService.getGardenbedsbyId(id);
        if (gardenbed == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gardenbed);
    }
    
    @PostMapping
    public ResponseEntity<Gardenbed> postGardenbed(@RequestBody Gardenbed gardenbed) {
        Gardenbed createdGardenbed = gardenbedService.postGardenbed(gardenbed);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGardenbed);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Gardenbed> patchGardenbed(@PathVariable Long id, @RequestBody Gardenbed gardenbed)
    {
        Gardenbed patchedGardenbed = gardenbedService.patchGardenbed(id, gardenbed);
        if (patchedGardenbed == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patchedGardenbed);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGardenbed(@PathVariable Long id) {
        gardenbedService.deleteGardenbed(id);
        return ResponseEntity.noContent().build();
    }
}
