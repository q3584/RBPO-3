package com.javalab.lab1.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javalab.lab1.DTO.Crop.CropRequest;
import com.javalab.lab1.DTO.Crop.CropResponse;
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

    private CropResponse toResponse(Crop crop)
    {
        return new CropResponse(crop.getId(),crop.getName(),crop.getSeason(),crop.getReqSize());
    }

    public List<Crop> getAllCrops()
    {
        return cropRep.findAll();
    }

    public Optional<Crop> getCropsbyId(Long id)
    {
        return cropRep.findById(id);
    }

    public CropResponse createCrop(CropRequest request) {
        Crop crop = new Crop();
        crop.setName(request.name());
        crop.setSeason(request.season());
        crop.setReqSize(request.reqSize());
        return toResponse(cropRep.save(crop));
    }

    public CropResponse updateCrop(Long id, CropRequest request){
        Crop crop = cropRep.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Crop not found"));
        crop.setName(request.name());
        crop.setSeason(request.season());
        crop.setReqSize(request.reqSize());
        return toResponse(cropRep.save(crop));
    }

    public void deleteCrop(Long id) {
        cropRep.deleteById(id);
    }
}

