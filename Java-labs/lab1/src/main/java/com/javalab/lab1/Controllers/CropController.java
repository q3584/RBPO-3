package com.javalab.lab1.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.javalab.lab1.DTO.Crop.CropRequest;
import com.javalab.lab1.DTO.Crop.CropResponse;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<Crop> getAllCrops()
    {
        return cropService.getAllCrops();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Crop> getCropbyId(@PathVariable Long id)
    {
        return cropService.getCropsbyId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CropResponse> createCrop(@RequestBody CropRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cropService.createCrop(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CropResponse> updateCrop(@PathVariable Long id, @RequestBody CropRequest request)
    {
        return ResponseEntity.ok(cropService.updateCrop(id, request));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }
}

