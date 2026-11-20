package com.javalab.lab1.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javalab.lab1.DTO.Harvest.HarvestRequest;
import com.javalab.lab1.DTO.Harvest.HarvestResponse;
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

    private HarvestResponse toResponse(Harvest harvest)
    {
        return new HarvestResponse(harvest.getId(),harvest.getPlanting().getId(),harvest.getDate(),harvest.getMass());
    }

    public List<Harvest> getAllHarvests() {
        return harvestRep.findAll();
    }

    public Optional<Harvest> getHarvestById(Long id) {
        return harvestRep.findById(id);
    }

    public HarvestResponse createHarvest(HarvestRequest request) {
        Planting planting = plantingRep.findById(request.plantingID()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Planting not found"));

        Harvest harvest = new Harvest();
        harvest.setPlanting(planting);
        harvest.setMass(request.mass());
        harvest.setDate(LocalDate.now());
        
        return toResponse(harvestRep.save(harvest));
    }

    public HarvestResponse updateHarvest(Long id, HarvestRequest request) {
    Harvest harvest = harvestRep.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Harvest not found"));
    Planting planting = plantingRep.findById(request.plantingID()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Planting not found"));

    harvest.setPlanting(planting);
    harvest.setMass(request.mass());
    harvest.setDate(LocalDate.now());

    return toResponse(harvestRep.save(harvest));
}


    public void deleteHarvest(Long id) {
        harvestRep.deleteById(id);
    }
}

