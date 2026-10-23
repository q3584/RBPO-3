package com.javalab.lab1.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<Gardenbed> getAllGardenbeds()
    {
        return gardenbedRep.findAll();
    }

    public Optional<Gardenbed> getGardenbedsbyId(Long id)
    {
        return gardenbedRep.findById(id);
    }

    public Gardenbed createGardenbed(Gardenbed gardenbed, Long userId) {
        User owner = userRep.findById(userId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Владелец не найден"));

        gardenbed.setOwner(owner);
        if (gardenbed.getStatus() == null) {
            gardenbed.setStatus(true);
        }
        return gardenbedRep.save(gardenbed);
    }

    public Gardenbed updateGardenbed(Long id, Gardenbed updatedBed){
        return gardenbedRep.findById(id).map(bed -> {
            bed.setArea(updatedBed.getArea());
            bed.setStatus(updatedBed.getStatus());
            return gardenbedRep.save(bed);
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Грядка не найдена"));
    }

    public void deleteGardenbed(Long id) {
        gardenbedRep.deleteById(id);
    }
}


