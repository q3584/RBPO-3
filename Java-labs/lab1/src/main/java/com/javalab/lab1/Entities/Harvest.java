package com.javalab.lab1.Entities;

public class  Harvest
{
    private Long id;
    private String date;
    private String collected;
    private Long plantingId;


    public Harvest(){}

    public Harvest(Long id, String date, String collected, Long plantingId)
    {
        this.id = id;
        this.date = date;
        this.collected = collected;
        this.plantingId = plantingId;
    }

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getdate()
    {
        return date;
    }
    public void setdate(String date)
    {
        this.date = date;
    }
    public String getcollected()
    {
        return collected;
    }
    public void setcollected(String collected)
    {
        this.collected = collected;
    }
    public Long getplantingId()
    {
        return plantingId;
    }
    public void setplantingId(Long plantingId)
    {
        this.plantingId = plantingId;
    }
}
