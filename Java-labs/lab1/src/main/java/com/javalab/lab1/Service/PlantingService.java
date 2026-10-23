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

    public List<Planting> getAllPlantings() {
        return plantingRep.findAll();
    }

    public Optional<Planting> getPlantingById(Long id) {
        return plantingRep.findById(id);
    }

    public Planting createPlanting(Long gardenbedId, Long cropId, Integer plantnum, String status) {
        Gardenbed bed = gardenbedRep.findById(gardenbedId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Грядка не найдена"));
        
        Crop crop = cropRep.findById(cropId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Культура не найдена"));

        Planting planting = new Planting();
        planting.setGardenbed(bed);
        planting.setCrop(crop);
        planting.setPlantnum(plantnum);
        planting.setStatus(status != null ? status : "ACTIVE");

        return plantingRep.save(planting);
    }

    public Planting updatePlanting(Long id, Planting updatedPlanting) {
    return plantingRep.findById(id).map(planting -> {
        planting.setPlantnum(updatedPlanting.getPlantnum());
        planting.setStatus(updatedPlanting.getStatus());
        
            if (updatedPlanting.getGardenbed() != null) {
            planting.setGardenbed(updatedPlanting.getGardenbed());
        }
        if (updatedPlanting.getCrop() != null) {
            planting.setCrop(updatedPlanting.getCrop());
        }
        
        return plantingRep.save(planting);
    }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Посадка не найдена"));
}


    public void deletePlanting(Long id) {
        plantingRep.deleteById(id);
    }
}



