package com.javalab.lab1.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javalab.lab1.Entities.Harvest;
import com.javalab.lab1.Entities.Planting;
import com.javalab.lab1.Repository.HarvestRep;
import com.javalab.lab1.Repository.PlantingRep;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HarvestService
{
    private final HarvestRep harvestRep;
    private final PlantingRep plantingRep;

    public HarvestService(HarvestRep harvestRep, PlantingRep plantingRep) {
        this.harvestRep = harvestRep;
        this.plantingRep = plantingRep;
    }

    public List<Harvest> getAllHarvests() {
        return harvestRep.findAll();
    }

    public Optional<Harvest> getHarvestById(Long id) {
        return harvestRep.findById(id);
    }

    public Harvest createHarvest(Long plantingId, Double mass) {
        Planting planting = plantingRep.findById(plantingId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Посадка не найдена"));

        Harvest harvest = new Harvest();
        harvest.setPlanting(planting);
        harvest.setMass(mass);
        
        harvest.setDate(LocalDate.now());
        return harvestRep.save(harvest);
    }

    public Harvest updateHarvest(Long id, Harvest updatedHarvest) {
    return harvestRep.findById(id).map(harvest -> {
        harvest.setMass(updatedHarvest.getMass());
        return harvestRep.save(harvest);
    }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Запись о сборе не найдена"));
}


    public void deleteHarvest(Long id) {
        harvestRep.deleteById(id);
    }
}

