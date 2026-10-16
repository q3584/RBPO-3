package com.javalab.lab1.Service;

import org.springframework.stereotype.Service;
import com.javalab.lab1.Entities.Crop;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CropService 
{
    private final Map<Long, Crop> crops = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong(1);

    public List<Crop> getAllCrops()
    {
        return new ArrayList<>(crops.values());
    }

    public Crop getCropsbyId(Long id)
    {
        return crops.get(id);
    }

    public Crop postCrop(Crop crop)
    {
        Long id = idCount.getAndIncrement();
        crop.setId(id);
        crops.put(id, crop);
        return crop;
    }

    public Crop patchCrop(Long id, Crop patchedCrop)
    {
        if (!crops.containsKey(id)) {return null;}
        patchedCrop.setId(id);
        crops.put(id, patchedCrop);
        return patchedCrop;
    }
    
    public void deleteCrop(Long id)
    {
        crops.remove(id);
    }
}

