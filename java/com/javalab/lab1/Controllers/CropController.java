package com.javalab.lab1.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('ROLE_USER')")
    public List<Crop> getAllCrops()
    {
        return cropService.getAllCrops();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('ROLE_USER')")
    public ResponseEntity<Crop> getCropbyId(@PathVariable Long id)
    {
        return cropService.getCropsbyId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Crop createCrop(@RequestBody Crop crop) {
        return cropService.createCrop(crop);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Crop updateCrop(@PathVariable Long id, @RequestBody Crop crop)
    {
        return cropService.updateCrop(id, crop);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }
}

