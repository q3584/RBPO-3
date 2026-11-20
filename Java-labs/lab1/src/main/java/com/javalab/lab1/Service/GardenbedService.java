package com.javalab.lab1.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javalab.lab1.DTO.Gardenbed.GardenbedRequest;
import com.javalab.lab1.DTO.Gardenbed.GardenbedResponse;
import com.javalab.lab1.Entities.Gardenbed;
import com.javalab.lab1.Entities.User;
import com.javalab.lab1.Repository.GardenbedRep;
import com.javalab.lab1.Repository.UserRep;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GardenbedService 
{
    private final GardenbedRep gardenbedRep;
    private final UserRep userRep;

    public GardenbedService(GardenbedRep gardenbedRep, UserRep userRep) {
        this.gardenbedRep = gardenbedRep;
        this.userRep = userRep;
    }

    private GardenbedResponse toResponse(Gardenbed gardenbed)
    {
        return new GardenbedResponse(gardenbed.getId(), gardenbed.getArea(),gardenbed.getAvailable(),gardenbed.getOwner().getId());
    }
    public List<Gardenbed> getAllGardenbeds()
    {
        return gardenbedRep.findAll();
    }

    public Optional<Gardenbed> getGardenbedsbyId(Long id)
    {
        return gardenbedRep.findById(id);
    }

    public GardenbedResponse createGardenbed(GardenbedRequest request) {
        User owner = userRep.findById(request.ownerID()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Owner not found"));
        Gardenbed gardenbed = new Gardenbed();
        gardenbed.setArea(request.area());
        gardenbed.setAvailable(request.available());
        gardenbed.setOwner(owner);
        Gardenbed saved = gardenbedRep.save(gardenbed);
        return toResponse(saved);
    }

    public GardenbedResponse updateGardenbed(Long id, GardenbedRequest request){
        Gardenbed gardenbed = gardenbedRep.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Gardenbed not found"));
        User owner = userRep.findById(request.ownerID()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        gardenbed.setArea(request.area());
        gardenbed.setAvailable(request.available());
        gardenbed.setOwner(owner);
        return toResponse(gardenbedRep.save(gardenbed));
    }

    public void deleteGardenbed(Long id) {
        gardenbedRep.deleteById(id);
    }
}


