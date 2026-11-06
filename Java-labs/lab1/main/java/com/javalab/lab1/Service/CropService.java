package com.javalab.lab1.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javalab.lab1.Entities.Crop;
import com.javalab.lab1.Repository.CropRep;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CropService 
{
    private final CropRep cropRep;

    public CropService(CropRep cropRep) {
        this.cropRep = cropRep;
    }

    public List<Crop> getAllCrops()
    {
        return cropRep.findAll();
    }

    public Optional<Crop> getCropsbyId(Long id)
    {
        return cropRep.findById(id);
    }

    public Crop createCrop(Crop crop) {
        return cropRep.save(crop);
    }

    public Crop updateCrop(Long id, Crop updatedCrop){
        return cropRep.findById(id).map(crop -> {
            crop.setName(updatedCrop.getName());
            crop.setSeason(updatedCrop.getSeason());
            crop.setReqSize(updatedCrop.getReqSize());
            return cropRep.save(crop);
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Культура не найдена"));
    }

    public void deleteCrop(Long id) {
        cropRep.deleteById(id);
    }
}

