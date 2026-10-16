package com.javalab.lab1.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javalab.lab1.Entities.Crop;
import com.javalab.lab1.Service.CropService;
import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController 
{
    private final CropService cropService;
    public CropController(CropService cropService)
    {
        this.cropService = cropService;
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getAllCrops()
    {
        return ResponseEntity.ok(cropService.getAllCrops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCropbyId(@PathVariable Long id)
    {
        Crop crop = cropService.getCropsbyId(id);
        if (crop == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(crop);
    }
    
    @PostMapping
    public ResponseEntity<Crop> postCrop(@RequestBody Crop crop) {
        Crop createdCrop = cropService.postCrop(crop);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCrop);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Crop> patchCrop(@PathVariable Long id, @RequestBody Crop crop)
    {
        Crop patchedCrop = cropService.patchCrop(id, crop);
        if (patchedCrop == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patchedCrop);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }
}

