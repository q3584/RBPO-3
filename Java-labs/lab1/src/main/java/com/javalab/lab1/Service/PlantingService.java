package com.javalab.lab1.Service;

import org.springframework.stereotype.Service;
import com.javalab.lab1.Entities.Planting;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PlantingService 
{
    private final Map<Long, Planting> plantings = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong(1);
    private final GardenbedService gardenbedService;
    private final CropService cropService;

    public PlantingService(GardenbedService gardenbedService, CropService cropService)
    {
        this.gardenbedService = gardenbedService;
        this.cropService = cropService;
    }

    public List<Planting> getAllPlantings()
    {
        return new ArrayList<>(plantings.values());
    }

    public Planting getPlantingsbyId(Long id)
    {
        return plantings.get(id);
    }

    public Planting postPlanting(Planting planting)
    {
       if (gardenbedService.getGardenbedsbyId(planting.getGardenbedId()) == null) {
            throw new RuntimeException("GardenBed with id " + planting.getGardenbedId() + " not found!");
        }

        if (cropService.getCropsbyId(planting.GetCropId()) == null) {
            throw new RuntimeException("Crop with id " + planting.GetCropId() + " not found!");
        }
        Long id = idCount.getAndIncrement();
        planting.setId(id);
        plantings.put(id, planting);
        return planting;
    }

    public Planting patchPlanting(Long id, Planting patchedPlanting)
    {
        if (!plantings.containsKey(id)) {return null;}
        patchedPlanting.setId(id);
        plantings.put(id, patchedPlanting);
        return patchedPlanting;
    }
    
    public void deletePlanting(Long id)
    {
        plantings.remove(id);
    }
}


