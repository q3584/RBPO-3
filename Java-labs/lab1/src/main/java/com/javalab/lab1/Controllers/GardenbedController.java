package com.javalab.lab1.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.javalab.lab1.DTO.Gardenbed.GardenbedRequest;
import com.javalab.lab1.DTO.Gardenbed.GardenbedResponse;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<Gardenbed> getAllGardenbeds()
    {
        return gardenbedService.getAllGardenbeds();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Gardenbed> getGardenbedbyId(@PathVariable Long id)
    {
        return gardenbedService.getGardenbedsbyId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GardenbedResponse> createGardenbed(@RequestBody GardenbedRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(gardenbedService.createGardenbed(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GardenbedResponse> updateGardenbed(@PathVariable Long id, @RequestBody GardenbedRequest request)
    {
        return ResponseEntity.ok(gardenbedService.updateGardenbed(id, request));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGardenbed(@PathVariable Long id) {
        gardenbedService.deleteGardenbed(id);
        return ResponseEntity.noContent().build();
    }
}
