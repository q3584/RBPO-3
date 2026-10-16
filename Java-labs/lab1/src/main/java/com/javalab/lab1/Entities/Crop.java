package com.javalab.lab1.Entities;

public class Crop
{
    private Long id;
    private String name;
    private String season;
    private String reqSize;

    public Crop(){}

    public Crop(Long id, String name, String season, String reqSize)
    {
        this.id = id;
        this.name = name;
        this.season = season;
        this.reqSize = reqSize;
    }

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getSeason()
    {
        return season;
    }
    public void setSeason(String Season)
    {
        this.season = Season;
    }
    public String getreqSize()
    {
        return reqSize;
    }
    public void setreqSize(String reqSize)
    {
        this.reqSize = reqSize;
    }

}
