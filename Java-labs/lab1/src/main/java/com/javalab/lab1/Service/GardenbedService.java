package com.javalab.lab1.Service;

import org.springframework.stereotype.Service;
import com.javalab.lab1.Entities.Gardenbed;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GardenbedService 
{
    private final Map<Long, Gardenbed> gardenbeds = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong(1);
    private final UserService userService;

    public GardenbedService(UserService userService)
    {
        this.userService = userService;
    }

    public List<Gardenbed> getAllGardenbeds()
    {
        return new ArrayList<>(gardenbeds.values());
    }

    public Gardenbed getGardenbedsbyId(Long id)
    {
        return gardenbeds.get(id);
    }

    public Gardenbed postGardenbed(Gardenbed gardenbed)
    {
        if (gardenbed.getuserId() != null && userService.getUserbyId(gardenbed.getuserId()) == null)
        {
            throw new RuntimeException("User with id " + gardenbed.getuserId() + " not found");
        }
        Long id = idCount.getAndIncrement();
        gardenbed.setId(id);
        gardenbeds.put(id, gardenbed);
        return gardenbed;
    }

    public Gardenbed patchGardenbed(Long id, Gardenbed patchedGardenbed)
    {
        if (!gardenbeds.containsKey(id)) {return null;}
        patchedGardenbed.setId(id);
        gardenbeds.put(id, patchedGardenbed);
        return patchedGardenbed;
    }
    
    public void deleteGardenbed(Long id)
    {
        gardenbeds.remove(id);
    }
}


