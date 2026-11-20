package com.javalab.lab1.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javalab.lab1.Entities.Planting;
import com.javalab.lab1.Repository.CropRep;
import com.javalab.lab1.Repository.GardenbedRep;
import com.javalab.lab1.Repository.PlantingRep;
import com.javalab.lab1.Entities.Gardenbed;
import com.javalab.lab1.DTO.Planting.PlantingRequest;
import com.javalab.lab1.DTO.Planting.PlantingResponse;
import com.javalab.lab1.Entities.Crop;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class PlantingService {

    private final PlantingRep plantingRep;
    private final GardenbedRep gardenbedRep;
    private final CropRep cropRep;

    public PlantingService(PlantingRep plantingRep, GardenbedRep gardenbedRep, CropRep cropRep) {
        this.plantingRep = plantingRep;
        this.gardenbedRep = gardenbedRep;
        this.cropRep = cropRep;
    }

    private PlantingResponse toResponse(Planting planting)
    {
        return new PlantingResponse(planting.getId(),planting.getGardenbed().getId(),planting.getCrop().getId(),planting.getPlantnum(),planting.getStatus());
    }

    public List<Planting> getAllPlantings() {
        return plantingRep.findAll();
    }

    public Optional<Planting> getPlantingById(Long id) {
        return plantingRep.findById(id);
    }

    public PlantingResponse createPlanting(PlantingRequest request) {
        Gardenbed gardenbed = gardenbedRep.findById(request.gardenbedID()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Gardenbed not found"));
        
        Crop crop = cropRep.findById(request.cropID()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Crop not found"));

        Planting planting = new Planting();
        planting.setGardenbed(gardenbed);
        planting.setCrop(crop);
        planting.setPlantnum(request.plantnum());
        planting.setStatus(request.status());

        return toResponse(plantingRep.save(planting));
    }

    public PlantingResponse updatePlanting(Long id, PlantingRequest request) {
    Planting planting = plantingRep.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Planting not found"));
    Gardenbed gardenbed = gardenbedRep.findById(request.gardenbedID()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Gardenbed not found"));
    Crop crop = cropRep.findById(request.cropID()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Crop not found"));
    
    planting.setGardenbed(gardenbed);
    planting.setCrop(crop);
    planting.setPlantnum(request.plantnum());
    planting.setStatus(request.status());

    return toResponse(plantingRep.save(planting));
}


    public void deletePlanting(Long id) {
        plantingRep.deleteById(id);
    }
}



