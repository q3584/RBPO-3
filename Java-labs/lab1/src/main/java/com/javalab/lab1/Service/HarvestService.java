package com.javalab.lab1.Service;

import org.springframework.stereotype.Service;
import com.javalab.lab1.Entities.Harvest;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class HarvestService
{
    private final Map<Long, Harvest> harvests = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong(1);
    private final PlantingService plantingService;

    public HarvestService(PlantingService plantingService)
    {
        this.plantingService = plantingService;
    }

    public List<Harvest> getAllHarvests()
    {
        return new ArrayList<>(harvests.values());
    }

    public Harvest getHarvestsbyId(Long id)
    {
        return harvests.get(id);
    }

    public Harvest postHarvest(Harvest harvest)
    {
        if (harvest.getplantingId() != null && plantingService.getPlantingsbyId(harvest.getplantingId()) == null)
        {
            throw new RuntimeException("User with id " + harvest.getplantingId() + " not found");
        }
        Long id = idCount.getAndIncrement();
        harvest.setId(id);
        harvests.put(id, harvest);
        return harvest;
    }

    public Harvest patchHarvest(Long id, Harvest patchedHarvest)
    {
        if (!harvests.containsKey(id)) {return null;}
        patchedHarvest.setId(id);
        harvests.put(id, patchedHarvest);
        return patchedHarvest;
    }
    
    public void deleteHarvest(Long id)
    {
        harvests.remove(id);
    }
}
