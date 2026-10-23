package com.javalab.lab1.Service;

import com.javalab.lab1.Entities.*;
import com.javalab.lab1.Repository.*;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDate;
import java.util.List;

@Service
public class GardenBusinessService {

    private final GardenbedRep gardenbedRep;
    private final CropRep cropRep;
    private final PlantingRep plantingRep;
    private final HarvestRep harvestRep;

    public GardenBusinessService(GardenbedRep gardenbedRep, CropRep cropRep, PlantingRep plantingRep, HarvestRep harvestRep) {
        this.gardenbedRep = gardenbedRep;
        this.cropRep = cropRep;
        this.plantingRep = plantingRep;
        this.harvestRep = harvestRep;
    }

    @Transactional
    public Planting createPlantingWithAreaCheck(Long gardenbedId, Long cropId, int plantnum) {
        Gardenbed bed = gardenbedRep.findById(gardenbedId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Грядка не найдена"));
        Crop crop = cropRep.findById(cropId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Культура не найдена"));

        if (!bed.getStatus()) {throw new IllegalStateException("Грядка занята или недоступна для посадки.");}

        double requiredArea = plantnum * crop.getReqSize();
        if (requiredArea > bed.getArea()) {
            throw new IllegalArgumentException("Недостаточно площади на грядке! Требуется: " + requiredArea + ",доступно: " + bed.getArea());
        }

        Planting planting = new Planting();
        planting.setGardenbed(bed);
        planting.setCrop(crop);
        planting.setPlantnum(plantnum);
        planting.setStatus("ACTIVE");

        bed.setStatus(false);
        gardenbedRep.save(bed);

        return plantingRep.save(planting);
    }

    @Transactional
    public Harvest registerHarvest(Long plantingId, double mass) {
        Planting planting = plantingRep.findById(plantingId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Посадка не найдена"));

        if ("HARVESTED".equals(planting.getStatus())) {
            throw new IllegalStateException("Урожай с этой посадки уже был собран ранее.");
        }

        Harvest harvest = new Harvest();
        harvest.setPlanting(planting);
        harvest.setDate(LocalDate.now());
        harvest.setMass(mass);

        planting.setStatus("HARVESTED");
        plantingRep.save(planting);

        Gardenbed bed = planting.getGardenbed();
        bed.setStatus(true);
        gardenbedRep.save(bed);

        return harvestRep.save(harvest);
    }

    @Transactional
    public int freezeUserGardenBeds(Long userId) {
        List<Gardenbed> beds = gardenbedRep.findAll();
        int count = 0;
        for (Gardenbed bed : beds) {
            if (bed.getOwner().getId().equals(userId) && bed.getStatus()) {
                bed.setStatus(false);
                gardenbedRep.save(bed);
                count++;
            }
        }
        return count;
    }

    public Double getTotalHarvestMassForCrop(Long cropId) {
        List<Harvest> harvests = harvestRep.findAll();
        double totalMass = 0.0;
        for (Harvest h : harvests) {
            if (h.getPlanting().getCrop().getId().equals(cropId)) {
                totalMass += h.getMass();
            }
        }
        return totalMass;
    }

    @Transactional
    public int auditPlantings() {
        List<Planting> plantings = plantingRep.findAll();
        int updatedCount = 0;
        for (Planting p : plantings) {
            if ("ACTIVE".equals(p.getStatus()) && p.getPlantnum() <= 0) {
                p.setStatus("FAILED");
                plantingRep.save(p);
                updatedCount++;
            }
        }
        return updatedCount;
    }
}




