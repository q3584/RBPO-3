package com.javalab.lab1.Entities;

public class Planting 
{
    private Long id;
    private String plantnum;
    private String growth;
    private Long GardenbedId;
    private Long CropId;

    public Planting(){}

    public Planting(Long id, String plantnum, String growth, Long GardenbedId, Long CropId)
    {
        this.id = id;
        this.plantnum = plantnum;
        this.growth = growth;
        this.GardenbedId = GardenbedId;
        this.CropId = CropId;
    }

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getplantnum()
    {
        return plantnum;
    }
    public void setplantnum(String plantnum)
    {
        this.plantnum = plantnum;
    }
    public String getgrowth()
    {
        return growth;
    }
    public void setgrowth(String growth)
    {
        this.growth = growth;
    }
    public Long getGardenbedId()
    {
        return GardenbedId;
    }
    public void setGardenbedId(Long GardenbedId)
    {
        this.GardenbedId = GardenbedId;
    }
    public Long GetCropId()
    {
        return CropId;
    }
    public void SetCropId(Long CropId)
    {
        this.CropId = CropId;
    }
}

